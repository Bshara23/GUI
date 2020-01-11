package ServerLogic;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.*;
import Protocol.PhaseStatus;
import Protocol.PhaseType;
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

		System.out.println(updateQuery);
		int numOfRowsChanged = executePreparedStatement(updateQuery, null);

		// Execute after getting the number of changed rows
		// uFunc.execute(numOfRowsChanged);
	}

	public void updateByObject(SqlObject obj) {

		updateByObject(obj, null);
	}

	public <E> String getEveryFieldWithValue(Class<E> ctype, Object obj) {

		Field[] fields = ctype.getFields();
		String result = "";

		for (int i = 0; i < fields.length; i++) {
			try {
				result += "`" + fields[i].getName() + "` = '";
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

	/**
	 * Check if an object exist by return true if it does exist, otherwise false.
	 */
	public boolean doesObjectExist(SqlObject obj) {

		String query = qb.select(qb.count(obj.getPrimaryKeyName())).from(obj.getTableName())
				.where(obj.getPrimaryKeyName()).eq(obj.getPrimaryKeyValue()).toString();

		ArrayList<Integer> res = new ArrayList<Integer>();
		executeStatement(query, rs -> {
			try {
				rs.next();
				res.add(rs.getInt(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// if result was not added or the result is 0 then return false
		// otherwise return true.
		return res.size() == 0 ? false : res.get(0) > 0 ? true : false;
	}

	/**
	 * Returns a new max id by the parameter object table.
	 */
	public long getNewMaxID(SqlObject obj) {

		String query = qb.select(qb.max(obj.getPrimaryKeyName())).from(obj.getTableName()).toString();

		ArrayList<Long> res = new ArrayList<Long>();
		executeStatement(query, rs -> {
			try {
				rs.next();
				res.add(rs.getLong(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		// Return the max id + 1 to get a new max id.
		return res.get(0) + 1;
	}

	public int insertObject(SqlObject obj) {

		String query = qb.insertInto(obj.getTableName()).forColumns(obj.getFieldsNames())
				.theValues(obj.getFieldsValues()).toString();

		System.out.println(query);

		return executePreparedStatement(query, null) > 0 ? 1 : 0;

	}

	public int deleteObject(SqlObject obj) {

		String query = qb.deleteFrom(obj.getTableName()).where(obj.getPrimaryKeyName()).eq(obj.getPrimaryKeyValue())
				.toString();

		System.out.println(query);
		return executePreparedStatement(query, null) > 0 ? 1 : 0;

	}

	public void createTable(SqlObject obj) {

		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE IF NOT EXISTS ");
		query.append(obj.tableInfo());

		query.append(";");

		executePreparedStatement(query.toString(), null);

	}

	public int insertFile(File file) {

		String query = "INSERT INTO `icm`.`file` (`requestID`, `data`, `fileName`, `type`) VALUES (?, ?, ?, ?);";
		try {

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setLong(1, file.getRequestID());
			ps.setBlob(2, file.getBinaryStream());
			ps.setString(3, file.getFileName());
			ps.setString(4, file.getType());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
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

		return getChangeRequests(null, -1, -1);
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
							rs.getLong(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getTimestamp(8),
							rs.getTimestamp(9), rs.getBoolean(10));

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

	public ArrayList<ExecutionReport> getExecutionReports() {
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
							rs.getString(8), rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));

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

	// SELECT COUNT(*) FROM icm.changerequest WHERE icm.changerequest.username =
	// 'username2'

	public int getCountOf(SqlObject obj, String whereCondition) {

		String query = qb.select(qb.count("*")).from(obj.getTableName()).where(whereCondition).toString();

		return executeStatement(query);

	}

	public ArrayList<ChangeRequest> getChangeRequests(String forUsername, int startingRow, int size) {

		String query = "SELECT * FROM icm.changerequest ";

		// This is added so we get the requests for all of the users, used for the
		// overridden function.
		if (forUsername != null) {
			query += "WHERE icm.changerequest.username = '" + forUsername + "' ";
		}

		query += "ORDER BY icm.changerequest.requestID ASC ";

		if (size > 0)
			query += "LIMIT " + startingRow + ", " + size;

		ArrayList<ChangeRequest> results = new ArrayList<ChangeRequest>();

		IStatement prepS = rs -> {
			try {

				while (rs.next()) {

					ChangeRequest cr = new ChangeRequest(rs.getLong(1), rs.getString(2), rs.getTimestamp(3),
							rs.getTimestamp(4), rs.getTimestamp(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10));

					results.add(cr);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;
	}

	public ArrayList<ChangeRequest> getChangeRequestPhaseByEmployee(long empNum, PhaseType phaseType) {

		String query = "SELECT * FROM icm.employee as emp "
				+ "INNER JOIN icm.phase as ph ON ph.empNumber = emp.empNumber "
				+ "INNER JOIN icm.changerequest as cr ON cr.requestID = ph.requestID " + "WHERE ph.empNumber = '"
				+ empNum + "' AND ph.status != 'Waiting To Set Evaluator' AND ph.phaseName = '" + phaseType.name()
				+ "' ORDER BY ph.deadline ASC";

		ArrayList<ChangeRequest> results = new ArrayList<ChangeRequest>();

		IStatement prepS = rs -> {

			// o = offset
			try {

				while (rs.next()) {
					int o = 4;

					Phase phase = new Phase(rs.getLong(o + 1), rs.getLong(o + 2), rs.getString(o + 3),
							rs.getString(o + 4), rs.getLong(o + 5), rs.getTimestamp(o + 6), rs.getTimestamp(o + 7),
							rs.getTimestamp(o + 8), rs.getTimestamp(o + 9), rs.getBoolean(o + 10));

					o += 10;

					ChangeRequest changeRequest = new ChangeRequest(rs.getLong(o + 1), rs.getString(o + 2),
							rs.getTimestamp(o + 3), rs.getTimestamp(o + 4), rs.getTimestamp(o + 5), rs.getString(o + 6),
							rs.getString(o + 7), rs.getString(o + 8), rs.getString(o + 9), rs.getString(o + 10));

					changeRequest.getPhases().add(phase);

					results.add(changeRequest);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;
	}

	public int updateMessage(Message msg) {

		String query = "UPDATE `icm`.`message` SET `subject` = ?, `from` = ?, `to` = ?,"
				+ " `messageContentLT` = ?, `hasBeenViewed` = ?, `sentAt` = ?,"
				+ " `isStarred` = ?, `isRead` = ?, `isArchived` = ?, `requestId` = ?, `phaseId` = ? WHERE (`messageID` = ?);";

		IPreparedStatement prepS = ps -> {
			try {

				ps.setString(1, msg.getSubject());
				ps.setString(2, msg.getFrom());
				ps.setString(3, msg.getTo());
				ps.setString(4, msg.getMessageContentLT());
				ps.setBoolean(5, msg.isHasBeenViewed());
				ps.setTimestamp(6, msg.getSentAt());
				ps.setBoolean(7, msg.isStarred());
				ps.setBoolean(8, msg.isRead());
				ps.setBoolean(9, msg.isArchived());
				ps.setLong(10, msg.getRequestId());
				ps.setLong(11, msg.getPhaseId());
				ps.setLong(12, msg.getMessageID());

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		return executePreparedStatement(query, prepS);

	}

	public Message getMessage(long msgID) {
		String query = "SELECT * FROM icm.message where icm.message.messageID = '" + msgID + "';";

		ArrayList<Message> res = new ArrayList<Message>(1);
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					Message msg = new Message(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getBoolean(6), rs.getTimestamp(7), rs.getBoolean(8), rs.getBoolean(9),
							rs.getBoolean(10), rs.getLong(11), rs.getLong(12));

					res.add(msg);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return res.get(0);
	}

	public ArrayList<Message> getMessages(String forUsername, int startingRow, int size) {
		String query = "SELECT * FROM icm.message WHERE icm.message.to = '" + forUsername + "' ";

		query += "ORDER BY icm.message.sentAt DESC ";

		if (size > 0)
			query += "LIMIT " + startingRow + ", " + size;

		ArrayList<Message> results = new ArrayList<Message>();

		IStatement prepS = rs -> {
			try {

				while (rs.next()) {

					Message cr = new Message(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getBoolean(6), rs.getTimestamp(7), rs.getBoolean(8), rs.getBoolean(9),
							rs.getBoolean(10), rs.getLong(11), rs.getLong(12));

					results.add(cr);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;
	}

	public int getCountOfPhasesByType(long empNumberForPhases, PhaseType phaseType) {
		String query = "SELECT COUNT(*) FROM icm.phase as ph WHERE ph.empNumber = '" + empNumberForPhases
				+ "' AND ph.phaseName = '" + phaseType.name() + "'";

		ArrayList<Integer> results = new ArrayList<Integer>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.get(0);
	}

	public ArrayList<Phase> getPhasesOfRequest(long requestID) {
		String query = "SELECT * " + "FROM icm.phase as ph "
				+ "INNER JOIN icm.changerequest as cr ON cr.requestID = ph.requestID "
				+ "LEFT JOIN icm.phasetimeextensionrequest as pte ON pte.phaseID = ph.phaseID "
				+ "WHERE ph.requestID = '" + requestID + "';";

		ArrayList<Phase> results = new ArrayList<Phase>();
		IStatement prepS = rs -> {
			try {

				while (rs.next()) {

					int offset = 0;

					Phase phase = new Phase(rs.getLong(offset + 1), rs.getLong(offset + 2), rs.getString(offset + 3),
							rs.getString(offset + 4), rs.getLong(offset + 5), rs.getTimestamp(offset + 6),
							rs.getTimestamp(offset + 7), rs.getTimestamp(offset + 8), rs.getTimestamp(offset + 9),
							rs.getBoolean(offset + 10));

					offset = 10 + 10;

					if (rs.getLong(offset + 1) == 0) {
						System.out.println("Null is found");
					} else {

						System.out.println(
								rs.getLong(offset + 1) + " is found, adding PhaseTimeExtensionRequest to the phase");
						PhaseTimeExtensionRequest pter = new PhaseTimeExtensionRequest(rs.getLong(offset + 1),
								rs.getInt(offset + 2), rs.getInt(offset + 3), rs.getString(offset + 4));
						phase.setPhaseTimeExtensionRequest(pter);
					}

					results.add(phase);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results;

	}

	public SystemUser getSystemUserByRequestID(long requestId) {
		String query = "SELECT *  FROM icm.systemUser as su "
				+ "INNER JOIN icm.changerequest as cr ON cr.username = su.userName WHERE cr.requestID = '" + requestId
				+ "';";

		ArrayList<SystemUser> results = new ArrayList<SystemUser>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					SystemUser systemUser = new SystemUser(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
					results.add(systemUser);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);

		if (results.size() == 1) {
			return results.get(0);
		} else {
			System.err.println("Error, user not found for the request id " + requestId);
			return SystemUser.getEmptyInstance();
		}
	}

	public Employee getEmployeeByEmpNumber(long empId) {
		String query = "SELECT * FROM icm.systemuser as su  "
				+ "INNER JOIN icm.employee as emp ON su.username = emp.userName WHERE emp.empNumber = '" + empId + "';";

		ArrayList<Employee> results = new ArrayList<Employee>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					Employee employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getLong(8), rs.getString(9),
							rs.getString(10));

					results.add(employee);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);

		if (results.size() == 1) {
			return results.get(0);
		} else {
			System.err.println("Error, user not found for the request id " + empId);
			return Employee.getEmptyInstance();
		}
	}

	public String getFullNameByUsername(String username45) {
		String query = "SELECT su.firstName, su.lastName FROM icm.systemuser as su WHERE su.userName = '" + username45
				+ "';";

		ArrayList<String> results = new ArrayList<String>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					results.add(rs.getString(1));
					results.add(rs.getString(2));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);

		return results.get(0) + " " + results.get(1);

	}

	public int getCountOfPhaseForEmpByUsername(String username23) {

		String query = "SELECT COUNT(ph.phaseName) FROM icm.employee as emp "
				+ "INNER JOIN icm.systemUser as su ON su.userName = emp.userName "
				+ "INNER JOIN icm.phase as ph ON ph.empNumber = emp.empNumber WHERE su.userName = '" + username23 + "'";

		ArrayList<Integer> results = new ArrayList<Integer>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);

		return results.get(0);

	}

	public boolean isUserManager(String username23) {

		String query = "SELECT COUNT(*) FROM icm.systemUser as su "
				+ "INNER JOIN icm.manager as m ON m.userName = su.userName WHERE su.userName = '" + username23 + "'";

		ArrayList<Integer> results = new ArrayList<Integer>();
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);

		return results.get(0) == 1;
	}

	public boolean isEmployeeIsSupervisor(long empNumber) {
		String query = "SELECT COUNT(*) FROM icm.supervisor as sv WHERE sv.empNumber = '" + empNumber + "'";

		ArrayList<Integer> results = new ArrayList<Integer>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.get(0) == 1;
	}

	public ArrayList<ChangeRequest> getChangeRequestWithCurrentPhase() {
		String query = "SELECT * FROM icm.phase AS ph "
				+ "INNER JOIN icm.changeRequest as cr ON cr.requestID = ph.requestID order by cr.startDateOfRequest;";

		ArrayList<ChangeRequest> results = new ArrayList<ChangeRequest>();

		IStatement prepS = rs -> {

			// o = offset
			try {

				while (rs.next()) {
					int o = 0;

					Phase phase = new Phase(rs.getLong(o + 1), rs.getLong(o + 2), rs.getString(o + 3),
							rs.getString(o + 4), rs.getLong(o + 5), rs.getTimestamp(o + 6), rs.getTimestamp(o + 7),
							rs.getTimestamp(o + 8), rs.getTimestamp(o + 9), rs.getBoolean(o + 10));

					o += 10;

					ChangeRequest changeRequest = new ChangeRequest(rs.getLong(o + 1), rs.getString(o + 2),
							rs.getTimestamp(o + 3), rs.getTimestamp(o + 4), rs.getTimestamp(o + 5), rs.getString(o + 6),
							rs.getString(o + 7), rs.getString(o + 8), rs.getString(o + 9), rs.getString(o + 10));

					changeRequest.getPhases().add(phase);

					results.add(changeRequest);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		executeStatement(query, prepS);

		return results;
	}

	public void insertMessage(Message msg) {

		String query = "INSERT INTO `icm`.`message` (`subject`, `from`, `to`, `messageContentLT`, "
				+ "`hasBeenViewed`, `sentAt`, `isStarred`, `isRead`, `isArchived`, `requestId`, `phaseId`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
		try {

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, msg.getSubject());
			ps.setString(2, msg.getFrom());
			ps.setString(3, msg.getTo());
			ps.setString(4, msg.getMessageContentLT());

			ps.setBoolean(5, msg.isHasBeenViewed());

			ps.setTimestamp(6, msg.getSentAt());

			ps.setBoolean(7, msg.isStarred());
			ps.setBoolean(8, msg.isRead());
			ps.setBoolean(9, msg.isArchived());
			ps.setLong(10, msg.getRequestId());
			ps.setLong(11, msg.getPhaseId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUsernameOfSupervisor() {
		String query = "SELECT su.userName FROM icm.supervisor as s\r\n"
				+ "inner join icm.employee as e on e.empNumber = s.empNumber\r\n"
				+ "inner join icm.systemuser as su on su.userName = e.userName;";

		ArrayList<String> results = new ArrayList<String>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getString(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) : "Supervisor not assigned";
	}

	public static void main(String[] args) {

		MySQL db = new MySQL("root", "Aa123456", "icm", null);

		System.out.println(db.getUsernameOfSupervisor());

	}

	public boolean isPhaseStatus(long phaseId, PhaseStatus status) {
		String query = "SELECT COUNT(*) FROM icm.phase as ph where ph.phaseID = '" + phaseId + "' and ph.status = '"
				+ status.name() + "';";

		ArrayList<Integer> results = new ArrayList<Integer>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) == 1 : false;
	}

	public ChangeRequest getChangeRequestById(long requestID) {

		String query = "SELECT * FROM icm.changerequest as cr where cr.requestID = '" + requestID + "';";

		ArrayList<ChangeRequest> results = new ArrayList<ChangeRequest>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					int o = 0;
					ChangeRequest changeRequest = new ChangeRequest(rs.getLong(o + 1), rs.getString(o + 2),
							rs.getTimestamp(o + 3), rs.getTimestamp(o + 4), rs.getTimestamp(o + 5), rs.getString(o + 6),
							rs.getString(o + 7), rs.getString(o + 8), rs.getString(o + 9), rs.getString(o + 10));

					results.add(changeRequest);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) : null;
	}

	public ArrayList<Employee> getEmployees() {

		String query = "SELECT * FROM icm.systemUser as su\r\n"
				+ "inner join icm.employee as e on e.userName=su.userName;";

		ArrayList<Employee> results = new ArrayList<Employee>();

		IStatement prepS = rs -> {
			try {

				while (rs.next()) {

					Employee emp = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getLong(8), rs.getString(9),
							rs.getString(10));

					results.add(emp);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results;
	}

	public String getUsernameByEmpNumber(long empNumber) {

		String query = "SELECT su.userName FROM icm.systemUser as su "
				+ "inner join icm.employee as e on e.userName=su.userName where e.empNumber = '" + empNumber + "';";

		ArrayList<String> results = new ArrayList<String>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {

					results.add(rs.getString(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) : "";

	}

	public boolean insertTimeExtension(PhaseTimeExtensionRequest pter) {

		String query = "INSERT INTO `icm`.`phasetimeextensionrequest` (`phaseID`, `requestedTimeInDays`, `requestedTimeInHours`, `description`) VALUES (?, ?, ?, ?);";

		IPreparedStatement prepS = ps -> {
			try {

				ps.setLong(1, pter.getPhaseID());
				ps.setInt(2, pter.getRequestedTimeInDays());
				ps.setInt(3, pter.getRequestedTimeInHours());
				ps.setString(4, pter.getDescription());

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		return executePreparedStatement(query, prepS) == 1;

	}

	public boolean updatePhaseStatus(long phaseId, PhaseStatus status) {

		String statusName = status.nameNo_();

		String query = "UPDATE `icm`.`phase` SET `status` = ? WHERE (`phaseID` = ?);";

		IPreparedStatement prepS = ps -> {
			try {

				ps.setString(1, statusName);
				ps.setLong(2, phaseId);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		return executePreparedStatement(query, prepS) == 1;

	}

	public long getRequestIdByPhaseId(long phId) {

		String query = "SELECT p.requestID FROM icm.phase as p where p.phaseID = '" + phId + "';";

		ArrayList<Long> results = new ArrayList<Long>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getLong(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) : -1;

	}

	public String getFullNameByPhaseId(long phId) {

		String query = "SELECT s.firstName, s.lastName FROM icm.phase as p\r\n"
				+ "inner join icm.employee as e on p.empNumber = e.empNumber\r\n"
				+ "inner join icm.systemUser as s on s.userName = e.userName where p.phaseID = '" + phId + "';";

		ArrayList<String> results = new ArrayList<String>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getString(1));
					results.add(rs.getString(2));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		};

		executeStatement(query, prepS);
		return results.size() == 0 ? "" : results.get(0) + " " + results.get(1);
	}

	public long getComHeadEmpNum() {
		
		String query = "SELECT e.empNumber FROM icm.employee as e\r\n"
				+ "inner join icm.executionchangescommitteemember as c on c.empNumber = e.empNumber\r\n"
				+ "where c.isManager = '1'";

		ArrayList<Long> results = new ArrayList<Long>();

		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					results.add(rs.getLong(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results.size() == 1 ? results.get(0) : -1;

	}

}
