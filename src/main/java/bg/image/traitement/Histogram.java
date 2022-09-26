package bg.image.traitement;

import java.awt.image.BufferedImage;

public class Histogram {

	public Histogram(BufferedImage bufferedImage) {
		process(bufferedImage);
	}
	
	private int[] histoR = new int[0xff+1];
	private int[] histoV = new int[0xff+1];
	private int[] histoB = new int[0xff+1];
	private int maxR =0;
	private int maxV =0;
	private int maxB =0;
	private int max=0;
	
	private void process(BufferedImage bufferedImage) {
		 maxR =0;
		 maxV =0;
		 maxB =0;
		
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		int pixel;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				// System.out.println("x,y: " + j + ", " + i);
				pixel = bufferedImage.getRGB(j, i);
				process(pixel);
				// System.out.println("value of K:"+k+ " value of pixel: " + pixel[j][i]);
			}
		}
		max= Math.max(Math.max(maxR, maxV),maxB)+1;
	}
	
	
	public int getMax() {
		return max;
	}


	

	private void process(int pixel) {
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		int r =histoR[red]++;
		int v  = histoV[green]++;
		int b = histoB[blue]++;
		maxR = Math.max(maxR,r);
		maxV = Math.max(maxV, v);
		maxB = Math.max(maxB, b);
		//System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
		// System.out.println(pixel);
	}


	public int[] getHistoR() {
		return histoR;
	}


	public void setHistoR(int[] histoR) {
		this.histoR = histoR;
	}


	public int[] getHistoV() {
		return histoV;
	}


	public void setHistoV(int[] histoV) {
		this.histoV = histoV;
	}


	public int[] getHistoB() {
		return histoB;
	}


	public void setHistoB(int[] histoB) {
		this.histoB = histoB;
	}
    public String  toStringDetail() {
    	String s ="";
    	for (int i=0; i<histoB.length;i++) {
    		s+= " i "+i+" r : "+histoR[i]+"\n";
    	}
    	return s;
    }
}
