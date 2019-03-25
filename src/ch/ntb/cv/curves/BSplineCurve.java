package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;

import ch.ntb.math.DoubleComparison;

public class BSplineCurve extends Curve {
	
	private double deltaT = 1. / 16.;
	private int[] tVector;

	public BSplineCurve(List<Point2D> points) {
		super(points);
		System.out.println("B-Spline:");
		// Rekursive
//		calculateCurveRec();
		
		// Iterativ
		calculateCurveIt();
	}
	
	public void calculateCurveIt() {
		
		// Initialize
		int numberOfPoints = controlPoints.size();
		int numberOfCurveSections = 3;
		int[] knotVector = getKnotVector(numberOfPoints, numberOfCurveSections);
		
		// Calculate B-spline
		for(double t = knotVector[0]; t < knotVector[knotVector.length-1]; t += deltaT) {
			
			double[] scalarValue = new double[numberOfPoints + numberOfCurveSections - 1];
			int numberOfiIteration = numberOfPoints + numberOfCurveSections - 1;
			
			// k\i table
			for(int k = 1; k <= numberOfCurveSections; k++) {
				for(int i = 0; i < numberOfiIteration; i++) {
					
					// first row is handled differently
					if(k == 1) {
						if(knotVector[i] <= t && t < knotVector[i + 1]) {
							scalarValue[i] = 1;
						}
						else {
							scalarValue[i] = 0;
						}
					}

					// Calculate polynom weightfunction
					else {
						double denominatorFirstTerm = (knotVector[i + k - 1] - knotVector[i]);
						double denominatorSecondTerm = (knotVector[i + k] - knotVector[i + 1]);
						double firstTermValue;
						double secondTermValue;
						
						// Handle division by zero.
						if(denominatorFirstTerm == 0) {
							firstTermValue = 0; 
						} else {
							firstTermValue = ((t - knotVector[i])/denominatorFirstTerm) * scalarValue[i];
						}
						if(denominatorSecondTerm == 0) {
							secondTermValue = 0;
						} else {
							secondTermValue = ((knotVector[i + k] - t)/denominatorSecondTerm) * scalarValue[i + 1];
						}						
						scalarValue[i] = firstTermValue + secondTermValue; 
					}
				}
				numberOfiIteration--;
			}
			// Create curve vector
			double x = 0;
			double y = 0;
			
			for(int i = 0; i < numberOfPoints; i++) {
				Point2D currentPoint = controlPoints.get(i);
				x += currentPoint.getX() * scalarValue[i];
				y += currentPoint.getY() * scalarValue[i];
			}
			System.out.println("["+ t +"]: (" + x + " / " + y + ")" );
			this.curvePoints.add(new Point2D.Double(x, y));
		}
		curvePoints.add(controlPoints.get(controlPoints.size()-1));
	}

	private int[] getKnotVector(int numberOfPoints, int numberOfCurveSections) {
		
		int n = numberOfPoints - 1;
		int size = numberOfPoints + numberOfCurveSections;
		int[] knotVector = new int[size];
		
		for(int i = 0; i < size; i++) {
			if(i < numberOfCurveSections) {
				knotVector[i] = 0;
			} 
			else if(numberOfCurveSections <= i && i <= n) {
				knotVector[i] = i - numberOfCurveSections + 1;
			}
			else if(i > n) {
				knotVector[i] = n - numberOfCurveSections + 2;
			}
		}
		return knotVector;
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
		for(double t = tVector[0]; t < tVector[tVector.length-1]; t += deltaT) {	
			Point2D p = new Point2D.Double();
			double x = 0;
			double y = 0;
			for(int i = 0; i <= n; i++) {
				double nik = calculateN(i, k, t);
				Point2D pi = controlPoints.get(i);
				x += pi.getX() * nik;
				y += pi.getY() * nik;
				System.out.println("curvepoint value ["+ t +"]: (" + x + " / " + y + ")" );
			}
			System.out.println("New curvepoint ["+ t +"]: (" + x + " / " + y + ")" );
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