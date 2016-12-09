package stackoverflow.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import business.Management;
import business.OperationPermission;
import database.exception.DatabaseConnectionException;
import database.exception.DatabaseException;
import database.exception.DatabaseUserDuplicated;
import domain.Answer;
import domain.AnswerCommentary;
import domain.Question;
import domain.QuestionCommentary;
import domain.User;
import domain.User.Permission;
import exceptions.permission.PermissionException;
import exceptions.userDAO.UserNotFoundException;
import ui.text.UIUtils;

public class TextForm {

	public static final int TAG = 1;
	public static final int TITLE = 2;
	public static final int DATE = 3;
	public static final int AUTHOR = 4;

	private Scanner scanner;

	/* Classe principal */
	public void showForm() {

		Scanner scanner = new Scanner(System.in);

		int initialMenuOption;

		do {

			initialMenuOption = showOptionsInitialMenu();

		} while (initialMenuOption != 0);

		scanner.close();
		System.exit(0);
	}

	/* Classe para seleção das opções do menu inicial */
	public int showOptionsInitialMenu() {

		User newUser, loggedUser = null;

		System.out.println("### INF UFRGS - Stack Overflow ###");
		System.out.println("   =============================");
		System.out.println("   |     1 - Login             |");
		System.out.println("   |     2 - Cadastro          |");
		System.out.println("   |     3 - Buscar Questão    |");
		System.out.println("   |     0 - Sair              |");
		System.out.println("   =============================\n");

		int initialMenuOption = getInput();

		Management m = new Management();
		switch (initialMenuOption) {

		case 1: // Login

			try {
				loggedUser = m.login(getUsername(), getPassword());

			} catch (UserNotFoundException e) {
				System.out.println(e.getMessage());

			} catch (DatabaseConnectionException e) {
				System.out.println(e.getMessage());

			} catch (DatabaseException e) {
				System.out.println(e.getMessage());

			}

			if (loggedUser != null) {
				System.out.println(UIUtils.INSTANCE.getTextManager().getText("message.login.success"));

				printInfoUser(loggedUser);

				int authenticatedOption;
				do {

					authenticatedOption = showOptionsLoggedMenu(loggedUser);
				}

				while (authenticatedOption != 0);
			}
			break;

		case 2: // Cadastro

			newUser = new User(getUsername(), getPassword(), Permission.AUTHENTICATED);
			try {
				m.createUser(newUser);
				System.out.println("Usuário cadastrado com sucesso!");
			} catch (DatabaseConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseUserDuplicated e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 3:
			if (OperationPermission.searchQuestion(loggedUser)) {
				// Buscar questao sem estar logado loggedUser = null
				showSearchQuestionMenu(null);
			}

			break;

		case 0: // Sair

			System.out.println("Saindo...");
			break;

		default:
			System.out.println("Digite uma opção válida.");
		}

		return initialMenuOption;
	}

	public void showSearchQuestionMenu(User loggedUser) {

		Management m = new Management();

		System.out.println("1 - Tag");
		System.out.println("2 - Título");
		System.out.println("3 - Data");
		System.out.println("4 - Autor");
		System.out.println("0 - Sair");

		int opcaoBuscar;
		opcaoBuscar = getInput();
		ArrayList<Question> obtainedQuestions = null;

		switch (opcaoBuscar) {

		case 1:
			System.out.print("Digite uma tag: ");
			String searchTag;
			searchTag = getStringInput();
			try {
				obtainedQuestions = m.searchQuestion(searchTag, TAG);
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 2:
			System.out.print("Digite um título: ");
			String searchTitle;
			searchTitle = getStringInput();
			try {
				obtainedQuestions = m.searchQuestion(searchTitle, TITLE);
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());
			}
			break;

		case 3:

			String searchDate;
			String searchDia;
			String searchMes;
			String searchAno;

			System.out.print("Dia (dd): ");
			searchDia = getStringInput();
			System.out.print("Mes (mm): ");
			searchMes = getStringInput();
			System.out.print("Ano (aaaa): ");
			searchAno = getStringInput();

			searchDate = searchAno + "/" + searchMes + "/" + searchDia;
			try {
				obtainedQuestions = m.searchQuestion(searchDate, DATE);
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());
			}
			break;

		case 4:
			System.out.print("Digite um autor: ");
			String searchAuthor;
			searchAuthor = getStringInput();
			try {
				obtainedQuestions = m.searchQuestion(searchAuthor, AUTHOR);
			} catch (DatabaseException e) {
				System.out.println(e.getMessage());
			}
			break;

		case 0:

			break;
		}

		if (obtainedQuestions != null) {
			for (Question question : obtainedQuestions) {
				System.out.println("-------------------------------");
				System.out.println("Id: " + question.getId() + "\nTítulo: " + question.getTitle() + "\nAutor: "
						+ question.getAuthor().getUsername() + "\nData: " + question.getDate().toString());
				System.out.println("-------------------------------");
			}

			displayQuestion(loggedUser);
		}
	}

