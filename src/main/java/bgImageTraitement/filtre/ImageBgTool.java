package bgImageTraitement.filtre;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageBgTool {

	
	public static BufferedImage getEdge(BufferedImage image) {
		 float[] SHARPEN3x3 = {
                 1.f, 0.f, -1.f,
                 2.f, 0.0f, -2.f,
                 1.f, 0.f, -1.f};
		BufferedImage srcbimg = convertToGrey(image);
		BufferedImage dstbimg = new BufferedImage(srcbimg.getWidth(), srcbimg.getHeight(),BufferedImage.TYPE_INT_RGB);
		double[][] FILTER_SOBEL_V = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
		Kernel kernel = new Kernel(3,3,SHARPEN3x3);
		ConvolveOp cop = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP, null);
		cop.filter(srcbimg,dstbimg);
		return dstbimg;
	}
	
	public static BufferedImage  convertToGrey(BufferedImage image) {
		System.out.println("ConvertToGrey");
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);

		BufferedImage bufferedImageGrey = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
		bufferedImageGrey = op.filter(image, null);
		return bufferedImageGrey;
	}
}
