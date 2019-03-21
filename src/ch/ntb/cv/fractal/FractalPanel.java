package ch.ntb.cv.fractal;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ch.ntb.cv.fractal.FractalFactory.FractalTypes;

@SuppressWarnings("serial")
public class FractalPanel extends JPanel implements ActionListener {
	
	final int vGap = 5;
	final int hGap = 10;
	
	private JButton draw;
	private JComboBox curveSelector;
	private JButton clearAll;
	
	private FractalCanvas drawingArea;
	private JPanel navigation;
	
	public FractalPanel() {
		setName("Fractals");
								
		layoutButtons();
		drawingArea = new FractalCanvas();		
		
		this.setLayout(new BorderLayout(hGap, vGap));
		this.add(navigation, BorderLayout.WEST);
		this.add(drawingArea, BorderLayout.CENTER);		
		
		draw.addActionListener(this);
		clearAll.addActionListener(this);		
	}

	private void layoutButtons() {
		draw = new JButton("Draw");
		curveSelector = new JComboBox(FractalTypes.values());
		clearAll = new JButton("Clear all");
		
		navigation = new JPanel();
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridy = 0;
		navigation.add(draw, gbc);
		
		gbc.gridy++;
		navigation.add(curveSelector, gbc);
		
		gbc.gridy++;
		navigation.add(clearAll, gbc);
		
		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;
		navigation.add(new JPanel(), gbc);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == draw){
			drawingArea.drawCurve((FractalTypes) curveSelector.getSelectedItem());
		}else if(source == clearAll){
			drawingArea.clearAll();			
		}else{
			System.err.println("An event from an unhandled source was sent." + source.toString());
		}			
	}

}
