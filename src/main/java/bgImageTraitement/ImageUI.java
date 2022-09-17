package bgImageTraitement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ImageUI {
	File fileImageTest = new File("images/maison.jpg");
	ProcessImage processImage = new ProcessImage();
	BufferedImage bufferedImage ;
	PanelImage panelImage = new PanelImage();
	
	public static void main(String[] a) {
		System.err.println(" start bgImageTraitement");
		new ImageUI();
	}
	
	public ImageUI() {
		JFrame jframe = new JFrame();
	    JPanel  panelGlobal= new JPanel(new BorderLayout());
		
		panelGlobal.add(createMenuBar(),BorderLayout.NORTH);
		panelGlobal.add(panelImage,BorderLayout.CENTER);
		Dimension dim = new Dimension(400, 200);
		panelGlobal.setPreferredSize(dim);
		jframe.add(panelGlobal);
		jframe.setTitle("Bg Traitement Image");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.pack();
		jframe.setVisible(true);
	}
	
	
	

    /* Methode de construction de la barre de menu */
    private JMenuBar createMenuBar() {

        // La barre de menu � proprement parler
        JMenuBar menuBar = new JMenuBar();

        // D�finition du menu d�roulant "File" et de son contenu
        JMenu mnuFile = new JMenu( "File" );
        mnuFile.setMnemonic( 'F' );

        JMenuItem mnuNewFile = new JMenuItem( "New File" );
        mnuNewFile.setMnemonic( 'N' );
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem( "Open File ..." );
        mnuFile.add(mnuOpenFile);
        mnuOpenFile.addActionListener((event)->{System.out.println("aaaaaaaaaaaaa "+event);});

        JMenuItem mnuSaveFile = new JMenuItem( "Save File ..." );
        mnuFile.add(mnuSaveFile);
        mnuSaveFile.addActionListener((event)->{System.out.println("bbbbbbbbbbbbbbb "+event);});

        JMenuItem mnuSaveFileAs = new JMenuItem( "Save File As ..." );
         mnuFile.add(mnuSaveFileAs);

        mnuFile.addSeparator();

        JMenuItem mnuExit = new JMenuItem( "Exit" );
          mnuFile.add(mnuExit);
        
        menuBar.add(mnuFile);
        
        // D�finition du menu d�roulant "Edit" et de son contenu
        JMenu mnuEdit = new JMenu( "Edit" );
        mnuEdit.setMnemonic( 'E' );
        
        JMenuItem mnuUndo = new JMenuItem( "Undo" );
        mnuUndo.setMnemonic( 'U' );
        mnuUndo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuUndo);
        
        JMenuItem mnuRedo = new JMenuItem( "Redo" );
         mnuRedo.setMnemonic( 'R' );
        mnuRedo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuRedo);
        
        mnuEdit.addSeparator();
        
        JMenuItem mnuCopy = new JMenuItem( "Copy" );
        mnuCopy.setMnemonic( 'C' );
        mnuCopy.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuCopy);
        
        JMenuItem mnuCut = new JMenuItem( "Cut" );
        mnuCut.setMnemonic( 't' );
        mnuCut.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuCut);
        
        JMenuItem mnuPaste = new JMenuItem( "Paste" );
        mnuPaste.setMnemonic( 'P' );
        mnuPaste.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK) );
        mnuEdit.add(mnuPaste);

        menuBar.add(mnuEdit);
        
        
        // D�finition du menu d�roulant "Edit" et de son contenu
        JMenuItem mnuProcess = new JMenuItem( "Process" );
        mnuProcess.addActionListener((event)->process());
        mnuEdit.setMnemonic( 'R' );
        menuBar.add(mnuProcess);

        // D�finition du menu d�roulant "Help" et de son contenu
        JMenu mnuHelp = new JMenu( "Help" );
        mnuHelp.setMnemonic( 'H' );
        
        menuBar.add( mnuHelp );
        
        return menuBar;
    }
    
    
    private void process()  {
    	System.out.println("Process ");
    	
    	this.bufferedImage=this.processImage.readImage(fileImageTest);
    	panelImage.setBufferedImage(this.bufferedImage);
    	panelImage.repaint();
    }

}