	public void displayQuestion(User loggedUser) {

		Management m = new Management();
		Question question;
		ArrayList<Answer> obtainedAnswers;
		ArrayList<QuestionCommentary> obtainedQuestionCommentaries;
		ArrayList<AnswerCommentary> obtainedAnswerCommentaries;

		int id;
		id = getInputId();
		question = m.getQuestion(id);
		obtainedAnswers = m.getAnswers(id);
		obtainedQuestionCommentaries = m.getQuestionCommentaries(id);
		obtainedAnswerCommentaries = m.getAnswerCommentaries(id);

		try {
		System.out.println("------------ QUESTÃO -----------");
		System.out.println("| Título: " + question.getTitle() + "\n| Autor: " + question.getAuthor().getUsername()
				+ "\n| Data: " + question.getDate().toString());
		System.out.println("| Texto: " + question.getText());

		System.out.println("--------------------------------");

		if (!obtainedQuestionCommentaries.isEmpty()) {
			System.out.println("---- COMENTÁRIOS DA QUESTÃO ----");
		}

		for (QuestionCommentary questionCommentary : obtainedQuestionCommentaries) {
			System.out.println("| Id: " + questionCommentary.getId());
			System.out.println("| Data: " + questionCommentary.getDate().toString());
			System.out.println("| Autor: " + questionCommentary.getAuthor().getUsername());
			System.out.println("| Texto: " + questionCommentary.getText());
			System.out.println("--------------------------------");
		}

		if (obtainedAnswers.size() == 0) {
			System.out.println("Não tem respostas");
		}
		for (Answer answer : obtainedAnswers) {
			System.out.println("---------- RESPOSTA -----------");
			obtainedAnswerCommentaries = m.getAnswerCommentaries(answer.getId());
			if (answer.getBestAnswer()) {
				System.out.println("\t| *ESCOLHIDA COMO MELHOR RESPOSTA PELO AUTOR");
			}
			System.out.println("\t| Id: " + answer.getId());
			System.out.println("\t| Data: " + answer.getDate().toString());
			System.out.println("\t| Autor: " + answer.getAuthor().getUsername());
			System.out.println("\t| Texto: " + answer.getText());
			System.out.println("-------------------------------");
			if (!obtainedAnswerCommentaries.isEmpty()) {
				System.out.println("-------- COMENTÁRIOS DA RESPOSTA --------");
			}
			for (AnswerCommentary answerCommentary : obtainedAnswerCommentaries) {
				System.out.println("\t\t| Id: " + answerCommentary.getId());
				System.out.println("\t\t| Data: " + answerCommentary.getDate().toString());
				System.out.println("\t\t| Autor: " + answerCommentary.getAuthor().getUsername());
				System.out.println("\t\t| Texto: " + answerCommentary.getText());
				System.out.println("-------------------------------");
			}

		}

		if (OperationPermission.displayPostOptions(loggedUser)) {
			showPostOptions(loggedUser, question, obtainedAnswers, obtainedQuestionCommentaries,
					obtainedAnswerCommentaries);
		} else {
			System.out.println("Por favor, faça o login para realizar outras operações");
		}

		System.out.println("--------------------------------");
		}
		catch (Exception e) {
			System.out.println("Questão inválida. Impossível exibir");
		}

	}

