package model.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import banco.NotificationBanco;
import model.Advisor;

import model.Student;
import model.User;
import model.InterfaceUser;

public class Notification {
	protected InterfaceUser user;
	protected String message;
	protected String status;

	public Notification(String message, InterfaceUser user) {
		this.message = message;
		this.status = "NAO_LIDA";
		this.user = user;
	}

	
	public Notification(String message, String status) {
		this.message = message;
		this.status = status;
	}


	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}
	
	public void sendNotification() throws SQLException {
		
	}

	protected String getStatus() {
		return status;
	}

	protected void setStatus(String status) {
		this.status = status;
	}
	
	public void loadNotification() throws SQLException {
	
	}


	public InterfaceUser getUser() {
		return this.user;
	}


}
