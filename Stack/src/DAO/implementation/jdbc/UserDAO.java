package DAO.implementation.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.UserDAOInterface;
import domain.User;

public class UserDAO implements UserDAOInterface {
	
	private String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	private String user = "sql9146901";
	private String password = "tPcthmKbZt";
	private final String TABLE = "user";
	
	private Connection conn = null;

	@Override
	public int insert(User user) throws SQLException {
		connect();
		
		String sqlInsert = "insert into "+TABLE+" VALUES (NULL, ?, ?, ?, ?)";
		
		PreparedStatement stmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, user.getUsername());
		stmt.setString(2, user.getPassword());
		stmt.setBoolean(3, false);
		stmt.setInt(4, user.getPermission().getPermission());
		
		stmt.execute();	
		
		ResultSet rs = stmt.getGeneratedKeys();
		int generatedKey = 0;
		if (rs.next()) {
		    generatedKey = rs.getInt(1);
		}
		 
		System.out.println("Inserted record's ID: " + generatedKey);
		
		stmt.close();
		conn.close();
		
		return generatedKey;
		
	}

	@Override
	public void update(User user) throws SQLException {
		connect();
		
		//Statement stmt = (Statement) conn.createStatement();
		
//		String sql = "UPDATE "+TABLE+" blocked= "+user.getBlocked()+", permission = "+user.getPermission().getPermission()
//				+" WHERE id="+user.getId();
		
		String sqlInsert = "UPDATE "+TABLE+" SET blocked = ?,permission = ? WHERE id="+user.getId();
		
		PreparedStatement stmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
		
		
		stmt.setBoolean(1, user.getBlocked());
		stmt.setInt(2, user.getPermission().getPermission());
		
		stmt.executeUpdate();	
		
		
		//stmt.executeUpdate(sql);
		
		
		stmt.close();
		conn.close();
		
	}

	@Override
	public void delete(User answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void connect() throws SQLException {
		
		try {

			String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
			String user = "sql9146901";
			String password = "tPcthmKbZt";

			conn = DriverManager.getConnection(url1, user, password);
			if (conn != null) {
				System.out.println("Connected to the database FREESQLDATABASE");
			}
		}

		catch (SQLException ex) {
			throw ex;
		}
		
	}

}
