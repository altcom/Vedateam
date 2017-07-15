package br.net.altcom.bean;

import javax.enterprise.inject.Model;

import br.net.altcom.modelo.entity.Usuario;

@Model
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	public String login(){
		return "";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
