package CreationAndBasicTesting;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.Student;
import Protocol.PhaseType;
import ServerLogic.MySQL;

public class Fieldssss {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {

		System.out.println(db.getCountOfPhasesByType(10, PhaseType.supervision));

	}
}
