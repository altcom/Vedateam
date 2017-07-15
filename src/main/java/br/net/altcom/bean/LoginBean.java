package br.net.altcom.bean;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.net.altcom.dao.UsuarioDAO;
import br.net.altcom.modelo.entity.Usuario;

@Model
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	public String login(){
		return "";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
