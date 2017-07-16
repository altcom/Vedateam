package br.net.altcom.modelo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.net.altcom.modelo.TipoDeAcesso;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer codigo;
	private String email;
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private TipoDeAcesso tipoDeAcesso;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public TipoDeAcesso getTipoDeAcesso() {
		return tipoDeAcesso;
	}
	
	public void setTipoDeAcesso(TipoDeAcesso tipoDeAcesso) {
		this.tipoDeAcesso = tipoDeAcesso;
	}
}
