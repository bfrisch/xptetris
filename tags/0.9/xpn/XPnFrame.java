package xpn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author David Kosbie
 * @author Benjamin Frisch
 * @version 0.9 Alpha 6
 */

public class XPnFrame extends JFrame {
	private static final long serialVersionUID = 4387726429177330603L;
	
	private boolean autoShowWindow = true;

public void init() {
  	// override this method to add your buttons, objects, etc
  }

  // This method lives outside the anonymous inner class Runnable
  // so that getClass() returns this class (in fact, the overridden subclass)  
  protected String makeTitle() {
  	return this.getClass().getName();
  }

  public XPnFrame() {
  	super();
  	try {
  	    UIManager.setLookAndFeel(
  	        UIManager.getSystemLookAndFeelClassName());
  	} catch (UnsupportedLookAndFeelException ex) {
  	  System.out.println("Unable to load native look and feel");
  	}
  	catch (Exception e) {}
    Container cp = this.getContentPane();
  	cp.setBackground(Color.white);
	cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new java.awt.Dimension(500, 500));
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
		  	
			if (autoShowWindow) {
				setVisible(true);
				setPreferredSize(null);
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
  
  public void dontAutoShowWindow() {
	  autoShowWindow = false;
  }
}