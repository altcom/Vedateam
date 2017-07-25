package br.net.altcom.modelo.dao;

import java.math.BigDecimal;

public class Mix {

	private String familia;
	private BigDecimal progresso;

	public Mix(String familia, BigDecimal progresso) {
		this.familia = familia;
		this.progresso = progresso;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public BigDecimal getProgresso() {
		return progresso;
	}

	public void setProgresso(BigDecimal progresso) {
		this.progresso = progresso;
	}
}
