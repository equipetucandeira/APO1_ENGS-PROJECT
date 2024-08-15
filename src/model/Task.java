package model;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import banco.DBConnection;

public class Task implements DocumentObserver {
	private Integer id;
	private LocalDate startDate;
	private LocalDate endDate;
	private String title;
	private String description;
	private String status;
	private List<Task> subTasks;
	private Integer duration;
	private Double grade;
	private Document attachedDocument;
	private Feedback feedback;

	private Task() {
		this.subTasks = null;
	}

	public Task(Integer id, LocalDate startDate, LocalDate endDate, String title, String description,
			String status, Double grade) {
		this.setID(id);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setDescription(description);
		this.setTitle(title);
		this.setStatus(status);
		this.setDuration();
		this.setGrade(grade);
		this.subTasks = new ArrayList<Task>();
	}

	@Override
	public void onDocumentAttached(Document document) {
		if (document != null) {
			this.status = "COMPLETA";
			this.attachedDocument = document;
		}
	}
	public void taskCompleteUpdate() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE task SET task_status = ? WHERE task_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setString(1, "COMPLETA");
			statement.setInt(2, this.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addDocumentIDFatherTask() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE task SET document_id = ? WHERE task_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setInt(1, this.getDocument().getID());
			statement.setInt(2, this.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public void subTaskCompleteUpdate() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE subtask SET task_status = ?, document_id = ? WHERE subtask_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setString(1, "COMPLETA");
			statement.setInt(2, this.getDocument().getID());
			statement.setInt(3, this.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void attachDocument(String filePath,String fileName) throws Exception {
		if (this.getStatus() != "COMPLETA") {
			if (this.haveSubtasks()) {
				throw new Exception("Erro, você não pode adicionar um documento dentro de uma tarefa pai");
			} else {
				
				try {
					Document document = new Document(filePath,fileName);
					document.attachObserver(this);
					document.attachDocument();
				}
				catch(Exception e1) {
					System.out.println("Erro ao inserir");
					return;
				} 
					if(this.subTasks == null) {
						subTaskCompleteUpdate();
					}else {
						taskCompleteUpdate();
						addDocumentIDFatherTask();
					}
			
			}
		}
	}

	public void verifySubTaskStatus() {
		if (this.haveSubtasks()) {
			for (Task task : subTasks) {
				if (task.getStatus() == "INCOMPLETA") {
					this.status =  "INCOMPLETA";
				} else {
					this.status =  "COMPLETA";
					taskCompleteUpdate();
				}
			}
		}
	}

	private void setID(Integer id) {
		this.id = id;
	}

	private void setGrade(Double grade) {
		this.grade = grade;
	}

	public Double getGrade() {
		return this.grade;
	}

	public void setFeedback(String text) {
		if(text != null) {
			if (this.feedback == null) {
				if (this.getStatus() == "COMPLETA") {
					this.feedback = new Feedback(LocalDate.now(), text);
					feedback.addToDatabase(this);
				}
			}
		}
	}

	public void loadFeedback() {

		if(this.getSubTasks() != null) {	
			try {
				DBConnection connection = new DBConnection();
				String sql = "SELECT * from feedback WHERE task_id = ?";
				PreparedStatement statement = connection.getConnection().prepareStatement(sql);

				statement.setInt(1, this.getID());

				ResultSet rs =  statement.executeQuery();
				if(rs.next()){
					this.feedback = new Feedback(rs.getDate("feedback_date").toLocalDate(),rs.getString("body"));
				}
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
		try {
			DBConnection connection = new DBConnection();
			String sql = "SELECT * from sub_feedback WHERE subtask_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setInt(1, this.getID());

			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				this.feedback = new Feedback(rs.getDate("feedback_date").toLocalDate(),rs.getString("body"));
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Feedback getFeedback() {
		return this.feedback;
	}

	public void createSubTask(Date startDate, Date endDate, String title, String description) {
		try {
			DBConnection connection = new DBConnection();
			String sql = "INSERT INTO subtask(task_id,initial_date, final_date, title, task_description) values (?,?,?,?,?) ";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, this.getID());
			statement.setDate(2, startDate);
			statement.setDate(3, endDate);
			statement.setString(4, title);
			statement.setString(5, description);

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Task setSubTask(Integer id, LocalDate startDate, LocalDate endDate, String title, String description,
			String status, Integer document_id, Double grade) throws Exception {

		Task task = new Task();
		task.setID(id);
		task.setTitle(title);
		task.setDescription(description);
		task.setStartDate(startDate);
		task.setEndDate(endDate);
		task.setStatus(status);
		task.setDocument(document_id);
		task.setDuration();
		task.setGrade(grade);
		task.loadFeedback();
		if (!this.subTasks.add(task)) {
			throw new Exception("Você não pode adicionar uma sub-tarefa dentro de uma sub-tarefa");
		}
		return task;
	}

	public List<Task> getSubTasks() {
		return subTasks;
	}

	public Boolean haveSubtasks() {
		if (this.subTasks != null) {
			if (this.getSubTasks().isEmpty()) {
				return false;
			}
			return true;
		}
		return false;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Integer getID() {
		return this.id;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setDocument(Integer document_id) {
		if(document_id != null) {
			Document document = new Document(document_id);
			document.getDocumentById();
			this.attachedDocument = document;
		}
	}

	public Document getDocument() {
		return this.attachedDocument;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration() {
		this.duration = (Period.between(getStartDate(), LocalDate.now()).getDays());
	}

	public Task getSubTaskByName(String name) {

		if (this.haveSubtasks()) {
			for (Task subSearch : this.getSubTasks()) {
				if (subSearch.getTitle().equalsIgnoreCase(name)) {
					return subSearch;
				}
			}
		}
		return null;
	}

	public void loadSubTaskList() {
		this.subTasks.clear();
		try {
			DBConnection connection = new DBConnection();
			String sql = "SELECT * from subtask WHERE task_id =(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, this.getID());

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				this.setSubTask(rs.getInt("subtask_id"), rs.getDate("initial_date").toLocalDate(),
						rs.getDate("final_date").toLocalDate(), rs.getString("title"), rs.getString("task_description"),
						rs.getString("task_status"), rs.getInt("document_id"), rs.getDouble("task_grade"));
			}
			checkTaskDate();
			verifySubTaskStatus();
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void checkTaskDate() {
		LocalDate minDate = LocalDate.now();
		LocalDate maxDate = LocalDate.now();
		for (Task subtask : this.getSubTasks()) {
			if (subtask.getStartDate().isBefore(minDate)) {
				minDate = subtask.getStartDate();
			}
			if (subtask.getEndDate().isAfter(maxDate)) {
				maxDate = subtask.getEndDate();
			}
		}
		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE task SET initial_date = ?, final_date = ? WHERE task_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setDate(1, java.sql.Date.valueOf(minDate));
			statement.setDate(2, java.sql.Date.valueOf(maxDate));
			statement.setInt(3, this.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTaskGrade(Double grade) {
		this.setGrade(grade);
		if(this.subTasks != null) {
			try {
				DBConnection connection = new DBConnection();
				String sql = "UPDATE task SET task_grade = ? WHERE task_id = ?";
				PreparedStatement statement = connection.getConnection().prepareStatement(sql);

				statement.setDouble(1,grade);
				statement.setInt(2, this.getID());

				statement.executeUpdate();

				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				DBConnection connection = new DBConnection();
				String sql = "UPDATE subtask SET task_grade = ? WHERE subtask_id = ?";
				PreparedStatement statement = connection.getConnection().prepareStatement(sql);

				statement.setDouble(1,grade);
				statement.setInt(2, this.getID());

				statement.executeUpdate();

				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
