package Server;



import java.io.*;
import java.net.*;
import java.util.*;



public class ServerThread extends Thread {
	private ServerSocket server;
	private ArrayList<ClientThread>clientThreads=new ArrayList<ClientThread>();
	Server s;
	private static int number=0;
	
	
	
	public ServerThread(Server s){
		this.s=s;
	}
	
	
	public void run(){
		try{
			server=new ServerSocket(8888);
			System.out.print("����������\n");
			Server.remind("����������\n");
			
			while(true){ 
				System.out.print("�ȴ��ͻ�����������\n");         //Ҳ���Բ��ã����������Ʒ������Ĺر�
				Server.remind("�ȴ��ͻ�����������\n");
				Socket client=server.accept();
				System.out.print("�ͻ���"+number+"�Ѿ���������\n");
				Server.remind("�ͻ���"+number+"�Ѿ���������\n");
				ClientThread clientThread=new ClientThread(client,number++);
				clientThread.start();
				clientThreads.add(clientThread);	
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.print("�������ر�\n");
			Server.remind("�������ر�\n");
		}
	}
	
	
	
	
	public void close(){
		try {
			server.close();
			for(int i=0;i<clientThreads.size();i++){
				clientThreads.get(i).close();
			}
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
