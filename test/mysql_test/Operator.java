package mysql_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Operator extends JFrame {

	private JPanel contentPane;
	private static JTextField add_text;
	private static JTextField delete_text;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	static TextArea area;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operator frame = new Operator();
					frame.setVisible(true);
					frame.setTitle("Database Operator");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Operator() throws UnknownHostException, IOException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton button_2 = new JButton("\u67E5\u8BE2");
		ActionListener query=new Query();
		button_2.addActionListener(query);
		panel_1.add(button_2);
		
		add_text = new JTextField();
		panel_1.add(add_text);
		add_text.setColumns(10);
		add_text.addActionListener(new Add());
		
		JButton bntadd = new JButton("\u6DFB\u52A0");
		bntadd.addActionListener(new Add());
		panel_1.add(bntadd);
		
		delete_text = new JTextField();
		panel_1.add(delete_text);
		delete_text.setColumns(10);
		delete_text.addActionListener(new Delete());
		
		JButton bntdelete = new JButton("\u5220\u9664");
		bntdelete.addActionListener(new Delete());
		panel_1.add(bntdelete);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		area = new TextArea();
		area.setBackground(Color.WHITE);
		area.setEditable(false);
		panel_4.add(area);
		pack();
	}
	static class Query implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			try {
				Socket s = new Socket("127.0.0.1", 9997);
				oos= new ObjectOutputStream(s.getOutputStream());
				ois= new ObjectInputStream(s.getInputStream());
				oos.writeObject(new Integer(2));
				String str=(String)ois.readObject();
				area.setText(str.replace(" ", "\n"));
				area.setCaretPosition(area.getText().length());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static class Add implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			try {
				Socket s = new Socket("127.0.0.1", 9997);
				oos= new ObjectOutputStream(s.getOutputStream());
				ois= new ObjectInputStream(s.getInputStream());
				oos.writeObject(new Integer(4));
				oos.writeObject(add_text.getText());
				add_text.setText("");
				
				s = new Socket("127.0.0.1", 9997);
				oos= new ObjectOutputStream(s.getOutputStream());
				ois= new ObjectInputStream(s.getInputStream());
				oos.writeObject(new Integer(2));
				String str=(String)ois.readObject();
				area.setText(str.replace(" ", "\n"));
				area.setCaretPosition(area.getText().length());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static class Delete implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			try {
				Socket s = new Socket("127.0.0.1", 9997);
				oos= new ObjectOutputStream(s.getOutputStream());
				ois= new ObjectInputStream(s.getInputStream());
				oos.writeObject(new Integer(5));
				oos.writeObject(delete_text.getText());
				delete_text.setText("");
				
				s = new Socket("127.0.0.1", 9997);
				oos= new ObjectOutputStream(s.getOutputStream());
				ois= new ObjectInputStream(s.getInputStream());
				oos.writeObject(new Integer(2));
				String str=(String)ois.readObject();
				area.setText(str.replace(" ", "\n"));
				area.setCaretPosition(area.getText().length());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
