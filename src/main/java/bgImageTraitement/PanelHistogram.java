package bgImageTraitement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class PanelHistogram extends JPanel implements MouseWheelListener{

	Histogram histogram;
	int w =400;
	int h = 200;
	private Dimension dim = new Dimension(w,h);
	double max;
	PanelHistogram(){
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.addMouseWheelListener(this);
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
	}

	private void paint(Graphics2D g2, int[] histoR, Color color) {
		g2.setColor(color);
		for(int i=0;i<histoR.length;i++) {
			int x = 5+i;
			int y =5+ h-(int) (h*histoR[i]/max);
			g2.fillOval(x, y, 3, 3);
		}
		
	}

	public Histogram getHistogram() {
		return histogram;
	}

	public void setHistogram(Histogram histogram) {
		this.histogram = histogram;
		this.max = histogram.getMax();
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			max = max * 1.1;
		} else {
			max = max * 0.9;
		}
		repaint();

	}
	

}
