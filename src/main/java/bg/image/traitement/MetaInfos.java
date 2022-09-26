package bg.image.traitement;

import java.awt.Point;

public class MetaInfos {
	private Point[] points =  new Point[3];
	private String fileNameImage;
	public Point[] getPoints() {
		return points;
	}
	public void setPoints(Point[] points) {
		this.points = points;
	}
	public String getFileNameImage() {
		return fileNameImage;
	}
	public void setFileNameImage(String fileNameImage) {
		this.fileNameImage = fileNameImage;
	}
	public void save() {
		System.out.println("save no implemented yet");
	}
	
}
