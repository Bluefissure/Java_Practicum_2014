package Login_test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.CompoundBorder;

public class RegisterPanel extends JPanel {
	private JTextField username_text;
	private JTextField verification_text;
	private JPasswordField password;
	private JPasswordField passwordverify;
	public JButton confirm;
	/**
	 * Create the panel.
	 */
	public RegisterPanel() {
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		

		JLabel username_label = new JLabel("用户名：");
		username_label.setHorizontalAlignment(SwingConstants.RIGHT);
		username_label.setFont(new Font("宋体", Font.PLAIN, 20));
		username_label.setBounds(32, 126, 103, 24);
		add(username_label);
		
		JLabel password_label = new JLabel("密码：");
		password_label.setHorizontalAlignment(SwingConstants.RIGHT);
		password_label.setFont(new Font("宋体", Font.PLAIN, 20));
		password_label.setBounds(32, 189, 103, 24);
		add(password_label);
		
		JLabel passwordverify_label = new JLabel("确认密码：");
		passwordverify_label.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordverify_label.setFont(new Font("宋体", Font.PLAIN, 20));
		passwordverify_label.setBounds(32, 252, 103, 24);
		add(passwordverify_label);
		
//		JLabel verification_label = new JLabel("验证码：");
//		verification_label.setHorizontalAlignment(SwingConstants.RIGHT);
//		verification_label.setFont(new Font("宋体", Font.PLAIN, 20));
//		verification_label.setBounds(32, 312, 103, 24);
//		add(verification_label);
		
		username_text = new JTextField();
		username_text.setBounds(155, 126, 138, 24);
		add(username_text);
		username_text.setColumns(10);
		
//		verification_text = new JTextField();
//		verification_text.setColumns(10);
//		verification_text.setBounds(155, 312, 138, 24);
//		add(verification_text);
		
		password = new JPasswordField();
		password.setBounds(155, 189, 138, 24);
		add(password);
		
		passwordverify = new JPasswordField();
		passwordverify.setBounds(155, 252, 138, 24);
		add(passwordverify);
		
		JLabel register_label = new JLabel("注册");
		register_label.setHorizontalAlignment(SwingConstants.CENTER);
		register_label.setFont(new Font("宋体", Font.BOLD, 30));
		register_label.setBounds(101, 44, 121, 40);
		add(register_label);
		
		confirm = new JButton("确认");
		confirm.setBounds(190, 436, 103, 31);
		add(confirm);    
		
		JButton reset = new JButton("重置");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		reset.setBounds(51, 436, 103, 31);
		add(reset);
		
//		JButton changeimg = new JButton("换张图片");
//		changeimg.setBounds(190, 374, 93, 23);
//		add(changeimg);
		
		JPanel img = new JPanel();
		img.setBounds(51, 362, 103, 45);
		add(img);

	}
	public Dimension getPreferredSize(){return new Dimension(330,530);}
	public boolean samepwd(){
		char[] ch1=password.getPassword(),ch2=passwordverify.getPassword();
		if(ch1.length!=ch2.length) return false;
		for(int i=0;i<ch1.length;i++){
			if(ch1[i]!=ch2[i]) return false;
		}
		return true;
	}
	public void reset(){
		username_text.setText("");
		password.setText("");
		passwordverify.setText("");
	}
	public String getID(){
		return username_text.getText();
	}
	public char[] getpwd(){
		return password.getPassword();
	}

}
