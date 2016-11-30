package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	static final String URL = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	static final String USER = "sql9146901";
	static final String PASSWORD = "tPcthmKbZt";
	static final String USER_TABLE = "user";
	static final String ANSWER_TABLE = "answer";
	static final String QUESTION_TABLE = "question";
	static final String COMMENT_TABLE = "question_comment";
	
	//Retorna uma nova conexão
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

