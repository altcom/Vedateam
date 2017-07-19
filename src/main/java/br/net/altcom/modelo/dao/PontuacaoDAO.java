package br.net.altcom.modelo.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.net.altcom.dao.CRUD;
import br.net.altcom.modelo.entity.Pontuacao;
import br.net.altcom.modelo.entity.Representante;

@Stateless
public class PontuacaoDAO extends CRUD<Pontuacao> {

	public Pontuacao buscaPorMesDoRepresentante(Representante representante, String mes) {
		String jpql = "SELECT p FROM Pontuacao p WHERE p.codigo = :codigo AND p.mes LIKE :mes";
		TypedQuery<Pontuacao> query = manager.createQuery(jpql, Pontuacao.class);
		query.setParameter("codigo", representante.getCodigo());
		query.setParameter("mes", mes);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			Pontuacao pontuacao = new Pontuacao();
			pontuacao.setCodigo(representante.getCodigo());
			pontuacao.setMes(mes);
			return pontuacao;
		}
	}
}
