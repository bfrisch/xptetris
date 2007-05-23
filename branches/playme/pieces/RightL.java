package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class RightL extends Piece{
	
	public RightL(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		arrayRotations = new boolean[][][]{
			{{false, false, true},
			 {true, true, true}},
			 
			{{true, false},
			 {true, false},
			 {true, true}},
								
			{{true, true, true},
			 {true, false, false}},
						
			{{true, true},
			 {false, true},
			 {false, true}},
		};
		pieceShape = arrayRotations[0];
		pieceColor = Color.blue;
	}
}
