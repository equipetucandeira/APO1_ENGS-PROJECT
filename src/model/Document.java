package model;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private String title;
    private String file;
    private String date;
    private List<DocumentObserver> observers = new ArrayList<>();


    public Document(String title, String file, String date) {
        this.title = title;
        this.file = file;
        this.date = date;
    }
    
    public void attachObserver(DocumentObserver observer) {
        observers.add(observer);
    }


    public void attachDocument() {
        // TODO: VERIRIFICAR SE O DOCUMENTO É VALIDO E INSERIR NO BANCO
        notifyObservers();
    }
    
    public void notifyObservers() {
        for (DocumentObserver observer : observers) {
            observer.onDocumentAttached(this);
        }
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