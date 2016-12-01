package exceptions.userDAO;

import dao.interfaces.UserDAOInterface;

/**
 * Main class for all exceptions derived from {@link UserDAOInterface} implementation
 *
 */
public class UserExceptionDAO extends Exception {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserExceptionDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserExceptionDAO(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Usuário já existe no banco de dados ";
	}
	
	
	
}
