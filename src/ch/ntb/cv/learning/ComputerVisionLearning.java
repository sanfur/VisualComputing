package ch.ntb.cv.learning;

import ch.ntb.cv.background.BackgroundPanel;
import ch.ntb.cv.bresenham.BresenhamDrawingPanel;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ch.ntb.cv.camera.CameraPanel;
import ch.ntb.cv.colors.ColorPanel;
import ch.ntb.cv.curves.CurvePanel;
import ch.ntb.cv.fractal.FractalPanel;
import ch.ntb.cv.pathfinding.PathfindingPanel;
import ch.ntb.cv.surfaces.SurfacePanel;

public class ComputerVisionLearning extends JFrame{

	private static final long serialVersionUID = -8650817367712198942L;
	
	public ComputerVisionLearning() {
		this.setName("Computer Vision Learning Application");
		initComponents();
	}
	
	private void initComponents() {
		
		JTabbedPane tabs = new JTabbedPane();
                tabs.add(new BresenhamDrawingPanel());
		tabs.add(new CurvePanel());
//		tabs.add(new SurfacePanel());
		tabs.add(new FractalPanel());
		tabs.add(new ColorPanel());
		tabs.add(new CameraPanel());
                tabs.add(new BackgroundPanel());
                tabs.add(new PathfindingPanel());

		this.add(tabs, BorderLayout.CENTER);
		this.setSize(1200, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ComputerVisionLearning().setVisible(true);
            }
        });
	}

}
