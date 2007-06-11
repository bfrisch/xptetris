package xpn;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 6
 */

public class XPnStringBundle {
	private static final String BUNDLE_NAME = "strings.strings";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private XPnStringBundle() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
