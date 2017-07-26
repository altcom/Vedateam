package br.net.altcom.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.net.altcom.excel.Excel;
import br.net.altcom.excel.FaturamentoExcel;
import br.net.altcom.excel.MetaExcel;
import br.net.altcom.excel.ParticipacaoMixExcel;
import br.net.altcom.excel.RegionalExcel;
import br.net.altcom.excel.RepresentanteExcel;

@Named
@ViewScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UploadedFile file;
	private byte[] contents;
	private List<String> sheetsName;
	private String data;
	
	@Inject
	private RepresentanteExcel representanteExcel;
	@Inject
	private RegionalExcel regionalExcel;
	@Inject
	private MetaExcel metaExcel;
	@Inject
	private FaturamentoExcel faturamentoExcel;
	@Inject
	private ParticipacaoMixExcel participacaoMixExcel;

	public void uploadExcel() {
		if (file == null)
			return;

		this.contents = file.getContents();

		try (Excel excel = new Excel(new ByteArrayInputStream(this.contents))) {
			sheetsName = excel.getSheetsName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processarRepresentanteExcel(String sheetName) {
		System.out.println("Processar Representante Excel");
		this.representanteExcel.setSheetName(sheetName);
		this.representanteExcel.setByte(this.contents);
		new Thread(this.representanteExcel).start();
	}

	public void processarRegionalExcel(String sheetName) {
		System.out.println("Processar Regional Excel");
		this.regionalExcel.setSheetName(sheetName);
		this.regionalExcel.setByte(this.contents);
		new Thread(this.regionalExcel).start();
	}

	public void processarFaturamentoExcel(String sheetName) {
		System.out.println("Processar Faturamento Excel");
		this.faturamentoExcel.setSheetName(sheetName);
		this.faturamentoExcel.setByte(this.contents);
		new Thread(this.faturamentoExcel).start();
		this.sheetsName.remove(sheetName);
	}

	public void processarMetaExcel(String sheetName) {
		System.out.println("Processar Meta Excel");
		this.metaExcel.setSheetName(sheetName);
		this.metaExcel.setByte(this.contents);
		new Thread(this.metaExcel).start();
	}
	
	public void processarParticipacaoExcel(String sheetName) {
		System.out.println("Processar Participacao Excel");
		this.participacaoMixExcel.setSheetName(sheetName);
		this.participacaoMixExcel.setByte(this.contents);
		this.participacaoMixExcel.setData(this.data);
		new Thread(this.participacaoMixExcel).start();
		this.sheetsName.remove(sheetName);
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<String> getSheetsName() {
		return sheetsName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
