package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import banco.NotificationBanco;
import banco.StudentBanco;
import model.notification.Notification;


public class Student extends User {
	private InterfaceProject project;
	
	public Student(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
	}

	public Student() {
	}
	

	public Student login() throws Exception {
		StudentBanco.login(this);
		return this;
	}
	
	public InterfaceProject getProject() {
		return this.project;
	}		
	
	public void setProject(Integer projectID, String title, Integer advisorID, String status, float grade)throws Exception {
		
		Advisor advisor = new Advisor();
		advisor = advisor.loadAdvisor(advisorID);
		InterfaceProject newProject = new ProjectTCC(projectID,title,advisor,this,status,grade);
		this.project = newProject;
	}
	
	public void loadNotification() throws Exception {
		NotificationBanco.loadStudentNotifications(this);
	}
	
	public void setNotification() throws SQLException {
		NotificationBanco.loadStudentNotifications(this);
	}

	public Student getStudentById(Integer id) throws Exception {
		return StudentBanco.getStudent(id);
	}
	
	public void loadProject() throws SQLException {
		StudentBanco.loadProject(this);
	}



}
