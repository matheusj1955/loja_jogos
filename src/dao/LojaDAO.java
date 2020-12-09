package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Util;
import model.Loja;
import model.Perfil;
import model.Sexo;
import model.TipoLoja;

@SuppressWarnings("unused")
public class LojaDAO implements DAO<Loja> {

	@Override
	public void inserir(Loja obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("loja ");
		sql.append("  (nome, descricao, preco, estoque, tipo_loja) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoLoja() == null ? null : obj.getTipoLoja().getId()));

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

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

		if (exception != null)
			throw exception;
		
	}

	@Override
	public void alterar(Loja obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE loja SET ");
		sql.append("  nome = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  preco = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  tipo_loja = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoLoja() == null ? null : obj.getTipoLoja().getId()));
			stat.setInt(6, obj.getId());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

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

		if (exception != null)
			throw exception;

		
	}

	@Override
	public void excluir(Loja obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM loja WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

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

		if (exception != null)
			throw exception;
	
	}

	@Override
	public List<Loja> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Loja> listaLoja = new ArrayList<Loja>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_loja ");
		sql.append("FROM  ");
		sql.append("  loja m ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Loja loja = new Loja();
				loja.setId(rs.getInt("id"));
				loja.setNome(rs.getString("nome"));
				loja.setDescricao(rs.getString("descricao"));
				loja.setPreco(rs.getDouble("preco"));
				loja.setEstoque(rs.getInt("estoque"));
				loja.setTipoLoja(TipoLoja.valueOf(rs.getInt("tipo_loja")));

				listaLoja.add(loja);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do loja.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em LojaDAO.");
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

		if (exception != null)
			throw exception;

		return listaLoja;
	}

	@Override
	public Loja obterUm(Loja obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Loja loja = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_loja ");
		sql.append("FROM  ");
		sql.append("  loja m ");
		sql.append("WHERE m.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				loja = new Loja();
				loja.setId(rs.getInt("id"));
				loja.setNome(rs.getString("nome"));
				loja.setDescricao(rs.getString("descricao"));
				loja.setPreco(rs.getDouble("preco"));
				loja.setEstoque(rs.getInt("estoque"));
				loja.setTipoLoja(TipoLoja.valueOf(rs.getInt("tipo_loja")));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do loja.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em LojaDAO.");
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

		if (exception != null)
			throw exception;

		return loja;
	}

	public List<Loja> obterListaLoja(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Loja> listaLoja = new ArrayList<Loja>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_loja ");
		sql.append("FROM  ");
		sql.append("  loja m ");
		sql.append("WHERE ");
		sql.append("  upper(m.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(m.descricao) LIKE upper( ? ) ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%"+ filtro +"%" : "%");
			stat.setString(2, tipo == 2 ? "%"+ filtro +"%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Loja loja = new Loja();
				loja.setId(rs.getInt("id"));
				loja.setNome(rs.getString("nome"));
				loja.setDescricao(rs.getString("descricao"));
				loja.setPreco(rs.getDouble("preco"));
				loja.setEstoque(rs.getInt("estoque"));
				loja.setTipoLoja(TipoLoja.valueOf(rs.getInt("tipo_loja")));

				listaLoja.add(loja);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do loja.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em LojaDAO.");
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

		if (exception != null)
			throw exception;

		return listaLoja;
	}
	
	public List<Loja> obterListaLojaComEstoque(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Loja> listaLoja = new ArrayList<Loja>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  m.id, ");
		sql.append("  m.nome, ");
		sql.append("  m.descricao, ");
		sql.append("  m.preco, ");
		sql.append("  m.estoque, ");
		sql.append("  m.tipo_loja ");
		sql.append("FROM  ");
		sql.append("  loja m ");
		sql.append("WHERE ");
		sql.append("  upper(m.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(m.descricao) LIKE upper( ? ) ");
		sql.append("  AND m.estoque > 0 ");
		sql.append("ORDER BY m.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%"+ filtro +"%" : "%");
			stat.setString(2, tipo == 2 ? "%"+ filtro +"%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Loja loja = new Loja();
				loja.setId(rs.getInt("id"));
				loja.setNome(rs.getString("nome"));
				loja.setDescricao(rs.getString("descricao"));
				loja.setPreco(rs.getDouble("preco"));
				loja.setEstoque(rs.getInt("estoque"));
				loja.setTipoLoja(TipoLoja.valueOf(rs.getInt("tipo_loja")));

				listaLoja.add(loja);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do loja.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em LojaDAO.");
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

		if (exception != null)
			throw exception;

		return listaLoja;
	}
}