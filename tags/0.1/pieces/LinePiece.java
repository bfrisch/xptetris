package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class LinePiece extends Piece{
	
	public LinePiece(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		boolean[][] p = {{true},{true},{true},{true}};
		pieceShape = p;
		pieceColor = Color.cyan;
	}
	
	public void rotate() {
		if (pieceShape.length > 1) {
			pieceShape = new boolean[][]{{true,true,true,true}};
		}
		else {
			pieceShape = new boolean[][]{{true},{true},{true},{true}};
		}
	}
}

