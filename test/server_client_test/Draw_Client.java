package server_client_test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import drawpad_test.DrawPad;
import drawpad_test.Drawing;

public class Draw_Client {
	private static String ServerIP;
	private static int port;
	public static DrawPad frame;
	private static boolean DEBUG=false;
	private static String UserID ;
	public static ObjectOutputStream oos;
	public static ObjectInputStream ois;
	private static  DataOutputStream dos;
    private static  DataInputStream dis;
	public static String[] ans;
	public static boolean[] f_ans;
	private static int time;
	private static boolean guessout=false;
	private static String def="!*&@^1OAS@F23^@!#!*&@^12#&s(!^(*@(DAOFvb6uyd&c$87";
	private static String tar_ans=def;
	private static Random rand=new Random();
	private static boolean drawer=false;
	private static final String default_time="30";
	public Draw_Client()
	{
		
	}
	public Draw_Client(String ID)
	{
		this();
		UserID=ID;
		init();
	}
	public Draw_Client(String ID,String IP,int pot)
	{
		this();
		UserID=ID;
		ServerIP=IP;
		port=pot;
		init();
	}
	public static void init(){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame = new DrawPad("DrawFrame User:"+UserID);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				try {
					Socket s = new Socket(ServerIP, port);
					oos = new ObjectOutputStream(s.getOutputStream());
					ois = new ObjectInputStream(s.getInputStream());
					
					Socket ss = new Socket("127.0.0.1",9999);
					dos = new DataOutputStream(ss.getOutputStream());
		            dis = new DataInputStream(ss.getInputStream());
		            ActionListener TxLis=new  TxListener();
		            frame.msg.addActionListener(TxLis);
		            frame.trans.addActionListener(TxLis);
		            new Thread(new ReceiveThread()).start();
					
					
			        int type=(Integer)ois.readObject();
//			        System.out.println("User type:"+type);
			        if(type==1){		//1 for drawer
						Runnable r = new Datatrans();
						Thread t = new Thread(r);
						t.start();
			        }else{				//0 for guesser
			        	Runnable r = new Datahandle();
						Thread t = new Thread(r);
						t.start();
			        }
			        
			        
			        
				} catch (ConnectException e) {
					JOptionPane.showMessageDialog(frame, "Server is not available!");
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}


			}

		});
		
	}
	public static void main(String[] args) {
		Draw_Client clt=new Draw_Client("testuser","127.0.0.1",9998);
	}

	public static class Datatrans implements Runnable {
		private static int client_index = 0;
		private static ObjectOutputStream oos=Draw_Client.oos;
		private static MyMouseHandler mousehandler;
		
		
		public void run() {
			System.out.println("Datatrans started.");
			frame.dialog.append("欢迎登陆，本局比赛您为绘画者\n");
			frame.dialog.setCaretPosition(frame.dialog.getText().length());
			mousehandler=new MyMouseHandler();
			frame.drawpad.addMouseMotionListener(mousehandler);
			frame.drawpad.addMouseListener(mousehandler);
			try {
				changeactionlistener();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public static void trans() throws IOException {
			
			if (client_index > frame.drawpad.index){
				client_index = frame.drawpad.index;
				oos.writeObject(new Integer(client_index));
				oos.writeObject(frame.drawpad.DrawingItem[client_index]);
				oos.flush();
			}
				
			for (; client_index < frame.drawpad.index; client_index++) {
				
				if (frame.drawpad.DrawingItem[client_index] == null)
					continue;
//				System.out.println("Writing Objects..");
				oos.writeObject(new Integer(client_index));
				oos.writeObject(frame.drawpad.DrawingItem[client_index]);
				oos.flush();
				if(client_index == frame.drawpad.index) break;
			}
			if(frame.drawpad.index==0){
				oos.writeObject(new Integer(0));
				oos.writeObject(frame.drawpad.DrawingItem[0]);
				oos.flush();
			}
//			System.out.println("client_index:"+client_index);
//			System.out.println("drawpad_index:"+frame.drawpad.index);
		}

		private static class MyMouseHandler extends MouseAdapter
		{
			
				public void mouseMoved(MouseEvent e) {
					if(DEBUG) System.out.println("Mouse moved");
					try {
						trans();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				public void mouseDragged(MouseEvent e) {
					if(DEBUG) System.out.println("Mouse draged");
					try {
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				public void mouseReleased(MouseEvent e) {
					if(DEBUG) System.out.println("Mouse Released");
					try {
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				public void mousePressed(MouseEvent e) {
					if(DEBUG) System.out.println("Mouse Pressed");
					try {
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				
		}
	
		private static void changeactionlistener() throws Exception{
			drawer = true;
			frame.bntback.removeActionListener(frame.bntback.getActionListeners()[0]);
			frame.bntback.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						frame.drawpad.back();
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
			frame.bntfront.removeActionListener(frame.bntfront.getActionListeners()[0]);
			frame.bntfront.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						frame.drawpad.front();
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
			frame.bntreset.removeActionListener(frame.bntreset.getActionListeners()[0]);
			frame.bntreset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						frame.drawpad.reset();
						trans();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			
			
			@SuppressWarnings("resource")
			Socket s = new Socket(ServerIP, 9997);
			ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			os.writeObject(new Integer(2));
			String pre_ans=(String)is.readObject();
			ans=pre_ans.split(" ");
//			System.out.println("pre_ans:"+pre_ans);
			f_ans=new boolean[ans.length];
			for(int i=0;i<f_ans.length;i++)
				f_ans[i]=false;
//			System.out.println("ans.length:"+ans.length);
			
			frame.bntnewrnd.addActionListener(new NewRoundListener());
		}
	
		
		public static class NewRoundListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				try {
					//left for complete
					int t=0,cnt=0;
					while(cnt<=100) {
						cnt++;
						t=rand.nextInt(ans.length-1)+1;
						if(f_ans[t]) continue;
						else{
							f_ans[t]=true;
							break;
						}
					}
//					System.out.println("t:"+t);
					JOptionPane.showMessageDialog(frame, "本局的目标图案是："+ans[t]);
					frame.drawpad.reset();
					frame.drawpad.changedrawable(true);
					dos.writeUTF("drawer::"+UserID);
					dos.writeUTF("time::"+default_time);
					dos.writeUTF("ans::"+ans[t]);
					trans();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public static class Datahandle implements Runnable {
		private static Integer current_index;
		private static ObjectInputStream ois=Draw_Client.ois;
		public void run() {
			System.out.println("Datahandle started.");
			frame.dialog.append("欢迎登陆，本局比赛您为猜画者\n");
			frame.dialog.setCaretPosition(frame.dialog.getText().length());
			try {
				while(ois.available()!=-1){
					handle();
				}
				ois.close();
			} catch (ConnectException e) {
				System.out.println("Server is not available!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		public static void handle() throws IOException {
			
			try {
				
				current_index = (Integer) ois.readObject();
				Drawing t= (Drawing) ois.readObject();
//				System.out.println("receive_client_index:"+current_index);
//				System.out.println("handle_drawpad_index:"+frame.drawpad.index);
//	    		System.out.println("current_index"+current_index);
	    		frame.drawpad.DrawingItem[current_index]=t;
	    		
	    		frame.drawpad.index=current_index;
	    		frame.drawpad.repaint();
	            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class ReceiveThread implements Runnable{
        private String str;
        private boolean iConnect = false;
        private Timer timer;
        public void run(){
            iConnect = true;
            try {
				recMsg();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        public void recMsg() throws Exception {
            try {
                while(iConnect){
                    str = dis.readUTF();
                    if(str.contains("time::"))
                    {
                    	String [] op=str.split("::");
                    	time=Integer.valueOf(op[1]);
                    	frame.res_time.setText(new Integer(time).toString());
                    	timer=new Timer();
    					timer.schedule(new TimerTask() {
    						@SuppressWarnings("deprecation")
    						public void run() {
    							time--;
    							if(time<0)
    							{
    								frame.drawpad.changedrawable(false);
//    								System.out.println("guessout:"+guessout);
    								if(!guessout){
    									JOptionPane.showMessageDialog(frame, "很遗憾，无人猜出答案。\n正确答案为："+tar_ans);
    								}
    								time=0;
    								frame.res_time.setText(new Integer(time).toString());
    								Thread.currentThread().stop();
    							}
    							frame.res_time.setText(new Integer(time).toString());
    						}
    					}, 1000,1000);
                    }else if(str.contains("ans::"))
                    {
                    	String [] op=str.split("::");
                    	tar_ans=op[1];
                    }else if(str.contains("drawer::")){
                    	frame.dialog.append(str.replace("drawer::","新游戏开始，画者为：")+"\n");
                    	frame.dialog.setCaretPosition(frame.dialog.getText().length());
                    	guessout=false;
                    }else if(str.contains(":::")){
                    	String [] op=str.split(":::");
                    	guessout=true;
                    	time=0;
                    	frame.res_time.setText(new Integer(time).toString());
                		JOptionPane.showMessageDialog(frame, "恭喜用户："+op[0]+" 猜出了答案\n正确答案为："+op[1]);
                    }
                    else{
                    	frame.dialog.append(str+"\n");
                    	frame.dialog.setCaretPosition(frame.dialog.getText().length());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

	public static class TxListener implements ActionListener{
        private String str;
        @Override
        public void actionPerformed(ActionEvent e) {
            str = frame.msg.getText().trim();
            frame.msg.setText("");
            try {
            	if(str.contains(tar_ans)&&drawer==true)
            		JOptionPane.showMessageDialog(frame, "禁止在聊天中涉及答案！");
            	else
            		dos.writeUTF(UserID+":"+str);
            } catch (IOException e1) {
                System.out.println("IOException");
                e1.printStackTrace();
            }
        }
        
    }
	
	
}
