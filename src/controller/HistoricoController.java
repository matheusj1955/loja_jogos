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
public class HistoricoController implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8291987367815528441L;
	
	private List<Venda> listaVenda;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			Object obj = Session.getInstance().getAttribute("usuarioLogado");
			
			if (obj != null)
				try {
					listaVenda = dao.obterTodos((Usuario) obj);
				} catch (Exception e) {
					Util.addErrorMessage("Não foi possível obter o histórico de vendas.");
					listaVenda = new ArrayList<Venda>();
				}
		}
		return listaVenda;
	}
	
	public void detalhes(Venda venda) {
		
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
}