package br.net.altcom.dao;

import java.math.BigDecimal;
import java.time.YearMonth;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Faturamento;
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
}
