//package bd;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class Banco {
//
//	public static void main(String[] args) {
//		Connection conn = getConnection();
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("INSERT INTO ");
//		sql.append("jogo ");
//		sql.append("  (id, nome, valor) ");
//		sql.append("VALUES ");
//		sql.append("  (?, ?, ?) ");
//		
//		try {
//			PreparedStatement stat = conn.prepareStatement(sql.toString());
//			stat.setInt(1, 2);
//			stat.setString(2, "CS-GO");
//			stat.setDouble(3, 20);
//			
//			stat.execute();
//			// efetivando a transacao
//			conn.commit();
//			System.out.println("Insert realizado com sucesso.");
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			// cancelando a transacao
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				System.out.println("Erro ao realizar o rollback.");
//				e1.printStackTrace();
//			}
//		}
//		
//		
//	}
//
//	private static Connection getConnection() {
//		Connection conn = null;
//		try {
//			// registrando o driver do postgres
//			Class.forName("org.postgresql.Driver");
//			
//			// estabelecendo a conexao com o banco de dados
//			conn = DriverManager.
//				getConnection("jdbc:postgresql://127.0.0.1:5432/lojabd", 
//						"topicos1", "123456");
//			
//			// obrigando a trabalhar com commit e rollback
//			conn.setAutoCommit(false);
//			
//		} catch (SQLException e) {
//			System.out.println("Erro ao conectar ao banco de dados.");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("Erro ao registar a conexao.");
//			e.printStackTrace();
//		}
//		
//		return conn;
//	}
//
//}
