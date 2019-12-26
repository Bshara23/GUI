package Entities;

import java.sql.Date;
import java.util.ArrayList;

public class Phase extends SqlObject {

	public long phaseID;
	public String phaseName, status;
	public long empNumber;

	public Date deadline, estimatedTimeOfCompletion, timeOfCompletion;
	public boolean hasBeenTimeExtended;

	private PhaseTimeExtensionRequest phaseTimeExtensionRequest;
	
	private ArrayList<File> files;
	private ArrayList<Phase> phases;
	private EvaluationReport evaluationReport;
	


	public Phase(long phaseID, String phaseName, String status, long empNumber, Date deadline,
			Date estimatedTimeOfCompletion, Date timeOfCompletion, boolean hasBeenTimeExtended) {
		super();
		this.phaseID = phaseID;
		this.phaseName = phaseName;
		this.status = status;
		this.empNumber = empNumber;
		this.deadline = deadline;
		this.estimatedTimeOfCompletion = estimatedTimeOfCompletion;
		this.timeOfCompletion = timeOfCompletion;
		this.hasBeenTimeExtended = hasBeenTimeExtended;
		
		files = new ArrayList<File>();
		phases = new ArrayList<Phase>(5);
	}

	EvaluationReport getEvaluationReport() {
		return evaluationReport;
	}

	void setEvaluationReport(EvaluationReport evaluationReport) {
		this.evaluationReport = evaluationReport;
	}

	ArrayList<File> getFiles() {
		return files;
	}

	void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	ArrayList<Phase> getPhases() {
		return phases;
	}

	void setPhases(ArrayList<Phase> phases) {
		this.phases = phases;
	}
	

	public PhaseTimeExtensionRequest getPhaseTimeExtensionRequest() {
		return phaseTimeExtensionRequest;
	}

	public void setPhaseTimeExtensionRequest(PhaseTimeExtensionRequest phaseTimeExtensionRequest) {
		this.phaseTimeExtensionRequest = phaseTimeExtensionRequest;
	}

	public long getPhaseID() {
		return phaseID;
	}

	public void setPhaseID(long phaseID) {
		this.phaseID = phaseID;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(long empNumber) {
		this.empNumber = empNumber;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getEstimatedTimeOfCompletion() {
		return estimatedTimeOfCompletion;
	}

	public void setEstimatedTimeOfCompletion(Date estimatedTimeOfCompletion) {
		this.estimatedTimeOfCompletion = estimatedTimeOfCompletion;
	}

	public Date getTimeOfCompletion() {
		return timeOfCompletion;
	}

	public void setTimeOfCompletion(Date timeOfCompletion) {
		this.timeOfCompletion = timeOfCompletion;
	}

	public boolean isHasBeenTimeExtended() {
		return hasBeenTimeExtended;
	}

	public void setHasBeenTimeExtended(boolean hasBeenTimeExtended) {
		this.hasBeenTimeExtended = hasBeenTimeExtended;
	}
	
	

}
