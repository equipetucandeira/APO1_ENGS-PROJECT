package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import banco.DBConnection;
import model.notification.AdvisorNotification;
import model.notification.Notification;

public class Advisor extends User {

	private List<ProjectTCC> associatedProjects;

	public Advisor(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
		this.associatedProjects = new ArrayList<>();
	}

	public Advisor() {
		this.associatedProjects = new ArrayList<>();
	}

	@Override
	public User login() throws Exception {

		DBConnection connection = new DBConnection();

		String sql = "select * from user_advisor where email =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			String searchEmail = rs.getString("email");
			if (searchEmail != null) {
				String searchPassword = rs.getString("user_password");
				if (searchPassword.equals(password)) {
					this.setUserID(Integer.valueOf(rs.getInt("advisor_id")));
					this.setName(rs.getString("username") + " " + rs.getString("lastname"));
					return this;
				}
			}
		} else {
			throw new Exception("Usuário não encontrado");
		}

		rs.close();
		statement.close();
		connection.getConnection().close();
		return null;

	}
	

	
	public void loadNotification() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "Select * from AdvisorNotifications where advisor_id = ?";
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
		Notification notification = new AdvisorNotification(body,type,this);
		this.sendNotification(notification);
	}

	private void setAssociatedProjects(Integer id, String title, int student_id, String status, float grade) {
		try {
			Student student = new Student();
			student = student.getStudentById(student_id);
			StatusTypes type = StatusTypes.UNDEFINED;
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
			ProjectTCC projeto = new ProjectTCC(id, title, this, student, type, grade);
			associatedProjects.add(projeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createProject(String title, Student student) throws Exception {
		for (ProjectTCC project : this.getAllAssociatedProjects()) {
			if (project.getStudent().getUserID() == student.getUserID()) {
				throw new Exception("Este estudante já está em um projeto");
			}
		}
		try {
			DBConnection connection = new DBConnection();
			String sql = "call newProject(?,?,?)";

			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setString(1, title);
			statement.setInt(2, student.getUserID());
			statement.setInt(3, this.getUserID());

			ResultSet rs = statement.executeQuery();
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadProjectList() {
		this.associatedProjects.clear();
		try {
			DBConnection connection = new DBConnection();
			String sql = "call getProjects(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, this.getUserID());

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				this.setAssociatedProjects(rs.getInt("project_id"), rs.getString("project_name"),
						rs.getInt("guiding_id"), rs.getString("project_status"), rs.getFloat("project_grade"));

			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ProjectTCC getProjectTCCByName(String project) {
		for (ProjectTCC projeto : associatedProjects) {
			if (projeto.getProjectTitle().equalsIgnoreCase(project)) {
				return projeto;
			}
		}
		return null;
	}

	public List<ProjectTCC> getAllAssociatedProjects() {
		return associatedProjects;
	}
	
	public Advisor getAdvisorById(Integer id) throws Exception {
		DBConnection connection = new DBConnection();

		String sql = "select * from user_advisor where advisor_id =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			this.setEmail(rs.getString("email"));
			this.setName(rs.getString("username") + " " + rs.getString("lastname"));
			this.setUserID(Integer.valueOf(rs.getInt("advisor_id")));
			return this;
		} else {
			throw new Exception("Usuário não encontrado");
		}
	}

}
