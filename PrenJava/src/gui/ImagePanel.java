package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
    
    public ImagePanel() {
        super();
        this.setSize(640,360);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 10, 10, null);
    }
	
}
