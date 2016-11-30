package dao.interfaces;

import java.sql.SQLException;

import domain.Answer;

public interface AnswerDAOInterface {
	
	void insert (Answer answer);
	void update (Answer answer);
	void delete (Answer answer);
	
	Answer findByID(Integer id);
	
	void connect() throws SQLException;

}
