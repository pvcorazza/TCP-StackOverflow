package database.exception;

import dao.interfaces.UserDAOInterface;

/**
 * Main class for all exceptions derived from {@link UserDAOInterface} implementation
 *
 */
public class DatabaseUserDuplicated extends Exception {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseUserDuplicated() {
		super("Usuário já existe no banco de dados");
		// TODO Auto-generated constructor stub
	}

	public DatabaseUserDuplicated(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
