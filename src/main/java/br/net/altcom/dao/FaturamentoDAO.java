package br.net.altcom.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.dao.Mix;
import br.net.altcom.modelo.entity.Cliente;
import br.net.altcom.modelo.entity.Faturamento;
import br.net.altcom.modelo.entity.Regional;
import br.net.altcom.modelo.entity.Representante;

@Stateless
public class FaturamentoDAO extends CRUD<Faturamento> {

	public BigDecimal somaDoMesDeUmRepresentante(Representante representante, YearMonth mes) {
		String jpql = "SELECT SUM(f.faturamento) FROM Faturamento f "
				+ "WHERE f.representante = :representante AND MONTH(f.data) = :mes AND YEAR(f.data) = :ano";
		TypedQuery<BigDecimal> query = manager.createQuery(jpql, BigDecimal.class);
		query.setParameter("representante", representante);
		query.setParameter("mes", mes.getMonthValue());
		query.setParameter("ano", mes.getYear());

		BigDecimal total = query.getSingleResult();
		return (total == null) ? BigDecimal.ZERO : total;
	}

	public Set<Cliente> buscarClientesAtivosDoMesDeUmRepresentante(Representante representante, YearMonth mes) {
		String jpql = "SELECT f.cliente FROM Faturamento f "
				+ "WHERE f.representante = :representante AND MONTH(f.data) = :mes AND YEAR(f.data) = :ano";
		TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
		query.setParameter("representante", representante);
		query.setParameter("mes", mes.getMonthValue());
		query.setParameter("ano", mes.getYear());

		Set<Cliente> clientesAtivos = new HashSet<>();
		query.getResultList().forEach(c -> clientesAtivos.add(c));

		return clientesAtivos;
	}

	public Set<Cliente> buscaClientesEntreMes(Representante representante, YearMonth mesInicial, YearMonth mesFinal) {

		LocalDate dataInicial = LocalDate.of(mesInicial.getYear(), mesInicial.getMonth(), 1);
		LocalDate dataFinal = LocalDate.of(mesFinal.getYear(), mesFinal.getMonth(), mesFinal.lengthOfMonth());

		String jpql = "SELECT f.cliente FROM Faturamento f WHERE f.representante = :representante "
				+ "AND f.data BETWEEN :dataInicial AND :dataFinal";

		TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
		query.setParameter("representante", representante);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		Set<Cliente> clientesNaBase = new HashSet<>();
		query.getResultList().forEach(c -> clientesNaBase.add(c));

		return clientesNaBase;
	}

	public BigDecimal somaDoMesDeUmRegional(Regional regional, YearMonth mes) {
		String jpql = "SELECT SUM(f.faturamento) FROM Faturamento f "
				+ "WHERE f.regional = :regional AND MONTH(f.data) = :mes AND YEAR(f.data) = :ano";
		TypedQuery<BigDecimal> query = manager.createQuery(jpql, BigDecimal.class);
		query.setParameter("regional", regional);
		query.setParameter("mes", mes.getMonthValue());
		query.setParameter("ano", mes.getYear());

		BigDecimal total = query.getSingleResult();
		return (total == null) ? BigDecimal.ZERO : total;
	}

	public void salvarTodosFaturamento(List<Faturamento> faturamentos) {
		faturamentos.forEach(f -> manager.persist(f));
	}

	public Map<String, Mix> somaDoMesDeCadaFamiliaDeUmRepresentante(Representante representante, YearMonth mes) {
		String jpql = "SELECT new br.net.altcom.modelo.dao.Mix(f.produto.familia, SUM(f.faturamento)) FROM Faturamento f "
				+ "WHERE f.representante = :representante AND MONTH(f.data) = :mes AND YEAR(f.data) = :ano "
				+ "GROUP BY f.produto.familia";
		TypedQuery<Mix> query = manager.createQuery(jpql, Mix.class);
		query.setParameter("representante", representante);
		query.setParameter("mes", mes.getMonthValue());
		query.setParameter("ano", mes.getYear());

		Map<String, Mix> mixs = new HashMap<>();
		query.getResultList().forEach(m -> mixs.put(m.getFamilia().toLowerCase(), m));

		return mixs;
	}
}
