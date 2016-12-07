package test;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dao.implementation.jdbc.AnswerDAO;
import database.exception.DatabaseException;
import domain.Answer;
import junit.framework.TestCase;

import static org.mockito.Mockito.*;

public class AnswerDaoTest extends TestCase  {
	
	Answer answer;	//declare a Answer
	AnswerDAO dao;
	private static AnswerDAO mockedAnswerDao;
	private static Answer answer1;
	private static Answer answer2;

//	@Test
//	public void testInsetAnswer() throws DatabaseException {
//		int id =dao.insert(answer);	
//	}
//
//	@Test
//	public void testSelect() throws DatabaseException{
//		Answer r = dao.select(13);
//		//System.out.println(r.getText());
//	}
	
	@Test
	public void testSelectArray() throws DatabaseException{
		ArrayList<Answer> answers = dao.selectAll(9);
		System.out.println("tamanho da lista: "+answers.size());
	}
	
//	@Test
//	public void testUpdateAnswer() throws DatabaseException{
//		answer.setBestAnswer(true);
//		answer.setText("Texto atualizado");
//		dao.update(answer);
//	}
	
//	@Test
//	public void testDeleteAnswer() throws DatabaseException{
//		dao.delete(answer);
//	}
	
	@Test
	public void testInsertAnswer() throws DatabaseException{
		int id = mockedAnswerDao.insert(answer1);
		assertNotNull(id);
		assertEquals(answer1.getId(), id);
	}
	
	@Before
	protected
	void setUp() throws DatabaseException{
		answer = new Answer(92, "Titulo", new Date(System.currentTimeMillis()), false);
		
		answer.setText("Isto é uma resposta para uma questao");
		answer.setId_question(9);
		answer.setId(13);
		
		dao = new AnswerDAO();
		
		//Create a mock object of AnswerDAO
		mockedAnswerDao = mock(AnswerDAO.class);
		
		//Create a few instances of Answer class
		answer1 = new Answer();
		answer1.setId(1);
		answer1.setId_author(1);
		answer1.setId_question(1);
		answer1.setText("Este é a resposta 1 feita pelo id_author=1");
		answer1.setDate(new Date(System.currentTimeMillis()));
		answer1.setBestAnswer(false);
		
		answer2 = new Answer();
		answer2.setId(2);
		answer2.setId_author(1);
		answer2.setId_question(1);
		answer2.setText("Este é a resposta 1 feita pelo id_author=1");
		answer2.setDate(new Date(System.currentTimeMillis()));
		answer2.setBestAnswer(true);
		
		when(mockedAnswerDao.insert(answer1)).thenReturn(answer1.getId());
		
	}
}
