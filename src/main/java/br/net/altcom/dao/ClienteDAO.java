package br.net.altcom.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Cliente;

@Stateless
public class ClienteDAO extends CRUD<Cliente> {

	public Cliente buscaPeloNome(Cliente cliente) {
		String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome";
		TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", cliente.getNome());
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
