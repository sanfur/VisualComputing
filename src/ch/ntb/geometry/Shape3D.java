package ch.ntb.geometry;

import java.awt.Color;
import java.util.List;


public interface Shape3D {
	
	public List<Face> getFaces();
	
	public Color getForegroundColor();

}
