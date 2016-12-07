package dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import database.exception.DatabaseException;
import domain.Question;

public interface QuestionDAOInterface {
	
	int insert (Question question) throws DatabaseException;;
	void update (Question question) throws DatabaseException;;
	
	void delete (Question question) throws DatabaseException;;
	void delete (Integer id) throws DatabaseException;
	
	ArrayList<Question> select(String tag, int opção) throws DatabaseException;

}
