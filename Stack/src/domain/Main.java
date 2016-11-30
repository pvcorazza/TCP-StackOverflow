package domain;

import java.sql.SQLException;
import java.util.Scanner;

import dao.implementation.jdbc.UserDAO;
import database.MySQLConnect;
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
			System.out.println("2 - Sair");
			System.out.println("3 - Update");
			System.out.println("4 - Login");
			System.out.print("Digite a opção desejada: ");
			opcao = scanner.nextInt();
			
			if (opcao == 1) {
					
				String username;
				String password;
				
				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();
				
				user = new User(username, password, Permission.AUTHENTICATED);
				
				UserDAO userDAO = new UserDAO();
				try {
					int userID = userDAO.insert(user);
					user.setId(userID);
					//System.out.print("Usuario inserido\n");
				} catch (UserExceptionDAO e) {
		
					e.printStackTrace();
				}
			}
			if(opcao == 3){
				
				
				if(user != null){
					user.setPermission(User.Permission.ADMINISTRATOR);
					user.setBlocked(true);
					UserDAO userDAO = new UserDAO();
					try {
						userDAO.update(user);
						System.out.println("Realizou update\n");
					} catch (UserExceptionDAO e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			if(opcao == 4){
				String username;
				String password;
				
				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();
				
				UserDAO userDAO = new UserDAO();
				try {
					User loggedUser = userDAO.select(username, password);
					System.out.println("Login realizado");
					
					
					
					System.out.println("Id: "+loggedUser.getId());
					System.out.println("username: "+loggedUser.getUsername());
					System.out.println("Password: "+loggedUser.getPassword());
					System.out.println("Blocked: "+loggedUser.getBlocked());
					System.out.println("Permission: "+loggedUser.getPermission().getPermission());
				} catch (UserNotFoundException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		while (opcao != 2);
		
		scanner.close();
		System.out.println("Saindo...");
		
		System.exit(0);
		
		
	}

}
