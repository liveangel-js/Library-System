package Common;

import java.io.*;
import java.util.*;





public class Library implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FileInputStream fin;
	private static ObjectInputStream in;
	private static FileOutputStream fout;
	private static ObjectOutputStream out;
	//private static final File file_library=new File(Library.class.getClassLoader().getResource("Data/Library.dat").getFile());
	private static final File file_library=new File("Library.dat");
	public Manager manager;
	public ArrayList<Book>books=new ArrayList<Book>();
	public ArrayList<Student>users=new ArrayList<Student>();
	public static Library library;
	
	
	
	
	
	private  Library(){
		manager=new Manager();
	}
	
	
	public static void load() throws IOException{
		in=null;
		fin=new FileInputStream(file_library);
		try{
			in=new ObjectInputStream(fin);              //处理空文件
			library=(Library) in.readObject();
		}
		catch(Exception e){	
			e.printStackTrace();
		}
		if(in!=null){
			in.close();
		}
		fin.close();

		if(library==null){
			library=new Library();
		}
		
		synchronize();
		
		
	}
	
	
	
	public static  void store() throws IOException{
		fout=new FileOutputStream(file_library);
		out=new ObjectOutputStream(fout);
		out.writeObject(library);
		out.close();
		fout.close();
	}
	
	
	
	//由于序列化产生一个对象的多个实例，需要同步化
	public static void synchronize(){	
		for(int i=0;i<library.users.size();i++){
			Student user=library.users.get(i);
			user.getBooks().clear();
			for(int j=0;j<library.books.size();j++){
				Book book=library.books.get(j);
				if(book.isBorrowed()&&book.getBorrower().getID()==user.getID()){
					book.setBorrower(user);
					user.getBooks().add(book);
				}
			}
			for(int k=0;k<library.books.size();k++){
				Book book1=library.books.get(k);
				for(int p=0;p<user.getBooks().size();p++){
					Book book2=user.getBooks().get(p);
					if(book1.getID()==book2.getID()){
						user.getBooks().set(p, book1);
						//break;
					}
				}
			}
		}		
	}
	
	
	public static Student getUser(int ID){
		for(int i=0;i<Library.library.users.size();i++){
			Student user=Library.library.users.get(i);
			if(user.getID()==ID){
				return user;
			}
		}
		return null;
	}
	
	
	
	public static void main(String args[]){
		Library l=new Library();
		try {
			l.store();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	


}
