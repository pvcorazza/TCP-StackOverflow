package dao.implementation.jdbc;

import java.sql.SQLException;

import dao.interfaces.AnswerDAOInterface;
import database.exception.DatabaseException;
import domain.Answer;

public class AnswerDAO implements AnswerDAOInterface {
	
	private static final String TABLE = "answer";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_USER = "id_user";
	private static final String COLUMN_ID_QUESTION= "id_question";
	private static final String COLUMN_TEXT_ANSWER = "text_answer";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_DATE = "date_question";
	private static final String COLUMN_BEST_ANSWER = "best_answer";
	
	
	@Override
	public int insert(Answer answer) throws DatabaseException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void update(Answer answer) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Answer answer) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Answer select(Integer answerId) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Answer select(Integer answerId, Integer userId) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Answer select(Integer answerId, Integer userId, Integer id_question) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}



	
}
