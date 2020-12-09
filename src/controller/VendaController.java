package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Session;
import application.Util;
import dao.LojaDAO;
import model.ItemVenda;
import model.Loja;

@Named
@ViewScoped
public class VendaController implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3873794877995391906L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Loja> listaLoja;
	
	public void pesquisar() {
		LojaDAO dao = new LojaDAO();
		try {
			setListaLoja(dao.obterListaLojaComEstoque(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaLoja(null);
		}
	}
	
	public void addCarrinho(Loja loja) {
		try {
			LojaDAO dao = new LojaDAO();
			// obtendo os dados atuais da loja
			loja = dao.obterUm(loja);
			
			List<ItemVenda> listaItemVenda = null;
			Object obj = Session.getInstance().getAttribute("carrinho");
			
			if (obj == null) 
				listaItemVenda = new ArrayList<ItemVenda>();
			else 
				listaItemVenda = (List<ItemVenda>) obj;
			
			// montando o item de venda
			ItemVenda item = new ItemVenda();
			item.setLoja(loja);
			item.setPreco(loja.getPreco());
			listaItemVenda.add(item);
			
			// atualizando a sessao do carrinho de compras
			Session.getInstance().setAttribute("carrinho", listaItemVenda);
			
			Util.addInfoMessage("O produto: " + loja.getNome() + " foi adicionado ao carrinho.");
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problema ao adicionar o produto ao carrinho. Tente novamente.");
		}
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
		return listaLoja;
	}

	public void setListaLoja(List<Loja> listaLoja) {
		this.listaLoja = listaLoja;
	}

}
