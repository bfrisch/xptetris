package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class RightZigZag extends Piece{


	public RightZigZag(Color[][] board) {
		super(board);
		
		arrayRotations = new boolean[][][] {
		   {{false, true, true},
			{true, true, false}},
		  
		   {{true, false},
			{true, true},
			{false, true}}
		};
		
		pieceColor = Color.green;
	}
}

