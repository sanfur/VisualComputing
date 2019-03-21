package ch.ntb.cv.camera;

import java.util.ArrayList;
import java.util.List;

import ch.ntb.geometry.Shape3D;

/**
 * Manages the components of the 3D world
 *
 */
public class Scene {
	
	List<Shape3D> objects;
	
	public Scene() {
		objects = new ArrayList<Shape3D>();
	}

	public List<Shape3D> getObjects() {
		return objects;
	}

	public void addObject(Shape3D shape) {
		objects.add(shape);
		
	}

	public void clear() {
		objects.clear();
	}
	
	

}
