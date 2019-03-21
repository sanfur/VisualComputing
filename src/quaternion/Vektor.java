package quaternion;

public class Vektor {
	private double x, y, z;
	
	public Vektor (double a, double b, double c){
		setX(a); setY(b); setZ(c);		
	}
	
	public Vektor negVektor() {
		return new Vektor(-getX(), -getY(), -getZ());
	}
	public double skalarProdukt(Vektor v) {
		return getX()* v.getX() + getY()*v.getY() + getZ() * v.getZ();
	}
	public Vektor mulSkalar(double s) {
		return new Vektor(getX()*s, getY()*s, getZ()*s);		
	}
	public Vektor vektorProdukt(Vektor v) {
		return new Vektor(getY()*v.getZ()- getZ()* v.getY(), getZ()*v.getX()-getX()*v.getZ(), getX()*v.getY() - getY()*v.getX());
	}
	public Vektor add(Vektor v) {
		return new Vektor(getX()+ v.getX(), getY()+v.getY(), getZ()+v.getZ());

	}
	public String toString() {
		return "("+getX() + ", " + getY() + ", "+getZ()+ ")";
		
	}
	Vektor norm() {
		double l= Math.sqrt(skalarProdukt(this));
		return new Vektor(x/l, y/l, z/l);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}