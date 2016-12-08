package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import domain.User.Permission;

public class TextForm {

	private Scanner scanner;
	
	public void showForm() {

		Scanner scanner = new Scanner(System.in);

		int initialMenuOption;

		do {

			initialMenuOption = showOptionsInitialMenu();

		} while (initialMenuOption != 0);

		scanner.close();
		System.exit(0);

	}

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
				System.out.println("Login realizado");
				printInfoUser(loggedUser);

				do {
					int authenticatedOption;
					authenticatedOption = showOptionsLoggedMenu(loggedUser);
				}

				while (initialMenuOption != 0);
			}
			break;

		case 2: // Cadastro

			newUser = new User(getUsername(), getPassword(), Permission.AUTHENTICATED);
			m.createUser(newUser);
			break;

		case 3: // Buscar questão

			System.out.println("1 - Tag");
			System.out.println("2 - Título");
			System.out.println("3 - Data");
			System.out.println("4 - Autor");
			System.out.println("0 - Sair");
			System.out.print("Digite a opção desejada: ");

			int opcaoBuscar;
			opcaoBuscar = scanner.nextInt();
			ArrayList<Question> obtainedQuestions = null;

			switch (opcaoBuscar) {

			case 1:
				System.out.print("Digite uma tag: ");
				String searchTag;
				searchTag = scanner.next();
				obtainedQuestions = m.searchQuestion(searchTag, Management.TAG);
				break;
			case 2:
				System.out.print("Digite um título: ");
				String searchTitle;
				searchTitle = scanner.next();
				obtainedQuestions = m.searchQuestion(searchTitle, Management.TITLE);
				break;
			case 3:

				String searchDate;
				String searchDia;
				String searchMes;
				String searchAno;

				System.out.print("Dia (dd): ");
				searchDia = scanner.next();
				System.out.print("Mes (mm): ");
				searchMes = scanner.next();
				System.out.print("Ano (aaaa): ");
				searchAno = scanner.next();

				searchDate = searchAno + "/" + searchMes + "/" + searchDia;
				obtainedQuestions = m.searchQuestion(searchDate, Management.DATE);
				break;
			case 4:
				System.out.print("Digite um autor: ");
				String searchAuthor;
				searchAuthor = scanner.next();
				obtainedQuestions = m.searchQuestion(searchAuthor, Management.AUTHOR);
				break;
			case 0:
				break;
			}

			if (obtainedQuestions != null) {

				m.displayQuestion();
			}

			break;
			
		case 0: //Sair
			
			System.out.println("Saindo...");
			break;

		default:
			System.out.println("Digite uma opção válida.");
		}
		
		return initialMenuOption;
	}

	public int showOptionsLoggedMenu(User loggedUser) {
		
		Question newQuestion;
		Management m = new Management();
		String title;
		String text;
		
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
			break;

		case 3:

		case 0:
			System.out.println("Saindo...");
			break;
		default:
			System.out.println("Digite uma opção válida...");
		}
		return authenticatedOption;

	}
	
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
			int privilege;
			printPermissions();
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
			break;

		default:
			System.out.println("Digite uma opção válida.");
		}
		return updateOption;
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

	

	public void printPermissions() {
		System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");
	}

	public int getInput() {
		int opcao;
		scanner = new Scanner(System.in);
		System.out.print("Digite a opção desejada: ");
		opcao = scanner.nextInt();
		return opcao;
	}

	public String getStringInput() {
		String input;
		scanner = new Scanner(System.in);
		input = scanner.next();
		return input;
	}
	
	public void printMessageInvalid() {
		System.out.println("O valor digitado é inválido.");
	}

	public void printMessageSuccess() {
		System.out.println("Operação realizada com sucesso");
	}
}
