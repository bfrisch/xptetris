package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class SquarePiece extends Piece{
	
	public SquarePiece(Color[][] board) {
		super(board);
		
		arrayRotations = new boolean[][][] {
				{{true, true},
				{true, true}}
		};
		pieceColor = Color.yellow;
	}
	
	
	// Overridden to stop useless computations.
	public void rotateClockwise() {}
	public void rotateCounterClockwise() {}
}
