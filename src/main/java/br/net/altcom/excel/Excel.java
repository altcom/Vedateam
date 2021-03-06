package br.net.altcom.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

public class Excel implements AutoCloseable {

	private InputStream inputStream;
	private Workbook workbook;

	public Excel(InputStream stream) {
		this.inputStream = stream;
	}

	public List<String> getSheetsName() {
		List<String> sheetsName = new ArrayList<>();
		workbook = StreamingReader.builder().open(inputStream);
		workbook.forEach(sheet -> sheetsName.add(sheet.getSheetName()));

		return sheetsName;
	}

	public ExcelSheet getExcelSheetByName(String sheetName) {
		workbook = StreamingReader.builder().open(inputStream);
		for (Sheet sheet : workbook) {
			if (sheet.getSheetName().equalsIgnoreCase(sheetName))
				return new ExcelSheet(sheet);
		}
		return null;
	}

	public List<ExcelSheet> getExcelSheets() {
		List<ExcelSheet> sheets = new ArrayList<>();
		workbook = StreamingReader.builder().rowCacheSize(50000).bufferSize(6000).open(inputStream);
		workbook.forEach(sheet -> sheets.add(new ExcelSheet(sheet)));

		return sheets;
	}

	@Override
	public void close() throws Exception {
		if (workbook != null)
			workbook.close();
		if (inputStream != null)
			inputStream.close();
	}
}
