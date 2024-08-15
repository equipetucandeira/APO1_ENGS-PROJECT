package model.notification;

import java.sql.SQLException;

import banco.NotificationBanco;
import model.InterfaceProject;

public class TaskNotification extends Notification {

	public TaskNotification(InterfaceProject project) {
		super("Novo envio no projeto: "+ project.getTitle() ,project.getAdvisor());
	}
	
	@Override
	public void sendNotification() throws SQLException {
		NotificationBanco.CreateAdvisorNotification(this);
	}

}
