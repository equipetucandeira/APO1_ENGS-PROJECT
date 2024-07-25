package model;
import java.time.LocalDate;


public class Feedback {
	private Task associatedTask; 
    private LocalDate date;
    private String body;

    public Feedback(LocalDate date, String body) {
        this.date = date;
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}