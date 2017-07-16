package br.net.altcom.excel;

import java.io.Serializable;

public class RepresentanteExcel implements Serializable, Runnable{

	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");
	}
}
