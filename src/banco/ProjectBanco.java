package banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.InterfaceProject;
import model.ProjectTCC;
import model.Student;

public class ProjectBanco {


	public static void createNewProject(ProjectTCC project) throws Exception {
			DBConnection connection = new DBConnection();
			String sql = "call newProject(?,?,?)";

			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setString(1, project.getTitle());
			statement.setInt(2, project.getStudent().getUserID());
			statement.setInt(3, project.getAdvisor().getUserID());

			ResultSet rs = statement.executeQuery();
			rs.close();
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
