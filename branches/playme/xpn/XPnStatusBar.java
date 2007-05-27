package xpn;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

public class XPnStatusBar extends XPnCenteredLabel {
	private static final long serialVersionUID = 1L;
	
    /** Creates a new instance of StatusBar */
    public XPnStatusBar() {
        super();
    }
    
    public XPnStatusBar(String initialText) {
        super(initialText);
    }
    
    public void setMessage(String message) {
        setText(" "+message);        
    }        
}