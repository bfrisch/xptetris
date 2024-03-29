package gui;

/**
 * @author Benjamin Frisch
 * @version 0.9 Alpha 9
 */

import javax.swing.*;

import util.*;
import xpn.*;

import java.awt.*;

public class TetrisGUI extends XPnFrame {
	private static final long serialVersionUID = 1L;
	private TetrisComponent tetrisComponent;
	XPnStatusBar statusBar = new XPnStatusBar(XPnStringBundle.getString("playing"));
  
	public void init() {
		setTitle(XPnStringBundle.getString("title") + " " + XPnStringBundle.getString("version"));
		add(tetrisComponent = new TetrisComponent());
		addButtons(new String[]{XPnStringBundle.getString("playPause"), XPnStringBundle.getString("new"), /*XPnStringBundle.getString("save"), XPnStringBundle.getString("open"),*/ XPnStringBundle.getString("highScores"), XPnStringBundle.getString("exit")});
		
		this.setJMenuBar(new MainMenu());
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 300));
		this.setSize(500, 500);
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(XPnResourceLoader.getResource("res.TetrisIcon.png")));
	}
  
	public TetrisComponent getTetrisComponent() {
		return tetrisComponent;
	}
	
	public void onButtonPressed(String label) {
		if (label.equals(XPnStringBundle.getString("exit"))) {
			System.exit(1);
		}
		else if (label.equals(XPnStringBundle.getString("highScores"))) {
			HighScores.showScores();
		}
		else if (label.equals(XPnStringBundle.getString("new"))) {
			statusBar.setMessage(XPnStringBundle.getString("playing"));
			getTetrisComponent().reset();
		}
		else if (label.equals(XPnStringBundle.getString("playPause"))) {
			getTetrisComponent().pauseGame();
			if (getTetrisComponent().isPaused()) {
				statusBar.setMessage(XPnStringBundle.getString("paused"));
			}
			else {
				statusBar.setMessage(XPnStringBundle.getString("playing"));
			}
		}
	}
	
	public TetrisGUI getSelf() {
		return this;
	}

	class MainMenu extends xpn.XPnMenu {
	   private static final long serialVersionUID = 1L;
		
	   public MainMenu() {
	      String[] gameMenuItems = {XPnStringBundle.getString("new"), XPnStringBundle.getString("playPause"), "|",/* XPnStringBundle.getString("Save"), XPnStringBundle.getString("Open"), "|", */ XPnStringBundle.getString("exit")};
	      char[] gameMenuAccel = {'N', 'P', ' ', /*'S', 'O', ' ',*/ 'X'};
	      
	      add(makeMenu(new JMenu(XPnStringBundle.getString("game")), gameMenuItems, gameMenuAccel));
	      
	      String[] speedMenuItems = {XPnStringBundle.getString("slow"), XPnStringBundle.getString("medium"), XPnStringBundle.getString("fast"), "|", XPnStringBundle.getString("stopped")}; 
	      char[] speedMenuAccel = {'L', 'M', 'F',  ' ', 'C'};
	      	      
	      add(makeRadioMenu(new JMenu(XPnStringBundle.getString("speed")), speedMenuItems, speedMenuAccel));
	      
	      String[] soundMenuItems = {XPnStringBundle.getString("silent"), "|", XPnStringBundle.getString("birthday"), XPnStringBundle.getString("dowadidi"), XPnStringBundle.getString("footloose"), XPnStringBundle.getString("sender"), XPnStringBundle.getString("wildthing")};
	      char[] soundMenuAccel = {'Q', ' ', 'B', 'D', 'F', 'S', 'W'};
	      	      
	      add(makeRadioMenu(new JMenu(XPnStringBundle.getString("sound")), soundMenuItems, soundMenuAccel));
	      
	      String[] optionsMenuItems = {XPnStringBundle.getString("nextPiecesOpt")};
	      char[] optionsMenuAccel = {'E'};
	      	      
	      add(makeMenu(new JMenu(XPnStringBundle.getString("options")), optionsMenuItems, optionsMenuAccel));
	      
	      	      
	      
	      String[] helpMenuItems = {XPnStringBundle.getString("contents"), "|", XPnStringBundle.getString("about")};
	      char[] helpMenuAccel = {'H', ' ', 'A'};
	      
	      add(makeMenu(new JMenu(XPnStringBundle.getString("help")), helpMenuItems, helpMenuAccel));
	   }
	   
	   protected void processSelection(String selection){
		  if (selection.equals(XPnStringBundle.getString("new"))) {
			   statusBar.setMessage(XPnStringBundle.getString("playing"));
			   getTetrisComponent().reset();
		  }
		  else if (selection.equals(XPnStringBundle.getString("playPause"))) {
			  getTetrisComponent().pauseGame();
			  if (getTetrisComponent().isPaused()) {
				   JOptionPane.showMessageDialog(this, XPnStringBundle.getString("paused"), getTitle(), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
				   statusBar.setMessage(XPnStringBundle.getString("paused"));
			   }
			   else {
				   statusBar.setMessage(XPnStringBundle.getString("playing"));
			   }
		  }
		  else if (selection.equals(XPnStringBundle.getString("save"))) {
		  }
		  else if (selection.equals(XPnStringBundle.getString("open"))) {
		  }
		  else if (selection.equals(XPnStringBundle.getString("exit"))) {
			  System.exit(1);
		  }
		  else if (selection.equals(XPnStringBundle.getString("slow"))) {
			  getTetrisComponent().getTimer().setDelay(500);
		  }
		  else if (selection.equals(XPnStringBundle.getString("medium"))) {
			  getTetrisComponent().getTimer().setDelay(250);
		  }
		  else if (selection.equals(XPnStringBundle.getString("fast"))) {
			  getTetrisComponent().getTimer().setDelay(100);
		  }
		  else if (selection.equals(XPnStringBundle.getString("stopped"))) {
			  getTetrisComponent().doNothingOnTimer();
		  }
		  else if (selection.equals(XPnStringBundle.getString("silent"))) {
			  XPnSound.stopMidi();
		  }
		  else if (selection.equals(XPnStringBundle.getString("birthday"))) {
			  XPnSound.playMidiFromRes("res.birthday.mid", true);
		  }
		  else if (selection.equals(XPnStringBundle.getString("dowadidi"))) {
			  XPnSound.playMidiFromRes("res.dowadidi.mid", true);
		  }
		  else if (selection.equals(XPnStringBundle.getString("footloose"))) {
			  XPnSound.playMidiFromRes("res.footloose.mid", true);
		  }
		  else if (selection.equals(XPnStringBundle.getString("sender"))) {
			  XPnSound.playMidiFromRes("res.sender.mid", true);
		  }
		  else if (selection.equals(XPnStringBundle.getString("wildthing"))) {
			  XPnSound.playMidiFromRes("res.wildthing.mid", true);
		  }
		  else if (selection.equals(XPnStringBundle.getString("nextPiecesOpt"))) {
			String numberNextPiecesString = (String) JOptionPane.showInputDialog(this, "How many next pieces should be shown?", getTitle(), JOptionPane.QUESTION_MESSAGE, null, new String[]{"0","1", "2", "3", "4"}, new Integer(getTetrisComponent().getNumberNextPieces()).toString());
			
			if (numberNextPiecesString != null)
				getTetrisComponent().setNumberNextPieces(Integer.parseInt(numberNextPiecesString));
		  }
		  else if (selection.equals(XPnStringBundle.getString("contents"))) {
			  new HelpWindow(getSelf());
		  }
		  else if (selection.equals(XPnStringBundle.getString("about"))) {
			  JOptionPane.showMessageDialog(this, getTitle() + " - " + XPnStringBundle.getString("aboutMsg"), XPnStringBundle.getString("about") + " - " + getTitle(), JOptionPane.INFORMATION_MESSAGE);
		  }
	   }
	}
}

