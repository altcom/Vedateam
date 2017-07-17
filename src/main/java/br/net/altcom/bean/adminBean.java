package br.net.altcom.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.net.altcom.excel.Excel;
import br.net.altcom.excel.RegionalExcel;
import br.net.altcom.excel.RepresentanteExcel;

@Named
@ViewScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UploadedFile file;
	private byte[] contents;
	private List<String> sheets = new ArrayList<>();
	private String tipoDoExcel;

	@Inject
	private RepresentanteExcel representanteExcel;
	@Inject
	private RegionalExcel regionalExcel;

	public void upload() {
		if (file != null) {
			System.out.println("Arquivo: " + file.getFileName() + " Upload");
			this.contents = file.getContents();

			try (Excel excel = new Excel(new ByteArrayInputStream(this.contents))) {
				this.sheets = excel.getSheetsName();
			} catch (Exception e1) {
				System.out.println("Erro ao costruir Excel");
			}
		}
	}

	public void executarExcel(String sheet) {
		System.out.println("Planilha selecionada: " + sheet);

		switch (tipoDoExcel) {
		case "representante":
			System.out.println("Representante Selecionado");
			this.representanteExcel.setByte(contents);
			this.representanteExcel.setSheetName(sheet);
			new Thread(this.representanteExcel).start();
			break;
		case "regional":
			System.out.println("Regional Selecionado");
			this.regionalExcel.setByte(contents);
			this.regionalExcel.setSheetName(sheet);
			new Thread(this.regionalExcel).start();
			break;

		default:
			break;
		}
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getSheets() {
		return sheets;
	}

	public void setTipoDoExcel(String tipoDoExcel) {
		this.tipoDoExcel = tipoDoExcel;
	}

	public String getTipoDoExcel() {
		return tipoDoExcel;
	}
}
