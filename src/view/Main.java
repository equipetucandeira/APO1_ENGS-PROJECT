package view;
import model.*;
import java.util.ArrayList;
import java.util.List;

import model.*;
import java.time.LocalDate;
public class Main {

	public static void main(String[] args) {
		Tutored student = new Tutored("pedro","Pclemonini",123,"Familia");
		Mentor mentor = new Mentor("Joao","Jclemonini",124,"Familia");
	
		
		ProjectTCC projeto = new ProjectTCC("Projeto");
		
		projeto.setTutored(student);
		mentor.setAssociatedProjects(projeto);		
		
		
		System.out.println(mentor.getProject("Projeto"));
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		Task tarefa = new Task(startDate, endDate, "tarefa1", "tarefa-faca", StatusTypes.PROGRESS, 3);
		
		projeto.addTasks(tarefa);
		
	}

}
