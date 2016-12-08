package dao.interfaces;

import java.util.ArrayList;

import database.exception.DatabaseException;
import domain.QuestionCommentary;

/**
 * It handles all persistence issues about commentaries on questions.
 * @author Anderson
 *
 */
public interface QuestionCommentaryDAOInterface {
	
	int  insert(QuestionCommentary commentary) throws DatabaseException;
	void update(QuestionCommentary commentary) throws DatabaseException;
	
	void delete(QuestionCommentary commentary) throws DatabaseException;
	void delete(Integer id) throws DatabaseException;
	
	ArrayList<QuestionCommentary> selectAll(int idQuestion) throws DatabaseException;
	QuestionCommentary select(int idQuestion) throws DatabaseException;
	

}
