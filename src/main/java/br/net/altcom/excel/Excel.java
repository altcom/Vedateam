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

	public List<Sheet> getSheets() {
		List<Sheet> sheets = new ArrayList<>();
		workbook = StreamingReader.builder().open(inputStream);
		workbook.forEach(sheet -> sheets.add(sheet));
		
		return sheets;
	}

	@Override
	public void close() throws Exception {
		workbook.close();
	}
}
