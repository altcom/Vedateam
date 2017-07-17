package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.RepresentanteDAO;
import br.net.altcom.modelo.entity.Representante;

public class RepresentanteExcel extends ExcelProcessor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private RepresentanteDAO representanteDAO;
	
	@Override
	public void run() {
		System.out.println("Executando RepresentanteExcel");
		System.out.println("Planilha que sera executada: " + sheetName);

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			excelSheet.begin();

			while (!excelSheet.isFinish()) {
				List<Row> rows = excelSheet.getPlusRow(10);

				for (Row row : rows) {
					Representante representante = getRepresentante(row);
					representanteDAO.adiciona(representante);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private Representante getRepresentante(Row row) {
		Representante representante = new Representante();
		representante.setNome(row.getCell(0).getStringCellValue());
		representante.setEmail(row.getCell(5).getStringCellValue());

		Integer codigo = new Integer(row.getCell(1).getStringCellValue());
		representante.setCodigo(codigo);
		
		return representante;
	}
}
