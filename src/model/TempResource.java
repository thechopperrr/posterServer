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

public ArrayList<Post> getNextFive(int start) throws Exception{
	ArrayList<Post> arr = new ArrayList<Post>();
	ArrayList<Post> tempPosts = dataBase.getNextFivePosts(start);
	return tempPosts;
	
//	if(start > tempPosts.size())
//		return arr;
//	int end = start + 5;
//	if(end > tempPosts.size())
//		end = tempPosts.size();
//	for(int i=start; i<end; i ++){
//		if(i > tempPosts.size())
//			break;
//		else {
//			arr.add(tempPosts.get(i));
//		}
//	}
//	return arr;
}

public String likePostWithId(int id, String user) throws Exception{
	if(dataBase.likePost(id, user)){
		return "YES";
	}
	return "NO";
}

public String disLikePostWithId(int id, String user) throws Exception{
	if(dataBase.disLikePost(id, user)){
		return "YES";
	}
	return "NO";
}


public boolean isValidUser(User usr) throws Exception{
	System.out.println("isValidUser");
	User user = dataBase.getUser(usr.getMail());
	if(user != null){
		if(user.getPass().equals(usr.getPass())){
			if(dataBase.isValideUser(user.getMail()))
				return true;
		}
	}
	return false;
}

public boolean validateUser(String  userName) throws Exception{
	
	if(dataBase.validateUser(userName))
		return true;
	return false;
	
}

public User isValidUserGetInstance(User usr) throws Exception{
	System.out.println("isValidUserGetInstance");
	User user = dataBase.getUser(usr.getMail());
	if(user != null){
		if(user.getPass().equals(usr.getPass())){
			if(dataBase.isValideUser(user.getMail()))
				return user;
		}
	}
	return new User();
}

public boolean addComent(Coment coment) throws Exception{
	
	 return dataBase.addComent(coment);
}

public boolean addPost(Post post){
	this.posts.add(post);
	return true;
}

public String makeComent(Coment coment) throws Exception{
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


