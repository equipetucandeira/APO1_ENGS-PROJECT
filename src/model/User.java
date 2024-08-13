package model;

import java.util.ArrayList;
import java.util.List;

import model.notification.Notification;

public class User {
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

	public void sendNotification(Notification notification) {
		notifications.add(notification);
	}
	

	public List<Notification> getNotifications() {
		return notifications;
	}
	

	public User login() throws Exception {
		return this;

	}

	public void Logout() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserID() {
		return this.userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Chat> getChats() {
		return chats;
	}

	public void removeChat(Chat chat) {
		chat.removeParticipant(this);
		chats.remove(chat);
	}

	public Chat setSingleChat(int id, String chatName, User destination) {
		List<User> participants = new ArrayList<User>();

		participants.add(this);
		participants.add(destination);
		Chat newChat = new Chat(id, chatName, participants);
		chats.add(newChat);
		return newChat;
	}

	public void setGroupChat(int id, String chatName, List<User> UsersID) {
		List<User> participants = new ArrayList<User>();

		participants.add(this);
		participants.addAll(UsersID);

		chats.add(new GroupChat(id, chatName, participants));

	}

}
