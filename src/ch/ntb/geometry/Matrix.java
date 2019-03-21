package ch.ntb.geometry;

import ch.ntb.math.DoubleComparison;

public class Matrix {

	private double[][] elements;

	public Matrix(double[][] elements) {
		this.elements = elements;
	}

	/**
	 * Create a squared identity matrix
	 * @param size
	 * @return
	 */
	public static Matrix createIdentity(int size) {
		double[][] identity = new double[size][size];

		for (int i = 0; i < size; i++){
			identity[i][i] = 1;
		}
		return new Matrix(identity);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{");
		for (int row = 0; row < elements.length; row++) {
			s.append("{");
			int col;
			for (col = 0; col < elements[row].length-1; col++) {
				s.append(elements[row][col] + ", ");				
			}
			s.append(elements[row][col]);
			s.append("}");
			if(row + 1 != elements.length){
				s.append(", \n");
			}
		}
		s.append("}");
		return s.toString();
	}

	public int getRows() {
		return elements.length;
	}

	public int getCols() {
		return elements[0].length;
	}

	public double getValue(int row, int col) {
		return elements[row][col];
	}

	public void setValue(int row, int col, double val) {
		elements[row][col] = val;
	}

	/**
	 * Multiply the given matrix from the right 
	 * @param matrix
	 * @return this * matrix
	 */
	public Matrix multiply(Matrix matrix) {

		if (this.getCols() != matrix.getRows())
			throw new ArithmeticException("Matrix dimensions invalid, can only multiply axb with bxc");

		double[][] result = new double[this.getRows()][matrix.getCols()];

		for (int curRow = 0; curRow < this.getRows(); curRow++) {
			for (int mCol = 0; mCol < matrix.getCols(); mCol++) {
				for (int curCol = 0; curCol < this.getCols(); curCol++) {
					result[curRow][mCol] += this.getValue(curRow, curCol)
							* matrix.getValue(curCol, mCol);
				}
			}
		}
		return new Matrix(result);
	}

	/**
	 * Multiply the given point from the right 
	 * @param p
	 * @return this * p
	 */
	public Point3D multiply(Point3D p) {
		// A 3D point is 4x1 matrix
		double[][] vector = new double[4][1];
		vector[0][0] = p.x;
		vector[1][0] = p.y;
		vector[2][0] = p.z;
		vector[3][0] = p.w;

		Matrix r = this.multiply(new Matrix(vector));
		return new Point3D(r.getValue(0, 0), r.getValue(1, 0), r.getValue(2, 0),r.getValue(3, 0));
	}

	/**
	 * Calculate the inverse of a 4x4 matrix
	 * @return
	 */
	public Matrix invert() {
		int row, col; // indices of adjoint matrix (Adjunkte)
		int z1, z2, z3; // rows of the current sub determinant
		int s1, s2, s3; // colons of the current sub determinant

		boolean negflag = false;
		double det, detInv;
		
		if (this.getCols() != 4 && this.getRows() != 4)
			throw new ArithmeticException("Matrix dimensions invalid, can only invert 4x4 matrix with this function");

		double[][] origin = new double[4][4]; // origin
		double[][] target = new double[4][4]; // target

		// Create a copy
		for (row = 0; row < 4; row++) {
			for (col = 0; col < 4; col++) {
				origin[row][col] = this.getValue(row, col);
			}
		}

		for (row = 0; row < 4; row++) {
			// & 3 works as modulo operator for the current indices
			z1 = (row + 1) & 3;
			z2 = (row + 2) & 3;
			z3 = (row + 3) & 3;
			for (col = 0; col < 4; col++) {
				s1 = (col + 1) & 3;
				s2 = (col + 2) & 3;
				s3 = (col + 3) & 3;
				target[col][row] = origin[z1][s1] * (origin[z2][s2] * origin[z3][s3] 
						- origin[z2][s3] * origin[z3][s2])
						+ origin[z1][s2] * (origin[z2][s3] * origin[z3][s1] 
						- origin[z2][s1] * origin[z3][s3])
						+ origin[z1][s3] * (origin[z2][s1] * origin[z3][s2] 
						- origin[z2][s2] * origin[z3][s1]);
				if (negflag) {
					target[col][row] = -target[col][row];
				}
				negflag = !negflag;
			}
			negflag = !negflag;
		}
		det = origin[0][0] * target[0][0];
		for (row = 1; row < 4; row++) {
			det += origin[0][row] * target[row][0];
		}

		if(DoubleComparison.eq(det, 0)){
			throw new RuntimeException("Determinant zero, matrix not invertible.");
		}

		Matrix dest = Matrix.createIdentity(4);
		// With the use of the inverse determinant we can substitute division with multiplication
		detInv = 1 / det;
		for (row = 0; row < 4; row++) {
			for (col = 0; col < 4; col++) {
				dest.setValue(row, col, target[row][col] * detInv);
			}
		}
		return dest;
	}

}
