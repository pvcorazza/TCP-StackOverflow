package business;

import java.util.ArrayList;

import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import domain.Question;

public interface QuestionDAOInterface {
	
	int insert (Question question) throws DatabaseException,DatabaseConnectionException;
	void update (Question question) throws DatabaseException;;
	
	int delete (Question question) throws DatabaseException;;
	void delete (Integer id) throws DatabaseException;
	
	ArrayList<Question> select(String tag, int opção) throws DatabaseException;
	Question select(int id) throws DatabaseException;

}
