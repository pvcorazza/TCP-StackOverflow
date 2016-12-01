package domain;

import java.util.Scanner;

import domain.User.Permission;

public class Main {

	public static void main(String[] args) {

		int opcao;
		Scanner scanner = new Scanner(System.in);
		Management m = new Management();
		String username;
		String password;
		User newUser, loggedUser;

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
					System.out.println("0 - Sair");
					System.out.println("Digite a opção desejada: ");
					opcao1 = scanner.nextInt();
					switch (opcao1) {

					case 1:
						System.out.print("Digite o nome do usuário: ");
						m.updateUser(scanner.next());
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
