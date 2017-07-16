package br.net.altcom.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.net.altcom.modelo.entity.Usuario;

@Named @SessionScoped
public class UsuarioLogadoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public void usuarioLogado(Usuario usuario){
		this.usuario = usuario;
	}
	
	public void deslogar(){
		this.usuario = null;
	}
	
	public boolean isLogado(){
		return (usuario != null);
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
