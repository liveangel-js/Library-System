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
		this.setName("�ͻ���"+number);
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
				System.out.print("�ȴ�"+this.getName()+"������\n");
				Server.remind("�ȴ�"+this.getName()+"������\n");
				in.readObject();
				System.out.print("����"+this.getName()+"������\n");
				Server.remind("����"+this.getName()+"������\n\n");
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
			System.out.print("�� "+this.getName()+"�Ͽ�\n");
			Server.remind("�� "+this.getName()+"�Ͽ�\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
		
		
	public void sendInfo(){
		synchronized (Library.library) {
			try {
				out.writeObject(Library.library);
				System.out.print("��"+this.getName()+"��������\n");
				Server.remind("��"+this.getName()+"��������\n");
				Library.library=(Library) in.readObject();
				Library.synchronize();           //����Ƿ���Ҫ
				System.out.print("����"+this.getName()+"������\n");
				Server.remind("����"+this.getName()+"������\n");
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