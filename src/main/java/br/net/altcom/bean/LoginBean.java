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

	public String login() {
		Usuario usuarioDoBanco = usuarioDAO.buscaPeloEmail(usuario);
		
		if (usuarioDoBanco == null){
			System.out.println("Usuario não encontrado");			
			return "";
		}
		
		if (usuarioDoBanco.getSenha().equals(this.usuario.getSenha())){
			System.out.println("Usuario Logado");
			return "";
		}
		
		System.out.println("Senha inválida");
		return "";
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