	public void showPostOptions(User loggedUser, Question question, ArrayList<Answer> obtainedAnswers,
			ArrayList<QuestionCommentary> obtainedQuestionCommentaries,
			ArrayList<AnswerCommentary> obtainedAnswerCommentaries) {

		Management m = new Management();
		int postOption;

		System.out.println("   ====================================================");
		System.out.println("   |     1 - Responder questão                        |");
		System.out.println("   |     2 - Comentar questão                         |");
		System.out.println("   |     3 - Comentar resposta                        |");
		System.out.println("   |     4 - Excluir questão                          |");
		System.out.println("   |     5 - Excluir resposta                         |");
		System.out.println("   |     6 - Excluir comentário de questão            |");
		System.out.println("   |     7 - Excluir comentário de resposta           |");
		System.out.println("   |     8 - Editar questão                           |");
		System.out.println("   |     9 - Editar resposta                          |");
		System.out.println("   |     10 - Editar comentário de questão            |");
		System.out.println("   |     11 - Editar comentário de resposta           |");

		if (question.getIdAuthor() == loggedUser.getId()) {
			System.out.println("   |     12 - Selecionar a melhor resposta            |");
		}

		System.out.println("   |     0 - Sair                                     |");
		System.out.println("   ====================================================\n");

		postOption = getInput();

		switch (postOption) {

		case 1:
			if (OperationPermission.answerQuestion(loggedUser, question)) {
				showCreateAnswerOptions(loggedUser, question.getId());
			} else {
				System.out.println("Permissão negada ou a questão está fechada");
			}
			break;

		case 2:
			if (OperationPermission.commentQuestion(loggedUser, question)) {
				showCreateQuestionCommentaryOptions(loggedUser, question.getId());
			} else {
				System.out.println("Permissão negada ou a questão está fechada");
			}

			break;
		case 3:

			if (OperationPermission.commentAnswer(loggedUser, question)) {
				showCreateAnswerCommentaryOptions(loggedUser);
			} else {
				System.out.println("Permissão negada ou a questão está fechada");
			}
			break;

		case 4:

			if (OperationPermission.editOrDeletePost(loggedUser, question.getId())) {
				try {
					m.deleteQuestion(loggedUser,question);
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				}
			} else {

				System.out.println("Usuário não tem permissão para executar este comando");
			}
			break;

		case 5:
			showDeleteAnswerOptions(loggedUser, obtainedAnswers);
			break;

		case 6:
			showDeleteCommentaryQuestionOptions(loggedUser, obtainedQuestionCommentaries);
			break;

		case 7:
			showDeleteCommentaryAnswerOptions(loggedUser, obtainedAnswerCommentaries);
			break;

		case 8:
			showUpdateQuestionOptions(loggedUser, question);
			break;

		case 9:
			showUpdateAnswerOptions(loggedUser, obtainedAnswers);
			break;

		case 10:
			showUpdateQuestionCommentaryOptions(loggedUser, obtainedQuestionCommentaries);
			break;

		case 11:
			showUpdateAnswerCommentaryOptions(loggedUser, obtainedAnswerCommentaries);
			break;

		case 12:
			if (question.getIdAuthor() != loggedUser.getId()) {
				System.out.println("Opção inválida");
				break;

			}
			if (OperationPermission.selectBestAnswer(loggedUser, question)) {
				showSelectBestAnswerOptions(obtainedAnswers);
			} else {
				System.out.println("Não é possível selecionar como melhor resposta em uma questão que não é sua");
			}

			break;
		default:
			System.out.println("Opção inválida");

		}
	}

	private void showUpdateQuestionOptions(User loggedUser, Question questionToUpdate) {
		Management m = new Management();
		String questionText;
		System.out.print("Digite um novo texto para a pergunta: ");
		questionText = getStringInput();
		boolean foundQuestionId = false;
		
		questionToUpdate.setText(questionText);
		
		if (OperationPermission.editOrDeletePost(loggedUser, questionToUpdate.getIdAuthor())) {
			foundQuestionId = true;

			if (foundQuestionId == true) {
				m.updateQuestion(questionToUpdate);
			} else {
				System.out.println("Question não encontrada.");
			}

		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
	}
	}

