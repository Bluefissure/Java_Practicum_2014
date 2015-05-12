package server_client_test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
public class DB_Server implements Runnable
{
	Connection conn = null;
    String sql;
    String url = "jdbc:mysql://localhost:3306/test01?"
            + "user=root&password=&useUnicode=true&characterEncoding=gbk";
    ResultSet rs;
    String pwd_md5;
    String userID;
    
	static ObjectInputStream ois;
	static ObjectOutputStream oos;
	static int i;
	ServerSocket DB_socket;
	int port;
	public DB_Server() {
	}
	public DB_Server(int p) throws IOException {
		DB_socket = new ServerSocket(p);
		port=p;
	}
	
	public void run() {
		try
	      {  
	         i = 1;
	         System.out.println("DB_Server started.");
	         while (true)
	         {  
	            Socket incoming = DB_socket.accept();
	            oos= new ObjectOutputStream(incoming.getOutputStream());
	        	ois= new ObjectInputStream(incoming.getInputStream());  
	        	
	            System.out.println("DB_Server Spawning " + i);
	            int type,result;
	            type=0;
	            type = (Integer) ois.readObject();
	            
	            new com.mysql.jdbc.Driver();
//	            System.out.println("Load MySQL Successfully");
	            conn = DriverManager.getConnection(url);
	            Statement stmt = conn.createStatement();
	            if(type==0)	//0 for register
	            {
	            	try{
	            		userID=(String)ois.readObject();
		            	pwd_md5=(String)ois.readObject();
//			            System.out.println("userID"+userID+"\npwd_md5:"+pwd_md5);
			            sql="insert into user values('"+userID+"','"+pwd_md5+"',0)";
			            result=stmt.executeUpdate(sql);
			            if(result!=-1){
//			            	System.out.println("Insert successfully");
			            	oos.writeObject(new Integer(1));	//successful
			            }else{
//			            	System.out.println("Insert failed");
			            	oos.writeObject(new Integer(0));	//failed
			            }
	            	}catch (Exception e){
	            		e.printStackTrace();
	            		if(e.getMessage().contains("Duplicate entry"))
	            			oos.writeObject(new Integer(2));//2 for already exist
	            	}
	            }
	            else if(type==1) //1 for login
	            {
	            	String userID=(String)ois.readObject();
	            	sql="select password from user where username='"+userID+"'";
	            	rs = stmt.executeQuery(sql);
	            	rs.next();
	            	try{
		            	pwd_md5=rs.getString(1).toString();
//		                System.out.println("pwd:"+pwd_md5);
		                oos.writeObject(pwd_md5);
	            	}catch (Exception e)
	            	{
	            		if(e.getMessage().contains("Illegal operation on empty result set."))
	            		{
	            			oos.writeObject("");
	            		}
	            	}
	            }
	            else if(type==2)//2 for apply for ans
	            {
	            	
	            	sql="select * from answer";
	            	rs = stmt.executeQuery(sql);
	            	String str="";
	            	while(rs.next())
	            	{
	            		try{
//			            	System.out.println(rs.getString(1));
			            	str=str+" "+rs.getString(1);
			            	i++;
		            	}catch (Exception e)
		            	{
		            		if(e.getMessage().contains("Illegal operation on empty result set."))
		            		{
		            			oos.writeObject("");
		            		}
		            	}
	            	}
	            	System.out.println("ans:"+str);
	            	oos.writeObject(str);
	            }
	            else if(type==3)//3 for add score
	            {
	            	String userID=(String)ois.readObject();
	            	System.out.println("UserID:"+userID);
	            	sql="update user set score=score+1 where username='"+userID+"'";
	            	int i = stmt.executeUpdate(sql);
	            	System.out.println("result:"+i);
	            }
	            else if(type==4)//4 for add answer
	            {
	            	String item=(String)ois.readObject();
	            	sql="INSERT INTO `test01`.`answer` (`ans`) VALUES ('"+item+"');";
	            	int i = stmt.executeUpdate(sql);
	            	System.out.println("result:"+i);
	            }
	            else if(type==5)//5 for delete answer
	            {
	            	String item=(String)ois.readObject();
	            	sql="DELETE FROM `test01`.`answer` WHERE `ans`='"+item+"';";
	            	int i = stmt.executeUpdate(sql);
	            	System.out.println("result:"+i);
	            }
	            i++;
	         }
	      }
	      catch (Exception e)
	      {  
	         e.printStackTrace();
	      }
	   }

	public static void main(String[] args ) throws Exception{
		Thread t_DB =new Thread(new DB_Server(9997));
		t_DB.start();
		JFrame f=new JFrame("¥ ”Ôø‚");
		JLabel add_label =new JLabel("ÃÌº”¥ ”Ô");
		
		
	}
}
