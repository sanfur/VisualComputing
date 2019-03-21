package ch.ntb.cv.curves;

import java.awt.geom.Point2D;
import java.util.List;

public class NurbsCurveSettings extends CurveSettings{
	
	public NurbsCurveSettings(List<Point2D> pointList) {
		super(pointList);
	}

	double[] knots;
	double[] weights;

	public double[] getKnots() {
		return knots;
	}

	public void setKnots(double[] knots) {
		this.knots = knots;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double[] getWeights() {
		return weights;
	}

}
