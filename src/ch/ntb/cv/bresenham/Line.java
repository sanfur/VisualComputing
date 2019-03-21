package ch.ntb.cv.bresenham;

import java.awt.Point;

public class Line {
	public Line(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public Line(int x1, int y1, int x2, int y2) {
		point1 = new Point(x1, y1);
		point2 = new Point(x2, y2);	
	}
	
	public void swapPoints() {
		Point temp = point1;
		point1     = point2;
		point2     = temp;
	}

	public Point point1;
	public Point point2;
}