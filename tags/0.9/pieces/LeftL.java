package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

import java.awt.Color;

public class LeftL extends Piece{
	public LeftL(Color[][] board) {
		super(board);
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
		pieceColor = Color.orange;
	}
}