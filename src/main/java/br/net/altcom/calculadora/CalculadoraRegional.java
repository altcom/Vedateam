package br.net.altcom.calculadora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;

import javax.inject.Inject;

import br.net.altcom.dao.MetaDAO;
import br.net.altcom.dao.exception.MetaNaoEncontradaException;
import br.net.altcom.modelo.entity.Meta;
import br.net.altcom.modelo.entity.Regional;

public class CalculadoraRegional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MetaDAO metaDAO;
	private YearMonth mes = YearMonth.of(2017, Month.APRIL);

	private Meta meta;

	public void calcula(Regional regional) {
		try {
			buscaMeta(regional);
		} catch (MetaNaoEncontradaException e) {
			System.out.println("Meta Do Regional Não Encontrada");
		}
	}

	private void buscaMeta(Regional regional) throws MetaNaoEncontradaException {
		meta = metaDAO.buscaMetaDoRegional(regional, "4-2017");
	}

	public BigDecimal getMeta() {
		return meta.getFaturamento();
	}
}
