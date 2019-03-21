package ch.ntb.math;

public class DoubleComparison {
	
	/**
	 * Default number of significant digits
	 */
	private static int significantDigits = 10;
	
	/**
	 * Exponent for a given number of significant digits as index  
	 */
	private static final double[] exponents = { 1, 1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6,
			1e-7, 1e-8, 1e-9, 1e-10, 1e-11, 1e-12, 1e-13, 1e-14, 1e-15, 1e-16 };
	
	/**
	 * Check whether a equals b regarding the actual number of significant digits
	 * @param a
	 * @param b
	 * @return true if equal
	 */
	public static boolean eq(double a, double b) {
		// Absolute compare if one of the numbers is zero
		if (a == 0){
			return Math.abs(b) < exponents[significantDigits];
		}else if (b == 0){
			return Math.abs(a) < exponents[significantDigits];
		}else{
			// Relative compare on the given number of significant digits of the mean value
			return Math.abs(a - b) < (Math.abs(a) + Math.abs(b)) * exponents[significantDigits] / 2;
		}
	}

	/**
	 * Check whether a is less than b
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean le(double a, double b) {
		return a < b && !eq(a, b);
	}

	/**
	 * Check whether a is less or equal b
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean leq(double a, double b) {
		return a < b || eq(a, b);
	}

	/** 
	 * Check whether a is greater b
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean gt(double a, double b) {
		return a > b && !eq(a, b);
	}

	/**
	 * Check whether a is greater or equal b
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean geq(double a, double b) {
		return a > b || eq(a, b);
	}

	/**
	 * Check whether a is not equal b
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean neq(double a, double b) {
		return !eq(a, b);
	}

	public static int getSignificantDigits() {
		return significantDigits;
	}

	public static void setSignificantDigits(int significantDigits) {
		if (significantDigits >= 1 && significantDigits < exponents.length)
		DoubleComparison.significantDigits = significantDigits;
	}

	/**
	 * TODO What does this function return?
	 * The minimum delta with the current number of significant bits + one delta of the next accuracy
	 * @param a
	 * @return
	 */
	public static double getMinDelta(double a) {
		return 1.1 * exponents[significantDigits] * Math.abs(a);
	}

	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		double v = 999999990;
		int g = 32;
		double rd = getMinDelta(v), d = rd * 4 / g;
		System.out.println("Check +/- " + g/2 + " values around " + v + " in a total distance of 4 times the minimum delta");
		System.out.println("Distance =" + d + " Minimum Delta =" + rd);
		System.out.println();
		for (int i = 0; i < g; i++) {
			double h = (v + (i - g / 2) * d);
			System.out.println(i + ".   A = " + v + " B = " + h);
			System.out.print(" LE  " + le(v, h) + "	");
			System.out.print(" LEQ " + leq(v, h) + "	");
			System.out.print(" GT  " + gt(v, h) + "	");
			System.out.print(" GEQ " + geq(v, h) + "	");
			System.out.print(" NEQ " + neq(v, h)+"   ");
			System.out.println(" EQ  " + eq(v, h));

		}
	}
}
