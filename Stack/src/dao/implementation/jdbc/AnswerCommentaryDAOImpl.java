package dao.implementation.jdbc;

import dao.interfaces.AnswerCommentaryDAOInterface;
import database.exception.DatabaseException;
import domain.Commentary;

public class AnswerCommentaryDAOImpl implements AnswerCommentaryDAOInterface{
	
	private static final String TABLE = "answer_comment";
	
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_ID_USER = "id_user";
	private static final String COLUMN_ID_ANSWER= "id_answer";
	private static final String COLUMN_TEXT_COMMENT = "text_comment";
	private static final String COLUMN_DATE = "date_question";
	
	
	@Override
	public int insert(Commentary commentary) throws DatabaseException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void update(Commentary commentary) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Commentary commentary) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Commentary select(Integer id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Commentary select(Integer id, Integer userID, Integer answerID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
