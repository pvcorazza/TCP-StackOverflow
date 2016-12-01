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

	public void createUser(User user) {

		UserDAO userDAO = new UserDAO();
		scanner1 = new Scanner(System.in);

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

	public void updateUser(String username) {

		User updatedUser;
		UserDAO userDAO = new UserDAO();
		scanner1 = new Scanner(System.in);

		int option;

		try {
			updatedUser = userDAO.select(username);
			
			if (updatedUser != null) {
			
			System.out.println("Usuário encontrado");

			do {
				if (updatedUser.getBlocked() == true) {
					System.out.println("1 - Desbloquear usuário");
				} else {
					System.out.println("1 - Bloquear usuário");
				}
				System.out.println("2 - Modificar permissão");
				System.out.println("0 - Sair");
				System.out.print("Digite a opção desejada: ");
				option = scanner1.nextInt();

				switch (option) {
				case 1:
					updatedUser.setBlocked(!updatedUser.getBlocked());
					userDAO.update(updatedUser);
					System.out.println("Operação realizada com sucesso");
					break;

				// Lembrar de controlar a entrada do usuário aqui pois pode
				// entrar com um valor inválido...
				case 2:
					int privilege;
					System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");
					System.out.println("Digite a opção desejada: ");
					privilege = scanner1.nextInt();

					switch (privilege) {
					case 1:
						updatedUser.setPermission(User.Permission.AUTHENTICATED);
						userDAO.update(updatedUser);
						System.out.println("Operação realizada com sucesso");
						break;
					case 2:
						updatedUser.setPermission(User.Permission.MODERATOR);
						userDAO.update(updatedUser);
						System.out.println("Operação realizada com sucesso");
						break;
					case 3:
						updatedUser.setPermission(User.Permission.ADMINISTRATOR);
						userDAO.update(updatedUser);
						System.out.println("Operação realizada com sucesso");
						break;
					default:
						System.out.println("O valor digitado é inválido");

					}

					break;

				default:
					System.out.println("Digite uma opção válida.");

				}

			} while (option != 0); }
			else {
				System.out.println("Usuário não encontrado.");
			}

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public User login() {
		
		scanner1 = new Scanner(System.in);
		UserDAO userDAO = new UserDAO();
		System.out.print("Digite o nome do usuário: ");
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
			return loggedUser;
		} catch (UserNotFoundException e) {

			e.printStackTrace();
		}
		return null;
		
	}
}
