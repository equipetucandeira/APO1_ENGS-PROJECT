package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message {
	private int messageID;
	private InterfaceUser Author;
	private String content;
	private LocalDate date;
	private LocalTime time;

	public Message(InterfaceUser author, String content, LocalDate date, LocalTime time) {
		this.Author = author;
		this.content = content;
		this.date = date;
		this.time = time;
	}

	public Message() {
		this.Author = null;
		this.content = null;
		this.date = null;
		this.time = null;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public InterfaceUser getAuthor() {
		return Author;
	}

	public void setAuthor(InterfaceUser author) {
		Author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

}