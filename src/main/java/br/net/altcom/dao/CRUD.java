package br.net.altcom.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class CRUD<T> {

	@Inject
	protected EntityManager manager;

	public void adiciona(T t) {
		this.manager.persist(t);
	}

	public void altera(T t) {
		this.manager.merge(t);
	}

	public void deleta(T t) {
		this.manager.remove(t);
	}

	public void busca(T t) {
		this.manager.find(t.getClass(), t);
	}
}
