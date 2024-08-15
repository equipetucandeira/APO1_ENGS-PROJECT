package model.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.DBConnection;
import model.Advisor;

public class AdvisorNotification extends Notification {
	
	public AdvisorNotification(String message, String status, Advisor advisor) {
		super(message,advisor);
	}
	
	@Override
	public void sendNotification() {
		try {
			DBConnection connection = new DBConnection();
			String sql = "INSERT INTO AdvisorNotification(advisor_id,notification_status,body) values (?,?,?)";
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
