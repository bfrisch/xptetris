package xpn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author David Kosbie
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
 */

public class XPnDialog extends JDialog {
	private static final long serialVersionUID = -2898862000185257817L;
	
	private boolean autoShowDialog = true;

public void init() {
  	// override this method to add your buttons, objects, etc
  }

  public void onTimer() { }  

  // This method lives outside the anonymous inner class Runnable
  // so that getClass() returns this class (in fact, the overridden subclass)  
  protected String makeTitle() {
  	return this.getClass().getName();
  }

  public XPnDialog() {
  	super();
  	privateInit();
  }
  
  public XPnDialog(javax.swing.JFrame frame) {
	  	super(frame);
	  	privateInit();
	  }
  
  public XPnDialog(javax.swing.JFrame frame, boolean modal) {
	  	super(frame, modal);
	  	privateInit();
	  }
  
  private void privateInit() {
  	try {
  	    UIManager.setLookAndFeel(
  	        UIManager.getSystemLookAndFeelClassName());
  	} catch (UnsupportedLookAndFeelException ex) {
  	  System.err.println("Unable to load native look and feel");
  	}
  	catch (Exception e) {};
    Container cp = this.getContentPane();
  	cp.setBackground(Color.white);
	cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    // use invokeLater to allow instance variables (like buttonLabels[])
    // to be initialized in subclasses before calling init() method
 	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			setTitle(makeTitle());
			init();  // override this method!
			pack();
			if (focusComponent != null)
	  			// send key events to this component!
  				focusComponent.requestFocusInWindow();
		  	
			if (autoShowDialog) {
				setVisible(true);
			}
		}
 	});
  }
  
  private XPnComponent focusComponent = null;
  
  public Component add(Component c) {
  	Component result = super.add(c);
  	if (c instanceof XPnComponent) {
  		// the first one added gets the keyboard by default
  		if (focusComponent == null) {
  			focusComponent = (XPnComponent) c;
  			// send key events to this component!
  			focusComponent.requestFocusInWindow();
  		}
  	}
  	return result;
  }

  public void onButtonPressed(String label) {
  	System.out.println("onButtonPressed" + label + "\n");
  }
  
  public void addButtons(String[] buttonLabels) {
  	JPanel buttons = new JPanel();
  	buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
  	for (int i=0; i<buttonLabels.length; i++) {
  		JButton button = new JButton(buttonLabels[i]);
  		button.setFocusable(false);
  		button.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				onButtonPressed(((JButton)e.getSource()).getText());
  				repaint();
  			}
  		});
  		buttons.add(button);
  	}
  	this.getContentPane().add(buttons);
  }
  
  public void dontAutoShowDialog() {
	  autoShowDialog = false;
  }
}