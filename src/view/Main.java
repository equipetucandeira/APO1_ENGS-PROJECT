package view;
import model.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		
		Student student = new Student("pedro","Pclemonini",123,"Familia");
		Advisor advisor = new Advisor("Joao","Jclemonini",124,"Familia");
	
		ProjectTCC projeto = new ProjectTCC("Projeto", advisor, student);
		
		Document documento = new Document("Titulo", "Documento 1", "docu.pdf");
		
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa1", "tarefa-faca");
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa2", "tarefa-faca");
		projeto.createTask(LocalDate.now(), LocalDate.now().plusDays(10), "tarefa3", "tarefa-faca");	
		projeto.createSubTask(projeto.getTaskByName("tarefa1"), LocalDate.now(), LocalDate.now().plusDays(10), "sub-tarefa", "sub-tarefa-faca");
		projeto.createSubTask(projeto.getTaskByName("tarefa1"), LocalDate.now(), LocalDate.now().plusDays(10), "sub-tarefa2", "sub-tarefa-faca2");
	
		
		try {
			projeto.attachDocument("tarefa2", documento);
			projeto.attachDocument("sub-tarefa", documento);
			projeto.attachDocument("sub-tarefa2", documento);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		for(Task linha : projeto.getTaskList()) {
			System.out.print(linha.getTitle()+ " ");
			System.out.print(linha.getDescription()+ " ");
			System.out.print(linha.getStartDate().toString()+ " ");
			System.out.print(linha.getEndDate().toString()+ " ");
			System.out.println();
			if(linha.haveSubtasks()) {
				for(Task sublinha : linha.getSubTasks()) {
					System.out.print(sublinha.getTitle()+ " ");
					System.out.print(sublinha.getDescription()+ " ");
					System.out.print(sublinha.getStartDate().toString()+ " ");
					System.out.print(sublinha.getEndDate().toString()+ " ");
					System.out.println();
					
				}
			}
			}
		}
	

		
	}
		

