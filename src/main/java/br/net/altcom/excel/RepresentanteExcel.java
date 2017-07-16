package br.net.altcom.excel;

import java.io.InputStream;
import java.io.Serializable;

public class RepresentanteExcel implements Serializable, Runnable{

	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	private String sheetName;
	
	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");
		System.out.println("Planilha que sera executada: " + sheetName);
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
