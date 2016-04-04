package com.test.service;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.catalina.connector.Response;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import model.Account;
import model.Coment;
import model.Post;
import model.Resource;
import model.SendEmail;
import model.TempResource;
import model.User;
import model.DataBase;
//import sun.misc.BASE64Decoder;
 
@SuppressWarnings("unused")
@Path("auth")
public class HTTPService {
  
 @Context
 private HttpServletRequest request;
 private DataBase resource;
 
 //new
 
 private List<Post> posts;
 private static TempResource tempResource;
  
 public void createResource()   // add test values 
 {
	 resource = new DataBase(); 
 }
 
@GET
 @Path("validate")
 @Produces(MediaType.TEXT_PLAIN)
 public void validateUserHTTPHeaderPost() throws Exception{
  if(this.resource == null){
 	 this.createResource();
  }
  
  try{
   // Get the Authorisation Header from Request
   String header = request.getParameter("code"); // must me Authentication

   System.out.println("Validate user: "+header);
   resource.setValidUser(header);
   
  }catch(Exception e){
   e.printStackTrace();
  }
 }

@GET
@Path("hui")
@Produces(MediaType.TEXT_PLAIN)
public String hello() {

		return "Hello Jersey";
}


@POST
@Path("sendPassEmail")
@Produces(MediaType.TEXT_PLAIN)
public String forgotPassHTTPPost(String x) throws Exception{
 String status = "no_such_user";
 if(this.resource == null){
	 this.createResource();
 }
 try{
  if(!resource.isValudUserNameFree(x)){
	  String pass = resource.getPassForUser(x);
	  SendEmail emailSender = new SendEmail();
	  emailSender.subject = "Password restore";
	  emailSender.setMessage("Your password is: "+pass);
	  emailSender.setRecipient(x);
	  emailSender.sendMessage();
	  status = "pass_sended";
  }
   
 }catch(Exception e){
  e.printStackTrace();
 // decoded = "No/Invalid authentication information provided";
 }
 
return status;
}


@POST
@Path("basic")
@Produces(MediaType.TEXT_PLAIN)
public String authenticateHTTPPost(String x) throws Exception{
 User user = new User(); 
 String decoded;
 if(this.resource == null){
	 this.createResource();
 }
 try{
  String header = x;
  decoded = header;
   String [] dataArray = decoded.split("#"); 
  user.setId(dataArray[0]);
  user.setPass(dataArray[1]);
  System.out.println("Server:"+decoded);
   
 }catch(Exception e){
  e.printStackTrace();
  decoded = "No/Invalid authentication information provided";
 }
 
 if(resource.isValudUser(user)){
	 decoded = resource.getDataForUser(user);
 }
 else {
	 decoded = "fail";
 }
////  for test
// decoded = "<documents><document><url>file:///Users/interns/Library/Application%20Support/iPhone%20Simulator/7.1/Applications/DF92D88C-C31D-4231-A65E-D999A06867C2/DocReader.app/iPhoneAppProgrammingGuide.pdf</url><title>Shity boobs</title></document></documents>";
 return decoded;
}

@POST // to do remove
@Path("logOut")
@Produces(MediaType.TEXT_PLAIN)
public String logOutHTTPHeaderPost(String x) throws Exception{
 User user = new User(); 
 String decoded;
 if(this.resource == null){
	 this.createResource();
 }
 
 try{
  // Get the Authorisation Header from Request
  String header = x;
  System.out.println();
  System.out.println(header);
  decoded = header;
   String [] dataArray = decoded.split("#");
   
  user.setId(dataArray[0]);
  user.setPass(dataArray[1]);
  resource.setDataForUser(user, dataArray[2]);
  
  System.out.println("Sucsess:"+resource.setDataForUser(user, dataArray[2]));
   
 }catch(Exception e){
  e.printStackTrace();
  decoded = "No/Invalid authentication information provided";
 }
 
 if(resource.isValudUser(user)){
	 decoded = "suc";
 }
 decoded = "suc";
 return decoded;
}

@POST
@Path("register")
@Produces(MediaType.TEXT_PLAIN)
public String registerHTTPHeaderPost(String x) throws Exception{
	System.out.println("register");
 User user = new User(); 
 String decoded;
 if(this.resource == null){
	 this.createResource();
 }
 String status = "fail";
 try{
  String header = x;
  System.out.println();
  System.out.println(header);
  decoded = header;
   String [] dataArray = decoded.split("#");
   
  user.setId(dataArray[0]);
  user.setPass(dataArray[1]);
  if(resource.addNewUserAndData(user, "")){
	  status = "suc";
	  SendEmail emailSender = new SendEmail();
	  emailSender.subject = "Email validation";
	  emailSender.setRecipient(user.getId());
	  emailSender.setMessage( "http://localhost:8080/RestApi/auth/validate?code="+user.getId());
	  emailSender.sendMessage();
	  
  }
   
 }catch(Exception e){
  e.printStackTrace();
 }
 
 return status;
}

@POST
@Path("newPass")
@Produces(MediaType.TEXT_PLAIN)
public String changePassHTTPHeaderPost(String x) throws Exception{
 User user = new User(); 
 String decoded;
 if(this.resource == null){
	 this.createResource();
 }
 
 try{
  String header = x;
  System.out.println();
  System.out.println(header);
  decoded = header;
   String [] dataArray = decoded.split("#");
   
  user.setId(dataArray[0]);
  user.setPass(dataArray[1]);
   
 }catch(Exception e){
  e.printStackTrace();
  decoded = "No/Invalid authentication information provided";
 }
 
 if(resource.changePassForUser(user)){
	 decoded = "passChanged";
 }

 return decoded;
}


@POST
@Path("logOutHttps")
@Produces(MediaType.TEXT_PLAIN)
public String logOutHttps(String x) throws Exception {
	User user = new User(); 
	 if(this.resource == null){
		 this.createResource();
	 }
	 try{
		   String [] dataArray = x.split("#");
		  System.out.println(dataArray[2]);
		  user.setMail(dataArray[0]);
		  user.setPass(dataArray[1]);
		  resource.setDataForUser(user, dataArray[2]);
		  
		  System.out.println("Sucsess:"+resource.setDataForUser(user, dataArray[2]));
		   
		 }catch(Exception e){
		  e.printStackTrace();
		  x = "No/Invalid authentication information provided";
		 }
		 
		 if(resource.isValudUser(user)){
			 x = "suc";
		 }
		 else x="";
		 return x;
}

/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////


@POST
@Path("validateUser")
@Consumes(MediaType.APPLICATION_JSON)
public String validateUser(User usr) throws Exception {

	if(tempResource == null){
		tempResource = new TempResource();
	}
	
		if(tempResource.isValidUser(usr)){
			return "YES";
		}
		else
			return "NO";
}

@POST
@Path("isSuchUser")
@Consumes(MediaType.APPLICATION_JSON)
public String isSuchUser(User usr) throws Exception {
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
		if(tempResource.isSuchUser(usr.mail)){
			return "YES";
		}
		else
			return "NO";
}

@POST
@Path("register")
@Consumes(MediaType.APPLICATION_JSON)
public String register(User usr) throws Exception {
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
		if(tempResource.register(usr)){
			return "YES";
		}
		else
			return "NO";
}

@POST
@Path("forgetPass")
@Consumes(MediaType.APPLICATION_JSON)
public String forgetPass(User usr) throws Exception {
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
	try{
			  String pass = tempResource.getPassForUser(usr.mail);
			  SendEmail emailSender = new SendEmail();
			  emailSender.subject = "Password restore";
			  emailSender.setMessage("Your password is: "+pass);
			  emailSender.setRecipient(usr.mail);
			  emailSender.sendMessage();
			  return "YES";
		   
		 }catch(Exception e){
		  e.printStackTrace();
		  return "NO";
		 }
}

@POST
@Path("nextFive")
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Post> getNextFive(String numStr) throws Exception {
	
	System.out.println("start index:");
	System.out.println(numStr);
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
	int num = Integer.parseInt(numStr);
	return tempResource.getNextFive(num);
}

@GET
@Path("like")
@Consumes(MediaType.APPLICATION_JSON)
public String likePost(String numStr) throws Exception {
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
	int num = Integer.parseInt( request.getParameter("postId"));
	System.out.println("liking");
	System.out.println(num);
	String user =  request.getParameter("userMail");
	System.out.println(user);
	return tempResource.likePostWithId(num,user);
}

@GET
@Path("disLike")
@Consumes(MediaType.APPLICATION_JSON)
public String disLikePost(String numStr) throws Exception {
	
	if(tempResource == null){
		tempResource = new TempResource();
	}
	int num = Integer.parseInt( request.getParameter("postId"));
	System.out.println("disLiking");
	System.out.println(num);
	String user =  request.getParameter("userMail");
	System.out.println(user);
	return tempResource.disLikePostWithId(num,user);
}
//@POST
//@Path("hhh")
//@Produces(MediaType.APPLICATION_JSON)
//public Post getPosts(String x) throws Exception {
//	User user = new User(); 
//	 if(this.resource == null){
//		 this.createResource();
//	 }
//	 try{
//		   String [] dataArray = x.split("#");
//
//		 }catch(Exception e){
//
//		 }
//
//	 	User user1 = this.createUser("Hose@ada.dd");
//		Post p = this.createPost("ku4eto lapa topkite", user1, 10);
//		return p;
//}

public Coment createComent(User usr, String text){
	Coment coment = new Coment();
	coment.setUserMail(usr.getId());
	coment.setComentText(text);
	coment.setDate(Calendar.getInstance().getTime());
	return coment;
}

public User createUser(String name){
	User user = new User();
	user.setId(name);
	user.setPass("123");
	return user;
}

public Post createPost(String text, User user, int likes){
	
	User user1 = this.createUser("Hose@ada.dd");
	User user2 = this.createUser("consunela@jdak.vd");
	ArrayList coments = new ArrayList<Coment>();
	coments.add(this.createComent(user1, "coment1"));
	coments.add(this.createComent(user2, "coment1"));
	
	Post post = new Post();
	post.setUser(user);
	post.setPostText(text);
	post.setPostDate(Calendar.getInstance().getTime());
	post.setComents(coments);
	return post;
	
}
public String makeIt() throws JsonParseException, JsonMappingException, IOException{
	User user1 = this.createUser("Hose@ada.dd");
	Post p = this.createPost("ku4eto lapa topkite", user1, 10);
	User user2 = this.createUser("konsonela@ada.dd");
	Post p1 = this.createPost("bau bau gleda okoto", user2, 10);
	String s = "{\"posts\":[";
	s = s+ p.toJson() + "," + p1.toJson() + "]}";
	return s;
}

}