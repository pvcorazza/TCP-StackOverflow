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

	@Override
	public int insert(Question question) throws DatabaseException {

		int generatedKey = 0;

		String sqlInsert = "insert into " + TABLE + " VALUES (NULL,?,?,?,?,?,?,?,?,?,?)";

		try {

			Connection conn = new ConnectionFactory().getConnection();
			System.out.println("Conexão aberta!");

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

		} catch (SQLException e) {
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
	public ArrayList<Question> select(String searchText, int opcao) {

		ArrayList<Question> question = new ArrayList<Question>();

		Connection conn;
		try {
			conn = new ConnectionFactory().getConnection();

			System.out.println("Conexão aberta!");

			String selectSQL;
			PreparedStatement stmt = null;
			ResultSet rs;

			switch (opcao) {

			case Management.TAG:

				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND (tag1 = ? or tag2 = ? or tag3 = ? or tag4 = ? or tag5 = ?)";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);
				stmt.setString(2, searchText);
				stmt.setString(3, searchText);
				stmt.setString(4, searchText);
				stmt.setString(5, searchText);
				break;

			case Management.TITLE:

				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND title = ?";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);

				break;

			case Management.DATE:
				selectSQL = "SELECT * FROM " + ConnectionFactory.QUESTION_TABLE + "," + ConnectionFactory.USER_TABLE
						+ " WHERE id_user = " + ConnectionFactory.USER_TABLE + ".id AND date_question = ?";
				stmt = conn.prepareStatement(selectSQL);
				stmt.setString(1, searchText);
				System.out.println(selectSQL);
				break;

			case Management.AUTHOR:

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

			System.out.println("Conexão aberta!");

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
