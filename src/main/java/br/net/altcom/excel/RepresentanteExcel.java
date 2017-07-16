package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

public class RepresentanteExcel implements Serializable, Runnable {

	private static final long serialVersionUID = 1L;
	private String sheetName;
	private byte[] contents;

	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");
		System.out.println("Planilha que sera executada: " + sheetName);

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			System.out.println("Abriu inputStream");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setByte(byte[] contents) {
		this.contents = contents;
	}
}
