package ServerLogic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.File;
import Entities.SqlObject;
import Entities.SqlObject.Config;
import ServerLogic.UtilityInterfaces.IStatement;
import ServerLogic.UtilityInterfaces.UpdateFunc;
import Utility.VoidFunc;

/**
 * A MySql class that is built for fast development of Client/Server
 * application.
 * 
 * @author G7_BsharaZahran
 * 
 */
public class MySQL extends MySqlConnBase {

	private QueryBuilder qb;

	public MySQL(String username, String password, String schemaName, VoidFunc connectionErrorEvent) {
		super(username, password, schemaName, connectionErrorEvent);
		qb = new QueryBuilder();
	}

	
	/* print Table columns */

	/**
	 * Prints the table columns with their indexes.
	 * 
	 * @param tableName the name of the table to print columns from.
	 */
	public void printTableColumns(String tableName) {
		IStatement statment = f -> {
			try {
				// Get columns count
				int colCnt = f.getMetaData().getColumnCount();

				for (int i = 0; i < colCnt; i++) {
					System.out.println("Column index [" + (i + 1) + "], Column name ["
							+ f.getMetaData().getColumnLabel(i + 1) + "]");
				}

			} catch (SQLException e) {
			}
		};

		executeStatement("SELECT * FROM " + tableName, statment);
	}

	/* get table column name by index */

	/**
	 * Returns the table column name by the given index.
	 * 
	 * @param index     the index of the column.
	 * @param tableName name of the table to get the column name from.
	 */
	public String getTableColumnName(int index, String tableName) {
		ArrayList<String> result = new ArrayList<String>(1);
		IStatement statment = f -> {
			try {

				result.add(f.getMetaData().getColumnLabel(index));

			} catch (SQLException e) {
			}
		};

		executeStatement("SELECT * FROM " + tableName, statment);
		return result.get(0);
	}

	/* update Table Data */

	public void updateTableData(String tableName, String Set, String Where) {
		String updateQuery = "UPDATE " + tableName + " SET " + Set + " WHERE " + Where;
		executePreparedStatement(updateQuery, null);
	}

	public void updateByObject(SqlObject obj, UpdateFunc uFunc) {

		QueryBuilder qb = new QueryBuilder();

		String updateQuery = qb.update(obj.getTableName()).set(obj.getFieldsAndValues()).where(obj.getPrimaryKeyName())
				.eq(obj.getPrimaryKeyValue()).toString();

		int numOfRowsChanged = executePreparedStatement(updateQuery, null);

		// Execute after getting the number of changed rows
		uFunc.execute(numOfRowsChanged);
	}

	public void updateByObject(SqlObject obj) {

		updateByObject(obj, null);
	}

	public <E> String getEveryFieldWithValue(Class<E> ctype, Object obj) {

		Field[] fields = ctype.getFields();
		String result = "";

		for (int i = 0; i < fields.length; i++) {
			try {
				result += fields[i].getName() + " = '";
				result += fields[i].get(obj).toString() + "'";
				if (i != fields.length - 1)
					result += ", ";
			} catch (IllegalArgumentException | IllegalAccessException e) {

				e.printStackTrace();
			}
		}

		return result;
	}

	private <E> Object getFieldValue(Class<E> ctype, Object obj, String fieldName) {
		Field[] fields = ctype.getFields();

		for (int i = 0; i < fields.length; i++) {
			try {
				if (fields[i].getName().compareTo(fieldName) == 0) {
					return fields[i].get(obj);
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {

				e.printStackTrace();
			}
		}
		try {
			throw new Exception("Error, Field [" + fieldName + "] not found in class " + ctype.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/* insert using object */

	// TODO *********************************************
	public void insertObject(SqlObject obj) {

		String query = qb.insertInto(obj.getTableName()).forColumns(obj.getFieldsNamesForInsertion())
				.theValues(obj.getFieldsValuesForInsertion()).toString();

		// System.out.println(query);
		executePreparedStatement(query, null);

	}

	public void deleteObject(SqlObject obj) {

		String query = qb.deleteFrom(obj.getTableName()).where(obj.getPrimaryKeyName()).eq(obj.getPrimaryKeyValue())
				.toString();

		executePreparedStatement(query, null);

	}

	public void createTable(SqlObject obj) {

		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ");
		query.append(obj.tableInfo());

		query.append(";");

		executePreparedStatement(query.toString(), null);

	}

	public boolean insertFile(File file) {

		String query = "INSERT INTO `icm`.`file` (`requestID`, `data`, `fileName`, `type`) VALUES (?, ?, ?, ?);";
		try {

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setLong(1, file.getRequestID());
			ps.setBlob(2, file.getBinaryStream());
			ps.setString(3, file.getFileName());
			ps.setString(4, file.getType());

			return ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public File getFile(long ID) {
		String query = "SELECT * FROM `icm`.`file` WHERE `icm`.`file`.ID = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setLong(1, ID);
			ps.execute();

			ResultSet rs = ps.getResultSet();
			if (rs.next()) {

				File file = new File(ID, rs.getLong(2), rs.getString(4), rs.getString(5));

				file.setBytes(rs.getBlob(3).getBinaryStream(), (int) rs.getBlob(3).length());

				return file;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* Setters and getters */

}
