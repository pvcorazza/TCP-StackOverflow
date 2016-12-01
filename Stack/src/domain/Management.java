package domain;

import java.util.Scanner;

import dao.implementation.jdbc.UserDAO;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.User.Permission;
import exceptions.userDAO.UserNotFoundException;

public class Management {

	private String username;
	private String password;
	private Scanner scanner1;

	public void createUser() {

		UserDAO userDAO = new UserDAO();
		scanner1 = new Scanner(System.in);
		System.out.print("Digite o nome do usu�rio: ");
		username = scanner1.next();
		System.out.print("Digite a senha: ");
		password = scanner1.next();

		User user = new User(username, password, Permission.AUTHENTICATED);

		try {
			int userID = userDAO.insert(user);
			user.setId(userID);
			System.out.print("Usuario inserido\n");
		} catch (DatabaseUserDuplicated e) {
			System.out.println(e);
		} catch (DatabaseConnectionException e) {
			e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void updateUser() {

		User user;
		UserDAO userDAO = new UserDAO();
		scanner1 = new Scanner(System.in);
		System.out.print("Digite o nome do usu�rio: ");
		username = scanner1.next();

		int option;

		try {
			user = userDAO.select(username);
			System.out.println("Usu�rio encontrado");

			do {
				if (user.getBlocked() == true) {
					System.out.println("1 - Desbloquear usu�rio");
				} else {
					System.out.println("1 - Bloquear usu�rio");
				}
				System.out.println("2 - Modificar permiss�o");
				System.out.println("0 - Sair");
				System.out.print("Digite a op��o desejada: ");
				option = scanner1.nextInt();

				switch (option) {
				case 1:
					user.setBlocked(!user.getBlocked());
					userDAO.update(user);
					System.out.println("Opera��o realizada com sucesso");
					break;

				// Lembrar de controlar a entrada do usu�rio aqui pois pode
				// entrar com um valor inv�lido...
				case 2:
					int privilege;
					System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");
					System.out.println("Digite a op��o desejada: ");
					privilege = scanner1.nextInt();

					switch (privilege) {
					case 1:
						user.setPermission(User.Permission.AUTHENTICATED);
						userDAO.update(user);
						System.out.println("Opera��o realizada com sucesso");
						break;
					case 2:
						user.setPermission(User.Permission.MODERATOR);
						userDAO.update(user);
						System.out.println("Opera��o realizada com sucesso");
						break;
					case 3:
						user.setPermission(User.Permission.ADMINISTRATOR);
						userDAO.update(user);
						System.out.println("Opera��o realizada com sucesso");
						break;
					default:
						System.out.println("O valor digitado � inv�lido");

					}

					break;

				default:
					System.out.println("Digite uma op��o v�lida.");

				}

			} while (option != 0);

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	void login() {
		
		scanner1 = new Scanner(System.in);
		UserDAO userDAO = new UserDAO();
		System.out.print("Digite o nome do usu�rio: ");
		username = scanner1.next();
		System.out.print("Digite a senha: ");
		password = scanner1.next();

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
	}
}
