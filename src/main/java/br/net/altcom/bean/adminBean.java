package br.net.altcom.bean;

import javax.enterprise.inject.Model;

import org.primefaces.model.UploadedFile;

@Model
public class adminBean {

	private UploadedFile file;
	 
    public void upload() {
        if(file != null) {
        	System.out.println("Arquivo: " + file.getFileName() + " Upload");
        }
    }
    
    public UploadedFile getFile() {
    	return file;
    }
    
    public void setFile(UploadedFile file) {
    	this.file = file;
    }
}
