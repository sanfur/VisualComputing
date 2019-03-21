package ch.ntb.cv.colors;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class ColorPanel extends JPanel implements ChangeListener{
	
	final int vGap = 15;
	final int hGap = 10;
	
	private JSlider r= new JSlider(0,255,255);
	private ColorCanvas drawingArea;
	private JPanel navigation;
	
	public ColorPanel() {
		setName("Colors");		
		
		addChangeListeners();
		
		navigation = new JPanel();
		layoutSliders();
		
		drawingArea = new ColorCanvas(255, 255, 255);
		
		this.setLayout(new BorderLayout(hGap, vGap));		
		this.add(navigation, BorderLayout.WEST);
		this.add(drawingArea, BorderLayout.CENTER);
	}

	private void layoutSliders() {
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		navigation.add(new JLabel("Red"), gbc);
		gbc.gridx++;
		navigation.add(r, gbc);
// Todo		
	}

	private void addChangeListeners() {
		r.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
//ToDo
	
		//drawingArea.setRGB(r.getValue(), g.getValue(), b.getValue());		
	}

}