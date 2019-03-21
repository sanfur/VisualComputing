/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.bresenham;

import java.awt.Graphics;

/**
 * @author N.Frei
 */
public class BresenhamDrawingStrategy implements DrawingStrategy {

    public void drawLine(Line line, Graphics g) {
        drawBresenhamLine(line.point1.x, line.point1.y, line.point2.x, line.point2.y, g);
    }

    public static void drawPoint(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }

    public void drawBresenhamLine(int x1, int y1, int x2, int y2, Graphics g) {
        
    	int x,y,dx,dy,s,error;
    	
    	x = x1;
    	y = y1;

    	dx = x2 - x1;
    	dy = y2 - y1;
    	
    	s = 2*dy;
    	error = 0;
    	
    	if(dx > 0) {
    		if(dy > 0) {
    			while(x <= x2) {
    				drawPoint(g, x, y);
    				x++;
    				error += s;
    				if(error > dx) {
    					y = y + 1;
    					error = error - 2 * dx;
    				}
    			}     		
    		}
    		if(dy <= 0) {
    			while(x <= x2) {
    				drawPoint(g, x, y);
    				x++;
    				error += s;
    				if(error > dx) {
    					y = y - 1;
    					error = error - 2 * dx;
    				}
    			} 
    		}
    		else {
    			while(x <= x2) {
    				drawPoint(g, x, y);
    				x++;
    				error += s;
    				if(error > dx) {
    					y = y - 1;
    					error = error - 2 * dx;
    				}
    			}
    		}
    	}
    	
    	
    	/*
            * TODO: Implementieren Sie hiere den Bresenham Algorithmus
         */
    }

    public Line[] createMyName() {
        /*
		 * TODO: Implementieren Sie die ersten drei Buchstaben ihres Vornamens.
         */

        Line[] lines = null;

        return lines;
    }
}
