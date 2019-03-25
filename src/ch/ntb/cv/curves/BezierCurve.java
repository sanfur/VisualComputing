package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

public class BezierCurve extends Curve {
	
	protected final double deltaT = 1. / 16.;
	
	public BezierCurve(List<Point2D> points) {
		super(points);
		System.out.println("Bezier:");
		calculateCurve();
	}
	
	public void calculateCurve() {
		
		for (double t = 0; t <= 1; t += deltaT)
		{
			double px[] = new double[controlPoints.size() - 1];
			double py[] = new double[controlPoints.size() - 1];
						
			for (int iLevel = 0; iLevel < controlPoints.size() - 1; iLevel ++)
			{
				if (iLevel == 0)
					//Work with Points
				{
					for(int pCount = 0; pCount < controlPoints.size() - 1 ; pCount++)
					{	
						px[pCount] = (1 - t) * controlPoints.get(pCount).getX() + t * controlPoints.get(pCount+1).getX();
						py[pCount] = (1 - t) * controlPoints.get(pCount).getY() + t * controlPoints.get(pCount+1).getY();
					}

				}
				else
					//Work with newly created Points f.e. P01 (P0 and P1 used before)
				{
					for(int pCount = 0; pCount < controlPoints.size() - 1 - iLevel ; pCount++)
					{	
						px[pCount] = (1 - t) * px[pCount] + t * px[pCount + 1];
						py[pCount] = (1 - t) * py[pCount] + t * py[pCount + 1];
					}
				}
			}
			System.out.println("["+ t +"]: (" + px[0] + " / " + py[0] + ")" );
			this.curvePoints.add(new Point2D.Float((int) px[0], (int) py[0]));
		}
	}

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			Point2D p1 = curvePoints.get(i), p2 = curvePoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
	}
		
//	public int fact(int n) {
//	    int fact = 1;
//	    for (int i = 1; i <= n; i++) {
//	        fact *= i;
//	    }
//	    return fact;
//	}
//	
//	public double  bernstein(double t, int n, int i){
//	   return (fact(n) / (fact(i) * fact(n-i))) * Math.pow(1-t, n-i) * Math.pow(t, i);
//	}
}
