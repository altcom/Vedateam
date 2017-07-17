package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.RegionalDAO;
import br.net.altcom.modelo.entity.Regional;

public class RegionalExcel extends ExcelProcessor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegionalDAO regionalDAO;

	@Override
	public void run() {
		System.out.println("Executando RegionalExcel");
		System.out.println("Planilha que sera executada: " + sheetName);

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			excelSheet.begin();

			while (!excelSheet.isFinish()) {
				List<Row> rows = excelSheet.getPlusRow(10);

				for (Row row : rows) {
					Regional regional = getRegional(row);
					regionalDAO.adiciona(regional);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Regional getRegional(Row row) {
		Regional regional = new Regional();
		regional.setNome(row.getCell(0).getStringCellValue());
		regional.setEmail(row.getCell(2).getStringCellValue());

		Integer codigo = new Integer(row.getCell(3).getStringCellValue());
		regional.setCodigo(codigo);

		return regional;
	}
}
