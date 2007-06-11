package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
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
	public void paint(int squareSideLen, int boardX, int boardY, int offset, Graphics page) {
		int usedBoxX = boardX;
		int usedBoxY = boardY;
		
		for (int row = 0; row < getPieceShape().length; row++) {
			for (int col = 0; col < getPieceShape()[0].length; col++) {
				if (getPieceShape()[row][col] == true) {
					page.setColor(pieceColor);
					
					page.fillRect((col + pieceTopCol)*(squareSideLen) + usedBoxX + offset,(row + pieceTopRow)*(squareSideLen) + offset + usedBoxY, (squareSideLen) - (offset*2),(squareSideLen) - (offset*2));
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
	public void rotateClockwise() {
		if (rotationNum < arrayRotations.length - 1) {
			if (pieceCanRotateTo(rotationNum + 1)) {
				setRotatedTopRowCol(rotationNum + 1);
				rotationNum++;
			}
		}
		else {
			if (pieceCanRotateTo(0)) {
				setRotatedTopRowCol(0);
				rotationNum = 0;
			}
		}
	}
	
	public void rotateCounterClockwise() {
		if (rotationNum > 0) {
			if (pieceCanRotateTo(rotationNum - 1)) {
				setRotatedTopRowCol(rotationNum - 1);
				rotationNum--;
			}
		}
		else {
			if (pieceCanRotateTo(arrayRotations.length - 1)) {
				setRotatedTopRowCol(arrayRotations.length - 1);
				rotationNum = arrayRotations.length - 1;
			}
		}
	}

	private boolean pieceCanRotateTo(int rotationNum) {
		if (pieceTopCol > board[pieceTopRow].length - getRotationMaxCols(rotationNum)) {
			return pieceCanMoveTo(arrayRotations[rotationNum], pieceTopRow, board[pieceTopRow].length - getRotationMaxCols(rotationNum));
		}
		else {
			return pieceCanMoveTo(arrayRotations[rotationNum], pieceTopRow, pieceTopCol);
		}
	}
	
	private void setRotatedTopRowCol(int rotationNum) {
		if (pieceTopCol > board[pieceTopRow].length - getRotationMaxCols(rotationNum))
			pieceTopCol = board[pieceTopRow].length - getRotationMaxCols(rotationNum); 
	}
	
	private int getRotationMaxCols(int rotationNum) {
		int pieceCols = 0;
		for (int r = 0; r < arrayRotations[rotationNum].length; r++) {
			if (arrayRotations[rotationNum][r].length - 1> pieceCols) {
				pieceCols = arrayRotations[rotationNum][r].length; 
			}
		}
		return pieceCols;
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
