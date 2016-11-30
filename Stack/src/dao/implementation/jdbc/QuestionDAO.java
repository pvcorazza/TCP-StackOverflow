package dao.implementation.jdbc;

import java.sql.SQLException;

import dao.interfaces.QuestionDAOInterface;
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
	public void insert(Question question) throws DatabaseException {
		// TODO Auto-generated method stub
		
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
