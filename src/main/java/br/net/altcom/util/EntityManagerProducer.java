package br.net.altcom.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {

	@PersistenceUnit
	private EntityManagerFactory managerFactory;

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return managerFactory.createEntityManager();
	}
	
	public void close(@Disposes EntityManager manager){
		manager.close();
	}
}
