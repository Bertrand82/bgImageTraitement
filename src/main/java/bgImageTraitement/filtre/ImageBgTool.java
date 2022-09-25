package bgImageTraitement.filtre;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBgTool {

	
	public static BufferedImage getEdge(BufferedImage image) {
		 float[] SHARPEN3x3 = {
                 1.f, 0.f, -1.f,
                 2.f, 0.0f, -2.f,
                 1.f, 0.f, -1.f};
		 float[] SHARPEN5x5 = {
				 -5f  ,-4f , 0f,   4f ,  5f,
				 -8f ,-10f , 0f,  10f,   8f,
				 -10f, -20f,  0f,  20f,  10f,
				 -8f, -10f,  0f,  10f ,  8f,
				 -5f,  -4f,  0f,   4f,   5f
		 };
		BufferedImage srcbimg = convertToGrey(image);
		BufferedImage dstbimg = new BufferedImage(srcbimg.getWidth(), srcbimg.getHeight(),BufferedImage.TYPE_INT_RGB);
		Kernel kernel = new Kernel(3,3,normalize3(SHARPEN3x3));
		ConvolveOp cop = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP, null);
		cop.filter(srcbimg,dstbimg);
		return dstbimg;
	}
	
	private static float[] normalize3(float[] matrice) {
		float norme = normeMatrice(matrice);
		float[] mNormalize = new float[matrice.length];
		for(int i =0;i<matrice.length;i++) {
			mNormalize[i]=matrice[i]/norme;
		}
		float norme2 = normeMatrice(mNormalize);
		System.out.println("normalize norme : "+norme+"  "+norme2+"     size2 "+matrice.length);
		return mNormalize;
	}

	private static float normeMatrice(float[] matrice) {
		float s2=0;
		for(int i =0;i<matrice.length;i++) {
			s2+=(matrice[i]*matrice[i]);
		}
		double n = Math.sqrt(s2);
		return (float) n;
	}



	public static BufferedImage blur2 (BufferedImage srcbimg,int size) {
		float[] filter = new float[size*size];
		 for (int i = 0; i < filter.length; i++) {
		        filter[i] = 1f;
		    }
		
		Kernel kernel = new Kernel(size,size,normalize3(filter));
		ConvolveOp cop = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP, null);
		BufferedImage dstbimg = new BufferedImage(srcbimg.getWidth(), srcbimg.getHeight(),BufferedImage.TYPE_INT_RGB);
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
	
	public static BufferedImage convertToRgb(BufferedImage buf) {
		final int width = buf.getWidth();
	    final int height = buf.getHeight();
	    BufferedImage newRGB = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    newRGB .createGraphics().drawImage(buf, 0, 0, width, height, null);
	    return newRGB;
	    
	}
	
	public static BufferedImage readImage(File fileImage)  {
		try {
			BufferedImage image = ImageIO.read(fileImage);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveImageToFile(BufferedImage bufferedImage,File fileImage)  {
		try {
			String type = getTypeFromFile(fileImage.getName());
			ImageIO.write(bufferedImage, type, fileImage);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] s){
		test("zzozoz.jpg");
	}
	private static void test(String name) {
		System.out.println("  "+name +"    "+getTypeFromFile(name));
	}

	private static String getTypeFromFile(String fileImageNAme) {
		
		String suffix= fileImageNAme.substring(fileImageNAme.lastIndexOf("."));
		
		return "jpg";
	}
	
	

}
