package domain;

import java.util.ArrayList;

import dao.implementation.jdbc.AnswerCommentaryDAO;
import dao.implementation.jdbc.AnswerDAO;
import dao.implementation.jdbc.QuestionCommentaryDAO;
import dao.implementation.jdbc.QuestionDAO;
import dao.implementation.jdbc.UserDAO;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import exceptions.permission.PermissionException;
import exceptions.userDAO.UserNotFoundException;

public class Management {
	UserDAO userDAO;
	QuestionDAO questionDAO;
	AnswerDAO answerDAO;
	AnswerCommentaryDAO answerCommentaryDAO;
	QuestionCommentaryDAO questionCommentaryDAO;
	
	public Management(UserDAO userDao,QuestionDAO questionDAO,
			AnswerDAO answerDAO,AnswerCommentaryDAO answerCommentaryDAO,
			QuestionCommentaryDAO questionCommentaryDAO){
		
		this.userDAO = userDao;
		this.questionDAO = questionDAO;
		this.answerDAO = answerDAO;
		this.answerCommentaryDAO = answerCommentaryDAO;
		this.questionCommentaryDAO = questionCommentaryDAO;
	}
	
	public Management(){
		this.userDAO = new UserDAO();
		this.questionDAO = new QuestionDAO();
		this.answerDAO = new AnswerDAO();
		this.answerCommentaryDAO = new AnswerCommentaryDAO();
		this.questionCommentaryDAO = new QuestionCommentaryDAO();
	}
	
	/* Recebe um usuário e solicita a inserção no banco de dados */

	public void createUser(User user) throws DatabaseException,DatabaseConnectionException,DatabaseUserDuplicated {

		int userID = userDAO.insert(user);
		user.setId(userID);

	}


	public void createQuestion(Question question) throws PermissionException, DatabaseConnectionException, DatabaseException {
		
		if(OperationPermission.createQuestion(question.getAuthor())){
			int questionID = questionDAO.insert(question);
			question.setId(questionID);
		}
		else{
			throw new PermissionException("Usuário não tem permissão para realizar esta operação");
		}	
		
	}
	
	/* Recebe uma resposta e solicita a inserção no banco de dados */
	
	public void createAnswer(Answer answer) throws DatabaseConnectionException, DatabaseException {

		int answerID = answerDAO.insert(answer);
		answer.setId(answerID);
	

	}
	
	/* Recebe um comentário de questão e solicita a inserção no banco de dados */
	
	public void createQuestionCommentary(QuestionCommentary questionCommentary) {


		try {
			int questionCommentaryId = questionCommentaryDAO.insert(questionCommentary);
			questionCommentary.setId(questionCommentaryId);
			System.out.print("Comentário da questão criado\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public void createAnswerCommentary(AnswerCommentary answerCommentary) {

		try {
			int answerCommentaryId = answerCommentaryDAO.insert(answerCommentary);
			answerCommentary.setId(answerCommentaryId);
			System.out.print("Comentário da resposta criado\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}
	
	/* Recebe um usuário logado e um nome de usuário para pesquisar e retorna um usuário com esse nome */ 

	public User findUserToUpdate(User loggedUser, String username) {

			User userToUpdate;

			try {
				userToUpdate = userDAO.select(username);

				if (userToUpdate != null) {

					System.out.println("Usuário encontrado");
					return userToUpdate;
				}

			 else {
					System.out.println("Usuário não encontrado.");
					return null;
				}

			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;

	}
	
	/* Recebe um usuário, atualiza as permissões e define bloqueio desse usuário */
	
	public void updatePermission (User loggedUser, User userToUpdate) throws PermissionException {
		
		if(OperationPermission.modifyUserPermission(loggedUser)){

			try {
				userDAO.update(userToUpdate);
			} catch (DatabaseUserDuplicated e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			throw new PermissionException("Você não tem permissão para realizar a operação");
		}
		
	}
	
	/* Retorna um conjunto de questões com base em em texto de entrada e uma opção */

	public ArrayList<Question> searchQuestion(String searchText, int opcao) {

		try {
			ArrayList<Question> arrayQuestion = questionDAO.select(searchText, opcao);
			return arrayQuestion;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	/* Recebe um id de uma pergunta, solicita a busca de respostas com esse id do banco de dados e retorna essa lista de respostas */
	
	public ArrayList<Answer> getAnswers(int id) {

		try {
			ArrayList<Answer> arrayAnswer = answerDAO.selectAll(id);
			return arrayAnswer;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public ArrayList<QuestionCommentary> getQuestionCommentaries(int id) {

		try {
			ArrayList<QuestionCommentary> arrayQuestionCommentary = questionCommentaryDAO.selectAll(id);
			return arrayQuestionCommentary;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public ArrayList<AnswerCommentary> getAnswerCommentaries(int idAnswer) {

		try {
			ArrayList<AnswerCommentary> arrayAnswerCommentary = answerCommentaryDAO.selectALL(idAnswer);
			return arrayAnswerCommentary;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	/* Recebe um id, solicita a busca de uma questão com esse id do banco de dados e a retorna */

	public Question getQuestion(int questionId) {

		try {
			Question question = questionDAO.select(questionId);
			return question;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	

	public void deleteQuestion(User loggedUser, Question question) throws PermissionException {
		if(OperationPermission.deleteQuestion(loggedUser, question)){
		try {
			questionDAO.delete(question.getId());

			} catch (Exception e) {
	
				e.printStackTrace();
			}
		}
	}
	
	public void deleteAnswer(int answerId) {

		AnswerDAO answerDAO = new AnswerDAO();

		try {
			answerDAO.delete(answerId);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void deleteQuestionCommentary(int questionCommentaryId) {

		QuestionCommentaryDAO questionCommentaryDAO = new QuestionCommentaryDAO();

		try {
			questionCommentaryDAO.delete(questionCommentaryId);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	public void deleteAnswerCommentary(int answerCommentaryId) throws DatabaseException {

		AnswerCommentaryDAO answerCommentaryDAO = new AnswerCommentaryDAO();


		answerCommentaryDAO.delete(answerCommentaryId);

		
	}
	
	public void selectBestAnswer (int id) {
		
		Answer answer;
		
		try {
			answer = answerDAO.select(id);
			answer.setBestAnswer(true);
			
			answerDAO.update(answer);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/* Recebe um nome de usuário e password e retorna esse usuário do banco de dados */
	
	public User login(String username, String password) throws UserNotFoundException, DatabaseException {

		
		User loggedUser = null;
		loggedUser = userDAO.select(username, password);
		return loggedUser;
		

	}


	public void updateQuestion(Question question) {
		QuestionDAO questionDAO = new QuestionDAO ();
		
		try {
			questionDAO.update(question);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void updateAnswer(Answer answer) {
		AnswerDAO answerDAO = new AnswerDAO ();
		
		try {
			answerDAO.update(answer);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void updateQuestionCommentary(QuestionCommentary questionCommentaryToUpdate) {
		QuestionCommentaryDAO questionCommentaryDAO = new QuestionCommentaryDAO ();
		
		try {
			questionCommentaryDAO.update(questionCommentaryToUpdate);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateAnswerCommentary(AnswerCommentary answerCommentaryToUpdate) {
		AnswerCommentaryDAO answerCommentaryDAO = new AnswerCommentaryDAO ();
		
		try {
			answerCommentaryDAO.update(answerCommentaryToUpdate);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
