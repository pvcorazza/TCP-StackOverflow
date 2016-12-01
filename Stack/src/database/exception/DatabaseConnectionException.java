package database.exception;

public class DatabaseConnectionException extends DatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Impossivel conectar ao banco de dados ";
	}
	
	

}
