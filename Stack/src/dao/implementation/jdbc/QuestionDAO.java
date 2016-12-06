package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import dao.interfaces.QuestionDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.Question;


public class QuestionDAO implements QuestionDAOInterface {
	
	private static final String TABLE = "question";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_USER = "id_user";
	private static final String COLUMN_TITLE= "title";
	private static final String COLUMN_TEXT = "text";
	private static final String COLUMN_DATE = "date_question";
	private static final String COLUMN_TAG1 = "tag1";
	private static final String COLUMN_TAG2 = "tag2";
	private static final String COLUMN_TAG3 = "tag3";
	private static final String COLUMN_TAG4 = "tag4";
	private static final String COLUMN_TAG5 = "tag5";
	
	@Override
	public int insert(Question question) throws DatabaseException {
		
		int generatedKey = 0;
		
		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?,?,?,?,?,?,?)";
		
	
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			System.out.println("Conex�o aberta!");
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			
	
			stmt.setInt(1, question.getId_author());
			stmt.setString(2, question.getTitle());
			stmt.setString(3, question.getText());
			
			
			
			stmt.setDate(4, new java.sql.Date(question.getDate().getTime()));
			stmt.setBoolean(5, question.getClosed());
			stmt.setString(6, question.getTag1());
			stmt.setString(7, question.getTag2());
			stmt.setString(8, question.getTag3());
			stmt.setString(9, question.getTag4());
			stmt.setString(10, question.getTag5());
		
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
		}
		
		return generatedKey;
	}
	
	@Override
	public void update(Question question) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Question question) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Question select(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Question select(Integer id, Integer id_user) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Question select(Integer id, Integer id_user, String tag1, String tag2, String tag3, String tag4, String tag5)
			throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Question select(String tag1, String tag2, String tag3, String tag4, String tag5) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	



}
