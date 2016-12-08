package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import dao.implementation.jdbc.AnswerCommentaryDAOImpl;
import database.exception.DatabaseException;
import domain.AnswerCommentary;
import junit.framework.TestCase;

public class AnswerCommentaryDaoTest extends TestCase  {

	@Test
	public void testInsert() throws DatabaseException {
		//fail("Not yet implemented");
		AnswerCommentaryDAOImpl dao = new AnswerCommentaryDAOImpl();
		AnswerCommentary commentary = new AnswerCommentary();
		commentary.setIdAuthor(91);
		commentary.setId_answer(36);
		commentary.setText("Comentario de uma resposta id = 36");
		commentary.setDate(new Date(System.currentTimeMillis()));
		dao.insert(commentary);
	}

}
