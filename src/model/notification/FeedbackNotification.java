package model.notification;

import java.sql.SQLException;

import banco.NotificationBanco;
import model.Student;
import model.InterfaceUser;

public class FeedbackNotification extends Notification{

	public FeedbackNotification(Student user) {
		super("Você recebeu um novo Feedback",user);
	}
	
	@Override
	public void sendNotification() throws SQLException {
		NotificationBanco.CreateStudentNotification(this);
	}

}
