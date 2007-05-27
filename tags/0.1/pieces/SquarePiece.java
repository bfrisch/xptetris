package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class SquarePiece extends Piece{
	
	public SquarePiece(Color[][] board) {
		super(board);
		
		pieceShape = new boolean[][] {
				{true, true},
				{true, true}
		};
		pieceColor = Color.yellow;
	}
	
	public void rotate() {}
}
