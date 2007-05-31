package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

import pieces.*;
import xpn.*;

import util.HighScores;
import util.Messages;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 5
 */

class TetrisComponent extends XPnComponent implements XPnTimerHandler {
	private static final long serialVersionUID = -7820551496471765983L;
	
	public Color[][] board = new Color[20][10];
	private static final Color boardBackground = Color.lightGray,
		boardBorder = Color.black;

	public static final int offset = 1;
	public int score = 0;
	public Piece piece = getRandomNextPiece();
	public Piece nextPiece = getRandomNextPiece();
	private boolean gamePaused = false;
	
	private XPnTimer timer = new XPnTimer(this);

	public TetrisComponent() {
		super();
		
		JOptionPane.showMessageDialog(this, "Welcome to " + Messages.getString("title") + " " + Messages.getString("version") + "\nEnjoy Your Game!\n\nPlanned Features: Saving and Opening Of Games, More Than 1 Next Piece Showing\nFilled Row Becomes White Before Disappearing, \n\n- Ben Frisch", Messages.getString("title") + " " + Messages.getString("version"), JOptionPane.INFORMATION_MESSAGE);
		
		init();
	}
	
	public void init() {
		timer.start(500);
	}
	
	public void reset() {
		timer.stop();
		timer.start(500);
		
		board = new Color[20][10];
		
		piece = getRandomNextPiece();
		nextPiece = getRandomNextPiece();
		
		gamePaused = false;
		
		repaint();
	}
	
	public void doNothingOnTimer() {
		timer.stop();
	}
	
	public void paintComponent(Graphics page) {
		drawBoard(getWidth()/6 + ((getWidth() % 3)/2), 10 + (((2*getWidth()) % 3)/2), 2*getWidth()/3 - ((getWidth() % 3)/2), getHeight()-20, page);

		nextPiece.paintAsNextPiece(2, 70, 20, 1, page);
		piece.paint(getWidth()/6 + ((getWidth() % 3)/2), 10 + (((2*getWidth()) % 3)/2), 2*getWidth()/3 - ((getWidth() % 3)/2), getHeight()-20, offset, page);
	}
	
	public void drawBoard(int boardX, int boardY, int boardWidth, int boardHeight, Graphics page) {
		page.setColor(boardBorder);
		//page.drawRect(getWidth()/6, 10, 2*getWidth()/3, getHeight()-20);
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				page.setColor(boardBorder);
				page.fillRect(col*(boardWidth/board[0].length) + boardX,row*(boardHeight/board.length) + boardY, boardWidth/board[0].length,boardHeight/board.length);
				if (board[row][col] != null) {
					page.setColor(board[row][col]);
				}
				else {
					page.setColor(boardBackground);
				}
				page.fillRect(col*(boardWidth/board[0].length) + boardX + offset,row*(boardHeight/board.length) + offset + boardY, (boardWidth/board[0].length) - (offset*2),(boardHeight/board.length) - (offset*2));
			}
		}
		
		page.setColor(Color.black);
		page.drawString("Score " + score, 5, 20);
		page.drawString("Next Piece", 5, 50);
	}
	
	public void pauseGame() {
		gamePaused = !gamePaused;
		if (!gameOver()) {
			if (timer.isRunning()) {
				timer.stop();
			}
			else {
				timer.start();
			}
		}
		else {
			timer.stop();
		}
	}
	
	public boolean gameOver() {
		return piece.gameOver();
	}
	
	public boolean isPaused() {
		return gamePaused || gameOver();
	}
	  
	////////////////////////////////////////////
	// Event Handlers
	////////////////////////////////////////////

	public void onMousePressed(int x, int y) { }

	public void onMouseDragged(int x, int y) { }

	public void onMouseReleased(int x, int y) { }

	public void onKeyPressed(KeyEvent e, int keyCode, char keyChar) {
		if (!isPaused()) {
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
				while (!piece.isInBoard() && !piece.gameOver()) {
					piece.fall();
					score+=10;
				}
				onTimer();
				break;
			}
		}
	}
	
	public xpn.XPnTimer getTimer() {
		return timer;
	}
	
	public void onTimer() {
		if (gameOver() && !gamePaused) {
			((TetrisGUI)this.getRootPane().getParent()).statusBar.setMessage("Game Over");
			timer.stop();
			
			if (HighScores.getLowestHighScore() < score || (HighScores.getNumScores() < 10 && (this.score == HighScores.getLowestHighScore()))) {
				String name = JOptionPane.showInputDialog(this, "Congratulations! You got a high score!\nEnter your name:\n\nPress cancel to hide your high score", "Ben's XPTetris 2007", JOptionPane.QUESTION_MESSAGE);
				if (name != null) {
					HighScores.add(name, score);
					HighScores.showScores();
				}
			}
			gamePaused = true;
		}
		if (!isPaused()) {
			if (piece.isInBoard()) {
				if (piece.getNumRowsFilled() > 0) {
					XPnSound.beep();
				}
				
				score += (45/piece.getPieceShapeRows()) + 20*piece.getNumRowsFilled();
				piece = nextPiece;
				
				nextPiece = getRandomNextPiece();
			}
			else {
				piece.fall();
			}
			
			repaint();
		}
	}

	private Piece getRandomNextPiece() {

		java.util.Random rand = new java.util.Random();
		
		switch (rand.nextInt(7)) {
			case 0:
				return new RightL(board);
			case 1:
				return new SquarePiece(board);
			case 2:
				return new LeftL(board);
			case 3:
				return new LinePiece(board);
			case 4:
				return new RightZigZag(board);
			case 5:
				return new LeftZigZag(board);
			case 6:
				return new TPiece(board);
			default:
				throw new java.lang.RuntimeException("Invalid next int");
		}
	}
	
	public void onResized() {repaint();}
	
	

}

