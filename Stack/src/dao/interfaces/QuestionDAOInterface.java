package dao.interfaces;

import java.sql.SQLException;

import domain.Question;

public interface QuestionDAOInterface {
	
	void insert (Question answer);
	void update (Question answer);
	void delete (Question answer);
	
	Question findByID(Integer id);
	
	void connect() throws SQLException;

}
