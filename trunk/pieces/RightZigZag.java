package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class RightZigZag extends Piece{


	public RightZigZag(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		
		arrayRotations = new boolean[][][] {
		   {{false, true, true},
			{true, true, false}},
		  
		   {{true, false},
			{true, true},
			{false, true}}
		};
		
		pieceShape = arrayRotations[0];
		pieceColor = Color.green;
	}
}

