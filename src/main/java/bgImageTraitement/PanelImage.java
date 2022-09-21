package bgImageTraitement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.swing.JPanel;

public class PanelImage extends JPanel implements MouseWheelListener {

	private BufferedImage bufferedImage;
	private double zoom = 1.0;
	int wIni =PropertiesApplication.w;
	int hIni = PropertiesApplication.h;
	private Dimension dimIni = new Dimension(wIni,hIni);
	public PanelImage() {
		this.addMouseWheelListener(this);
		this.setDimIni();
	}

	public void paint(Graphics g1) {
		super.paintComponents(g1);
		Graphics2D g2 = (Graphics2D) g1;

		if (bufferedImage == null) {
		} else {
			g2.setColor(Color.white);
			int w = this.getWidth();
			int h = this.getHeight();
			g2.fillRect(0, 0, w, h);
			g2.scale(zoom, zoom);
			g2.drawImage(bufferedImage, 0, 0, null);
		}
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage2(BufferedImage bufferedImage2) {
		zoom = ((double) this.getHeight())/ (double) (bufferedImage2.getHeight());
		
		BufferedImage old = this.bufferedImage;
		this.bufferedImage = bufferedImage2;
		repaint();
		
	}

	private void setDimIni() {
		 
		this.setPreferredSize(dimIni);
		this.setMinimumSize(dimIni);
		this.setSize(dimIni);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			zoom = zoom * 1.1;
		} else {
			zoom = zoom * 0.9;
		}
		repaint();

	}

	
	public Histogram getHistogram() {
		return new Histogram(this.bufferedImage);
		
	}

	

}
