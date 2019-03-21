package ch.ntb.cv.camera;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SceneView extends JPanel{
	
	public static enum NavigationMode {FirstPerson, ThirdPerson};	
	private NavigationMode mode = NavigationMode.ThirdPerson;	
	
	private Camera camera;	
	private Camera cameraBackup;
	private Renderer renderer;

	public SceneView(Camera defaultCamera, Scene scene) {
		
		// Set up camera	
		this.camera = defaultCamera;
		this.cameraBackup = defaultCamera.getCopy();
				
		this.renderer = new Renderer(camera, scene.getObjects());
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		
		MouseAdapter mouseControl = new MouseAdapter() {
			// Start Coordinates
			private int x, y;
			
			// Backup camera 
			private Camera cameraStart;
			
			// Motion speeds
			private final double motionSpeedxThirdPerson = Math.PI / 2500;
			private final double motionSpeedyThirdPerson = Math.PI / 2000;
			// Don't move as fast in first person mode
			private final double motionSpeedxFirstPerson = Math.PI / 5000;
			private final double motionSpeedyFirstPerson = Math.PI / 4000;
			
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				cameraStart = camera.getCopy();
			}
			public void mouseDragged(MouseEvent e) {
				// Calculate difference
				int dx = e.getX() - x;
				int dy = y - e.getY();

				Camera cameraResult = cameraStart.getCopy();
				if(SwingUtilities.isRightMouseButton(e)){
					cameraResult.zoom(1 + ((double)dy)/getHeight());
				}else{
					switch (mode) {
					case ThirdPerson:
						cameraResult.thirdPersonMove(motionSpeedxThirdPerson * dx , motionSpeedyThirdPerson * dy);
						break;
					case FirstPerson:
						cameraResult.firstPersonMove(motionSpeedxFirstPerson * dx , motionSpeedyFirstPerson * dy);
						break;
	
					default:
						break;
					}
				}
				
				camera.assign(cameraResult);
				repaint();
			}
		};
		this.addMouseListener(mouseControl);
		this.addMouseMotionListener(mouseControl);
	}
	
	/**
	 * Resets the view to the initial view settings
	 */
	public void reset(){
		camera.assign(cameraBackup);
		repaint();
	}
	
	/** 
	 * Sets the navigation mode
	 * @param mode
	 */
	public void setNavigationMode(NavigationMode mode){
		this.mode = mode;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderer.render(g);
	}
	
	

	
	
	
	

}
