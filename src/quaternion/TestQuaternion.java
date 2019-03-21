package quaternion;

public class TestQuaternion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		double w = Math.PI/3;
//		Vektor v = new Vektor(1, 1, 1).norm();
		double w = Math.PI/2;
		Vektor v = new Vektor(0, 0, 1).norm();
		Quaternion q1, q2, q3, q4, q5, q6;

		q6 = new Quaternion(Math.cos(w), v.mulSkalar(Math.sin(w)));
		q1 = q6.normiere();
		q2 = q1.inverse();
		q3 = new Quaternion(0, new Vektor(1, 1, 0));

		q4 = q1.produkt(q3);
		q5 = q4.produkt(q2);
		
		System.out.println(q6);
		System.out.println("q1:"+ q1);
		System.out.println("q2:"+ q2);
		System.out.println("q3:"+ q3);
		System.out.println("q4:"+ q4);
		System.out.println("q5:"+ q5);
		Matrix m= q1.asMatrix();
		Vertex vt= new Vertex(1, 1, 0, 1);
		vt= m.multiply(vt);
		System.out.println(m);
		System.out.println(vt);
	}

}