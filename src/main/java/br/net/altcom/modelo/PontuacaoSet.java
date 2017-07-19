package br.net.altcom.modelo;

import br.net.altcom.modelo.entity.Pontuacao;

public class PontuacaoSet {

	private String nome;
	private Pontuacao primeiro;
	private Pontuacao segundo;
	private Pontuacao terceiro;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pontuacao getPrimeiro() {
		return primeiro;
	}

	public void setPrimeiro(Pontuacao primeiro) {
		this.primeiro = primeiro;
	}

	public Pontuacao getSegundo() {
		return segundo;
	}

	public void setSegundo(Pontuacao segundo) {
		this.segundo = segundo;
	}

	public Pontuacao getTerceiro() {
		return terceiro;
	}

	public void setTerceiro(Pontuacao terceiro) {
		this.terceiro = terceiro;
	}
}
