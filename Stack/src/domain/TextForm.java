package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import domain.User.Permission;

public class TextForm {

	private Scanner scanner;

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

	public int printInitialMenu() {

		System.out.println("### INF UFRGS - Stack Overflow ###");
		System.out.println("   =============================");
		System.out.println("   |     1 - Login             |");
		System.out.println("   |     2 - Cadastro          |");
		System.out.println("   |     3 - Buscar Questão    |");
		System.out.println("   |     0 - Sair              |");
		System.out.println("   =============================\n");
		return getInput();
	}

	public int printLoggedMenu() {
		
		System.out.println("   =======================================================");
		System.out.println("   |     1 - Definir permissão de um usuário             |");
		System.out.println("   |     2 - Criar questão                               |");
		System.out.println("   |     3 - Buscar questão                              |");
		System.out.println("   |     0 - Sair                                        |");
		System.out.println("   =======================================================\n");
		return getInput();
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

	public void showForm() {

		Scanner scanner = new Scanner(System.in);
		Management m = new Management();
		String username, password;
		String title;
		String text;
		User newUser, loggedUser;
		Question newQuestion;
		int initialMenuOption;

		do {

			initialMenuOption = printInitialMenu();

			switch (initialMenuOption) {

			case 1:
				// Login
				loggedUser = m.login(getUsername(), getPassword());
				
				if (loggedUser != null) {
					System.out.println("Login realizado");
					printInfoUser(loggedUser);	
				}
				
				// Update usuário
				do {
					int authenticatedOption;
					printLoggedMenu();
					authenticatedOption = getInput();
					switch (authenticatedOption) {

					case 1:
						
						m.updateUser(loggedUser, getUsername());
						break;

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

				}

				while (initialMenuOption != 0);

				break;

			case 2:

				// Criar usuário
				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();

				newUser = new User(username, password, Permission.AUTHENTICATED);
				m.createUser(newUser);
				break;

			case 3:

				// Buscar questão

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
			case 0:
				System.out.println("Saindo...");
				break;

			default:
				System.out.println("Digite uma opção válida.");
			}
		} while (initialMenuOption != 0);

		scanner.close();
		System.exit(0);

	}

}
