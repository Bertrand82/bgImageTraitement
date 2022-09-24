package bgImageTraitement;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class PanelImage extends JPanel implements MouseWheelListener {

	private Cursor cursorCrossHair = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	private Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private BufferedImage bufferedImage;
	private double zoom = 1.0;
	int wIni = PropertiesApplication.w;
	int hIni = PropertiesApplication.h;
	private Dimension dimIni = new Dimension(wIni, hIni);
	private JPopupMenu popupMenu = new JPopupMenu();
	private JCheckBoxMenuItem[] checkboxPoints = new JCheckBoxMenuItem[3];
	private MetaInfos mergeInfos;
	public PanelImage() {
		this(new MetaInfos());
	}
	public PanelImage(MetaInfos mergeInfos) {
		this.mergeInfos=mergeInfos;
		this.addMouseWheelListener(this);
		this.setDimIni();
		JMenuItem menuSelect = new JMenuItem("Select");
		menuSelect.addActionListener((event) -> {
			System.out.println("select");
		});
		popupMenu.add(menuSelect);
		for(int i=0; i<checkboxPoints.length;i++) {
			checkboxPoints[i]= new JCheckBoxMenuItem("Point merge "+(i+1));
			popupMenu.add(checkboxPoints[i]);
			checkboxPoints[i].addActionListener((item)->{processMergePoint(item);});
			popupMenu.add(checkboxPoints[i]);
		}
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				System.out.println("mouse Pressed  isPopupTrigger: " + event.isPopupTrigger());
				if (event.isPopupTrigger()) {
					displayPopupMenu(event);
				}
			};

			public void mouseClicked(MouseEvent e) {
					mouseClicked2(e);
			}

			

			public void mouseReleased(MouseEvent event) {
				System.out.println("mouse mouseReleased " + " isPopupTrigger " + event.isPopupTrigger());
				if (event.isPopupTrigger()) {
					displayPopupMenu(event);
				}
			};
		});
		
	}

	private void processMergePoint(ActionEvent item) {
		JCheckBoxMenuItem cbmi = (JCheckBoxMenuItem) item.getSource();
		System.out.println("processPointA  "+cbmi.getText()+"  "+cbmi.isSelected());
		if(cbmi.isSelected()) {
			this.setCursor(cursorCrossHair);
			deSelectAllCheckBox();
			cbmi.setSelected(true);
		}else {
			this.setCursor(cursorDefault);
		}
	}
	
	private void deSelectAllCheckBox() {
		for( int i =0;i<checkboxPoints.length;i++) {
			checkboxPoints[i].setSelected(false);
		}
	}

	

	private void displayPopupMenu(MouseEvent e) {
		System.out.println("Show popup");
		this.popupMenu.show(this,e.getX()+10,e.getY()+10);
	}
	
	private void mouseClicked2(MouseEvent e) {
		int x  = (int) (e.getX()/zoom);
		int y  = (int) (e.getY()/zoom);
		int i = getMergePointNumber();
		if (i >=0) {
			this.mergeInfos.getPoints()[i]= new Point(x,y);
		}
		System.out.println("mouse mouseClicked   X: " + x + "  Y: " + y + " zoom "+zoom);
	
	}

	private int getMergePointNumber() {
		for(int i=0;i<checkboxPoints.length;i++) {
			JCheckBoxMenuItem cbmi = checkboxPoints[i];
			if (cbmi.isSelected()) {
				return i;
			}
		}
		return -1;
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
		zoom = ((double) this.getHeight()) / (double) (bufferedImage2.getHeight());

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
