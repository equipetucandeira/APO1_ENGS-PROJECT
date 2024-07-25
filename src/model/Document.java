package model;
public class Document {
    private String title;
    private String file;
    private String date;

    public Document(String title, String file, String date) {
        this.title = title;
        this.file = file;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}