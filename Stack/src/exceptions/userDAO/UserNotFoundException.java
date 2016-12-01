package exceptions.userDAO;

import database.exception.DatabaseUserDuplicated;

public class UserNotFoundException extends DatabaseUserDuplicated {

	
	
	public UserNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}



}
