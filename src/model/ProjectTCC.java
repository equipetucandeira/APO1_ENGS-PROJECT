package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import banco.DBConnection;
import model.notification.Notification;

public class ProjectTCC {
	private Integer id;
	private String title;
	private Advisor advisor;
	private Student student;
	private StatusTypes status;
	private float grade;
	private List<Task> tasks;

	public ProjectTCC(Integer id, String title, Advisor advisor, Student student, StatusTypes status, float grade) {
		this.setID(id);
		this.setTitle(title);
		this.setAdvisor(advisor);
		this.setStudent(student);
		this.setStatus(status);
		this.setGrade(grade);
		this.tasks = new ArrayList<Task>();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setStatus(StatusTypes status) {
		this.status = status;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public Advisor getAdvisor() {
		return this.advisor;
	}

	public Integer getID() {
		return this.id;
	}

	public String getProjectTitle() {
		return title;
	}

	public String getAdvisorName() {
		return this.advisor.toString();
	}

	public Student getStudent() {
		return this.student;
	}

	public String getProjectStatus() {
		return this.status.toString();
	}

	public float getProjectGrade() {
		return this.grade;
	}

	public List<Task> getTaskList() {
		return tasks;
	}


	public Task getTaskByName(String name) {
		for (Task search : this.tasks) {
			if (search.getTitle().equalsIgnoreCase(name)) {
				return search;
			}
			if (search.haveSubtasks()) {
				for (Task subSearch : search.getSubTasks()) {
					if (subSearch.getTitle().equalsIgnoreCase(name)) {
						return search;
					}
				}
			}
		}
		return null;
	}

	public void createTask(Date startDate, Date endDate, String title, String description) {
		try {
			DBConnection connection = new DBConnection();
			String sql = "INSERT INTO task(initial_date, final_date, title, task_description, project_id) values (?,?,?,?,?) ";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setString(3, title);
			statement.setString(4, description);
			statement.setInt(5, this.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setTask(Integer id, LocalDate startDate, LocalDate endDate, String title, String description,
		String status, Integer document_id, Double grade) {
		StatusTypes type = StatusTypes.UNDEFINED;
		switch (status) {
		case "INCOMPLETA":
			type = StatusTypes.INCOMPLETE;
			break;
		case "COMPLETA":
			type = StatusTypes.COMPLETED;
			break;
		}
	
		
		Task newtask = new Task(id, startDate, endDate, title, description, type,grade);
		newtask.setDocument(document_id);
		newtask.loadFeedback();
		tasks.add(newtask);
	}

	

	public void sendNotification(User user, Notification notification) {
		user.sendNotification(notification);
	}

	public void loadTaskList() {
		this.tasks.clear();
		try {
			DBConnection connection = new DBConnection();
			String sql = "SELECT * from task WHERE project_id =(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, this.getID());

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				this.setTask(rs.getInt("task_id"), rs.getDate("initial_date").toLocalDate(),
						rs.getDate("final_date").toLocalDate(), rs.getString("title"), rs.getString("task_description"),
						rs.getString("task_status"), rs.getInt("document_id"), rs.getDouble("task_grade"));

			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
