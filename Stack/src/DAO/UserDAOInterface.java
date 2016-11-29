package DAO;

import java.sql.SQLException;

import domain.User;

public interface UserDAOInterface {
	
	int insert (User answer) throws SQLException ;
	void update (User answer) throws SQLException;
	void delete (User answer);
	
	User findByID(Integer id);
	

}
