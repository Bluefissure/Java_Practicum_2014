package server_client_test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Draw_Server implements Runnable
{
	private static boolean  drawer=true;
	static Object temp[]=new Object[100000];
	static int server_index;
	static ObjectInputStream ois;
	static ObjectOutputStream oos;
	static int i;
	ServerSocket draw_socket;
	int port;
	public Draw_Server() {
	}
	public Draw_Server(int p) throws IOException {
		draw_socket = new ServerSocket(p);
		port=p;
	}
	public void run() {
		try
	      {  
	         i = 1;
	         System.out.println("Draw_Server started.");
	         while (true)
	         {  
	            Socket incoming = draw_socket.accept();
	            oos= new ObjectOutputStream(incoming.getOutputStream());
	        	ois= new ObjectInputStream(incoming.getInputStream());  
	        	
	            System.out.println("Draw_Server Spawning " + i);
//	            System.out.println("drawer:"+drawer);
	            
	            if(i==1){
	            	server_index=0;
	            	Runnable r = new DataHandler(incoming);
		            Thread t = new Thread(r);
		            t.start();
		            drawer=false;
	            }else{
	            	Runnable r = new DataTransfer(incoming);
		            Thread t = new Thread(r);
		            t.start();
	            }
	            i++;
	         }
	      }
	      catch (Exception e)
	      {  
	         e.printStackTrace();
	      }
	   }
}

class DataHandler implements Runnable
{ 
   private Socket incoming;
   ObjectInputStream ois=Draw_Server.ois;
   ObjectOutputStream oos=Draw_Server.oos;
   public DataHandler(Socket s)
   { 
      incoming = s; 
   }

   public void run(){  
      try{  
         try{
//        	InputStream inStream = incoming.getInputStream();
//        	 oos= new ObjectOutputStream(incoming.getOutputStream());
//        	ois= new ObjectInputStream(incoming.getInputStream());  
//            OutputStream outStream = incoming.getOutputStream();
        	
        	oos.writeObject(1);
        	oos.flush();
        	
        	while(ois.available()!=-1)
        	{
        		try {
        			Integer handle_index=(Integer) ois.readObject();
            		Object t= ois.readObject();
//            		System.out.println("handle_index"+handle_index);
//                    System.out.println(t);
//                    System.out.println("Server_01.server_index:"+Server_01.server_index);
//            		if(t==null) continue;
            		Draw_Server.temp[handle_index]=t;
            		Draw_Server.server_index=handle_index;
                    
                    
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					if(e.getMessage().equals("Connection reset")){
						Draw_Server.i--;
						break;
					}
						
				}
                
        	}
        	ois.close();
        	
         	}
         catch (Exception e){
        	 e.printStackTrace();
         }
         finally{
            incoming.close();
         }
      }
      catch (Exception e){  
         e.printStackTrace();
      }
   }
}
class DataTransfer implements Runnable
{ 
   private Socket incoming;
   private Integer trans_index=0;
   ObjectOutputStream oos=Draw_Server.oos;
   ObjectInputStream ois=Draw_Server.ois;
   
   public DataTransfer(Socket s)
   { 
      incoming = s; 
   }

   public void run(){  
      try{  
         try{
        	oos.writeObject(0);
        	while(true){
//        		System.out.println("trans_index:"+trans_index);
//        		System.out.println("Draw_Server.server_index:"+Draw_Server.server_index);
        		if(Draw_Server.server_index==0){
        			oos.writeObject(new Integer(0));
					oos.writeObject(null);
        		}
        		if (trans_index  > Draw_Server.server_index )
        		{
        			trans_index  = Draw_Server.server_index;
	        		oos.writeObject(new Integer(trans_index));
					oos.writeObject(Draw_Server.temp[trans_index]);
        		}
        		for (; trans_index <= Draw_Server.server_index; trans_index++) {
    				if (Draw_Server.temp[trans_index] == null)
    					continue;
//    				System.out.println("trans_index:"+trans_index);
//    				System.out.println("Draw_Server.server_index:"+Draw_Server.server_index);
    				oos.writeObject(new Integer(trans_index));
    				oos.writeObject(Draw_Server.temp[trans_index]);
//    				if(trans_index == Draw_Server.server_index) break;
    			}
        		trans_index--;
        		Thread.sleep(10);
        	}
        	
         }
         catch (Exception e){
        	 e.printStackTrace();
        	 System.out.println(e.getMessage());
        	 if(e.getMessage().equals("Connection reset by peer: socket write error")){
        		 Draw_Server.i--;
        	 }
         }
         finally{
//            incoming.close();
         }
      }
      catch (Exception e){  
         e.printStackTrace();
      }
   }
}
