package dao.implementation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import dao.interfaces.UserDAOInterface;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.User;
import domain.User.Permission;
import exceptions.userDAO.UserNotFoundException;

public class UserDAO implements UserDAOInterface {

	@Override
	public int insert(User user) throws DatabaseConnectionException, DatabaseUserDuplicated, DatabaseException {

		int generatedKey = 0;

		try {

			Connection conn = new ConnectionFactory().getConnection();

			String sqlInsert = "insert into " + ConnectionFactory.USER_TABLE + " VALUES (NULL, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

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
		}

		catch (MySQLIntegrityConstraintViolationException e) {
			throw new DatabaseUserDuplicated();

		} catch (SQLException e) {
			throw new DatabaseException("Impossivel inserir usuário");
		}

		return generatedKey;

	}

	@Override
	public void update(User user) throws DatabaseUserDuplicated {

		try {
			Connection conn = new ConnectionFactory().getConnection();

			String sqlInsert = "UPDATE " + ConnectionFactory.USER_TABLE + " SET blocked = ?,permission = ? WHERE id="
					+ user.getId();

			PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			stmt.setBoolean(1, user.getBlocked());
			stmt.setInt(2, user.getPermission().getPermission());

			stmt.executeUpdate();

			stmt.close();
			conn.close();

		} catch (DatabaseConnectionException d) {
			System.out.println(d);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DatabaseUserDuplicated("Não foi possível atualizar o usuário");
		}

	}

	@Override
	public void delete(User answer) {
		// TODO Auto-generated method stub

	}

	@Override
	public User select(Integer id) throws UserNotFoundException {
		try {

			Connection conn = new ConnectionFactory().getConnection();

			String selectSQL = "SELECT * FROM " + ConnectionFactory.USER_TABLE + " WHERE id = ?";

			PreparedStatement stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), null,
						rs.getBoolean("password"), Permission.createPermission(rs.getInt("permission")));

				return user;
			}

			stmt.close();
			conn.close();

		} catch (Exception e) {
			throw new UserNotFoundException("Usuário não encontrado.");
		}
		return null;
	}

	@Override
	public User select(String username) throws UserNotFoundException {

		try {

			Connection conn = new ConnectionFactory().getConnection();

			String selectSQL = "SELECT * FROM " + ConnectionFactory.USER_TABLE + " WHERE username = ?";

			PreparedStatement stmt = conn.prepareStatement(selectSQL);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"),
						rs.getString("password"), rs.getBoolean("blocked"), Permission.createPermission(rs.getInt("permission")));
				return user;
			}

			stmt.close();
			conn.close();

		} catch (Exception e) {
			throw new UserNotFoundException("Usuário não encontrado.");
		}
		return null;
	}


	@Override
	public User select(String username, String password) throws UserNotFoundException,DatabaseException {
		
		User user = null;

		try {

			Connection conn = new ConnectionFactory().getConnection();

			String selectSQL = "SELECT * FROM " + ConnectionFactory.USER_TABLE + " WHERE username = ? AND password=?";

			PreparedStatement stmt = conn.prepareStatement(selectSQL);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println(""+rs.getInt("id"));
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getBoolean("password"), Permission.createPermission(rs.getInt("permission")));
			}
			else{
				throw new UserNotFoundException("Usuário não foi localizado");
			}
			
			stmt.close();
			conn.close();

		}
		 catch (DatabaseConnectionException e) {
				throw new DatabaseConnectionException("Erro ao conectar banco de dados");
				
		}
		catch (SQLException e) {
			throw new DatabaseException("Não foi possível realizar operação");
			
		}
		return user;
	}

}
