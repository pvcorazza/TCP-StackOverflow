package business;

import java.util.ArrayList;

import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.AnswerCommentary;

/**
 * It handles all persistence issues about commentaries on answers.
 * Insert/delete/update a commentary into the structure (can be any form of persistence) that contains all commentaries
 * on answers.
 *
 */
public interface AnswerCommentaryDAOInterface {
	
	int  insert (AnswerCommentary commentary) throws DatabaseException,DatabaseConnectionException;
	void update (AnswerCommentary commentary) throws DatabaseException;
	
	void delete (AnswerCommentary commentary) throws DatabaseException;
	void delete (Integer id) throws DatabaseException;
	
	AnswerCommentary select (int idAnswer) throws DatabaseException;
	ArrayList<AnswerCommentary> selectALL (int idAnswer) throws DatabaseException;
}
