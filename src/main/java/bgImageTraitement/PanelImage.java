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
	int w =PropertiesApplication.w;
	int h = PropertiesApplication.h;
	private Dimension dim = new Dimension(w,h);
	public PanelImage() {
		this.addMouseWheelListener(this);
		this.setDim();
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

	public BufferedImage setBufferedImage(BufferedImage bufferedImage2) {
		zoom = ((double) this.h)/ (double) (bufferedImage2.getHeight());
		this.w =(int)( zoom * bufferedImage2.getWidth());
		this.setDim();
		BufferedImage old = this.bufferedImage;
		this.bufferedImage = bufferedImage2;
		return old;
	}

	private void setDim() {
		 dim = new Dimension(w,h);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);
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

	public void convertToGrey() {
		System.out.println("ConvertToGrey");
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);

		BufferedImage bufferedImageGrey = new BufferedImage(this.bufferedImage.getWidth(),
				this.bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
		bufferedImageGrey = op.filter(bufferedImage, null);
		this.bufferedImage = bufferedImageGrey;
	}

	public Histogram getHistogram() {
		return new Histogram(this.bufferedImage);
		
	}

	

}
