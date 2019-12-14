package Entities;

import java.sql.Date;

public class PhaseTimeExtensionRequest {

	
	public long phaseID;
	public Date requestedTime;
	
	
	public PhaseTimeExtensionRequest(long phaseID, Date requestedTime) {
		super();
		this.phaseID = phaseID;
		this.requestedTime = requestedTime;
	}


	public long getPhaseID() {
		return phaseID;
	}


	public void setPhaseID(long phaseID) {
		this.phaseID = phaseID;
	}


	public Date getRequestedTime() {
		return requestedTime;
	}


	public void setRequestedTime(Date requestedTime) {
		this.requestedTime = requestedTime;
	}
	
	
	
	
}
