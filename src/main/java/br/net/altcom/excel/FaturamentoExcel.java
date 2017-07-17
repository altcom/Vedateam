package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.ProdutoDAO;
import br.net.altcom.dao.RegionalDAO;
import br.net.altcom.dao.RepresentanteDAO;
import br.net.altcom.modelo.entity.Produto;
import br.net.altcom.modelo.entity.Regional;
import br.net.altcom.modelo.entity.Representante;

public class FaturamentoExcel extends ExcelProcessor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegionalDAO regionalDAO;
	@Inject
	private RepresentanteDAO representanteDAO;
	@Inject
	private ProdutoDAO produtoDAO;

	@Override
	public void run() {
		System.out.println("Faturamento MetaExcel");
		System.out.println("Planilha que sera executada: " + sheetName);

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			excelSheet.begin();

			while (!excelSheet.isFinish()) {
				List<Row> rows = excelSheet.getPlusRow(10);

				for (Row row : rows) {
					getFaturamento(row);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void getFaturamento(Row row) {
		Regional regional = new Regional();
		regional.setNome(row.getCell(0).getStringCellValue());
		regional = regionalDAO.buscaPeloNome(regional);

		Representante representante = new Representante();
		representante.setNome(row.getCell(2).getStringCellValue());

		Integer codigo = new Integer("0");
		String codigoTexto = row.getCell(1).getStringCellValue();
		if (!codigoTexto.equals("-")) {
			codigo = new Integer(row.getCell(1).getStringCellValue());
		}
		representante.setCodigo(codigo);

		representante = representanteDAO.buscaPeloCodigo(representante);

		getProduto(row);
	}

	private Produto getProduto(Row row) {
		Produto produto = new Produto();
		produto.setProduto(row.getCell(3).getStringCellValue());
		produto.setFamilia(row.getCell(4).getStringCellValue());
		produto.setCodigo(row.getCell(5).getStringCellValue());
		produto.setItem(row.getCell(6).getStringCellValue());

		Produto produtoDoBanco = produtoDAO.buscaPeloCodigo(produto);

		if (produtoDoBanco == null) {
			produtoDAO.adiciona(produto);
			return produtoDAO.buscaPeloCodigo(produto);
		} else {
			return produtoDoBanco;
		}
	}
}
