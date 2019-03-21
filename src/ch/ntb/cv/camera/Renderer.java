
package ch.ntb.cv.camera;

import java.awt.Graphics;
import java.util.List;

import ch.ntb.geometry.Face;
import ch.ntb.geometry.Matrix;
import ch.ntb.geometry.Point3D;
import ch.ntb.geometry.Shape3D;


public class Renderer {

	private Camera camera;
	private List<Shape3D> shapes;

	public Renderer(Camera camera, List<Shape3D> shapes) {
		this.camera = camera;
		this.shapes = shapes;
	}

	public void render(Graphics g) {

		// Transformation matrix
		// World -> View Reference -> Normalized Projection -> Device
		Matrix viewingPipeline = camera.normalizedProjectionToDevice().multiply(camera.viewReferenceToNormalizedProjection().multiply(camera.toViewReference()));

		for (Shape3D d : shapes) {
			g.setColor(d.getForegroundColor());
			for (Face f : d.getFaces()) {
				Point3D start = viewingPipeline.multiply(f.getVertices().get(0));
				Point3D end = null;
				int anz= f.getVertices().size();

				for (int i = 1; i <= anz; i++) {
					end = viewingPipeline.multiply(f.getVertices().get(i%anz));
					start.homogenize();
					end.homogenize();
					g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
					start = end;
				}

			}
		}
	}

}
