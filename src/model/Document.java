package model;

import java.util.ArrayList;

import javax.xml.crypto.Data;


public class Document {
	private Data file; //maybe not that type 
	private ArrayList<BookMark> marks;
	private String title;
	private String type; // still not use 
	public Data getFile() {
		return file;
	}
	public void setFile(Data file) {
		this.file = file;
	}
	public ArrayList<BookMark> getMarks() {
		return marks;
	}
	public void setMarks(ArrayList<BookMark> marks) {
		this.marks = marks;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
