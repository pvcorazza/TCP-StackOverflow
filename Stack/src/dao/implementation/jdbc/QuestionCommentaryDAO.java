package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.interfaces.QuestionCommentaryDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.QuestionCommentary;
import domain.User;

/**
 * This interface handles all communications with the table that contains 
 * question commentaries
 *
 */
public class QuestionCommentaryDAO implements QuestionCommentaryDAOInterface {

	private static final String TABLE = "question_comment";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_AUTHOR = "id_user";
	private static final String COLUMN_ID_QUESTION= "id_question";
	private static final String COLUMN_TEXT_COMMENT = "text_comment";
	private static final String COLUMN_DATE = "date_question";
	
	
	@Override
	public int insert(QuestionCommentary commentary) throws DatabaseException {
		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?)";
		
		int generatedKey = -1;
		try {
			
			Connection conn = new ConnectionFactory().getConnection();

			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);	
			
			stmt.setInt(1,commentary.getIdAuthor());
			stmt.setInt(2,commentary.getIdQuestion());
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
	public void update(QuestionCommentary commentary) throws DatabaseException {
try {
			
			Connection conn = new ConnectionFactory().getConnection();

			
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
	public void delete(QuestionCommentary commentary) throws DatabaseException {
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
	public QuestionCommentary select(int questionId) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs= null;
		
		try {
			conn = new ConnectionFactory().getConnection();
			
			String selectSQL = "SELECT * FROM " + TABLE + " WHERE id=?";
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, questionId);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				QuestionCommentary commentary = new QuestionCommentary();
				commentary.setId(rs.getInt(COLUMN_ID));
				commentary.setIdQuestion(rs.getInt(COLUMN_ID_QUESTION));
				commentary.setIdAuthor(rs.getInt(COLUMN_ID_AUTHOR));
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
	
	@Override
	public ArrayList<QuestionCommentary> selectAll(int idQuestion) throws DatabaseException {
		
		ArrayList<QuestionCommentary> questionCommentaries = new ArrayList<>();
		
		try {
			Connection conn = new ConnectionFactory().getConnection();

			
			String selectSQL = "SELECT * FROM " + TABLE + "," + ConnectionFactory.USER_TABLE
					+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND id_question = ?";
			PreparedStatement stmt = conn.prepareStatement(selectSQL);

			stmt.setInt(1, idQuestion);
			
			ResultSet rs = stmt.executeQuery();
			User author;
			
			while(rs.next()){
				
				author = new User(rs.getString("username"));
				QuestionCommentary commentary = new QuestionCommentary();
				commentary.setId(rs.getInt(COLUMN_ID));
				commentary.setIdQuestion(rs.getInt(COLUMN_ID_QUESTION));
				commentary.setIdAuthor(rs.getInt(COLUMN_ID_AUTHOR));
				commentary.setText(rs.getString(COLUMN_TEXT_COMMENT));
				commentary.setDate(rs.getDate(COLUMN_DATE));
				commentary.setAuthor(author);
				
				questionCommentaries.add(commentary);
			}
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível recuperar a resposta");
		}
		return questionCommentaries;
	}
	
	
	
	
	
	

}
