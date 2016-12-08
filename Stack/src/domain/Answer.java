package domain;

import java.util.Date;

public class Answer extends Post {

	private int id;
	private int idQuestion;
	private Boolean bestAnswer;
	
	

	public Answer(int id_user, int idQuestion, String text, Date date_question, Boolean bestAnswer) {
		super();
		setIdAuthor(id_user);
		setIdQuestion(idQuestion);
		setText(text);
		setDate(date_question);
		this.bestAnswer = bestAnswer;
	}
	
	public Answer(int id_user, int idQuestion, String text, Date date_question, Boolean bestAnswer, User author) {
		super();
		setIdAuthor(id_user);
		setIdQuestion(idQuestion);
		setText(text);
		setDate(date_question);
		this.bestAnswer = bestAnswer;
		setAuthor(author);
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

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int id_question) {
		this.idQuestion = id_question;
	}


}
