package Client.ui;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.Book;
import Mysql.Mysql;


public class GetBookData {

	String isbnUrl = "http://api.douban.com/book/subject/isbn/";  
	ArrayList<String> isbnlist = new ArrayList<String>();
	String txtlist = null;
	public GetBookData(){
		
	}
	  
	public static void main(String[] args) throws Exception {  
		
	    //requestUrl = isbnUrl + isbnNo + "?apikey=" + apikey;  
	    //eg:http://api.douban.com/book/subject/isbn/9787111298854?apikey=111111111111111111114  
		GetBookData a = new GetBookData();  
	//    String isbnNo = "9787111298854";  
	  //  String xml = a.fetchBookInfoByXML(isbnNo);  
	 //   System.out.println(xml);  
	//    a.getBook(xml);
//		a.readFile();
		//a.extractISBN();
//		a.queryBooksInfo();
		a.setRandomType();
		
		
	}  
	public void setRandomType(){
		String [] a = {"艺术","哲学","社会科学总论","政法","军事","经济","文学","工业技术","自然科学总论","航空航天","交通运输","综合性图书","历史","生物科学","天文学"};
		ResultSet s = Mysql.getInstance().query("select book_id from booklist"); 
		try {
			while(s.next()){
				int temp =(int)(Math.random()*a.length);
				if(temp==a.length) temp--;
				
				Mysql.getInstance().execute("update booklist set booktype=\""+a[temp]+"\"  where book_id="+s.getInt("book_id"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	/** 
	 * 从根据isbn号从豆瓣获取数据。已经申请apikey，每分钟最多40次请求，足够用。 
	 * @param isbnNo 
	 * @return 
	 * @throws IOException  
	 */  
	public String fetchBookInfoByXML(String isbnNo) throws IOException  {  
	    String requestUrl = isbnUrl + isbnNo;  
	    URL url = new URL(requestUrl);  
	    URLConnection conn = url.openConnection();  
	    InputStream is = conn.getInputStream();  
	    InputStreamReader isr = new InputStreamReader(is, "utf-8");  
	    BufferedReader br = new BufferedReader(isr);  
	    StringBuilder sb = new StringBuilder();  
	      
	    String line = null;  
	    while ((line = br.readLine()) != null) {  
	        sb.append(line);  
	    }  
	      
	    br.close();  
	    return sb.toString();  
	}  
	public void getBook (String a){
		String name = "name=\"title\"";
		String isbn = "9787111298854";
		String author = "name=\"author\"";
		boolean rare= false;
		String publisher= "name=\"publisher\"";
		
		System.out.println(getAttribute(name,a));
		System.out.print("author"+getAttribute(author,a));
		System.out.print("publisher"+getAttribute(publisher,a));
	}
	public void queryBooksInfo(){
		for(int i=0;i<isbnlist.size();i++){
			String isbn = isbnlist.get(i);
			try {
				String xml = fetchBookInfoByXML(isbn);
				getBook(xml);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public String getAttribute(String key,String xml){
		int tmp = xml.indexOf(key);
		if(tmp==-1){
			return null;
		}
		int tmp1 = xml.indexOf('<',tmp);
		return xml.substring(tmp+key.length()+1, tmp1);
		
	}
	public void extractISBN(){
		ArrayList<Book> b = new ArrayList<Book>();
		
		int tmp2 = -1;
		int tmp=-2;
		String linerecord=null;
		while(tmp!=-1){
			 tmp = txtlist.indexOf("<br>",tmp2+1);
			tmp2 = txtlist.indexOf("<br>", tmp+1);
			
			try{
				linerecord = txtlist.substring(tmp,tmp2);
			}catch (StringIndexOutOfBoundsException e){
				
			}
			String tmpISBN= linerecord.split(",")[7];
			if(tmpISBN.length()>10){
				isbnlist.add(linerecord.split(",")[7]);
//				System.out.println(linerecord.split(",")[1]);
//				System.out.println(linerecord.split(",")[2]);
//				System.out.println(linerecord.split(",")[3]);
//				System.out.println(linerecord.split(",")[7]);
//				b.add(new Book(linerecord.split(",")[1], linerecord.split(",")[3], linerecord.split(",")[2], linerecord.split(",")[7]));
			}
			
			
		}
		Mysql m = Mysql.getInstance();
		String sql = "insert into booklist(name,author,publisher,isbn) values";
		int j=0;
		for(int i=0;i<b.size();i++){
			
				sql = sql+"("+(b.get(i).getName().length()==0?"\"\"":b.get(i).getName())+","+(b.get(i).getAuthor().length()==0?"\"\"":b.get(i).getAuthor())+","+(b.get(i).getPublisher().length()==0?"\"\"":b.get(i).getPublisher())+","+b.get(i).getISBN()+")";
			j++;
			
			if(j==100){
				
				sql = sql +";";
				m.query("set names gbk");
				System.out.println(sql);
				//m.update(sql);
				j=0;
				//return;
			}
			if(i!=b.size()-1){
				sql=sql+",";
			}
			if(j==0){
				sql = "insert into booklist(name,author,publisher,isbn) values";
			}
			
			
				
			
			
		}
		sql = sql +";";
		m.query("set names gbk");
		System.out.println(sql);
		//m.update(sql);
		
		
		
	}
	public String readFile(){
		String fileurl = "图书.txt";
		 
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileurl));
			txtlist=br.readLine();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return txtlist;
	}
}
