package Entities;

import java.sql.Date;

public class TimeException {

	public long phaseID;
	public Date timeTaken;
	
	public TimeException(long phaseID, Date timeTaken) {
		super();
		this.phaseID = phaseID;
		this.timeTaken = timeTaken;
	}

	public long getPhaseID() {
		return phaseID;
	}

	public void setPhaseID(long phaseID) {
		this.phaseID = phaseID;
	}

	public Date getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Date timeTaken) {
		this.timeTaken = timeTaken;
	}
	
	
	
}
