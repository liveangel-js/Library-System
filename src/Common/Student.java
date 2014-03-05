package Common;


import java.io.*;
import java.util.*;




public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int nextID=0;
	private int ID;
	private String username;
	private String password;
	private boolean undergraduate=true;
	private boolean on=false;
	private ArrayList<Book>books=new ArrayList<Book>();
	private ArrayList<Book>books_request=new ArrayList<Book>();

		
	
	public Student(){	

	}

	
	public Student(String username,String password,boolean undergraduate){
		this.ID=Student.getNextID();
		this.setStudent(username, password, undergraduate);
		Library.library.users.add(this.ID,this);	
	}

	
	
	
	
	public static int getNextID(){
		int i=0;
		for(i=0;i<Library.library.users.size();i++){
			if(Library.library.users.get(i).getID()!=i){
				nextID=i;
				break;
			}
		}
		if(i==Library.library.users.size()){
			nextID=i;
		}
		return nextID;
	}

	public boolean isOn(){
		return on;
	}
	public void setOn(boolean on){
		this.on=on;
	}
	
	public int getID(){
		return ID;
	}
	public void setID(int ID){
		this.ID=ID;
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
	
	
	
	public  boolean isUndergraduate(){
		return undergraduate;
	}
	public void setUndergraduate(boolean undergraduate){
		this.undergraduate=undergraduate;
	}
	
	public ArrayList<Book> getBooks(){
		return books;
	}
	
	
	
	public ArrayList<Book> getRequestBooks(){
		return books_request;
	}
	
	
	public void setStudent(String username,String password,boolean undergraduate){
		this.username=username;
		this.password=password;
		this.undergraduate=undergraduate;
	}
	

	
	public void borrow(Book b){
		books.add(b);
	}
	
	public void returnBook(Book b){
		books.remove(b);
	}
	


	
	
	//图书到期提醒和教师图书请求
	public void remind(){
		Calendar calendar=Calendar.getInstance();
		for(int i=0;i<books.size();i++){
			if(calendar.after(books.get(i).getReturnCalendar())){
				System.out.println("你借阅的图书 ID:"+books.get(i).getID()+"已到期，请尽快归还！");
			}
			else{
				if(calendar.after(books.get(i).getRemindCalendar())){
					System.out.println("你借阅的图书ID "+books.get(i).getID()+" 即将到期，请尽快归还！");
				}
			}
		}
	}
	

}
