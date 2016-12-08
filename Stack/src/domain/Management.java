package domain;

import java.util.ArrayList;
import java.util.Scanner;

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
		scanner1 = new Scanner(System.in);

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
	
	public void updatePermission (User userToUpdate) {
		UserDAO userDAO = new UserDAO();

		try {
			userDAO.update(userToUpdate);
		} catch (DatabaseUserDuplicated e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Question> searchQuestion(String searchText, int opcao) {

		scanner1 = new Scanner(System.in);
		QuestionDAO questionDAO = new QuestionDAO();

		try {
			ArrayList<Question> arrayQuestion = questionDAO.select(searchText, opcao);

			for (Question question : arrayQuestion) {
				System.out.println("-------------------------------");
				System.out.println("Id: " + question.getId() + "\nT�tulo: " + question.getTitle() + "\nAutor: "
						+ question.getAuthor().getUsername() + "\nData: " + question.getDate().toString());
				System.out.println("-------------------------------");
			}
			return arrayQuestion;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

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

	public void displayQuestion() {

		Question question;
		scanner = new Scanner(System.in);
		int id;
		System.out.print("Digite o id da quest�o que deseja visualizar: ");
		id = scanner.nextInt();
		question = this.getQuestion(id);
		System.out.println("-------------------------------");
		System.out.println("Id: " + question.getId() + "\nT�tulo: " + question.getTitle() + "\nAutor: "
				+ question.getAuthor().getUsername() + "\nData: " + question.getDate().toString());
		System.out.println("\t" + question.getText());
		System.out.println("-------------------------------");
	}

	// public void searchQuestion(String date) {
	//
	// scanner1 = new Scanner(System.in);
	// QuestionDAO questionDAO = new QuestionDAO();
	//
	// try {
	// ArrayList<Question> arrayQuestion = questionDAO.select(date);
	//
	// for (int i=0; i<arrayQuestion.size(); i++) {
	// System.out.println("Quest�o encontrada");
	// System.out.println("-------------------------------");
	// System.out.println("Id: " + arrayQuestion.get(i).getId());
	// System.out.println("T�tulo: " + arrayQuestion.get(i).getTitle());
	// System.out.println("Texto: " + arrayQuestion.get(i).getText());
	// System.out.println("-------------------------------");
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

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
