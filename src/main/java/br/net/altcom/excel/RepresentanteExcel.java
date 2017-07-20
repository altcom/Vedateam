package br.net.altcom.excel;

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
	protected void executa(ExcelSheet excelSheet) {
		List<Row> rows = excelSheet.getPlusRow(10);

		for (Row row : rows) {
			Representante representante = getRepresentante(row);
			representanteDAO.adiciona(representante);
		}
	}
	
	private Representante getRepresentante(Row row) {
		Representante representante = new Representante();
		representante.setNome(row.getCell(0).getStringCellValue());
		representante.setEmail(row.getCell(5).getStringCellValue());

		Integer codigo = new Integer("0");
		String codigoTexto = row.getCell(1).getStringCellValue();
		
		if(!codigoTexto.equals("-"))
			codigo = new Integer(codigoTexto);
		
		representante.setCodigo(codigo);
		
		return representante;
	}

}
