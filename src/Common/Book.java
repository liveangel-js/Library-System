package Common;

import java.io.*;
import java.util.*;



public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int nextID=0;
	private int ID;
	private String name;
	private String author;
	private boolean rare;
	private boolean borrowed=false;
	private Student borrower;
	private boolean renewed=false;
	private Calendar borrow_calendar;
	private Calendar return_calendar;
	private Calendar remind_calendar;
	private String borrow_time;
	private String return_time;
	private boolean request=false;
	private Student requester;
	private String publisher;
	private String ISBN;
	private String type;

	
	private String getType(){
		return type;
	}
	public String getPublisher(){
		return publisher;
	}
	public String getISBN(){
		return ISBN;
	}
	public Book(String name,String author,boolean rare){
		this.ID=Book.getNextID();
		this.setBook(name,author,rare);
		Library.library.books.add(this.ID,this);
	}
	public Book(String name,String author,String publisher,String ISBN,String  type){
		this.name=name;
		this.author=author;
		this.publisher=publisher;
		this.ISBN=ISBN;
		this.type = type;
	}
	public String getBookType(){
		return type;
	}
	
	
	public static int getNextID(){
		int i=0;
		for(i=0;i<Library.library.books.size();i++){
			if(Library.library.books.get(i).getID()!=i){
				nextID=i;
				break;
			}
		}
		if(i==Library.library.books.size()){
			nextID=i;
		}
		return nextID;
	}
	
	

	public int getID(){
		return ID;
	}
	public void setID(int ID){
		this.ID=ID;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getAuthor(){
		return author;
	}
	public void setAuthor(String author){
		this.author=author;
	}
	public boolean isRare(){
		return rare;
	}
	public void setRare(boolean rare){
		this.rare=rare;
	}
	public boolean isBorrowed(){
		return borrowed;
	}
	public void setBorrowed(boolean borrowed){
		this.borrowed=borrowed;	
	}
	public Student getBorrower(){
		return borrower;
	}
	public void setBorrower(Student student){
		this.borrower=student;
	}
	public boolean isRenewed(){
		return renewed;
	}
	public void setRenewed(boolean renewed){
		this.renewed=renewed;
	}
	
	
	public void setBook(String name,String author,boolean rare){
		this.name=name;
		this.author=author;
		this.rare=rare;
	}
	

	
	
	public Calendar getBorrowCalendar(){
		return borrow_calendar;
	}
	
	public Calendar getRemindCalendar(){
		return remind_calendar;
	}
	public Calendar getReturnCalendar(){
		return return_calendar;
	}
	public String getBorrowTime(){
		return borrow_time;
	}
	public String getReturnTime(){
		return return_time;
	}
	
	
	public void borrowedBy(Student s){
		this.borrower=s;
		this.borrowed=true;
		borrow_calendar=Calendar.getInstance(); 
		borrow_time=borrow_calendar.get(Calendar.YEAR)+"/"+(borrow_calendar.get(Calendar.MONTH)+1)+"/"+borrow_calendar.get(Calendar.DAY_OF_MONTH);
		remind_calendar=Calendar.getInstance();
		remind_calendar.add(Calendar.DAY_OF_MONTH,4);         
		return_calendar=Calendar.getInstance();
		return_calendar.add(Calendar.DAY_OF_MONTH,7);
		return_time=return_calendar.get(Calendar.YEAR)+"/"+(return_calendar.get(Calendar.MONTH)+1)+"/"+return_calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	public void renewed(){
		this.renewed=true;
		remind_calendar.add(Calendar.DAY_OF_MONTH,7);
		return_calendar.add(Calendar.DAY_OF_MONTH,7);
		return_time=return_calendar.get(Calendar.YEAR)+"/"+(return_calendar.get(Calendar.MONTH)+1)+"/"+return_calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	
	public void returned(){
		borrowed=false;
		borrower=null;
		renewed=false;
		borrow_calendar=null;
		return_calendar=null;
		remind_calendar=null;
	}
	
	public boolean isDeadLine(){
		if(Calendar.getInstance().after(return_calendar)){	
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public boolean isNeedReminded(){
		if(Calendar.getInstance().after(remind_calendar)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isRequest(){
		return request;
	}
	
	public void setRequest(boolean request){
		this.request=request;
	}
	
	public Student getRequester(){
		return requester;
	}
	
	public void setRequester(Student requester){
		this.requester=requester;
	}

	
}
