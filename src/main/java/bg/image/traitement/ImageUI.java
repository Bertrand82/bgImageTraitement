package bg.image.traitement;

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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import bg.image.traitement.filtre.FiltreInverseColor;
import bg.image.traitement.filtre.FiltreSeuilRedisplay;
import bg.image.traitement.filtre.ImageBgTool;
import bg.image.traitement.filtre.ImageFiltre;

public class ImageUI {
	File fileImageTest = new File("images/maison.jpg");
	List<BufferedImage> listBufferedImage = new ArrayList<BufferedImage>();
	PanelImage panelImage = new PanelImage();
	PanelProcessHistogram panelHistogram = new PanelProcessHistogram();
	JLabel labelLog = new JLabel("");
	final JFileChooser fileChooser = new JFileChooser();

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
		panelGlobal.add(labelLog, BorderLayout.SOUTH);
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
		JMenuBar menuBar2 = new JMenuBar();

		// Définition du menu déroulant "File" et de son contenu
		JMenu mnuFile = new JMenu("File");
		mnuFile.setMnemonic('F');

		mnuFile.addSeparator();

		JMenuItem mnuOpenFile = new JMenuItem("Open Image File ...");
		mnuFile.add(mnuOpenFile);
		mnuOpenFile.addActionListener((event) -> {
			chooseImage();
		});

		JMenuItem mnuSaveFile = new JMenuItem("Save Image File ...");
		mnuFile.add(mnuSaveFile);
		mnuSaveFile.addActionListener((event) -> {
			saveImage();
		});

		JMenuItem mnuSaveFileAs = new JMenuItem("Save Image File As ...");
		mnuFile.add(mnuSaveFileAs);
		mnuSaveFileAs.addActionListener((event) -> {
			saveImageAs();
		});

		mnuFile.addSeparator();

		JMenuItem mnuExit = new JMenuItem("Exit");
		mnuFile.add(mnuExit);
		mnuExit.addActionListener((event) -> {
			System.exit(0);
		});
		menuBar2.add(mnuFile);

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
		menuBar2.add(mnuEdit);

		// Définition du menu déroulant "Edit" et de son contenu
		JMenu mnuActions = new JMenu("Actions");
		menuBar2.add(mnuActions);
		JMenuItem mnuFlipFlop = new JMenuItem("Flip Flop");
		mnuFlipFlop.addActionListener((event) -> flipFlop());
		mnuActions.add(mnuFlipFlop);
		// Définition du menu déroulant "Edit" et de son contenu
		JMenuItem mnuToRGB = new JMenuItem("To RGB");
		mnuToRGB.addActionListener((event) -> toRGB());
		mnuEdit.setMnemonic('R');
		mnuActions.add(mnuToRGB);

		JMenuItem mnuToGrey = new JMenuItem("To Grey");
		mnuToGrey.addActionListener((event) -> toGrey());
		mnuEdit.setMnemonic('R');
		mnuActions.add(mnuToGrey);
		// Définition du menu déroulant "Edit" et de son contenu
		JMenuItem mnuHisto = new JMenuItem("Histo");
		mnuHisto.addActionListener((event) -> processHistogram());
		menuBar2.add(mnuHisto);
		JMenuItem mnuBlur = new JMenuItem("Blur");
		mnuBlur.addActionListener((event) -> blur());
		mnuBlur.setMnemonic('R');
		mnuActions.add(mnuBlur);
		JMenuItem mnuEdge = new JMenuItem("Edge");
		mnuEdge.addActionListener((event) -> edge());
		mnuActions.add(mnuEdge);
		
		JMenuItem mnuProcess = new JMenuItem("Process");
		mnuProcess.addActionListener((event) -> process());
		menuBar2.add(mnuProcess);

		// Définition du menu déroulant "Help" et de son contenu
		JMenuItem mnuMerge2 = new JMenuItem("merge");	
		mnuMerge2.addActionListener((event) ->merge2());
		menuBar2.add(mnuMerge2);
		
		JMenu mnuHelp = new JMenu("Help");		
		menuBar2.add(mnuHelp);

		return menuBar2;
	}

	private void merge2() {
		System.err.println("pouer merge");
		this.log("merge");
	}

	private void saveImage() {
		log("saveImage ");
		if (fileImageFrom != null) {
			saveImage(fileImageFrom);
		}
	}

	private void saveImage(File file) {
		log("saveImage " + file.getName());
		ImageBgTool.saveImageToFile( getBufferedImage(),file);
	}

	private void flipFlop() {
		log("flipflop");
		FiltreInverseColor filtreInverscolor = new FiltreInverseColor();
		BufferedImage b1 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreInverscolor);
		this.setBufferedImage(b1);

	}

	private void saveImageAs() {
		try {
			int returnValue = fileChooser.showOpenDialog(this.panelImage);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				log("save image as " + fileToSave.getName());
				ImageBgTool.saveImageToFile(getBufferedImage(), fileToSave);
			}
		} catch (Exception e) {
			log("saveAs Exception " + e.getMessage());
			e.printStackTrace();
		}

	}

	private void chooseImage() {
		int returnVal = fileChooser.showOpenDialog(this.panelImage);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			log("Open command ");
			this.loadImage(file);
		} else {
			log("Open command cancelled by user");
		}
	}

	private void log(String s) {
		System.out.println("log " + s);
		this.labelLog.setText(s);
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
		log(" histo ::: " + histogramme.toStringDetail());

	}

	private void process() {
		log("------------->process");
		FiltreSeuilRedisplay filtreSeuil1 = new FiltreSeuilRedisplay(30, 0xd8, true, true, false);
		BufferedImage b1 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreSeuil1);
		setBufferedImage(b1);
		FiltreSeuilRedisplay filtreSeuil2 = new FiltreSeuilRedisplay(30, 0xa0, false, false, true);
		BufferedImage b2 = ImageFiltre.createBufferedImage(this.panelImage.getBufferedImage(), filtreSeuil2);
		setBufferedImage(b2);
		this.panelImage.repaint();
	}

	File fileImageFrom;

	private void loadImage(File f) {
		log("Process loadImage " + f.getName());
		this.fileImageFrom = f;
		BufferedImage bufferedImage = ImageBgTool.readImage(f);
		BufferedImage bufferedImage2 = ImageBgTool.convertToRgb(bufferedImage);
		setBufferedImage(bufferedImage2);
		panelImage.repaint();
		log("Process loadImage " + f.getName()+" done");
	}

	private void edge() {
		log("edge ");

		BufferedImage srcbimg = this.panelImage.getBufferedImage();
		BufferedImage dstbimg = ImageBgTool.getEdge(srcbimg);
		setBufferedImage(dstbimg);
		panelImage.repaint();
	}

	private void blur() {
		log("blur ");
		BufferedImage srcbimg = this.panelImage.getBufferedImage();
		BufferedImage dstbimg = ImageBgTool.blur2(srcbimg, 3);
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
