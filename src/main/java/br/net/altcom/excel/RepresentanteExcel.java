package br.net.altcom.excel;

import java.io.InputStream;
import java.io.Serializable;

public class RepresentanteExcel implements Serializable, Runnable{

	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	
	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");		
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
