package quaternion;


public class Quaternion {
	private double s;
	private Vektor v;
	public Quaternion(double s, Vektor v) {
		this.s=s;
		this.v=v;
	}
	
	public Quaternion add(Quaternion q){
		return new Quaternion(s+ q.s, v.add(q.v));
	}
	public Quaternion mulSkalar(double c){
		return new Quaternion(c* s, v.mulSkalar(c));
	}
	public double skalarProdukt(Quaternion q){
		return s* q.s+  v.skalarProdukt(q.v);
	}
	public Quaternion produkt(Quaternion q){
		return new Quaternion(s*q.s - v.skalarProdukt(q.v), q.v.mulSkalar(s).add(v.mulSkalar(q.s).add(v.vektorProdukt(q.v))));
	}
	public Quaternion konjugiere(){
		return new Quaternion(s, v.negVektor());
	}
	public double norm(){
		return Math.sqrt(skalarProdukt(this));
	}
	public Quaternion normiere() {
		return this.mulSkalar(1/this.norm());
	}
	public Quaternion inverse(){
		double l= this.norm();
		return this.konjugiere().mulSkalar(l*l);
	}	
	public String toString() {
		return "["+s+ ", " + v +"]";
	}
	private double sq(double x) {return x*x;}

	public Matrix asMatrix() {
		double[][] res= new  double[4][4];
		res[0][0]= 1-2*(sq(v.getY())+sq(v.getZ())); 
		res[0][1]= 2*(v.getX()*v.getY()- s*v.getZ()); 
		res[0][2]= 2* (v.getX()*v.getZ()+ s*v.getY()); 
		res[1][0]= 2*(v.getX()*v.getY()+ s*v.getZ()); 
		res[1][1]= 1- 2*(sq(v.getX())+sq(v.getZ())); 
		res[1][2]= 2*(v.getY()*v.getZ()- s* v.getX()); 
		res[2][0]= 2*(v.getX()*v.getZ() - s*v.getY()); 
		res[2][1]= 2*( v.getY()*v.getZ()+ s*v.getX()); 
		res[2][2]= 1- 2*(sq(v.getX()) +sq(v.getY())); 
		res[3][3]= 1; 
		
		return new Matrix(res);
	}
	
	
}