	private void showUpdateAnswerOptions(User loggedUser, ArrayList<Answer> answers) {

		Management m = new Management();
		String answerText;
		int answerOption;
		boolean foundAnswerId = false;
		Answer answerToUpdate = null;

		System.out.print("Digite o id da resposta: ");
		answerOption = scanner.nextInt();

		System.out.println("Digite um novo texto para a resposta: ");
		answerText = getStringInput();

		for (Answer answer : answers) {
			if (answerOption == answer.getId()) {
				answerToUpdate = answer;
				foundAnswerId = true;
			}
		}

		answerToUpdate.setText(answerText);

		if (OperationPermission.editOrDeletePost(loggedUser, answerToUpdate.getIdAuthor())) {

			if (foundAnswerId == true) {
				m.updateAnswer(answerToUpdate);
			} else {
				System.out.println("Resposta não encontrada.");
			}

		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}
	}

	private void showUpdateQuestionCommentaryOptions(User loggedUser, ArrayList<QuestionCommentary> questionCommentaries) {
		
		Management m = new Management();

		String questionCommentaryText;
		int questionCommentaryOption;
		boolean foundQuestionCommentaryId = false;
		QuestionCommentary questionCommentaryToUpdate = null;

		System.out.print("Digite o id do comentário da questão: ");
		questionCommentaryOption = scanner.nextInt();

		System.out.println("Digite um novo texto para o comentário da questão: ");
		questionCommentaryText = getStringInput();

		for (QuestionCommentary questionCommentary : questionCommentaries) {
			if (questionCommentaryOption == questionCommentary.getId()) {
				questionCommentaryToUpdate = questionCommentary;
				foundQuestionCommentaryId = true;
			}
		}

		questionCommentaryToUpdate.setText(questionCommentaryText);

		if (OperationPermission.editOrDeletePost(loggedUser, questionCommentaryToUpdate.getIdAuthor())) {
			if (foundQuestionCommentaryId == true) {
				m.updateQuestionCommentary(questionCommentaryToUpdate);
			} else {
				System.out.println("Resposta para a questão não encontrada.");
			}

		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}

	}
	
	private void showUpdateAnswerCommentaryOptions(User loggedUser, ArrayList<AnswerCommentary> answerCommentaries) {
		
		Management m = new Management();
		String answerCommentaryText;
		int answerCommentaryOption;
		boolean foundAnswerCommentaryId = false;
		AnswerCommentary answerCommentaryToUpdate = null;

		System.out.print("Digite o id do comentário da resposta: ");
		answerCommentaryOption = scanner.nextInt();

		System.out.println("Digite um novo texto para o comentário da resposta: ");
		answerCommentaryText = getStringInput();

		for (AnswerCommentary answerCommentary : answerCommentaries) {
			if (answerCommentaryOption == answerCommentary.getId()) {
				answerCommentaryToUpdate = answerCommentary;
				foundAnswerCommentaryId = true;
			}
		}

		answerCommentaryToUpdate.setText(answerCommentaryText);

		if (OperationPermission.editOrDeletePost(loggedUser, answerCommentaryToUpdate.getIdAuthor())) {
			if (foundAnswerCommentaryId == true) {
				m.updateAnswerCommentary(answerCommentaryToUpdate);
			} else {
				System.out.println("Comentário para a resposta não encontrada.");
			}

		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}

	}

	private void showSelectBestAnswerOptions(ArrayList<Answer> obtainedAnswers) {

		Management m = new Management();

		int option;
		boolean foundAnswerId = false;
		System.out.println("Digite o id da melhor resposta: ");
		option = scanner.nextInt();
		for (Answer answer : obtainedAnswers) {
			if (option == answer.getId()) {
				foundAnswerId = true;
			}
		}

		if (foundAnswerId == true) {
			m.selectBestAnswer(option);
		} else {
			System.out.println("Resposta não encontrada.");
		}

	}

