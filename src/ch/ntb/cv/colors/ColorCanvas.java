package ch.ntb.cv.colors;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorCanvas extends JPanel{

	private ColorShape shape;

	public ColorCanvas(int red, int green, int blue) {
		super();
		setBackground(Color.white);
		shape = new ColorCircles();
		setRGB(red, green, blue);		
	}

	public void setRGB(int red, int green, int blue) {
		shape.setRGB(red, green, blue);
		this.repaint();		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		shape.draw(g);
	}
	
	
}
