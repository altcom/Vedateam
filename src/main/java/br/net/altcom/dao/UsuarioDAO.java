package br.net.altcom.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.modelo.entity.Usuario;

@Stateless
public class UsuarioDAO {

	@Inject
	private EntityManager manager;

	public Usuario buscaPeloEmail(Usuario usuario) {
		String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		query.setParameter("email", usuario.getEmail());

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
