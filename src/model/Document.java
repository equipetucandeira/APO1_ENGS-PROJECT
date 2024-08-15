package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import banco.DBConnection;
import banco.DocumentBanco;

public class Document {
	private int id;
	private String title;
	private String file;
	private List<DocumentObserver> observers = new ArrayList<>();

	public Document(String file, String title) {
		this.title = title;
		this.file = file;
		
	}

	public Document() {
	}

	public void attachObserver(DocumentObserver observer) {
		observers.add(observer);
	}	

	public void attachDocument() throws Exception{
		handleFileUpload();
		notifyObservers();
	}

	public void notifyObservers() {
		for (DocumentObserver observer : observers) {
			observer.onDocumentAttached(this);
		}
	}

	
	public void handleFileUpload() throws Exception {
		int documentid = -1;
	    String documentPath = System.getProperty("user.dir") + "/documents/";
	    String destinationPath =  documentPath + new File(this.file).getName();
	    
	    try {
	       	Files.copy(Paths.get(file), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
	       
	        documentid = DocumentBanco.InsertPathAndSendNotification(destinationPath,this.title);
	        
	        this.setID(documentid);
	    } catch (IOException | SQLException e) {
	        throw e;
	    } 
	  
	}
	
	private void setID(int documentid) {
		this.id = documentid;
		
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFile() {
		File file = new File(this.file);
        return file.getName();
	}
	
	public String getFileExtension() {
		 String fileName = this.getFile();
	     int dotIndex = fileName.lastIndexOf('.');
	     if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
	            return fileName.substring(dotIndex + 1);
	        }
	        return ""; 
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getID() {
		return this.id;
	}
	
	public Document getDocumentById(Integer id) throws SQLException {
		return DocumentBanco.getDocument(id);
	}
	
	public void copyFile(String destinationPath) throws IOException {
        File sourceFile = new File(this.file);
        File destinationFile = new File(destinationPath);
        
        try (FileInputStream in = new FileInputStream(sourceFile);
             FileOutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }


}