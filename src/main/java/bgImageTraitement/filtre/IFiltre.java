package bgImageTraitement.filtre;

import java.awt.image.BufferedImage;

public interface IFiltre {

	public int getPixel(int y, int x, BufferedImage buffIn);

	

}
