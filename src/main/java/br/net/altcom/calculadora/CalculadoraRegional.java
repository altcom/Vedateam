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
import br.net.altcom.modelo.entity.Regional;

public class CalculadoraRegional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MetaDAO metaDAO;
	@Inject
	private FaturamentoDAO faturamentoDAO;

	private YearMonth mes = YearMonth.of(2017, Month.APRIL);

	private Meta meta;

	private BigDecimal progressoMeta = BigDecimal.ZERO;

	private BigDecimal porcentagemDoHabilitador;

	public void calcula(Regional regional) {
		try {
			buscaMeta(regional);
			buscaProgressoMeta(regional);

			calculaPorcentagemDoHabilitador();
		} catch (MetaNaoEncontradaException e) {
			System.out.println("Meta Do Regional Não Encontrada");
		}
	}

	private void buscaMeta(Regional regional) throws MetaNaoEncontradaException {
		meta = metaDAO.buscaMetaDoRegional(regional, "4-2017");
	}

	private void buscaProgressoMeta(Regional regional) {
		progressoMeta = faturamentoDAO.somaDoMesDeUmRegional(regional, mes);
	}

	private void calculaPorcentagemDoHabilitador() {
		BigDecimal metaFaturamento = this.meta.getFaturamento();
		porcentagemDoHabilitador = this.progressoMeta.divide(metaFaturamento, 2, RoundingMode.CEILING);
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
