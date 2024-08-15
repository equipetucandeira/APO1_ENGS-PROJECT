package model;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import banco.DBConnection;
import banco.FeedbackBanco;

public class Feedback {
	private Integer id;
	private LocalDate date;
	private String body;

	public Feedback(LocalDate date, String body) {
		this.date = date;
		this.body = body;
	}

	public void CreateFeedback(Task task) throws SQLException {
		String body = this.getBody();
		Date date = Date.valueOf(this.getDate());
		Integer id = task.getID();
		if(!task.isSubTask()) {	
			FeedbackBanco.CreateTaskFeedback(date,body,id);
		}else {
			FeedbackBanco.CreateSubTaskFeedback(date,body,id);
		}
	}
	
	public void loadFeedback() throws Exception{
		
		
	}

	public LocalDate getDate() {
		return date;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}