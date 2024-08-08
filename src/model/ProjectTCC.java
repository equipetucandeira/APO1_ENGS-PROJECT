package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectTCC {
	
	private String title;
	private Advisor advisor;
	private List<Student> students;
    private StatusTypes status;
    private float grade;
    private List<Task> tasks;
    
    public ProjectTCC(String title,Advisor advisor, Student student) {
    	this.setTitle(title);
    	this.setAdvisor(advisor);	
    	this.setStatus(StatusTypes.PROGRESS);
    	this.setGrade(grade);
    	this.tasks = new ArrayList<Task>();
    	
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    public void setAdvisor(Advisor advisor) {
    	this.advisor = advisor;
    }
    public void setStudent(Student student) {
    	this.students.add(student);
    }
    public void setStatus(StatusTypes status) {
    	this.status = status;
    }
    public void setGrade(float grade) {
    	this.grade = grade;
    }
    
    
    public Task createTask(LocalDate startDate, LocalDate endDate, String title, String description){
    	Task newtask = new Task(startDate,endDate,title,description);
    	tasks.add(newtask);
    	
    	return newtask;
   }
    
    public Task createSubTask(Task task,LocalDate startDate, LocalDate endDate, String title, String description){
    	try {
    	Task subtask = task.setSubTask(startDate, endDate, title, description);
    	return subtask;
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
   }
   
    
    public void attachDocument(String taskname, Document document) throws Exception {
    	Task searchTask = this.getTaskByName(taskname);
    	Task subTask = searchTask.getSubTaskByName(taskname);
    	Task useSearch;
    	
    	if(subTask != null) {
    		useSearch = subTask;
    	}else {
    		useSearch = searchTask;
    	}
    	
    	if(useSearch.getStatus() != StatusTypes.COMPLETED) {
	    	if(useSearch.haveSubtasks()) {
	    		throw new Exception("Erro, você não pode adicionar um documento dentro de uma tarefa pai");
	    	}else {
	    		useSearch.attachDocument(document);
	    		 Notification notification = new TaskCompletedNotification(
	                     "Documento '" + document.getTitle() + "' foi anexado à tarefa '" + useSearch.getTitle() + "'.",
	                     StatusTypes.SEND
	                 );
	    		 this.sendNotification(this.advisor,notification);
	    		searchTask.verifySubTaskStatus();
	    	}
	    }
    }
    
   public Advisor getAdvisor() {
	   return this.advisor;
   }
 
    public String getProjectTitle() {
    	return title;
    }
    public String getAdvisorName() {
    	return this.advisor.toString();
    }
    public List<Student> getStudentsList() {
    	return this.students;
    }
    
    public Student getStudentByName(String name) {
    	for(Student search : this.students) {
    		if(search.getName().equalsIgnoreCase(name)){
    			return search;
    		}
    	}
    	return null;
    }
    public String getProjectStatus() {
    	return this.status.toString();
    }
    
    public List<Task> getTaskList(){
    	return tasks;
    }
    
    
    public Task getTaskByName(String name) {
    	for(Task search : this.tasks) {
    		if(search.getTitle().equalsIgnoreCase(name)){
    			return search;
    		}
    		if(search.haveSubtasks()) {
	    		for(Task subSearch : search.getSubTasks()) {
	    			if(subSearch.getTitle().equalsIgnoreCase(name)) {
	    				return search;
	    			}
	    		}
    		}
    	}
    	return null;
    }
   
    
    public void sendNotification(User user,Notification notification) {
    	user.sendNotification(notification);
    }
 
}
