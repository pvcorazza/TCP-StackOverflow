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
			System.out.println("3 - Buscar Quest�o");
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
					System.out.println("2 - Criar quest�o");
					System.out.println("3 - Buscar quest�o");
					System.out.println("0 - Sair");
					System.out.println("Digite a op��o desejada: ");
					opcao1 = scanner.nextInt();
					switch (opcao1) {

					case 1:
						System.out.print("Digite o nome do usu�rio: ");
						m.updateUser(loggedUser, scanner.next());
						break;
						
					case 2:
						String tag1, tag2, tag3, tag4, tag5;
						System.out.print("Digite um t�tulo para a quest�o: ");
						title = scanner.next();
						System.out.print("Digite a quest�o: ");
						text = scanner.next();
						System.out.print("Digite no m�nimo uma tag para a quest�o: ");
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
						
						//Buscar quest�o
						System.out.println("Digite uma tag: ");
						String searchTag;
						searchTag = scanner.next();
						m.searchQuestion(searchTag);
						
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

				
			case 3:
				
				//Buscar quest�o
				System.out.println("Digite uma tag: ");
				String searchTag;
				searchTag = scanner.next();
				m.searchQuestion(searchTag);
				
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
