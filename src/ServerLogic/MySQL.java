package ServerLogic;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.SqlObject;
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

	
	/**
	 * Fetches the data from the table into an object with the specified class type.
	 * Note1: This only fetches to Classes with public fields. Note2: The fields has
	 * to be in the same order as the columns in the table. Supported fields for
	 * now: int, long, java.sql.Date, java.lang.String.
	 * 
	 * @param ctype     type of the class to fetch to.
	 * @param tableName name of the table in the database
	 * @param Where     the WHERE statement in the "SELECT * FROM tableName WHERE
	 *                  where" query
	 */
	public <E> ArrayList<E> fetchTableData(Class<E> ctype, String tableName, String Where) {

		ArrayList<E> result = new ArrayList<E>();
		Field[] fields = ctype.getFields();

		IStatement statment = f -> {
			try {
				// Get columns count
				int colCnt = f.getMetaData().getColumnCount();
				if (colCnt != fields.length)
					throw new Exception("Error, the number of fields in class " + ctype.getName()
							+ " don't match the number of columns in the table!");
				// iterate over all rows
				while (f.next()) {
					E row = null;
					try {
						row = ctype.newInstance();
					} catch (InstantiationException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Set the fields by their type
					setFields(fields, row, f);

					// add the row to the result list
					result.add(row);

				}

			} catch (SQLException e) {
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		};

		executeStatement("SELECT * FROM " + tableName + " WHERE " + Where, statment);

		return result;
	}

	private <E> void setFields(Field[] fields, E row, ResultSet rs) {
		for (int i = 0; i < fields.length; i++) {
			try {
				// Set field by the field name of the class E.
				if (fields[i].getType().toString().compareTo("class java.lang.String") == 0)
					fields[i].set(row, rs.getString(i + 1));
				else if (fields[i].getType().toString().compareTo("class java.sql.Date") == 0)
					fields[i].set(row, rs.getDate(i + 1));
				else if (fields[i].getType().toString().compareTo("int") == 0)
					fields[i].set(row, rs.getInt(i + 1));
				else if (fields[i].getType().toString().compareTo("long") == 0)
					fields[i].set(row, rs.getLong(i + 1));

			} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Fetches the data from the table into an object with the specified class type.
	 * Note: This only fetches to Classes with public String fields.
	 * 
	 * @param ctype     type of the class to fetch to.
	 * @param tableName name of the table in the database
	 */
	public <E> ArrayList<E> fetchTableData(Class<E> ctype, String Where) {
		return fetchTableData(ctype, Where);
	}

	/**
	 * 
	 * Returns a 2D ArrayList of strings that contains all of the table data.
	 * 
	 * @param tableName name of that table for the query: SELECT * FROM tableName
	 *                  WHERE where.
	 * @param Where     the Where statement.
	 */
	public ArrayList<ArrayList<String>> getTableData(String tableName, String Where) {

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		IStatement statment = f -> {
			try {
				// Get columns count
				int colCnt = f.getMetaData().getColumnCount();
				// iterate over all rows
				while (f.next()) {
					// create a new row
					ArrayList<String> row = new ArrayList<String>();

					// add rows values
					for (int i = 0; i < colCnt; i++) {

						row.add(f.getString(i + 1));
					}
					// add the row to the result list
					result.add(row);
				}

			} catch (SQLException e) {
			}
		};

		executeStatement("SELECT * FROM " + tableName + " WHERE " + Where, statment);

		return result;
	}

	/**
	 * 
	 * Returns a 2D ArrayList of strings that contains all of the table data.
	 * 
	 * @param tableName name of that table for the query: SELECT * FROM tableName.
	 */
	public ArrayList<ArrayList<String>> getTableData(String tableName) {

		return getTableData(tableName, "true");
	}

	/**
	 * 
	 * 
	 * /** Prints the table data
	 * 
	 * @param separator separates each column.
	 * @param tableName name of that table for the query: SELECT * FROM tableName.
	 * @param Where     the WHERE statement.
	 */
	public void printTableData(String tableName, String separator, String Where) {
		String query = "SELECT * FROM " + tableName + " WHERE " + Where;
		IStatement statment = f -> {
			try {
				// Get columns count
				int colCnt = f.getMetaData().getColumnCount();

				for (int i = 0; i < colCnt; i++) {
					System.out.print("[" + f.getMetaData().getColumnLabel(i + 1) + "]" + separator);
				}
				// new line
				System.out.println("\n");
				// iterate over all rows
				while (f.next()) {

					// print rows values
					for (int i = 0; i < colCnt; i++) {

						System.out.print(f.getString(i + 1) + separator);
					}
					// new line
					System.out.println("");

				}

			} catch (SQLException e) {
			}
		};

		executeStatement(query, statment);
	}

	/**
	 * Prints the table data
	 * 
	 * @param separator separates each column.
	 * @param tableName name of that table for the query: SELECT * FROM tableName.
	 */
	public void printTableData(String tableName, String separator) {
		printTableData(tableName, separator, "true");
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

	// TODO: need to check if the key can always be a string, probably yes!
	// Note: Class has to match the column names.
	public <E> String updateByObject(Class<E> ctype, Object obj, String tableName, String key) {

		// TODO: check if ' ' different for 'int' and int
		String objArgs = getEveryFieldWithValue(ctype, obj);
		String Where = key + " = '" + getFieldValue(ctype, obj, key) + "'"; // TODO
		String updateQuery = "UPDATE " + tableName + " SET " + objArgs + " WHERE " + Where;

		return updateQuery;
		// executePreparedStatement(updateQuery, null);

	}

	public void updateByObject(SqlObject obj, UpdateFunc uFunc) {

		QueryBuilder qb = new QueryBuilder();

		String updateQuery = qb.update(obj.getTableName()).set(obj.getFieldsAndValues()).where(obj.getPrimaryKeyName())
				.eq(obj.getKeyValue()).toString();

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

		String query = qb.insertInto(obj.getTableName()).forColumns(obj.getFieldsNames())
				.theValues(obj.getFieldsValues()).toString();

		executePreparedStatement(query, null);

	}

	public void deleteObject(SqlObject obj) {

		String query = qb.deleteFrom(obj.getTableName()).where(obj.getPrimaryKeyName()).eq(obj.getKeyValue()).toString();

		executePreparedStatement(query, null);

	}

	public void createTable(SqlObject obj) {

		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ");
		query.append(obj.tableInfo());		  
		
		query.append(";");

		executePreparedStatement(query.toString(), null);

	}
	/* Setters and getters */

}
