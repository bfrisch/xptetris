package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class SquarePiece extends Piece{
	
	public SquarePiece(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		boolean[][] p = {
				{true, true},
				{true, true}
		};
		pieceShape = p;
		pieceColor = Color.yellow;
	}
	
	public void rotate() {}
}
