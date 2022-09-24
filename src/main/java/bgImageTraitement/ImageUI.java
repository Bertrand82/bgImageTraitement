package bgImageTraitement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import bgImageTraitement.filtre.FiltreInverseColor;
import bgImageTraitement.filtre.FiltreSeuilRedisplay;
import bgImageTraitement.filtre.ImageBgTool;
import bgImageTraitement.filtre.ImageFiltre;

public class ImageUI {
	File fileImageTest = new File("images/maison.jpg");
	List<BufferedImage> listBufferedImage = new ArrayList<BufferedImage>();
	PanelImage panelImage = new PanelImage();
	PanelProcessHistogram panelHistogram = new PanelProcessHistogram();
	final JFileChooser fc = new JFileChooser();

	public static void main(String[] a) {
		System.err.println(" start bgImageTraitement");
		new ImageUI();
	}

	public ImageUI() {
		JFrame jframe = new JFrame();
		JPanel panelGlobal = new JPanel(new BorderLayout());

		panelGlobal.add(createMenuBar(), BorderLayout.NORTH);
		panelGlobal.add(panelImage, BorderLayout.CENTER);
		panelGlobal.add(panelHistogram, BorderLayout.EAST);

		jframe.add(panelGlobal);
		jframe.setTitle("Bg Traitement Image");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.pack();
		jframe.setVisible(true);
		loadImage(fileImageTest);
		jframe.pack();
	}

	/* Methode de construction de la barre de menu */
	private JMenuBar createMenuBar() {

		// La barre de menu à proprement parler
		JMenuBar menuBar = new JMenuBar();

		// Définition du menu déroulant "File" et de son contenu
		JMenu mnuFile = new JMenu("File");
		mnuFile.setMnemonic('F');

		mnuFile.addSeparator();

		JMenuItem mnuOpenFile = new JMenuItem("Open File ...");
		mnuFile.add(mnuOpenFile);
		mnuOpenFile.addActionListener((event) -> {
			chooseImage();
		});

		JMenuItem mnuSaveFile = new JMenuItem("Save File ...");
		mnuFile.add(mnuSaveFile);
		mnuSaveFile.addActionListener((event) -> {
			System.out.println("save file No implemented " + event);
		});

		JMenuItem mnuSaveFileAs = new JMenuItem("Save File As ...");
		mnuFile.add(mnuSaveFileAs);
		mnuSaveFile.addActionListener((event) -> {
			saveAs();
		});

		mnuFile.addSeparator();

		JMenuItem mnuExit = new JMenuItem("Exit");
		mnuFile.add(mnuExit);
		mnuExit.addActionListener((event) -> {
			System.exit(0);
		});
		menuBar.add(mnuFile);

		// Définition du menu déroulant "Edit" et de son contenu
		JMenu mnuEdit = new JMenu("Edit");
		mnuEdit.setMnemonic('E');

		JMenuItem mnuUndo = new JMenuItem("Undo");
		mnuUndo.setMnemonic('U');
		mnuUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		mnuUndo.addActionListener((event) -> {
			undo();
		});
		mnuEdit.add(mnuUndo);

		JMenuItem mnuRedo = new JMenuItem("Redo");
		mnuRedo.setMnemonic('R');
		mnuRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		mnuEdit.add(mnuRedo);
		mnuRedo.addActionListener((event) -> {
			redo();
		});

		mnuEdit.addSeparator();

		JMenuItem mnuCopy = new JMenuItem("Copy");
		mnuCopy.setMnemonic('C');
		mnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
		mnuEdit.add(mnuCopy);

		JMenuItem mnuCut = new JMenuItem("Cut");
		mnuCut.setMnemonic('t');
		mnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		mnuEdit.add(mnuCut);

		JMenuItem mnuPaste = new JMenuItem("Paste");
		mnuPaste.setMnemonic('P');
		mnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		mnuEdit.add(mnuPaste);

		menuBar.add(mnuEdit);

		// Définition du menu déroulant "Edit" et de son contenu
		JMenuItem mnuFlipFlop = new JMenuItem("Flip Flop");
		mnuFlipFlop.addActionListener((event) -> flipFlop());
		menuBar.add(mnuFlipFlop);
		// Définition du menu déroulant "Edit" et de son contenu
		JMenuItem mnuToRGB = new JMenuItem("To RGB");
		mnuToRGB.addActionListener((event) -> toRGB());
		mnuEdit.setMnemonic('R');
		menuBar.add(mnuToRGB);

		JMenuItem mnuToGrey = new JMenuItem("To Grey");
		mnuToGrey.addActionListener((event) -> toGrey());
		mnuEdit.setMnemonic('R');
		menuBar.add(mnuToGrey);
		// Définition du menu déroulant "Edit" et de son contenu
		JMenuItem mnuHisto = new JMenuItem("Histo");
		mnuHisto.addActionListener((event) -> processHistogram());
		mnuEdit.setMnemonic('R');
		menuBar.add(mnuHisto);
		JMenuItem mnuBlur = new JMenuItem("Blur");
		mnuBlur.addActionListener((event) -> blur());
		mnuBlur.setMnemonic('R');
		menuBar.add(mnuBlur);
		JMenuItem mnuEdge = new JMenuItem("Edge");
		mnuEdge.addActionListener((event) -> edge());
		mnuEdit.setMnemonic('R');
		menuBar.add(mnuEdge);
		JMenuItem mnuProcess = new JMenuItem("Process");
		mnuProcess.addActionListener((event) -> process());
		mnuEdit.setMnemonic('R');
		menuBar.add(mnuProcess);

		// Définition du menu déroulant "Help" et de son contenu
		JMenu mnuHelp = new JMenu("Help");
		mnuHelp.setMnemonic('H');

		menuBar.add(mnuHelp);

		return menuBar;
	}

