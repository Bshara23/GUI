package CreationAndBasicTesting;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.Student;
import ServerLogic.MySQL;

public class Fieldssss {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {
		
		Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
		System.out.println(ts);
		ChangeRequest cr = new ChangeRequest(989898, "username2", ts, ts, ts, "comments989898", "desc989898", "desc989898", "desc989898", "moodle");
		
		db.insertObject(cr);
		
	}
}
