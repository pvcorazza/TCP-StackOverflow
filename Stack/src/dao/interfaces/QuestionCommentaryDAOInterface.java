package dao.interfaces;

import java.sql.SQLException;

import database.exception.DatabaseException;
import domain.Commentary;

/**
 * It handles all persistence issues about commentaries on questions.
 * @author Anderson
 *
 */
public interface QuestionCommentaryDAOInterface {
	
	int  insert(Commentary commentary) throws DatabaseException;
	void update(Commentary commentary) throws DatabaseException;
	
	void delete(Commentary commentary) throws DatabaseException;
	void delete(Integer id) throws DatabaseException;
	
	Commentary select(Integer id) throws DatabaseException;
	

}
