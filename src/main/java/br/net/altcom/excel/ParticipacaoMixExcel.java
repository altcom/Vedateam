package br.net.altcom.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.RepresentanteDAO;
import br.net.altcom.modelo.dao.ParticipacaoMixDAO;
import br.net.altcom.modelo.entity.ParticipacaoMix;
import br.net.altcom.modelo.entity.Representante;

public class ParticipacaoMixExcel extends ExcelProcessor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RepresentanteDAO representanteDAO;
	@Inject
	private ParticipacaoMixDAO participacaoDAO;
	private Row header = null;
	private String data;
	
	@Override
	protected void executa(ExcelSheet excelSheet) {
		header = excelSheet.getHeader();
		List<Row> rows = excelSheet.getPlusRow(10);

		for (Row row : rows) {
			getParticipacaoMix(row);
		}
	}

	private void getParticipacaoMix(Row row) {
		Representante representante = null;
		String codigo = "";
		for (Cell cell : header) {
			if (cell.getColumnIndex() == 0) {
				codigo = row.getCell(cell.getColumnIndex()).getStringCellValue();
				representante = getRepresentante(codigo);
				continue;
			}

			if (representante == null) {
				System.out.println("Não foi encontrado representante: " + codigo);
				return;
			}
			ParticipacaoMix participacaoMix = new ParticipacaoMix();

			String porcentagem = row.getCell(cell.getColumnIndex()).getStringCellValue();
			porcentagem = porcentagem.replaceAll(",", "").replace("%", "");
			participacaoMix.setPorcentagem(new BigDecimal(porcentagem));

			participacaoMix.setRepresentante(representante);
			participacaoMix.setFamilia(cell.getStringCellValue());
			participacaoMix.setMes(this.data);

			participacaoDAO.adiciona(participacaoMix);
		}
	}

	private Representante getRepresentante(String codigo) {
		Representante representante = new Representante();
		representante.setCodigo(new Integer(codigo));
		return representanteDAO.buscaPeloCodigo(representante);
	}

	public void setData(String data) {
		this.data = data;
	}
}
