package server_client_test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server_01 {
	
	public static void main(String[] args ) throws Exception{
		Thread t_draw =new Thread(new Draw_Server(9998));	//9998 for drawing items transmission
		t_draw.start();
		Thread t_DB =new Thread(new DB_Server(9997));		//9997 for database operation
		t_DB.start();
		Thread t_msg =new Thread(new Msg_Server(9999));		//9999 for user communication and game control
		t_msg.start();
	}
}


