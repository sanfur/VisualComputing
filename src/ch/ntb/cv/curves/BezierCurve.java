package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve extends Curve {
	
	protected final double deltaT = 1. / 16.;
	  private Point2D[] bPoints;
	  private Point2D[] controlPoints;
	
	public BezierCurve(List<Point2D> points) {
		super(points);
		calculateCurve(points);
	}


	public void calculateCurve(List<Point2D> points) {

//		bezierTryOne(points);
//		for(int i = 0; i < bPoints.length; i++) {
//			
//			this.curvePoints.add(new Point2D.Float((int) bPoints[i].getX(), (int) bPoints[i].getY()));
//		}
		
		bezierTryTwo();
		
		// Punkt zum  Zeichnungspolygon hinzu: 
//			 this.curvePoints.add(new Point2D.Float((int) px[0], (int) py[0]));
	}

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			Point2D p1 = curvePoints.get(i), p2 = curvePoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
	}
	
	public void bezierTryTwo() {
		Point p1,p2,p3,p4;
	    p1=points[0].getPosition();
	    p2=points[1].getPosition();
	    p3=points[2].getPosition();
	    p4=points[3].getPosition();
	    double x,y;

	    x = p1.x*(Math.pow(-t,3)+3*Math.pow(t,2)-3*t+1)
	      +3*p2.x*t*(Math.pow(t,2)-2*t+1)
	      +3*p3.x*Math.pow(t,2)*(1-t)+p4.x*Math.pow(t,3);


	    y = p1.y*(Math.pow(-t,3)+3*Math.pow(t,2)-3*t+1)
	      +3*p2.y*t*(Math.pow(t,2)-2*t+1)
	      +3*p3.y*Math.pow(t,2)*(1-t)+p4.y*Math.pow(t,3);

	    return new Point((int)Math.round(x),(int)Math.round(y));
	}
	
	public void bezierTryOne(List<Point2D> points) {
		
		int n = points.size();
		if(n < 3) {
			return;
		}
		
		bPoints = new Point[2 * (n - 2)];
	
		// Set Point A,B,C
		double paX, paY;
		double pbX = points.get(0).getX();
		double pbY = points.get(0).getY();
		double pcX = points.get(1).getX();
		double pcY = points.get(1).getY();
		
		for(int i = 0; i < n - 2; i++) {
			paX = pbX;
			paY = pbY;
			pbX = pcX;
			pbY = pcY;
			pcX = points.get(i + 2).getX();
			pcY = points.get(i + 2).getY();
			
			// Vector AB
			double abX = pbX - paX;
			double abY = pbY - paY;
			// Vector AC
			double acX = pcX - paX;
			double acY = pcY - paY;
			// Length of AC
			double lac = Math.sqrt(acX * acX + acY * acY);
			acX = acX / lac;
			acY = acY / lac;
			
			double proj = abX * acX + abY * acY;
			proj = proj < 0 ? -proj : proj;
			
			double apX = proj * acX;
			double apY = proj * acY;
			
			 double p1X = pbX - deltaT * apX;
			 double p1Y = pbY - deltaT * apY;
			 bPoints[2 * i] = new Point((int) p1X, (int) p1Y);
			
			 acX = -acX;
			 acY = -acY;
			 double cbX = pbX - pcX;
			 double cbY = pbY - pcY;
			 proj = cbX * acX + cbY * acY;
			 proj = proj < 0 ? -proj : proj;
			 apX = proj * acX;
			 apY = proj * acY;
			
			 double p2X = pbX - deltaT * apX;
			 double p2Y = pbY - deltaT * apY;
			 bPoints[2 * i + 1] = new Point((int) p2X, (int) p2Y);
		}
	}
}
