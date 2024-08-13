package model.notification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import banco.DBConnection;
import model.StatusTypes;
import model.Student;

public class StudentNotification extends Notification {
	
	public StudentNotification(String message, StatusTypes status,Student student) {
		super(message, status,student);
		
	}
	
	@Override
	public void sendNotification() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "INSERT INTO GuidingNotification(guiding_id,notification_status,body) values (?,?,?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setInt(1, user.getUserID());
			statement.setString(2, "NOT_READ");
			statement.setString(4, this.message);

			statement.executeUpdate();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
	}
	
	

}
