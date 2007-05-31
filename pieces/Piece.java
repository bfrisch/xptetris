package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 5
 */

import java.awt.Color;
import java.awt.Graphics;

public abstract class Piece {
	private int pieceTopRow = 0, pieceTopCol = 0;
	protected int rotationNum = 0;
	private Color[][] board;
	protected Color pieceColor;
	private boolean isInBoard = false, gameOver = false;
	protected boolean[][][] arrayRotations;
	private boolean pieceHasFallen = false;
	
	private int rowsFilled = 0;
	
	public Piece(Color[][] board) {
		this.board = board;
		this.pieceTopCol = board[0].length / 2;
	}
	
	
// Piece Painting Methods
	public void paint(int boardX, int boardY, int boardWidth, int boardHeight, int offset, Graphics page) {
		for (int row = 0; row < getPieceShape().length; row++) {
			for (int col = 0; col < getPieceShape()[0].length; col++) {
				if (getPieceShape()[row][col] == true) {
					page.setColor(pieceColor);
					page.fillRect((col + pieceTopCol)*(boardWidth/board[0].length) + boardX + offset,(row + pieceTopRow)*(boardHeight/board.length) + offset + boardY, (boardWidth/board[0].length) - (offset*2),(boardHeight/board.length) - (offset*2));
				}
			}
		}
	}
	
	public void paintAsNextPiece(int locX, int locY, int squareSide, int offset, Graphics page) {
		for (int row = 0; row < getPieceShape().length; row++) {
			for (int col = 0; col < getPieceShape()[0].length; col++) {
				if (getPieceShape()[row][col] == true) {
					page.setColor(Color.black);
					page.fillRect((col)*(squareSide) + locX,(row)*(squareSide) + locY, squareSide,squareSide);
					page.setColor(pieceColor);
					page.fillRect((col)*(squareSide) + locX + offset,(row)*(squareSide) + locY + offset, squareSide - (offset * 2),squareSide - (offset * 2));
				}
			}
		}
	}
// End Piece Painting Methods

// Piece Rotation and Game Status
	public void rotate() {
		rotationNum++;
		if (rotationNum == arrayRotations.length) rotationNum = 0;
		int lastRotationNum =rotationNum - 1;
		if (lastRotationNum < 0) lastRotationNum = arrayRotations.length;
	}

	public boolean gameOver() {
		return gameOver;
	}

// Piece Falling Control Methods
	public void fall() {
		if (!gameOver()) {
			if (!pieceCanFall()) {
				if (pieceHasFallen) {
					addPieceToBoard();
					clearFilledRows();				
				}
				else {
					gameOver = true;
				}
			}
			else if (pieceTopRow < board.length - getPieceShapeRows()) {
				if (!this.isInBoard() && !this.gameOver()) {
					pieceTopRow++;
					pieceHasFallen = true;
				}
			}
			else {
				addPieceToBoard();
				clearFilledRows();
				pieceHasFallen = true;
			}
		}
	}

	private void addPieceToBoard() {
		for (int row = 0; row < getPieceShapeRows(); row++) {
			for (int col = 0; col < getPieceShape()[row].length; col++) {
				if (getPieceShape()[row][col] == true && pieceTopRow + row < board.length && pieceTopCol + col < board[0].length) {
					board[pieceTopRow + row ][pieceTopCol + col] = pieceColor;
				}
			}
		}
		isInBoard = true;
	}


	private void clearFilledRows() { 
		for (int curRow = pieceTopRow; curRow < pieceTopRow + getPieceShapeRows(); curRow++) {
			boolean clearRow = true;
			for (int curCol = 0; curCol < board[curRow].length; curCol++) {
				if (board[curRow][curCol] == null) {
					clearRow = false;
					break;
				}
			}
			if (clearRow) {clearRow(curRow);}
		}
	}

	private void clearRow(int rowToClear) {
		if (rowToClear > 0) {
			for (int c = 0; c < board[0].length; c++) {
				board[rowToClear][c] = board[rowToClear - 1][c];			
			}
			clearRow(rowToClear - 1);
		}
		else if (rowToClear == 0) {
			for (int c = 0; c < board[0].length; c++) {
				board[0][c] = null; 
			}					
		}
	}


	public boolean pieceCanFall() {
		return pieceCanMoveTo(getPieceShape(), pieceTopRow + 1, pieceTopCol);
	}

	public boolean isInBoard() {
		return isInBoard;
	}
	
	public void moveLeft() {
		if (canMoveLeft()) {
			pieceTopCol--;
		}
	}
	
	private boolean canMoveLeft() {
		return pieceCanMoveTo(getPieceShape(), pieceTopRow, pieceTopCol - 1);
	}

	public void moveRight() {
		if (canMoveRight()) {
			pieceTopCol++;
		}
	}

	public boolean canMoveRight() {
		return pieceCanMoveTo(getPieceShape(), pieceTopRow, pieceTopCol + 1);
	}
	
	private boolean pieceCanMoveTo(boolean[][] pieceShape, int topRow, int topCol) {
		if (!isInBoard() && !gameOver() && (topRow >= 0) &&
		  ((topRow + pieceShape.length) <= board.length) && (topCol >= 0)) {
			for (int r = pieceShape.length - 1; r >= 0; r--) {
				for (int c = 0; c < pieceShape[r].length; c++) {
					if  ((topCol + pieceShape[r].length > board[r + topRow].length)
						|| (board[topRow + r][topCol + c] != null && pieceShape[r][c])) {
							return false;
					}
				}	
			}
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//
	
	// ----------- Accessor Methods
	
	public int getPieceShapeRows() {
		return getPieceShape().length;
	}
	
	public int getNumRowsFilled() {
		return rowsFilled;
	}
	
	private boolean[][] getPieceShape() {
		return arrayRotations[rotationNum];
	}
}
