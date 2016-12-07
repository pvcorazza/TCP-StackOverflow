package dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import database.exception.DatabaseException;
import domain.Answer;

/**
 * This interface handles the interaction with the table that contains all answers of a question
 *
 */
public interface AnswerDAOInterface {
	
	int  insert (Answer answer) throws DatabaseException;
	
	void update (Answer answer) throws DatabaseException;
	
	void delete (Answer answer) throws DatabaseException;
	void delete (Integer id) throws DatabaseException;
	
	Answer select(Integer answerId) throws DatabaseException;
	
	ArrayList<Answer> selectAll(int questionId) throws DatabaseException;
	
	

}
