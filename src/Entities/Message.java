package Entities;

public class Message extends SqlObject {

	public long messageID;
	public String from, to, messageContentLT;
	public boolean hasBeenViewed;
	
	public Message(long messageID, String from, String to, String messageContentLT, boolean hasBeenViewed) {
		this.messageID = messageID;
		this.from = from;
		this.to = to;
		this.messageContentLT = messageContentLT;
		this.hasBeenViewed = hasBeenViewed;
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
