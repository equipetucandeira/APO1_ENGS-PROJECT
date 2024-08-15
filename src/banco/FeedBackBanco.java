package banco;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Feedback;
import model.Task;


public class FeedbackBanco {
	public static void CreateTaskFeedback(Date date, String body, Integer task_id) throws SQLException {

		DBConnection connection = new DBConnection();
		String sql = "call addFeedback(?,?,?)";
		CallableStatement statement = connection.getConnection().prepareCall(sql);

		statement.setDate(1, date);
		statement.setString(2, body);
		statement.setInt(3, task_id);
		statement.executeUpdate();

		statement.close();

	}

	public static void CreateSubTaskFeedback(Date date, String body, Integer subtask_id) throws SQLException {


		DBConnection connection = new DBConnection();
		String sql = "call addSubtaskFeedback(?,?,?)";
		CallableStatement statement = connection.getConnection().prepareCall(sql);

		statement.setDate(1, date);
		statement.setString(2, body);
		statement.setInt(3, subtask_id);
		statement.executeUpdate();
		
		statement.executeUpdate();

		statement.close();
	}
	
	public static void loadTaskFeedback(Task task) throws SQLException {
		DBConnection connection = new DBConnection();
		String sql = "SELECT * from task INNER JOIN feedback ON task.feedback_id=feedback.feedback_id WHERE task_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, task.getID());

		ResultSet rs =  statement.executeQuery();
		if(rs.next()){
			Feedback feedback = new Feedback(rs.getDate("feedback_date").toLocalDate(),rs.getString("body"));
			task.setFeedback(feedback);
		}
		statement.close();
	}


	public static void loadSubTaskFeedback(Task task) throws SQLException{
		DBConnection connection = new DBConnection();
		String sql = "SELECT * from subtask INNER JOIN feedback ON subtask.feedback_id=feedback.feedback_id WHERE subtask_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, task.getID());

		ResultSet rs = statement.executeQuery();
		if(rs.next()){
			Feedback feedback = new Feedback(rs.getDate("feedback_date").toLocalDate(),rs.getString("body"));
			task.setFeedback(feedback);
		}

		statement.close();

	}

}
