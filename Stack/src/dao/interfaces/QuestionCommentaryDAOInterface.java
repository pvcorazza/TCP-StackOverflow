package dao.interfaces;

import java.sql.SQLException;

import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.AnswerCommentary;

/**
 * It handles all persistence issues about commentaries on questions.
 * @author Anderson
 *
 */
public interface QuestionCommentaryDAOInterface {
	
	int  insert(AnswerCommentary commentary) throws DatabaseException,DatabaseConnectionException;
	void update(AnswerCommentary commentary) throws DatabaseException;
	
	void delete(AnswerCommentary commentary) throws DatabaseException;
	void delete(Integer id) throws DatabaseException;
	
	AnswerCommentary select(Integer id) throws DatabaseException;
	

}
