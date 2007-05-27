package gui;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 4
 */

import javax.swing.*;

import util.HighScores;
import xpn.*;

import java.awt.*;

public class TetrisGUI extends XPnFrame {
	private static final long serialVersionUID = 1L;
	private TetrisComponent tetrisComponent;
	XPnStatusBar statusBar = new XPnStatusBar("Game Now Being Played");
  
	public void init() {
		setTitle(Strings.title);
		add(tetrisComponent = new TetrisComponent());
		addButtons(new String[]{"Pause/Play Game", "New Game", /*"Save Game", "Open Game",*/ "High Scores", "Exit"});
		this.setJMenuBar(new MainMenu());
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 300));
		this.setSize(500, 500);
		HighScores.add("Benjamin Frisch", Integer.MAX_VALUE);
		HighScores.add("Benjamin Frisch", 1);
		HighScores.add("Benjamin Frisch", 18);
		
	}
  
	public TetrisComponent getTetrisComponent() {
		return tetrisComponent;
	}
	
	public void onButtonPressed(String label) {
		if (label.equals("Exit")) {
			System.exit(1);
		}
		else if (label.equals("High Scores")) {
			if (!getTetrisComponent().isPaused()) {
				getTetrisComponent().pauseGame();
				statusBar.setMessage("Game Paused");
			}
			HighScores.showScores();
		}
		else if (label.equals("New Game")) {
			getTetrisComponent().reset();
		}
		else if (label.equals("Pause/Play Game")) {
			getTetrisComponent().pauseGame();
			if (getTetrisComponent().isPaused()) {
				JOptionPane.showMessageDialog(this, "Tetris Game is now Paused", getTitle(), JOptionPane.INFORMATION_MESSAGE);
				statusBar.setMessage("Game Paused");
			}
			else {
				statusBar.setMessage("Game Now Being Played");
			}
		}
	}

	public void onTimer() { }
	
	public TetrisGUI getSelf() {
		return this;
	}

	class MainMenu extends xpn.XPnMenu {
	private static final long serialVersionUID = 1L;
		
	   public MainMenu() {
	      String[] gameMenuItems = {"New Game", "Pause Game", "|",/* "Save Game", "Open Game", "|", */"Exit"};
	      char[] gameMenuAccel = {'N', 'P', ' ', 'S', 'O', ' ', 'X'};
	      
	      add(makeMenu(new JMenu("Game"), gameMenuItems, gameMenuAccel));
	      
	      String[] speedMenuItems = {"Slow", "Medium", "Fast", "|", "Stopped (Cheater's Mode)"};
	      char[] speedMenuAccel = {'L', 'M', 'F',  ' ', 'P'};
	      	      
	      add(makeRadioMenu(new JMenu("Speed"), speedMenuItems, speedMenuAccel));
	      
	      String[] helpMenuItems = {"Help Contents", "|", "About"};
	      char[] helpMenuAccel = {'H', ' ', 'A'};
	      
	      add(makeMenu(new JMenu("Help"), helpMenuItems, helpMenuAccel));
	      
	      
	   }
	   
	   protected void processSelection(String selection){
		   if (selection.equalsIgnoreCase("exit")) {
			   System.exit(1);
		   }
		   else if (selection.equalsIgnoreCase("Help Contents")) {
			   if (!getTetrisComponent().isPaused()) {
				   getTetrisComponent().pauseGame();
				   statusBar.setMessage("Game Paused");
			   }
			   new HelpWindow(getSelf());
		   }
		   else if (selection.equalsIgnoreCase("about")) {
			   if (!getTetrisComponent().isPaused()) {
				   getTetrisComponent().pauseGame();
				   statusBar.setMessage("Game Paused");
			   }
			   JOptionPane.showMessageDialog(this, getTitle() + " (Xross Platform Tetris) - Copyright 2007 - Ben Inc. - The Fictional Corporation of Benjamin Frisch", getTitle(), JOptionPane.INFORMATION_MESSAGE); 
		   }
		   else if (selection.equals("New Game")) {
			   getTetrisComponent().reset();
		   }
		   else if (selection.equals("Pause Game")) {
			   getTetrisComponent().pauseGame();
			   if (getTetrisComponent().isPaused()) {
				   JOptionPane.showMessageDialog(this, "Tetris Game Pause", getTitle(), JOptionPane.INFORMATION_MESSAGE);
				   statusBar.setMessage("Game Paused");
			   }
			   else {
				   statusBar.setMessage("Game Now Being Played");
			   }
		   }
		   else if (selection.equals("Slow")) {
			   getTetrisComponent().getTimer().setDelay(500);
		   }
		   else if (selection.equals("Medium")) {
			   getTetrisComponent().getTimer().setDelay(250);
		   }
		   else if (selection.equals("Fast")) {
			   getTetrisComponent().getTimer().setDelay(100);
		   }
		   else if (selection.equals("Stopped (Cheater's Mode)")) {
			   getTetrisComponent().doNothingOnTimer();
		   }
	   }
	   

}}

class HelpWindow extends XPnDialog {
	private static final long serialVersionUID = 1L;
	public HelpWindow (javax.swing.JFrame frame) {super(frame);}
	public void init() {
		setTitle("Help Contents");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new XPnCenteredLabel("Select What You Want Help With"), java.awt.BorderLayout.NORTH);
		addButtons(new String[]{"Playing the Game", "Saving the Game", "Open a Past Game"});
		addButtons(new String[]{"Viewing High Scores", "Changing the Block Falling Speed", "Exiting the Program"});
		add(new XPnCenteredLabel(" "), java.awt.BorderLayout.SOUTH);
		addButtons(new String[]{"Close Help Window"});
		setResizable(false);
	}
	
	public void onButtonPressed(String label) {
		if (label.equals("Close Help Window")) {
			this.dispose();
		}
		else if (label.equals("Playing the Game")) {
			JOptionPane.showMessageDialog(this, "Game Play is like regualar tetris: " +
					"Line the blocks up in rows, you lose if a piece can not fully enter the board." +
					"\n\nKey Commands:\nDown Arrow: Piece Falls a Level\n" +
					"\nLeft Arrow: Move Piece Left\n" +
					"Right Arrow: Move Piece Right\n" +
					"Enter: Drop Piece to Bottom of Board", label + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (label.equals("Saving the Game")) {
			JOptionPane.showMessageDialog(this, "Go to the File Menu and select Save Game, or Click on the Save Game Button", 
					label + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (label.equals("Open a Past Game")) {
			JOptionPane.showMessageDialog(this, "Go to the File Menu and select Open Game, or Click on the Open Game Button", 
					label  + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (label.equals("Viewing High Scores")) {
			JOptionPane.showMessageDialog(this, "Click on the High Scores Button", 
					label + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
			
		}
		else if (label.equals("Changing the Block Falling Speed")) {
			JOptionPane.showMessageDialog(this, "Go to the Speed Menu and Select the Block Falling Speed Desired", 
					label + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (label.equals("Exiting the Program")) {
			JOptionPane.showMessageDialog(this, "Click on the Exit Button or Go to the File Menu and Select Exit", 
					label + " " + Strings.title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
