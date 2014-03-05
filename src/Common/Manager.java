package Common;

import java.io.*;



public class Manager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username="123";
	private String password="123";
	private boolean on=false;


	
	
	
	public Manager(){		
	}
	
	
	public String getUsername(){
		return username;
	}
	
	
	public void setUsername(String username){
		this.username=username;
	}
	
	
	public String getPassword(){
		return password;
	}
	
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public boolean isOn(){
		return on;
	}
	
	public void setOn(boolean on){
		this.on=on;
	}

}
