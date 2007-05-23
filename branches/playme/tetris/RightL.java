package tetris;

import java.awt.Color;

public class RightL extends Piece{
	
	public RightL(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		arrayRotations = new boolean[][][]{
				{{true, false},
					{true, false},
				{true, true}
				},
				
				{{false, false, true},
					{true, true, true}},
					
					{{true, true, true},
						{false, false, true}},
						
						{{false, true},
							{false, true},
						{true, true}
						},
				};
		pieceShape = arrayRotations[0];
		pieceColor = Color.blue;
	}
}
