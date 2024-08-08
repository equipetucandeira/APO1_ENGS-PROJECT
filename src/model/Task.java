package model;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Task implements DocumentObserver{
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
    private StatusTypes status;
    private List<Task> subTasks;
    private Integer duration;
    private Double grade;
    private Document attachedDocument;

    
    private Task() {
    	this.subTasks = null;
    }
    
    public Task(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.setStartDate(startDate); 
        this.setEndDate(endDate);
        this.setDescription(description);
        this.setTitle(title);
        this.setStatus(StatusTypes.INCOMPLETE);
        this.setDuration();
        this.setGrade(0.0);
        this.subTasks = new ArrayList<Task>();
    }
    
    @Override
    public void onDocumentAttached(Document document) {
    	if(document != null) {
    		this.status = StatusTypes.COMPLETED;
            this.attachedDocument = document;	
    	}         
    }
    
    public void attachDocument(Document document) {
        document.attachObserver(this);
        document.attachDocument();
    }
    
    public void verifySubTaskStatus() {
    	if(this.haveSubtasks()) {
    		for(Task task: subTasks) {
    			if(task.getStatus() == StatusTypes.INCOMPLETE) {
    				this.status = StatusTypes.INCOMPLETE;
    			}else {
    				this.status = StatusTypes.COMPLETED;
    			}
    		}
    	}
    }
    
    private void setGrade(Double grade) {
		this.grade = grade;
	}
    
   
    public Task setSubTask(LocalDate startDate, LocalDate endDate, String title, String description) throws Exception {
    	Task task = new Task();
    	task.setTitle(title);
    	task.setDescription(description);
    	task.setStartDate(startDate);
    	task.setEndDate(endDate);
    	task.setStatus(StatusTypes.INCOMPLETE);
    	task.setDuration();
    	task.setGrade(0.0);
    	if (!this.subTasks.add(task)) {
    		throw new Exception("Você não pode adicionar uma sub-tarefa dentro de uma sub-tarefa");
    	}
    	return task;
    }
    
    public List<Task> getSubTasks(){
    	return subTasks;
    }
    
    public Boolean haveSubtasks() {
    	if(this.subTasks != null) {
	    	if(this.getSubTasks().isEmpty())  {
	    		return false;
	    	}
	    	return true;
    	}
    	return false;
    }
  
       
    public List<String> getTaskInfo() {
    	List<String> lista = new ArrayList<String>();
    	lista.add(title);
    	lista.add(description);
    	lista.add(startDate.toString());
    	lista.add(endDate.toString());
    	lista.add(status.toString());
    	lista.add(duration.toString());
    	lista.add(grade.toString());
    	return lista;
    }
    

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setDocument(Document document) {
    	this.attachedDocument = document;
    }
    
    public Document getDocument() {
    	return this.attachedDocument;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTypes getStatus() {
        return status;
    }

    public void setStatus(StatusTypes status) {
        this.status = status;
    }
    

    public Integer getDuration() {
        return duration;
    }

    public void setDuration() {
        this.duration = (Period.between(getStartDate(), LocalDate.now()).getDays());
    }
    
    public Task getSubTaskByName(String name) {
    	
		if(this.haveSubtasks()) {
    		for(Task subSearch : this.getSubTasks()) {
    			if(subSearch.getTitle().equalsIgnoreCase(name)) {
    				return subSearch;
    			}
    		}
		}
		return null;
    }
}
