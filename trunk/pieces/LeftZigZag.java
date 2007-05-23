package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class LeftZigZag extends Piece{
	public LeftZigZag(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		arrayRotations = new boolean[][][] {
			{{true, true, false},
        	 {false, true, true}},
			
			{{false, true},
		     {true, true},
		     {true, false}}
		 };			
		pieceShape = arrayRotations[0];
		pieceColor = Color.RED;
	}
}

