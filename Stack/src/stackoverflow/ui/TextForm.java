package stackoverflow.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.Answer;
import domain.Management;
import domain.OperationPermission;
import domain.Question;
import domain.User;
import domain.User.Permission;
import exceptions.permission.PermissionException;
import exceptions.userDAO.UserNotFoundException;
import ui.text.UIUtils;

public class TextForm {

	private Scanner scanner;

	/* Classe principal */
	public void showForm() {

		Scanner scanner = new Scanner(System.in);

		int initialMenuOption;

		do {

			initialMenuOption = showOptionsInitialMenu();

		} while (initialMenuOption != 0);

		scanner.close();
		System.exit(0);
	}

	/* Classe para seleção das opções do menu inicial */
	public int showOptionsInitialMenu() {

		User newUser, loggedUser = null;

		System.out.println("### INF UFRGS - Stack Overflow ###");
		System.out.println("   =============================");
		System.out.println("   |     1 - Login             |");
		System.out.println("   |     2 - Cadastro          |");
		System.out.println("   |     3 - Buscar Questão    |");
		System.out.println("   |     0 - Sair              |");
		System.out.println("   =============================\n");

		int initialMenuOption = getInput();

		Management m = new Management();
		switch (initialMenuOption) {

		case 1: // Login
			
			try {
				loggedUser = m.login(getUsername(), getPassword());
				
			} catch (UserNotFoundException e) {
				System.out.println(e.getMessage());
				
			} catch (DatabaseConnectionException e) {
				System.out.println(e.getMessage());
				
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());

			}

			if (loggedUser != null) {
				System.out.println(UIUtils.INSTANCE.getTextManager().
						getText("message.login.success"));
				
				printInfoUser(loggedUser);

				int authenticatedOption;
				do {

					authenticatedOption = showOptionsLoggedMenu(loggedUser);
				}

				while (authenticatedOption != 0);
			}
			break;

		case 2: // Cadastro

			newUser = new User(getUsername(), getPassword(), Permission.AUTHENTICATED);
			try {
				m.createUser(newUser);
			} catch (DatabaseConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseUserDuplicated e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 3:
			if(OperationPermission.searchQuestion(loggedUser)){
				//Buscar questao sem estar logado loggedUser = null
				showSearchQuestionMenu(null);
			}
			
			break;

		case 0: // Sair

			System.out.println("Saindo...");
			break;

		default:
			System.out.println("Digite uma opção válida.");
		}

		return initialMenuOption;
	}

	public void showSearchQuestionMenu(User loggedUser) {

		Management m = new Management();

		System.out.println("1 - Tag");
		System.out.println("2 - Título");
		System.out.println("3 - Data");
		System.out.println("4 - Autor");
		System.out.println("0 - Sair");

		int opcaoBuscar;
		opcaoBuscar = getInput();
		ArrayList<Question> obtainedQuestions = null;

		switch (opcaoBuscar) {

		case 1:
			System.out.print("Digite uma tag: ");
			String searchTag;
			searchTag = getStringInput();
			obtainedQuestions = m.searchQuestion(searchTag, Management.TAG);
			break;
		case 2:
			System.out.print("Digite um título: ");
			String searchTitle;
			searchTitle = getStringInput();
			obtainedQuestions = m.searchQuestion(searchTitle, Management.TITLE);
			break;

		case 3:

			String searchDate;
			String searchDia;
			String searchMes;
			String searchAno;

			System.out.print("Dia (dd): ");
			searchDia = getStringInput();
			System.out.print("Mes (mm): ");
			searchMes = getStringInput();
			System.out.print("Ano (aaaa): ");
			searchAno = getStringInput();

			searchDate = searchAno + "/" + searchMes + "/" + searchDia;
			obtainedQuestions = m.searchQuestion(searchDate, Management.DATE);
			break;

		case 4:
			System.out.print("Digite um autor: ");
			String searchAuthor;
			searchAuthor = getStringInput();
			obtainedQuestions = m.searchQuestion(searchAuthor, Management.AUTHOR);
			break;

		case 0:

			break;
		}

		if (obtainedQuestions != null) {
			for (Question question : obtainedQuestions) {
				System.out.println("-------------------------------");
				System.out.println("Id: " + question.getId() + "\nTítulo: " + question.getTitle() + "\nAutor: "
						+ question.getAuthor().getUsername() + "\nData: " + question.getDate().toString());
				System.out.println("-------------------------------");
			}

			displayQuestion(loggedUser);
		}
	}
	

