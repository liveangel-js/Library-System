package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Common.Book;

public class Mysql {
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/library";
	String user = "root";
	String password = "root";
	Connection conn ;
	Statement statement;
	 static Mysql  mysql;

	public static Mysql getInstance(){
		if(mysql ==null){
			mysql = new Mysql();
		}
		return mysql;
	}
	public Mysql(){
		try {
			Class.forName(driver);
			 conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			
			statement = conn.createStatement();
			//String sql = "select * from books";
		//	ResultSet rs = statement.executeQuery(sql); 
			//while(rs.next()) { 
				
//			}
//			rs.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public int update(String u){
		int rs = -1;
		try {
			statement = conn.createStatement();
			rs = statement.executeUpdate(u);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void  execute(String u){
		try {
			statement = conn.createStatement();
			 statement.execute(u);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ResultSet query(String u){
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void close(){
		  
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void main(String args[]){
		Mysql mysql = Mysql.getInstance();
//		mysql.simulateHistory();
		
		ResultSet rs = mysql.query("select isbn, name ,publisher from booklist where book_id not in (select t.book_id from borrowrecord t)");
		int i=0;
		try {
			while(rs.next()){
				System.out.println(i);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	
	
	}
	public int addBook(Book a){
		int temp = update("insert into booklist(name,author,publisher,isbn,booktype) values(\"" +
				a.getName()+"\",\"" +
				a.getAuthor()+"\",\"" +
				a.getPublisher()+"\",\"" +
				a.getISBN()+"\",\"" +
				a.getBookType()+"\")" +
				"");
		return temp;
		
		
	}
	
	public void simulateHistory(){
		ResultSet rs = mysql.query("select book_id,booktype from booklist");
		ArrayList<Integer> book_id = new ArrayList<Integer>(); 
		ArrayList<String> book_type  = new ArrayList<String>();
		
		try {
			while(rs.next()){
				book_id.add(rs.getInt("book_id"));
				book_type.add(rs.getString("booktype"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Integer> user_id = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			user_id.add(i);
		}
		//Ä£Äâ 3/2´Î½èÔÄ
		for(int i=0;i<(book_id.size()+book_id.size()/2);i++){
			System.out.println("simulate :NO"+i);
			int book_index = (int) (Math.random()*(book_id.size()-1));
			int id_index = (int)(Math.random()*(user_id.size()-1));
			mysql.update("insert into borrowrecord values("+user_id.get(id_index)+","+book_id.get(book_index)+",\""+book_type.get(book_index)+"\")");
			
		}
		
		
	}
	

}
