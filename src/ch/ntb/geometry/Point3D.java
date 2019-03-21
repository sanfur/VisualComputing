package ch.ntb.geometry;

import ch.ntb.math.DoubleComparison;

public class Point3D {

	public double x;
	public double y;
	public double z;
	public double w;

	/**
	 * Default constructor
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	public Point3D(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public Point3D(Point3D copy) {
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
		this.w = copy.w;
	}
	
	/**
	 * Assigns new coordinates to a Point
	 * @param target
	 */
	public void assign(Point3D target){
		this.x = target.x;
		this.y = target.y;
		this.z = target.z;
		this.w = target.w;
	}

	/**
	 * Subtract given point from this point
	 * @param subtrahend
	 * @return this - b
	 */
	public Point3D minus(Point3D subtrahend) {
		Point3D copy = new Point3D(this);
		copy.homogenize();
		Point3D subCopy = new Point3D(subtrahend);
		subCopy.homogenize();

		copy.x -= subCopy.x;
		copy.y -= subCopy.y;
		copy.z -= subCopy.z;		
		
		return copy;
	}
	
	/**
	 * Add given point to this point
	 * @param summand
	 * @return this + b
	 */
	public Point3D plus(Point3D summand) {
		Point3D copy = new Point3D(this);
		copy.homogenize();
		Point3D subCopy = new Point3D(summand);
		subCopy.homogenize();

		copy.x += subCopy.x;
		copy.y += subCopy.y;
		copy.z += subCopy.z;		
		
		return copy;
	}

	/**
	 * Project projective point back to the viewing plane 
	 */
	public void homogenize() {
		if (DoubleComparison.eq(w, 0)){
			throw new RuntimeException("Can't homogenize points at infinity (last component zero)");
		}

		this.x = x / w;
		this.y = y / w;
		this.z = z / w;
		this.w = 1;
	}

	/**
	 * Normalize length to 1
	 */
	public void normalize() {
		double norm = norm();
		if (DoubleComparison.eq(norm, 0)) {
			throw new RuntimeException("Failed to normalize: length is zero");
		}
		this.x = this.x / norm;
		this.y = this.y / norm;
		this.z = this.z / norm;
	}

	/**
	 * Calculate the norm of the homogenized coordinates (vector length)
	 * @return
	 */
	public double norm() {
		homogenize();
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	/**
	 * Calculate scalar product of homogenized coordinates with given point
	 * @param b
	 * @return this * b
	 */
	public double scalarProduct(Point3D b) {
		Point3D copy = new Point3D(this);
		double res = copy.x * b.x + copy.y * b.y + copy.z * b.z + copy.w * b.w;
		res /= copy.w * b.w;
		return res;
	}

	/**
	 * Calculate cross product of homogenized coordinates
	 * @param b
	 * @return
	 */
	public Point3D crossProduct(Point3D b) {
		Point3D copy = new Point3D(this);
		Point3D bCopy = new Point3D(b);
		copy.homogenize();
		bCopy.homogenize();

		Point3D dest = new Point3D(0, 0, 0);
		dest.x = copy.y * bCopy.z - copy.z * bCopy.y;
		dest.y = copy.z * bCopy.x - copy.x * bCopy.z;
		dest.z = copy.x * bCopy.y - copy.y * bCopy.x;
		return dest;
	}

	/**
	 * Multiply homogeneous coordinates with a scalar
	 * @param f scale factor
	 * @return
	 */
	public Point3D multiply(double f) {
		Point3D copy = new Point3D(this);
		copy.homogenize();
		copy.x = copy.x * f;
		copy.y = copy.y * f;
		copy.z = copy.z * f;
		return copy;
	}
	
	/**
	 * Divide homogeneous coordinates with a scalar
	 * @param f scale factor
	 * @return
	 */
	public Point3D divide(double f) {
		Point3D copy = new Point3D(this);
		copy.homogenize();
		copy.x = copy.x / f;
		copy.y = copy.y / f;
		copy.z = copy.z / f;
		return copy;
	}
	
	/**
	 * Rotate vector {@code angle} radians around axis
	 * @param angle Rotation angle in radian
	 * @param rotationAxis {@link Point3D} to rotate around
	 * @return this {@link Point3D} rotated around angle 
	 */
	public Point3D rotatedAroundAxis(double angle, Point3D rotationAxis){
		// Create a quaternion from the vector to rotate
		if(DoubleComparison.neq(this.w, 1)){
			throw new RuntimeException("Rotation target must be homogenized before rotating");
		}
		
		Point3D copy = new Point3D(this);		
		double length = copy.norm();
		
		// Rotation with quaternions should be done with normalized vectors
		copy.normalize();
		
		// Target quaternion
		Quaternion3D target = new Quaternion3D(
				this.x, 
				this.y, 
				this.z,
				0);

		// Rotationquaternion
		double quatAngle = angle / 2;
		Quaternion3D rotation = new Quaternion3D(
				rotationAxis.x * Math.sin(quatAngle), 
				rotationAxis.y * Math.sin(quatAngle), 
				rotationAxis.z * Math.sin(quatAngle),
				Math.cos(quatAngle));

		// Rotated quaternion is calculated with the following formula
		// result = rotation * target * rotation' 
		// (where rotation' is the conjugate of rotation)
		Quaternion3D rotated = rotation.multiply(target).multiply(rotation.conjugate());
		
		// Turn quaternion into a Point3D
		Point3D result = (Point3D) rotated;
		result.w = 1;	
		result.normalize();
		
		// Obtain original length
		result = result.multiply(length);
		return result;
	}

	public String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}

	public static void main(String[] args) {
		
	}
}
