package bg.image.traitement.filtre;

import java.awt.image.BufferedImage;

public class FiltreSeuilRedisplay implements IFiltre {

	int seuilMin = 0;
	int seuilMax = 0xff;

	boolean colorR = false;
	boolean colorV = false;
	boolean colorB = false;

	public FiltreSeuilRedisplay(int min, int max, boolean colorR, boolean colorV, boolean colorB) {
		this.seuilMin = min;
		this.seuilMax = max;
		this.colorR = colorR;
		this.colorB = colorB;
		this.colorV = colorV;

	}

	@Override
	public int getPixel(int y, int x, BufferedImage buffIn) {
		int pixel = buffIn.getRGB(y, x);
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		
		if (colorR) {
			red=filtre(red);
		}
		if (colorV) {
			green = filtre(green);
		}
		if (colorB) {
			blue = filtre(blue);
		}
		
		int p = (red << 16) | (green << 8) | blue;
		return p;
	}

	private int filtre(int red) {
		if (red > seuilMax) {
			red = 0xff;
		}
		else if (red < seuilMin) {
			red = 0;
		}
		else {
			red = (int) ((double) ((red - seuilMin)*(0xff))/(seuilMax- seuilMin));
		}
		return red;
	}

}