	private void showDeleteAnswerOptions(User loggedUser, ArrayList<Answer> obtainedAnswers) {

		Management m = new Management();
		int answerOption, idAuthor = -1;
		boolean foundAnswerId = false;

		System.out.print("Digite o id da resposta: ");
		answerOption = scanner.nextInt();

		for (Answer answer : obtainedAnswers) {
			if (answerOption == answer.getId()) {
				idAuthor = answer.getIdAuthor();
				foundAnswerId = true;
			}
		}

		if (OperationPermission.editOrDeletePost(loggedUser, idAuthor)) {

			if (foundAnswerId == true) {
				m.deleteAnswer(answerOption);
			} else {
				System.out.println("Resposta não encontrada.");
			}

		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}
	}

	private void showDeleteCommentaryQuestionOptions(User loggedUser,
			ArrayList<QuestionCommentary> obtainedQuestionCommentaries) {

		Management m = new Management();
		int questionCommentaryOption, idAuthor = -1;
		boolean foundQuestionCommentaryId = false;

		System.out.print("Digite o id do comentário da questão: ");
		questionCommentaryOption = scanner.nextInt();

		for (QuestionCommentary commentaryQuestion : obtainedQuestionCommentaries) {
			if (questionCommentaryOption == commentaryQuestion.getId()) {
				idAuthor = commentaryQuestion.getIdAuthor();
				foundQuestionCommentaryId = true;
			}
		}
		if (OperationPermission.editOrDeletePost(loggedUser, idAuthor)) {
			if (foundQuestionCommentaryId == true) {
				m.deleteQuestionCommentary(questionCommentaryOption);
			} else {
				System.out.println("Comentário da questão não encontrado.");
			}
		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}

	}

