package Common;






public class Teacher extends Student{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	public Teacher(String username,String password){
		super(username,password,false);
	}
	
	public void setStudent(String username,String password){
		super.setStudent(username, password,false);
	}
	
	public void setStudent(String username,String password,boolean undergraduate){
		super.setStudent(username, password,false);
	}
	public void askForReturn(){
		
	}



}
