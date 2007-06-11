package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


import pieces.*;
import xpn.*;

import util.HighScores;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 7
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
	
	private boolean pausedDueToLostFocus = false;
	
	private int numberOfNextPieces = 1;
	
	private XPnTimer timer = new XPnTimer(this);

	public TetrisComponent() {
		super();
		
		JOptionPane.showMessageDialog(this, XPnStringBundle.getString("welcomeTo") + getTitle() + "\n" + XPnStringBundle.getString("enjoy") + "\n\n" + XPnStringBundle.getString("planned"), getTitle(), JOptionPane.INFORMATION_MESSAGE);
		
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
		int widthOffset = getWidthOffset(getSquareSideLen());
		int heightOffset = getYOffset(getSquareSideLen());
		

		drawBoard(getSquareSideLen(), widthOffset, heightOffset, page);

		nextPiece.paintAsNextPiece(widthOffset/2, 70, 20, 1, page);
		
		piece.paint(getSquareSideLen(), widthOffset, heightOffset, offset, page);
		
		page.setColor(Color.black);
		page.drawString(XPnStringBundle.getString("score") + score, widthOffset/2, 20);
		
		if (numberOfNextPieces == 1)
			page.drawString(XPnStringBundle.getString("nextPiece"), widthOffset/2, 50);
		else
			page.drawString(XPnStringBundle.getString("nextPieces"), widthOffset/2, 50);
	}
	
	public void drawBoard(int squareSideLen, int boardX, int boardY, Graphics page) {
		page.setColor(boardBorder);
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[col].length; col++) {
				page.setColor(boardBorder);
				
				page.fillRect(col*squareSideLen + boardX,row*squareSideLen + boardY, squareSideLen, squareSideLen);
				
				if (board[row][col] != null) {
					page.setColor(board[row][col]);
				}
				else {
					page.setColor(boardBackground);
				}
				 
				page.fillRect(col*squareSideLen + boardX + offset,row*squareSideLen + offset + boardY, squareSideLen - (offset*2),squareSideLen - (offset*2));
			}
		}
	}
	
	private int getSquareSideLen() {
		if (getHeight()/board.length < getWidth()/board.length)
			return getHeight()/board.length;
	
		return getWidth()/board[0].length;
	}
	
	private int getWidthOffset(int squareSideLen) {
		return (getWidth()/6) + ((((2*getWidth())/3) - (squareSideLen*board[0].length))/2);
	}
	
	private int getYOffset(int squareSideLen) {
		return (getHeight() - squareSideLen*board.length)/2;
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
				onTimer();
				if (!piece.isInBoard()) score+=10;
				break;
			case KeyEvent.VK_UP:
				piece.rotateCounterClockwise();
				break;
			case KeyEvent.VK_SPACE:
				piece.rotateClockwise();
				break;
			case KeyEvent.VK_ENTER:
				while (!piece.isInBoard() && !piece.gameOver()) {
					onTimer();
					score+=10;
				}
				break;
			}
		}
	}
	
	public xpn.XPnTimer getTimer() {
		return timer;
	}
	
	public void onTimer() {
		if (gameOver() && !gamePaused) {
			((TetrisGUI)this.getRootPane().getParent()).statusBar.setMessage(XPnStringBundle.getString("gameOver"));
			timer.stop();
			
			if (HighScores.getLowestHighScore() < score || (HighScores.getNumScores() < 10 && (this.score == HighScores.getLowestHighScore()))) {
				String name = JOptionPane.showInputDialog(this, XPnStringBundle.getString("congrats"), getTitle(), JOptionPane.QUESTION_MESSAGE);
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
	
	public void onFocusGained() {
		if (pausedDueToLostFocus) {
			pauseGame();
			((TetrisGUI) this.getRootPane().getParent()).statusBar.setMessage(XPnStringBundle.getString("playing"));
			pausedDueToLostFocus = false;
		}
	}
	
	public void onFocusLost() {
		if (!isPaused()) {
			pauseGame();
			((TetrisGUI) this.getRootPane().getParent()).statusBar.setMessage(XPnStringBundle.getString("paused"));
			pausedDueToLostFocus = true;
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
	
	private String getTitle() {
		return XPnStringBundle.getString("title") + " " + XPnStringBundle.getString("version"); 
	}
}

