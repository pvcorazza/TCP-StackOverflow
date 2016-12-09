package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.interfaces.QuestionDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.Management;
import domain.Question;
import domain.User;

public class QuestionDAO implements QuestionDAOInterface {

	private static final String TABLE = "question";

	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_USER = "id_user";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_TEXT = "text";
	private static final String COLUMN_DATE = "date_question";
	private static final String COLUMN_CLOSED = "closed";
	private static final String COLUMN_TAG1 = "tag1";
	private static final String COLUMN_TAG2 = "tag2";
	private static final String COLUMN_TAG3 = "tag3";
	private static final String COLUMN_TAG4 = "tag4";
	private static final String COLUMN_TAG5 = "tag5";
	public static final int TAG = 1;
	public static final int TITLE = 2;
	public static final int DATE = 3;
	public static final int AUTHOR = 4;

	@Override
	public int insert(Question question) throws DatabaseException,DatabaseConnectionException {

		int generatedKey = 0;

		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?,?,?,?,?,?,?)";

		try {

			Connection conn = new ConnectionFactory().getConnection();

			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, question.getIdAuthor());
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
			
		} catch (SQLException e) {
			new DatabaseException("databaseException.insert.error");
			e.printStackTrace();
		}

		return generatedKey;
	}

	@Override
	public void update(Question question) throws DatabaseException {
		try {
			
			Connection conn = new ConnectionFactory().getConnection();
			
			String sql = "UPDATE "+TABLE+" SET "+
					COLUMN_TEXT+"=? "+
					"WHERE "+
					COLUMN_ID+"=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, question.getText());
			stmt.setInt(2, question.getId());

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
	public void delete(Question question) throws DatabaseException {
		String sql = "DELETE FROM "+TABLE+
				" WHERE "+COLUMN_ID+"=?";
		
		try {
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, question.getId());
			stmt.executeUpdate();
			
			System.out.println("Row id deleted = "+question.getId());
			
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
	public ArrayList<Question> select(String searchText, int opcao) {

		ArrayList<Question> question = new ArrayList<Question>();

		Connection conn;
		try {
			conn = new ConnectionFactory().getConnection();


			String selectSQL;
			PreparedStatement stmt = null;
			ResultSet rs;

			switch (opcao) {

			case TAG:

				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND (tag1 = ? or tag2 = ? or tag3 = ? or tag4 = ? or tag5 = ?)";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);
				stmt.setString(2, searchText);
				stmt.setString(3, searchText);
				stmt.setString(4, searchText);
				stmt.setString(5, searchText);
				break;

			case TITLE:

				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND title = ?";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);

				break;

			case DATE:
				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND date_question = ?";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);
				System.out.println(selectSQL);
				break;

			case AUTHOR:

				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
				+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND username = ?";
				System.out.println(selectSQL);
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);
				break;

			}

			rs = stmt.executeQuery();
			
			User author;

			while (rs.next()) {
				author = new User(rs.getString("username"));
				question.add(new Question(rs.getInt(COLUMN_ID), rs.getInt(COLUMN_ID_USER), rs.getString(COLUMN_TITLE),
						rs.getString(COLUMN_TEXT), rs.getDate(COLUMN_DATE), rs.getBoolean(COLUMN_CLOSED),
						rs.getString(COLUMN_TAG1), rs.getString(COLUMN_TAG2), rs.getString(COLUMN_TAG3),
						rs.getString(COLUMN_TAG4), rs.getString(COLUMN_TAG5), author));
			}

			stmt.close();
			conn.close();

			return question;

		} catch (DatabaseConnectionException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Question select(int id) throws DatabaseException {
		Question question = null;
		Connection conn;
		try {
			conn = new ConnectionFactory().getConnection();


			String selectSQL;
			PreparedStatement stmt = null;
			ResultSet rs;
			
			selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
					+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND " + ConnectionFactory.QUESTION_TABLE + ".id = ?";
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
			
			User author;

			while (rs.next()) {
				author = new User(rs.getString("username"));
				question = new Question(rs.getInt(COLUMN_ID), rs.getInt(COLUMN_ID_USER), rs.getString(COLUMN_TITLE),
						rs.getString(COLUMN_TEXT), rs.getDate(COLUMN_DATE), rs.getBoolean(COLUMN_CLOSED),
						rs.getString(COLUMN_TAG1), rs.getString(COLUMN_TAG2), rs.getString(COLUMN_TAG3),
						rs.getString(COLUMN_TAG4), rs.getString(COLUMN_TAG5), author);
			}

			stmt.close();
			conn.close();

			return question;
			
		} catch (DatabaseConnectionException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
			
		return null;
	}

}
