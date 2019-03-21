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
	//ToDo
		
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
