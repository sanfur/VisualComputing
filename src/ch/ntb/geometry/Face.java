package ch.ntb.geometry;

import java.util.LinkedList;
import java.util.List;

public class Face {

	private List<Point3D> vertices;

	/**
	 * Create a new Face defined by its boundary vertices
	 * @param vertices
	 */
	public Face(Point3D... vertices) {
		this.vertices = new LinkedList<Point3D>();
		for (Point3D v : vertices) {
			this.vertices.add(v);
		}
	}

	public List<Point3D> getVertices() {
		return vertices;
	}

}
