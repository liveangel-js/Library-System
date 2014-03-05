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
		super("ͼ�����ϵͳ2.5");
		this.setLocation(X,Y);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		
		this.remindDialogInitialize();	
		this.login();
	}

	
	
	
	
	
	
	
	

	
	
	

	//��½����
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
			Login.remind("���ӷ������С�����������\n");	
			System.out.print("���ӷ������С�����������\n");
			client=new Socket(IP,port);
			Login.remind("������������\n");
			in=new ObjectInputStream(client.getInputStream());
			out=new ObjectOutputStream(client.getOutputStream());
		}
		catch(IOException e){
			Login.remind("����������ʧ��\n������������\n");
			System.out.print("����������ʧ��\n������������\n");
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
	
	
	
	
	//�����������󲢽�������
	public static void sendRequest(){
		try {
			out.writeObject(null);
			System.out.println("������������");
			Library.library=(Library) in.readObject();
			Library.synchronize();
			if(Client.user!=null){
				Client.user=Library.getUser(Client.user.getID());                                             //bug�þã�
			}
			System.out.println("��������");
		} catch (IOException e) {
			e.printStackTrace();
			Client.remind("��������Ͽ�����");
			System.exit(0);
			new Client();                    //system.0֮����仰����ִ����
			//Client.login();                        //�˴���취 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	
	//���ͷ�������
	public static void returnInfo(){
		try {
			out.writeObject(Library.library);
			System.out.println("��������");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//��ʼ����ʾ�Ի���
	public void remindDialogInitialize(){
		dialog_remind=new JDialog(this,"��ʾ",true);
		dialog_remind.setSize(260,120);
		dialog_remind.setLocation(this.getX()+WIDTH/2-130,this.getY()+HEIGHT/2-60);
		dialog_remind.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		dialog_remind.setResizable(false);
		label_remind=new JLabel("", JLabel.CENTER);
		dialog_remind.add(label_remind);
	}
	
	
	
	//��ʾ�Ի���
	public static void remind(String string_remind){
		label_remind.setText(string_remind);
		dialog_remind.setVisible(true);
	}

	
	
	// main�������
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e){e.printStackTrace();}
		Client c=new Client();
	}	
}
