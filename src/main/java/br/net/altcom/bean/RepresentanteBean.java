package br.net.altcom.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.net.altcom.calculadora.CalculadoraRepresentante;
import br.net.altcom.dao.RepresentanteDAO;
import br.net.altcom.modelo.PontuacaoSet;
import br.net.altcom.modelo.dao.PontuacaoDAO;
import br.net.altcom.modelo.entity.Representante;

@Named
@ViewScoped
public class RepresentanteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Representante representante;

	@Inject
	private UsuarioLogadoBean usuarioLogado;
	@Inject
	private RepresentanteDAO representanteDAO;
	@Inject
	private CalculadoraRepresentante calculadora;
	@Inject
	private PontuacaoDAO pontuacaoDAO;

	@PostConstruct
	public void setup() {
		calculadora.calcula(getRepresentante());
	}

	public Representante getRepresentante() {
		if (this.representante == null) {
			Representante representante = new Representante();
			representante.setCodigo(usuarioLogado.getUsuario().getCodigo());
			this.representante = representanteDAO.buscaPeloCodigo(representante);
		}

		return this.representante;
	}

	public List<PontuacaoSet> getPontuacao() {
		List<PontuacaoSet> pontuacaos = new ArrayList<>();
		
		PontuacaoSet pontuacao1Set = new PontuacaoSet();
		pontuacao1Set.setNome("Primeiro Set");
		pontuacao1Set.setPrimeiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "2-2017"));
		pontuacao1Set.setSegundo(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "3-2017"));
		pontuacao1Set.setTerceiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "4-2017"));
		
		PontuacaoSet pontuacao2Set = new PontuacaoSet();
		pontuacao2Set.setNome("Segundo Set");
		pontuacao2Set.setPrimeiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "5-2017"));
		pontuacao2Set.setSegundo(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "6-2017"));
		pontuacao2Set.setTerceiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "7-2017"));
		
		PontuacaoSet pontuacao3Set = new PontuacaoSet();
		pontuacao3Set.setNome("Terceiro Set");
		pontuacao3Set.setPrimeiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "8-2017"));
		pontuacao3Set.setSegundo(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "9-2017"));
		pontuacao3Set.setTerceiro(pontuacaoDAO.buscaPorMesDoRepresentante(getRepresentante(), "10-2017"));
		
		pontuacaos.add(pontuacao1Set);
		pontuacaos.add(pontuacao2Set);
		pontuacaos.add(pontuacao3Set);
		
		return pontuacaos;
	}

	public CalculadoraRepresentante getCalculadora() {
		return calculadora;
	}
}
