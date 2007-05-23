package gui;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * @author David Kosbie
 */

class IntroCsComponent extends JComponent {
	private static final long serialVersionUID = 1L;
  public IntroCsComponent() {
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

  public void beep() {
	Toolkit.getDefaultToolkit().beep();
  }

  public void startTimer(final int timerDelay) { 
    // use invokeLater to allow window to become visible before starting
    // to be initialized in subclasses before calling init() method
 	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    new javax.swing.Timer(timerDelay, new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		        onTimer();
		        repaint();
		      }
		    }).start();
		}
 	});
  }  
   
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
  }
}