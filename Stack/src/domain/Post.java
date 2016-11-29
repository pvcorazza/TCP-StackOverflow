package domain;

import java.util.Date;

public abstract class Post {
	
	private String text;
	private int idAuthor;		//id do usuario que fez o post
	private Date date;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId_author() {
		return idAuthor;
	}

	public void setId_author(int id_author) {
		this.idAuthor = id_author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
