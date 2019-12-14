package Entities;

import java.sql.Blob;

public class File extends SqlObject{

	public long ID, requestID;
	public Blob data;
	public String type;
	
	public File(long iD, long requestID, Blob data, String type) {
		ID = iD;
		this.requestID = requestID;
		this.data = data;
		this.type = type;
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
	
	
	
}
