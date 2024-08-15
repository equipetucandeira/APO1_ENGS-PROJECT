package model;

import java.util.List;

import model.notification.Notification;

public interface UserInterfaces {

	List<Notification> getNotifications();
	public void setNotification(Notification notification);
	String getName();

	String getEmail();

	Integer getUserID();

	String getPassword();

}