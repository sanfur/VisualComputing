package ch.ntb.cv.surfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import ch.ntb.cv.camera.Camera;
import ch.ntb.cv.camera.Scene;
import ch.ntb.cv.camera.SceneView;
import ch.ntb.cv.curves.NurbsCurve;
import ch.ntb.cv.util.Parsers;
import ch.ntb.geometry.Point3D;

@SuppressWarnings("serial")
public class SurfacePanel extends JPanel implements ActionListener{
	
	final int vGap = 5;
	final int hGap = 10;
		
	private Scene real, bspline;
	private SceneView bsplineView, realView;
	private JPanel viewsPanel, navigation;
	
	private JTextArea controlGrid;
	private JTextArea weightsGrid;
	private JTextField knotVectorV;
	private JTextField knotVectorU;
	private JButton draw;
	private JButton clearAll;

	
	public SurfacePanel() {
		setName("Surfaces");
		
		setupViews();		
		setupButtons();	
		
		setLayout(new BorderLayout(hGap, vGap));
		add(viewsPanel, BorderLayout.CENTER);
		add(navigation, BorderLayout.WEST);
		repaint();
	}

	private void setupButtons() {
		draw = new JButton("Draw Curve");
		clearAll = new JButton("Clear all");
		
		navigation = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		JLabel instructions = new JLabel(
				"<html>Instructions:<br>" +
				"Enter the <b>non-periodic</b> knot vectors for U and V direction as well as a control point and weight matrix below.<br>" +
				"The upper panel shows the BSpline Basic surface, the lower panel the real surface<br>" +
				"</html>");
		instructions.setPreferredSize(new Dimension(150, 250));
		navigation.add(instructions, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;		
		knotVectorU = new JTextField("0,0,0,3,3,3", 20);
		knotVectorU.setBorder(new TitledBorder("Knots U direction"));
		navigation.add(knotVectorU, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;		
		knotVectorV = new JTextField("0,0,0,3,3,3", 20);
		knotVectorV.setBorder(new TitledBorder("Knots V direction"));
		navigation.add(knotVectorV, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		navigation.add(new JLabel("Enter control points and weights as uxv matrix below"), gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		String ln = "\n";
		controlGrid = new JTextArea(
				"-1,1,2;0,1,1;1,1,0.5" + ln + 
				"-1,0,1;0,0,0;1,0,0" + ln +
				"-1,-1,-1;0,-1,-0.5;1,-1,0.5");
		controlGrid.setPreferredSize(new Dimension(150,150));
		controlGrid.setBorder(new TitledBorder("Control points"));
		navigation.add(controlGrid, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		weightsGrid = new JTextArea(
				"1; 4; 2" + ln + 
				"2; 1; 3" + ln +
				"1; 3; 3");
		weightsGrid.setPreferredSize(new Dimension(150,150));
		weightsGrid.setBorder(new TitledBorder("Weights"));
		navigation.add(weightsGrid, gbc);
				
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(draw);
		buttons.add(clearAll);
		
		gbc.gridy++;
		gbc.gridx = 0;
		navigation.add(buttons, gbc);
		
		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;		
		navigation.add(new JPanel(), gbc);
		
		// Add Action listeners
		draw.addActionListener(this);
		clearAll.addActionListener(this);
		
	}

	private void setupViews() {
		
		// Set up scene
		bspline = new Scene();
		Camera c = new Camera(
				new Point3D(15,15,15), 
				Camera.defaultLook, 
				Camera.defaultUp, 
				Camera.defaultFov, 
				Camera.defaultDisplaySize, 
				Camera.defaultNearplane, 
				Camera.defaultFarplane);
		bsplineView = new SceneView(c, bspline);
		bsplineView.setPreferredSize(Camera.defaultDisplaySize);
		bsplineView.setBorder(new TitledBorder("Weight Surface (without control point influence)"));
		
		real = new Scene();
		Camera c2 = new Camera(
				new Point3D(15,15,15), 
				Camera.defaultLook, 
				Camera.defaultUp, 
				Camera.defaultFov, 
				Camera.defaultDisplaySize, 
				Camera.defaultNearplane, 
				Camera.defaultFarplane);
		realView = new SceneView(c2, real);
		realView.setPreferredSize(Camera.defaultDisplaySize);
		realView.setBorder(new TitledBorder("Nurbs Surface"));
		
		viewsPanel = new JPanel(new GridLayout(2, 1, hGap, vGap));
		viewsPanel.add(bsplineView);
		viewsPanel.add(realView);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == draw){			
			List<List<Point3D>> controlPoints = Parsers.parseControlPoints(controlGrid.getText());
			if(controlPoints == null){
				JOptionPane.showMessageDialog(this, "Control points not correct");
				return;
			}
			
			NurbsSurfaceSettings settings = new NurbsSurfaceSettings(controlPoints);
			
			List<List<Double>> weights = Parsers.parseWeightGrid(weightsGrid.getText());
			if(weights == null 
					|| controlPoints.size() != weights.size() 
					|| controlPoints.get(0).size() != weights.get(0).size()){
				JOptionPane.showMessageDialog(this, "Weight matrix is not correct");
				return;
			}
			settings.setWeights(weights);
			
			double[] knotsU = Parsers.parseRealVector(knotVectorU.getText());
			if(NurbsCurve.checkKnots(knotsU, settings.getControlPoints().size()) == false){
				JOptionPane.showMessageDialog(this, "Knot vector for u direction not correct");
				return;
			}
			settings.setKnotsU(knotsU);
			
			double[] knotsV = Parsers.parseRealVector(knotVectorV.getText());
			if(NurbsCurve.checkKnots(knotsV, settings.getControlPoints().get(0).size()) == false){
				JOptionPane.showMessageDialog(this, "Knot vector for v direction not correct");
				return;
				
			}
			settings.setKnotsV(knotsV);
			
			// Plot Control point and surface
			real.addObject(new StandardSurface(controlPoints));
			NurbsSurface surf = new NurbsSurface(controlPoints, settings);
			real.addObject(surf);
			
			// Plot weight surface
			bspline.addObject(surf.getWeightSurface());
			
			repaint();
		}else if(source == clearAll){
			bspline.clear();
			real.clear();
			repaint();
		}else{
			System.err.println("An event from an unhandled source was sent." + source.toString());
		}		
	}

}
