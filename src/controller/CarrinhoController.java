package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Session;
import application.Util;
import dao.VendaDAO;
import model.ItemVenda;
import model.Usuario;
import model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5545729119393303912L;
	private Venda venda;

	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		}
		// obtendo o carrinho da sessao
		Object obj = Session.getInstance().getAttribute("carrinho");
		if (obj != null)
			venda.setListaItemVenda((List<ItemVenda>) obj);
		
		return venda;
	}
	
	public void remover(ItemVenda itemVenda) {
		// vcs devem implementar
	}
	
	public void finalizar() {
		// obtendo o usuario da sessao
		Object obj = Session.getInstance().getAttribute("usuarioLogado");
		if (obj == null) {
			Util.addErrorMessage("Para finalizar a venda o usu�rio deve estar logado.");
			return;
		}
		
		// adicionando o usuario logado na venda
		getVenda().setUsuario((Usuario) obj);
		
		VendaDAO dao = new VendaDAO();
		try {
			dao.inserir(getVenda());
			Util.addInfoMessage("Inclus�o realizada com sucesso.");
			
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
			setVenda(null);
			
		} catch (Exception e) {
			Util.addErrorMessage("N�o � possivel fazer uma inclus�o.");
			e.printStackTrace();
		}
		
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}