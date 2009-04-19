package gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


import pieces.*;
import xpn.*;

import util.HighScores;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 8
 */

class TetrisComponent extends XPnComponent implements XPnTimerHandler, java.io.Serializable {
	private static final long serialVersionUID = -7820551496471765983L;
	
	private Color[][] board = new Color[20][10];
	private static final Color boardBackground = Color.lightGray,
		boardBorder = Color.black;

	private static final int offset = 1;
	private int score = 0;
	private ConcurrentLinkedQueue nextPieces = null;
	private boolean gamePaused = false;
	
	private boolean pausedDueToLostFocus = false;
	
	private int numberOfNextPieces = 2;
	private int piecesToLose = 0;
	
	private XPnTimer timer = new XPnTimer(this);

	public TetrisComponent() {
		super();
		
		JOptionPane.showMessageDialog(this, XPnStringBundle.getString("welcomeTo") + getTitle() + "\n" + XPnStringBundle.getString("enjoy") + "\n\n" + XPnStringBundle.getString("planned"), getTitle(), JOptionPane.INFORMATION_MESSAGE);
		
		init();
	}
	
	public void init() {
		timer.start(500);
		
		initializeNextPieces();
	}
	
	public void reset() {
		timer.stop();
		timer.start(500);
		
		board = new Color[20][10];
		
		gamePaused = false;
		
		initializeNextPieces();
		
		repaint();
	}
	
	private void initializeNextPieces() {
		nextPieces = new ConcurrentLinkedQueue();
		
		for (int pieceNum = 0; pieceNum <= numberOfNextPieces; pieceNum++)
			nextPieces.add(getRandomNextPiece());
	}
	
	public void doNothingOnTimer() {
		timer.stop();
	}
	
	public void paintComponent(Graphics page) {
		int widthOffset = getWidthOffset(getSquareSideLen());
		int heightOffset = getYOffset(getSquareSideLen());
		

		drawBoard(getSquareSideLen(), widthOffset, heightOffset, page);
		
		java.util.Iterator it = nextPieces.iterator();
		
		((Piece)it.next()).paint(getSquareSideLen(), widthOffset, heightOffset, offset, page);
		
		int loc = 70, pieceCount = 0;
		while (it.hasNext() && pieceCount < numberOfNextPieces) {
			Piece next = (Piece) it.next();
			next.paintAsNextPiece(widthOffset/2, loc, 20, 1, page);
			loc += 20*(next.getPieceShapeRows() + 1);
			pieceCount++;
		}
		
		page.setColor(Color.black);
		page.drawString(XPnStringBundle.getString("score") + score, widthOffset/2, 20);
		
		if (numberOfNextPieces == 1)
			page.drawString(XPnStringBundle.getString("nextPiece"), widthOffset/2, 50);
		else if (numberOfNextPieces > 1)
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
		return ((Piece)nextPieces.peek()).gameOver();
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
				((Piece)nextPieces.peek()).moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				((Piece)nextPieces.peek()).moveRight();
				break;
			case KeyEvent.VK_DOWN:
				onTimer();
				if (!((Piece)nextPieces.peek()).isInBoard()) score+=10;
				break;
			case KeyEvent.VK_UP:
				((Piece)nextPieces.peek()).rotateCounterClockwise();
				break;
			case KeyEvent.VK_SPACE:
				((Piece)nextPieces.peek()).rotateClockwise();
				break;
			case KeyEvent.VK_ENTER:
				while (!((Piece)nextPieces.peek()).isInBoard() && !((Piece)nextPieces.peek()).gameOver()) {
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
	
	public void setNumberNextPieces(int numPieces) {
		if (numPieces > numberOfNextPieces) {
			for (int nextPieceNum = numberOfNextPieces; nextPieceNum < numPieces; nextPieceNum++) {
				nextPieces.add(getRandomNextPiece());
			}
			
			numberOfNextPieces = numPieces;
		}
		else if (numPieces < numberOfNextPieces && numPieces >= 0) {
			piecesToLose = numberOfNextPieces - numPieces;
			numberOfNextPieces = numPieces;
		}
		
		repaint();
	}
	
	public int getNumberNextPieces() {
		return numberOfNextPieces;
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
			if (((Piece)nextPieces.peek()).isInBoard()) {
				if (!isFlashing) {
					if (((Piece)nextPieces.peek()).getNumRowsFilled() > 0) {
						if (!flashComplete) {
							flashCount = defaultFlashCount;
							isFlashing = true;
						} else {
							flashComplete = false;
							((Piece)nextPieces.peek()).clearFilledRows();
						}
					}
					
					score += (45/((Piece)nextPieces.peek()).getPieceShapeRows()) + 20*((Piece)nextPieces.poll()).getNumRowsFilled();
					
					if (piecesToLose == 0) {
						nextPieces.add(getRandomNextPiece());
					}
					else {
						piecesToLose--;
					}
				} else {
					flashCount--;
					flashBoard();
					if (flashCount <= 0) {
						flashComplete = true;
						isFlashing = false;
					}
				}
			}
			else {
				((Piece)nextPieces.peek()).fall();
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

