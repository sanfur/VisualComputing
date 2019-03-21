package ch.ntb.cv.bresenham;

import java.awt.Graphics;

/**
 * @author N.Frei
 */

public interface DrawingStrategy {
	public void drawLine(Line line, Graphics g);

	public Line[] createMyName();
}
