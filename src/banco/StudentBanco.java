package banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Student;
import model.InterfaceUser;

public class StudentBanco {

	public StudentBanco() {
		// TODO Auto-generated constructor stub
	}

	public static void login(Student student) throws SQLException, Exception {

		DBConnection connection = new DBConnection();

		String sql = "select * from user_guiding where email =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setString(1, student.email);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			String searchEmail = rs.getString("email");
			if (searchEmail != null) {
				String searchPassword = rs.getString("user_password");
				if (searchPassword.equals(student.password)) {
					student.setUserID(Integer.valueOf(rs.getInt("guiding_id")));
					student.setName(rs.getString("username") + " " + rs.getString("lastname"));
				}

			}
		} else {
			throw new Exception("Estudante não encontrado");
		}

		rs.close();
		statement.close();
		connection.getConnection().close();

	}
	
	
	public static void loadProject(Student student) throws Exception {
		
			DBConnection connection = new DBConnection();
			String sql = "call getGuidingProjects(?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setInt(1, student.getUserID());

			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				student.setProject(
					rs.getInt("project_id"), 
					rs.getString("project_name"),
					rs.getInt("advisor_id"), 
					rs.getString("project_status"), 
					rs.getFloat("project_grade")
					);
			}	
		
			rs.close();
			statement.close();
	
	}
	
	public static Student getStudent(Integer id) throws Exception {
		DBConnection connection = new DBConnection();

		String sql = "select * from user_guiding where guiding_id =?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			Student student = new Student();
			student.setEmail(rs.getString("email"));
			student.setName(rs.getString("username") + " " + rs.getString("lastname"));
			student.setUserID(Integer.valueOf(rs.getInt("guiding_id")));
			return student;
		} else {
			throw new Exception("Usuário não encontrado");
		}
	}
	
}
