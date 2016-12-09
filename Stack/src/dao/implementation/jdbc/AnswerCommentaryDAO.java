package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.interfaces.AnswerCommentaryDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.AnswerCommentary;
import domain.User;
import ui.text.UIUtils;

public class AnswerCommentaryDAO implements AnswerCommentaryDAOInterface{
	
	private static final String TABLE = "answer_comment";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_AUTHOR = "id_user";
	private static final String COLUMN_ID_ANSWER= "id_answer";
	private static final String COLUMN_TEXT_COMMENT = "text_comment";
	private static final String COLUMN_DATE = "date_question";
	
	
	@Override
	public int insert(AnswerCommentary commentary) throws DatabaseException {
		
		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?)";
		
		int generatedKey = -1;
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);	
			
			stmt.setInt(1,commentary.getIdAuthor());
			stmt.setInt(2,commentary.getIdAnswer());
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
			throw new DatabaseException("N�o foi poss�vel inserir uma resposta");
		}
		return generatedKey;
	}
	
	
	@Override
	public void update(AnswerCommentary commentary) throws DatabaseException {
		
		
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			
			String sql = "UPDATE "+TABLE+" SET "+
					COLUMN_TEXT_COMMENT+"=? "+
					"WHERE "+
					COLUMN_ID+"=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, commentary.getText());
			stmt.setInt(2, commentary.getId());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(UIUtils.INSTANCE.getTextManager().getText("databaseException.update.error"));
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
			throw new DatabaseException("N�o foi poss�vel deletar a resposta");
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
			throw new DatabaseException("N�o foi poss�vel deletar a resposta");
		}
		
	}
	@Override
	public AnswerCommentary select(int idAnswer) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs= null;
		
		try {
			conn = new ConnectionFactory().getConnection();

			
			String selectSQL = "SELECT * FROM " + TABLE + " WHERE id=?";
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, idAnswer);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				AnswerCommentary commentary = new AnswerCommentary();
				commentary.setId(rs.getInt(COLUMN_ID));
				commentary.setIdAnswer(rs.getInt(COLUMN_ID_ANSWER));
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
	public ArrayList<AnswerCommentary> selectALL(int idAnswer) throws DatabaseException {
		ArrayList<AnswerCommentary> answerCommentaries = new ArrayList<>();
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			
			String selectSQL = "SELECT * FROM " + TABLE + "," + ConnectionFactory.USER_TABLE
					+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND id_answer = ?";
			PreparedStatement stmt = conn.prepareStatement(selectSQL);

			stmt.setInt(1, idAnswer);
			
			ResultSet rs = stmt.executeQuery();
			User author;
			
			while(rs.next()){
				
				author = new User(rs.getString("username"));
				AnswerCommentary commentary = new AnswerCommentary();
				commentary.setId(rs.getInt(COLUMN_ID));
				commentary.setIdAnswer(rs.getInt(COLUMN_ID_ANSWER));
				commentary.setIdAuthor(rs.getInt(COLUMN_ID_AUTHOR));
				commentary.setText(rs.getString(COLUMN_TEXT_COMMENT));
				commentary.setDate(rs.getDate(COLUMN_DATE));
				commentary.setAuthor(author);
				
				answerCommentaries.add(commentary);
			}
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("N�o foi poss�vel recuperar a resposta");
		}
		return answerCommentaries;
	}

	
	

}
