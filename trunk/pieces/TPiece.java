package pieces;
import java.awt.Color;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

public class TPiece extends Piece {
	public TPiece(Color[][] board) {
		super(board);
		arrayRotations = new boolean[][][] {
			{{false, true, false},
        	 {true, true, true}},
			
			{{true, false},
		     {true, true},
		     {true, false}},
		     
		     {{true, true, true},
		      {false, true, false}},
		      
		     {{false, true},
		      {true,  true},	  
		      {false, true}}
		};		
		
		pieceShape = arrayRotations[0];
		pieceColor = Color.magenta;
	}
}
