package dao.interfaces;

import database.exception.DatabaseException;
import domain.Commentary;

/**
 * It handles all persistence issues about commentaries on answers.
 * Insert/delete/update a commentary into the structure (can be any form of persistence) that contains all commentaries
 * on answers.
 *
 */
public interface AnswerCommentaryDAOInterface {
	
	int  insert (Commentary commentary) throws DatabaseException;
	void update (Commentary commentary) throws DatabaseException;
	
	void delete (Commentary commentary) throws DatabaseException;
	void delete (Integer id) throws DatabaseException;
	
	Commentary select (Integer id) throws DatabaseException;
	Commentary select (Integer id,Integer userID,Integer answerID) throws DatabaseException;
	
}
