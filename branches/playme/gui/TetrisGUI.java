package gui;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import util.HighScores;

import java.awt.*;

public class TetrisGUI extends IntroCsApplication {
	private static final long serialVersionUID = 1L;
	private TetrisComponent tetrisComponent;
	StatusBar statusBar = new StatusBar("Game Now Being Played");
  
	public void init() {
		setTitle("Ben's XPTetris 2007 0.1 Alpha 3");
		add(tetrisComponent = new TetrisComponent());
		addButtons(new String[]{"Pause/Play Game", "New Game", /*"Save Game", "Open Game",*/ "High Scores", "Exit"});
		this.setJMenuBar(new MainMenu(this));
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(300, 300));
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
			HighScores.showScores();
		}
		else if (label.equals("New Game")) {
			getTetrisComponent().init();
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
}

class MainMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;
		TetrisGUI parFrame = null; 
		
	   public MainMenu(TetrisGUI frame) {
		  this.parFrame = frame;

	      String[] gameMenuItems = {"New Game", "Pause Game", "|",/* "Save Game", "Open Game", "|", */"Exit"};
	      char[] gameMenuAccel = {'N', 'P', ' ', 'S', 'O', ' ', 'X'};
	      
	      add(makeMenu(new JMenu("Game"), gameMenuItems, gameMenuAccel));
	      /*
	      String[] speedMenuItems = {"Slow", "Medium", "Fast", "|", "Stopped"};
	      char[] speedMenuAccel = {'L', 'M', 'F',  ' ', 'P'};
	      	      
	      add(makeRadioMenu(new JMenu("Speed"), speedMenuItems, speedMenuAccel));
	      */
	      String[] helpMenuItems = {"Help Contents", "|", "About"};
	      char[] helpMenuAccel = {'H', ' ', 'A'};
	      
	      add(makeMenu(new JMenu("Help"), helpMenuItems, helpMenuAccel));
	   }
	   
	   private void processSelection(String selection){
		   if (selection.equalsIgnoreCase("exit")) {
			   System.exit(1);
		   }
		   else if (selection.equalsIgnoreCase("help contents")) {
			   new HelpWindow();
		   }
		   else if (selection.equalsIgnoreCase("about")) {
			   JOptionPane.showMessageDialog(parFrame, parFrame.getTitle() + " (Xross Platform Tetris) - Copyright 2007 - Ben Inc. - The Fictional Corporation of Benjamin Frisch", parFrame.getTitle(), JOptionPane.INFORMATION_MESSAGE); 
		   }
		   else if (selection.equals("New Game")) {
			   parFrame.getTetrisComponent().init();
		   }
		   else if (selection.equals("Pause Game")) {
			   parFrame.getTetrisComponent().pauseGame();
			   if (parFrame.getTetrisComponent().isPaused()) {
				   JOptionPane.showMessageDialog(this, "Tetris Game Pause", parFrame.getTitle(), JOptionPane.INFORMATION_MESSAGE);
				   parFrame.statusBar.setMessage("Game Paused");
			   }
			   else {
				   parFrame.statusBar.setMessage("Game Now Being Played");
			   }
		   }
	   }
	   
	   public JMenu makeMenu(JMenu menu, String[] items, char[] accell) {
		   JMenu tempMenu = menu;
		   
		   ActionListener printListener = new ActionListener(  ) {
			   public void actionPerformed(ActionEvent event) {
				   processSelection(event.getActionCommand());
			   }
		   };
		   
		   for (int i = 0; i<items.length; i ++) {
			   if (!items[i].equals("|")) {
				   JMenuItem item = new JMenuItem(items[i], accell[i]);
				   item.addActionListener(printListener);
				   item.setAccelerator(KeyStroke.getKeyStroke(accell[i],
						   Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
				   tempMenu.add(item);
			   }
			   else {
				   tempMenu.addSeparator();
			   }
		   }
		   return tempMenu;
		   

	   }
	   
	   public JMenu makeRadioMenu(JMenu menu, String[] items, char[] accell) {
		   JMenu tempMenu = menu;
		   
		   ActionListener printListener = new ActionListener(  ) {
			   public void actionPerformed(ActionEvent event) {
				   processSelection(event.getActionCommand());
			   }
		   };
		   
		   ButtonGroup buttonGroup = new ButtonGroup(  );
		   
		   for (int i = 0; i<items.length; i ++) {
			   if (!items[i].equals("|")) {
				   
				   JMenuItem item = new JRadioButtonMenuItem(items[i]);
				   buttonGroup.add(item);
				   item.addActionListener(printListener);
				   item.setAccelerator(KeyStroke.getKeyStroke(accell[i],
						   Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
				   if (i == 0) {item.setSelected(true);}
				   tempMenu.add(item);
			   }
			   else {
				   tempMenu.addSeparator();
			   }
		   }
		   return tempMenu;
		   

	   }
}

class HelpWindow extends IntroCsApplication {
	private static final long serialVersionUID = 1L;
	
	public void init() {
		setTitle("Help Contents");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new CenteredLabel("Select What You Want Help With"), java.awt.BorderLayout.NORTH);
		addButtons(new String[]{"Playing the Game", "Saving the Game", "Open a Past Game"});
		addButtons(new String[]{"Viewing High Scores", "Changing the Block Falling Speed", "Exiting the Program"});
		add(new CenteredLabel(" "), java.awt.BorderLayout.SOUTH);
		addButtons(new String[]{"Close Help Window"});
		setResizable(false);
	}
	
	public void onButtonPressed(String label) {
		if (label.equals("Close Help Window")) {
			this.dispose();
		}
	}

}

class StatusBar extends JLabel {
	private static final long serialVersionUID = 1L;
	
    /** Creates a new instance of StatusBar */
    public StatusBar(String initalText) {
        super();
        super.setPreferredSize(new java.awt.Dimension(100, 16));
        super.setAlignmentX(CENTER_ALIGNMENT);
        
        setMessage(initalText);
    }
    
    public void setMessage(String message) {
        setText(" "+message);        
    }        
}