
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import drawpad_test.DrawPadComponent;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Font;

public class Drawpadddd extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public  DrawPadComponent drawpad;
	private JTextField msg;
	public JButton bntcolor;
	public JButton bntreset;
	public JButton bntback;
	public JButton bntfront;
	public TextArea dialog;
//	private boolean candraw=false;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Drawpadddd frame = new Drawpadddd();
					frame.setSize(1400, 852);
					frame.setVisible(true);
					frame.drawpad.changedrawable(true);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public Drawpadddd() {
		setResizable(false);
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setHgap(10);
		drawpad= new DrawPadComponent();
		JPanel toolarea = new JPanel();
		getContentPane().add(toolarea, BorderLayout.WEST);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolarea.add(toolBar);
		
		JButton btnPencil = new JButton("Pencil");
		btnPencil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				drawpad.currenttype=0;
			}
		});
		toolBar.add(btnPencil);
		
		JButton btnLine = new JButton("Line");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=1;
			}
		});
		toolBar.add(btnLine);
		
		JButton btnEraser = new JButton("Eraser");
		btnEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=2;
			}
		});
		toolBar.add(btnEraser);
		
		JButton btnRect = new JButton("Rect");
		btnRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=3;
			}
		});
		toolBar.add(btnRect);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=4;
			}
		});
		toolBar.add(btnCircle);
		
		JPanel typearea = new JPanel();
		typearea.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(typearea, BorderLayout.NORTH);
		
		bntcolor = new JButton("\u9009\u62E9\u989C\u8272");
		bntcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.chooseColor();
			}
		});
		
		bntreset = new JButton("\u91CD\u7F6E");
		bntreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.reset();
			}
		});
		
		bntback = new JButton("\u64A4\u9500");
		bntback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.back(); 
			}
		});
		
		JButton bntnewrnd = new JButton("\u65B0\u4E00\u8F6E");
		typearea.add(bntnewrnd);
		typearea.add(bntback);
		
		bntfront = new JButton("\u524D\u8FDB");
		bntfront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.front();
			}
		});
		typearea.add(bntfront);
		typearea.add(bntreset);
		typearea.add(bntcolor);
		DrawPadComponent.Slider sll =  drawpad.new Slider();
		sll.setBackground(Color.GRAY);
		sll.setBorder(null);
		typearea.add(sll);
		
		JLabel time_label = new JLabel("\u5269\u4F59\u65F6\u95F4\uFF1A");
		time_label.setFont(new Font("宋体", Font.PLAIN, 24));
		typearea.add(time_label);
		
		JLabel time = new JLabel("");
		typearea.add(time);
		
		JPanel comarea = new JPanel();
		getContentPane().add(comarea, BorderLayout.EAST);
		comarea.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		comarea.add(panel_5, BorderLayout.SOUTH);
		
		msg = new JTextField();
		panel_5.add(msg);
		msg.setColumns(15);
		
		
		JButton trans = new JButton("发送");
		trans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.append(msg.getText()+"\n");
				msg.setText("");
			}
		});
		panel_5.add(trans);
		
		JLabel label = new JLabel("聊天/答题窗口");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		comarea.add(label, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		comarea.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		dialog = new TextArea("",20,43,TextArea.SCROLLBARS_VERTICAL_ONLY);
		dialog.setBackground(Color.WHITE);
		dialog.setEditable(false);
		System.out.println(this.getHeight());
		dialog.setBounds(10, 10, 220, 660);
		panel.add(dialog);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		
		JPanel drawarea = new JPanel();
		getContentPane().add(drawarea, BorderLayout.CENTER);
		drawarea.setLayout(new BorderLayout(0, 0));
		drawarea.add(drawpad,BorderLayout.CENTER);
		drawarea.setSize(drawpad.getPreferredSize());
		pack();
		
//		if(candraw)
		drawpad.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent arg0) {
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				sll.slider.setValue((int)drawpad.stroke);	
			}
		});
		
		
//		pack();
	}
	public Drawpadddd(String title){
		this();
		this.setTitle(title);
	}
}
