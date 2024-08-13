package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import model.notification.AdvisorNotification;
import model.notification.Notification;
import model.notification.StudentNotification;

public class Student extends User {
	private ProjectTCC project;
	
	public Student(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
	}

	public Student() {
	}
	
	
	
	private void setProject(Integer projectID, String title, Integer advisorID, String status, float grade) {
		try {
		Advisor advisor = new Advisor();
		advisor = advisor.getAdvisorById(advisorID);
		StatusTypes type =StatusTypes.UNDEFINED;
		switch (status) {
		case "INICIADO":
			type = StatusTypes.INITIATED;
			break;
		case "EM_PROGRESSO":
			type = StatusTypes.PROGRESS;
			break;
		case "FINALIZAD0":
			type = StatusTypes.COMPLETED;
			break;
		}
		ProjectTCC newProject = new ProjectTCC(projectID,title,advisor,this,type,grade);
		this.project = newProject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User login() throws Exception {

		DBConnection connection = new DBConnection();

		String sql = "select * from user_guiding where email =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			String searchEmail = rs.getString("email");
			if (searchEmail != null) {
				String searchPassword = rs.getString("user_password");
				if (searchPassword.equals(password)) {
					this.setUserID(Integer.valueOf(rs.getInt("guiding_id")));
					this.setName(rs.getString("username") + " " + rs.getString("lastname"));
					return this;
				}

			}
		} else {
			throw new Exception("Estudante não encontrado");

		}

		rs.close();
		statement.close();
		connection.getConnection().close();
		return null;

	}
	public ProjectTCC getProject() {
		return this.project;
	}
	
	public void loadNotification() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "Select * from GuidingNotifications where guiding_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setInt(1, this.getUserID());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				setNotification(rs.getString("body"),rs.getString("notification_status"));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	
	private void setNotification(String body,String status) {
		StatusTypes type = StatusTypes.UNDEFINED;
		switch (status) {
		case "READ":
			type = StatusTypes.READ;
			break;
		case "NOT_READ":
			type = StatusTypes.RECEIVED;
			break;
		}
		Notification notification = new StudentNotification(body,type,this);
		this.sendNotification(notification);
	}

	public Student getStudentById(int id) throws Exception {
		DBConnection connection = new DBConnection();

		String sql = "select * from user_guiding where guiding_id =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			this.setEmail(rs.getString("email"));
			this.setName(rs.getString("username") + " " + rs.getString("lastname"));
			this.setUserID(Integer.valueOf(rs.getInt("guiding_id")));
			return this;
		} else {
			throw new Exception("Usuário não encontrado");
		}
	}
	
	public void loadProject() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "call getGuidingProjects(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, this.getUserID());

			ResultSet rs = statement.executeQuery();
			rs.next();
			this.setProject(
					rs.getInt("project_id"), 
					rs.getString("project_name"),
					rs.getInt("advisor_id"), 
					rs.getString("project_status"), 
					rs.getFloat("project_grade")
					);
			
			
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	

}
