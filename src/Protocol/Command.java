package Protocol;

public enum Command {

	LogIn, LogOut, Update, Insert, Delete, Search, DoesExist, GetAllData, countOfObjects, updateProfile,

	deleteObjects,

	insertRequest, insertRequestWithFiles, insertPhase,

	insertEvaluationReport, getEvaluationReport, insertExecutionReport, getExecutionReport,

	GetMyRequests,

	updateRequest, updatePhaseEstimatedTime, insertFile, getFile,

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
	GetMyIssuedRequestsCount, GetMyIssuedRequests, SendExaminerReportOfFailures, receivedNewMessage,
	checkIfPhaseIsWaiting, getChangeRequestFromMessagePage, acceptPhaseTimeExtensionSupervisor,
	rejectPhaseTimeExtensionSupervisor, receivedNewOrUpdateRequests, getEmployeesListSimple, updatePhaseOwner,
	setEvaluationPhaseToWaitingToSetTime, insertTimeExtension, confirmPhaseDeadline, rejectPhaseDeadline,
	declineDecisionPhase, acceptDecisionPhase, requestMoreDateForDecision, getLatestEvalReport, confirmExamination,
	rejectExamination, getExecutionReportForExaminationAndComsNames, assignExaminerForRequest, requestOwnerConfirm,
	requestOwnerDecline, getRequestFiles, isMyRequestWaitingForMyConfirmation, confirmRequestEndedBySupervisor,
	declinedRequestEndedBySupervisor, freezePhase, unfreezePhase, checkLogIn, getDepartmentsManagers,

}
