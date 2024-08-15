package banco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Advisor;
import model.Student;
import model.User;
import model.InterfaceUser;
import model.notification.Notification;

public class NotificationBanco {

	public NotificationBanco() {
		// TODO Auto-generated constructor stub
	}

	public static void loadStudentNotifications(InterfaceUser user) throws SQLException {
		DBConnection connection = new DBConnection();
		String sql = "SELECT * from GuidingNotifications WHERE guiding_id =(?)";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);
		statement.setInt(1, user.getUserID());

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Notification notification = new Notification(rs.getString("body"),rs.getString("notification_status"));
			user.setNotification(notification);
		}
		rs.close();
		statement.close();
	}
	
	public static void loadAdvisorNotification(InterfaceUser user) throws Exception {

		DBConnection connection = new DBConnection();
		String sql = "Select * from AdvisorNotifications where advisor_id = ?";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, user.getUserID());

		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Notification notification = new Notification(rs.getString("body"),rs.getString("notification_status"));
			user.setNotification(notification);
		}
			statement.close();

		}

	
	public static void CreateStudentNotification(Notification notification) throws SQLException {
		
			DBConnection connection = new DBConnection();
			String sql = "INSERT INTO GuidingNotifications(guiding_id,notification_status,body) values (?,?,?)";
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);

			statement.setInt(1, notification.getUser().getUserID());
			statement.setString(2, "NOT_READ");
			statement.setString(3, notification.getMessage());

			statement.executeUpdate();

			statement.close();
	}
	
	public static void CreateAdvisorNotification(Notification notification) throws SQLException {
		
		DBConnection connection = new DBConnection();
		String sql = "INSERT INTO AdvisorNotifications(advisor_id,notification_status,body) values (?,?,?)";
		PreparedStatement statement = connection.getConnection().prepareStatement(sql);

		statement.setInt(1, notification.getUser().getUserID());
		statement.setString(2, "NAO_LIDA");
		statement.setString(3, notification.getMessage());

		statement.executeUpdate();

		statement.close();
}
}
