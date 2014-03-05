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
			System.out.print("服务器开启\n");
			Server.remind("服务器开启\n");
			
			while(true){ 
				System.out.print("等待客户端连接请求\n");         //也可以采用（）参数控制服务器的关闭
				Server.remind("等待客户端连接请求\n");
				Socket client=server.accept();
				System.out.print("客户端"+number+"已经建立连接\n");
				Server.remind("客户端"+number+"已经建立连接\n");
				ClientThread clientThread=new ClientThread(client,number++);
				clientThread.start();
				clientThreads.add(clientThread);	
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.print("服务器关闭\n");
			Server.remind("服务器关闭\n");
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
