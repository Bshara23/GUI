package Entities;

import java.sql.Date;

public class PhaseTimeExtensionRequest extends SqlObject {

	
	private static PhaseTimeExtensionRequest emptyInstance = new PhaseTimeExtensionRequest(0, null, null);
	public static PhaseTimeExtensionRequest getEmptyInstance() {
		return emptyInstance;
	}
	
	public long phaseID;
	public Date requestedTime;
	public String description;
	

	@Override
	public boolean isPrimaryKeyIncremental() {
		return true;
	}

	public PhaseTimeExtensionRequest(long phaseID, Date requestedTime, String description) {
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


	public Date getRequestedTime() {
		return requestedTime;
	}


	public void setRequestedTime(Date requestedTime) {
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
	
	
	
	
}
