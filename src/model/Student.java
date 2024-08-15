package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import banco.StudentBanco;
import model.notification.AdvisorNotification;
import model.notification.Notification;
import model.notification.StudentNotification;

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
	
	public void setProject(Integer projectID, String title, Integer advisorID, String status, float grade) {
		try {
		Advisor advisor = new Advisor();
		advisor = advisor.loadAdvisor(advisorID);
		InterfaceProject newProject = new ProjectTCC(projectID,title,advisor,this,status,grade);
		this.project = newProject;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadNotification() throws Exception {
		StudentBanco.loadNotifications(this);
	}
	
	public void setNotification(String body,String status) {
	
		Notification notification = new StudentNotification(body,status,this);
		this.sendNotification(notification);
	}

	public Student getStudentById(Integer id) throws Exception {
		
		StudentBanco.loadStudent(id);
		
		return this;
	}
	
	public void loadProject() throws SQLException {
		StudentBanco.loadProject(this);
	}



}
