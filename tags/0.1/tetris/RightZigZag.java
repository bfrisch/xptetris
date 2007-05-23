package tetris;

import java.awt.Color;

public class RightZigZag extends Piece{
	private int rotationNum = 0;
	boolean[][][] arrayRotations = 
		{{{true, false},
		 {true, true},
		 {false, true}},
		 
		 {{false, true, true},
		  {true, true, false}}
	};
	public RightZigZag(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		boolean[][] p = {{true, false},
				{true, true},{false, true}};
		pieceShape = p;
		pieceColor = Color.magenta;
	}
	
	public void rotate() {
		rotationNum++;
		if (rotationNum == arrayRotations.length) rotationNum = 0;
		pieceShape = arrayRotations[rotationNum];
	}
}

