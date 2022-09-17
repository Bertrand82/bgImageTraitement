package bgImageTraitement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProcessImage  {

	public BufferedImage readImage(File fileImage)  {
		try {
			BufferedImage image = ImageIO.read(fileImage);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

     
}
