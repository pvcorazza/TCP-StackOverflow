package domain;

import java.util.ArrayList;
import java.util.Scanner;

import dao.implementation.jdbc.AnswerCommentaryDAO;
import dao.implementation.jdbc.AnswerDAO;
import dao.implementation.jdbc.QuestionCommentaryDAO;
import dao.implementation.jdbc.QuestionDAO;
import dao.implementation.jdbc.UserDAO;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import exceptions.userDAO.UserNotFoundException;

public class Management {
	
	/* Recebe um usu�rio e solicita a inser��o no banco de dados */

	public void createUser(User user) {

		UserDAO userDAO = new UserDAO();

		try {
			int userID = userDAO.insert(user);
			user.setId(userID);
			System.out.print("Usuario inserido\n");
		} catch (DatabaseUserDuplicated e) {
			System.out.println(e);
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	/* Recebe uma quest�o e solicita a inser��o no banco de dados */
	
	public void createQuestion(Question question) {

		QuestionDAO questionDAO = new QuestionDAO();

		try {
			int questionID = questionDAO.insert(question);
			question.setId(questionID);
			System.out.print("Quest�o criada\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	/* Recebe uma resposta e solicita a inser��o no banco de dados */
	
	public void createAnswer(Answer answer) {
		// TODO Auto-generated method stub
		AnswerDAO answerDAO = new AnswerDAO();

		try {
			int answerID = answerDAO.insert(answer);
			answer.setId(answerID);
			System.out.print("Resposta criada\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	/* Recebe um coment�rio de quest�o e solicita a inser��o no banco de dados */
	
	public void createQuestionCommentary(QuestionCommentary questionCommentary) {
		// TODO Auto-generated method stub
		QuestionCommentaryDAO questionCommentaryDAO = new QuestionCommentaryDAO();

		try {
			int questionCommentaryId = questionCommentaryDAO.insert(questionCommentary);
			questionCommentary.setId(questionCommentaryId);
			System.out.print("Coment�rio da quest�o criado\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public void createAnswerCommentary(AnswerCommentary answerCommentary) {
		// TODO Auto-generated method stub
		AnswerCommentaryDAO answerCommentaryDAO = new AnswerCommentaryDAO();

		try {
			int answerCommentaryId = answerCommentaryDAO.insert(answerCommentary);
			answerCommentary.setId(answerCommentaryId);
			System.out.print("Coment�rio da resposta criado\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	/* Recebe um usu�rio logado e um nome de usu�rio para pesquisar e retorna um usu�rio com esse nome */ 

	public User findUserToUpdate(User loggedUser, String username) {

		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR
				|| loggedUser.getPermission() == User.Permission.MODERATOR) {

			User userToUpdate;
			UserDAO userDAO = new UserDAO();

			try {
				userToUpdate = userDAO.select(username);

				if (userToUpdate != null) {

					System.out.println("Usu�rio encontrado");
					return userToUpdate;
				}

			 else {
					System.out.println("Usu�rio n�o encontrado.");
					return null;
				}

			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Voc� n�o tem permiss�o para exeutar essa a��o.");
		}
		return null;

	}
	
	/* Recebe um usu�rio, atualiza as permiss�es e define bloqueio desse usu�rio */
	
	public void updatePermission (User userToUpdate) {
		UserDAO userDAO = new UserDAO();

		try {
			userDAO.update(userToUpdate);
		} catch (DatabaseUserDuplicated e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Retorna um conjunto de quest�es com base em em texto de entrada e uma op��o */

	public ArrayList<Question> searchQuestion(String searchText, int opcao) {

		QuestionDAO questionDAO = new QuestionDAO();

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

		AnswerDAO answerDAO = new AnswerDAO();

		try {
			ArrayList<Answer> arrayAnswer = answerDAO.selectAll(id);
			return arrayAnswer;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public ArrayList<QuestionCommentary> getQuestionCommentaries(int id) {

		QuestionCommentaryDAO questionCommentaryDAO = new QuestionCommentaryDAO();

		try {
			ArrayList<QuestionCommentary> arrayQuestionCommentary = questionCommentaryDAO.selectAll(id);
			return arrayQuestionCommentary;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public ArrayList<AnswerCommentary> getAnswerCommentaries(int idAnswer) {

		AnswerCommentaryDAO answerCommentaryDAO = new AnswerCommentaryDAO();

		try {
			ArrayList<AnswerCommentary> arrayAnswerCommentary = answerCommentaryDAO.selectALL(idAnswer);
			return arrayAnswerCommentary;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	/* Recebe um id, solicita a busca de uma quest�o com esse id do banco de dados e a retorna */

	public Question getQuestion(int id) {

		QuestionDAO questionDAO = new QuestionDAO();

		try {
			Question question = questionDAO.select(id);
			return question;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public void deleteQuestion(User loggedUser, int id) {

		QuestionDAO questionDAO = new QuestionDAO();

		try {
			questionDAO.delete(id);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void selectBestAnswer (int id) {
		
		Answer answer;
		AnswerDAO answerDAO = new AnswerDAO();
		
		try {
			answer = answerDAO.select(id);
			answer.setBestAnswer(true);
			
			answerDAO.update(answer);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/* Recebe um nome de usu�rio e password e retorna esse usu�rio do banco de dados */
	
	public User login(String username, String password) {

		UserDAO userDAO = new UserDAO();

		try {
			User loggedUser = userDAO.select(username, password);
			return loggedUser;
		} catch (UserNotFoundException e) {

			e.printStackTrace();
		}
		return null;

	}

}
