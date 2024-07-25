package view;
import model.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class Main {

	public static void main(String[] args) {
		Tutored student = new Tutored("pedro","Pclemonini",123,"Familia");
		Mentor mentor = new Mentor("Joao","Jclemonini",124,"Familia");
	
		
		ProjectTCC projeto = new ProjectTCC("Projeto");
		projeto.setTutored(student);
		mentor.setAssociatedProjects(projeto);
		
		mentor.getProject("Projeto");
		
		
		
		
	}

}
