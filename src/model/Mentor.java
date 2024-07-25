package model;

import java.util.ArrayList;
import java.util.List;

public class Mentor extends User {
	
	private List<ProjectTCC> associatedProjects;
	
	
	public Mentor(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
		this.associatedProjects =  new ArrayList<ProjectTCC>();
	}
	
	public void setAssociatedProjects(ProjectTCC project) {
		associatedProjects.add(project);
	}
	
	public List<ProjectTCC> getAllAssociatedProjects(){
		return associatedProjects;
	}
	
	public ProjectTCC getProject(String projectName){
		for(ProjectTCC project : associatedProjects) {
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		throw new RuntimeException("Projeto não encontrado");
		
	}

}
