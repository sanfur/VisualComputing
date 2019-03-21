package ch.ntb.geometry;

public class Quaternion3D extends Point3D {

	public Quaternion3D(double x, double y, double z, double w) {
		super(x, y, z, w);
	}

	public Quaternion3D(Quaternion3D copy) {
		super(copy);
	}

	/**
	 * Calculate multiplication of quaternions
	 * 
	 * @param right
	 * @return this * right
	 */
	public Quaternion3D multiply(Quaternion3D right) {
		Quaternion3D copy = new Quaternion3D(this);
		copy.x = this.w * right.x + this.x * right.w + this.y * right.z
				- this.z * right.y;
		copy.y = this.w * right.y - this.x * right.z + this.y * right.w
				+ this.z * right.x;
		copy.z = this.w * right.z + this.x * right.y - this.y * right.x
				+ this.z * right.w;
		copy.w = this.w * right.w - this.x * right.x - this.y * right.y
				- this.z * right.z;
		return copy;
	}

	/**
	 * Create inverse quaternion
	 * @return
	 */
	public Quaternion3D conjugate() {
		Quaternion3D copy = new Quaternion3D(this);
		copy.x = -this.x;
		copy.y = -this.y;
		copy.z = -this.z;
		copy.w = this.w;
		return copy;
	}
}
