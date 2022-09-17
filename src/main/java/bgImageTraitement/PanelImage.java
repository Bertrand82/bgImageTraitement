package bgImageTraitement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PanelImage extends JPanel implements MouseWheelListener{

	private BufferedImage bufferedImage;
	private double zoom = 1.0;
	
	public PanelImage(){
		this.addMouseWheelListener(this);
	}
	
	public void paint(Graphics g1) {
		super.paintComponents(g1);
		Graphics2D g2 = (Graphics2D) g1;
	   
		if (bufferedImage == null) {
		}else {	
			g2.setColor(Color.white);
			int w = this.getWidth();
			int h = this.getHeight();
			g2.fillRect(0,0,w, h);
			g2.scale(zoom, zoom);
			g2.drawImage(bufferedImage, 0, 0, null);
		}
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage2) {
		zoom = 300.0d /bufferedImage2.getWidth();
		this.bufferedImage = bufferedImage2;
		
	}

	@Override
	   public void mouseWheelMoved(MouseWheelEvent e) {
	       int notches = e.getWheelRotation();
	       if (notches < 0) {
	          zoom = zoom*1.1;
	       } else {
	    	   zoom = zoom*0.9;
	       }
	       repaint();
	   
	}
	
	
}
