package ch.ntb.cv.camera;

import java.awt.Dimension;

import ch.ntb.geometry.Matrix;
import ch.ntb.geometry.Point3D;

public class Camera {

	/**
	 * Camera parameters
	 */
	private Point3D eyePoint, lookAtPoint, upVector;

	/**
	 * Distance to near and far plane to limit viewable volume
	 */
	private double dmin, dmax; 

	/**
	 * Field of view (degree) and aspect ratio
	 */
	private double fov, aspect; 

	/**
	 * Size of the output display
	 */
	private Dimension displaySize;

	// Default camera settings
	public static final Point3D defaultEye = new Point3D(4, 10, 10);
	public static final Point3D defaultUp = new Point3D(0, 1, 0);
	public static final Point3D defaultLook = new Point3D(0, 0, 0);
	public static final double defaultFov = 22.5;
	public static final Dimension defaultDisplaySize = new Dimension(640, 480);
	public static final double defaultNearplane = 0.1;
	public static final double defaultFarplane = 100;	

	/**
	 * Creates a new camera
	 * @param eyePoint position of the camera
	 * @param lookAtPoint position of the object to look at
	 * @param upVector
	 * @param fov field of view
	 * @param xsize width of the display
	 * @param ysize height of the display
	 * @param near distance to near plane
	 * @param far distance to far plane
	 */
	public Camera(Point3D eyePoint, Point3D lookAtPoint, Point3D upVector,
			          double fov, Dimension displaySize, double near, double far) {
		this.eyePoint = eyePoint;
		this.lookAtPoint = lookAtPoint;
		this.upVector = upVector;
		this.fov = fov;
		this.displaySize = displaySize;
		this.dmin = near;
		this.dmax = far;

		this.aspect = displaySize.height / (double) displaySize.width;
	}
	
	public Camera getCopy() {
		return new Camera(getEyePoint(), getLookAtPoint(), getUpVector(), fov, getDisplaySize(), dmin, dmax);
	}
	
	public void assign(Camera other){
		setEyePoint(other.eyePoint);
		setLookAtPoint(other.lookAtPoint);
		setUpVector(other.upVector);
		fov = other.fov;
		setDisplaySize(other.displaySize);
		dmin = other.dmin;
		dmax = other.dmax;
	}

	
	public Point3D getEyePoint() {
		return new Point3D(eyePoint);
	}


	public void setEyePoint(Point3D eyePoint) {
		this.eyePoint.assign(eyePoint);
	}


	public Point3D getLookAtPoint() {
		return new Point3D(lookAtPoint);
	}


	public void setLookAtPoint(Point3D lookAtPoint) {
		this.lookAtPoint.assign(lookAtPoint);
	}


	public Point3D getUpVector() {
		return new Point3D(upVector);
	}


	public void setUpVector(Point3D upVector) {
		this.upVector.assign(upVector);
	}
	
	

	public Dimension getDisplaySize() {
		return new Dimension(displaySize);
	}

	public void setDisplaySize(Dimension displaySize) {
		this.displaySize.height = displaySize.height;
		this.displaySize.width = displaySize.width;
	}

	/**
	 * Calculates matrix to transform world coordinates to view reference
	 * @return die Matrix
	 */
	public Matrix toViewReference() {
		// ToDo
		return null; //ToDo
	}

	/**
	 * Calculates the matrix to transform from view reference to normalized projection
	 * @return
	 */
	public Matrix viewReferenceToNormalizedProjection() {
		
//ToDo
		return null; //ToDo
	}

	/**
	 * Calculate Identity cube for the visible volume
	 * @return
	 */
	private Matrix createIdentityCube() {
		// d is distance from eye to view plane in z dimension
		double d = eyePoint.minus(lookAtPoint).norm();

		// Calculate limits
		double xmax = Math.abs(Math.tan((Math.PI * 2 * (fov / 360)) / 2) * d);
		double xmin = -1 * xmax;
		
		double ymax = aspect * xmax;
		double ymin = -1 * ymax;
			
		Matrix toCube = Matrix.createIdentity(4);
		toCube.setValue(0, 0, d / (xmax - xmin));
		toCube.setValue(1, 1, d / (ymax - ymin));
		toCube.setValue(0, 2, 0.5 * (1 - (xmin + xmax) / (xmax - xmin)));
		toCube.setValue(1, 2, 0.5 * (1 - (ymin + ymax) / (ymax - ymin)));
		toCube.setValue(2, 2, dmax / (dmax - dmin));
		toCube.setValue(3, 2, 1);
		toCube.setValue(2, 3, (-dmax * dmin) / (dmax - dmin));
		toCube.setValue(3, 3, 0);
		return toCube;
	}

	/**
	 * Calculate matrix to transform from normalized projection to device coordinate system
	 * @return
	 */
	public Matrix normalizedProjectionToDevice() {
		Matrix npc2dc = Matrix.createIdentity(4);
		npc2dc.setValue(0, 0, displaySize.width);
		npc2dc.setValue(1, 1, -displaySize.height);
		npc2dc.setValue(1, 3, displaySize.height);
		return npc2dc;
	}
	
	/**
	 * Rotate eye around up vector right (positive yaw) or left (negative yaw)
	 * and along up vector up (positive pitch) or down (negative pitch)
	 * @param yawAngle
	 * @param pitchAngle
	 */
	public void thirdPersonMove(double yawAngle, double pitchAngle){
		
		// Vector from look to eye
		Point3D direction = eyePoint.minus(lookAtPoint);
		
		// Yaw
		direction.assign(direction.rotatedAroundAxis(yawAngle, upVector));
		
		// Pitch
		Point3D rotationAxis = direction.crossProduct(upVector);	
		Point3D newdirection = direction.rotatedAroundAxis(pitchAngle, rotationAxis);
		eyePoint.assign(lookAtPoint.plus(newdirection));
		
		upVector.assign(upVector.rotatedAroundAxis(pitchAngle, rotationAxis));				
	}
	
	/**
	 * Rotate eye around up vector right (positive yaw) or left (negative yaw)
	 * and along up vector up (positive pitch) or down (negative pitch)
	 * @param yawAngle
	 * @param pitchAngle
	 */
	public void firstPersonMove(double yawAngle, double pitchAngle){
		
		// Vector from look to eye
		Point3D direction = lookAtPoint.minus(eyePoint);
		
		// Yaw
		direction.assign(direction.rotatedAroundAxis(-yawAngle, upVector));
		
		// Pitch
		Point3D rotationAxis = direction.crossProduct(upVector);						
		lookAtPoint.assign(eyePoint.plus(direction.rotatedAroundAxis(pitchAngle, rotationAxis)));
		upVector.assign(upVector.rotatedAroundAxis(pitchAngle, rotationAxis));				
	}

	/**
	 * Change distance from eye point to look at point 
	 * @param zoomFactor
	 */
	public void zoom(double zoomFactor) {
		Point3D direction = eyePoint.minus(lookAtPoint);
		direction.assign(direction.multiply(zoomFactor));
		eyePoint.assign(lookAtPoint.plus(direction));		
	}

}
