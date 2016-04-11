package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Coment {
	
	private String userMail;
	private String comentText;
	private Date date;
	private long postId;
	
	public Coment(){
		
	}
	
	public Coment (long id, String mail, String text,Date postDate)
	{
		userMail = mail;
		comentText = text;
		date = postDate;
		postId = id;
	}
	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getComentText() {
		return comentText;
	}

	public void setComentText(String comentText) {
		this.comentText = comentText;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
	public String toJson(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String json = "{\"userMail\":\"" + this.userMail + "\", \"comentText\":\"" + this.comentText + "\", \"comentDate\":\""+df.format(this.date)+"\"}";
		return json;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}

}
