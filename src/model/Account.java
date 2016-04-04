package model;

public class Account {
	private User user;
	//private ArrayList<Document> documents;
	private String data;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
