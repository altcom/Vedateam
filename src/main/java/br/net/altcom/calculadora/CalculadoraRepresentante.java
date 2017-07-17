package br.net.altcom.calculadora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.YearMonth;

import javax.inject.Inject;

import br.net.altcom.dao.FaturamentoDAO;
import br.net.altcom.dao.MetaDAO;
import br.net.altcom.dao.exception.MetaNaoEncontradaException;
import br.net.altcom.modelo.entity.Meta;
import br.net.altcom.modelo.entity.Representante;

public class CalculadoraRepresentante implements Serializable {

	private static final long serialVersionUID = 1L;
	private Meta meta;
	private BigDecimal progressoMeta = BigDecimal.ZERO;
	private BigDecimal porcentagemDoHabilitador = BigDecimal.ZERO;

	@Inject
	private MetaDAO metaDAO;
	@Inject
	private FaturamentoDAO faturamentoDAO;
	private YearMonth data = YearMonth.of(2017, Month.APRIL);

	public void calcula(Representante representante) {
		try {
			buscaMeta(representante);
			buscaProgressoMeta(representante);
			calculaPorcentagemDoHabilitador();
		} catch (MetaNaoEncontradaException e) {
			System.out.println("Meta não encontrada");
		}
	}

	private void buscaMeta(Representante representante) throws MetaNaoEncontradaException {
		this.meta = metaDAO.buscaMetaDoRepresentante(representante, "4-2017");
	}

	private void buscaProgressoMeta(Representante representante) {
		progressoMeta = faturamentoDAO.somaDoMesDeUmRepresentante(representante, data);
	}

	private BigDecimal calculaPorcentagemDoHabilitador() {
		BigDecimal metaFaturamento = this.meta.getFaturamento();
		return porcentagemDoHabilitador = this.progressoMeta.divide(metaFaturamento, 2, RoundingMode.CEILING);
	}

	public BigDecimal getMeta() {
		return meta.getFaturamento();
	}

	public BigDecimal getProgressoMeta() {
		return progressoMeta;
	}

	public BigDecimal getPorcentagemDoHabilitador() {
		return porcentagemDoHabilitador;
	}
}
