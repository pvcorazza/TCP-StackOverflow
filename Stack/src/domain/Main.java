package domain;

import java.sql.Date;
import java.util.Scanner;

import domain.User.Permission;

public class Main {

	public static void main(String[] args) {

		int opcao;
		Scanner scanner = new Scanner(System.in);
		Management m = new Management();
		String username, password;
		String title;
		String text;
		User newUser, loggedUser;
		Question newQuestion;

		do {
			System.out.println("1 - Login");
			System.out.println("2 - Cadastro");
			System.out.println("3 - Buscar Questão");
			System.out.println("0 - Sair");
			System.out.print("Digite a opção desejada: ");
			opcao = scanner.nextInt();

			switch (opcao) {
			case 1:
				// Login
				loggedUser = m.login();
				int opcao1;

				// Update usuário
				do {
					System.out.println("1 - Definir permissão de um usuário");
					System.out.println("2 - Criar questão");
					System.out.println("3 - Buscar questão");
					System.out.println("0 - Sair");
					System.out.println("Digite a opção desejada: ");
					opcao1 = scanner.nextInt();
					switch (opcao1) {

					case 1:
						System.out.print("Digite o nome do usuário: ");
						m.updateUser(loggedUser, scanner.next());
						break;

					case 2:
						String tag1, tag2, tag3, tag4, tag5;
						System.out.print("Digite um título para a questão: ");
						title = scanner.next();
						System.out.print("Digite a questão: ");
						text = scanner.next();
						System.out.print("Digite no mínimo uma tag para a questão: ");
						System.out.print("Digite a tag 1: ");
						tag1 = scanner.next();
						System.out.print("Digite a tag 2: ");
						tag2 = scanner.next();
						System.out.print("Digite a tag 3: ");
						tag3 = scanner.next();
						System.out.print("Digite a tag 4: ");
						tag4 = scanner.next();
						System.out.print("Digite a tag 5: ");
						tag5 = scanner.next();
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

				while (opcao1 != 0);

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

				switch (opcaoBuscar) {
				
				case 1:
					System.out.print("Digite uma tag: ");
					String searchTag;
					searchTag = scanner.next();
					m.searchQuestion(searchTag, Management.TAG);
					break;
				case 2:
					System.out.print("Digite um título: ");
					String searchTitle;
					searchTitle = scanner.next();
					m.searchQuestion(searchTitle, Management.TITLE);
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
					m.searchQuestion(searchDate, Management.DATE);
					break;
				case 4:
					System.out.print("Digite um autor: ");
					String searchAuthor;
					searchAuthor = scanner.next();
					m.searchQuestion(searchAuthor, Management.AUTHOR);
					break;
				case 0:
					break;
				}

				break;
			case 0:
				System.out.println("Saindo...");
				break;

			default:
				System.out.println("Digite uma opção válida.");
			}
		} while (opcao != 0);

		scanner.close();
		System.exit(0);

	}
}
