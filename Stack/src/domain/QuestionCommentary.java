package domain;

import java.util.Date;

public class QuestionCommentary extends Post {
	
	private int id;
	private int idQuestion;
	
	public QuestionCommentary() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public QuestionCommentary(int idAuthor, int idQuestion, String text, Date date) {
		super();
		this.setIdAuthor(idAuthor);
		this.idQuestion = idQuestion;
		this.setText(text);
		this.setDate(date);
	}
	
	public QuestionCommentary(int idAuthor, int idQuestion, String text, Date date, User author) {
		super();
		this.setIdAuthor(idAuthor);
		this.idQuestion = idQuestion;
		this.setText(text);
		this.setDate(date);
		this.setAuthor(author);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

}
