package domain;

import java.util.Date;

public class AnswerCommentary extends Post {

	int id,id_answer;

	public AnswerCommentary(int id, int id_answer) {
		super();
		this.id = id;
		this.id_answer = id_answer;
	}

	public AnswerCommentary() {
		super();
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_answer() {
		return id_answer;
	}

	public void setId_answer(int id_answer) {
		this.id_answer = id_answer;
	}

	

}
