package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dao.implementation.jdbc.AnswerDAO;
import database.exception.DatabaseException;
import domain.Answer;
import junit.framework.TestCase;

public class AnswerDaoTest extends TestCase  {
	
	Answer answer;	//declare a Answer
	AnswerDAO dao;

	@Test
	public void testInsetAnswer() throws DatabaseException {
		int id =dao.insert(answer);	
		answer.setId(id);
		System.out.println("Inserido resposta\n");
		System.out.println("id = "+answer.getId());
		System.out.println("id_author "+answer.getIdAuthor()+ "\n");
		System.out.println("id_question "+answer.getIdQuestion()+ "\n");
		System.out.println("id_author "+answer.getIdAuthor()+"\n");
		
	}

//	@Test
//	public void testSelect() throws DatabaseException{
//		Answer r = dao.select(13);
//		//System.out.println(r.getText());
//	}
//	
//	@Test
//	public void testSelectArray() throws DatabaseException{
//		ArrayList<Answer> answers = dao.selectAll(9);
//		System.out.println("tamanho da lista: "+answers.size());
//	}
//	
//	@Test
//	public void testUpdateAnswer() throws DatabaseException{
//		answer.setBestAnswer(true);
//		answer.setText("Texto atualizado");
//		dao.update(answer);
//	}
//	
	@Test
	public void testDeleteAnswer() throws DatabaseException{
		int id =dao.insert(answer);	
		answer.setId(id);
		dao.delete(answer);
	}
	@Before
	protected
	void setUp(){
		//answer = new Answer(92, "Titulo", new Date(System.currentTimeMillis()), false);
		
		answer.setText("Isto é uma resposta para uma questao");
		answer.setIdQuestion(9);
		answer.setId(13);
		dao = new AnswerDAO();
		
	}
}
