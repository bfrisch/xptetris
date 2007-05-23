package tetris;

import java.awt.Color;

public class LeftL extends Piece{
	boolean[][][] arrayRotations = 
		{{{false, true},
		 {true, true},
		 {true, false}},
		 
		 {{true, true, false },
		  {false, true, true}}
	};
	
	public LeftL(Color[][] board, Color boardBackground) {
		super(board, boardBackground);
		arrayRotations = new boolean[][][]{
				{{true, true},
					{false, true},
				{false, true}
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
		pieceColor = Color.RED;
	}
}
