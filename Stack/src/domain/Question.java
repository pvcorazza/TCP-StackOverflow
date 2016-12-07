package domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Question extends Post {
	
	private int id;
	private String title;
	private Boolean closed;
	private String tag1;
	private String tag2;
	private String tag3;
	private String tag4;
	private String tag5;
	
	public Question(int userId, String title, String text, Date date, String tag1, String tag2, String tag3, String tag4, String tag5) {
		super();
		this.setId_author(userId);
		this.title = title;
		this.setText(text);
		this.closed = false;
		this.setDate(date);
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.tag4 = tag4;
		this.tag5 = tag5;
	}
	
	public Question(int id, int idAuthor, String title, String text, Date date, Boolean closed, String tag1, String tag2, String tag3, String tag4,
			String tag5) {
		this.id = id;
		this.setId_author(idAuthor);
		this.title = title;
		this.setText(text);
		this.setDate(date);
		this.closed = closed;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.tag4 = tag4;
		this.tag5 = tag5;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public String getTag3() {
		return tag3;
	}
	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}
	public String getTag4() {
		return tag4;
	}
	public void setTag4(String tag4) {
		this.tag4 = tag4;
	}
	public String getTag5() {
		return tag5;
	}
	public void setTag5(String tag5) {
		this.tag5 = tag5;
	}
	
}
