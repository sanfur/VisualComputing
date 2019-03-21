/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.bresenham;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class BresenhamDrawingPanel extends JPanel implements ActionListener{

    final int vGap = 5;
    final int hGap = 10;
    final int gridsize = 4;
    final int mapWidth = 200;
    final int mapHeight = 80;
    
    
    private JButton bDrawLine;
    private JButton bDrawExample;
    private JButton bClearAll;
            
    private BresenhamDrawingCanvas drawingArea;
    private JPanel navigation;

    
    public BresenhamDrawingPanel() {
            setName("Bresenham drawing");
            
            layoutButtons();

            drawingArea = new BresenhamDrawingCanvas();

            this.setLayout(new BorderLayout(hGap, vGap));		
            this.add(navigation, BorderLayout.WEST);
            this.add(drawingArea, BorderLayout.CENTER);

    }
    
    private void layoutButtons() {
		bDrawLine = new JButton("Draw line");
                bDrawExample = new JButton("Draw example");
		bClearAll = new JButton("Clear all");
		
		navigation = new JPanel();
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		navigation.add(bDrawLine, gbc);
		bDrawLine.addActionListener(this);	
                
                gbc.gridy++;
                navigation.add(bDrawExample, gbc);
		bDrawExample.addActionListener(this);
		
                gbc.gridy++;
                navigation.add(bClearAll, gbc);
		bClearAll.addActionListener(this);

		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;
		navigation.add(new JPanel(), gbc);
	}
    

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
		if(source == bDrawLine){
                    drawingArea.setState(1);
                }else if(source == bDrawExample){
                    drawingArea.drawMyName();
		}else if(source == bClearAll){
                    drawingArea.clear();
		}else{
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
    }
}
