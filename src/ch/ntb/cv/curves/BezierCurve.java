package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve extends Curve {
	protected final double deltaT = 1. / 16.;

	public BezierCurve(List<Point2D> points) {
		super(points);
		calculateCurve();
	}


	public void calculateCurve() {

		int length = 16;
		double[] px = new double[16];
		double[] py = new double[16];
		
		for(int i = 0; i < length; i++) {
			double d = i/10;
				Point2D point = bezierFunction(d); 
				px[i] = point.getX();
				py[i] = point.getY();				
		}
			
			
			
			
			
		// Punkt zum  Zeichnungspolygon hinzu: 
		this.curvePoints.add(new Point2D.Float((int) px[0], (int) py[0]));
	}
	
	private Point2D bezierFunction(double t) {

		List<Point2D> helperPoints = new ArrayList<Point2D>();
		double x, y;
		
//		for(int n = controlPoints.size(); n >= 0; n--) {
//			x = (1 - t) * controlPoints.get(n - 1).getX() + t*controlPoints.get(n).getX();
//			y = (1 - t) * controlPoints.get(n - 1).getY() + t*controlPoints.get(n).getY();
//			
//			Point2D helperPoint = new Point2D();
//			helperPoint.setLocation(x, y);
//			helperPoints.add(helperPoint);
//		}
//		
//		for(int n = helperPoints.size(); n >= 0; n--) {
//			x = (1 - t) * helperPoints.get(n - 1).getX() + t*helperPoints.get(n).getX();
//			y = (1 - t) * helperPoints.get(n - 1).getY() + t*helperPoints.get(n).getY();			
//		}
		
		for(int i = 0; i < controlPoints.size(); i++) {
			for(int k = 0; k < controlPoints.size(); k++) {
				
				x = (1 - t) * controlPoints.get(k - 1).getX() + t*controlPoints.get(k).getX();
				y = (1 - t) * controlPoints.get(k - 1).getY() + t*controlPoints.get(k).getY();
				
				Point2D helperPoint = new Point2D();
				helperPoint.setLocation(x, y);
				helperPoints.add(helperPoint);
			}
		}
		
		
		
		
		
		
		return null;
	}
	

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			Point2D p1 = curvePoints.get(i), p2 = curvePoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}

	}
}
