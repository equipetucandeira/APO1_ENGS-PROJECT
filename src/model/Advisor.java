package model;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends User {
	
	private List<ProjectTCC> associatedProjects;
	
	public Advisor(String name, String email, Integer userID, String password) {
		super(name, email, userID, password);
		this.associatedProjects =  new ArrayList<ProjectTCC>();
	}
	
	public void setAssociatedProjects(ProjectTCC project) {
		associatedProjects.add(project);
	}
	
	public List<ProjectTCC> getAllAssociatedProjects(){
		return associatedProjects;
	}
	
	

}
