package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import ch.ntb.math.DoubleComparison;

/**
 * Represents a NURBS curve
 * When implemented in a real application, NURBS should be the superclass of BSpline and Bézier as it is the most generalized curve form
 *
 */
public class NurbsCurve extends Curve {
	
	protected double deltaT = 1. / 16.;
	
	private double[] weights;
	private double[] knots;

	public NurbsCurve(List<Point2D> points, NurbsCurveSettings settings) {
		super(points);
		this.weights = settings.getWeights();
		this.knots = settings.getKnots();
		calculateCurve();
	}

	/**
	 * Check if weight vector has same size as control point vector and every weight > 0
	 * @param weights
	 * @param n
	 */
	public static boolean checkWeights(double[] weights, int n) {		
		if(weights == null){
			return false;
		}
		
		// There must be equivalent weights as control points
		if(weights.length != n){
			return false;
		}
		
		for (int i = 0; i < weights.length; i++) {
			// Weights must be > 0
			if(weights[i] <= 0){
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if knot vector has at least same size as control point vector and strictly increasing values
	 * @param knots
	 * @param n
	 */
	public static boolean checkKnots(double[] knots, int n) {
		if(knots == null){
			return false;
		}
		
		// Degree is at least 0
		if(knots.length < n){
			return false;
		}
		
		double preValue = knots[0];
		for (int i = 1; i < knots.length; i++) {
			// Knots must be strictly increasing
			if(knots[i] < preValue){
				return false;
			}
			preValue = knots[i];
		}
		return true;		
	}

	public void calculateCurve() {

		// Initialize
		int numberOfPoints = controlPoints.size();
		int numberOfCurveSections = 3;
		double[] knotVector = knots;
		
		// Calculate NURBS
		for(double t = knotVector[0]; t < knotVector[knotVector.length-1]; t += deltaT) {
			
			double[] scalarValue = new double[numberOfPoints + numberOfCurveSections - 1];
			// hi * Nik(t)
			double nurbsDenominator = 0;
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

					// Calculate polynom weight function
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
						
						// As soon as B-spline calculation finishes, weights are getting involved
						if(k != numberOfCurveSections) continue;
						// The denominator of the R-function stays the same, at every run, therefore its calculated here.
						nurbsDenominator += weights[i] * scalarValue[i];
					}
				}
				numberOfiIteration--;
			}
			// Create curve vector
			double x = 0;
			double y = 0;
			
			for(int i = 0; i < numberOfPoints; i++) {
				Point2D currentPoint = controlPoints.get(i);
				x += currentPoint.getX() * (weights[i] * scalarValue[i])/nurbsDenominator;
				y += currentPoint.getY() * (weights[i] * scalarValue[i])/nurbsDenominator;
			}
			
			System.out.println("["+ t +"] | (" + x + " / " + y + ")" );
			this.curvePoints.add(new Point2D.Double(x, y));
		}
		curvePoints.add(controlPoints.get(controlPoints.size()-1));
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
