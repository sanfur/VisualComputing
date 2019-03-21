package ch.ntb.cv.fractal;

import java.awt.Point;
import java.util.Vector;

import javax.swing.JOptionPane;

import ch.ntb.geometry.Drawable;

public class FractalFactory {
	
	public enum FractalTypes{Tree, Farn, Julia};
	
	public Drawable createCurve(FractalTypes type, Vector<Point> c){
		switch(type){
		case Tree:
			return new FractalTree(c);
		case Farn:
			JOptionPane.showMessageDialog(null, "Farn is not yet implemented");
			return new FractalFarn();
		case Julia:
			JOptionPane.showMessageDialog(null, "Julia is not yet implemented");
			return new FractalJulia();
		}
		return null;
	}

}
