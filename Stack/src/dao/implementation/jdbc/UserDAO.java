package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.interfaces.UserDAOInterface;
import domain.User;
import domain.User.Permission;
import exceptions.userDAO.UserExceptionDAO;
import exceptions.userDAO.UserNotFoundException;

public class UserDAO implements UserDAOInterface {
	
	private String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	private String user = "sql9146901";
	private String password = "tPcthmKbZt";
	private final String TABLE = "user";
	
	private Connection conn = null;

	@Override
	public int insert(User user) throws UserNotFoundException{

		int generatedKey = 0;
		
		try {
			connect();
			
			String sqlInsert = "insert into "+TABLE+" VALUES (NULL, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setBoolean(3, false);
			stmt.setInt(4, user.getPermission().getPermission());
			
			stmt.execute();	
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			 
			System.out.println("Inserted record's ID: " + generatedKey);
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserNotFoundException("Usuário não encontrado");
		}
		
		
		
		return generatedKey;
		
	}

	@Override
	public void update(User user) throws UserExceptionDAO    {
		
		try {
			connect();
			
			String sqlInsert = "UPDATE "+TABLE+" SET blocked = ?,permission = ? WHERE id="+user.getId();
			
			PreparedStatement stmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
			
			
			stmt.setBoolean(1, user.getBlocked());
			stmt.setInt(2, user.getPermission().getPermission());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserExceptionDAO("Não foi possível atualizar o usuário");
		}
			
		
	}

	@Override
	public void delete(User answer) {
		// TODO Auto-generated method stub
		
	}


	
	public void connect() throws SQLException {
		
		try {

			conn = DriverManager.getConnection(url1, user, password);
			if (conn != null) {
				System.out.println("Connected to the database FREESQLDATABASE");
			}
		}

		catch (SQLException ex) {
			throw ex;
		}
		
	}

	@Override
	public User select(Integer id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User select(String username, String password) throws UserNotFoundException {
		
		try {
			
			
			connect();
			
			String selectSQL = "SELECT * FROM "+TABLE+" WHERE username = ? AND password=?";
			
			PreparedStatement stmt = conn.prepareStatement(selectSQL);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				User user = new User(rs.getInt("id"), rs.getString("username"), 
						rs.getString("password"), rs.getBoolean("password"), 
						Permission.createPermission(rs.getInt("permission")));
				
				return user;
			}
			
			
			
		} catch (Exception e) {
			throw new UserNotFoundException("Não foi possível fazer login. Usuário não encontrado.");
		}
		return null;
	}

}
