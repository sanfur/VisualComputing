package ch.ntb.cv.surfaces;

import java.util.List;

import ch.ntb.geometry.Point3D;

public class NurbsSurfaceSettings extends SurfaceSettings{

	private List<List<Double>> weights;
	private double[] knotsU, knotsV;
	
	public NurbsSurfaceSettings(List<List<Point3D>> controlPoints) {
		super(controlPoints);
	}

	public List<List<Double>> getWeights() {
		return weights;
	}

	public void setWeights(List<List<Double>> weights) {
		this.weights = weights;
	}

	public double[] getKnotsU() {
		return knotsU;
	}

	public void setKnotsU(double[] knotsU) {
		this.knotsU = knotsU;
	}

	public double[] getKnotsV() {
		return knotsV;
	}

	public void setKnotsV(double[] knotsV) {
		this.knotsV = knotsV;
	}

	
}
