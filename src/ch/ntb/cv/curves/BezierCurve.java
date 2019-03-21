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
		
	    int npts = (b.Length) / 2;
	    int icount, jcount;
	    double step, t;

	    // Calculate points on curve

	    icount = 0;
	    t = 0;
	    step = (double)1.0 / (cpts - 1);

	    for (int i1 = 0; i1 != cpts; i1++)
	    { 
	        if ((1.0 - t) < 5e-6) 
	            t = 1.0;

	        jcount = 0;
	        p[icount] = 0.0;
	        p[icount + 1] = 0.0;
	        for (int i = 0; i != npts; i++)
	        {
	            double basis = Bernstein(npts - 1, i, t);
	            p[icount] += basis * b[jcount];
	            p[icount + 1] += basis * b[jcount + 1];
	            jcount = jcount +2;
	        }

	        icount += 2;
	        t += step;
	    }
		
		
		
//		this.curvePoints.add(new Point2D.Float((int) x, (int) y));
		
//		bezierTryOne(points);
//		for(int i = 0; i < bPoints.length; i++) {
//			
//			this.curvePoints.add(new Point2D.Float((int) bPoints[i].getX(), (int) bPoints[i].getY()));
//		}
		
		// Punkt zum  Zeichnungspolygon hinzu: 
//		this.curvePoints.add(new Point2D.Float((int) px[0], (int) py[0]));
	}

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			Point2D p1 = curvePoints.get(i), p2 = curvePoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
	}
		
	public int fact(int n) {
	    int fact = 1;
	    for (int i = 1; i <= n; i++) {
	        fact *= i;
	    }
	    return fact;
	}
	
	public double  bernstein(double t, int n, int i){
	   return (fact(n) / (fact(i) * fact(n-i))) * Math.pow(1-t, n-i) * Math.pow(t, i);
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
