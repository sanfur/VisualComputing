package ch.ntb.cv.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import ch.ntb.cv.fractal.FractalFactory.FractalTypes;
import ch.ntb.geometry.Drawable;

@SuppressWarnings("serial")
public class FractalCanvas extends JPanel{
	
	private FractalFactory cFactory = new FractalFactory();		
	private ArrayList<Drawable> curveList = new ArrayList<Drawable>();
	private Vector<Point> currentPointList = new Vector<Point>();
	
	public FractalCanvas() {
		setBackground(Color.white);
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				currentPointList.add(new Point(e.getX(), e.getY()));
				repaint();
			}
		});
	}

	/**
	 * Create a new Fractal from the chosen type and the given input points
	 * @param selectedItem
	 */
	public void drawCurve(FractalTypes selectedItem) {		
		if (currentPointList.size() > 0) {
			Drawable curve = cFactory.createCurve(selectedItem, currentPointList);
			currentPointList.clear();
			curveList.add(curve);
			repaint();
		}
	}

	/**
	 * Reset the view
	 */
	public void clearAll() {
		currentPointList.clear();
		curveList.clear();
		repaint();		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(Point2D p : currentPointList){
			g2d.setBackground(Color.black);	
			g2d.drawOval((int) p.getX(),(int) p.getY(), 4, 4);
		}

		for (Drawable c : curveList) {
			c.draw(g);
		}
	}
	
	

}
