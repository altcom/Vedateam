package br.net.altcom.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelSheet {

	private Sheet sheet;
	private Row header;
	private Iterator<Row> rowIterator;
	private int lastPositionOfTheRow;
	private boolean finish;
	private int rowSize;

	public ExcelSheet(Sheet sheet) {
		this.sheet = sheet;
		rowCount();
	}

	private void rowCount() {
		rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			rowSize++;
			rowIterator.next();
		}
	}

	public void begin() {
		rowIterator = sheet.rowIterator();
		finish = false;
		if (rowIterator.hasNext())
			header = rowIterator.next();
		lastPositionOfTheRow = 1;
	}

	public synchronized List<Row> getPlusRow(int amount) {
		List<Row> rows = new ArrayList<>();
		amount += lastPositionOfTheRow;

		while (lastPositionOfTheRow < amount) {
			if (rowIterator.hasNext()) {
				Row rowNext = rowIterator.next();
				rows.add(rowNext);
				lastPositionOfTheRow++;
			} else {
				finish = true;
				break;
			}
		}
		return rows;
	}

	public boolean isFinish() {
		return finish;
	}

	public String getSheetName() {
		return sheet.getSheetName();
	}

	public int getLastPositionOfTheRow() {
		return lastPositionOfTheRow;
	}

	public Row getHeader() {
		return header;
	}

	public int getRowSize() {
		return rowSize;
	}
}
