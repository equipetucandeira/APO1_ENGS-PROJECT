package banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Advisor;

public class AdvisorBanco {

	public  AdvisorBanco() {
	}

	public static void loginAdvisor(Advisor advisor)throws Exception { {
		DBConnection connection = new DBConnection();
		String sql = "select * from user_advisor where email =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setString(1, advisor.getEmail());
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			String searchEmail = rs.getString("email");
			if (searchEmail != null) {
				String searchPassword = rs.getString("user_password");
				if (searchPassword.equals(advisor.getPassword())) {
					advisor.setUserID(Integer.valueOf(rs.getInt("advisor_id")));
					advisor.setName(rs.getString("username") + " " + rs.getString("lastname"));
				}
			}
		} else {
			throw new Exception("Usuário não encontrado");
		}
		rs.close();
		statement.close();
		connection.getConnection().close();
	}
	}

	public static void loadAdvisorNotification(Advisor advisor) throws Exception {

		DBConnection connection = new DBConnection();
		String sql = "Select * from AdvisorNotifications where advisor_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, advisor.getUserID());

		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			advisor.setNotification(rs.getString("body"),rs.getString("notification_status"));

			statement.close();

		}
	}

	public static void loadProjects(Advisor advisor) throws Exception{

		DBConnection connection = new DBConnection();
		String sql = "call getProjects(?)";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, advisor.getUserID());

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			advisor.setAssociatedProjects(rs.getInt("project_id"), rs.getString("project_name"),
					rs.getInt("guiding_id"), rs.getString("project_status"), rs.getFloat("project_grade"));
		}
		rs.close();
		statement.close();

	}
	
	public static Advisor getAdvisor(Integer id) throws Exception{
		
			DBConnection connection = new DBConnection();

			String sql = "select * from user_advisor where advisor_id =?";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				Advisor advisor = new Advisor();
				advisor.setEmail(rs.getString("email"));
				advisor.setName(rs.getString("username") + " " + rs.getString("lastname"));
				advisor.setUserID(Integer.valueOf(rs.getInt("advisor_id")));
				return advisor;
			} else {
				throw new Exception("Usuário não encontrado");
			}
		
	}


}
