package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;

public class LinePiece extends Piece{
	
	public LinePiece(Color[][] board) {
		super(board);
		arrayRotations = new boolean[][][]{
				{{true},{true},{true},{true}},
				{{true,true,true,true}}
				
		};
		
		pieceColor = Color.cyan;
	}
}

