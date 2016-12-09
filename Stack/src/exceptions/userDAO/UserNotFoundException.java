package exceptions.userDAO;

import database.exception.DatabaseUserDuplicated;

public class UserNotFoundException extends DatabaseUserDuplicated {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}



}
