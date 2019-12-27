package Entities;

import java.sql.Date;

public class Message extends SqlObject {

	
	private static Message emptyInstance = new Message(0, null, null, null, null, false, null, false, false, false);
	public static Message getEmptyInstance() {
		return emptyInstance;
	}
	
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

	@Override
	public boolean isPrimaryKeyIncremental() {
		return true;
	}

	public boolean isStarred() {
		return isStarred;
	}



	public void setStarred(boolean isStarred) {
		this.isStarred = isStarred;
	}



	public boolean isRead() {
		return isRead;
	}



	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}



	public boolean isArchived() {
		return isArchived;
	}



	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}



	public void setHasBeenViewed(boolean hasBeenViewed) {
		this.hasBeenViewed = hasBeenViewed;
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
