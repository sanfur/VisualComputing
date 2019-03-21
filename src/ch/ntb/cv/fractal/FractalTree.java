package ch.ntb.cv.fractal;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import ch.ntb.geometry.Drawable;

/**
 * Fractal Tree based on Koch-Flocke
 * Angle 60° = PI/3
 * Distance quotien = 1 : 1/3
 *
 */
public class FractalTree implements Drawable {
	
	private class Line {
		Point p1, p2;

		Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}
	
	ArrayList<Line> lines = new ArrayList<Line>();
	
	private double sinPhi, cosPhi;

	public FractalTree(Vector<Point> c) {
		Point p1, p2, p3;
		p1 = new Point(10, 150);
		p3 = new Point(510, 150);
		setRotationAngle(Math.PI/3);
		p2 = rotateVector(p1, p3);
		
		if (c != null && c.size() > 1)  {
			// Create a line from a Point p to its neighbor in the point list
			for (int i = 0; i < c.size()-1; i++){				
				lines.add(new Line(c.get(i), c.get(i+1)));
			}
			// Connect start and end of the line
			lines.add(new Line(c.get(c.size()-1), c.get(0)));
		} else {
			lines.add(new Line(p1, p2));
			lines.add(new Line(p2, p3));
			lines.add(new Line(p3, p1));
		}

		// Recursive generation of fractal
		ArrayList<Line> n;
		for (int i = 0; i < 5; i++) {
			n = new ArrayList<Line>();
			for (Line l : lines) {
				p2 = new Point(l.p1.x + (l.p2.x - l.p1.x)/3, l.p1.y + (l.p2.y - l.p1.y)/3);
				Point p4 = new Point(l.p1.x + 2*(l.p2.x - l.p1.x)/3, l.p1.y + 2*(l.p2.y - l.p1.y)/3);
				p3 = rotateVector(p2, p4);

				n.add(new Line(l.p1, p2));
				n.add(new Line(p2, p3));
				n.add(new Line(p3, p4));
				n.add(new Line(p4, l.p2));
			}
			lines = n;
		}
	}

	private void setRotationAngle(double angle) {
		sinPhi = Math.sin(angle);
		cosPhi = Math.cos(angle);
	}
	
	/**
	 * Rotate vector around rotation angle
	 *  cos(phi) -sin(phi) 0   x   cos(phi)*x -sin(phi)*y 0
	 *  sin(phi)  cos(phi) 0 * y = sin(phi)*x  cos(phi)*y 0
	 *     0         0     1   z      0           0       1
	 * @param p1 start point
	 * @param p2 end point
	 * @return
	 */
	private Point rotateVector(Point p1, Point p2) {
		int dx = (int) (cosPhi*(p2.x - p1.x) - sinPhi*(p2.y - p1.y));
		int dy = (int) (sinPhi*(p2.x - p1.x) + cosPhi*(p2.y - p1.y));
		return new Point(p1.x + dx, p1.y + dy);
	}


	public void draw(Graphics g) {
		for (Line l : lines)
			g.drawLine(l.p1.x, l.p1.y, l.p2.x, l.p2.y);
	}


}
