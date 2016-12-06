package domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import domain.User.Permission;

public class Main {

	public static void main(String[] args) {

		int opcao;
		Scanner scanner = new Scanner(System.in);
		Management m = new Management();
		String username;
		String password;
		String title;
		String text;
		String tag;
		User newUser, loggedUser;
		Question newQuestion;

		do {
			System.out.println("1 - Login");
			System.out.println("2 - Cadastro");
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
					System.out.println("0 - Sair");
					System.out.println("Digite a opção desejada: ");
					opcao1 = scanner.nextInt();
					switch (opcao1) {

					case 1:
						System.out.print("Digite o nome do usuário: ");
						m.updateUser(loggedUser, scanner.next());
						break;
						
					case 2:
						System.out.print("Digite um título para a questão: ");
						title = scanner.next();
						System.out.print("Digite a questão: ");
						text = scanner.next();
						System.out.println("Digite uma tag para a questão: ");
						tag = scanner.next();
						Date date = new Date(System.currentTimeMillis());
						newQuestion = new Question(loggedUser.getId(), title, text, date, tag);
						m.createQuestion(newQuestion);
						break;
						
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
