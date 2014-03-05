package Server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;

import Common.Library;



public class Server extends JFrame implements ActionListener{
	public static final int WIDTH=250;
	public static final int HEIGHT=280;
	private static final int X=300;
	private static final int Y=200;
	private static JButton button_open;
	private static JButton button_close;
	private static JButton button_exit;
	private static TextArea text_clientInfo;
	private static ServerThread serverThread;

	
	
	
	public Server(){
		super("图书管理系统2.5服务器");
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(X, Y);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		
		
		
		JPanel controlPanel=new JPanel();
		button_open=new JButton("开启");
		button_open.addActionListener(this);
		controlPanel.add(button_open);
		controlPanel.add(Box.createVerticalStrut(10));
		button_close=new JButton("关闭");
		button_close.addActionListener(this);
		button_close.setEnabled(false);
		controlPanel.add(button_close);
		controlPanel.add(Box.createVerticalStrut(10));
		button_exit=new JButton("退出");
		button_exit.addActionListener(this);
		controlPanel.add(button_exit);
		//this.getContentPane().add(p);
		this.add(controlPanel);
		
		
		
		JPanel infoPanel=new JPanel();
		text_clientInfo=new TextArea("",10,25,TextArea.SCROLLBARS_VERTICAL_ONLY);
		infoPanel.add(text_clientInfo);
		this.add(infoPanel);
		this.setVisible(true);
		
		
		
		

	}
	
	
	
	
	public static void remind(String string_remind){
		text_clientInfo.append(string_remind);
	}
	
	
	
	public void openServer(){
		try {
			Library.load();	
			Library.library.manager.setOn(false);
			for(int i=0;i<Library.library.users.size();i++){
				Library.library.users.get(i).setOn(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		button_open.setEnabled(false);
		button_close.setEnabled(true);
		button_exit.setEnabled(false);
		serverThread=new ServerThread(this);
		serverThread.start();
	}
	
	
	
	
	public void closeServer(){
		button_close.setEnabled(false);
		button_open.setEnabled(true);
		button_exit.setEnabled(true);
		serverThread.close();
		try {
			Library.store();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="开启"){
			this.openServer();
			return;
		}
		if(e.getActionCommand()=="关闭"){
			this.closeServer();
			return;
		}
		if(e.getActionCommand()=="退出"){
			System.exit(0);
		}
	}
	
	
	
	public static void main(String args[]){
		//try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		//(Exception e){e.printStackTrace();}
		Server server=new Server();
	}

}
