package Entities;

import java.io.Serializable;

public class ExecutionReport extends Report implements Serializable {

	private static ExecutionReport emptyInstance = new ExecutionReport(0, 0, null, null);

	public static ExecutionReport getEmptyInstance() {
		return emptyInstance;
	}

	public ExecutionReport(long reportID, long requestID, String contentLT, String place) {
		super(reportID, requestID, contentLT, place);
	}

	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getReferenceTableName() {
		// TODO Auto-generated method stub
		return "ChangeRequest";
	}

	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "requestID";
	}

	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
