package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChangeRequest extends SqlObject implements Serializable {

	
	private static ChangeRequest emptyInstance = new ChangeRequest(0, null, null, null, null, null, null, null, null, null);
	
	
	public static ChangeRequest getEmptyInstance() {
		return emptyInstance;
	}

	


	public long requestID;
	public String username;
	public LocalDate startDateOfRequest, estimatedTimeForExecution, endDateOfRequest;
	public String commentsLT, requestDescriptionLT;
	public String descriptionOfRequestedChangeLT;
	public String descriptionOfCurrentStateLT;
	public String relatedInformationSystem;
	
	private ArrayList<EvaluationReport> evaluationReports;
	private ArrayList<ExecutionReport> executionReports;
	private ArrayList<File> files;
	private ArrayList<Phase> phases;
	
	
	


	public ChangeRequest() {
		this(0, null, null, null, null, null, null, null, null, null);
	}
	

	public ChangeRequest(long requestID, String username, LocalDate startDateOfRequest, LocalDate estimatedTimeForExecution,
			LocalDate endDateOfRequest, String commentsLT, String requestDescriptionLT,
			String descriptionOfRequestedChangeLT, String descriptionOfCurrentStateLT,
			String relatedInformationSystem) {
		super();
		this.requestID = requestID;
		this.username = username;
		this.startDateOfRequest = startDateOfRequest;
		this.estimatedTimeForExecution = estimatedTimeForExecution;
		this.endDateOfRequest = endDateOfRequest;
		this.commentsLT = commentsLT;
		this.requestDescriptionLT = requestDescriptionLT;
		this.descriptionOfRequestedChangeLT = descriptionOfRequestedChangeLT;
		this.descriptionOfCurrentStateLT = descriptionOfCurrentStateLT;
		this.relatedInformationSystem = relatedInformationSystem;
		

		this.evaluationReports = new ArrayList<EvaluationReport>();
		this.executionReports = new ArrayList<ExecutionReport>();
		this.files = new ArrayList<File>();
		this.phases = new ArrayList<Phase>();
	}



	public LocalDate getStartDateOfRequest() {
		return startDateOfRequest;
	}



	public void setStartDateOfRequest(LocalDate startDateOfRequest) {
		this.startDateOfRequest = startDateOfRequest;
	}



	public LocalDate getEstimatedTimeForExecution() {
		return estimatedTimeForExecution;
	}



	public void setEstimatedTimeForExecution(LocalDate estimatedTimeForExecution) {
		this.estimatedTimeForExecution = estimatedTimeForExecution;
	}



	public LocalDate getEndDateOfRequest() {
		return endDateOfRequest;
	}



	public void setEndDateOfRequest(LocalDate endDateOfRequest) {
		this.endDateOfRequest = endDateOfRequest;
	}



	public ArrayList<EvaluationReport> getEvaluationReports() {
		return evaluationReports;
	}



	public void setEvaluationReports(ArrayList<EvaluationReport> evaluationReports) {
		this.evaluationReports = evaluationReports;
	}



	public ArrayList<ExecutionReport> getExecutionReports() {
		return executionReports;
	}



	public void setExecutionReports(ArrayList<ExecutionReport> executionReports) {
		this.executionReports = executionReports;
	}



	public ArrayList<File> getFiles() {
		return files;
	}



	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}



	public ArrayList<Phase> getPhases() {
		return phases;
	}



	public void setPhases(ArrayList<Phase> phases) {
		this.phases = phases;
	}



	public long getRequestID() {
		return requestID;
	}


	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public LocalDate getDateOfRequest() {
		return startDateOfRequest;
	}


	public void setDateOfRequest(LocalDate dateOfRequest) {
		this.startDateOfRequest = dateOfRequest;
	}


	public String getCommentsLT() {
		return commentsLT;
	}


	public void setCommentsLT(String commentsLT) {
		this.commentsLT = commentsLT;
	}


	public String getRequestDescriptionLT() {
		return requestDescriptionLT;
	}


	public void setRequestDescriptionLT(String requestDescriptionLT) {
		this.requestDescriptionLT = requestDescriptionLT;
	}


	public String getDescriptionOfRequestedChangeLT() {
		return descriptionOfRequestedChangeLT;
	}


	public void setDescriptionOfRequestedChangeLT(String descriptionOfRequestedChangeLT) {
		this.descriptionOfRequestedChangeLT = descriptionOfRequestedChangeLT;
	}


	public String getDescriptionOfCurrentStateLT() {
		return descriptionOfCurrentStateLT;
	}


	public void setDescriptionOfCurrentStateLT(String descriptionOfCurrentStateLT) {
		this.descriptionOfCurrentStateLT = descriptionOfCurrentStateLT;
	}


	public String getRelatedInformationSystem() {
		return relatedInformationSystem;
	}


	public void setRelatedInformationSystem(String relatedInformationSystem) {
		this.relatedInformationSystem = relatedInformationSystem;
	}



	@Override
	public int getPrimaryKeyIndex() {
		return 0;
	}



	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return -1;
	}




	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getReferenceTableName() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 10;
	}




	@Override
	public String toString() {
		return "ChangeRequest [requestID=" + requestID + ", username=" + username + ", startDateOfRequest="
				+ startDateOfRequest + ", estimatedTimeForExecution=" + estimatedTimeForExecution
				+ ", endDateOfRequest=" + endDateOfRequest + ", commentsLT=" + commentsLT + ", requestDescriptionLT="
				+ requestDescriptionLT + ", descriptionOfRequestedChangeLT=" + descriptionOfRequestedChangeLT
				+ ", descriptionOfCurrentStateLT=" + descriptionOfCurrentStateLT + ", relatedInformationSystem="
				+ relatedInformationSystem + "]";
	}
	
	
	
	
}
