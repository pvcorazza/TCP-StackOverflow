package DAO;

import java.sql.SQLException;

import domain.Comment;

public interface CommentDAOInterface {
	
	void insert (Comment answer);
	void update (Comment answer);
	void delete (Comment answer);
	
	Comment findByID(Integer id);
	
	void connect() throws SQLException;

}
