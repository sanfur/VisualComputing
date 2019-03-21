package ch.ntb.cv.surfaces;

import java.util.List;

import ch.ntb.geometry.Point3D;

public class SurfaceSettings {
	
	protected List<List<Point3D>> controlPoints;
	
	public SurfaceSettings(List<List<Point3D>> controlPoints){
		this.controlPoints = controlPoints;
	}

	public List<List<Point3D>> getControlPoints() {
		return controlPoints;
	}

	public void setControlPoints(List<List<Point3D>> controlPoints) {
		this.controlPoints = controlPoints;
	}
	
	

}
