package CreationAndBasicTesting;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Entities.ChangeRequest;
import Entities.Employee;
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

		
		//System.out.println(db.getEmployees().size());
		
		Phase decisionPhase = new Phase(-1, -1, PhaseType.Evaluation.name(), PhaseStatus.Active.name(),
				db.getComHeadEmpNum(), DateUtil.daysFromNow(7), DateUtil.daysFromNow(7), DateUtil.NA,
				DateUtil.now(), false);
		
		db.insertObject(decisionPhase);
	
	
	
	}
	
	
}
