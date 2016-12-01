package domain;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		int opcao;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("1 - Criar novo usuário");
			System.out.println("2 - Update");
			System.out.println("3 - Login");
			System.out.println("0 - Sair");
			System.out.print("Digite a opção desejada: ");
			opcao = scanner.nextInt();

			Management m = new Management();

			switch (opcao) {

			// Criar usuário
			case 1:
				m.createUser();
				break;

			// Update usuário
			case 2:

				m.updateUser();
				break;

			// Login
			case 3:

				m.login();
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
