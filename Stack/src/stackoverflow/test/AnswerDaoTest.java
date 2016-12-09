
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import database.exception.DatabaseException;
import domain.Answer;
import domain.User;
import impl.AnswerDAO;
import junit.framework.TestCase;

public class AnswerDaoTest extends TestCase  {
	

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
	
//	@Test
//	public void testSelectArray() throws DatabaseException{
//		ArrayList<Answer> answers = dao.selectAll(9);
//		System.out.println("tamanho da lista: "+answers.size());
//	}
	
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
	public void testInsertAnswer() throws DatabaseException{
		
		when(mockedAnswerDao.insert(answer1)).thenReturn(answer1.getId());
		
		int id = mockedAnswerDao.insert(answer1);
		System.out.println("id="+id);
		assertNotNull(id);
		assertEquals(answer1.getId(), id);
		}

	@Test
	public void testUpdateAnswerText() throws DatabaseException{
		
		
		when(mockedAnswerDao.update(answer1)).thenReturn(true);
		
		boolean result = mockedAnswerDao.update(answer1);
		
		assertTrue(result);
		
	}
	
//	public void testDeleteAnswer() throws DatabaseException{
//		int id =dao.insert(answer);	
//		answer.setId(id);
//		dao.delete(answer);
//	}
	
	@Before
	protected
	void setUp(){
		

		//Create a mock object of AnswerDAO
		mockedAnswerDao = mock(AnswerDAO.class);
		
		User author = new User(1, "author 1", "123", false, User.Permission.ADMINISTRATOR);
		
		//Create a few instances of Answer class
		answer1 = new Answer();
		answer1.setId(1);
		answer1.setAuthor(author);
		answer1.setIdQuestion(1);
		answer1.setText("Este é a resposta 1 feita pelo id_author=1");
		answer1.setDate(new Date(System.currentTimeMillis()));
		answer1.setBestAnswer(false);
		
		answer2 = new Answer();
		answer2.setId(2);
		answer2.setAuthor(author);
		answer2.setIdQuestion(1);
		answer2.setText("Este é a resposta 1 feita pelo id_author=1");
		answer2.setDate(new Date(System.currentTimeMillis()));
		answer2.setBestAnswer(true);
		
		//when(mockedAnswerDao.insert(answer1)).thenReturn(answer1.getId());

		
	}
}
