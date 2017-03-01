package com.example.customlists_36;

//import java.util.ArrayList;
//import java.util.List;

public class Message {
	private String sender;
	private String title;
	private int id;
	private boolean read = false;

	public Message(int id, String sender, String title ) {
		super();
		this.sender = sender;
		this.title = title;
		this.id = id ;

	}

	public Message(int id, String sender, String title, boolean read ) {
		super();
		this.sender = sender;
		this.title = title;
		this.id = id ;
		this.read = read;
		

	}	
	
	public boolean isRead() {
		return read;
	}



	public void setRead(boolean read) {
		this.read = read;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
