package database.exception;

public class DatabaseConnectionException extends DatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseConnectionException() {
		super("Impossível conectar-se ao banco de dados");
		// TODO Auto-generated constructor stub
	}

	public DatabaseConnectionException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	

}
