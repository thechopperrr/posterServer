package model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class Post {
	
	private User user;
	private String postText;
	private ArrayList<String> likes;
	private Date postDate;
	private ArrayList<Coment> coments;
	private long postId;

	public Post (long postId, User usr, String pstTxt,ArrayList<String> lks, Date date, ArrayList<Coment> cmnts ){
		user = usr;
		postText = pstTxt;
		likes = lks;
		postDate = date;
		coments = cmnts;
		this.postId = postId; 
	}
	
	public Post(){
		
	}
	
	public String toJson() throws JsonParseException, JsonMappingException, IOException{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date = df.format(this.postDate);
		String json = "{\"user\":" + this.getUser().toJson() + ", \"postText\":\"" + this.postText + "\", \"postDate\":\""+date+"\", \"likes\":\""+this.getLikes() +"\", \"coments\":"+this.comentsToJson()+"}";
		return json;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public ArrayList<String> getLikes() {
		return likes;
	}

	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public List<Coment> getComents() {
		return coments;
	}

	public void setComents(ArrayList coments) {
		this.coments = coments;
	}
	
	private String comentsToJson(){
		String coments = "[";
		for(int i = 0; i< this.coments.size(); i ++){
			coments = coments + this.coments.get(i).toJson();
			if(i < (this.coments.size()-1)){
				coments = coments + ",";
			}
		}
		coments = coments + "]";
		return coments;
		
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}
}
