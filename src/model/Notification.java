package model;

public class Notification {
	
private int notificationID;
private StatusTypes type;
private String message;
private Boolean status;

public Notification(int notificationID,String message, Boolean status) {
	this.notificationID = notificationID;
	this.message = message;
	this.status = status;
	this.type = StatusTypes.UNDEFINED;
}

public String getMessage() {
	return message;
}

public int getNotificationID() {
	return notificationID;
}

public void setNotificationID(int notificationID) {
	this.notificationID = notificationID;
}

public StatusTypes getType() {
	return type;
}

public void setType(StatusTypes type) {
	this.type = type;
}

public void setMessage(String message) {
	this.message = message;
}

public Boolean getStatus() {
	return status;
}

public void setStatus(Boolean status) {
	this.status = status;
}



}
