package ch.ntb.cv.curves;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.ntb.cv.curves.CurveFactory.CurveTypes;

@SuppressWarnings("serial")
public class NurbsPanel extends CurvePanel implements ActionListener{
	
	final int vGap = 5;
	final int hGap = 10;
	
	private JPanel curveControl;
	
	private JButton draw;
	private JButton clearAll;
	
	private CurveCanvas drawingArea;
	private NurbsControlPanel nurbsControlPanel;	
	
	public NurbsPanel() {
		setName("NURBS Editor");
		
		setupButtons();		
		drawingArea = new CurveCanvas(); 
		
		this.setLayout(new BorderLayout(hGap, vGap));
		this.add(curveControl, BorderLayout.WEST);
		this.add(drawingArea, BorderLayout.CENTER);
	}

	private void setupButtons() {
		draw = new JButton("Draw Curve");
		clearAll = new JButton("Clear all");
		
		curveControl = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		JLabel instructions = new JLabel(
				"<html>Instructions:<br>" +
				"Enter control points on the canvas. Each control point must have an associated weight > 0.<br>" +
				"Depending on the desired order of the curve, a knot vector with strictly increasing values must be specified.<br>" +
				"Enter weight and knot vector entries seprarated with a comma ','.</html>");
		instructions.setPreferredSize(new Dimension(150, 150));
		curveControl.add(instructions, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		nurbsControlPanel = new NurbsControlPanel("Settings", "1,1,1,1,1", "0,0,0,1,2,3,3,3");
		curveControl.add(nurbsControlPanel, gbc);
				
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(draw);
		buttons.add(clearAll);
		
		gbc.gridy++;
		gbc.gridx = 0;
		curveControl.add(buttons, gbc);
		
		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;		
		curveControl.add(new JPanel(), gbc);
		
		// Add Action listeners
		draw.addActionListener(this);
		clearAll.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == draw){
			// At least two points needed for a curve
			if(drawingArea.getCurrentPointList().size() > 1){		
				NurbsCurveSettings settings = new NurbsCurveSettings(drawingArea.getCurrentPointList());
				if(nurbsControlPanel.checkAndUpdateCurveSettings(settings) == true){
					Curve curve = CurveFactory.createCurve(CurveTypes.NURBS, settings);
					drawingArea.drawCurve(curve);
				}else{
					JOptionPane.showMessageDialog(this, "Curve could not be created, weight or knot vector not correct");
				}
			}else{
				throw new RuntimeException("A curve should have at least two control points");
			}
		}else if(source == clearAll){
			drawingArea.clearAll();			
		}else{
			System.err.println("An event from an unhandled source was sent." + source.toString());
		}		
	}



}
