package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Util;
import dao.LojaDAO;
import model.Loja;

@Named
@ViewScoped
public class ConsultaLojaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690653793669794787L;
	/**
	 * 
	 */
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Loja> listaLoja;
	
	public void novaLoja() {
		Util.redirect("loja.xhtml");
	}
	
	public void pesquisar() {
		LojaDAO dao = new LojaDAO();
		try {
			setListaLoja(dao.obterListaLoja(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaLoja(null);
		}
	}
	
	public void editar(Loja loja) {
		LojaDAO dao = new LojaDAO();
		Loja editarLoja = null;
		try {
			editarLoja = dao.obterUm(loja);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar a loja no banco de dados.");
			return;
		}
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("lojaFlash", editarLoja);
		novaLoja();
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Loja> getListaLoja() {
		if (listaLoja == null)
			listaLoja = new ArrayList<Loja>();
		return listaLoja;	}

	public void setListaLoja(List<Loja> listaLoja) {
		this.listaLoja = listaLoja;
	}

}