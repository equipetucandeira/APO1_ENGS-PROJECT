package model;

import java.util.ArrayList;
import java.util.List;

import model.notification.Notification;

public class User implements InterfaceUser {
	public String name;
	public String email;
	public Integer userID;
	public String password;
	public List<Chat> chats;
	private List<Notification> notifications;

	public User(String name, String email, Integer userID, String password) {
		this.setName(name);
		this.setEmail(email);
		this.setUserID(userID);
		this.setPassword(password);
		this.chats = new ArrayList<>();
		this.notifications = new ArrayList<Notification>();
	}

	public User() {
		this.notifications = new ArrayList<Notification>();
	}

	public void setNotification(Notification notification) {
		notifications.add(notification);
	}
	

	@Override
	public List<Notification> getNotifications() {
		return notifications;
	}
	

	public User login() throws Exception {
		return this;
	}

	public void Logout() {
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Integer getUserID() {
		return this.userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String getPassword() {
		return this.password;
	}

}
