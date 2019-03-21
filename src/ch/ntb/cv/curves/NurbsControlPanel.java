package ch.ntb.cv.curves;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NurbsControlPanel extends JPanel implements ActionListener{
	
	final int vGap = 2;
	final int hGap = 5;
	
	private JTextField weightVector;
	private JTextField knotVector;
	private JTextField order;
	private JLabel nOfControlPoints;
	private JLabel nOfKnots;
	
	public NurbsControlPanel(String name, String weights, String knots) {
		setBorder(BorderFactory.createTitledBorder(name));
		weightVector = new JTextField(weights, 20);
		knotVector = new JTextField(knots, 20);
		order = new JTextField("3", 5);
		nOfControlPoints = new JLabel("-");
		nOfKnots = new JLabel("-");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, vGap, hGap);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
				
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		add(new JLabel("Weight values"), gbc);
		
		gbc.gridx++;
		add(new JLabel("Expected\nSize"), gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		add(weightVector, gbc);
		
		gbc.gridx++;
		add(nOfControlPoints, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		add(new JLabel("Desired order"), gbc);
		
		gbc.gridx++;
		add(order, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		add(new JLabel("Knot values"), gbc);
		
		gbc.gridx++;
		add(new JLabel("Expected\nSize"), gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		add(knotVector, gbc);
		
		gbc.gridx++;
		add(nOfKnots, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == order){
			// update
		}else{
			System.err.println("An event from an unhandled source was sent." + source.toString());
		}			
	}

	/**
	 * Splits a comma separated string of real values and creates a double[]
	 * @param vectorString
	 */
	private double[] parseVector(String vectorString) {
		if(!vectorString.isEmpty()){
			String [] values = vectorString.split(",");
			double[] result = new double[values.length];
			for (int i = 0; i < values.length; i++) {				
				result[i] = Double.parseDouble(values[i]);
			}
			return result;
		}else{
			return null;
		}
	}

	/**
	 * Reads weight and knot vector and put them in the settings
	 * @param settings
	 * @return false if vectors not correct
	 */
	public boolean checkAndUpdateCurveSettings(NurbsCurveSettings settings) {
		double[] weights = parseVector(weightVector.getText());
		
		
		double[] knots = parseVector(knotVector.getText());
		
		
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