	public void displayQuestion(User loggedUser) {

		Management m = new Management();
		Question question;
		ArrayList<Answer> obtainedAnswers;

		int id;
		id = getInputId();
		question = m.getQuestion(id);
		obtainedAnswers = m.getAnswers(id);

		System.out.println("------------ QUESTÃO ----------");
		System.out.println("| Título: " + question.getTitle() + "\n| Autor: "
				+ question.getAuthor().getUsername() + "\n| Data: " + question.getDate().toString());
		System.out.println("| Texto: " + question.getText());
		
		System.out.println("---------- RESPOSTAS ----------");
		
		if(obtainedAnswers.size()==0){
			System.out.println("Não tem respostas");
		}
		else{
			for (Answer answer : obtainedAnswers) {
				
				System.out.println("\t| Id: " + answer.getId());
				System.out.println("\t| Data: " + answer.getDate().toString());
				System.out.println("\t| Autor: " + answer.getAuthor().getUsername());
				System.out.println("\t| Texto: " + answer.getText());
				System.out.println("-------------------------------");
			}
		}
			

		if(OperationPermission.displayPostOptions(loggedUser)){
			showPostOptions(loggedUser, question);
		}
		else{
			System.out.println("Por favor, faça o login para realizar outras operações");
		}

		System.out.println("-------------------------------");

	}

	public void showPostOptions(User loggedUser, Question question) {
		
		if(loggedUser!= null){
			System.out.println("   ====================================================");
			System.out.println("   |     1 - Responder questão                        |");
			System.out.println("   |     2 - Comentar questão                         |");
			System.out.println("   |     3 - Comentar resposta                        |");
			System.out.println("   |     4 - Selecionar a melhor resposta             |");
			System.out.println("   |     0 - Sair                                     |");
			System.out.println("   ====================================================\n");

			int postOption;
			postOption = getInput();

			
			switch (postOption) {

			case 1:
				showCreateAnswerOptions(loggedUser, question.getId());

				break;

			case 2:
				break;

			}
		}
		else{
			System.out.println("Faça o login para poder realizar as operações");
		}

		

	}

	public void showCreateAnswerOptions(User loggedUser, int idQuestion) {

		Management m = new Management();
		Answer newAnswer;
		String text;

		System.out.print("Digite a resposta: ");
		text = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newAnswer = new Answer(loggedUser.getId(), idQuestion, text, date, false);
		
		try {
			m.createAnswer(newAnswer);
			
		} catch (DatabaseConnectionException e) {
			System.out.println(e.getMessage());
			
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			
		}

	}

	/* Classe para seleção das opções do menu de usuário logado */
	public int showOptionsLoggedMenu(User loggedUser) {
		Management m = new Management();

		System.out.println("   =======================================================");
		System.out.println("   |     1 - Definir permissão de um usuário             |");
		System.out.println("   |     2 - Criar questão                               |");
		System.out.println("   |     3 - Buscar questão                              |");
		System.out.println("   |     0 - Sair                                        |");
		System.out.println("   =======================================================\n");

		int authenticatedOption;
		authenticatedOption = getInput();
		switch (authenticatedOption) {

		case 1: // Definir permissão de um usuário
			
			if(OperationPermission.modifyUserPermission(loggedUser)){
				User updatedUser;
				updatedUser = m.findUserToUpdate(loggedUser, getUsername());
				
				int updateOption;

				do {
					updateOption = showUpdateOptions(loggedUser,updatedUser);
				} while (updateOption != 0);
			}	
			else{
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}

			break;

		// Criar Questão
		case 2:
			
			if(OperationPermission.createQuestion(loggedUser)){
				showCreateQuestionOptions(loggedUser);
			}
			else{
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}
			
			break;

		case 3:
			if(OperationPermission.searchQuestion(loggedUser)){
				showSearchQuestionMenu(loggedUser);
			}
			else{
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}
			break;

		case 0:
			System.out.println("Saindo...");
			break;
		default:
			System.out.println("Digite uma opção válida...");
		}
		return authenticatedOption;

	}

