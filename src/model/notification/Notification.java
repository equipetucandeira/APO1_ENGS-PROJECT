package model.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import model.Advisor;
import model.StatusTypes;
import model.Student;
import model.User;

public abstract class Notification {
	protected User user;
	protected String message;
	protected StatusTypes status;

	public Notification(String message, StatusTypes status, User user) {
		this.message = message;
		this.status = StatusTypes.UNDEFINED;
		this.user = user;
	}

	
	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}
	
	protected void sendNotification() {
		
	}

	protected StatusTypes getStatus() {
		return status;
	}

	protected void setStatus(StatusTypes status) {
		this.status = status;
	}

}
