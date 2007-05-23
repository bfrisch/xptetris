package tetris;

import java.awt.Color;

public class LeftZigZag extends Piece{
	public LeftZigZag(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		boolean[][] p = new boolean[][]{{false, true},
				{true, true},{true, false}};
		arrayRotations = new boolean[][][] 
		              		{{{false, true},
		                   		 {true, true},
				                         		 {true, false}},
				                         		 
				                         		 {{true, true, false },
				                         		  {false, true, true}}};			
		pieceShape = arrayRotations[0];
		pieceColor = Color.RED;

	}
}

