package model;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import banco.DBConnection;

public class Feedback {
	private LocalDate date;
	private String body;
	private Task task;

	public Feedback(LocalDate date, String body) {
		this.date = date;
		this.body = body;
	}

	public void addToDatabase(Task task) {
		if(!task.isSubTask()) {	
			try {
				DBConnection connection = new DBConnection();
				String sql = "call addFeedback(?,?,?)";
				CallableStatement statement = connection.getConnection().prepareCall(sql);

				statement.setDate(1, Date.valueOf(this.getDate()));
				statement.setString(2, this.getBody());
				statement.setInt(3, task.getID());

				statement.executeUpdate();

				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
			try {
				DBConnection connection = new DBConnection();
				String sql = "call addSubtaskFeedback(?,?,?)";
				CallableStatement statement = connection.getConnection().prepareCall(sql);

				statement.setDate(1, Date.valueOf(this.getDate()));
				statement.setString(2, this.getBody());
				statement.setInt(3, task.getID());

				statement.executeUpdate();

				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

	public LocalDate getDate() {
		return date;
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