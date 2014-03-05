package Client.ui;


import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;

import Common.Library;
import Common.Student;




public class Client extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=1000;
	public static final int HEIGHT=600;
	public static final int X=200;
	public static final int Y=50;
	
	
	private static JDialog dialog_remind;
	private static JLabel label_remind;
	
	
	private static Socket client;
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static String IP="127.0.0.1";
	private static int port=8888;
	
	public static Student user;


    
	
	
	
	public Client(){
		super("图书管理系统2.5");
		this.setLocation(X,Y);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		
		this.remindDialogInitialize();	
		this.login();
	}

	
	
	
	
	
	
	
	

	
	
	

	//登陆界面
	public void login(){
		this.setVisible(false);
		new Login(this).setVisible(true);
	}	
	
	public String getIP(){
		return IP;
	}
	
	
	public int getPort(){
		return port;
	}
	
	public void setServer(String IP,int port){
		Client.IP=IP;
		Client.port=port;
	}
	
	
	
	public void connectServer(){
		try{
			Login.remind("连接服务器中······\n");	
			System.out.print("连接服务器中······\n");
			client=new Socket(IP,port);
			Login.remind("服务器已连接\n");
			in=new ObjectInputStream(client.getInputStream());
			out=new ObjectOutputStream(client.getOutputStream());
		}
		catch(IOException e){
			Login.remind("服务器连接失败\n请检查网络设置\n");
			System.out.print("服务器连接失败\n请检查网络设置\n");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static void breakServer(){
		try {
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//发送数据请求并接收数据
	public static void sendRequest(){
		try {
			out.writeObject(null);
			System.out.println("发送数据请求");
			Library.library=(Library) in.readObject();
			Library.synchronize();
			if(Client.user!=null){
				Client.user=Library.getUser(Client.user.getID());                                             //bug好久！
			}
			System.out.println("接收数据");
		} catch (IOException e) {
			e.printStackTrace();
			Client.remind("与服务器断开连接");
			System.exit(0);
			new Client();                    //system.0之后这句话不会执行了
			//Client.login();                        //此处想办法 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	
	//发送返回数据
	public static void returnInfo(){
		try {
			out.writeObject(Library.library);
			System.out.println("发送数据");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//初始化提示对话框
	public void remindDialogInitialize(){
		dialog_remind=new JDialog(this,"提示",true);
		dialog_remind.setSize(260,120);
		dialog_remind.setLocation(this.getX()+WIDTH/2-130,this.getY()+HEIGHT/2-60);
		dialog_remind.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		dialog_remind.setResizable(false);
		label_remind=new JLabel("", JLabel.CENTER);
		dialog_remind.add(label_remind);
	}
	
	
	
	//提示对话框
	public static void remind(String string_remind){
		label_remind.setText(string_remind);
		dialog_remind.setVisible(true);
	}

	
	
	// main方法入口
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e){e.printStackTrace();}
		Client c=new Client();
	}	
}
