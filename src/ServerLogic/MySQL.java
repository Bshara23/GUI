package ServerLogic;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.*;
import ServerLogic.UtilityInterfaces.IPreparedStatement;
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

	/* insert using object */

	// TODO *********************************************
	public void insertObject(SqlObject obj) {

		String query = qb.insertInto(obj.getTableName()).forColumns(obj.getFieldsNames())
				.theValues(obj.getFieldsValues()).toString();

		//System.out.println(query);
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

	public File getFile(long fileID) {
		String query = "SELECT * FROM `icm`.`file` WHERE `icm`.`file`.ID = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setLong(1, fileID);
			ps.execute();

			ResultSet rs = ps.getResultSet();
			if (rs.next()) {

				File file = new File(fileID, rs.getLong(2), rs.getString(4), rs.getString(5));

				file.setBytes(rs.getBlob(3).getBinaryStream(), (int) rs.getBlob(3).length());

				return file;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ChangeRequest> getChangeRequests(int options) {

		return getChangeRequests(null);
	}

	public ArrayList<Phase> getPhases() {
		return getPhases(0);
	}

	public ArrayList<Phase> getPhases(long forRequestID) {
		String query = "SELECT icm.phase.* FROM icm.changerequest "
				+ "INNER JOIN icm.phase ON icm.phase.requestID=icm.changerequest.requestID ";

		if (forRequestID != 0) {
			query += "WHERE icm.changerequest.requestID = " + forRequestID;
		}
		
		query += " ORDER BY icm.changerequest.requestID ASC ,icm.phase.requestID ASC ";


		ArrayList<Phase> results = new ArrayList<Phase>();

		IStatement prepS = rs -> {
			try {
				while (rs.next()) {

					Phase phase = new Phase(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4),
							rs.getLong(5), rs.getDate(6).toLocalDate(), rs.getDate(7).toLocalDate(), rs.getDate(8).toLocalDate(), rs.getBoolean(9));

					// add at the last change request
					results.add(phase);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;

	}

	public ArrayList<ExecutionReport> getExecutionReports(){
		return getExecutionReports(0);
	}
	
	public ArrayList<ExecutionReport> getExecutionReports(long forRequestID) {
		String query = "SELECT icm.executionreport.* FROM icm.changerequest "
				+ "INNER JOIN icm.executionreport ON icm.executionreport.requestID=icm.changerequest.requestID ";

		if (forRequestID != 0) {
			query += "WHERE icm.changerequest.username = " + forRequestID;
		}

		query += " ORDER BY icm.changerequest.requestID ASC ,icm.executionreport.requestID ASC ";

		ArrayList<ExecutionReport> results = new ArrayList<ExecutionReport>();

		IStatement prepS = rs -> {
			try {
				while (rs.next()) {

					ExecutionReport exeRep = new ExecutionReport(rs.getLong(1), rs.getLong(2), rs.getString(3),
							rs.getString(4));

					results.add(exeRep);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;

	}

	public ArrayList<EvaluationReport> getEvaluationReports() {
		return getEvaluationReports(0);
	}
	
	public ArrayList<EvaluationReport> getEvaluationReports(long forRequestID) {
		String query = "SELECT icm.evaluationreport.* FROM icm.changerequest "
				+ "INNER JOIN icm.evaluationreport ON icm.evaluationreport.requestID=icm.changerequest.requestID ";

		if (forRequestID != 0) {
			query += "WHERE icm.changerequest.username = " + forRequestID;
		}
		query += " ORDER BY icm.changerequest.requestID ASC ,icm.evaluationreport.requestID ASC ";


		ArrayList<EvaluationReport> results = new ArrayList<EvaluationReport>();

		IStatement prepS = rs -> {
			try {
				while (rs.next()) {

					EvaluationReport evalRep = new EvaluationReport(rs.getLong(5), rs.getLong(6), rs.getString(7),
							rs.getString(8), rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate());

					results.add(evalRep);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;

	}

	public ArrayList<File> getFiles() {
		return getFiles(0);
	}
	
	public ArrayList<File> getFiles(long forRequestID) {
		String query = "SELECT icm.file.* FROM icm.changerequest "
				+ "INNER JOIN icm.file ON icm.file.requestID=icm.changerequest.requestID ";
				

		if (forRequestID != 0) {
			query += "WHERE icm.changerequest.username = " + forRequestID;
		}

		query += " ORDER BY icm.changerequest.requestID ASC ,icm.file.requestID ASC ";
		ArrayList<File> results = new ArrayList<File>();

		IStatement prepS = rs -> {
			try {
				while (rs.next()) {

					File file = new File(rs.getLong(1), rs.getLong(2), rs.getString(4), rs.getString(5));

					file.setBytes(rs.getBlob(3).getBinaryStream(), (int) rs.getBlob(3).length());

					results.add(file);

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;

	}
	
	public ArrayList<ChangeRequest> getChangeRequests() {
		return getChangeRequests(0);
	}

	public ArrayList<ChangeRequest> getChangeRequests(String forUsername) {

		String query = "SELECT * FROM icm.changerequest ";

		// This is added so we get the requests for all of the users, used for the
		// overridden function.
		if (forUsername != null) {
			query += "WHERE icm.changerequest.username = 'username2' ";
		}

		query += "ORDER BY icm.changerequest.requestID ASC;";

		ArrayList<ChangeRequest> results = new ArrayList<ChangeRequest>();

		IStatement prepS = rs -> {
			try {

				while (rs.next()) {

					ChangeRequest cr = new ChangeRequest(rs.getLong(1), rs.getString(2), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(),
							rs.getDate(5).toLocalDate(), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
							rs.getString(10));

					results.add(cr);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;
	}

}
