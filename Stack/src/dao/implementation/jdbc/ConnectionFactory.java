package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.exception.DatabaseConnectionException;

public class ConnectionFactory {
	
	static final String URL = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	static final String USER = "sql9146901";
	static final String PASSWORD = "tPcthmKbZt";
	public static final String USER_TABLE = "user";
	public static final String ANSWER_TABLE = "answer";
	public static final String QUESTION_TABLE = "question";
	public static final String COMMENT_TABLE = "question_comment";
	
	//Retorna uma nova conex�o
	public Connection getConnection() throws DatabaseConnectionException {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new DatabaseConnectionException();
		}
	}
}

