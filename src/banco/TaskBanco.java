package banco;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ProjectTCC;

public class TaskBanco {

	public TaskBanco() {
		// TODO Auto-generated constructor stub
	}
	
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
	
	public static void loadTaskList(ProjectTCC project) throws SQLException {
		
			DBConnection connection = new DBConnection();
			String sql = "SELECT * from task WHERE project_id =(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, project.getID());

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				project.setTask(rs.getInt("task_id"), rs.getDate("initial_date").toLocalDate(),
						rs.getDate("final_date").toLocalDate(), rs.getString("title"), rs.getString("task_description"),
						rs.getString("task_status"), rs.getInt("document_id"), rs.getDouble("task_grade"));

			}
			rs.close();
			statement.close();

	}

}

