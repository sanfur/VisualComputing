package ch.ntb.cv.camera;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ch.ntb.cv.camera.SceneView.NavigationMode;
import ch.ntb.geometry.Cube;


@SuppressWarnings("serial")
public class CameraPanel extends JPanel{
	
	final int vGap = 5;
	final int hGap = 10;
		
	private SceneView view;
	private JPanel navigation;
	
	private JButton resetView;
	private JRadioButton firstPersonMode;
	private JRadioButton thirdPersonMode;	

	public CameraPanel() {
		setName("Camera Control");

		setupView();		
		setupButtons();	
		
		setLayout(new BorderLayout(hGap, vGap));
		add(view, BorderLayout.CENTER);
		add(navigation, BorderLayout.WEST);
		repaint();
	}

	private void setupView() {		
		
		// Set up scene
		Scene s = new Scene();
		s.addObject(new Cube());		
		Camera c = new Camera(
				Camera.defaultEye, 
				Camera.defaultLook, 
				Camera.defaultUp, 
				Camera.defaultFov, 
				Camera.defaultDisplaySize, 
				Camera.defaultNearplane, 
				Camera.defaultFarplane);
		view = new SceneView(c, s);
		view.setPreferredSize(Camera.defaultDisplaySize);		
	}

	private void setupButtons() {
		navigation = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		firstPersonMode = new JRadioButton("First Person");
		thirdPersonMode = new JRadioButton("Third Person Mode");
		thirdPersonMode.setSelected(true);
		
		ButtonGroup group = new ButtonGroup();
		group.add(firstPersonMode);
		group.add(thirdPersonMode);
		
		resetView = new JButton("Reset");
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		navigation.add(firstPersonMode, gbc);
		
		gbc.gridy++;
		navigation.add(thirdPersonMode, gbc);
		
		gbc.gridy++;
		navigation.add(resetView, gbc);
		
		gbc.gridy++;
		JLabel instructions = new JLabel(
				"<html>" +
				"First Person: <br>" +
				"Head movement corresponds to mouse movement<br>" +
				"Third Person: <br>" +
				"Person moves around the object corresponding to the mouse movement<br>" +
				"<br>" +
				"Zoom with right mouse button pressed" +
				"</html>");
		instructions.setPreferredSize(new Dimension(200, 200));
		navigation.add(instructions, gbc);
		
		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;
		navigation.add(new JPanel(), gbc);
		
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if(src == resetView){
					view.reset();					
				}else if(src == firstPersonMode){
					view.setNavigationMode(NavigationMode.FirstPerson);
					
				}else if(src == thirdPersonMode){
					view.setNavigationMode(NavigationMode.ThirdPerson);
				}				
			}
		};
		
		firstPersonMode.addActionListener(al);
		thirdPersonMode.addActionListener(al);
		resetView.addActionListener(al);
	}
	

}
