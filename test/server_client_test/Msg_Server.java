package server_client_test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Msg_Server implements Runnable {
	static ObjectInputStream ois;
	static ObjectOutputStream oos;
	static String def="!*&@^1OAS@F23^@!#!*&@^12#&s(!^(*@(DAOFvb6uyd&c$87";
	static String tar_ans=def;
	static String now_ans="";
	static String drawer;
	static List<ClientThread> clients = new ArrayList<ClientThread>();
	static int i;
	ServerSocket msg_socket;
	public static String str = "";
	int port;

	public Msg_Server() {
	}

	public Msg_Server(int p) throws IOException {
		msg_socket = new ServerSocket(p);
		port = p;
	}

	public void run() {
		try{
			boolean iConnect = false;
	        iConnect = true;
	        while(iConnect){
	        	System.out.println("Msg_Server started.");
	            Socket s = msg_socket.accept();
	            ClientThread currentClient = new ClientThread(s);
	            clients.add(currentClient);
	            new Thread(currentClient).start();
	        }
		} catch (IOException e) {
        System.out.println("IOException");
        e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t_msg = new Thread(new Msg_Server(9999));
		t_msg.start();
	}
	
	 class ClientThread implements Runnable {
	        private Socket s;
	        private DataInputStream dis;
	        private DataOutputStream dos;
	        private String str;
	        private boolean iConnect = false;
	        
	        ClientThread(Socket s){
	            this.s = s;
	            iConnect = true;
	        }
	        
	        public void run(){
	            try {
	                
	                while(iConnect){
	                    dis = new DataInputStream(s.getInputStream());
	                    str = dis.readUTF();
	                    System.out.println(str);
	                    
	                    String [] op;
	                    if(str.contains("ans::"))
	                    {
	                    	op=str.split("::");
	                    	tar_ans=op[1].trim();
	                    }else if(str.contains("drawer::"))
	                    {
	                    	op=str.split("::");
	                    	drawer = op[1].trim();
	                    }else if(!str.contains("time")&&str.contains(":")){
	                    	op=str.split(":");
	                    	now_ans=op[1].trim();
	                    	if(now_ans.equals(tar_ans))
	                    	{
	                    		//scores of op[0] ++
	                    		addscore(op[0].trim());
	                    		addscore(drawer.trim());
	                    		for(int i=0; i<clients.size(); i++){
	    	                        ClientThread c = clients.get(i);
	    	                        c.sendMsg(op[0]+":::"+now_ans);
	    	                    }
	                    		tar_ans=def;
	                    		continue;
	                    	}
	                    }
	                    for(int i=0; i<clients.size(); i++){
	                        ClientThread c = clients.get(i);
	                        c.sendMsg(str);
	                    }
	                    
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	        }
	        
	        public void sendMsg(String str){
	            try {
	                dos = new DataOutputStream(this.s.getOutputStream());
	                dos.writeUTF(str);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        public void addscore(String user) throws Exception
	    	{
	    		@SuppressWarnings("resource")
	    		Socket s = new Socket("127.0.0.1", 9997);
	    		ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
	    		oos.writeObject(new Integer(3)); //1 for login
	    		oos.writeObject(user);
	    	}
	        
	    }
	
}
