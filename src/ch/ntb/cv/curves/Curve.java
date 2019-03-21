package ch.ntb.cv.curves;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import ch.ntb.geometry.Drawable;

public abstract class Curve implements Drawable{
	protected List<Point2D> controlPoints;	
	protected ArrayList<Point2D> curvePoints;
	
	public Curve(List<Point2D> points){
		this.controlPoints = new ArrayList<Point2D>(points);
		this.curvePoints = new ArrayList<Point2D>();	
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;		
		drawPoints(g2d);
		drawCurve(g2d);
	}

	/**
	 * Draw crosses for the control points of the current curve
	 * @param g2d
	 */
	private void drawPoints(Graphics2D g2d) {
		g2d.setColor(Color.blue);		
		g2d.setStroke(new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,1, new float[]{ 4 }, 0 ));
		drawPoint(g2d,controlPoints.get(0));
		for(int i = 0; i < controlPoints.size()-1; i++){			
			drawPoint(g2d,controlPoints.get(i+1));
		}
		g2d.setStroke( new BasicStroke(2));
	}

	private void drawPoint(Graphics2D g2d, Point2D p) {
		int s = 4;
		g2d.drawLine((int)p.getX() -s, (int)p.getY() -s, (int)p.getX() +s, (int)p.getY() +s);		
		g2d.drawLine((int)p.getX() -s, (int)p.getY() +s, (int)p.getX() +s, (int)p.getY() -s);		
	}
	
	protected abstract void drawCurve(Graphics2D g2d);

}
