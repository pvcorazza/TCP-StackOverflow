import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import business.Management;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.Answer;
import domain.Question;
import domain.User;
import exceptions.permission.PermissionException;
import exceptions.userDAO.UserNotFoundException;
import impl.AnswerCommentaryDAO;
import impl.AnswerDAO;
import impl.QuestionCommentaryDAO;
import impl.QuestionDAO;
import impl.UserDAO;
import junit.framework.TestCase;

public class ManagementTest extends TestCase {
	
//	Management mn;
	User authenticatedUser,authUser2,administrator;
	Question questionFromAuthUser;
	Answer answerFromAuthUser;
	
	@Test
	public void testCreateUser() throws DatabaseConnectionException, DatabaseUserDuplicated, DatabaseException{
		
		UserDAO mockedUserDao = mock(UserDAO.class);
		when(mockedUserDao.insert(authenticatedUser)).thenReturn(authenticatedUser.getId());
		
		Management mn = new Management(mockedUserDao, new QuestionDAO(), new AnswerDAO(), 
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		
		mn.createUser(authenticatedUser);
		
	}
	
	@Test 
	public void testCreateQuestion() throws DatabaseConnectionException, PermissionException, DatabaseException{
		
		QuestionDAO mockedQuestionDao = mock(QuestionDAO.class);
		when(mockedQuestionDao.insert(questionFromAuthUser)).thenReturn(questionFromAuthUser.getId());
		Management mn = new Management(new UserDAO(), mockedQuestionDao, new AnswerDAO(),
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		mn.createQuestion(questionFromAuthUser);
	}
	
	@Test(expected = PermissionException.class)
	public void testUserCannotCreateQuestion() throws DatabaseConnectionException, DatabaseException {
		
		Question questionFail = new Question(1, authenticatedUser.getId(), "Questao A", 
				"Pergunta", new Date(System.currentTimeMillis()), 
				false, "tag1", "", "", "", "", null);
		
		QuestionDAO mockedQuestionDao = mock(QuestionDAO.class);
		when(mockedQuestionDao.insert(questionFail)).thenReturn(questionFail.getId());
		
		Management mn = new Management(new UserDAO(), mockedQuestionDao, new AnswerDAO(),
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		
		try {
			mn.createQuestion(questionFail);
		} catch (PermissionException e) {
			assertTrue(e.getMessage().contains("Usuário não tem permissão para realizar esta operação"));
		}		
	}
	
	@Test
	public void testUserModifyPermission() throws PermissionException, DatabaseConnectionException, DatabaseUserDuplicated, DatabaseException{
		UserDAO mockedUserDao = mock(UserDAO.class);
		when(mockedUserDao.insert(authenticatedUser)).thenReturn(authenticatedUser.getId());
		
		Management mn = new Management(mockedUserDao, new QuestionDAO(), new AnswerDAO(), 
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		
		mn.updatePermission(administrator, authenticatedUser);
	}
	
	@Test
	public void testUserCannotModifyPermission() throws DatabaseConnectionException, DatabaseUserDuplicated, DatabaseException{
		UserDAO mockedUserDao = mock(UserDAO.class);
		when(mockedUserDao.insert(authenticatedUser)).thenReturn(authenticatedUser.getId());
		
		Management mn = new Management(mockedUserDao, new QuestionDAO(), new AnswerDAO(), 
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		
		try {
			mn.updatePermission(authenticatedUser,administrator);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Você não tem permissão para realizar a operação"));
		}
		
	}
	
	@Test(expected = PermissionException.class)
	public void testUserCannotDeleteQuestion() throws DatabaseException{
		
		QuestionDAO mockedQuestionDao = mock(QuestionDAO.class);
		when(mockedQuestionDao.delete(questionFromAuthUser)).thenReturn(questionFromAuthUser.getId());
		
		Management mn = new Management(new UserDAO(), mockedQuestionDao, new AnswerDAO(), 
				new AnswerCommentaryDAO(), new QuestionCommentaryDAO());
		
		try {
			mn.deleteQuestion(authUser2, questionFromAuthUser);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Você não tem permissão para realizar a operação"));
		}
	}
	
	

	@Before
	protected
	void setUp(){

		authenticatedUser = new User(1, "usuario autenticado", "pwd", false, User.Permission.AUTHENTICATED);
		authUser2 = new User(3, "usuario autenticado", "pwd", false, User.Permission.AUTHENTICATED);
		administrator  = new User(2, "usuario moderador", "pwd", false, User.Permission.ADMINISTRATOR);
		questionFromAuthUser = new Question(1, authenticatedUser.getId(), "Questao A", 
				"Pergunta", new Date(System.currentTimeMillis()), 
				false, "tag1", "", "", "", "", authenticatedUser);
		
		answerFromAuthUser = new Answer(1, questionFromAuthUser.getId(), "texto", new Date(System.currentTimeMillis()), false, authenticatedUser);
		answerFromAuthUser.setId(1);
	}
	

}

