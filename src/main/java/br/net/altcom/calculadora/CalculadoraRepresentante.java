package br.net.altcom.calculadora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import br.net.altcom.dao.FaturamentoDAO;
import br.net.altcom.dao.MetaDAO;
import br.net.altcom.dao.exception.MetaNaoEncontradaException;
import br.net.altcom.modelo.dao.Mix;
import br.net.altcom.modelo.dao.ParticipacaoMixDAO;
import br.net.altcom.modelo.entity.Cliente;
import br.net.altcom.modelo.entity.Meta;
import br.net.altcom.modelo.entity.ParticipacaoMix;
import br.net.altcom.modelo.entity.Representante;

public class CalculadoraRepresentante implements Serializable {

	private static final long serialVersionUID = 1L;
	private Meta meta = new Meta();

	private BigDecimal progressoMeta = BigDecimal.ZERO;
	private BigDecimal porcentagemDoHabilitador = BigDecimal.ZERO;
	private int pontosHabilitador;
	private int pontosClientesNovo;

	private Set<Cliente> clientesAtivos = new HashSet<>();
	private Set<Cliente> clientesNaBase = new HashSet<>();
	private Set<Cliente> clientesNovo = new HashSet<>();
	private List<ParticipacaoMix> participacoes;

	@Inject
	private MetaDAO metaDAO;
	@Inject
	private FaturamentoDAO faturamentoDAO;
	@Inject
	private ParticipacaoMixDAO participacaoDAO;

	private YearMonth data = YearMonth.of(2017, Month.APRIL);

	public void calcula(Representante representante) {
		try {
			buscaMeta(representante);
			buscaProgressoMeta(representante);
			buscaParticipacaoMix(representante, data);

			calcularPontosDoHabilitador();
			calcularPontosClienteNovos(representante);
		} catch (MetaNaoEncontradaException e) {
			System.out.println("Meta não encontrada");
		}
	}

	private void calcularPontosDoHabilitador() {
		calculaPorcentagemDoHabilitador();
		BigDecimal porcentagem = this.porcentagemDoHabilitador.multiply(new BigDecimal("100"));
		pontosHabilitador = TabelaDePontos.pontosHabilitadorRepresentante(porcentagem.doubleValue());
	}

	private void calcularPontosClienteNovos(Representante representante) {
		pegaClientesNovos(representante);
		pontosClientesNovo = TabelaDePontos.pontosClientesNovo(this.clientesNovo.size());
	}

	public int getTotalDePontosAcumulados() {
		return this.pontosHabilitador + this.pontosClientesNovo;
	}

	private void buscaMeta(Representante representante) throws MetaNaoEncontradaException {
		this.meta = metaDAO.buscaMetaDoRepresentante(representante, "4-2017");
	}

	private void buscaProgressoMeta(Representante representante) {
		progressoMeta = faturamentoDAO.somaDoMesDeUmRepresentante(representante, data);
	}

	private void buscaClientesAtivos(Representante representante) {
		clientesAtivos = faturamentoDAO.buscarClientesAtivosDoMesDeUmRepresentante(representante, data);
	}

	private void buscaClientesNaBase(Representante representante) {
		clientesNaBase = faturamentoDAO.buscaClientesEntreMes(representante, data.minusMonths(6), data.minusMonths(1));
	}

	private Map<String, Mix> buscaTotalDeCadaFamilia(Representante representante, YearMonth mes) {
		return faturamentoDAO.somaDoMesDeCadaFamiliaDeUmRepresentante(representante, mes);
	}

	private void buscaParticipacaoMix(Representante representante, YearMonth mes) {
		Map<String, Mix> familias = buscaTotalDeCadaFamilia(representante, mes);
		participacoes = participacaoDAO.buscaDoMesDeUmRepresentante(representante);

		for (ParticipacaoMix participacao : participacoes) {
			Mix mix = familias.get(participacao.getFamilia().toLowerCase());
			
			if (mix == null) {
				participacao.setProgresso(new BigDecimal("0"));
			} else {
				participacao.setProgresso(mix.getProgresso());
			}
			BigDecimal porcentagem = participacao.getPorcentagem().divide(new BigDecimal("100"));
			BigDecimal metaMix = meta.getFaturamento().multiply(porcentagem).setScale(2, RoundingMode.CEILING);
			participacao.setMetaMix(metaMix);
		}
	}

	private void pegaClientesNovos(Representante representante) {
		buscaClientesAtivos(representante);
		buscaClientesNaBase(representante);
		this.clientesNovo = new HashSet<>(this.clientesAtivos);
		clientesNovo.removeAll(this.clientesNaBase);
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

	public int getPontosHabilitador() {
		return pontosHabilitador;
	}

	public int getPontosClientesNovo() {
		return pontosClientesNovo;
	}

	public Set<Cliente> getClientesAtivos() {
		return clientesAtivos;
	}

	public Set<Cliente> getClientesNaBase() {
		return clientesNaBase;
	}

	public Set<Cliente> getClientesNovo() {
		return clientesNovo;
	}
	
	public List<ParticipacaoMix> getParticipacoes() {
		return participacoes;
	}
}
