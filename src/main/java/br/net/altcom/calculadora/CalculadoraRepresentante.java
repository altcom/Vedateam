package br.net.altcom.calculadora;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.net.altcom.dao.MetaDAO;
import br.net.altcom.dao.exception.MetaNaoEncontradaException;
import br.net.altcom.modelo.entity.Meta;
import br.net.altcom.modelo.entity.Representante;

public class CalculadoraRepresentante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MetaDAO metaDAO;

	private Meta meta;
	
	public void calcula(Representante representante){
		try {
			buscaMeta(representante);
		} catch (MetaNaoEncontradaException e) {
			System.out.println("Meta não encontrada");
		}
	}
	
	private void buscaMeta(Representante representante) throws MetaNaoEncontradaException{
		this.meta = metaDAO.buscaMetaDoRepresentante(representante, "4-2017");
	}
	
	public BigDecimal getMeta() {
		return meta.getFaturamento();
	}
}
