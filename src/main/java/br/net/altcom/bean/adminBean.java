package br.net.altcom.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;

import org.apache.poi.ss.usermodel.Sheet;
import org.primefaces.model.UploadedFile;

import br.net.altcom.excel.Excel;

@Model
public class adminBean {

	private UploadedFile file;
	private List<Sheet> sheets = new ArrayList<>();

	public void upload() {
		if (file != null) {
			System.out.println("Arquivo: " + file.getFileName() + " Upload");
			InputStream inputstream = null;

			try {
				inputstream = file.getInputstream();
			} catch (IOException e1) {
				System.out.println("Erro getInputStream");
			}
			
			try (Excel excel = new Excel(inputstream)) {
				sheets = excel.getSheets();
			} catch (Exception e1) {
				System.out.println("Erro ao costruir Excel");
			}
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public List<Sheet> getSheets() {
		return sheets;
	}
}
