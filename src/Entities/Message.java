package Entities;

import java.sql.Date;

public class Message extends SqlObject {

	public long messageID;
	public String subject, from, to, messageContentLT;
	public boolean hasBeenViewed;
	public Date sentAt;
	public boolean isStarred, isRead, isArchived;
	

	public Message(long messageID, String subject, String from, String to, String messageContentLT,
			boolean hasBeenViewed, Date sentAt, boolean isStarred, boolean isRead, boolean isArchived) {
		super();
		this.messageID = messageID;
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.messageContentLT = messageContentLT;
		this.hasBeenViewed = hasBeenViewed;
		this.sentAt = sentAt;
		this.isStarred = isStarred;
		this.isRead = isRead;
		this.isArchived = isArchived;
	}

	boolean isStarred() {
		return isStarred;
	}

	void setStarred(boolean isStarred) {
		this.isStarred = isStarred;
	}

	boolean isRead() {
		return isRead;
	}

	void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	boolean isArchived() {
		return isArchived;
	}

	void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessageContentLT() {
		return messageContentLT;
	}

	public void setMessageContentLT(String messageContentLT) {
		this.messageContentLT = messageContentLT;
	}

	public boolean isHasBeenViewed() {
		return hasBeenViewed;
	}

	public void setHasBeenViewed(boolean hasBeenViewed) {
		this.hasBeenViewed = hasBeenViewed;
	}
	
	
	
}
