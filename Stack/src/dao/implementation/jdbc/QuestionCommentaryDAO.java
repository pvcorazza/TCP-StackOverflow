package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.interfaces.QuestionCommentaryDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.AnswerCommentary;

/**
 * This interface handles all communications with the table that contains 
 * question commentaries
 *
 */
public class QuestionCommentaryDAO implements QuestionCommentaryDAOInterface {

	private static final String TABLE = "question_comment";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_AUTHOR = "id_user";
	private static final String COLUMN_ID_ANSWER= "id_question";
	private static final String COLUMN_TEXT_COMMENT = "text_comment";
	private static final String COLUMN_DATE = "date_question";
	
	
	@Override
	public int insert(AnswerCommentary commentary) throws DatabaseException {
		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?)";
		
		int generatedKey = -1;
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			System.out.println("Conexão aberta!");
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);	
			
			stmt.setInt(1,commentary.getId_author());
			stmt.setInt(2,commentary.getId_answer());
			stmt.setString(3,commentary.getText());
			stmt.setDate(4,new java.sql.Date(commentary.getDate().getTime()));
			
			System.out.println(stmt.toString());
			
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}

			System.out.println("Inserted record's ID: " + generatedKey);

			stmt.close();
			conn.close();
			
			
		} catch (DatabaseConnectionException e) {
			new DatabaseConnectionException("Erro ao conectar banco de dados");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível inserir uma resposta");
		}
		return generatedKey;
	}
	@Override
	public void update(AnswerCommentary commentary) throws DatabaseException {
try {
			
			Connection conn = new ConnectionFactory().getConnection();
			System.out.println("Conexão aberta!");
			
			String sql = "UPDATE "+TABLE+" SET "+
					COLUMN_TEXT_COMMENT+"=? "+
					"WHERE "+
					COLUMN_ID+"=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, commentary.getText());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possivel atualizar");
		}
		
		
	}
	@Override
	public void delete(AnswerCommentary commentary) throws DatabaseException {
		String sql = "DELETE FROM "+TABLE+
				" WHERE "+COLUMN_ID+"=?";
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, commentary.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível deletar a resposta");
		}
		
	}
	@Override
	public void delete(Integer id) throws DatabaseException {
		String sql = "DELETE FROM "+TABLE+
				" WHERE "+COLUMN_ID+"=?";
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível deletar a resposta");
		}
		
	}
	@Override
	public AnswerCommentary select(Integer id) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs= null;
		
		try {
			conn = new ConnectionFactory().getConnection();
			System.out.println("Conexão aberta!");
			
			String selectSQL = "SELECT * FROM " + TABLE + " WHERE id=?";
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				AnswerCommentary commentary = new AnswerCommentary();
				commentary.setId(rs.getInt(COLUMN_ID));
				commentary.setId_answer(rs.getInt(COLUMN_ID_ANSWER));
				commentary.setId_author(rs.getInt(COLUMN_ID_AUTHOR));
				commentary.setText(rs.getString(COLUMN_TEXT_COMMENT));
				commentary.setDate(rs.getDate(COLUMN_DATE));
				
				return commentary;
				
				
			}
			
		} catch (Exception e) {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return null;
	}

}
