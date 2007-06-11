package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class XPnColorChooserDialog extends XPnDialog{
	private static final long serialVersionUID = -5121404171888674328L;
	
	Color selected = null;
	  public XPnColorChooserDialog(JFrame owner) {
		  super(owner, true);

	    final XPnColorChooser colorChooser = new XPnColorChooser("Choose Color");

	    javax.swing.colorchooser.ColorSelectionModel model = colorChooser.getSelectionModel();
	    ChangeListener changeListener = new ChangeListener() {
	      public void stateChanged(ChangeEvent changeEvent) {
	        Color newForegroundColor = colorChooser.getColor();
	        selected = newForegroundColor;
	      }
	    };
	    model.addChangeListener(changeListener);
	    add(colorChooser, BorderLayout.CENTER);
	  }
	  
	  public Color getSelectedColor() {
		  return selected;
	  }
}
