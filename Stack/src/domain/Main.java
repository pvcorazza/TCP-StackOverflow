package domain;

import java.sql.SQLException;
import java.util.Scanner;

import dao.implementation.jdbc.UserDAO;
import database.exception.DatabaseConnectionException;
import domain.User.Permission;
import exceptions.userDAO.UserExceptionDAO;
import exceptions.userDAO.UserNotFoundException;

public class Main {

	public static void main(String[] args) {

		User user = null;

		int opcao;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("1 - Criar novo usuário");
			System.out.println("2 - Update");
			System.out.println("3 - Login");
			System.out.println("0 - Sair");
			System.out.print("Digite a opção desejada: ");
			opcao = scanner.nextInt();

			String username;
			String password;
			UserDAO userDAO = new UserDAO();

			switch (opcao) {

			// Criar usuário
			case 1:

				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();

				user = new User(username, password, Permission.AUTHENTICATED);

				try {
					int userID = userDAO.insert(user);
					user.setId(userID);
					System.out.print("Usuario inserido\n");
				} catch (UserExceptionDAO e) {
					System.out.println(e);
				} catch (DatabaseConnectionException e) {
					System.out.println(e);
				}
				break;

			// Update usuário
			case 2:

				if (user != null) {
					user.setPermission(User.Permission.ADMINISTRATOR);
					user.setBlocked(true);
					try {
						userDAO.update(user);
						System.out.println("Realizou update\n");
					} catch (UserExceptionDAO e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;

			// Login
			case 3:

				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();

				try {
					User loggedUser = userDAO.select(username, password);
					System.out.println("Login realizado");
					System.out.println("Id: " + loggedUser.getId());
					System.out.println("username: " + loggedUser.getUsername());
					System.out.println("Password: " + loggedUser.getPassword());
					System.out.println("Blocked: " + loggedUser.getBlocked());
					System.out.println("Permission: " + loggedUser.getPermission().getPermission());
				} catch (UserNotFoundException e) {

					e.printStackTrace();
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
