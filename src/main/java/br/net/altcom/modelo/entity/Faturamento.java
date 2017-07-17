package br.net.altcom.modelo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Faturamento {

	@Id @GeneratedValue
	private Integer id;
	
	@OneToOne
	private Regional regional;
	@OneToOne
	private Representante representante;
	@OneToOne
	private Produto produto;
	@OneToOne
	private Cliente cliente;
}
