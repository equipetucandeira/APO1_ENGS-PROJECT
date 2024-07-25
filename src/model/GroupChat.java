package model;
import java.util.List;

public class GroupChat extends Chat {
	private Integer chatID;
	private String chatName;
	private List<User> participants;
	private List<Message> messages;

	public GroupChat(Integer chatID, String chatName, List<User> participants) {
		super(chatID, chatName);
		this.setParticipants(participants);
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


	public List<User> getParticipants() {
		return participants;
	}

	public Boolean setParticipants(List<User> participantsID) {
		this.participants = participantsID;
		return true;
	}
}
