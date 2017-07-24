package br.net.altcom.modelo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.net.altcom.dao.CRUD;
import br.net.altcom.modelo.entity.ParticipacaoMix;
import br.net.altcom.modelo.entity.Representante;

@Stateless
public class ParticipacaoMixDAO extends CRUD<ParticipacaoMix>{

	public List<ParticipacaoMix> buscaDoMesDeUmRepresentante(Representante representante) {
		String jpql = "SELECT p FROM ParticipacaoMix p WHERE p.representante = :representante";
		TypedQuery<ParticipacaoMix> query = manager.createQuery(jpql, ParticipacaoMix.class);
		query.setParameter("representante", representante);
		return query.getResultList();
	}

}
