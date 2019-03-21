/**
 * 
 */
package ch.ntb.geometry;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;



public class Cube implements Shape3D {

	List<Face> faces;
	public Cube()
	{
		faces = new LinkedList<Face>();
		
		Face side;
		side = new Face(new Point3D(-1,-1,1), new Point3D(-1,1,1), new Point3D(1,1,1), new Point3D(1,-1,1));
		faces.add(side);
		
		side = new Face(new Point3D(1,-1,1), new Point3D(1,1,1), new Point3D(1,1,-1), new Point3D(1,-1,-1)); 
		faces.add(side);

		side = new Face(new Point3D(1,-1,-1), new Point3D(1,1,-1), new Point3D(-1,1,-1), new Point3D(-1,-1,-1));
		faces.add(side);

		side = new Face(new Point3D(-1,-1,-1), new Point3D(-1,1,-1), new Point3D(-1,1,1), new Point3D(-1,-1,1));
		faces.add(side);

		side = new Face(new Point3D(-1,1,1), new Point3D(-1,1,-1), new Point3D(1,1,-1), new Point3D(1,1,1));
		faces.add(side);

		side = new Face(new Point3D(-1,-1,1), new Point3D(-1,-1,-1), new Point3D(1,-1,-1), new Point3D(1,-1,1));
		faces.add(side);	

	}
	
	@Override
	public List<Face> getFaces() {
		return faces;
	}

	@Override
	public Color getForegroundColor() {
		return Color.black;
	}
	
	

}
