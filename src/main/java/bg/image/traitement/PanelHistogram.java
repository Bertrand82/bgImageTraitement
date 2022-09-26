package bg.image.traitement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelHistogram extends JPanel implements MouseWheelListener{

	Histogram histogram;
	int w =PropertiesApplication.w;
	int h = PropertiesApplication.h;
	private Dimension dim = new Dimension(w,h);
	double maxX =0xff;
	double maxY;
	PanelHistogram(){
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.addMouseWheelListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.red,4));
	}
	
	public void paint(Graphics g1) {
		super.paintComponents(g1);
		Graphics2D g2 = (Graphics2D) g1;

		if (histogram == null) {
			g2.setColor(Color.RED);
			
			g2.drawString("No Histogram", 15, 60);
		} else {
			g2.setColor(Color.white);
			int w = this.getWidth();
			int h = this.getHeight();
			g2.fillRect(0, 0, w, h);
			paint(g2, histogram.getHistoR(),Color.red);
			paint(g2, histogram.getHistoV(),Color.green);
			paint(g2, histogram.getHistoB(),Color.blue);
		}
		paintAxes(g2);
	}

	
	private void paintAxes(Graphics2D g2) {
		int wu =w - 2* offsetX;
		int hu =w - 2* offsetY;
		
		g2.setColor(Color.black);
		g2.drawLine(offsetX, offsetY+ hu, offsetX+wu, offsetY+ hu);
		g2.drawLine(wu+offsetX, offsetY+ hu-5, wu+offsetX, offsetY+ hu+5);
		g2.drawLine(   offsetX, offsetY+ hu-5,    offsetX, offsetY+ hu+5);
	}


	private static final int offsetY = 10;
	private static final int offsetX = 10;
	private void paint(Graphics2D g2, int[] histoR, Color color) {
		g2.setColor(color);
		int wu =w - 2* offsetX;
		int hu =w - 2* offsetY;
		for(int i=0;i<histoR.length;i++) {
			int x = offsetX+(int) (i * wu/maxX);
			int y = h-offsetY-(int)( hu *histoR[i]/(maxY));
			g2.fillOval(x, y, 3, 3);
		}		
	}

	public Histogram getHistogram() {
		return histogram;
	}

	public void setHistogram(Histogram histogram) {
		this.histogram = histogram;
		this.maxY = histogram.getMax();
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			maxY = maxY * 1.1;
		} else {
			maxY = maxY * 0.9;
		}
		repaint();

	}
	

}
