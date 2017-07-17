package br.net.altcom.excel;

public abstract class ExcelProcessor implements Runnable {

	protected static final long serialVersionUID = 1L;
	protected String sheetName;
	protected byte[] contents;
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setByte(byte[] contents) {
		this.contents = contents;
	}

	@Override
	public void run() {
		
	}
}