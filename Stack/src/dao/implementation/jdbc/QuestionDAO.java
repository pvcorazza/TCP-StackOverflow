package dao.implementation.jdbc;

import java.sql.SQLException;

import dao.interfaces.QuestionDAOInterface;
import domain.Question;

public class QuestionDAO implements QuestionDAOInterface {
	
	private String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	private String user = "sql9146901";
	private String password = "tPcthmKbZt";
	
	private final String TABLE = "question";

	@Override
	public void insert(Question answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Question answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Question answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Question findByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
