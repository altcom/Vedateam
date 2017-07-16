package br.net.altcom.excel;

import java.io.Serializable;

public class RepresentanteExcel implements Serializable, Runnable{

	private static final long serialVersionUID = 1L;
	private ExcelSheet sheet;
	
	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");
	}
	
	public void setSheet(ExcelSheet sheet) {
		this.sheet = sheet;
	}
}
