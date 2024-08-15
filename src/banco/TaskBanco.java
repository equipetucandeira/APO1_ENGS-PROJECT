package banco;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Document;
import model.Feedback;
import model.ProjectTCC;
import model.Task;

public class TaskBanco {


	public static void createTask(Date startDate, Date endDate, String title, String description,Integer project_id)throws Exception {

		DBConnection connection = new DBConnection();
		String sql = "INSERT INTO task(initial_date, final_date, title, task_description, project_id) values (?,?,?,?,?) ";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setDate(1, startDate);
		statement.setDate(2, endDate);
		statement.setString(3, title);
		statement.setString(4, description);
		statement.setInt(5, project_id);

		statement.executeUpdate();

		statement.close();
	}

	public static void createSubTask(Date startDate, Date endDate, String title, String description, Task task) throws SQLException {

		DBConnection connection = new DBConnection();
		String sql = "INSERT INTO subtask(task_id,initial_date, final_date, title, task_description) values (?,?,?,?,?) ";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, task.getID());
		statement.setDate(2, startDate);
		statement.setDate(3, endDate);
		statement.setString(4, title);
		statement.setString(5, description);

		statement.executeUpdate();
		statement.close();
	}

	public static void UpdateTaskStatusToComplete(Task task) throws Exception {

		DBConnection connection = new DBConnection();
		String sql = "UPDATE task SET task_status = ? WHERE task_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setString(1, "COMPLETA");
		statement.setInt(2, task.getID());

		statement.executeUpdate();

		statement.close();
	}


	public static void updateDocumentToAttached (Task task) throws SQLException {

		DBConnection connection = new DBConnection();
		String sql = "UPDATE task SET document_id = ? WHERE task_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, task.getDocument().getID());
		statement.setInt(2, task.getID());

		statement.executeUpdate();

		statement.close();

	}

	public static void subTaskCompleteUpdate(Task task) throws SQLException {

		DBConnection connection = new DBConnection();
		String sql = "UPDATE subtask SET task_status = ?, document_id = ? WHERE subtask_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setString(1, "COMPLETA");
		statement.setInt(2, task.getDocument().getID());
		statement.setInt(3, task.getID());

		statement.executeUpdate();

		statement.close();

	}

	

	public static void loadSubTaskList(Task task) throws SQLException, Exception {

		DBConnection connection = new DBConnection();
		String sql = "SELECT * from subtask WHERE task_id =(?)";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, task.getID());

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			task.setSubTask(rs.getInt("subtask_id"), rs.getDate("initial_date").toLocalDate(),
					rs.getDate("final_date").toLocalDate(), rs.getString("title"), rs.getString("task_description"),
					rs.getString("task_status"), rs.getInt("document_id"), rs.getDouble("task_grade"));
		}
		rs.close();
		statement.close();


	}

	public static void UpdateTaskDate(Task task) {
		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE task SET initial_date = ?, final_date = ? WHERE task_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setDate(1, java.sql.Date.valueOf(task.getStartDate()));
			statement.setDate(2, java.sql.Date.valueOf((task.getEndDate())));
			statement.setInt(3, task.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void addTaskGrade(Task task) throws SQLException {
		DBConnection connection = new DBConnection();
		String sql = "UPDATE task SET task_grade = ? WHERE task_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setDouble(1,task.getGrade());
		statement.setInt(2, task.getID());

		statement.executeUpdate();

		statement.close();

	}
	public static void addSubTaskGrade(Task task) throws SQLException{

		try {
			DBConnection connection = new DBConnection();
			String sql = "UPDATE subtask SET task_grade = ? WHERE subtask_id = ?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setDouble(1,task.getGrade());
			statement.setInt(2, task.getID());

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

