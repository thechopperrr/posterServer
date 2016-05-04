package model;

import java.util.ArrayList;
import java.util.Date;

public class TempResource {
	public  ArrayList<Post> posts;
	public  ArrayList<User> users;
	public DataBaseProvider dataBase;
	
	public TempResource(){
		this.make();
		 dataBase = new DataBaseProvider();
	}
	
	
public void make(){
	posts = new ArrayList<Post>();
	users = new ArrayList<User>();
	
	User user1 = new User("hose@abv.bg", "123", "dadada");
	User user2 = new User("konsunela@abv.bg", "123", "dadada");
	
	users.add(user1);
	users.add(user2);
	posts = this.getPosts(15);
}

public ArrayList<Coment> makeComents(){
	ArrayList<Coment> ar = new ArrayList<Coment>();
	for(int i = 0; i < 5; i++){
		Coment c = new Coment( 0, users.get(0).getMail(),"coment"+i, new Date());
		ar.add(c);
	}
	return ar;
}

public ArrayList<String> getLikes(){
	ArrayList<String> ar = new ArrayList<String>();
	for(int i =0; i < 5; i ++){
		ar.add("like"+i);
	}
	return ar;
}

public ArrayList<Post> getPosts(int num){
	ArrayList<Post> arr = new ArrayList<Post>();
	for(int i=0; i< num; i++){
		Post post = new Post(i,users.get(0), "post text"+i, this.getLikes(), new Date(), this.makeComents());
		for(int j = 0; j<post.getComents().size(); j++){
			post.getComents().get(j).setPostId(post.getPostId());
		}
		arr.add(post);
	}
	return arr;
}

public ArrayList<Post> getNextFive(int start){
	ArrayList<Post> arr = new ArrayList<Post>();
	
	if(start > posts.size())
		return arr;
	int end = start + 5;
	if(end > posts.size())
		end = posts.size();
	for(int i=start; i<end; i ++){
		if(i > posts.size())
			break;
		else {
			arr.add(posts.get(i));
		}
	}
	return arr;
}

public String likePostWithId(long id, String user){
	for(int i=0; i< posts.size(); i++){
		if(posts.get(i).getPostId() == id){
			posts.get(i).getLikes().add(user);
			return "YES";
		}
			
	}
	return "NO";
}

public String disLikePostWithId(long id, String user){
	for(int i=0; i< posts.size(); i++){
		if(posts.get(i).getPostId() == id){
			if(posts.get(i).getLikes().remove(user))
				return "YES";
		}
			
	}
	return "NO";
}


public boolean isValidUser(User usr) throws Exception{
	System.out.println("isValidUser");
	User user = dataBase.getUser(usr.getMail());
	if(user != null){
		if(user.getPass().equals(usr.getPass())){
			return true;
		}
	}
	return false;
}

public User isValidUserGetInstance(User usr) throws Exception{
	System.out.println("isValidUserGetInstance");
	User user = dataBase.getUser(usr.getMail());
	if(user != null){
		if(user.getPass().equals(usr.getPass())){
			return user;
		}
	}
	return new User();
}

public boolean addComent(Coment coment){
	for(int i=0; i < this.posts.size(); i++){
		if(posts.get(i).getPostId() == coment.getPostId()){
			posts.get(i).getComents().add(coment);
			return true;
		}
	}
	return false;
}

public boolean addPost(Post post){
	this.posts.add(post);
	return true;
}

public String makeComent(Coment coment){
	if(this.addComent(coment))
		return "YES";
	else return "NO";
}

public String makePost(Post post) throws Exception{
	this.dataBase.addPost(post);
	if(this.addPost(post))
		return "YES";
	else return "NO";
}

public boolean isSuchUser(String name) throws Exception{
	
	System.out.println("isSuchUser");
	User tempUser = dataBase.getUser(name);
	if( tempUser == null){
	
		return false;
	}
	return true;
}

public boolean register(User usr) throws Exception{
	
	User tempUser = dataBase.getUser(usr.getMail());
	if( tempUser == null){
		System.out.println("adding user");
		dataBase.addUser(usr);
		//todo remove
		this.users.add(usr);
		return true;
	}
	else {
		System.out.println("user exists");
		return false;
	}
}

public String getPassForUser(String email) throws Exception{
	System.out.println("getPassForUser");
	User tempUser = dataBase.getUser(email);
	if( tempUser == null){
		return "error";
	}
	else {
		return tempUser.getPass();
	}
}

public boolean saveImageToUser(String url,String email) throws Exception{
	return dataBase.setUserImageUrl(email, url);
}
}


