/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.background;

import static ch.ntb.cv.background.BackgroundRemoval.Channel.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JPanel;



@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel implements ActionListener{

    final int vGap = 5;
    final int hGap = 10;
    
    private JButton bShowForeground;
    private JButton bShowBackground;
    private JButton bShowComposite;
    private JButton bShowHue;
    private JButton bShowSaturation;
    private JButton bShowLuminance;
    private JButton bShowAlpha;
    private JButton bClearAll;
    
    BufferedImage biForeground = null;
    BufferedImage biBackground = null;
    BufferedImage biComposite = null;
    BufferedImage biHSV_Channel = null;
    
    BackgroundRemoval br;
    
    private BackgroundCanvas drawingArea;
    private JPanel navigation;


    public BackgroundPanel() {
            setName("Background removal");
            
            layoutButtons();

            drawingArea = new BackgroundCanvas(255, 255, 255);
            
            try {
                biForeground = ImageIO.read(new File("ressources/green_screen_trex.png"));
                biBackground = ImageIO.read(new File("ressources/shopping_center.png"));
                biComposite = new BufferedImage(biForeground.getWidth(),biForeground.getHeight(), BufferedImage.TYPE_INT_RGB);
                
                br = new BackgroundRemoval(biForeground, biBackground);
                calculateComposite();

            } catch (IOException e) {
                System.err.println("Image(s) not found");
            }
            
            

            this.setLayout(new BorderLayout(hGap, vGap));		
            this.add(navigation, BorderLayout.WEST);
            this.add(drawingArea, BorderLayout.CENTER);
    }
    
    private void layoutButtons() {
		bShowForeground = new JButton("Show Foreground");
                bShowBackground = new JButton("Show Background");
                bShowComposite = new JButton("Show Composite");
                
                bShowHue = new JButton("Show Hue");
                bShowSaturation = new JButton("Show Saturation");
                bShowLuminance = new JButton("Show Luminance");
                
                bShowAlpha = new JButton("Show Alpha");
                
		bClearAll = new JButton("Clear all");
		
		navigation = new JPanel();
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		navigation.add(bShowForeground, gbc);
		bShowForeground.addActionListener(this);	
                
                gbc.gridy++;
                navigation.add(bShowBackground, gbc);
		bShowBackground.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bShowComposite, gbc);
		bShowComposite.addActionListener(this);
                
                gbc.gridy+=10;
                navigation.add(bShowHue, gbc);
		bShowHue.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bShowSaturation, gbc);
		bShowSaturation.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bShowLuminance, gbc);
		bShowLuminance.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bShowAlpha, gbc);
		bShowAlpha.addActionListener(this);
                
                gbc.gridy+=10;
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
		if(source == bShowForeground){
                    drawingArea.setImage(biForeground);
                }else if(source == bShowBackground){
                    drawingArea.setImage(biBackground);	
		}else if(source == bShowComposite){
                    calculateComposite();
                    drawingArea.setImage(biComposite);
                
                }else if(source == bShowHue){
                    drawingArea.setImage(br.getHsvChannel(HUE));
                }else if(source == bShowSaturation){
                    drawingArea.setImage(br.getHsvChannel(SATURATION));
                }else if(source == bShowLuminance){
                    drawingArea.setImage(br.getHsvChannel(VALUE));
                }else if(source == bShowAlpha){
                    drawingArea.setImage(br.getHsvChannel(ALPHA));
		}else if(source == bClearAll){
			drawingArea.clearAll();	
		}else{
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
    }
    
    private void calculateComposite(){
        br.execute();
        biComposite = br.biComposite;
    }
    
}
