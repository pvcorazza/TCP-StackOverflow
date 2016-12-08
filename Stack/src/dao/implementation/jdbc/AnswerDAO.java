package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.interfaces.AnswerDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.Answer;
import domain.User;


public class AnswerDAO implements AnswerDAOInterface {
	
	private static final String TABLE = "answer";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_AUTHOR = "id_author";
	private static final String COLUMN_ID_QUESTION= "id_question";
	private static final String COLUMN_TEXT_ANSWER = "text_answer";
	private static final String COLUMN_DATE = "date_answer";
	private static final String COLUMN_BEST_ANSWER = "best_answer";
	
	
	@Override
	public int insert(Answer answer) throws DatabaseException {
		
		int generatedKey = -1;
		
		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?,?)";
		
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);			
			
			stmt.setInt(1, answer.getIdAuthor());
			stmt.setInt(2, answer.getIdQuestion());
			stmt.setString(3, answer.getText());
			stmt.setDate(4, new java.sql.Date(answer.getDate().getTime()));
			stmt.setBoolean(5, answer.getBestAnswer());
			
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
	public void update(Answer answer) throws DatabaseException {
		
		try {
			
			Connection conn = new ConnectionFactory().getConnection();

			
			String sql = "UPDATE "+TABLE+" SET "+
					COLUMN_TEXT_ANSWER+"=?,"+
					COLUMN_BEST_ANSWER+"=? "+
					"WHERE "+
					COLUMN_ID+"=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, answer.getText());
			stmt.setBoolean(2, answer.getBestAnswer());
			stmt.setInt(3, answer.getId());

			System.out.println(stmt.toString());
			stmt.executeUpdate();

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possivel atualizar");
		}
		
		
	}
	@Override
	public void delete(Answer answer) throws DatabaseException {
		String sql = "DELETE FROM "+TABLE+
				" WHERE "+COLUMN_ID+"=?";
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, answer.getId());
			stmt.executeUpdate();
			
			System.out.println("Row id deleted = "+answer.getId());
			
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
			
			System.out.println("Row id deleted = "+id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível deletar a resposta");
		}
		
	}
	@Override
	public Answer select(Integer answerId) throws DatabaseException {
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs= null;
		
		try {
			conn = new ConnectionFactory().getConnection();

			
			String selectSQL = "SELECT * FROM " + TABLE + " WHERE id=?";
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, answerId);
			rs= stmt.executeQuery();
			
			if(rs.next()){
				Answer answer = new Answer();
				answer.setId(rs.getInt(COLUMN_ID));
				answer.setIdAuthor(rs.getInt(COLUMN_ID_AUTHOR));
				answer.setIdQuestion(rs.getInt(COLUMN_ID_QUESTION));
				answer.setText(rs.getString(COLUMN_TEXT_ANSWER));
				return answer;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível recuperar a resposta");
		}
		finally {
			try{
				conn.close();
				stmt.close();
				rs.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		return null;
	}

	@Override
	public ArrayList<Answer> selectAll(int questionId) throws DatabaseException {
		ArrayList<Answer> answers = new ArrayList<>();
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			
			String selectSQL = "SELECT * FROM " + TABLE + "," + ConnectionFactory.USER_TABLE
					+ " WHERE id_author = " + ConnectionFactory.USER_TABLE + ".id AND id_question = ?";
			PreparedStatement stmt = conn.prepareStatement(selectSQL);

			stmt.setInt(1, questionId);
			
			ResultSet rs = stmt.executeQuery();
			User author;
			
			while(rs.next()){
				
				author = new User(rs.getString("username"));
				Answer answer = new Answer();
				answer.setId(rs.getInt(COLUMN_ID));
				answer.setIdAuthor(rs.getInt(COLUMN_ID_AUTHOR));
				answer.setIdQuestion(rs.getInt(COLUMN_ID_QUESTION));
				answer.setText(rs.getString(COLUMN_TEXT_ANSWER));
				answer.setDate(rs.getDate(COLUMN_DATE));
				answer.setBestAnswer(rs.getBoolean(COLUMN_BEST_ANSWER));
				answer.setAuthor(author);
				
				

				answers.add(answer);
			}
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Não foi possível recuperar a resposta");
		}
		return answers;
	}
	



	
}
