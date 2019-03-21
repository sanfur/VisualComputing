package ch.ntb.cv.surfaces;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ch.ntb.cv.curves.BSplineCurve;
import ch.ntb.geometry.Face;
import ch.ntb.geometry.Point3D;
import ch.ntb.math.DoubleComparison;

/**
 * Represents a NURBS curve
 * When implemented in a real application, NURBS should be the superclass of BSpline and Bézier as it is the most generalized curve form
 *
 */
public class NurbsSurface extends Surface {
	
	protected double deltaTU = 1. / 16.;
	protected double deltaTV = 1. / 16.;
	
	/**
	 * Surface that describes the weighting
	 */
	private List<List<Point3D>> bsplinePoints;
	
	private List<List<Double>> weights;
	private double[] knotsU, knotsV;

	public NurbsSurface(List<List<Point3D>> points, NurbsSurfaceSettings settings) {
		super(points);
		bsplinePoints = new ArrayList<List<Point3D>>();
		this.weights = settings.getWeights();
		this.knotsU = settings.getKnotsU();
		this.knotsV = settings.getKnotsV();
		calculateSurface();
	}
	

	public Surface getWeightSurface() {
		return new StandardSurface(bsplinePoints);
	}


	/**
	 * De Casteljau Algorithm to calculate surface points recursively 
	 *     S(u, v) = sum(i = 0, n)sum(j = 0, m) P_i,j * R_i,k, j,l(u, v) 
	 *
	 *		                                  w_i,j * N_i,k(u) * N_j,l(v)
     *      R_i,k,j,l(u, v) = ---------------------------------------------------------
	 *	                     sum(r = 0, n){sum(s = 0, m){w_r,s * N_r,k(u) * N_s,l(u)}}
	 *
	 * @return
	 */
	public void calculateSurface() {
// ToDo				
	}

	@Override
	protected List<Face> buildFaces() {
		StandardSurface s = new StandardSurface(curvePoints);
		return s.getFaces();
	}

	@Override
	public Color getForegroundColor() {		
		return Color.black;
	}
	
	
	
	
}
