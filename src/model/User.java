package model;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
	public String mail;
	public String pass;
	public String imageUrl;
	
	public User() {
	}
	
	public User (String ident, String pss, String image)
	{
		pass= pss;
		mail = ident;
		setImageUrl(image);
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String id) {
		this.mail = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String toJson() throws JsonParseException, JsonMappingException, IOException{
		String json = "{\"mail\":\"" + this.mail + "\", \"pass\":\"" + this.pass + "\"}";
		this.jsonToObject();
		return json;
	}
	
	public void jsonToObject() throws JsonParseException, JsonMappingException, IOException{
		
		User us = new User();
		us.setMail("hose@abv.bg");
		us.setPass("123");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String j = gson.toJson(us);
        System.out.print(j);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
