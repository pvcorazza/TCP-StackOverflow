package stackoverflow.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Answer;
import domain.Management;
import domain.Question;
import domain.User;
import domain.User.Permission;
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

		User newUser, loggedUser;

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

			loggedUser = m.login(getUsername(), getPassword());

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
			m.createUser(newUser);
			break;

		case 3: // Buscar questão

			showSearchQuestionMenu(null);
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
		
		for (Answer answer : obtainedAnswers) {
			
			System.out.println("\t| Id: " + answer.getId());
			System.out.println("\t| Data: " + answer.getDate().toString());
			System.out.println("\t| Autor: " + answer.getAuthor().getUsername());
			System.out.println("\t| Texto: " + answer.getText());
			System.out.println("-------------------------------");
		}
		

		showPostOptions(loggedUser, question);

		System.out.println("-------------------------------");

	}

	public void showPostOptions(User loggedUser, Question question) {

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

	public void showCreateAnswerOptions(User loggedUser, int idQuestion) {

		Management m = new Management();
		Answer newAnswer;
		String text;

		System.out.print("Digite a resposta: ");
		text = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newAnswer = new Answer(loggedUser.getId(), idQuestion, text, date, false);
		m.createAnswer(newAnswer);

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

			User updatedUser;
			updatedUser = m.findUserToUpdate(loggedUser, getUsername());
			int updateOption;

			do {
				updateOption = showUpdateOptions(updatedUser);
			} while (updateOption != 0);

			break;

		// Criar Questão
		case 2:
			showCreateQuestionOptions(loggedUser);
			break;

		case 3:
			showSearchQuestionMenu(loggedUser);
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
	public int showUpdateOptions(User updatedUser) {

		Management m = new Management();

		System.out.println("   =======================================================");
		if (updatedUser.getBlocked() == true) {
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
			updatedUser.setBlocked(!updatedUser.getBlocked());

			m.updatePermission(updatedUser);

			printMessageSuccess();
			break;

		// Lembrar de controlar a entrada do usuário aqui
		// pois
		// pode
		// entrar com um valor inválido...
		case 2:
			setPermissions(updatedUser);
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
	public void setPermissions(User updatedUser) {

		Management m = new Management();
		System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");

		int privilege;
		privilege = getInput();

		switch (privilege) {
		case 1:
			updatedUser.setPermission(User.Permission.AUTHENTICATED);
			m.updatePermission(updatedUser);
			printMessageSuccess();
			break;
		case 2:
			updatedUser.setPermission(User.Permission.MODERATOR);
			m.updatePermission(updatedUser);
			printMessageSuccess();
			break;
		case 3:
			updatedUser.setPermission(User.Permission.ADMINISTRATOR);
			m.updatePermission(updatedUser);
			printMessageSuccess();
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
		m.createQuestion(newQuestion);

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
