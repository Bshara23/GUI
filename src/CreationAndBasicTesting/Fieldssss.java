package CreationAndBasicTesting;

import java.awt.Desktop;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.ChangeRequest;
import Entities.Employee;
import Entities.EvaluationReport;
import Entities.File;
import Entities.Phase;
import Entities.Student;
import Protocol.PhaseStatus;
import Protocol.PhaseType;
import ServerLogic.MySQL;
import Utility.DateUtil;

public class Fieldssss {
	private static MySQL db = new MySQL("root", "Aa123456", "ICM", null);

	public static void main(String[] args) {

//		Employee emp = db.getEmployeeByEmpNumber(10);
//		
//		System.out.println(db.isUserManager("username12"));
//		Phase phase = new Phase(-1, -1, PhaseType.Evaluation.name(), PhaseStatus.Waiting.name(), -1, DateUtil.NA, DateUtil.NA, DateUtil.NA, DateUtil.now(), false);
//		db.insertObject(phase);

		// System.out.println(db.getEmployees().size());

		//System.out.println(db.getSupervisorEmpNum());
		
		
		
		System.out.println(db.getMaintainanceManagers());

	}
	

}
