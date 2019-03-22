package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;

import ch.ntb.math.DoubleComparison;

public class BSplineCurve extends Curve {
	protected double deltaT = 1. / 16.;
	private int[] tVector;

	public BSplineCurve(List<Point2D> points) {
		super(points);
		// Rekursive
		calculateCurveRec();
		
		// Iterativ
		calculateCurveIt();
	}

	public void calculateCurveIt() {
		// Initialisieren
		int[] knotVector;
		int n = controlPoints.size() - 1;
		int k = 3;
		// Plus 1, da 0 miteinbezogen wird in der Grösse des Knotenvektors.
		int size = n + k + 1;
		knotVector = new int[size];
		
		// Knotenvektor mit Werten füllen.
		for(int j = 0; j < size; j++) {
			if(j < k) {
				knotVector[j] = 0;
			} 
			else if(k <= j && j <= n) {
				knotVector[j] = j - k + 1;
			}
			else if(j > n) {
				knotVector[j] = n - k + 2;
			}
		}
		
		for(double t = 0; t <= 1; t += deltaT) {
			
			for(int i = 0; i <= n; i++) {
				
			}
		}
		
		
		
		
	}

	public void calculateCurveRec() {
		int k = 3;
		
		// Knotenvektor berechnen
		int n = controlPoints.size() - 1;
		int size = n + k+1;
		tVector = new int[size];
		for(int j = 0; j < size; j++) {
			if(j < k) {
				tVector[j] = 0;
			} else if(j <= n && j >= k) {
				tVector[j] = j - k + 1;
			} else { // j > n
				tVector[j] = n - k + 2;
			}
		}				

		// B-Spline-Punkte mithilfe der Gewichtungspolynome berechnen
//		for(double t = 0; t <= 1; t += deltaT) {
		for(double t = tVector[0]; t < tVector[tVector.length-1]; t += deltaT) {	
			Point2D p = new Point2D.Double();
			double x = 0;
			double y = 0;
			for(int i = 0; i <= n; i++) {
				double nik = calculateN(i, k, t);
				Point2D pi = controlPoints.get(i);
				x += pi.getX() * nik;
				y += pi.getY() * nik;
			}
			p.setLocation(x, y);
			this.curvePoints.add(p);
		}
		curvePoints.add(controlPoints.get(controlPoints.size()-1));
	}


	private double calculateN(int i, int k, double t) {
		if(k == 1) {
			if(t < tVector[i + 1] && t >= tVector[i]) {
				return 1;
			} else {
				return 0;
			}
		} else {
			double quotient1 = 0;
			double quotient2 = 0;
			if(tVector[i + k - 1] != tVector[i]) { // würde keine Division durch 0 ergeben, d.h. quotient normal berechnen
				quotient1 = (t - tVector[i]) / (tVector[i + k - 1] - tVector[i]);
			}
			if(tVector[i + k] != tVector[i + 1]) { // würde keine Division durch 0 ergeben, d.h. quotient normal berechnen
				quotient2 = (tVector[i + k] - t) / (tVector[i + k] - tVector[i + 1]);
			}
			double n1 = calculateN(i, k-1, t);
			double n2 = calculateN(i+1, k-1, t);
			return quotient1 * n1 + quotient2 * n2;
		}
	}

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.green);
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			Point2D p1 = curvePoints.get(i), p2 = curvePoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}		
	}

}

