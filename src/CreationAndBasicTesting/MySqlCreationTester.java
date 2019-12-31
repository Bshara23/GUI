package CreationAndBasicTesting;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import ClientLogic.Client;
import Entities.*;
import ServerLogic.MySQL;
import Utility.AppManager;

public class MySqlCreationTester {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {

		// db.insertObject(SystemUser.emptyInstance);
		// printTableInfo();
		// insertObjects();
		// db.createTable(File.getEmptyInstance());

		// printFields(File.class);

		// File file = new File(4, 4, "dd.jpg", "jpg");
		// file.loadBytesFromLocal();

		// db.insertFile(file);

		//insertObjects();
		// changeRequestTest();

		//populateChangeRequest();
		//createDatabase();

		// insertObjects();
		int count = db.getCountOf(ChangeRequest.getEmptyInstance(), "`username`='username2'");
		System.out.println(count);
//		boolean res = db.doesObjectExist(new ChangeRequest(9996, null, null, null, null, null, null, null, null, null));
//		System.out.println(res);
		
		
		//System.out.println(db.getNewMaxID(ChangeRequest.getEmptyInstance()));
	}

	private static void changeRequestTest() {
		ArrayList<ChangeRequest> result = db.getChangeRequests();

		for (ChangeRequest changeRequest : result) {
			// System.out.println(changeRequest);

//			ArrayList<Phase> phases = db.getPhases(changeRequest.getRequestID());
//			for (Phase phase : phases) {
//				System.out.println(phase);
//			}

//			ArrayList<ExecutionReport> exeReps = db.getExecutionReports(changeRequest.getRequestID());
//			for (ExecutionReport exeRep : exeReps) {
//				System.out.println(exeRep);
//			}

//			ArrayList<EvaluationReport> evalReps = db.getEvaluationReports(changeRequest.getRequestID());
//			for (EvaluationReport evalRep : evalReps) {
//				System.out.println(evalRep);
//			}

			ArrayList<File> files = db.getFiles(changeRequest.getRequestID());
			for (File file : files) {
				System.out.println(file);
			}
		}
	}

	private static void createDatabase() {

		db.createTable(SystemUser.getEmptyInstance());
		db.createTable(Student.getEmptyInstance());
		db.createTable(Employee.getEmptyInstance());
		db.createTable(ExecutionChangesCommitteeMember.getEmptyInstance());

		db.createTable(ChangeRequest.getEmptyInstance());
		db.createTable(File.getEmptyInstance());

		db.createTable(ExecutionReport.getEmptyInstance());
		db.createTable(EvaluationReport.getEmptyInstance());

		db.createTable(Phase.getEmptyInstance());
		db.createTable(PhaseTimeExtensionRequest.getEmptyInstance());

		db.createTable(Message.getEmptyInstance());

		db.createTable(SupervisorUpdateCR.getEmptyInstance());

	}

	private static void printTableInfo() {

		System.out.println(SystemUser.getEmptyInstance().tableInfo());
		System.out.println(Student.getEmptyInstance().tableInfo());
		System.out.println(Employee.getEmptyInstance().tableInfo());
		System.out.println(ExecutionChangesCommitteeMember.getEmptyInstance().tableInfo());

		System.out.println(ChangeRequest.getEmptyInstance().tableInfo());
		System.out.println(File.getEmptyInstance().tableInfo());

		System.out.println(ExecutionReport.getEmptyInstance().tableInfo());
		System.out.println(EvaluationReport.getEmptyInstance().tableInfo());

		System.out.println(Phase.getEmptyInstance().tableInfo());
		System.out.println(PhaseTimeExtensionRequest.getEmptyInstance().tableInfo());

		System.out.println(Message.getEmptyInstance().tableInfo());

		System.out.println(SupervisorUpdateCR.getEmptyInstance().tableInfo());

	}

	private static void populateChangeRequest() {

		Random rnd = new Random(); 
		for (int i = 1; i < 500; i++) {

			LocalDate d1 = LocalDate.of(2020, rnd.nextInt(11) + 1, rnd.nextInt(27) + 1);
			LocalDate d2 = LocalDate.of(2020, rnd.nextInt(11) + 1, rnd.nextInt(27) + 1);
			LocalDate d3 = LocalDate.of(2020, rnd.nextInt(11) + 1, rnd.nextInt(27) + 1);

			ChangeRequest cr = new ChangeRequest(i + 1000, "username" + 3, d1, d2, d3, "comments" + i,
					"request description" + i, "descriptionOfRequestedChange" + i, "descriptionOfCurrentState",
					"Software");

			db.insertObject(cr);
		}
	}

