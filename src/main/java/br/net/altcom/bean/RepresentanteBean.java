package br.net.altcom.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.net.altcom.dao.RepresentanteDAO;
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

	public Representante getRepresentante() {
		if (this.representante == null) {
			Representante representante = new Representante();
			representante.setCodigo(usuarioLogado.getUsuario().getCodigo());
			this.representante = representanteDAO.buscaPeloCodigo(representante);
		}

		return this.representante;
	}
}
