package br.net.altcom.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.net.altcom.modelo.entity.Usuario;

@Named
@SessionScoped
public class UsuarioLogadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public void usuarioLogado(Usuario usuario) {
		this.usuario = usuario;
	}

	public String deslogar() {
		this.usuario = null;
		return "pretty:login";
	}

	public boolean isLogado() {
		return (usuario != null);
	}

	public String dashboard() {
		switch (usuario.getTipoDeAcesso()) {
		case ADMIN:
			return "admin.xhtml";
		case REPRESENTANTE:
			return "representante.xhtml";
		case REGIONAL:
			return "regional.xhtml";
		default:
			return "";
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
