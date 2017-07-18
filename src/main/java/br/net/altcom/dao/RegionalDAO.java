package br.net.altcom.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Regional;

@Stateless
public class RegionalDAO extends CRUD<Regional> {

	public Regional buscaPeloNome(Regional regional) {
		String jpql = "SELECT r FROM Regional r WHERE r.nome LIKE :nome";
		TypedQuery<Regional> query = manager.createQuery(jpql, Regional.class);
		query.setParameter("nome", regional.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Regional buscaPeloCodigo(Integer codigo) {
		String jpql = "SELECT r FROM Regional r WHERE r.codigo = :codigo";
		TypedQuery<Regional> query = manager.createQuery(jpql, Regional.class);
		query.setParameter("codigo", codigo);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
