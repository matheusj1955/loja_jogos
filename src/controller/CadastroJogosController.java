package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import application.Util;
import model.Jogo;
@Named
@ViewScoped
public class CadastroJogosController implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 9006004924308428897L;
	/**
	 * 
	 */
	
	
	private Jogo jogo;
	private List<Jogo> listaJogos;
	private int id = 0;

	private static Connection getConnection() {
		Connection conn = null;
		try {
			// registrando o driver do postgres
			Class.forName("org.postgresql.Driver");

			// estabelecendo a conexao com o banco de dados
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/lojabd", "topicos1", "123456");

			// obrigando a trabalhar com commit e rollback
			conn.setAutoCommit(false);

		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Erro ao registar a conexao.");
			e.printStackTrace();
		}

		return conn;
	}

	public void incluir() {
		Connection conn = getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("jogo ");
		sql.append("  (id, nome, valor) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?) ");
		PreparedStatement stat = null;
		try {
			Jogo jogo = getJogo();
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, jogo.getId());
			stat.setString(2, jogo.getNome());
			stat.setDouble(3, jogo.getValor());

			stat.execute();
			// efetivando a transacao
			conn.commit();
			limpar();
			Util.addInfoMessage("Inclusão realizada com sucesso.");

		} catch (SQLException e) {
			Util.addErrorMessage("Não é possivel fazer uma inclusão.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

	}

	public void limpar() {
		jogo = null;
		listaJogos = null;
	}

	public List<Jogo> getListaJogos() {
		if (listaJogos == null) {
			listaJogos = new ArrayList<Jogo>();

			Connection conn = getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  u.id, ");
			sql.append("  u.nome, ");
			sql.append("  u.valor, ");
			sql.append("FROM  ");
			sql.append("  jogo u ");
			sql.append("ORDER BY u.jogo ");

			PreparedStatement stat = null;
			try {
				
				stat = conn.prepareStatement(sql.toString());
				
				ResultSet rs = stat.executeQuery();
				
				while (rs.next()) {
					Jogo jogo = new Jogo();
					jogo.setId(rs.getInt("id"));
					jogo.setNome(rs.getString("jogo"));
//					jogo.setValor(rs.getDouble(valor);
					listaJogos.add(jogo);
				}
				
			} catch (SQLException e) {
				Util.addErrorMessage("Não foi possivel buscar os jogos.");
				e.printStackTrace();
			} finally {
				try {
					if (!stat.isClosed())
						stat.close();
				} catch (SQLException e) {
					System.out.println("Erro ao fechar o Statement");
					e.printStackTrace();
				}

				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					System.out.println("Erro a o fechar a conexao com o banco.");
					e.printStackTrace();
				}
			}

		}
		return listaJogos;
	}

	public Jogo getJogo() {
		if (jogo == null)
			jogo = new Jogo();
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

}
