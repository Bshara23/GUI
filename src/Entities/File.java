package Entities;

import java.sql.Blob;

public class File extends SqlObject {

	private static File emptyInstance = new File(0, 0, null, null, null);
	public static File getEmptyInstance() {
		return emptyInstance;
	}
	
	public long ID, requestID;
	public Blob data;
	public String fileName, type;
	

	public File(long iD, long requestID, Blob data, String fileName, String type) {
		super();
		ID = iD;
		this.requestID = requestID;
		this.data = data;
		this.fileName = fileName;
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getRequestID() {
		return requestID;
	}

	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		return 1;
	}

	@Override
	public String getReferenceTableName() {
		return "ChangeRequest";
	}

	@Override
	public boolean hasForeignKey() {
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
