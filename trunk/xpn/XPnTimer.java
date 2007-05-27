package xpn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */


public class XPnTimer extends Timer {
	private static final long serialVersionUID = 6063357268542462249L;

	public XPnTimer(final XPnTimerHandler comp) {
		super(1000, new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
			        comp.onTimer();
			      }
			    });
	}
	
	public XPnTimer(final XPnTimerHandler comp, int time) {
		super(time, new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
			        comp.onTimer();
			      }
			    });
	}
	
	public void start(int delay) {
		this.setDelay(delay);
		this.start();
	}
	
	public void start(int delay, int initialDelay) {
		this.setInitialDelay(initialDelay);
		this.setDelay(delay);
		this.start();
	}
}
