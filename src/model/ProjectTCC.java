package model;
import java.util.ArrayList;
import java.util.List;

public class ProjectTCC {
	
	private String name;
	private List<Tutored> tutoreds;
    private StatusTypes status;
    private float grade;
    private List<Task> tasks;

    public ProjectTCC(String name, StatusTypes status, float grade) {
        this.name = name;
        this.status = status;
        this.grade = grade;
        this.tasks = new ArrayList<Task>();
    }
    
    public ProjectTCC(String name) {
        this.name = name;
        this.tutoreds = new ArrayList<Tutored>();
        this.status = StatusTypes.PROGRESS;
        this.tasks = new ArrayList<Task>();   
    }
        
    
    public void setTutored(Tutored tutored) {
    	this.tutoreds.add(tutored);
    }
    

    public void setName(String name) {
        this.name = name;
    }
    
    public void setStatus(StatusTypes status) {
        this.status = status;
    }
    
    public void setTasks(Task task) {
        this.tasks.add(task);
    }
    
    public void setSubTask(Task task,Task subTask) {
    	if(this.getTaskFromList(task).getSubTasks().equals(null)) {
    		
    		this.getTaskFromList(task).setSubTasks(subTask);	
    	}
    }
    
    public Tutored getTutoredByName(String tutoredName) {
    	for(Tutored tutored : tutoreds) {
    		if(tutored.getName().equals(tutoredName)) { 
    			return tutored;
    		}
    	}
		return null;
    }
    
    public List<Tutored> getAllTutoreds() {
    	return tutoreds;
    }
    public void setGrade(float grade) {
        this.grade = grade;
    }

    public StatusTypes getStatus() {
        return status;
    }
    
    public float getGrade() {
        return grade;
    }
    
    public String getName() {
        return name;
    }

    public List<Task> getAllTasksFromList() {
        return tasks;
    }
    
    public Task getTaskFromList(Task task) {
    	return tasks.get(tasks.indexOf(task));
    }
    
    public void checkTask(Task task) {
    	
    }
 
}
