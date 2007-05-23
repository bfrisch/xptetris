package tetris;
import javax.swing.*;

class CenteredLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
    /** Creates a new instance of StatusBar */
    public CenteredLabel(String initalText) {
        super(initalText);
        super.setPreferredSize(new java.awt.Dimension(100, 16));
        super.setAlignmentX(CENTER_ALIGNMENT);
    }
}