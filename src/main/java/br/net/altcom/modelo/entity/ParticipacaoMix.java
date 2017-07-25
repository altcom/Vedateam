package br.net.altcom.modelo.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class ParticipacaoMix {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String familia;
	private String mes;
	private BigDecimal porcentagem = BigDecimal.ZERO;

	@OneToOne
	private Representante representante;

	@Transient
	private BigDecimal metaMix = BigDecimal.ZERO;
	@Transient
	private BigDecimal progresso;
	@Transient
	private BigDecimal porcentagemProgresso = BigDecimal.ZERO;

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public BigDecimal getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(BigDecimal porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public BigDecimal getProgresso() {
		return progresso;
	}

	public void setProgresso(BigDecimal progresso) {
		this.progresso = progresso;
	}

	public BigDecimal getPorcentagemProgresso() {
		BigDecimal progressoPorcentagem = BigDecimal.ZERO;
		BigDecimal cem = new BigDecimal("100");

		if (metaMix.compareTo(new BigDecimal("0")) == 0) {
			if (metaMix.compareTo(progresso) > 0)
				progressoPorcentagem = cem;
		} else {
			progressoPorcentagem = progresso.divide(metaMix, 2, RoundingMode.DOWN).multiply(cem);
		}

		return progressoPorcentagem.min(cem);
	}

	public BigDecimal getMetaMix() {
		return metaMix;
	}

	public void setMetaMix(BigDecimal metaMix) {
		this.metaMix = metaMix;
	}
}
