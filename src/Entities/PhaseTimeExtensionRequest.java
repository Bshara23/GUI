package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class PhaseTimeExtensionRequest extends SqlObject implements Serializable {

	private static PhaseTimeExtensionRequest emptyInstance = new PhaseTimeExtensionRequest(0, null, null);

	public static PhaseTimeExtensionRequest getEmptyInstance() {
		return emptyInstance;
	}

	public long phaseID;
	public Timestamp requestedTime;
	public String description;

	

	public PhaseTimeExtensionRequest(long phaseID, Timestamp requestedTime, String description) {
		super();
		this.phaseID = phaseID;
		this.requestedTime = requestedTime;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPhaseID() {
		return phaseID;
	}

	public void setPhaseID(long phaseID) {
		this.phaseID = phaseID;
	}

	public Timestamp getRequestedTime() {
		return requestedTime;
	}

	public void setRequestedTime(Timestamp requestedTime) {
		this.requestedTime = requestedTime;
	}

	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReferenceTableName() {
		// TODO Auto-generated method stub
		return "Phase";
	}

	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "phaseID";
	}

	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "PhaseTimeExtensionRequest [phaseID=" + phaseID + ", requestedTime=" + requestedTime + ", description="
				+ description + "]";
	}
	
	
	

}
