package ch.ntb.cv.curves;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class StandardCurve extends Curve{

	public StandardCurve(List<Point2D> points) {
		super(points);
	}

	@Override
	protected void drawCurve(Graphics2D g2d) {
		g2d.setColor(Color.black);
		for(int i = 0; i < controlPoints.size()-1; i++){				
			g2d.draw(new Line2D.Float(controlPoints.get(i),controlPoints.get(i+1)));			
			//drawLine(g2d, controlPoints.get(i),controlPoints.get(i+1));
		}		
	}
	
	/**
	 * Draw a line from start to end according the Bresenham algorithm
	 * @param g
	 * @param start
	 * @param end
	 */
	public void drawLine(Graphics g, Point2D start, Point2D end){
		int x1 = (int) (start.getX());
		int x2 = (int) (end.getX());
		int y1 = (int) (start.getY());
		int y2 = (int) (end.getY());

		int x, y, error, delta, step, dx, dy, inc_x, inc_y;

		// Backup coordinates
		x = x1;
		y = y1;

		// Calculate deltas
		dy = y2 - y1;
		dx = x2 - x1;

		// Initialize increments according to line direction
		if (dx > 0){
			// Right
			inc_x = 1;
		}else{
			// Left
			inc_x = -1;
		}

		if (dy > 0){
			// Top
			inc_y = 1;
		}else{
			// Down
			inc_y = -1;
		}

		// Angle smaller or bigger 45°?
		if (Math.abs(dy) < Math.abs(dx)){
			// Smaller 45°
			error = -Math.abs(dx);
			delta = 2 * Math.abs(dy);
			step = 2 * error;
			// Iterate over every pixel column
			while (x != x2)	{
				g.fillRect(x, y, 1, 1);
				x += inc_x;
				error = error + delta;
				// Next column reached
				if (error > 0){
					y += inc_y;
					error += step;
				}
			}
		} else{
			// Bigger 45°
			error = -Math.abs(dy);
			delta = 2 * Math.abs(dx);
			step = 2 * error;
			// Iterate over every pixel row
			while (y != y2){
				g.fillRect(x, y, 1, 1);
				y += inc_y;
				error = error + delta;
				// Next row reached
				if (error > 0){
					x += inc_x;
					error += step;
				}
			}
		}
		g.fillRect(x2, y2, 1, 1);
	}
	
	
	
	

}
