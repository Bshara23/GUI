package ServerLogic;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.*;
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
							rs.getString(8), rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate());

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
				+ empNum + "' AND ph.phaseName = '" + phaseType.name() + "' ORDER BY ph.deadline ASC";

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
				+ " `isStarred` = ?, `isRead` = ?, `isArchived` = ? WHERE (`messageID` = ?);";

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
				ps.setLong(10, msg.getMessageID());

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
							rs.getBoolean(10));

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
							rs.getBoolean(10));

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
								rs.getTimestamp(offset + 2), rs.getString(offset + 2));
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
				+ "INNER JOIN icm.changeRequest as cr ON cr.requestID = ph.requestID WHERE ph.status = 'Active';";

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

	/**
	 * @author EliaB get counter of request with specific status
	 */
	public ArrayList<Integer> GetCounterOfPhaseByStatus(int i) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		String query = "SELECT count(phaseID),p.status from icm.phase as p WHERE p.startingDate <=CURDATE() - INTERVAL "
				+ i + " DAY AND p.timeOfCompletion <=CURDATE() - INTERVAL " + i + " DAY  group by p.status;";
		IStatement prepS = rs -> {

			try {
				

				while (rs.next()) {
				
					results.add(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results;
	}

	/**
	 * @author EliaB update request description by id
	 */
	public int updateRequestStatusByID(String RequestID, String status) {
		String query = "update icm.changerequest set descriptionOfCurrentStateLT=? where requestID=?;";

		IPreparedStatement prepS = ps -> {
			try {

				ps.setString(1, status);
				ps.setString(2, RequestID);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		return executePreparedStatement(query, prepS);
	}

	/**
	 * @author EliaB update phase description by id
	 */
	public int updatePhaseStatusByID(String PhaseID, String status) {
		String query = "update icm.changerequest set status=? where phaseID=?;";

		IPreparedStatement prepS = ps -> {
			try {

				ps.setString(1, status);
				ps.setString(2, PhaseID);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};

		return executePreparedStatement(query, prepS);
	}

	/**
	 * @author EliaB insert new phase to database
	 * 
	 */
	public int insertPhase(int phaseID, String status, int requestID, int empNumber, Timestamp ts) {

		String query = "INSERT INTO `icm`.`phase` (`phaseId`,`requestID`, `phaseName`,`status`,`empNumber`, `deadline`, `estimatedTimeOfCompletion`,`timeOfCompletion`,`startingDate`,`hasBeenTimeExtended`) "
				+ "VALUES (?, ?, ?, ?,?,?,?,?,?,?);";
		try {

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setInt(1, phaseID);
			ps.setInt(2, requestID);
			ps.setString(3, "evaluation");
			ps.setString(4, status);
			ps.setInt(5, empNumber);
			ps.setTimestamp(6, ts);
			ps.setTimestamp(7, ts);
			ps.setTimestamp(8, ts);
			ps.setTimestamp(9, ts);
			ps.setInt(10, 0);

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * @author EliaB
	 * get counter of phases by status from two different dates
	 * */
	public HashMap<String,Integer> GetCounterOfPhasesBetweenTwoDates(Timestamp from,Timestamp to, int minus,int plus){
		
		ArrayList<Integer> totalWorkingCount=new ArrayList<>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Integer> results = new HashMap<String,Integer>();
		String query="select sum(timestampdiff(day,p.startingDate, p.deadline)) from icm.phase as p " + 
				"where (p.startingDate>= '"+sdf.format(from)+"' and p.deadline<='"+sdf.format(to).toString()+"'  ) or " + 
				"(p.startingDate<='"+sdf.format(from)+"' and p.deadline>='"+sdf.format(from)+"' and p.deadline<='"+sdf.format(to).toString()+"'  )or " + 
				"(p.startingDate>='"+sdf.format(from)+"' and p.deadline>='"+sdf.format(to).toString()+"'  and p.startingDate<='"+sdf.format(to).toString()+"'  ) or " + 
				"(p.startingDate<='"+sdf.format(from)+"' and p.deadline>='"+sdf.format(to).toString()+"' ) ;";
		IStatement prepS = rs -> {

			try {
				

				if (rs.next()) {
					
					totalWorkingCount.add(rs.getInt(1));
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		
		String query1="select count(p.phaseID),p.status from icm.phase as p " + 
				"where (p.startingDate>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.startingDate<='"+sdf.format(from).toString()+"' + interval "+plus+" day ) or " + 
					  "(p.deadline>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.deadline<='"+sdf.format(from).toString()+"' + interval "+plus+" day)or " + 
				"(p.startingDate<='"+sdf.format(from)+"' + interval "+minus+" day and p.deadline>='"+sdf.format(from).toString()+"' + interval "+plus+" day ) group by p.status;";
		IStatement prepS1 = rs -> {

			try {
				

				while (rs.next()) {
					
					results.put(rs.getString(2),rs.getInt(1));
					
				}
				results.put("totalWorkingCount", totalWorkingCount.get(0));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query1, prepS1);
		return results;
		
		
	}
	/**
	 * @author EliaB
	 * get sum of length (estimatedTimeOfCompletion - deadline) where time has extended from two different dates
	 * */
	public HashMap<String,Integer> GetSumOfTwoDiffernceDateBetweenTwoDates(Timestamp from,int minus,int plus){
	
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Integer> results = new HashMap<String,Integer>();
		
		
		String query="select   sum(timestampdiff(day, p.deadline,p.estimatedTimeOfCompletion)) AS  sum from icm.phase as p " + 
				"where p.deadline<=p.estimatedTimeOfCompletion and p.hasBeenTimeExtended=1 and ((p.startingDate>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.startingDate<='"+sdf.format(from).toString()+"' + interval "+plus+" day ) or " + 
				  "(p.deadline>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.deadline<='"+sdf.format(from).toString()+"' + interval "+plus+" day)or " + 
			"(p.startingDate<='"+sdf.format(from)+"' + interval "+minus+" day and p.deadline>='"+sdf.format(from).toString()+"' + interval "+plus+" day ));";
		IStatement prepS = rs -> {

			try {
				

				while (rs.next()) {
					
					results.put("["+minus+"-"+plus+"]",rs.getInt(1));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results;
		
		
	}
	/**
	 * @author EliaB
	 * get saved data by the manager
	 * */
	public ArrayList<HashMap<String,Integer>> GetData(String reportName){
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<HashMap<String,Integer>> results = new ArrayList<HashMap<String,Integer>>();
		
		
		String query="SELECT sr.activeCount,sr.freezeCount,sr.closedNum,sr.deniedNum,sr.totalWorkingCount  FROM icm.statereport as sr where sr.reportName='"+reportName+"';";
		IStatement prepS = rs -> {

			try {
				

				while (rs.next()) {
					
					results.add(new HashMap<String,Integer>());
					results.get(results.size()-1).put("Active",rs.getInt(1));
					results.get(results.size()-1).put("Locked",rs.getInt(2));
					results.get(results.size()-1).put("Closed",rs.getInt(3));
					results.get(results.size()-1).put("Canceled",rs.getInt(4));
					results.get(results.size()-1).put("totalWorkingCount",rs.getInt(5));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return results;
		
		
	}
	
	
	public HashMap<String,Integer> GetLatePhases(Timestamp from,int minus,int plus){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			HashMap<String,Integer> results = new HashMap<String,Integer>();
			
			
			String query="select   sum(timestampdiff(day, p.estimatedTimeOfCompletion,p.deadline)), count(p.phaseID) AS  sum from icm.phase as p " + 
					"where p.deadline>p.estimatedTimeOfCompletion and ((p.startingDate>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.startingDate<='"+sdf.format(from).toString()+"' + interval "+plus+" day ) or " + 
					  "(p.deadline>= '"+sdf.format(from)+"'  + interval "+minus+" day and p.deadline<='"+sdf.format(from).toString()+"' + interval "+plus+" day)or " + 
				"(p.startingDate<='"+sdf.format(from)+"' + interval "+minus+" day and p.deadline>='"+sdf.format(from).toString()+"' + interval "+plus+" day ));";
			IStatement prepS = rs -> {

				try {
					
			
					while (rs.next()) {
						
						results.put("SumOfCounts", rs.getInt(2));
						results.put("SumOfSums", rs.getInt(1));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			};
			executeStatement(query, prepS);
			return results;
			
		
	}
	/**
	 * @author EliaB
	 * save the wanted datat by the manager
	 * */
	public void SaveData(ArrayList<HashMap<String,Integer>> results,Timestamp today) {
		String query1 = "SELECT max(reportName) FROM icm.statereport ";

		ArrayList<Integer> result=new ArrayList();
		System.out.println(result.toString());
		IStatement prepS = rs -> {
			try {

				if (rs.next()) {
					result.add(rs.getInt(1));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query1, prepS);
		
		
		String query = "INSERT INTO `icm`.`statereport`(`reportName`, `dateOfCreation`, `activeCount`, `freezeCount`,`closedNum`,`deniedNum`,`totalWorkingCount`) VALUES (?, ?, ?, ?,?,?,?);";
		
		try {
			for(HashMap<String,Integer> x:results) {
			PreparedStatement ps = conn.prepareStatement(query);

				ps.setInt(1, result.get(0)+1);
				ps.setTimestamp(2, today);
				if(x.containsKey("Active"))
					ps.setInt(3, x.get("Active"));
				else
					ps.setInt(3, 0);
				if(x.containsKey("Locked"))
					ps.setInt(4, x.get("Locked"));
				else
					ps.setInt(4, 0);
				if(x.containsKey("Closed"))
					ps.setInt(5, x.get("Closed"));
				else
					ps.setInt(5, 0);
				if(x.containsKey("Canceled"))
					ps.setInt(6, x.get("Canceled"));
				else
					ps.setInt(6, 0);
				if(x.containsKey("totalWorkingCount"))
					ps.setInt(7, x.get("totalWorkingCount"));
				else
					ps.setInt(7, 0);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		
		
	}
	/**
	 * @author EliaB
	 * get array list of reports name in state report
	 * */
	public ArrayList<String> getNameOfReports(){
		ArrayList<String> reports =new ArrayList<>();

		String query="SELECT distinct reportName as reportName  FROM icm.statereport;";
		IStatement prepS = rs -> {

			try {
				

				while (rs.next()) {
					
					
				reports.add(rs.getString(1));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		};
		executeStatement(query, prepS);
		return reports;
		
	}

}
