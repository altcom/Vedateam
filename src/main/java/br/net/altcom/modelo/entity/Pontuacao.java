package br.net.altcom.modelo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pontuacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer codigo;
	private String mes;
	private int pontuacao;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getMesNome() {
		String[] split = mes.split("-");
		switch (split[0]) {
		case "1":
			return "Janeiro";
		case "2":
			return "Fevereiro";
		case "3":
			return "Março";
		case "4":
			return "Abril";
		case "5":
			return "Maio";
		case "6":
			return "Junho";
		case "7":
			return "Julho";
		case "8":
			return "Agosto";
		case "9":
			return "Setembro";
		case "10":
			return "Outubro";
		case "11":
			return "Novembro";
		case "12":
			return "Dezembro";
		default:
			return "";
		}
	}
}
