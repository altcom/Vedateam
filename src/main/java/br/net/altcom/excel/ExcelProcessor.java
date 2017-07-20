package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public abstract class ExcelProcessor implements Runnable {

	private String sheetName;
	private byte[] contents;

	public int quantidadeDeLinha() {
		int quantidade = 0;

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			quantidade = excelSheet.getRowSize();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return quantidade;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setByte(byte[] contents) {
		this.contents = contents;
	}

	@Override
	public void run() {
		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			excelSheet.begin();

			while (!excelSheet.isFinish()) {
				executa(excelSheet);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected abstract void executa(ExcelSheet excelSheet);
}