class HelpWindow extends XPnDialog {
	private static final long serialVersionUID = 1L;
	public HelpWindow (javax.swing.JFrame frame) {super(frame);}
	public void init() {
		setTitle(XPnStringBundle.getString("contents"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new XPnCenteredLabel(XPnStringBundle.getString("helpDesc")), java.awt.BorderLayout.NORTH);
		addButtons(new String[]{XPnStringBundle.getString("playing"), XPnStringBundle.getString("saving"), XPnStringBundle.getString("opening")});
		addButtons(new String[]{XPnStringBundle.getString("viewingScores"), XPnStringBundle.getString("changingSpeed"), XPnStringBundle.getString("exiting")});
		add(new XPnCenteredLabel(" "), java.awt.BorderLayout.SOUTH);
		addButtons(new String[]{XPnStringBundle.getString("closeHelp")});
		setResizable(false);
		//this.setIconImage(Toolkit.getDefaultToolkit().createImage(new res.ResourceLoader().getResource("TetrisIcon.png")));
	}
	
	public void onButtonPressed(String label) {
		if (label.equals(XPnStringBundle.getString("closeHelp"))) {
			this.dispose();
		}
		else if (label.equals(XPnStringBundle.getString("playing"))) {
			showHelpMessage(XPnStringBundle.getString("playingMsg1") + "\n" + 
					XPnStringBundle.getString("playingMsg2") + "\n" + 
					XPnStringBundle.getString("playingMsg3") + "\n" +
					XPnStringBundle.getString("playingMsg4") + "\n" + 
					XPnStringBundle.getString("playingMsg5") + "\n" +
					XPnStringBundle.getString("playingMsg6") + "\n" +
					XPnStringBundle.getString("playingMsg7") + "\n" +
					XPnStringBundle.getString("playingMsg8")
					, label);
		}
		else if (label.equals(XPnStringBundle.getString("saving"))) {
			showHelpMessage(XPnStringBundle.getString("savingMsg"), label);
		}
		else if (label.equals(XPnStringBundle.getString("opening"))) {
			showHelpMessage(XPnStringBundle.getString("openingMsg"), label);
		}
		else if (label.equals(XPnStringBundle.getString("viewingScores"))) {
			showHelpMessage(XPnStringBundle.getString("viewingScoresMsg"), label);			
		}
		else if (label.equals(XPnStringBundle.getString("changingSpeed"))) {
			showHelpMessage(XPnStringBundle.getString("changingSpeedMsg"), label);
		}
		else if (label.equals(XPnStringBundle.getString("exiting"))) {
			showHelpMessage(XPnStringBundle.getString("exitingMsg"),  label);
		}
	}

	private void showHelpMessage(String message, String label) {
		JOptionPane.showMessageDialog(this, message, label + " " + XPnStringBundle.getString("help") + " - " + XPnStringBundle.getString("title") + " " + XPnStringBundle.getString("version"), JOptionPane.INFORMATION_MESSAGE);
	}
}
