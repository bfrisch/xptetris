package res;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

public class ResourceLoader extends xpn.XPnResourceLoader {
	public java.io.InputStream getResourceAsStream(String resLoc) {
		return getResourceAsStream(resLoc, ResourceLoader.class);
	}
	
	public java.net.URL getResource(String resLoc) {
		return getResource(resLoc, ResourceLoader.class);
	}
}
