package domain;

import java.util.ArrayList;
import java.util.Scanner;

import dao.implementation.jdbc.AnswerDAO;
import dao.implementation.jdbc.QuestionDAO;
import dao.implementation.jdbc.UserDAO;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import exceptions.userDAO.UserNotFoundException;

public class Management {

	public static final int TAG = 1;
	public static final int TITLE = 2;
	public static final int DATE = 3;
	public static final int AUTHOR = 4;
	private String username;
	private String password;
	private Scanner scanner1;
	private Scanner scanner;
	
	/* Recebe um usuário e solicita a inserção no banco de dados */

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


	public void createQuestion(Question question) {
		
		QuestionDAO questionDAO = new QuestionDAO();

		try {
			int questionID = questionDAO.insert(question);
			question.setId(questionID);
			System.out.print("Questão criada\n");
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
	}
	
	/* Recebe uma resposta e solicita a inserção no banco de dados */
	
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
	
	/* Recebe um usuário logado e um nome de usuário para pesquisar e retorna um usuário com esse nome */ 

	public User findUserToUpdate(User loggedUser, String username) {

		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR
				|| loggedUser.getPermission() == User.Permission.MODERATOR) {

			User userToUpdate;
			UserDAO userDAO = new UserDAO();

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
		} else {
			System.out.println("Você não tem permissão para exeutar essa ação.");
		}
		return null;

	}
	
	/* Recebe um usuário, atualiza as permissões e define bloqueio desse usuário */
	
	public void updatePermission (User userToUpdate) {
		UserDAO userDAO = new UserDAO();

		try {
			userDAO.update(userToUpdate);
		} catch (DatabaseUserDuplicated e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Retorna um conjunto de questões com base em em texto de entrada e uma opção */

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
	
	/* Recebe um id, solicita a busca de uma questão com esse id do banco de dados e a retorna */

	public Question getQuestion(int id) {

		scanner1 = new Scanner(System.in);
		QuestionDAO questionDAO = new QuestionDAO();

		try {
			Question question = questionDAO.select(id);
			return question;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}
	
	/* Recebe um nome de usuário e password e retorna esse usuário do banco de dados */
	
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
