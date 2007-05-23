package tetris;

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
			boolean[][] p = {{true,true,true,true}};
			pieceShape = p;
		}
		else {
			boolean[][] p = {{true},{true},{true},{true}};
			pieceShape = p;
		}
	}
}

