package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CurveCanvas extends JPanel{
	
	protected Vector<Point2D> currentPointList = new Vector<Point2D>();
	protected Vector<Curve> curveList = new Vector<Curve>();
	
	public CurveCanvas() {
		super();
		setBackground(Color.white);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				currentPointList.add(new Point2D.Float(e.getX(), e.getY()));
				repaint();		
			}
		});
	}	
	
	public Vector<Point2D> getCurrentPointList() {
		return currentPointList;
	}


	public void drawCurve(Curve curve){
		curveList.add(curve);
		currentPointList.clear();
		repaint();		
	}
	
	public void clearAll(){
		currentPointList.clear();
		curveList.clear();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for(Point2D p : currentPointList){
			g2d.setBackground(Color.black);	
			g2d.drawOval((int) p.getX(),(int) p.getY(), 4, 4);
		}
		for(Curve c : curveList){
			c.draw(g);
		}		
	}
}
