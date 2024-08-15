package model;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import model.notification.Notification;

public interface InterfaceProject {

	void setTitle(String title);

	void setAdvisor(Advisor advisor);

	void setID(Integer id);

	void setStudent(Student student);

	void setStatus(String status);

	void setGrade(float grade);

	Advisor getAdvisor();

	Integer getID();

	String getTitle();
	
	Student getStudent();

	String getStatus();

	float getGrade();

	List<Task> getTasks();

	void createTask(Date startDate, Date endDate, String title, String description) throws Exception;

	void setTask(Integer id, LocalDate startDate, LocalDate endDate, String title, String description, String status,
			Integer document_id, Double grade);

	void sendNotification(User user, Notification notification);

	void loadTaskList() throws SQLException;

}