package Login_test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Encryption.EncryptionUtil;
import drawpad_test.DrawPad;

public class LoginFrame extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension ScreenSize = kit.getScreenSize();
	int Screen_width = ScreenSize.width;
	int Screen_height = ScreenSize.height;
	private final int DEFAULT_WIDTH = Screen_width >> 1;
	private final int DEFAULT_HEIGHT = Screen_height >> 1;
	int mid_x = Screen_width >> 1;
	int mid_y = Screen_height >> 1;
	public RegisterFrame r = new RegisterFrame();
	
	public JButton enter;
	public JTextField user_text;
	public JPasswordField password_text;
	public JButton register;
	
	public LoginFrame() {
//		this.setUndecorated(true);
		setTitle("Login");
		setResizable(false);
		add(new LoginPanel());
		pack();
		setLocation(DEFAULT_WIDTH-this.getWidth()/2, DEFAULT_HEIGHT-this.getHeight()/2);
	}
//	public static void main(String [] args)
//	{
//		LoginFrame lf=new LoginFrame();
//		lf.setVisible(true);
//		lf.repaint();
//		lf.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}

	class LoginPanel extends JPanel {

		ImageIcon backgroundimg ;

		public LoginPanel() {
			
//			setOpaque(true);
			setLayout(new BorderLayout());
			InfoPanel info = new InfoPanel();
			add(info, BorderLayout.EAST);
			backgroundimg = new ImageIcon("b.jpg");
			
			
			
		}

		public void paintComponent(Graphics g) {
			g.drawImage(backgroundimg.getImage(), 0, 0, null);
		}

		public Dimension getPreferredSize() {
			return new Dimension(backgroundimg.getImage().getWidth(null),
					backgroundimg.getImage().getHeight(null));
		}
	}

	class InfoPanel extends JPanel {
		public InfoPanel() {
			setOpaque(false);
			JLabel user_label = new JLabel("用户名：");
			Font f = new Font("Serif", Font.BOLD, 20);
			user_label.setFont(f);
			JLabel password_label = new JLabel("密码：");
			password_label.setFont(f);
			user_text = new JTextField(15);
			user_text.setSize(1, 1);
			password_text = new JPasswordField(15);
			setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
			setLayout(new GridLayout(30, 1));
			int k = 0;
			for (int i = 1; i <= 10; i++)
				// 占位
				// add(new JLabel(k+++""));
				add(new JLabel());
			add(user_label);
			add(user_text);
			add(new JLabel());
			add(password_label);
			add(password_text);
			add(new JLabel());
			enter = new JButton("确定");
			
			
			add(enter);

			add(new JLabel());
			register = new JButton("注册");
			register.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					r.setVisible(true);
					r.pack();
					r.setLocation(mid_x-330/2, mid_y-530/2);
					
				}
			});
			add(register);

		}
	}
}
