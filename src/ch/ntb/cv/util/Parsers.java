package ch.ntb.cv.util;

import java.util.ArrayList;
import java.util.List;

import ch.ntb.geometry.Point3D;

public class Parsers {
	
	/**
	 * Extracts 3D points from string
	 * Example (2x2 matrix) 
	 * 1,2,3;4,5,6
	 * 6,7,8;3,4,5
	 * @param matrix
	 */
	public static List<List<Point3D>> parseControlPoints(String matrix){
		if(!matrix.isEmpty()){			
			// In memory documents have \n as line break
			String[] rowStrings = matrix.split("\n");
			List<List<Point3D>> rows = new ArrayList<List<Point3D>>(rowStrings.length);
			for (int i = 0; i < rowStrings.length; i++) {
				String[] colStrings = rowStrings[i].split(";");
				List<Point3D> row = new ArrayList<Point3D>(colStrings.length);
				for (int j = 0; j < colStrings.length; j++) {
					String[] pointStrings = colStrings[j].split(",");
					if(pointStrings.length != 3){
						System.err.println("Error while reading point in matrix");
						return null;
					}
					row.add(new Point3D(
							Double.parseDouble(pointStrings[0]), 
							Double.parseDouble(pointStrings[1]), 
							Double.parseDouble(pointStrings[2])));
				}
				rows.add(row);
			}
			return rows;
		}else{
			return null;
		}
	}
	
	/**
	 * Splits a comma separated string of real values and creates a double[]
	 * @param vectorString
	 */
	public static double[] parseRealVector(String vectorString) {
		if(!vectorString.isEmpty()){
			String [] values = vectorString.split(",");
			double[] result = new double[values.length];
			for (int i = 0; i < values.length; i++) {				
				result[i] = Double.parseDouble(values[i]);
			}
			return result;
		}else{
			return null;
		}
	}

	/**
	 * Extracts weights from string
	 * Example (2x2 matrix) 
	 * 1;4
	 * 6;5
	 * @param matrix
	 */
	public static List<List<Double>> parseWeightGrid(String matrix) {
		if(!matrix.isEmpty()){			
			// In memory documents have \n as line break
			String[] rowStrings = matrix.split("\n");
			List<List<Double>> rows = new ArrayList<List<Double>>(rowStrings.length);
			for (int i = 0; i < rowStrings.length; i++) {
				String[] colStrings = rowStrings[i].split(";");
				List<Double> row = new ArrayList<Double>(colStrings.length);
				for (int j = 0; j < colStrings.length; j++) {
					row.add(Double.parseDouble(colStrings[j]));
				}
				rows.add(row);
			}
			return rows;
		}else{
			return null;
		}
	}

}
