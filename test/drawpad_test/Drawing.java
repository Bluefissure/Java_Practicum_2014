package drawpad_test;
import java.awt.*;
import java.io.Serializable;
public class Drawing implements Serializable {
	int x1,x2,y1,y2;   	    
	int  R,G,B;				
	float stroke=5 ;			
	void draw(Graphics2D g ){}
}

class Pencil extends Drawing
{
	void draw(Graphics2D g2)
	{
//		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new Color(R,G,B));
		g2.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g2.drawLine(x1, y1, x2, y2);
	}
}
class Line extends Drawing
{
	void draw(Graphics2D g2)
	{
		g2.setPaint(new Color(R,G,B));
		g2.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g2.drawLine(x1, y1, x2, y2);
	}
}
class Eraser extends Drawing
{
	void draw(Graphics2D g2)
	{
		g2.setPaint(new Color(255,255,255));
		g2.setStroke(new BasicStroke(stroke+10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g2.drawLine(x1, y1, x2, y2);
	}
}
class Rect extends Drawing
{
	void draw(Graphics2D g2)
	{
		g2.setPaint(new Color(R,G,B));
		g2.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
}
class Ellipse extends Drawing
{
	void draw(Graphics2D g2)
	{
		g2.setPaint(new Color(R,G,B));
		g2.setStroke(new BasicStroke(stroke,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g2.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
}

