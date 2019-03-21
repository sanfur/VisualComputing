package ch.ntb.cv.bresenham;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BresenhamDrawingCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private static final int DRAW_LINE_ST = 1; // Linien zeichnen
    private static final int DEF_CLIP_AREA_ST = 2; // Clipping Bereich definieren

    private int state = DRAW_LINE_ST;	// Status
    private Point startPoint = null;

    private static final long serialVersionUID = 1L;
    private final DrawingStrategy drawingStrategy = new BresenhamDrawingStrategy();// SampleBresenhamDrawingStrategy();
    /**
     * @associates Line
     */
    private ArrayList<Line> lines; // Liste der zu zeichnenden Linien
    /**
     * @associates Line
     */
    private ArrayList<Line> tempLines; 	// Temporäre Linien, die nur einmal gezeichnet werden

    public BresenhamDrawingCanvas() {
        super();
        this.setBackground(Color.white);
        lines = new ArrayList<Line>();
        tempLines = new ArrayList<Line>();
        lines.clear();
        tempLines.clear();
        
        this.addMouseListener(this);
	this.addMouseMotionListener(this);

    }

    public void setState(int state) {
        this.state = state;
    }

    public void addLine(Point point1, Point point2) {
        Line line = new Line(point1, point2);
        lines.add(line);

        this.repaint();
    }

    public void drawMyName() {
        Line[] myName = drawingStrategy.createMyName();
        if(myName == null){ System.err.println("Function createMyName() not yet implemented "); return; }
        for (int i = 0; i < myName.length; i++) {
            lines.add(myName[i]);
        }
        this.repaint();
    }

    public void addTempLine(Point point1, Point point2) {
        Line tempLine = new Line(point1, point2);
        tempLines.add(tempLine);
    }

    public void clear() {
        lines.clear();
        this.setBackground(Color.white);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ListIterator<Line> lineIt = lines.listIterator();
        Line line;

        while (lineIt.hasNext()) {
            line = lineIt.next();
            drawingStrategy.drawLine(line, g);
            //System.out.print("lines[] = new Line(" + line.point1.x +"," + line.point1.y +"," + line.point2.x +"," + line.point2.y +");");
        }

        // Temporäre Linien zeichnen, falls vorhanden (ohne Clipping)
        lineIt = tempLines.listIterator();
        while (lineIt.hasNext()) {
            line = (Line) lineIt.next();
            drawingStrategy.drawLine(line, g);
        }
        tempLines.clear();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == DRAW_LINE_ST || state == DEF_CLIP_AREA_ST) {
            startPoint = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (state == DRAW_LINE_ST) {
            addLine(startPoint, e.getPoint());
            startPoint = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (state == DRAW_LINE_ST) {
            if (startPoint != null) {
                addTempLine(startPoint, e.getPoint());
                this.repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

}
