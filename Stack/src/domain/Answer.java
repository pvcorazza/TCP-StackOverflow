package domain;

import java.util.Date;

public class Answer extends Post {

	private int id;
	private int id_question;
	private Boolean bestAnswer;
	
	

	public Answer(int id_user, String title, Date date_question, Boolean bestAnswer) {
		super();
		setId_author(id_user);
		setDate(date_question);
		this.bestAnswer = bestAnswer;
	}
	
	

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getBestAnswer() {
		return bestAnswer;
	}

	public void setBestAnswer(Boolean bestAnswer) {
		this.bestAnswer = bestAnswer;
	}

	public int getId_question() {
		return id_question;
	}

	public void setId_question(int id_question) {
		this.id_question = id_question;
	}


}
