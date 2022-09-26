package bg.image.traitement.filtre;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageFiltre extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static BufferedImage createBufferedImage(int width, int height) {
		BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return buff;
	}

	public static BufferedImage createBufferedImage(BufferedImage buffIn, IFiltre filtre) {
		BufferedImage buffOut = new BufferedImage(buffIn.getWidth(), buffIn.getHeight(), buffIn.getType());
		for (int x = 0; x < buffIn.getHeight(); x++) {
			for (int y = 0; y < buffIn.getWidth(); y++) {
				int pixelNew = filtre.getPixel(y,x ,buffIn);
				buffOut.setRGB(y, x,pixelNew);
			}
		}
		return buffOut;
	}

}
