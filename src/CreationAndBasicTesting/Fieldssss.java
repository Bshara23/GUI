package CreationAndBasicTesting;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.Phase;
import Entities.Student;
import Protocol.PhaseType;
import ServerLogic.MySQL;

public class Fieldssss {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {



		Employee emp = db.getEmployeeByEmpNumber(10);
		
		System.out.println(db.getFullNameByUsername("username1"));
		

	}
	
	
}
