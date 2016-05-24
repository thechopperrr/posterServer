package model;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

//database name: poster_db
public class DataBaseProvider {
	private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  private static final String mysqlDomain = "jdbc:mysql://localhost/poster_db"; 
	  private static final String mysqlUser = "root"; 
	  private static final String mysqlPass = "";
	  
	  public boolean addPost (Post post) throws Exception
	  {
		  boolean status = false;
		  try {
		      connect = connect();
		      statement = connect.createStatement();
		      String query ="INSERT INTO poster_db.Posts VALUES('"+post.getUser().getMail() +"','"+post.getPostText() +"','"+post.likesToString() +"',NOW(),'"+this.getNextMaxPostId() +"');";
		      System.out.println(query);
		     int result = statement.executeUpdate(query);
		      if(result == 1)
		    	  status = true;
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return status;
	  }
	  
	public int getNextMaxPostId() throws Exception
	{
		  int result = 0;
		  try {
			  
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT MAX(postId) FROM Posts;";
		      System.out.println(query);
		      ResultSet resultS = statement.executeQuery(query);
		      while (resultS.next()) {
		    	  result = ((Number)resultS.getObject(1)).intValue();;
		    	 System.out.println(result);
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		    	close();
		    }
		  return result + 1;
	  }
	
	public int getNextMaxComentId() throws Exception
	{
		  int result = 0;
		  try {
			  
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT MAX(comentId) FROM Coments;";
		      System.out.println(query);
		      ResultSet resultS = statement.executeQuery(query);
		      while (resultS.next()) {
		    	  result = ((Number)resultS.getObject(1)).intValue();;
		    	 System.out.println(result);
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		    	close();
		    }
		  return result + 1;
	  }
	  
	  public boolean addUser (User user) throws Exception
	  {
		  boolean status = false;
		  try {
		      connect = connect();
		      statement = connect.createStatement();
		      String query ="INSERT INTO poster_db.Users VALUES('"+user.getMail()+"','"+user.getPass() +"','"+user.getImageUrl() +"',"+0+");";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result == 1)
		    	  status = true;
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return status;
	  }
	  
	  public boolean validateUser(String email) throws Exception {
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "UPDATE Users SET isValid= 1 WHERE mail='"+ email +"';";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result != 1){
		    	System.out.println("Error setUserImageUrl "); 
		      	return false;
		      }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return true;
	  }
	  
	  public User getUser (String mail) throws Exception
	  {
		  User user = null;
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT mail, pass, imageUrl FROM Users WHERE mail = '"+ mail +"';";
		      System.out.println(query);
		      ResultSet result = statement.executeQuery(query);
		      while (result.next()) {
		    	  user = new User();
			      user.setMail(result.getString("mail"));
			      user.setPass(result.getString("pass"));
			      user.setImageUrl(result.getString("imageUrl"));
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return user;
	  }
	  
	  public String getPassForUser (String mail) throws Exception
	  {
		  String pass = "";
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT  pass FROM Users WHERE mail = '"+ mail +"';";
		      System.out.println(query);
		      ResultSet result = statement.executeQuery(query);
		      while (result.next()) {
			      pass = result.getString("pass");
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return pass;
	  }
	  
	  public boolean changePassForUser(User user) throws Exception{
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "UPDATE Users SET pass='"+ user.getPass() +"' WHERE mail='"+ user.getMail() +"';";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result != 1){
		    	System.out.println("Error setUserImageUrl "); 
		      	return false;
		      }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return true;
	  }
	  
	  public ArrayList<Post> getNextFivePosts( int index) throws Exception
	  {
		  System.out.println("get nex five with index: "+index);
		  ArrayList<Post> posts = new ArrayList<Post>();
		  Post temPost;
		  int lastPostId = this.getNextMaxPostId() - 1;
		  int endIndex = lastPostId - index;
		  int startIndex;
		  if((endIndex -5) < 0)
			  startIndex = 0;
		  else
			  startIndex = endIndex -5;
		  try {
			  endIndex = endIndex + 1;
			  startIndex = startIndex -1;
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT userMail, postText, likes, postDate, postId FROM Posts WHERE postId < "+ endIndex +" AND postId > " + startIndex +";";
		      System.out.println(query);
		      ResultSet tempResultSet = statement.executeQuery(query);
		      System.out.println(tempResultSet);
		      while (tempResultSet.next()) {
		    	  temPost = new Post();
		    	  temPost.setUser(this.getUser(tempResultSet.getString("userMail")));
		    	  temPost.setPostText(tempResultSet.getString("postText"));
		    	  temPost.setLikes(stringToLikes(tempResultSet.getString("likes")));
		    	  temPost.setPostDate(dateFromString(tempResultSet.getString("postDate")));
		    	  temPost.setPostId(tempResultSet.getLong("postId"));
		    	  temPost.setComents(getComentsForPost((int)temPost.getPostId()));
		    	  System.out.println(temPost);
		    	  posts.add(temPost);
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return posts;
	  }
	  
	  public Post getPost( int id) throws Exception
	  {
		  System.out.println("get post with id: "+id);
		  Post temPost = null;
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT userMail, postText, likes, postDate, postId FROM Posts WHERE postId = "+ id +";";
		      System.out.println(query);
		       ResultSet set = statement.executeQuery(query);
		      while (set.next()) {
		    	  temPost = new Post();
		    	  temPost.setUser(this.getUser(set.getString("userMail")));
		    	  temPost.setPostText(set.getString("postText"));
		    	  temPost.setLikes(stringToLikes(set.getString("likes")));
		    	  temPost.setPostDate(dateFromString(set.getString("postDate")));
		    	  temPost.setPostId(set.getLong("postId"));
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return temPost;
	  }
	  
	  public boolean isValideUser( String email) throws Exception
	  {
		  System.out.println("isValideUser: "+email);
		  boolean isValid = false;
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT isValid FROM Users WHERE mail = '"+ email +"';";
		      System.out.println(query);
		      ResultSet set = statement.executeQuery(query);
		      while (set.next()) {
		    	   isValid = set.getBoolean("isValid");
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return isValid;
	  }
	  
	  public Date dateFromString(String dateString) throws ParseException{

		  DateFormat formatter ; 
		  Date date ; 
		  formatter = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
		  date = formatter.parse(dateString);
		  return date;
	  }
	  
	  public ArrayList<String> stringToLikes(String likesStr){
		  
		  ArrayList<String> likes = new ArrayList<String>();
		  String[] parts = likesStr.split(",");
		  for(int i=0; i< parts.length; i++){
			likes.add(parts[i]);  
		  }
		  return likes;
	  }
	  
	  public String likesToString(ArrayList<String> likes)
	  {
		  String likesStr = "";
		  for (String like:likes){
			  likesStr += like;
		  }
		  return likesStr;
	  }
	  
	  public boolean setUserImageUrl (String mail, String imageUrl) throws Exception
	  {
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "UPDATE Users SET imageUrl='"+ imageUrl +"' WHERE mail='"+ mail +"';";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result != 1){
		    	System.out.println("Error setUserImageUrl "); 
		      	return false;
		      }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return true;
	  }
	  
	  public boolean likePost(int id, String userMail) throws Exception{
		  
		  Post post = this.getPost(id);
		  String likes = this.likesToString(post.getLikes());
		  likes = likes + userMail + ",";
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "UPDATE Posts SET likes='"+ likes +"' WHERE postId='"+ id +"';";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result != 1){
		    	System.out.println("Error liking "); 
		      	return false;
		      }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return true;
		  
	  }
	  
public boolean addComent(Coment coment) throws Exception{
	int nextComentId = getNextMaxComentId();
	boolean status = false;
	  try {
	      connect = connect();
	      statement = connect.createStatement();
	      String query ="INSERT INTO poster_db.Coments VALUES('"+coment.getUserMail()+"','"+coment.getComentText() +"', NOW() ,"+coment.getPostId() +","+ nextComentId +");";
	      System.out.println(query);
	      int result = statement.executeUpdate(query);
	      if(result == 1)
	    	  status = true;
	      
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      close();
	    }
	  return status;
}

public ArrayList<Coment> getComentsForPost(int id) throws Exception{
	ArrayList<Coment> coments = new ArrayList<Coment>();
	Coment tempComent;
	try {
		  connect = connect();
	      statement = connect.createStatement();
	      String query = "SELECT userMail, comentText, postId, date FROM Coments WHERE postId = '"+ id +"';";
	      System.out.println(query);
	      ResultSet result = statement.executeQuery(query);
	      while (result.next()) {
	    	  tempComent = new Coment();
	    	  tempComent.setUserMail(result.getString("userMail"));
	    	  tempComent.setComentText(result.getString("comentText"));
	    	  tempComent.setPostId(result.getLong("postId"));
	    	  tempComent.setDate(dateFromString(result.getString("date")));
	    	  coments.add(tempComent);
		    }
	      
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      close();
	    }
	
	return coments;
}
	  
public boolean disLikePost(int id, String userMail) throws Exception{
		  
		  Post post = this.getPost(id);
		  String likes = this.likesToString(post.getLikes());
		  likes = likes.replace(userMail,"");
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "UPDATE Posts SET likes='"+ likes +"' WHERE postId='"+ id +"';";
		      System.out.println(query);
		      int result = statement.executeUpdate(query);
		      if(result != 1){
		    	System.out.println("Error liking "); 
		      	return false;
		      }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return true;
		  
	  }
	  


	  public static Connection connect()
	  {
	      try
	      {
	    	  System.out.println("class forname");
	          Class.forName("com.mysql.jdbc.Driver");   
	          System.out.println(" return class forname");
	          return DriverManager.getConnection(mysqlDomain, mysqlUser, mysqlPass);
	      }

	      catch (Exception e)
	      {
	          System.out.println("Exception occured: "+ e.getMessage());
	          return null;
	      }
	  }
	  
	  private void close() {
		    close(resultSet);
		    close(statement);
		    close(connect);
		  }
		  private void close(Object c) {
		    try {
		      if (c != null) {
		    	  Closeable cl = (Closeable)c;
		       cl.close();
		      }
		    } catch (Exception e) {
		    // don't throw now as it might leave following closables in undefined state
		    }
		  }
}
