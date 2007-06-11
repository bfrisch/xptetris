package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

import javax.swing.*;

public class XPnColorChooser extends JColorChooser {
	private static final long serialVersionUID = 7242367248846643197L;

	XPnColorChooser() {
		super();
		
	}
	
	XPnColorChooser(String borderText) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(borderText));
	}
}
