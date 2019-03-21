package ch.ntb.cv.curves;


public class CurveFactory {
	
	public enum CurveTypes {Standard, Bezier, BSpline, NURBS}
	
	public static Curve createCurve(CurveTypes curveType, CurveSettings settings){
		switch(curveType){
		case Standard:
			return new StandardCurve(settings.getPoints());
		case Bezier:
			return new BezierCurve(settings.getPoints());
		case BSpline:
			return new BSplineCurve(settings.getPoints());
		case NURBS:
			if(settings != null && settings instanceof NurbsCurveSettings){
				return new NurbsCurve(settings.getPoints(), (NurbsCurveSettings) settings);
			}else{
				System.err.println("NURBS can only be created with according settings");
			}
			break;
		default:
				System.err.println("The selected curve type is not implemented yet.");
		}
		return null;
	}

}
