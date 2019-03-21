package ch.ntb.cv.pathfinding;

import static ch.ntb.cv.pathfinding.PathfindingCanvas.Type.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PathfindingCanvas extends JPanel {

    public enum Type{ start, goal, blocked, none  }
    
    private int r = 3, x, y;
    private int gridSize;
    private int offset;
    private Type type;

    PathfindingController pc = null;
    BufferedImage currentBuffer = null;

    public PathfindingCanvas(int width, int height, int grid) {
        super();
        type = none;
        
        pc = new PathfindingController(width, height, grid);
        pc.setPredefinedMap();
        
        setBackground(Color.white);
        gridSize = grid;
        offset = grid / 2;

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouse(e);
            }

            public void mouse(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                int xo = x, yo = y;
                x = roundGrid(x);
                y = roundGrid(y);
                if (x != xo || y != yo) {
                    repaint();
                }
            }

            private int roundGrid(int x) {
                return ((x + gridSize / 2) / gridSize) * gridSize;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouse(e);
            }

        });
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                
                switch(type){
                    case   start:   pc.setStart(new Point(x/gridSize, y/gridSize)); break;
                    case    goal:   pc.setGoal(new Point(x/gridSize, y/gridSize));  break;
                    case blocked:   pc.setOccupancy(x/gridSize, y/gridSize);        break;
                }

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }
        });

    }

    
    public void setType(Type t){
        type = t;
    }

    public void setImage() {
        currentBuffer = pc.getGeneratedMap();
        this.repaint();
    }
    
    public void clearAll() {
        currentBuffer = null;
        this.setBackground(Color.white);
        this.repaint();
    }

    private boolean eqP(int x, int y, Point2D p) {
        if (abs(x - p.getX()) < r && abs(y - p.getY()) < r) {
            return true;
        }
        return false;
    }

    private double abs(double d) {
        return d > 0 ? d : -d;
    }

    private void drawPoint(Graphics2D g2d, int x, int y) {
        int s = 4;
        g2d.drawLine(x - s, y - s, x + s, y + s);
        g2d.drawLine(x - s, y + s, x + s, y - s);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        currentBuffer = pc.getGeneratedMap();
        
        if (currentBuffer != null) {
            g.drawImage(currentBuffer, 0, 0, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        drawPoint(g2d, x + offset, y + offset);

    }

}
