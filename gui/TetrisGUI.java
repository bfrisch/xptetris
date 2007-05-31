package gui;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 5
 */

import javax.swing.*;

import util.*;
import xpn.*;

import java.awt.*;

public class TetrisGUI extends XPnFrame {
	private static final long serialVersionUID = 1L;
	private TetrisComponent tetrisComponent;
	XPnStatusBar statusBar = new XPnStatusBar(Messages.getString("TetrisGUI.0")); //$NON-NLS-1$
  
	public void init() {
		setTitle(Messages.getString("title") + " " + Messages.getString("version")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		add(tetrisComponent = new TetrisComponent());
		addButtons(new String[]{"Pause/Play Game", "New Game", /*"Save Game", "Open Game",*/ "High Scores", "Exit"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		this.setJMenuBar(new MainMenu());
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 300));
		this.setSize(500, 500);
		HighScores.add("Benjamin Frisch", Integer.MAX_VALUE); //$NON-NLS-1$
		HighScores.add("Benjamin Frisch", 1); //$NON-NLS-1$
		HighScores.add("Benjamin Frisch", 18); //$NON-NLS-1$
		
	}
  
	public TetrisComponent getTetrisComponent() {
		return tetrisComponent;
	}
	
	public void onButtonPressed(String label) {
		if (label.equals("Exit")) { //$NON-NLS-1$
			System.exit(1);
		}
		else if (label.equals("High Scores")) { //$NON-NLS-1$
			if (!getTetrisComponent().isPaused()) {
				getTetrisComponent().pauseGame();
				statusBar.setMessage("Game Paused"); //$NON-NLS-1$
			}
			HighScores.showScores();
		}
		else if (label.equals("New Game")) { //$NON-NLS-1$
			statusBar.setMessage(Messages.getString("TetrisGUI.15")); //$NON-NLS-1$
			getTetrisComponent().reset();
		}
		else if (label.equals("Pause/Play Game")) { //$NON-NLS-1$
			getTetrisComponent().pauseGame();
			if (getTetrisComponent().isPaused()) {
				JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.17"), getTitle(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
				statusBar.setMessage("Game Paused"); //$NON-NLS-1$
			}
			else {
				statusBar.setMessage(Messages.getString("TetrisGUI.19")); //$NON-NLS-1$
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
	      String[] gameMenuItems = {"New Game", "Pause Game", "|",/* "Save Game", "Open Game", "|", */"Exit"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	      char[] gameMenuAccel = {'N', 'P', ' ', 'S', 'O', ' ', 'X'};
	      
	      add(makeMenu(new JMenu("Game"), gameMenuItems, gameMenuAccel)); //$NON-NLS-1$
	      
	      String[] speedMenuItems = {Messages.getString("TetrisGUI.25"), Messages.getString("TetrisGUI.26"), "Fast", "|", "Stopped (Cheater's Mode)"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	      char[] speedMenuAccel = {'L', 'M', 'F',  ' ', 'P'};
	      	      
	      add(makeRadioMenu(new JMenu("Speed"), speedMenuItems, speedMenuAccel)); //$NON-NLS-1$
	      
	      String[] helpMenuItems = {"Help Contents", "|", "About"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	      char[] helpMenuAccel = {'H', ' ', 'A'};
	      
	      add(makeMenu(new JMenu("Help"), helpMenuItems, helpMenuAccel)); //$NON-NLS-1$
	      
	      
	   }
	   
	   protected void processSelection(String selection){
		   if (selection.equalsIgnoreCase("exit")) { //$NON-NLS-1$
			   System.exit(1);
		   }
		   else if (selection.equalsIgnoreCase("Help Contents")) { //$NON-NLS-1$
			   if (!getTetrisComponent().isPaused()) {
				   getTetrisComponent().pauseGame();
				   statusBar.setMessage("Game Paused"); //$NON-NLS-1$
			   }
			   new HelpWindow(getSelf());
		   }
		   else if (selection.equalsIgnoreCase("about")) { //$NON-NLS-1$
			   if (!getTetrisComponent().isPaused()) {
				   getTetrisComponent().pauseGame();
				   statusBar.setMessage("Game Paused"); //$NON-NLS-1$
			   }
			   JOptionPane.showMessageDialog(this, getTitle() + Messages.getString("TetrisGUI.40"), getTitle(), JOptionPane.INFORMATION_MESSAGE);  //$NON-NLS-1$
		   }
		   else if (selection.equals("New Game")) { //$NON-NLS-1$
			   getTetrisComponent().reset();
		   }
		   else if (selection.equals("Pause Game")) { //$NON-NLS-1$
			   getTetrisComponent().pauseGame();
			   if (getTetrisComponent().isPaused()) {
				   JOptionPane.showMessageDialog(this, "Tetris Game Pause", getTitle(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
				   statusBar.setMessage("Game Paused"); //$NON-NLS-1$
			   }
			   else {
				   statusBar.setMessage("Game Now Being Played"); //$NON-NLS-1$
			   }
		   }
		   else if (selection.equals("Slow")) { //$NON-NLS-1$
			   getTetrisComponent().getTimer().setDelay(500);
		   }
		   else if (selection.equals("Medium")) { //$NON-NLS-1$
			   getTetrisComponent().getTimer().setDelay(250);
		   }
		   else if (selection.equals("Fast")) { //$NON-NLS-1$
			   getTetrisComponent().getTimer().setDelay(100);
		   }
		   else if (selection.equals("Stopped (Cheater's Mode)")) { //$NON-NLS-1$
			   getTetrisComponent().doNothingOnTimer();
		   }
	   }
	   

}}

class HelpWindow extends XPnDialog {
	private static final long serialVersionUID = 1L;
	public HelpWindow (javax.swing.JFrame frame) {super(frame);}
	public void init() {
		setTitle("Help Contents"); //$NON-NLS-1$
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new XPnCenteredLabel(Messages.getString("TetrisGUI.91")), java.awt.BorderLayout.NORTH); //$NON-NLS-1$
		addButtons(new String[]{Messages.getString("TetrisGUI.90"), Messages.getString("TetrisGUI.92"), Messages.getString("TetrisGUI.93")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addButtons(new String[]{Messages.getString("TetrisGUI.55"), Messages.getString("TetrisGUI.56"), Messages.getString("TetrisGUI.57")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		add(new XPnCenteredLabel(" "), java.awt.BorderLayout.SOUTH); //$NON-NLS-1$
		addButtons(new String[]{"Close Help Window"}); //$NON-NLS-1$
		setResizable(false);
	}
	
	public void onButtonPressed(String label) {
		if (label.equals("Close Help Window")) { //$NON-NLS-1$
			this.dispose();
		}
		else if (label.equals("Playing the Game")) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.62") + //$NON-NLS-1$
					Messages.getString("TetrisGUI.63") + //$NON-NLS-1$
					Messages.getString("TetrisGUI.64") + //$NON-NLS-1$
					Messages.getString("TetrisGUI.65") + //$NON-NLS-1$
					Messages.getString("TetrisGUI.66") + //$NON-NLS-1$
					Messages.getString("TetrisGUI.67"), label + Messages.getString("TetrisGUI.68") + Messages.getString(Messages.getString("TetrisGUI.69")), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		else if (label.equals("Saving the Game")) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.71"),  //$NON-NLS-1$
					label + " " + Messages.getString(Messages.getString("TetrisGUI.73")), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if (label.equals(Messages.getString("TetrisGUI.94"))) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.75"),  //$NON-NLS-1$
					label  + " " + Messages.getString("title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if (label.equals("Viewing High Scores")) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.79"),  //$NON-NLS-1$
					label + Messages.getString("TetrisGUI.80") + Messages.getString("title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			
		}
		else if (label.equals(Messages.getString("TetrisGUI.82"))) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.83"),  //$NON-NLS-1$
					label + " " + Messages.getString("title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if (label.equals(Messages.getString("TetrisGUI.86"))) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(this, Messages.getString("TetrisGUI.87"),  //$NON-NLS-1$
					label + " " + Messages.getString("title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

}
