package ch.ntb.cv.surfaces;


import java.util.ArrayList;
import java.util.List;

import ch.ntb.geometry.Face;
import ch.ntb.geometry.Point3D;
import ch.ntb.geometry.Shape3D;

public abstract class Surface implements Shape3D{
	protected List<Face> faces;
	protected List<List<Point3D>> controlPoints;	
	protected List<List<Point3D>> curvePoints;
	
	public Surface(List<List<Point3D>> points){
		this.controlPoints = new ArrayList<List<Point3D>>(points);
		this.curvePoints = new ArrayList<List<Point3D>>();	
	}
	
	@Override
	public List<Face> getFaces() {
		if(faces == null){
			faces = buildFaces();
		}
		return faces;		
	}
	
	protected abstract List<Face> buildFaces();

	public static boolean checkPointGrid(List<List<Point3D>> points){
		if(points == null){
			return false;
		}
		// A grid has at least dimension 2x2
		if(points.size() < 2){
			return false;
		}
		
		int sizeX = points.get(0).size();
		for (List<Point3D> row : points) {
			if(row.size() != sizeX){
				return false;
			}
		}		
		return true;
		
	}

}