	private void flipFlop() {
		FiltreInverseColor filtreInverscolor = new FiltreInverseColor();
		BufferedImage b1 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreInverscolor);
		this.setBufferedImage(b1);
		
	}

	private void saveAs() {
		try {
			System.out.println("save as ");
			File outputfile = new File("transformed.jpg");
			ImageIO.write(this.panelImage.getBufferedImage(), "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void chooseImage() {
		int returnVal = fc.showOpenDialog(this.panelImage);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			log("Open command ");
			this.loadImage(file);
		} else {
			log("Open command cancelled by user");
		}
	}

	private void log(String s) {
		System.out.println("log " + s);
	}

	private void toGrey() {
		BufferedImage b = ImageBgTool.convertToGrey(this.getBufferedImage());
		this.setBufferedImage(b);
	}

	private void toRGB() {
		BufferedImage b = ImageBgTool.convertToRgb(this.getBufferedImage());
		this.setBufferedImage(b);
	}

	private void processHistogram() {
		Histogram histogramme = this.panelImage.getHistogram();
		this.panelHistogram.setHistogram(histogramme);
		this.panelHistogram.repaint();
		System.out.println(" histo ::: " + histogramme.toStringDetail());

	}

	private void process() {
		System.out.println("------------->process");
		FiltreSeuilRedisplay filtreSeuil1 = new FiltreSeuilRedisplay(30, 0xd8, true, true, false);
		BufferedImage b1 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreSeuil1);
		setBufferedImage(b1);
		FiltreSeuilRedisplay filtreSeuil2 = new FiltreSeuilRedisplay(30, 0xa0, false, false, true);
		BufferedImage b2 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreSeuil2);
		setBufferedImage(b2);
		this.panelImage.repaint();
	}

	private void loadImage(File f) {
		System.out.println("Process ");
		BufferedImage bufferedImage = ImageBgTool.readImage(f);
		BufferedImage bufferedImage2 = ImageBgTool.convertToRgb(bufferedImage);
		setBufferedImage(bufferedImage2);
		panelImage.repaint();
	}

	private void edge() {
		System.out.println("edge ");

		BufferedImage srcbimg = this.panelImage.getBufferedImage();
		BufferedImage dstbimg = ImageBgTool.getEdge(srcbimg);
		setBufferedImage(dstbimg);
		panelImage.repaint();
	}

	private void blur() {
		System.out.println("blur ");
		BufferedImage srcbimg = this.panelImage.getBufferedImage();
		BufferedImage dstbimg = ImageBgTool.blur2(srcbimg,3);
		setBufferedImage(dstbimg);
		panelImage.repaint();
	}

	private BufferedImage getBufferedImage() {
		return this.panelImage.getBufferedImage();
	}

	private void setBufferedImage(BufferedImage buf) {
		this.listBufferedImage.add(buf);
		panelImage.setBufferedImage2(buf);
	}

	private void redo() {
		int i = getOrdreImage(this.panelImage.getBufferedImage());
		if (i >= listBufferedImage.size() - 1) {
			log("No redo");
			return;
		}
		log("redo i : " + (i + 1) + " / " + listBufferedImage.size());
		BufferedImage b_next = this.listBufferedImage.get(i + 1);
		panelImage.setBufferedImage2(b_next);
	}

	private void undo() {

		int i = getOrdreImage(this.panelImage.getBufferedImage());
		if (i <= 0) {
			log("no Undo");
			return;
		}
		log("undo  i :" + (i - 1) + " / " + listBufferedImage.size());
		BufferedImage b_Z_1 = this.listBufferedImage.get(i - 1);
		panelImage.setBufferedImage2(b_Z_1);

	}

	private int getOrdreImage(BufferedImage bufferedImage) {
		int i = 0;
		for (BufferedImage image : listBufferedImage) {
			if (image.equals(bufferedImage)) {
				return i;
			}
			i++;
		}
		return -1;
	}

}
