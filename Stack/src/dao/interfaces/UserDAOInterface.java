package dao.interfaces;

import java.sql.SQLException;

import domain.User;
import exceptions.userDAO.UserExceptionDAO;
import exceptions.userDAO.UserNotFoundException;

/**
 * This class is responsible for making the communication with the database.
 * It manages all persistence interactions that uses a {@link User} object
 * @author Anderson
 *
 */
public interface UserDAOInterface {
	
	int insert (User answer) throws UserExceptionDAO ;
	void update (User answer) throws UserExceptionDAO;
	void delete (User answer) throws UserExceptionDAO;
	
	User select(Integer id) throws UserNotFoundException;
	User select(String username, String password) throws UserNotFoundException;
	

}
