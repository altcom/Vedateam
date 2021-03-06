package br.net.altcom.dao;

import java.time.YearMonth;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.dao.exception.MetaNaoEncontradaException;
import br.net.altcom.modelo.entity.Meta;

@Stateless
public class MetaDAO extends CRUD<Meta> {

	public Meta buscaMetaDoMes(Integer codigo, YearMonth mes) throws MetaNaoEncontradaException {
		String jpql = "SELECT m FROM Meta m WHERE m.codigo = :codigo AND m.mes LIKE :mes";
		TypedQuery<Meta> query = manager.createQuery(jpql, Meta.class);
		query.setParameter("codigo", codigo);
		query.setParameter("mes", mes.getMonthValue() + "/" + mes.getYear());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new MetaNaoEncontradaException();
		}
	}
}
