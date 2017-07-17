package br.net.altcom.excel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Row;

import br.net.altcom.dao.ClienteDAO;
import br.net.altcom.dao.FaturamentoDAO;
import br.net.altcom.dao.ProdutoDAO;
import br.net.altcom.dao.RegionalDAO;
import br.net.altcom.dao.RepresentanteDAO;
import br.net.altcom.modelo.entity.Cliente;
import br.net.altcom.modelo.entity.Faturamento;
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
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private FaturamentoDAO faturamentoDAO;

	@Override
	public void run() {
		System.out.println("Faturamento MetaExcel");
		System.out.println("Planilha que sera executada: " + sheetName);

		try (Excel excel = new Excel(new ByteArrayInputStream(contents))) {
			ExcelSheet excelSheet = excel.getExcelSheetByName(sheetName);
			excelSheet.begin();

			while (!excelSheet.isFinish()) {
				List<Row> rows = excelSheet.getPlusRow(10);
				System.out.println(excelSheet.getLastPositionOfTheRow());
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
		Regional regional = getRegional(row);
		Representante representante = getRepresentante(row);
		Produto produto = getProduto(row);
		Cliente cliente = getCliente(row);
		LocalDate data = getData(row);
		BigDecimal valorFaturamento = new BigDecimal(row.getCell(12).getStringCellValue().replace(",", "."));

		Faturamento faturamento = new Faturamento();
		faturamento.setCliente(cliente);
		faturamento.setProduto(produto);
		faturamento.setRegional(regional);
		faturamento.setRepresentante(representante);
		faturamento.setData(data);
		faturamento.setFaturamento(valorFaturamento);

		faturamentoDAO.adiciona(faturamento);
	}

	private Representante getRepresentante(Row row) {
		Representante representante = new Representante();
		representante.setNome(row.getCell(2).getStringCellValue());

		Integer codigo = new Integer("0");
		String codigoTexto = row.getCell(1).getStringCellValue();
		if (!codigoTexto.equals("-")) {
			codigo = new Integer(row.getCell(1).getStringCellValue());
		}
		representante.setCodigo(codigo);

		representante = representanteDAO.buscaPeloCodigo(representante);
		return representante;
	}

	private Regional getRegional(Row row) {
		Regional regional = new Regional();
		regional.setNome(row.getCell(0).getStringCellValue());
		regional = regionalDAO.buscaPeloNome(regional);
		return regional;
	}

	public LocalDate getData(Row row) {
		int dia = Integer.parseInt(row.getCell(10).getStringCellValue());
		int mes = Integer.parseInt(row.getCell(9).getStringCellValue());
		int ano = Integer.parseInt(row.getCell(8).getStringCellValue());
		return LocalDate.of(ano, mes, dia);
	}

	private Cliente getCliente(Row row) {
		Cliente cliente = new Cliente();
		cliente.setNome(row.getCell(7).getStringCellValue());

		Cliente clienteDoBanco = clienteDAO.buscaPeloNome(cliente);

		if (clienteDoBanco == null) {
			clienteDAO.adiciona(cliente);
			return clienteDAO.buscaPeloNome(cliente);
		} else {
			return clienteDoBanco;
		}
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