	/* Classe para seleção das opções para atualizar permissões de um usuário */
	public int showUpdateOptions(User loggedUser,User userToUpdate) {

		Management m = new Management();

		System.out.println("   =======================================================");
		if (userToUpdate.getBlocked() == true) {
			System.out.println("   |     1 - Desbloquear usuário                         |");
		} else {
			System.out.println("   |     1 - Bloquear usuário                            |");
		}
		System.out.println("   |     2 - Modificar permissão                         |");
		System.out.println("   |     0 - Sair                                        |");
		System.out.println("   =======================================================\n");

		int updateOption;
		updateOption = getInput();

		switch (updateOption) {

		case 1:
			
			if(OperationPermission.blockUser(loggedUser)){
				
				userToUpdate.setBlocked(!userToUpdate.getBlocked());

				try {
					m.updatePermission(loggedUser,userToUpdate);
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				}

				printMessageSuccess();
				
			}
			else{
				System.out.println("Usuário não autorizado");
			}
			
			break;

		// Lembrar de controlar a entrada do usuário aqui
		// pois
		// pode
		// entrar com um valor inválido...
		case 2:
			
			if(OperationPermission.blockUser(loggedUser)){
				userToUpdate.setBlocked(!userToUpdate.getBlocked());
				setPermissions(loggedUser, userToUpdate);
			}
			else{
				System.out.println("Você não tem permissão para realizar a operação");
			}
			
		case 0:

			System.out.println("Saindo...");

			break;

		default:
			System.out.println("Digite uma opção válida.");
		}
		return updateOption;
	}

	/*
	 * Classe para seleção das opções para atualizar a permissão de um usuário
	 */
	public void setPermissions(User logedUser,User updatedUser) {

		Management m = new Management();
		System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");

		int privilege;
		privilege = getInput();

		switch (privilege) {
		
			case 1:
				updatedUser.setPermission(User.Permission.AUTHENTICATED);
				try {
					m.updatePermission(logedUser,updatedUser);
					printMessageSuccess();
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			
			case 2:
				updatedUser.setPermission(User.Permission.MODERATOR);
				try {
					m.updatePermission(logedUser,updatedUser);
					printMessageSuccess();
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			
			case 3:
				updatedUser.setPermission(User.Permission.ADMINISTRATOR);
				try {
					m.updatePermission(logedUser,updatedUser);
					printMessageSuccess();
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			default:
				printMessageInvalid();
			}
	}

	public void showCreateQuestionOptions(User loggedUser) {
		
		
		Management m = new Management();
		Question newQuestion;
		String title;
		String text;

		String tag1, tag2, tag3, tag4, tag5;
		System.out.print("Digite um título para a questão: ");
		title = getStringInput();
		System.out.print("Digite a questão: ");
		text = getStringInput();
		System.out.print("Digite no mínimo uma tag para a questão: ");
		System.out.print("Digite a tag 1: ");
		tag1 = getStringInput();
		System.out.print("Digite a tag 2: ");
		tag2 = getStringInput();
		System.out.print("Digite a tag 3: ");
		tag3 = getStringInput();
		System.out.print("Digite a tag 4: ");
		tag4 = getStringInput();
		System.out.print("Digite a tag 5: ");
		tag5 = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newQuestion = new Question(loggedUser.getId(), title, text, date, tag1, tag2, tag3, tag4, tag5);
		try {
			m.createQuestion(newQuestion);
		} catch (DatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (PermissionException e) {
			System.out.println(e.getMessage());
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
		}

	}

	public void printInfoUser(User loggedUser) {
		System.out.println("Id: " + loggedUser.getId());
		System.out.println("username: " + loggedUser.getUsername());
		System.out.println("Blocked: " + loggedUser.getBlocked());
		System.out.println("Permission: " + loggedUser.getPermission().getPermission());
	}

	public String getUsername() {
		System.out.print("Digite o nome do usuário: ");
		return getStringInput();

	}

	public String getPassword() {
		System.out.print("Digite a senha: ");
		return getStringInput();
	}

	public int getInput() {
		int opcao;
		scanner = new Scanner(System.in);
		System.out.print("Digite a opção desejada: ");
		opcao = scanner.nextInt();
		return opcao;
	}
	
	public int getInputId() {
		int opcao;
		scanner = new Scanner(System.in);
		System.out.print("Digite o ID da questão para visualizar: ");
		opcao = scanner.nextInt();
		return opcao;
	}

	public String getStringInput() {
		String input;
		scanner = new Scanner(System.in);
		input = scanner.nextLine();
		return input;
	}

	public void printMessageInvalid() {
		System.out.println("O valor digitado é inválido.");
	}

	public void printMessageSuccess() {
		System.out.println("Operação realizada com sucesso");
	}
}
