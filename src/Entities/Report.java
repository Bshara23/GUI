package Entities;

public abstract class Report extends SqlObject {

	public long reportID;
	public long requestID;
	public String contentLT;
	
	public Report(long reportID, long requestID, String contentLT) {
		super();
		this.reportID = reportID;
		this.requestID = requestID;
		this.contentLT = contentLT;
	}

	public long getReportID() {
		return reportID;
	}

	public void setReportID(long reportID) {
		this.reportID = reportID;
	}

	public long getRequestID() {
		return requestID;
	}

	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}

	public String getContentLT() {
		return contentLT;
	}

	public void setContentLT(String contentLT) {
		this.contentLT = contentLT;
	}
	
	
	
}
