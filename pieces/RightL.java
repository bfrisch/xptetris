package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

import java.awt.Color;

public class RightL extends Piece{
	
	public RightL(Color[][] board) {
		super(board);
		arrayRotations = new boolean[][][]{
			{{true, false, false},
			 {true, true, true}},
			 
			{{true, true},
			 {true, false},
			 {true, false}},
								
			{{true, true, true},
			 {false, false, true}},
						
			{{false, true},
			 {false, true},
			 {true, true}},
		};
		pieceColor = Color.blue;
		
	}
}
