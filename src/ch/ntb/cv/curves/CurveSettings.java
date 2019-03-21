package ch.ntb.cv.curves;

import java.awt.geom.Point2D;
import java.util.List;

public class CurveSettings {
	
	List<Point2D> points;

	public CurveSettings(List<Point2D> pointList) {
		this.points = pointList;
	}

	public List<Point2D> getPoints(){
		return points;		
	}

}
