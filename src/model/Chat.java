package model;
import java.util.ArrayList;
import java.util.List;

public class Chat{
	private Integer chatID;
	private String chatName;
	private List<User> participants;
	private List<Message> messages;
	
	
	public Chat(Integer chatID, String chatName, List<User> participants) {
		this.chatID = chatID;
		this.chatName = chatName;
		this.setParticipants(participants);
		this.messages = new ArrayList<Message>();
	}
	
	public Chat(Integer chatID,String chatName) {
		this.chatID = chatID;
		this.chatName = chatName;
		this.messages = new ArrayList<Message>();
	}

	public int getChatID() {
		return chatID;
	}

	public void setChatID(Integer chatID) {
		this.chatID = chatID;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public List<Message> getMessages() {
		return messages;
	}
	

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<User> getAllParticipants() {
		return participants;
	}
	
	public User getParticipant(User user) {
		return participants.get(participants.indexOf(user));
	}
	
	public void removeParticipant(User user) {
		participants.remove(user);
	}

	public Boolean setParticipants(List<User> participants) {
		if(participants.size() > 2) {
			return false;
		}
		this.participants = participants;
		return true;
	}
	
	
	
	
}