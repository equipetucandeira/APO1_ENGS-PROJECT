package model;

public abstract class Notification {

protected String message;
protected StatusTypes status;

public Notification(String message, StatusTypes status) {
	this.message = message;
	this.status = StatusTypes.UNDEFINED;
}

public String getMessage() {
	return message;
}


public void setMessage(String message) {
	this.message = message;
}

public StatusTypes getStatus() {
	return status;
}

public void setStatus(StatusTypes status) {
	this.status = status;
}



}
