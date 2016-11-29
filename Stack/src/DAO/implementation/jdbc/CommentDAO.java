package DAO.implementation.jdbc;

import java.sql.SQLException;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import DAO.CommentDAOInterface;

public class CommentDAO implements CommentDAOInterface {
	
	private String url1 = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9146901";
	private String user = "sql9146901";
	private String password = "tPcthmKbZt";
	
	private final String TABLE = "question_comment";

	@Override
	public void insert(domain.Comment answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(domain.Comment answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(domain.Comment answer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public domain.Comment findByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	

}
