package domain;

public class Answer extends Post {

	private int id;
	private Boolean bestAnswer;

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

}
