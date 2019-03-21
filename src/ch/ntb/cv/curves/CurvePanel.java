package ch.ntb.cv.curves;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import ch.ntb.cv.curves.CurveFactory.CurveTypes;
import ch.ntb.cv.util.Parsers;

@SuppressWarnings("serial")
public class CurvePanel extends JPanel implements ActionListener{
	
	final int vGap = 5;
	final int hGap = 10;
	
	private JButton draw;
	private JButton clearAll;
	private JComboBox curveSelector;
	private JTextField knotVector;
	private JTextField weightVector;	

	private JPanel navigation;
	private CurveCanvas drawingArea;
	private JPanel nurbsControl;

	
	public CurvePanel() {
		setName("Curves");
		
		layoutButtons();		
		drawingArea = new CurveCanvas(); 
		
		this.setLayout(new BorderLayout(hGap, vGap));
		this.add(navigation, BorderLayout.WEST);
		this.add(drawingArea, BorderLayout.CENTER);		
		
	}

	private void layoutButtons() {
		draw = new JButton("Draw");
		curveSelector = new JComboBox(CurveTypes.values());
		clearAll = new JButton("Clear all");
		
		navigation = new JPanel();
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		navigation.add(draw, gbc);
		draw.addActionListener(this);		
		
		gbc.gridy++;
		navigation.add(curveSelector, gbc);
		curveSelector.addActionListener(this);
		
		gbc.gridy++;
		navigation.add(clearAll, gbc);
		clearAll.addActionListener(this);
		
		nurbsControl = new JPanel();
		nurbsControl.setLayout(new GridBagLayout());
		nurbsControl.setVisible(false);
		nurbsControl.setBorder(new TitledBorder("Nurbs Settings"));
		setupNurbsControl();
		
		gbc.gridy++;
		navigation.add(nurbsControl, gbc);
		
		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;
		navigation.add(new JPanel(), gbc);
	}

	private void setupNurbsControl() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		JLabel instructions = new JLabel(
				"<html>Instructions:<br>" +
				"Enter control points on the canvas. Each control point must have an associated weight > 0.<br>" +
				"Depending on the desired order of the curve, a non-periodic knot vector with strictly increasing values must be specified.<br>" +
				"Enter weight and knot vector entries seprarated with a comma ','." +
				"<br><br>" +
				"The knot vector has length: Number of control points + order<br>" +
				"A non-periodic knot vector has <i>order</i> times the same value at start and end.<br><br>" +
				"Example: 5 control points, order 3<br>" +
				"0,0,0,1,2,3,3,3</html>");
		instructions.setPreferredSize(new Dimension(150, 300));
		nurbsControl.add(instructions, gbc);
		
		gbc.gridy++;
		weightVector = new JTextField("1,1,1,1,1", 20);
		weightVector.setBorder(new TitledBorder("Weights"));
		nurbsControl.add(weightVector, gbc);
		
		gbc.gridy++;
		knotVector = new JTextField("0,0,0,1,2,3,3,3", 20);
		knotVector.setBorder(new TitledBorder("Knots"));
		nurbsControl.add(knotVector, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == draw){
			// At least two points needed for a curve
			if(drawingArea.getCurrentPointList().size() > 1){		
				CurveTypes type = (CurveTypes) curveSelector.getSelectedItem();
				Curve curve;
				if(type == CurveTypes.NURBS){
					NurbsCurveSettings settings = new NurbsCurveSettings(drawingArea.getCurrentPointList());
					if(checkAndUpdateCurveSettings(settings) == true){
						curve = CurveFactory.createCurve(type, settings);
					}else{
						JOptionPane.showMessageDialog(this, "Nurbs settings not correct");
						curve = null;
					}					
				}else{
					CurveSettings settings = new CurveSettings(drawingArea.getCurrentPointList());
					curve = CurveFactory.createCurve(type, settings);
				}
				
				if(curve != null){
					drawingArea.drawCurve(curve);
				}else{
					JOptionPane.showMessageDialog(this, "Curve could not be created");
				}
			}else{
				JOptionPane.showMessageDialog(this, "A curve should have at least two control points");
			}
		}else if(source == clearAll){
			drawingArea.clearAll();	
		}else if(source == curveSelector){
			if((CurveTypes) curveSelector.getSelectedItem() == CurveTypes.NURBS){
				nurbsControl.setVisible(true);
			}else{
				nurbsControl.setVisible(false);
			}
		}else{
			System.err.println("An event from an unhandled source was sent." + source.toString());
		}		
	}
	
	/**
	 * Reads weight and knot vector and put them in the settings
	 * @param settings
	 * @return false if vectors not correct
	 */
	public boolean checkAndUpdateCurveSettings(NurbsCurveSettings settings) {
		double[] weights = Parsers.parseRealVector(weightVector.getText());
		double[] knots = Parsers.parseRealVector(knotVector.getText());
		
		if(NurbsCurve.checkWeights(weights, settings.getPoints().size()) == true
				&& NurbsCurve.checkKnots(knots, settings.getPoints().size()) == true){
			settings.setWeights(weights);
			settings.setKnots(knots);
			return true;
		}else{
			return false;
		}
	}

}
