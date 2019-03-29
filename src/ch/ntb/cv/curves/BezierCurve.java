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
		
		double px[] = new double[controlPoints.size()];
		double py[] = new double[controlPoints.size()];

		for (double t = 0; t <= 1; t += deltaT)
		{
			// copy controlpoints
			for (int i = 0; i < controlPoints.size(); i++)
			{
				px[i] = controlPoints.get(i).getX();
				py[i] = controlPoints.get(i).getY();
			}
			
			// Calculate points according to casteljau
			for(int i = 0; i < controlPoints.size() - 1; i++) {
				for(int j = 0; j < controlPoints.size() - 1 - i; j++) {
					px[j] = (1 - t) * px[j] + t * px[j + 1];
					py[j] = (1 - t) * py[j] + t * py[j + 1];
				}
			}
			
			System.out.println("["+ t +"] | (" + px[0] + " / " + py[0] + ")" );
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
}