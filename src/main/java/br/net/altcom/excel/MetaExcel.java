package br.net.altcom.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.MetaDAO;
import br.net.altcom.modelo.entity.Meta;

public class MetaExcel extends ExcelProcessor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MetaDAO metaDAO;	
	
	@Override
	protected void executa(ExcelSheet excelSheet) {
		List<Row> rows = excelSheet.getPlusRow(10);

		for (Row row : rows) {
			getMeta(row);
		}
	}
	
	public void getMeta(Row row) {
		for (int i = 1; i <= 12; i++) {
			Meta meta = new Meta();

			Integer codigo = new Integer(row.getCell(1).getStringCellValue());
			meta.setCodigo(codigo);

			String faturamentoTexto = row.getCell(i + 1).getStringCellValue().replace(",", ".").replace(".", "");
			BigDecimal faturamento = new BigDecimal(faturamentoTexto);
			meta.setFaturamento(faturamento);

			meta.setMes(i + "/" +"2017");

			metaDAO.adiciona(meta);
		}
	}
}
