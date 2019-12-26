package Entities;

import java.sql.Date;

public class Message extends SqlObject {

	
	private static Message emptyInstance = new Message(0, null, null, null, null, 0, null, 0, 0, 0);
	public static Message getEmptyInstance() {
		return emptyInstance;
	}
	
	public long messageID;
	public String subject, from, to, messageContentLT;
	public int hasBeenViewed;
	public Date sentAt;
	public int isStarred, isRead, isArchived;
	

	public Message(long messageID, String subject, String from, String to, String messageContentLT,
			int hasBeenViewed, Date sentAt, int isStarred, int isRead, int isArchived) {
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

	int isStarred() {
		return isStarred;
	}

	void setStarred(int isStarred) {
		this.isStarred = isStarred;
	}

	int isRead() {
		return isRead;
	}

	void setRead(int isRead) {
		this.isRead = isRead;
	}

	int isArchived() {
		return isArchived;
	}

	void setArchived(int isArchived) {
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

	public int isHasBeenViewed() {
		return hasBeenViewed;
	}

	public void setHasBeenViewed(int hasBeenViewed) {
		this.hasBeenViewed = hasBeenViewed;
	}

	@Override
	public int getPrimaryKeyIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getForeignKeyIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String getReferenceTableName() {
		// TODO Auto-generated method stub
		return "SystemUser";
	}

	@Override
	public boolean hasForeignKey() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getReferenceTableForeignKeyName() {
		// TODO Auto-generated method stub
		return "userName";
	}

	@Override
	public int fieldsLastIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
