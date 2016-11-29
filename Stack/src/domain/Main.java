package domain;

import java.util.Scanner;

import database.MySQLConnect;
import domain.User.Permission;

public class Main {

	public static void main(String[] args) {
		
		int opcao;
		Scanner scanner = new Scanner(System.in);		
		
		do {
			System.out.println("1 - Criar novo usuário");
			System.out.println("2 - Sair");
			System.out.print("Digite a opção desejada: ");
			opcao = scanner.nextInt();
			
			if (opcao == 1) {
				
				String username;
				String password;
				
				System.out.print("Digite o nome do usuário: ");
				username = scanner.next();
				System.out.print("Digite a senha: ");
				password = scanner.next();
				
				User user = new User(username, password, Permission.AUTHENTICATED);
				MySQLConnect connect = new MySQLConnect();
				connect.connect(user.getUsername(),user.getPassword());
			}
		}
		while (opcao != 2);
		
		scanner.close();
		System.out.println("Saindo...");
		
		System.exit(0);
		
		
	}

}
