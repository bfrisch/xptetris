package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

public class XPnResourceLoader {	
	public static java.io.InputStream getResourceAsStream(String resLoc) {
		return ClassLoader.getSystemResourceAsStream(resLoc);
	}
	
	public static java.net.URL getResource(String resLoc) {
		return ClassLoader.getSystemResource(resLoc);
	}
}