	private void showDeleteCommentaryAnswerOptions(User loggedUser,
			ArrayList<AnswerCommentary> obtainedAnswerCommentaries) {

		Management m = new Management();
		int answerCommentaryOption, idAuthor = -1;
		boolean foundAnswerCommentaryId = false;

		System.out.print("Digite o id do comentário da questão: ");
		answerCommentaryOption = scanner.nextInt();

		for (AnswerCommentary commentaryAnswer : obtainedAnswerCommentaries) {
			if (answerCommentaryOption == commentaryAnswer.getId()) {
				idAuthor = commentaryAnswer.getIdAuthor();
				foundAnswerCommentaryId = true;
			}
		}
		if (OperationPermission.editOrDeletePost(loggedUser, idAuthor)) {
			if (foundAnswerCommentaryId == true) {
				try {
					m.deleteAnswerCommentary(answerCommentaryOption);
				} catch (DatabaseException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Comentário da resposta não encontrado.");
			}
		} else {
			System.out.println("Usuário não tem permissão para executar este comando");
		}

	}

	// private void showDeleteAnswerOptions(ArrayList<Answer> obtainedAnswers) {
	//
	// Management m = new Management();
	// int answerOption;
	// boolean foundAnswerId = false;
	//
	// System.out.print("Digite o id da resposta: ");
	// answerOption = scanner.nextInt();
	//
	// for (Answer answer : obtainedAnswers) {
	// if (answerOption == answer.getId()) {
	// foundAnswerId = true;
	// }
	// }
	//
	// if (foundAnswerId==true) {
	// m.deleteAnswer(answerOption);
	// }
	// else {
	// System.out.println("Resposta não encontrada.");
	// }
	// }

	public void showCreateAnswerOptions(User loggedUser, int idQuestion) {

		Management m = new Management();
		Answer newAnswer;
		String text;

		System.out.print("Digite a resposta: ");
		text = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newAnswer = new Answer(loggedUser.getId(), idQuestion, text, date, false);

		try {
			m.createAnswer(newAnswer);

		} catch (DatabaseConnectionException e) {
			System.out.println(e.getMessage());

		} catch (DatabaseException e) {
			System.out.println(e.getMessage());

		}

	}

	public void showCreateQuestionCommentaryOptions(User loggedUser, int idQuestion) {

		Management m = new Management();
		QuestionCommentary newQuestionCommentary;
		String text;

		System.out.print("Digite o comentário: ");
		text = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newQuestionCommentary = new QuestionCommentary(loggedUser.getId(), idQuestion, text, date);
		m.createQuestionCommentary(newQuestionCommentary);

	}

	public void showCreateAnswerCommentaryOptions(User loggedUser) {

		Management m = new Management();
		AnswerCommentary newAnswerCommentary;
		String text;
		int idAnswer;

		System.out.println("Digite o id da resposta: ");
		idAnswer = scanner.nextInt();
		System.out.print("Digite o comentário: ");
		text = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newAnswerCommentary = new AnswerCommentary(loggedUser.getId(), idAnswer, text, date);
		m.createAnswerCommentary(newAnswerCommentary);

	}

	/* Classe para seleção das opções do menu de usuário logado */
	public int showOptionsLoggedMenu(User loggedUser) {
		Management m = new Management();

		System.out.println("   =======================================================");
		System.out.println("   |     1 - Definir permissão de um usuário             |");
		System.out.println("   |     2 - Criar questão                               |");
		System.out.println("   |     3 - Buscar questão                              |");
		// System.out.println(" | 4 - Visualizar minhas perguntas |");
		System.out.println("   |     0 - Sair                                        |");
		System.out.println("   =======================================================\n");

		int authenticatedOption;
		authenticatedOption = getInput();
		switch (authenticatedOption) {

		case 1: // Definir permissão de um usuário

			if (OperationPermission.modifyUserPermission(loggedUser)) {
				User updatedUser;
				updatedUser = m.findUserToUpdate(loggedUser, getUsername());

				int updateOption;

				do {
					updateOption = showUpdateOptions(loggedUser, updatedUser);
				} while (updateOption != 0);
			} else {
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}

			break;

		// Criar Questão
		case 2:

			if (OperationPermission.createQuestion(loggedUser)) {
				showCreateQuestionOptions(loggedUser);
			} else {
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}

			break;

		case 3:
			if (OperationPermission.searchQuestion(loggedUser)) {
				showSearchQuestionMenu(loggedUser);
			} else {
				System.out.println("Usuário não possui permissão para realizar esta operação");
			}
			break;

		case 4:
			// m.getQuestionsOfUser(id);

		case 0:
			System.out.println("Saindo...");
			break;
		default:
			System.out.println("Digite uma opção válida...");
		}
		return authenticatedOption;

	}

	/* Classe para seleção das opções para atualizar permissões de um usuário */
	public int showUpdateOptions(User loggedUser, User userToUpdate) {

		Management m = new Management();

		System.out.println("   =======================================================");
		if (userToUpdate.getBlocked() == true) {
			System.out.println("   |     1 - Desbloquear usuário                         |");
		} else {
			System.out.println("   |     1 - Bloquear usuário                            |");
		}
		System.out.println("   |     2 - Modificar permissão                         |");
		System.out.println("   |     0 - Sair                                        |");
		System.out.println("   =======================================================\n");

		int updateOption;
		updateOption = getInput();

		switch (updateOption) {

		case 1:

			if (OperationPermission.blockUser(loggedUser)) {

				userToUpdate.setBlocked(!userToUpdate.getBlocked());

				try {
					m.updatePermission(loggedUser, userToUpdate);
				} catch (PermissionException e) {
					System.out.println(e.getMessage());
				} catch (DatabaseConnectionException e) {
					System.out.println(e.getMessage());
				} catch (DatabaseUserDuplicated e) {
					System.out.println(e.getMessage());
				}

				printMessageSuccess();

			} else {
				System.out.println("Usuário não autorizado");
			}

			break;

		// Lembrar de controlar a entrada do usuário aqui
		// pois
		// pode
		// entrar com um valor inválido...
		case 2:

			if (OperationPermission.blockUser(loggedUser)) {
				userToUpdate.setBlocked(!userToUpdate.getBlocked());
				setPermissions(loggedUser, userToUpdate);
			} else {
				System.out.println("Você não tem permissão para realizar a operação");
			}

		case 0:

			System.out.println("Saindo...");

			break;

		default:
			System.out.println("Digite uma opção válida.");
		}
		return updateOption;
	}

	/*
	 * Classe para seleção das opções para atualizar a permissão de um usuário
	 */
	public void setPermissions(User logedUser, User updatedUser) {

		Management m = new Management();
		System.out.println("AUTHENTICATED(1),MODERATOR(2),ADMINISTRATOR(3)");

		int privilege;
		privilege = getInput();

		switch (privilege) {

		case 1:
			updatedUser.setPermission(User.Permission.AUTHENTICATED);
			try {
				m.updatePermission(logedUser, updatedUser);
				printMessageSuccess();
			} catch (PermissionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseConnectionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseUserDuplicated e) {
				System.out.println(e.getMessage());
			}

			break;

		case 2:
			updatedUser.setPermission(User.Permission.MODERATOR);
			try {
				m.updatePermission(logedUser, updatedUser);
				printMessageSuccess();
			} catch (PermissionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseConnectionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseUserDuplicated e) {
				System.out.println(e.getMessage());
			}

			break;

		case 3:
			updatedUser.setPermission(User.Permission.ADMINISTRATOR);
			try {
				m.updatePermission(logedUser, updatedUser);
				printMessageSuccess();
			} catch (PermissionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseConnectionException e) {
				System.out.println(e.getMessage());
			} catch (DatabaseUserDuplicated e) {
				System.out.println(e.getMessage());
			}

			break;
		default:
			printMessageInvalid();
		}
	}

	public void showCreateQuestionOptions(User loggedUser) {

		Management m = new Management();
		Question newQuestion;

		String title;
		String text;

		String tag1, tag2, tag3, tag4, tag5;
		System.out.print("Digite um título para a questão: ");
		title = getStringInput();
		System.out.print("Digite a questão: ");
		text = getStringInput();
		System.out.print("Digite no mínimo uma tag para a questão: ");
		System.out.print("Digite a tag 1: ");
		tag1 = getStringInput();
		System.out.print("Digite a tag 2: ");
		tag2 = getStringInput();
		System.out.print("Digite a tag 3: ");
		tag3 = getStringInput();
		System.out.print("Digite a tag 4: ");
		tag4 = getStringInput();
		System.out.print("Digite a tag 5: ");
		tag5 = getStringInput();

		Date date = new Date(System.currentTimeMillis());
		newQuestion = new Question(loggedUser.getId(), title, text, date, tag1, tag2, tag3, tag4, tag5);
		newQuestion.setAuthor(loggedUser);

		try {
			m.createQuestion(newQuestion);
		} catch (DatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (PermissionException e) {
			System.out.println(e.getMessage());
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
		}

	}

	public void printInfoUser(User loggedUser) {
		System.out.println("Id: " + loggedUser.getId());
		System.out.println("username: " + loggedUser.getUsername());
		System.out.println("Blocked: " + loggedUser.getBlocked());
		System.out.println("Permission: " + loggedUser.getPermission().getPermission());
	}

	public String getUsername() {
		System.out.print("Digite o nome do usuário: ");
		return getStringInput();

	}

	public String getPassword() {
		System.out.print("Digite a senha: ");
		return getStringInput();
	}

	public int getInput() {
		int opcao = -1;
		scanner = new Scanner(System.in);
		System.out.print("Digite a opção desejada: ");
		try {
		opcao = scanner.nextInt();
		}
		catch (Exception e) {
			System.out.println("O número digitado é inválido.");
			getInput();
		}
		return opcao;
	}

	public int getInputId() {
		int opcao=-1;
		scanner = new Scanner(System.in);
		System.out.print("Digite o ID da questão para visualizar: ");
		try {
		opcao = scanner.nextInt();
		}
		catch (Exception e) {
			System.out.println("O número digitado é inválido.");
			getInput();
		}
		return opcao;
	}

	public String getStringInput() {
		String input=null;
		scanner = new Scanner(System.in);
		try {
			input = scanner.nextLine();
		}
		catch (Exception e) {
			System.out.println("Entrada inválida");
			getStringInput();
		}
		
		return input;
	}

	public void printMessageInvalid() {
		System.out.println("O valor digitado é inválido.");
	}

	public void printMessageSuccess() {
		System.out.println("Operação realizada com sucesso");
	}
}
