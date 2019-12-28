package Protocol;

public enum Command {

	LogIn, LogOut,
	Update, Insert, Delete, Search,
	DoesExist,
	GetAllData,
	
	updateProfile,
	
	insertRequest, insertPhase,
	
	insertEvaluationReport, getEvaluationReport, 
	insertExecutionReport, getExecutionReport, 
	
	GetMyRequests, GetMyRequestsSupervisor, GetMyRequestsEvaluator, 
	GetMyRequestsExaminer, GetMyRequestsExecuter, GetMyRequestsDecide,
	
	updateRequest, updatePhase, 
	insertFile, getFile,
	
	insertComitteeMember, insertSupervisor,
	updateCommitteeMember, updateSupervisor, 
	
	
	getUsersList_InformationEngineer,
	getUsersList_OtherEmployee,
	getUsersList_Lecturers,
	getUsersList_Students,
	getUsersList_CommitteeMemeber,
	
	getMessagesPrimary,
	getMessagesUpdate,
	getMessagesStaff,
	getMessagesWork,

	insertMessage, updateMessage,


	getCount_RequestsClosed,
	getCount_RequestsLocked,
	getCount_RequestsActive,
	getCount_RequestsCanceled,
	
	insertPeriodicalReport,
	
	getExecutedTimeExtensions, // ask malki
	getExtraTimeRelateToEvaluatedTime,
	
	
	debug_simulateBigCalculations,
	
	
	
}
