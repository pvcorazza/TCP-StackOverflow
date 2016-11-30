package dao.interfaces;

import java.sql.SQLException;

import database.exception.DatabaseException;
import domain.Question;

public interface QuestionDAOInterface {
	
	void insert (Question question) throws DatabaseException;;
	void update (Question question) throws DatabaseException;;
	
	void delete (Question question) throws DatabaseException;;
	void delete (Integer id) throws DatabaseException;
	
	Question select(Integer id) throws DatabaseException;
	Question select(Integer id,Integer id_user) throws DatabaseException;
	
	Question select(Integer id,Integer id_user,String tag1,String tag2,
			String tag3,String tag4,String tag5) throws DatabaseException;
	
	Question select(String tag1,String tag2,String tag3,String tag4,String tag5) throws DatabaseException;

}
