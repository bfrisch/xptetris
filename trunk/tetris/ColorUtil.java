package tetris;

public class ColorUtil {
	public static java.awt.Color randColor() {
		java.util.Random rand = new java.util.Random();
		
		return new java.awt.Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)); 
	}
}
