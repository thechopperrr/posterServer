package model;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
		    	  result = ((Number)resultSet.getObject(1)).intValue();;
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
		      String query ="INSERT INTO poster_db.Users VALUES('"+user.getMail()+"','"+user.getPass() +"','"+user.getImageUrl() +"');";
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
	  public User getUser (String mail) throws Exception
	  {
		  User user = null;
		  try {
			  connect = connect();
		      statement = connect.createStatement();
		      String query = "SELECT mail, pass, imageUrl FROM Users WHERE mail = '"+ mail +"';";
		      System.out.println(query);
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
		    	  user = new User();
			      user.setMail(resultSet.getString("mail"));
			      user.setPass(resultSet.getString("pass"));
			      user.setImageUrl(resultSet.getString("imageUrl"));
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return user;
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
