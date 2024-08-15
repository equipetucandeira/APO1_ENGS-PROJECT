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

public class ProjectTCC implements InterfaceProject {
	private Integer id;
	private String title;
	private Advisor advisor;
	private Student student;
	private String status;
	private float grade;
	private List<Task> tasks;

	public ProjectTCC(Integer id, String title, Advisor advisor, Student student, String status, float grade) {
		this.setID(id);
		this.setTitle(title);
		this.setAdvisor(advisor);
		this.setStudent(student);
		this.setStatus(status);
		this.setGrade(grade);
		this.tasks = new ArrayList<Task>();
	}
	
	public ProjectTCC(String title, Advisor advisor, Student student, String status, float grade) {
		this.setTitle(title);
		this.setAdvisor(advisor);
		this.setStudent(student);
		this.setStatus(status);
		this.setGrade(grade);
		this.tasks = new ArrayList<Task>();
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	@Override
	public void setID(Integer id) {
		this.id = id;
	}

	@Override
	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setGrade(float grade) {
		this.grade = grade;
	}

	@Override
	public Advisor getAdvisor() {
		return this.advisor;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	@Override
	public String getTitle() {
		return title;
	}


	@Override
	public Student getStudent() {
		return this.student;
	}

	@Override
	public String getStatus() {
		return this.status.toString();
	}

	@Override
	public float getGrade() {
		return this.grade;
	}

	@Override
	public List<Task> getTasks() {
		return tasks;
	}
	@Override
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

	@Override
	public void setTask(Integer id, LocalDate startDate, LocalDate endDate, String title, String description,
		String status, Integer document_id, Double grade) {

		
		Task newtask = new Task(id, startDate, endDate, title, description, status,grade);
		newtask.setDocument(document_id);
		newtask.loadFeedback();
		tasks.add(newtask);
	}

	

	@Override
	public void sendNotification(User user, Notification notification) {
		user.sendNotification(notification);
	}

	@Override
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
