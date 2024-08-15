package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import banco.AdvisorBanco;
import banco.DBConnection;
import banco.NotificationBanco;
import banco.ProjectBanco;
import banco.AdvisorBanco;
import model.notification.AdvisorNotification;
import model.notification.Notification;

public class Advisor extends User  {

	private List<ProjectTCC> associatedProjects;

	public Advisor(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
		this.associatedProjects = new ArrayList<>();
	}

	public Advisor() {
		this.associatedProjects = new ArrayList<>();
	}

	public User login() throws Exception {	
		AdvisorBanco.loginAdvisor(this);
		return this;
	}

	public void loadNotification() throws Exception {
		NotificationBanco.loadAdvisorNotification(this);
	}


	public void setAssociatedProjects(Integer id, String title, int student_id, String status, float grade) {
		try {
			Student student = new Student();
			student = student.getStudentById(student_id);
			ProjectTCC projeto = new ProjectTCC(id, title, this, student, status, grade);
			associatedProjects.add(projeto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void createProject(String title, Student student) throws Exception {

		for (InterfaceProject project : this.getAllAssociatedProjects()) {
			if (project.getStudent().getUserID() == student.getUserID()) {
				throw new Exception("Este estudante já está em um projeto seu");
			}
		}
		if(title.length() >45) {
			throw new Exception("Título do projeto excede o tamanho máximo");
		}

		ProjectTCC projeto = new ProjectTCC(title,this,student,"INICIADO",0);
		ProjectBanco.createNewProject(projeto);

	}


	public void loadProjectList()  throws Exception{
		this.associatedProjects.clear();
		AdvisorBanco.loadProjects(this);
	}


	public InterfaceProject getProjectTCC(String project) {
		for (InterfaceProject projeto : associatedProjects) {
			if (projeto.getTitle().equalsIgnoreCase(project)) {
				return projeto;
			}
		}
		return null;
	}


	public List<ProjectTCC> getAllAssociatedProjects() {
		return associatedProjects;
	}


	public Advisor loadAdvisor(Integer id) throws Exception {
		return AdvisorBanco.getAdvisor(id);
	}

}
