package ch.ntb.cv.background;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BackgroundCanvas extends JPanel {

    BufferedImage currentBuffer = null;

    public BackgroundCanvas(int red, int green, int blue) {
        super();
        setBackground(Color.white);

    }

    public void setImage(BufferedImage img) {
        currentBuffer = img;
        this.repaint();
    }

    public void clearAll() {
        currentBuffer = null;
        this.setBackground(Color.white);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (currentBuffer != null) {
            g.drawImage(currentBuffer, 0, 0, null);
        }

    }

}
