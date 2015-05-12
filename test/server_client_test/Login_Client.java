package server_client_test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Encryption.EncryptionUtil;
import Login_test.LoginFrame;
import Login_test.RegisterPanel;

public class Login_Client {
	LoginFrame frame;
	String ServerIP = "127.0.0.1";
	int port = 9997;
	public Login_Client() {
		frame = new LoginFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(950,630);
		init();
	}

	public void init() {

		frame.enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (confirm(frame.user_text.getText(),frame.password_text.getPassword()))
					{
						JOptionPane.showMessageDialog(frame, "登陆成功");
						Draw_Client dclt=new Draw_Client(frame.user_text.getText(),"127.0.0.1",9998);
						frame.dispose();
					}else{
						JOptionPane.showMessageDialog(frame, "用户名或密码错误");
					}
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(e.getMessage().contains("Connection refused: connect"))
					{
						JOptionPane.showMessageDialog(frame, "服务器无法连接");
						System.exit(0);
					}
				}
			}
		});
		frame.r.p.confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!frame.r.p.samepwd())
				{
					JOptionPane.showMessageDialog(frame.r.p, "确认密码失败，请重新输入");
					frame.r.p.reset();
				}else{
					try {
						int t=register(frame.r.p.getID(),frame.r.p.getpwd());
						if(t==1){
							JOptionPane.showMessageDialog(frame.r.p, "注册成功");
							frame.r.dispose();
						}else if(t==2){
							JOptionPane.showMessageDialog(frame.r.p, "用户已存在");
						}else {
							JOptionPane.showMessageDialog(frame.r.p, "Error");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						if(e1.getMessage().contains("Connection refused: connect"))
						{
							JOptionPane.showMessageDialog(frame.r.p, "服务器无法连接");
							System.exit(0);
						}
					}
				}
			}
		});
		
	}

	private boolean confirm(String ID, char[] pwd) throws Exception {
		Socket s = new Socket(ServerIP, port);
		ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois= new ObjectInputStream(s.getInputStream());
		oos.writeObject(new Integer(1)); //1 for login
		oos.writeObject(ID);
		String pwd_md5=(String)ois.readObject();
		String pwd_current=EncryptionUtil.getHash(new String(pwd), "MD5");
//		System.out.println("pwd_md5:"+pwd_md5);
//		System.out.println("pwd_current:"+pwd_current);
		if(pwd_md5.equals(pwd_current)){
//			System.out.println("bingo");
			return true;
		}
		return false;
	}
	
	private int register(String ID, char[] pwd) throws Exception {
		Socket s = new Socket(ServerIP, port);
		ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois= new ObjectInputStream(s.getInputStream());
		oos.writeObject(new Integer(0)); //0 for register
		
		oos.writeObject(ID);
		
		String pwd_current=EncryptionUtil.getHash(new String(pwd), "MD5");
		oos.writeObject(pwd_current);
		int returnvalue=(Integer)ois.readObject();
		return returnvalue;
	}

	public static void main(String[] args) {
		Login_Client clt = new Login_Client();
	}

}
