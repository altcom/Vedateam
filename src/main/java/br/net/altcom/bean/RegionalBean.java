package br.net.altcom.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.net.altcom.dao.RegionalDAO;
import br.net.altcom.modelo.entity.Regional;

@Named
@ViewScoped
public class RegionalBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Regional regional;

	@Inject
	private UsuarioLogadoBean logadoBean;
	@Inject
	private RegionalDAO regionalDAO;

	public Regional getRegional() {
		if (this.regional == null) {
			this.regional = regionalDAO.buscaPeloCodigo(logadoBean.getUsuario().getCodigo());
		}

		return this.regional;
	}
}
