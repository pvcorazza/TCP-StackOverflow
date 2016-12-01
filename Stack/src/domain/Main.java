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
			System.out.print("Digite a op��o desejada: ");
			opcao = scanner.nextInt();

			switch (opcao) {
			case 1:
				// Login
				loggedUser = m.login();
				int opcao1;

				// Update usu�rio
				do {
					System.out.println("1 - Definir permiss�o de um usu�rio");
					System.out.println("0 - Sair");
					System.out.println("Digite a op��o desejada: ");
					opcao1 = scanner.nextInt();
					switch (opcao1) {

					case 1:
						System.out.print("Digite o nome do usu�rio: ");
						m.updateUser(scanner.next());
						break;

					case 0:
						System.out.println("Saindo...");
						break;
					default:
						System.out.println("Digite uma op��o v�lida...");

					}

				}

				while (opcao1 != 0);

				break;

			case 2:

				// Criar usu�rio
				System.out.print("Digite o nome do usu�rio: ");
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
				System.out.println("Digite uma op��o v�lida.");
			}
		} while (opcao != 0);

		scanner.close();
		System.exit(0);

	}
}
