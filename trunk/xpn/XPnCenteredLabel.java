package xpn;
import javax.swing.JLabel;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */


public class XPnCenteredLabel extends JLabel {
	private static final long serialVersionUID = 4596263439400590191L;

	/** Creates a new instance of StatusBar */
    public XPnCenteredLabel(String initalText) {
        super(initalText);
        super.setPreferredSize(new java.awt.Dimension(100, 16));
        super.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    public XPnCenteredLabel() {
        super();
        super.setPreferredSize(new java.awt.Dimension(100, 16));
        super.setAlignmentX(CENTER_ALIGNMENT);
    }
}
