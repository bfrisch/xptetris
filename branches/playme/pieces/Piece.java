package pieces;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import java.awt.Color;
import java.awt.Graphics;

public abstract class Piece {
	private int pieceRow = 0, pieceCol = 0;
	protected int rotationNum = 0;
	private Color[][] board;
	protected Color pieceColor, boardBackground;
	protected boolean pieceShape[][] = {{false}};
	private boolean isInBoard = false, gameIsLost = false;
	private boolean noFallAllowed = false;
	protected boolean[][][] arrayRotations;
	
	private int rowsFilled = 0;
	
	public Piece(Color[][] board, Color boardBackground) {
		this.boardBackground = boardBackground;
		pieceRow = -1 * (pieceShape.length - 1);
		this.board = board;
		this.pieceCol = board[0].length / 2;
	}
	
	public void fall() {
		if (pieceIsDirectlyAboveOtherPieces()) {
			if (pieceRow > 0) {
				addPieceToBoard();
				clearNewFilledRow();
				noFallAllowed = true;
				
			}
			else {
				gameIsLost = true;
			}
		}
		else if (pieceRow < board.length - pieceShape.length) {
			if (noFallAllowed == false) {
				pieceRow++;
			}
		}
		else {
			addPieceToBoard();
			clearNewFilledRow();
		}
	}
	
	public boolean pieceIsDirectlyAboveOtherPieces() {
		for (int r = pieceShape.length - 1; r >= 0; r--) {
			for (int c = 0; c < pieceShape[pieceShape.length -1].length; c++) {
				if (pieceRow + pieceShape.length >= 0 && pieceCol + c >= 0 && pieceRow + pieceShape.length != board.length) {
					if (!board[pieceRow + r + 1][pieceCol + c].equals(boardBackground)) {
						if (pieceShape[r][c] == true) {
							return true;
						}
					}
				}
			}	
		}
		
		return false;
	}
	
	public void paint(int boardX, int boardY, int boardWidth, int boardHeight, int offset, Graphics page) {
		for (int row = 0; row < pieceShape.length; row++) {
			for (int col = 0; col < pieceShape[0].length; col++) {
				if (pieceShape[row][col] == true) {
					page.setColor(pieceColor);
					page.fillRect((col + pieceCol)*(boardWidth/board[0].length) + boardX + offset,(row + pieceRow)*(boardHeight/board.length) + offset + boardY, (boardWidth/board[0].length) - (offset*2),(boardHeight/board.length) - (offset*2));
				}
			}
		}
	}
	
	public void nextPaint(int locX, int locY, int squareSide, int offset, Graphics page) {
		for (int row = 0; row < pieceShape.length; row++) {
			for (int col = 0; col < pieceShape[0].length; col++) {
				if (pieceShape[row][col] == true) {
					page.setColor(Color.black);
					page.fillRect((col)*(squareSide) + locX,(row)*(squareSide) + locY, squareSide,squareSide);
					page.setColor(pieceColor);
					page.fillRect((col)*(squareSide) + locX + offset,(row)*(squareSide) + locY + offset, squareSide - (offset * 2),squareSide - (offset * 2));
				}
			}
		}
	}
	
	private void addPieceToBoard() {
		for (int row = 0; row < pieceShape.length; row++) {
			for (int col = 0; col < pieceShape[0].length; col++) {
				if (pieceShape[row][col] == true) {
					board[pieceRow + row ][pieceCol + col] = pieceColor;
				}
			}
		}
		isInBoard = true;
	}
	
	private void clearNewFilledRow() {
		boolean[] rowsToClear = new boolean[pieceShape.length]; 
		for (int curRow = pieceRow; curRow < pieceRow + pieceShape.length; curRow++) {
			for (int curCol = 0; curCol < board[0].length; curCol++) {
				if (board[curRow][curCol].equals(boardBackground)) {
					rowsToClear[curRow - pieceRow] = false;
					break;
				}
				else {
					rowsToClear[curRow - pieceRow] = true;
				}
			}
		}
		//System.out.println(java.util.Arrays.toString(rowsToClear));
		for (int i = 0; i < rowsToClear.length; i++) {
			if (rowsToClear[i] == true) {
				rowsFilled++;
				clearRow(i + pieceRow);
			}
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
				board[0][c] = boardBackground; 
			}					
		}
	}
	public void rotate() {
		rotationNum++;
		if (rotationNum == arrayRotations.length) rotationNum = 0;
		pieceShape = arrayRotations[rotationNum];
	}
	
	public boolean gameIsLost() {
		return gameIsLost;
	}
	
	public boolean isInBoard() {
		return isInBoard;
	}
	
	public void moveLeft() {
		if (noFallAllowed == false) {
			if (pieceCol > 0 && !boardIsDirectlyLeftOfOtherPieces()) {
				pieceCol--;
			}
		}
	}
	
	public boolean boardIsDirectlyLeftOfOtherPieces() {
		for (int r = pieceShape.length - 1; r >= 0; r--) {
			for (int c = 0; c < pieceShape[pieceShape.length -1].length; c++) {
				if ((pieceCol + c - 1) >= 0) {
					if (!board[pieceRow + r][pieceCol + c - 1].equals(boardBackground)) {
						if (pieceShape[r][c] == true) {
							return true;
						}
					}
				}
			}	
		}
		
		return false;
	}
	
	public void moveRight() {
		if (noFallAllowed == false) {
			if (pieceCol < (board[0].length - pieceShape[0].length) && !boardIsDirectlyRightOfOtherPieces()) 
				pieceCol++;
		}
	}
	
	public boolean boardIsDirectlyRightOfOtherPieces() {
		for (int r = pieceShape.length - 1; r >= 0; r--) {
			for (int c = 0; c < pieceShape[pieceShape.length -1].length; c++) {
				if (!board[pieceRow + r][pieceCol + c + 1].equals(boardBackground)) {
					if (pieceShape[r][c] == true) {
						return true;
					}
				}
			}	
		}
		
		return false;
	}
	
	public int getRows() {
		return board.length;
	}
	
	public int getNumRowsFilled() {
		return rowsFilled;
	}
}
