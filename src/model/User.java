package model;
import java.util.ArrayList;
import java.util.List;

public class User {
	protected String name;
	protected String email;
	protected Integer userID;
	protected String password;
	protected List<Chat> chats;
	private List<Notification> notifications;
	
	public User(String name, String email, Integer userID, String password) {
		this.setName(name);
		this.setEmail(email);
		this.setUserID(userID);
		this.setPassword(password);
		this.chats = new ArrayList<>();
		this.notifications = new ArrayList<Notification>();
	}
	
	public void sendNotification(Notification notification) {
		notifications.add(notification);	
	}
	
	public void getNotifications() {
		for( Notification notification: this.notifications){
			System.out.println(notification.message);
		}
	}
	
	protected void Login() {
		//TODO
	}
	
	protected void Logout(){
		//TODO
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected String getEmail() {
		return this.email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	protected Integer getUserID() {
		return this.userID;
	}

	protected void setUserID(int userID) {
		this.userID = userID;
	}

	protected void setPassword(String password) {
		this.password = password;
	}
	
	protected List<Chat> getChats() {
		return chats;
	}
	
	protected void removeChat(Chat chat) {
		chat.removeParticipant(this);
		chats.remove(chat);
	}
	
	
	protected Chat setSingleChat(int id,String chatName,User destination) {
		List<User> participants = new ArrayList<User>();
		
		participants.add(this);
		participants.add(destination);
		Chat newChat = new Chat(id,chatName,participants);
		chats.add(newChat);
		return newChat;
	}

	protected void setGroupChat(int id,String chatName,List<User> UsersID) {
		List<User> participants = new ArrayList<User>();
		
		participants.add(this);
		participants.addAll(UsersID);
		
		chats.add(new GroupChat(id, chatName, participants));
		
	}
	
	
}

