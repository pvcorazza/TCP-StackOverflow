

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import database.exception.DatabaseException;
import domain.AnswerCommentary;
import impl.AnswerCommentaryDAO;
import junit.framework.TestCase;

public class AnswerCommentaryDaoTest extends TestCase  {

	@Test
	public void testInsert() throws DatabaseException {
		//fail("Not yet implemented");
		AnswerCommentaryDAO dao = new AnswerCommentaryDAO();
		AnswerCommentary commentary = new AnswerCommentary();
		commentary.setIdAuthor(91);
		commentary.setIdAnswer(36);
		commentary.setText("Comentario de uma resposta id = 36");
		commentary.setDate(new Date(System.currentTimeMillis()));
		dao.insert(commentary);
	}

}
