package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

public abstract class XPnResourceLoader {
	public abstract java.io.InputStream getResourceAsStream(String resLoc);
	
	public abstract java.net.URL getResource(String resLoc);
	
	protected static java.io.InputStream getResourceAsStream(String resLoc, Class classIns) {
		return classIns.getResourceAsStream(resLoc);
	}
	
	protected static java.net.URL getResource(String resLoc, Class classIns) {
		return classIns.getResource(resLoc);
	}
}
