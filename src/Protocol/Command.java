package Protocol;

public enum Command {

	LogIn, LogOut, Update, Insert, Delete, Search, DoesExist, GetAllData, countOfObjects, updateProfile,

	deleteObjects,

	insertRequest, insertRequestWithFiles, insertPhase,

	insertEvaluationReport, getEvaluationReport, insertExecutionReport, getExecutionReport,

	GetMyRequests,

	updateRequest, updatePhase, insertFile, getFile,

	insertComitteeMember, insertSupervisor, updateCommitteeMember, updateSupervisor,

	getUsersList_InformationEngineer, getUsersList_OtherEmployee, getUsersList_Lecturers, getUsersList_Students,
	getUsersList_CommitteeMemeber,

	getMessagesPrimary, getMessagesUpdate, getMessagesStaff, getMessagesWork,

	insertMessage, updateMessage,

	getCount_RequestsClosed, getCount_RequestsLocked, getCount_RequestsActive, getCount_RequestsCanceled,

	insertPeriodicalReport,

	getExecutedTimeExtensions, // ask malki
	getExtraTimeRelateToEvaluatedTime,

	debug_simulateBigCalculations, getCountOfPhasesTypes, getPhasesOfRequestWithTimeExtensionsIfPossible,
	getSystemUserByRequest, getEmployeeByEmployeeNumber, getFirstLastName, getFullNameByUsername, getPermissionsData,
	GetMyIssuedRequestsCount, GetMyIssuedRequests,GetCounterOfRequestsByStatus, GetCounterOfPhasesByStatus, GetCounterOfPhasesByStatusDateRange, GetSumOfTwoDiffernceDateBetweenTwoDates, SaveTheData, GetTheData, getNameOfReports

}
