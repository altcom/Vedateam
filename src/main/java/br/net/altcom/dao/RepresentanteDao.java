package br.net.altcom.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Representante;

@Stateless
public class RepresentanteDAO extends CRUD<Representante> {

	public Representante buscaPeloCodigo(Representante representante) {
		String jpql = "SELECT r FROM Representante r WHERE r.codigo = :codigo";
		TypedQuery<Representante> query = manager.createQuery(jpql, Representante.class);
		query.setParameter("codigo", representante.getCodigo());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
