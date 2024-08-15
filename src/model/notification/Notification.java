package model.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import model.Advisor;

import model.Student;
import model.UserInterfaces;

public abstract class Notification {
	protected UserInterfaces user;
	protected String message;
	protected String status;

	public Notification(String message, String status, UserInterfaces user) {
		this.message = message;
		this.status = "NAO_LIDA";
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

	protected String getStatus() {
		return status;
	}

	protected void setStatus(String status) {
		this.status = status;
	}

}
