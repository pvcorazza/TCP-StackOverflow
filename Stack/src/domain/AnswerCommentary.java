package domain;

import java.util.Date;

public class AnswerCommentary extends Post {

	private int id;
	private int idAnswer;
	
	public AnswerCommentary() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public AnswerCommentary(int idAuthor, int idQuestion, String text, Date date) {
		super();
		this.setIdAuthor(idAuthor);
		this.idAnswer = idQuestion;
		this.setText(text);
		this.setDate(date);
	}
	
	public AnswerCommentary(int idAuthor, int idQuestion, String text, Date date, User author) {
		super();
		this.setIdAuthor(idAuthor);
		this.idAnswer = idQuestion;
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
	public int getIdAnswer() {
		return idAnswer;
	}
	public void setIdAnswer(int idAnswer) {
		this.idAnswer = idAnswer;
	}

	

}
