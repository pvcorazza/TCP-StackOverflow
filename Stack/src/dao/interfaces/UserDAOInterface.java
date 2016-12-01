package dao.interfaces;

import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.User;
import exceptions.userDAO.UserNotFoundException;

/**
 * This class is responsible for making the communication with the database.
 * It manages all persistence interactions that uses a {@link User} object
 * @author Anderson
 *
 */
public interface UserDAOInterface {
	
	int insert (User answer) throws DatabaseUserDuplicated, DatabaseException ;
	void update (User answer) throws DatabaseUserDuplicated;
	void delete (User answer) throws DatabaseUserDuplicated;
	
	User select(Integer id) throws UserNotFoundException;
	User select(String username, String password) throws UserNotFoundException;
	User select(String username) throws UserNotFoundException;
	

}
