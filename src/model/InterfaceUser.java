package model;

import java.util.List;

import model.notification.Notification;

public interface InterfaceUser {

	List<Notification> getNotifications();
	public void setNotification(Notification notification);
	String getName();

	String getEmail();

	Integer getUserID();

	String getPassword();

}