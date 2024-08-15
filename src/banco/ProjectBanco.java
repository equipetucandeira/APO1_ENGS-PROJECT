package banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.InterfaceProject;
import model.ProjectTCC;
import model.Student;

public class ProjectBanco {

	public ProjectBanco() {
		// TODO Auto-generated constructor stub
	}
	
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
	


}
