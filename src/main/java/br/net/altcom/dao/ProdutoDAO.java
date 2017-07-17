package br.net.altcom.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Produto;

@Stateless
public class ProdutoDAO extends CRUD<Produto> {

	public Produto buscaPeloCodigo(Produto produto) {
		String jpql = "SELECT p FROM Produto p WHERE p.codigo = :codigo";
		TypedQuery<Produto> query = manager.createQuery(jpql, Produto.class);
		query.setParameter("codigo", produto.getCodigo());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
