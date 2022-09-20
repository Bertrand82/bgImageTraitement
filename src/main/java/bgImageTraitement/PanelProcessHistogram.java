package bgImageTraitement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelProcessHistogram extends JPanel {
	PanelHistogram panelHistogram = new PanelHistogram();
	int w = PropertiesApplication.w;
	int h = PropertiesApplication.h;
	private Dimension dim = new Dimension(w, h);
	double maxX = 0xff;
	double maxY;

	public PanelProcessHistogram() {
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setLayout(new BorderLayout());
		this.add(panelHistogram, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.red,4));
	}

	public void setHistogram(Histogram histogramme) {
		this.panelHistogram.setHistogram(histogramme);
		
	}

}
