package quaternion;


public class Vertex {

	public final static double EPSILON = 0.0000000001;

	public double x;
	public double y;
	public double z;
	public double w;

	/**
	 * Erzeugt ein neues Vertex
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Vertex(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Copy-Konstruktor
	 * 
	 * @param copy
	 *            Orginal, das kopiert werden soll
	 */

	public Vertex(Vertex copy) {
		this.x = copy.x;
		this.y = copy.y;
		this.z = copy.z;
		this.w = copy.w;
	}

	/**
	 * Berechnet diesen Vertex - den übergebenen Vertex
	 * 
	 * @param b
	 *            Subtrahend
	 * @return Ergebnis
	 */
	public Vertex minus(Vertex b) {
		Vertex copy = new Vertex(this);
		Vertex bCopy = new Vertex(b);
		if(Math.abs(copy.w) > EPSILON)
			copy.homogenize();
		if(Math.abs(bCopy.w) > EPSILON)
			bCopy.homogenize();

		copy.x = copy.x - bCopy.x;
		copy.y = copy.y - bCopy.y;
		copy.z = copy.z - bCopy.z;

		return copy;
	}
	
	/**
	 * Berechnet diesen Vertex - den übergebenen Vertex
	 * 
	 * @param b
	 *            Subtrahend
	 * @return Ergebnis
	 */
	public Vertex multiplyComponentWise(Vertex b) {
		Vertex copy = new Vertex(this);
		Vertex bCopy = new Vertex(b);
		if(Math.abs(copy.w) > EPSILON)
			copy.homogenize();
		if(Math.abs(bCopy.w) > EPSILON)
			bCopy.homogenize();

		copy.x = copy.x * bCopy.x;
		copy.y = copy.y * bCopy.y;
		copy.z = copy.z * bCopy.z;

		return copy;
	}

	/**
	 * Teilt alle Komponenten durch die homogene Komponente
	 */
	public void homogenize() {
		if (Math.abs(w) < EPSILON) {
			throw new RuntimeException("Failed to homogenize: w is zero");
		}

		this.x = x / w;
		this.y = y / w;
		this.z = z / w;
		this.w = 1;

	}

	/**
	 * Normiert das Vertex (homogenisiert zunächst)
	 */
	public void normalize() {
		if (norm() < EPSILON) {
			throw new RuntimeException("Failed to normalize: length is zero");
		}

		// norm() homogenisiert den Vertex bereits

		double norm = norm();

		this.x = this.x / norm;
		this.y = this.y / norm;
		this.z = this.z / norm;

	}

	/**
	 * Berechnet die 2-Norm des Vertex aufgefasst als 3D-Punkt (homogenisiert
	 * zunächst)
	 * 
	 * @return die Norm
	 */
	public double norm() {
		if(Math.abs(w) > EPSILON)
			homogenize();
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	/**
	 * Berechnet das Skalarprodukt dieses und des übergebenen Vertex (seiteneffektfrei)
	 * 
	 * @param b übergebene Vertex
	 * @return Ergebnis
	 */
	public double scalarProduct(Vertex b) {

		Vertex copy = new Vertex(this);
		Vertex bCopy = new Vertex(b);
		if (Math.abs(copy.w) > EPSILON) {
			copy.homogenize();
		}
		if(Math.abs(bCopy.w) > EPSILON)
		{
			bCopy.homogenize();
		}
		
		return copy.x * bCopy.x + copy.y * bCopy.y + copy.z * bCopy.z;

	}

	/**
	 * Berechnet das Kreuzprodukt dieses mit dem übergebenen Vertex (seiteneffektfrei)
	 * @param b übergebene Vertex
	 * @return Ergebnisvertex
	 */
	public Vertex crossProduct(Vertex b) {
		Vertex copy = new Vertex(this);
		Vertex bCopy = new Vertex(b);
		if(Math.abs(copy.w) > EPSILON)
			copy.homogenize();
		if(Math.abs(bCopy.w) > EPSILON)
			bCopy.homogenize();

		Vertex dest = new Vertex(0, 0, 0, 1);

		dest.x = copy.y * bCopy.z - copy.z * bCopy.y;
		dest.y = copy.z * bCopy.x - copy.x * bCopy.z;
		dest.z = copy.x * bCopy.y - copy.y * bCopy.x;

		return dest;

	}

	/**
	 * Multipliziert diesen Vertex mit einem Skalar (seiteneffektfrei)
	 * @param f Skalar
	 * @return Ergebnis
	 */
	public Vertex multiply(double f) {
		Vertex copy = new Vertex(this);
		if(Math.abs(copy.w) > EPSILON)
			copy.homogenize();
		copy.x = copy.x * f;
		copy.y = copy.y * f;
		copy.z = copy.z * f;
		return copy;
	}

	/**
	 * Gibt den Vertex als String zurück
	 */
	public String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}

}
