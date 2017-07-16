package br.net.altcom.bean;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

@Model
public class adminBean {

	private UploadedFile file;
	 
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public UploadedFile getFile() {
    	return file;
    }
    
    public void setFile(UploadedFile file) {
    	this.file = file;
    }
}
