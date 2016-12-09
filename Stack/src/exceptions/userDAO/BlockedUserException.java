package exceptions.userDAO;

import database.exception.DatabaseUserDuplicated;

public class BlockedUserException extends DatabaseUserDuplicated {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlockedUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlockedUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
