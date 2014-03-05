package Server;



import java.io.*;
import java.net.*;

import Common.Library;



public class ClientThread extends Thread{
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;

		
	
	
	public ClientThread(Socket client,int number){
		this.client=client;
		this.setName("客户端"+number);
		try {
			out=new ObjectOutputStream(client.getOutputStream());
			in=new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {	
			e.printStackTrace();
		}	
	}
		
		
		
	public void run() {
		while(true){
			try {
				System.out.print("等待"+this.getName()+"的请求\n");
				Server.remind("等待"+this.getName()+"的请求\n");
				in.readObject();
				System.out.print("接受"+this.getName()+"的请求\n");
				Server.remind("接受"+this.getName()+"的请求\n\n");
				this.sendInfo();	
			} catch (IOException e) {
				e.printStackTrace();
				close();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
		
	
		
	public void close(){
		try {
			if(in!=null){
				in.close();
			}
			out.close();
			client.close();
			//this.stop();
			System.out.print("与 "+this.getName()+"断开\n");
			Server.remind("与 "+this.getName()+"断开\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
		
		
	public void sendInfo(){
		synchronized (Library.library) {
			try {
				out.writeObject(Library.library);
				System.out.print("向"+this.getName()+"发送数据\n");
				Server.remind("向"+this.getName()+"发送数据\n");
				Library.library=(Library) in.readObject();
				Library.synchronize();           //这个是否不需要
				System.out.print("接收"+this.getName()+"的数据\n");
				Server.remind("接收"+this.getName()+"的数据\n");
			} 
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}