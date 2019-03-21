package ch.ntb.cv.surfaces;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import ch.ntb.geometry.Face;
import ch.ntb.geometry.Point3D;

public class StandardSurface extends Surface {

	public StandardSurface(List<List<Point3D>> points) {
		super(points);
	}

	@Override
	public List<Face> buildFaces() {
		LinkedList<Face> faces = new LinkedList<Face>();
		// Iterate over the grid starting at (1,1) to (end,end) and build a face of the control points
		List<Point3D> pre, current;
		pre = controlPoints.get(0);
		for (int row = 1; row < controlPoints.size(); row++) {
			current = controlPoints.get(row);
			for (int col = 1; col < controlPoints.get(0).size(); col++) {						
				faces.add(new Face(pre.get(col-1), pre.get(col), current.get(col), current.get(col-1)));				
			}	
			pre = current;
		}
		return faces;
	}

	@Override
	public Color getForegroundColor() {
		return Color.blue;
	}
	
	
}

