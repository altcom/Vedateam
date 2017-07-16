package br.net.altcom.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.Sheet;
import org.primefaces.model.UploadedFile;

import br.net.altcom.excel.Excel;
import br.net.altcom.excel.RepresentanteExcel;

@Named
@ViewScoped
public class adminBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UploadedFile file;
	private List<Sheet> sheets = new ArrayList<>();
	
	@Inject
	private RepresentanteExcel representanteExcel;
	
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

	public void executarExcel(Sheet sheet){
		System.out.println("Planilha selecionada: " + sheet.getSheetName());
		new Thread(this.representanteExcel).start();
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
