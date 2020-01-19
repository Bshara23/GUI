package CreationAndBasicTesting;

import java.awt.Desktop;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.EvaluationReport;
import Entities.File;
import Entities.Phase;
import Entities.PhaseStatus;
import Entities.PhaseType;
import Entities.Student;
import ServerLogic.MySQL;
import ServerLogic.SQLUtil;
import Utility.DateUtil;

/**
 * This class is only used for debugging purposes
 * 
 * @author Bshara
 */
public class Fieldssss {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {

//		Employee emp = db.getEmployeeByEmpNumber(10);
//		
//		System.out.println(db.isUserManager("username12"));
//		Phase phase = new Phase(-1, -1, PhaseType.Evaluation.name(), PhaseStatus.Waiting.name(), -1, DateUtil.NA, DateUtil.NA, DateUtil.NA, DateUtil.now(), false);
//		db.insertObject(phase);

		// System.out.println(db.getEmployees().size());

		// System.out.println(db.getSupervisorEmpNum());

		LocalDate from = LocalDate.of(2020, 1, 16);
		LocalDate to = LocalDate.of(2020, 1, 20);

		Timestamp tFrom = DateUtil.get(from);
		Timestamp tTo = DateUtil.get(to);

		System.out.println(SQLUtil.toString(tFrom));
		System.out.println(SQLUtil.toString(tTo));

		System.out.println(db.countOfActiveReqests(tFrom, tTo));

		// String res = SQLUtil.toString(DateUtil.now());
		// System.out.println(res);

	}

}
