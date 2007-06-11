package xpn;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JComponent;

/**
 * @author David Kosbie
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

public class XPnComponent extends JComponent {
	  private static final long serialVersionUID = 1L;
	  public XPnComponent() {
	    this.addEventListeners();
	    this.setPreferredSize(new Dimension(400,400));
	  } 

	  // override these methods to respond to events
	  public void onTimer() { }
	  public void onMousePressed(int x, int y) { }
	  public void onMouseDragged(int x, int y) { }
	  public void onMouseReleased(int x, int y) { }
	  public void onKeyPressed(KeyEvent e, int keyCode, char keyChar) { }
	  public void onResized() { }
	  public void onFocusGained() { }
	  public void onFocusLost() { }
	  
	  protected void addEventListeners() {
	    // add actions in response to mouse events
	    addMouseListener(new MouseAdapter() {
	      public void mousePressed(MouseEvent e) {
	        onMousePressed(e.getX(),e.getY());
	        repaint();
	      }
	      public void mouseReleased(MouseEvent e) {
	        onMouseReleased(e.getX(),e.getY());
	        repaint();
	      }
	    });
	    
	    // and for mouse motion events
	    addMouseMotionListener(new MouseMotionAdapter() {
	      public void mouseDragged(MouseEvent e) {
	        onMouseDragged(e.getX(),e.getY());
	        repaint();
	      }
	    });
	    
	    // and for keyboard events
	    addKeyListener(new KeyAdapter() {
	      public void keyPressed(KeyEvent e) {
	        onKeyPressed(e,e.getKeyCode(),e.getKeyChar());
	        repaint();
	      }
	    });

	    // and for component events
	    addComponentListener(new ComponentAdapter() {
	      public void componentResized(ComponentEvent e) {
	        onResized();
	        repaint();
	      }
	    });
	    
	    this.addFocusListener(new FocusListener() {
	    	public void focusGained(FocusEvent e) {
	    		onFocusGained();
	    	}
	    	
	    	public void focusLost(FocusEvent e) {
	    		onFocusLost();
	    	}
	    });
	  }
	}
