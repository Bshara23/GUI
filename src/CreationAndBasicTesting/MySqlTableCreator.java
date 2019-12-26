package CreationAndBasicTesting;

import java.lang.reflect.Method;
import java.sql.Blob;

import ClientLogic.Client;
import Entities.*;

import ServerLogic.MySQL;

public class MySqlTableCreator {

	public static void main(String[] args) {

		MySQL db = new MySQL("root", "Aa123456", "ICM", null);
		
		// Users
		//db.createTable(new SystemUser("", "", "", "", "", ""));
		//db.createTable(new Employee(null, null, null, null, null, null, 0, null, null));
		
		System.out.println(new Employee(null, null, null, null, null, null, 0, null, null).tableInfo());
		
//		db.createTable(new Student(null, null, null, null, null, null, 0, null));
//		db.createTable(new ExecutionChangesCommitteeMember(null, null, null, null, null, null, 0, null, null, false));
//		
//		// Request
//		db.createTable(new ChangeRequest(0, null, null, null, null, null, null, null, null));
//		db.createTable(new Phase(0, null, null, 0, null, null, null, false));
//		db.createTable(new PhaseTimeExtensionRequest(0, null));
//		db.createTable(new File(0, 0, null, null, null));
//		
//		// Message
//		db.createTable(new Message(0, null, null, null, null, false, null, false, false, false));
//		
//		// Reports
//		db.createTable(new EvaluationReport(0, 0, null, null, null, null, null, null));
//		db.createTable(new ExecutionReport(0, 0, null, null));
		
	}

}
