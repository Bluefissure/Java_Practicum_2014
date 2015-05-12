package Login_test;

import javax.swing.JFrame;

public class RegisterFrame extends JFrame {
	public RegisterPanel p=new RegisterPanel();
	public RegisterFrame()
	{
		setTitle("Register");
		add(p);
		pack();
		setResizable(false);
	}
}
