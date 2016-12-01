package exceptions.userDAO;

import database.exception.DatabaseUserDuplicated;

public class BlockedUserException extends DatabaseUserDuplicated {

	
	
	public BlockedUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlockedUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
