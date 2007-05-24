package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

import pieces.*;

import util.HighScores;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

class TetrisComponent extends IntroCsComponent {
	private static final long serialVersionUID = 1L;
	private static final Color boardBackground = Color.lightGray,
		boardBorder = Color.black;
	private static final int offset = 1;
	private Color[][] board = new Color [20][10];
	private int score = 0;
	private Piece piece = getRandomNextPiece();
	private Piece nextPiece = getRandomNextPiece();

	private boolean gameInPlay = true;
	private boolean gameBeingPlayed = true;
	
	public TetrisComponent() {
		super();
		JOptionPane.showMessageDialog(this, "Welcome Ben's XPTetris 2007 Version 0.1 Alpha 3 RTC (Release To Class)\n Enjoy Your Game!\n\nPlanned Features: Saving and Opening Of Games, Sound, Fall Speed Changing, More Than 1 Next Piece Showing\nFilled Row Becomes White Before Disappearing, Working Help\n\n- Ben Frisch", "Ben's XPTetris 2007 0.1 Alpha 3", JOptionPane.INFORMATION_MESSAGE);		startTimer(500);
		init();
	}
	
	public void init() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col] = boardBackground;
			}
		}
		
		score = 0;
		
		piece = getRandomNextPiece();
		nextPiece = getRandomNextPiece();
		
		gameInPlay = true;
		gameBeingPlayed = true;
	}
	
	public void paintComponent(Graphics page) {
		drawBoard(getWidth()/6 + ((getWidth() % 3)/2), 10 + (((2*getWidth()) % 3)/2), 2*getWidth()/3 - ((getWidth() % 3)/2), getHeight()-20, page);

		nextPiece.nextPaint(2, 70, 20, 1, page);
		piece.paint(getWidth()/6 + ((getWidth() % 3)/2), 10 + (((2*getWidth()) % 3)/2), 2*getWidth()/3 - ((getWidth() % 3)/2), getHeight()-20, offset, page);
	}
	
	public void drawBoard(int boardX, int boardY, int boardWidth, int boardHeight, Graphics page) {
		page.setColor(boardBorder);
		//page.drawRect(getWidth()/6, 10, 2*getWidth()/3, getHeight()-20);
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				page.setColor(boardBorder);
				page.fillRect(col*(boardWidth/board[0].length) + boardX,row*(boardHeight/board.length) + boardY, boardWidth/board[0].length,boardHeight/board.length);
				page.setColor(board[row][col]);
				page.fillRect(col*(boardWidth/board[0].length) + boardX + offset,row*(boardHeight/board.length) + offset + boardY, (boardWidth/board[0].length) - (offset*2),(boardHeight/board.length) - (offset*2));
			}
		}
		
		page.setColor(Color.black);
		page.drawString("Score " + score, 5, 20);
		page.drawString("Next Piece", 5, 50);
	}
	
	public void pauseGame() {
		gameBeingPlayed = !gameBeingPlayed;
	}
	
	public boolean gameIsOver() {
		return gameInPlay;
	}
	
	public Color getDefaultColor() {
		return boardBackground;
	}
	
	public boolean isPaused() {
		return !gameBeingPlayed;
	}
	  
	////////////////////////////////////////////
	// Event Handlers
	////////////////////////////////////////////

	public void onMousePressed(int x, int y) { }

	public void onMouseDragged(int x, int y) { }

	public void onMouseReleased(int x, int y) { }

	public void onKeyPressed(KeyEvent e, int keyCode, char keyChar) {
		if (gameBeingPlayed == true) {
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				piece.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				piece.moveRight();
				break;
			case KeyEvent.VK_DOWN:
				piece.fall();
				if (!piece.isInBoard()) score+=10;
				break;
			case KeyEvent.VK_SPACE:
				piece.rotate();
				break;
			case KeyEvent.VK_ENTER:
				while (!piece.isInBoard()) {
					piece.fall();
					score+=10;
				}
				break;
			}
		}
	}

	private int getScore() {
		return score;
	}
	
	public void onTimer() {
		if (gameBeingPlayed == true) {			
			if (piece.gameIsLost() && gameInPlay == true) {
				if (HighScores.getLowestHighScore() < this.getScore() || (HighScores.getNumScores() < 10 && (this.getScore() == HighScores.getLowestHighScore()))) {
					String name = JOptionPane.showInputDialog(this, "Congratulations! You got a high score!\nEnter your name:\n\nPress cancel to hide your high score", "Ben's XPTetris 2007", JOptionPane.QUESTION_MESSAGE);
					if (name != null) {
						HighScores.add(name, getScore());
						HighScores.showScores();
					}
				}
				gameBeingPlayed = false;
				gameInPlay = false;
			}
			else if (piece.isInBoard()) {
				score += (45/piece.getRows()) + 20*piece.getNumRowsFilled();
				piece = nextPiece;
				
				nextPiece = getRandomNextPiece();
				
				
			}
			else {
				piece.fall();
			}
		}
	}

	private Piece getRandomNextPiece() {

		java.util.Random rand = new java.util.Random();
		
		switch (rand.nextInt(8)) {
			case 1:
				return new RightL(board, boardBackground);
			case 2:
				return new SquarePiece(board, boardBackground);
			case 3:
				return new LeftL(board, boardBackground);
			case 4:
				return new LinePiece(board, boardBackground);
			case 5:
				return new RightZigZag(board, boardBackground);
			case 6:
				return new LeftZigZag(board, boardBackground);
			case 7:
				return new TPiece(board, boardBackground);
			default:
				return null;
		}
	}
	public void onResized() {repaint();} 
}