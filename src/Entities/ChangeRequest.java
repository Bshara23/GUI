package Entities;

import java.sql.Date;

public class ChangeRequest extends SqlObject {

	public long requestID;
	public String username;
	public Date dateOfRequest;
	public String fileGroup, commentsLT, requestDescriptionLT;
	public String descriptionOfRequestedChangeLT;
	public String descriptionOfCurrentStateLT;
	public String relatedInformationSystem;
	
	
	public ChangeRequest(long requestID, String username, Date dateOfRequest, String fileGroup, String commentsLT,
			String requestDescriptionLT, String descriptionOfRequestedChangeLT, String descriptionOfCurrentStateLT,
			String relatedInformationSystem) {
		this.requestID = requestID;
		this.username = username;
		this.dateOfRequest = dateOfRequest;
		this.fileGroup = fileGroup;
		this.commentsLT = commentsLT;
		this.requestDescriptionLT = requestDescriptionLT;
		this.descriptionOfRequestedChangeLT = descriptionOfRequestedChangeLT;
		this.descriptionOfCurrentStateLT = descriptionOfCurrentStateLT;
		this.relatedInformationSystem = relatedInformationSystem;
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


	public Date getDateOfRequest() {
		return dateOfRequest;
	}


	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}


	public String getFileGroup() {
		return fileGroup;
	}


	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
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
	
	
	
}
