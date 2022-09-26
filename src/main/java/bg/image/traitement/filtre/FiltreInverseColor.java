package bg.image.traitement.filtre;

import java.awt.image.BufferedImage;

public class FiltreInverseColor implements IFiltre {

	

	public FiltreInverseColor() {
		
	}

	@Override
	public int getPixel(int y, int x, BufferedImage buffIn) {
		int pixel = buffIn.getRGB(y, x);
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		int r = 255 - red;
		int g = 255 - green;
		int b = 255 - blue;
		int p = (r << 16) | (g << 8) | b;
		return p;
	}

	

}
