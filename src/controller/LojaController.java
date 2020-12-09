package controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.LojaDAO;
import model.Loja;
import model.TipoLoja;

@Named
@ViewScoped
public class LojaController extends Controller<Loja> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4857247790977444736L;

	public LojaController() {
		super(new LojaDAO());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("lojaFlash");
		setEntity((Loja)flash.get("lojaFlash"));
	}

	@Override
	public Loja getEntity() {
		if (entity == null)
			entity = new Loja();
		return entity;
	}
	
	public TipoLoja[] getListaTipoLoja() {
		return TipoLoja.values();
	}

}
