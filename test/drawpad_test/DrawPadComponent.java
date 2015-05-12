package drawpad_test;

import javax.swing.*;
import javax.swing.event.*;

import drawpad_test.Drawing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DrawPadComponent extends JPanel{
	/**
	 * 
	 */
	private final int MAXN = 100000;
	private final int DEFAULT_WIDTH = 700;
	private final int DEFAULT_HEIGTH = 500;
	public int currenttype=0;
	public int index = 0;
	private boolean rightpressed=false;
	private double x1,x2,y1,y2;
	private int R,G,B;
	public Drawing [] DrawingItem= new Drawing[MAXN];
	public Slider sl ;
	private Stack <Integer> preindex = new Stack<Integer>();
	private Stack <Integer> frontindex = new Stack<Integer>();
	private mouselistener mselisten;
	private MouseMotionHandler msehandle;
	boolean DEBUG=false;
	public static float stroke;
	private ChangeListener listener= new ChangeListener()
    {
        public void stateChanged(ChangeEvent event)
        {
           // update text field when the slider value changes
           JSlider source = (JSlider) event.getSource();
           stroke=source.getValue();
        }
     };;
	public DrawPadComponent()
	{
		setLayout(null);
		setBackground(Color.WHITE);
		mselisten= new mouselistener();
		msehandle=new MouseMotionHandler();
		if(DEBUG){
					JButton selecttype = new JButton("改变种类");
					selecttype.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							currenttype=(currenttype+1)%5;
						}
					});
					selecttype.setBounds(100,10,100,50);
					add(selecttype);
					
					JButton selectcolor = new JButton("选择颜色");
					selectcolor.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							chooseColor();
						}
					});
					selectcolor.setBounds(200,10,100,50);
					add(selectcolor );
		}
	}
	public void reset()
	{
		index=0;
		preindex.clear();
		frontindex.clear();
		repaint();
	}
	public void back()
	{
		
		if(preindex.empty()) return;
		if(index-(Integer)preindex.peek()!=1)
		{
			frontindex.push(index);
		}
		if(!preindex.empty())
			index=(Integer)preindex.pop();
		repaint();
//		System.out.println("preindex.peek:"+(preindex.empty()?-1:(Integer)preindex.peek()));
//		System.out.println("frontindex.peek:"+(frontindex.empty()?-1:(Integer)frontindex.peek()));
//		System.out.println("index:"+index);
	}
	public void front()

	{
		if(frontindex.empty()) return;
		if(index-(Integer)frontindex.peek()!=1)
		{
			preindex.push(index);
		}
		if(!frontindex.empty())
			index=(Integer)frontindex.pop();
		repaint();
//		System.out.println("preindex.peek:"+preindex.peek());
//		System.out.println("frontindex.peek:"+(frontindex.empty()?-1:(Integer)frontindex.peek()));
//		System.out.println("index:"+index);
	}
	public void changedrawable(boolean f){
		if(f){
			addMouseListener(mselisten);
			addMouseMotionListener(msehandle);
		}else{
			if(this.getMouseListeners().length>0)
				this.removeMouseListener(mselisten);
			if(this.getMouseMotionListeners().length>0)
				this.removeMouseMotionListener(msehandle);
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for(int i=0;i<=index;i++)
		{
			if(DrawingItem[i]!=null)
				draw(g2d,DrawingItem[i]);
		}
		
	}
	public void chooseColor()
	{
		Component drawpad = null;
		Color color = new Color(0,0,0);
		color=JColorChooser.showDialog(this, "请选择颜色", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = G = B = 0;
		}
	}
	public void createNewitem()
	{
		if(rightpressed) return;
		index++;
//		System.out.println("index:"+index);
		if(index>=MAXN) index%=MAXN;
		switch(currenttype){
		case 0:
			DrawingItem[index]=new Pencil();
			break;
		case 1:
			DrawingItem[index]=new Line();
			break;
		case 2:
			DrawingItem[index]=new Eraser();
			break;
		case 3:
			DrawingItem[index]=new Rect();
			break;
		case 4:
			DrawingItem[index]=new Ellipse();
			break;
		}
		DrawingItem[index].R = R;
		DrawingItem[index].G = G;
		DrawingItem[index].B = B;
		DrawingItem[index].stroke = stroke;
			
	}
	void draw(Graphics2D g2d, Drawing i) {
		i.draw(g2d);
	}
	class mouselistener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			preindex.push(index);
			if(!frontindex.empty())
				frontindex.clear();
//			frontindex.clear();
			int mods=e.getModifiers();
			//鼠标右键
			if((mods&InputEvent.BUTTON3_MASK)!=0){
			//弹出菜单
//				System.out.println("右键单击");
				rightpressed=true;
				if(sl==null)
				{
					sl =new Slider();
					sl.setVisible(true);
//					System.out.println("W:"+sl.getWidth()+"\nH:"+sl.getHeight());
					sl.setBounds(e.getX(),e.getY(),sl.getWidth(),sl.getHeight());
					DrawPadComponent.this.add(sl);
					DrawPadComponent.this.validate();
					sl.requestFocusInWindow();
				}
			}
			else
			{
				rightpressed=false;
				if(sl!=null)
					DrawPadComponent.this.remove(sl);
				sl=null;
				createNewitem();
				DrawingItem[index].x1 = DrawingItem[index].x2 = e.getX();
				DrawingItem[index].y1 = DrawingItem[index].y2 = e.getY();
//				System.out.println("pressed");
//				System.out.println("color:"+DrawingItem[index].R+" "+DrawingItem[index].G+" "+DrawingItem[index].B);
//				System.out.println("class:"+DrawingItem[index].getClass());
				
			}
			
		}
		public void mouseReleased(MouseEvent e)
		{
			frontindex.push(index);
			if(rightpressed) return ;
			createNewitem();
			DrawingItem[index].x2  = DrawingItem[index].x1 = e.getX();
			DrawingItem[index].y2  = DrawingItem[index].y1 = e.getY();
			repaint();
			
		}
	}
	private class MouseMotionHandler implements MouseMotionListener
	   {
	      public void mouseMoved(MouseEvent e)
	      {

	      }

	      public void mouseDragged(MouseEvent e)
	      {
	    	  if(rightpressed)return ;
	    	  if(currenttype==0||currenttype==2){
	    	  		createNewitem();
//	    	  		System.out.println("dragged\nindex:"+index);
	    	  		DrawingItem[index - 1].x2 = 
	    	  				DrawingItem[index].x2 =
	    	  				DrawingItem[index].x1 = e.getX();
	    	  		DrawingItem[index - 1].y2 = 
	    	  				DrawingItem[index].y2 = 
	    	  				DrawingItem[index].y1 = e.getY();
	    	  }
	    	  else{
	    	  		DrawingItem[index].x2= e.getX();
	    	  		DrawingItem[index].y2= e.getY();
	    	  }
	    	  repaint();
	      }
	   }
	public Dimension getPreferredSize(){return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGTH);}
	public class Slider extends JPanel{
		public JSlider slider ;
		public ChangeListener listener= new ChangeListener()
	    {
	        public void stateChanged(ChangeEvent event)
	        {
	           // update text field when the slider value changes
	           JSlider source = (JSlider) event.getSource();
	           DrawPadComponent.stroke=source.getValue();
	        }
	     };
		public Slider()
		{
			slider = new JSlider(0,50,(int)DrawPadComponent.this.stroke);
//			JSlider slider = new JSlider(0,50,0);
		     slider.setPaintTicks(true);
		   	 slider.setPaintLabels(true);
		   	 slider.setMajorTickSpacing(10);
		   	 slider.setMinorTickSpacing(5);
		   	 addSlider(slider, "");
//		   	 this.setBackground(Color.WHITE);
		   	 this.setSize(250,70);
//		   	 this.setSize(slider.getSize());
		   	 setOpaque(false);
		   	
		}
		public void addSlider(JSlider s, String description)
		   {
		      s.addChangeListener(listener);
		      JPanel panel = new JPanel();      
		      panel.add(s);
		      panel.setBackground(Color.WHITE);
		      panel.add(new JLabel(description));
		      panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		      panel.setOpaque(false);
//		      GridBagConstraints gbc = new GridBagConstraints();
//		      gbc.gridy = this.getComponentCount();
//		      gbc.anchor = GridBagConstraints.WEST;
//		      this.add(panel, gbc);  
		      this.add(panel);
		   }
	}
}