package dao.implementation.jdbc;

import java.sql.SQLException;

import dao.interfaces.AnswerDAOInterface;
import domain.Answer;

public class AnswerDAO implements AnswerDAOInterface {
	
	private String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	private String user = "sql9146901";
	private String password = "tPcthmKbZt";
	private final String TABLE = "answer";

	@Override
	public void insert(Answer answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Answer answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Answer answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Answer findByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
