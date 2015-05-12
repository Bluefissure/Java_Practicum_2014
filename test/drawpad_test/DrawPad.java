package drawpad_test;


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

public class DrawPad extends JFrame {

	/**
	 * Launch the application.
	 */
	
	public  DrawPadComponent drawpad;
	public  JTextField msg;
	public JButton bntcolor;
	public JButton bntreset;
	public JButton bntback;
	public JButton bntfront;
	public JButton bntnewrnd;
	public JButton trans;
	public TextArea dialog;
	public JLabel res_time;
//	private boolean candraw=false;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawPad frame = new DrawPad();
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
	public DrawPad() {
		setResizable(false);
		setSize(1400, 852);
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setHgap(10);
		drawpad= new DrawPadComponent();
		JPanel toolarea = new JPanel();
		getContentPane().add(toolarea, BorderLayout.WEST);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolarea.add(toolBar);
		
		JButton btnPencil = new JButton("铅笔");
		btnPencil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				drawpad.currenttype=0;
			}
		});
		toolBar.add(btnPencil);
		
		JButton btnLine = new JButton("直线");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=1;
			}
		});
		toolBar.add(btnLine);
		
		JButton btnEraser = new JButton("橡皮");
		btnEraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=2;
			}
		});
		toolBar.add(btnEraser);
		
		JButton btnRect = new JButton("矩形");
		btnRect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=3;
			}
		});
		toolBar.add(btnRect);
		
		JButton btnCircle = new JButton("圆");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.currenttype=4;
			}
		});
		toolBar.add(btnCircle);
		
		JPanel typearea = new JPanel();
		typearea.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(typearea, BorderLayout.NORTH);
		
		bntcolor = new JButton("选择颜色");
		bntcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.chooseColor();
			}
		});
		
		bntreset = new JButton("重置");
		bntreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.reset();
			}
		});
		
		bntback = new JButton("撤销");
		bntback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawpad.back(); 
			}
		});
		
		bntnewrnd = new JButton("新一轮");
		typearea.add(bntnewrnd);
		typearea.add(bntback);
		
		bntfront = new JButton("前进");
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
		
		JLabel time_label = new JLabel("剩余时间");
		time_label.setFont(new Font("宋体", Font.PLAIN, 24));
		typearea.add(time_label);
		
		res_time = new JLabel("");
		res_time.setFont(new Font("宋体", Font.PLAIN, 24));
		typearea.add(res_time);
		
		JPanel comarea = new JPanel();
		getContentPane().add(comarea, BorderLayout.EAST);
		comarea.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		comarea.add(panel_5, BorderLayout.SOUTH);
		
		msg = new JTextField();
		panel_5.add(msg);
		msg.setColumns(15);
		
		
		trans = new JButton("发送");
//		trans.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				dialog.append(msg.getText()+"\n");
//				msg.setText("");
//			}
//		});
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
		dialog.setBounds(10, 10, 220, 660);
		panel.add(dialog);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		
		JPanel drawarea = new JPanel();
		getContentPane().add(drawarea, BorderLayout.CENTER);
		drawarea.setLayout(new BorderLayout(0, 0));
		drawarea.add(drawpad,BorderLayout.CENTER);
		drawarea.setSize(drawpad.getPreferredSize());
		
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
		
		
	}
	public DrawPad(String title){
		this();
		this.setTitle(title);
	}
}
