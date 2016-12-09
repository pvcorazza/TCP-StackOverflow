package business;

import domain.Question;
import domain.User;

public class OperationPermission {

	public static boolean modifyUserPermission(User loggedUser) {
		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR) {

			return true;
		} else {
			return false;
		}

	}
	
	public static boolean createQuestion(User loggedUser){
		
		if(loggedUser != null){
			
			if(loggedUser.getPermission() != User.Permission.NOT_AUTHENTICATED){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public static boolean searchQuestion(User loggedUser){
		
		return true;	//todos podem pesquisar uma quest�o

	}


	public static boolean displayPostOptions(User loggedUser) {

		if (loggedUser == null) {
			return false;
		}
		else{
			if (loggedUser.getPermission() != User.Permission.NOT_AUTHENTICATED && loggedUser.getBlocked() == false) {
				return true;
			} 
			else {
				return false;
			}
			
		}
		
	}

	public static boolean selectBestAnswer(User loggedUser, Question question) {
		if (loggedUser.getId() == question.getIdAuthor()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean blockUser(User loggedUser) {
		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR
				|| loggedUser.getPermission() == User.Permission.MODERATOR) {
			return true;

		} else {
			return false;
		}
	}

	public static boolean answerQuestion(User loggedUser, Question question) {

		if (!question.getClosed()) {
			if (loggedUser.getPermission() != User.Permission.NOT_AUTHENTICATED) {
				return true;
			}
		}

		return false;
	}

	public static boolean commentQuestion(User loggedUser, Question question) {

		if (!question.getClosed()) {
			if (loggedUser.getPermission() != User.Permission.NOT_AUTHENTICATED) {
				return true;
			}
		}

		return false;
	}

	public static boolean commentAnswer(User loggedUser, Question question) {

		if (!question.getClosed()) {
			if (loggedUser.getPermission() != User.Permission.NOT_AUTHENTICATED) {
				return true;
			}
		}

		return false;
	}

	public static boolean editOrDeletePost(User loggedUser, int id) {
		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR
				|| loggedUser.getPermission() == User.Permission.MODERATOR
				|| id == loggedUser.getId()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isModOrAdmin(User loggedUser) {
		if (loggedUser.getPermission() == User.Permission.ADMINISTRATOR
				|| loggedUser.getPermission() == User.Permission.MODERATOR) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean deleteQuestion(User loggedUser,Question question){
		if(loggedUser.getPermission() == User.Permission.ADMINISTRATOR 
				|| loggedUser.getPermission() == User.Permission.MODERATOR
				|| question.getAuthor().getId() == loggedUser.getId()){
			
			return true;
		}
		else{
			return false;
		}
	}
}