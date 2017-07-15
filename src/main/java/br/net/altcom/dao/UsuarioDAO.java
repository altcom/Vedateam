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

	public boolean verificarUsuarioNoBanco(Usuario usuario) {
		String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		query.setParameter("email", usuario.getEmail());
		try {
			Usuario usuarioDoBanco = query.getSingleResult();
			if (usuarioDoBanco.getSenha().equals(usuario.getSenha()))
				return true;

			return false;
		} catch (NoResultException e) {
			return false;
		}
	}
}