	private static void insertObjects() {

		LocalDate d1 = LocalDate.of(2020, AppManager.getRnd().nextInt(12) + 1, AppManager.getRnd().nextInt(28) + 1);
		LocalDate d2 = LocalDate.of(2020, AppManager.getRnd().nextInt(12) + 1, AppManager.getRnd().nextInt(28) + 1);
		LocalDate d3 = LocalDate.of(2020, AppManager.getRnd().nextInt(12) + 1, AppManager.getRnd().nextInt(28) + 1);
		for (int i = 0; i < 10; i++) {
			ChangeRequest cr = new ChangeRequest(i, "username" + 2, d1, d2, d3, "comments" + i,
					"request description" + i, "descriptionOfRequestedChange" + i, "descriptionOfCurrentState",
					"Software");

			db.insertObject(cr);
		}

//		for (int i = 0; i < 10; i++) {
//		ChangeRequest cr = new ChangeRequest(i, "username" + 2, new Date(2020, 3, 5), new Date(100, 3, 5), new Date(155, 3, 5), "comments" + i,
//				"request description" + i, "descriptionOfRequestedChange" + i, "descriptionOfCurrentState",
//				"Software");
//		
//		db.insertObject(cr);
//	}
//
//		for (int i = 10; i < 20; i++) {
//			ChangeRequest cr = new ChangeRequest(i, "username" + 3, new Date(120, 3, 5), new Date(120, 3, 6),
//					new Date(120, 3, 9), "comments" + i, "request description" + i, "descriptionOfRequestedChange" + i,
//					"descriptionOfCurrentState", "Software");
//
//			db.insertObject(cr);
//		}

		// TODO: the phase has 2 FK, need to add the empNumber as a FK too!
//		for (int i = 0; i < 10; i++) {
//
//			Phase ph = new Phase(i * 100 + 1, 3, "Evaluation", "Active", 10, new Date(120, 3, 5), new Date(120, 3, 5),
//					new Date(120, 3, 5), 0);
//			Phase ph1 = new Phase(i * 100 + 2, 3, "Examination", "Locked", 11, new Date(120, 3, 5), new Date(120, 3, 5),
//					new Date(120, 3, 5), 0);
//			Phase ph2 = new Phase(i * 100 + 3, 3, "Closing", "Active", 12, new Date(120, 3, 5), new Date(120, 3, 5),
//					new Date(120, 3, 5), 0);
//			Phase ph3 = new Phase(i * 100 + 4, 3, "Decision", "Active", 20, new Date(120, 3, 5), new Date(120, 3, 5),
//					new Date(120, 3, 5), 0);
//			Phase ph4 = new Phase(i * 100 + 5, 3, "Execution", "Active", 13, new Date(120, 3, 5), new Date(120, 3, 5),
//					new Date(120, 3, 5), 0);
//
//			db.insertObject(ph);
//			db.insertObject(ph1);
//			db.insertObject(ph2);
//			db.insertObject(ph3);
//			db.insertObject(ph4);
//
//		}

//		
//		for (int i = 0; i < 500; i++) {
//
//			Message msg = new Message(i, "subject" + i, "username" + (i + 5) % 10 , "username" + i % 20, "messageContent" + i, i % 2, new Date(120, 3, 5), (i + 1) % 2, i % 2, i % 2);
//			db.insertObject(msg);
//
//		}

//		
//		for (int i = 1; i < 10; i++) {
//
//			PhaseTimeExtensionRequest pte = new PhaseTimeExtensionRequest(i * 100 + 2, new Date(120, 3, 5), "description" + i);
//			db.insertObject(pte);
//
//		}

		// TODO: link a FK to the request it self or this should not appear near the
		// request?
//		for (int i = 1; i < 10; i++) {
//
//			SupervisorUpdateCR svucr = new SupervisorUpdateCR(i, 15, "eeee", new Date(120, 3, 5));
//			db.insertObject(svucr);
//
//		}

//		for (int i = 1; i < 10; i++) {
//
//			ExecutionReport exeReport = new ExecutionReport(i, i, "content" + i, "place" + i);
//			db.insertObject(exeReport);
//
//		}
//		
//		
//		for (int i = 1; i < 10; i++) {
//
//			// TODO: time instead of date?
//			EvaluationReport evalReport = new EvaluationReport(i, i, "content" + i, "place" + i, "result" + i, "constrainets" + i, "risks" + i, new Date(120, 3, 5));
//			db.insertObject(evalReport);
//
//		}	

	}

	private static void insertUsers() {
		for (int i = 0; i < 23; i++) {
			SystemUser su = new SystemUser("username" + i, i + "001", i + "@gmail.com", "FN" + i, "LN" + i,
					"052-258" + i);
			db.insertObject(su);
		}

		for (int i = 10; i < 22; i++) {
			Student stu = new Student("username" + i, i + "001", i + "@gmail.com", "FN" + i, "LN" + i, "052-258" + i, i,
					"Software");
			db.insertObject(stu);
		}

		for (int i = 10; i < 23; i++) {
			Employee emp = new Employee("username" + i, i + "001", i + "@gmail.com", "FN" + i, "LN" + i, "052-258" + i,
					i, "Software", "Role" + i);
			db.insertObject(emp);
		}

		ExecutionChangesCommitteeMember ecc1 = new ExecutionChangesCommitteeMember("", "", "", "", "", "", 20, "", "",
				false);
		ExecutionChangesCommitteeMember ecc2 = new ExecutionChangesCommitteeMember("", "", "", "", "", "", 21, "", "",
				false);
		ExecutionChangesCommitteeMember eccManager = new ExecutionChangesCommitteeMember("", "", "", "", "", "", 22, "",
				"", true);

		db.insertObject(ecc1);
		db.insertObject(ecc2);
		db.insertObject(eccManager);
	}

	private static void printFields(Class<?> cls) {

		for (Field f : cls.getFields()) {
			System.out.println(f);
		}

	}

}
