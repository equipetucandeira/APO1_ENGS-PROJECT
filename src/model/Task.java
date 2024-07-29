package model;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Task {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
    private StatusTypes status;
    private List<Task> subTasks;
    private int duration;
    private float grade;

    
    public Task(LocalDate startDate, LocalDate endDate, String title, String description, StatusTypes status, int duration) {
        this.setStartDate(startDate); 
        this.setEndDate(endDate);
        this.setDescription(description);
        this.setTitle(title);
        this.setStatus(status);
        
    }
 
    public Task(Task task) {
    	 this.setStartDate(task.startDate); 
         this.setEndDate(task.endDate);
         this.setDescription(task.description);
         this.setTitle(task.title);
         this.setStatus(task.status);
    }
    
    
    public void setSubTasks(Task task) {
    	
    	if(this.getSubTasks() == null) {
    		this.subTasks = new ArrayList<Task>();
    	}
    	this.subTasks.add(new Task(task));
    	
    }
    
    public List<Task> getSubTasks(){
    	return subTasks;
    }

    private LocalDate getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    private LocalDate getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private StatusTypes getStatus() {
        return status;
    }

    private void setStatus(StatusTypes status) {
        this.status = status;
    }

    private int getDuration() {
        return duration;
    }

    private void setDuration() {
        this.duration = (Period.between(getStartDate(), LocalDate.now()).getDays());
    }
}
