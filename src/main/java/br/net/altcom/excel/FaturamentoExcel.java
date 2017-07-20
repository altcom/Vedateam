package br.net.altcom.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	Set<Regional> regionais = new HashSet<>();
	Set<Representante> representantes = new HashSet<>();
	Set<Produto> produtos = new HashSet<>();
	Set<Cliente> clientes = new HashSet<>();
	
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
	protected void executa(ExcelSheet excelSheet) {
		List<Row> rows = excelSheet.getPlusRow(50);
		List<Faturamento> faturamentos = new ArrayList<>();
		
		for (Row row : rows) {
			faturamentos.add(getFaturamento(row));
		}
		
		faturamentoDAO.salvarTodosFaturamento(faturamentos);
		
		if(clientes.size() >= 2000)
			clientes = new HashSet<>();
		
	}

	private Regional getRegional(Row row) {
		Regional regional = new Regional();
		regional.setNome(row.getCell(0).getStringCellValue());
		
		Regional regionalDaLista = regionais.stream().filter(r -> r.equals(regional)).findFirst().orElse(null);
		
		if(regionalDaLista == null){
			regionalDaLista = regionalDAO.buscaPeloNome(regional);
			regionais.add(regionalDaLista);
		}
		
		return regionalDaLista;
	}

	private Representante getRepresentante(Row row) {
		Representante representante = new Representante();
		
		if (!row.getCell(1).getStringCellValue().equals("-"))
			representante.setCodigo(new Integer(row.getCell(1).getStringCellValue()));
		
		Representante representanteDaLista = representantes.stream().filter(r -> r.equals(representante)).findFirst().orElse(null);
		
		if(representanteDaLista == null){
			representanteDaLista = representanteDAO.buscaPeloCodigo(representante);
			representantes.add(representanteDaLista);
		}

		return representanteDaLista;
	}
	
	private Faturamento getFaturamento(Row row) {
		BigDecimal valorFaturamento = new BigDecimal(row.getCell(12).getStringCellValue().replace(",", "."));

		Faturamento faturamento = new Faturamento();
		faturamento.setRegional(getRegional(row));
		faturamento.setRepresentante(getRepresentante(row));
		faturamento.setProduto(getProduto(row));
		faturamento.setCliente(getCliente(row));
		
		faturamento.setData(getData(row));
		faturamento.setFaturamento(valorFaturamento);

		return faturamento;
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

		Cliente clienteDaLista = clientes.stream().filter(c -> c.equals(cliente)).findFirst().orElse(null);
		
		if(clienteDaLista == null){
			clienteDaLista = clienteDAO.buscaPeloNome(cliente);
			
			if(clienteDaLista == null){
				clienteDAO.adiciona(cliente);
				clienteDaLista = clienteDAO.buscaPeloNome(cliente);
			}
			
			clientes.add(clienteDaLista);
		}
		
		return clienteDaLista;
	}

	private Produto getProduto(Row row) {
		Produto produto = new Produto();
		produto.setCodigo(row.getCell(5).getStringCellValue());
		
		Produto produtoDaLista = produtos.stream().filter(p -> p.equals(produto)).findAny().orElse(null);
		
		if(produtoDaLista == null){
			produtoDaLista = produtoDAO.buscaPeloCodigo(produto);
			
			if(produtoDaLista == null){
				produtoDaLista = new Produto();
				produtoDaLista.setCodigo(produto.getCodigo());
				produtoDaLista.setProduto(row.getCell(3).getStringCellValue());
				produtoDaLista.setFamilia(row.getCell(4).getStringCellValue());
				produtoDaLista.setItem(row.getCell(6).getStringCellValue());
				produtoDAO.adiciona(produto);
				produtoDaLista = produtoDAO.buscaPeloCodigo(produto);
			}
			
			produtos.add(produtoDaLista);
		}
		
		return produtoDaLista;
	}
}
