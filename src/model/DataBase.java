

// user docReader
//pass pass
//FILE data
/*
CREATE TABLE USERS ( ID VARCHAR(45) NOT NULL, 
PASS VARCHAR(20) NOT NULL, DATA VARBINARY(8000),PRIMARY KEY (ID), confirmed TINYINT(1));
*/
package model;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import model.User;


public class DataBase {
	 private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  
	  
	  public String getDataForUser (User user) throws Exception
	  {
		  String result = null;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "SELECT id, pass, data FROM DATA.USERS WHERE id = '"+ user.getId() +"'";
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
			      result = resultSet.getString("data");
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return result;
	  }
	  
	  public boolean setDataForUser (User user, String dataString) throws Exception
	  {
		  boolean status = false;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "UPDATE data.users SET data='" + dataString + "' WHERE id='"+ user.getId()+"'";
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
	  
	  public void setValidUser(String id) throws Exception
	  {
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "UPDATE data.users SET confirmed=" + 1 + " WHERE id='"+ id+"'";
		     int result = statement.executeUpdate(query);
		      if(result != 1)
		    	System.out.println("Error validating user"); 
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
	  }
	  
	  public boolean changePassForUser (User user) throws Exception
	  {
		  boolean status = false;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "UPDATE data.users SET pass='" + user.getPass() + "' WHERE id='"+ user.getId()+"'";
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
	  
	  public String getPassForUser(String user) throws Exception
	  {
		  String pass ="";
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "Select id, pass, confirmed FROM DATA.USERS WHERE id ='"+user +"' and confirmed =1";
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
			      pass = resultSet.getString("pass");
			    }
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return pass;
	  
	  }
	  
	  public boolean addNewUserAndData (User user, String dataString) throws Exception
	  {
		  boolean status = false;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query ="INSERT INTO data.users VALUES('"+user.getId()+"','"+user.getPass() +"','"+dataString +"',0)";
		      System.out.println(query);
		     int result = statement.executeUpdate(query);
		      if(result == 1)
		    	  status = true;
		      
		    } catch (Exception e) {
		    	System.out.println("Error adding user");
		      throw e;
		    } finally {
		      close();
		    }
		  return status;
	  }
	  
	  public boolean isValudUser(User user) throws Exception
	  {
		  boolean result = false;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "SELECT id, pass, data, confirmed FROM DATA.USERS WHERE id = '"+ user.getId() +"' AND pass = '"+ user.getPass() + "' AND confirmed = "+ "1";
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
			      result = true;
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return result;
	  }
	  
	  public boolean deleteUser(User user) throws Exception
	  {
		  boolean status = false;
		  int result = 0;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "delete from data.users where id='"+ user.getId() +"'";
		      System.out.println(query);
		      result = statement.executeUpdate(query);
		      System.out.println(result);
		      if(result == 1)
		    	  status = true;
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return status;
	  }
	  
	  public boolean isValudUserNameFree(String name) throws Exception
	  {
		  boolean result = true;
		  try {
		      // this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // setup the connection with the DB.
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/data?"
		              + "user=docReader&password=pass");
		      statement = connect.createStatement();
		      String query = "SELECT id, pass, data FROM DATA.USERS WHERE id = '"+ name +"'";
		      resultSet = statement.executeQuery(query);
		      while (resultSet.next()) {
			      result = false;
			    }
		      
		    } catch (Exception e) {
		      throw e;
		    } finally {
		      close();
		    }
		  return result;
	  }
	  

	  	private void writeMetaData(ResultSet resultSet) throws SQLException {
		    // now get some metadata from the database
		    System.out.println("The columns in the table are: ");
		    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
		      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		    }
		  }

		  private void writeResultSet(ResultSet resultSet) throws SQLException {
		    // resultSet is initialised before the first data set
		    while (resultSet.next()) {
		      // it is possible to get the columns via name
		      // also possible to get the columns via the column number
		      // which starts at 1
		      // e.g., resultSet.getSTring(2);
		      String user = resultSet.getString("id");
		      String website = resultSet.getString("pass");
		      String summary = resultSet.getString("data");
		      System.out.println("User: " + user);
		      System.out.println("pass: " + website);
		      System.out.println("data: " + summary);
		    }
		  }

		  // you need to close all three to make sure
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
		  
		  public void readDataBase() throws Exception {
			    try {
			      // this will load the MySQL driver, each DB has its own driver
			    	 Class.forName("com.mysql.jdbc.Driver");
				      // setup the connection with the DB.
				      connect = DriverManager
				          .getConnection("jdbc:mysql://localhost/data?"
				              + "user=docReader&password=pass");
				      statement = connect.createStatement();
			      resultSet = statement
			      .executeQuery("select * from DATA.USERS");
			      writeResultSet(resultSet);
			      
			    } catch (Exception e) {
			      throw e;
			    } finally {
			      close();
			    }

			  }
		  
		  public static void main(String[] args) throws Exception {
			    DataBase dao = new DataBase();
			    //dao.readDataBase();
			    
			    User user = new User("andrey","123");
			    User user1 = new User("eli","123");
			    dao.readDataBase();
			  }
}
