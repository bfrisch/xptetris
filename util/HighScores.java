package util;

import java.util.*;
import javax.swing.*;
import java.io.*;

import xpn.XPnCenteredLabel;
import xpn.XPnDialog;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */
public class HighScores {	
	private static TreeSet highScores = null;
	private static int numScores = 0;
	//private static final HighScoreWindow HighScoreWindow = new HighScoreWindow();
	private static final int maxNumScores = 10;
	
	public static void add(String name, int score) {
		getPastHighScores().add(new ScoreData(name, score));
		numScores++;
		writeScores();
	}
	public static void clearAll() {
		highScores.removeAll(highScores);
		numScores = 0;
		writeScores();
	}

	public static void showScores() {
		new HighScoreWindow();
	}
	
	private static void writeScores() {
		if (highScores.size() != 0) {
			File dataFile = new File("highscores.xpr");
			
			FileWriter fw = null;
			BufferedWriter bw = null;
			 
			try {
				dataFile.createNewFile();
			 
				fw = new FileWriter(dataFile);
				bw = new BufferedWriter(fw); 
				
				if (highScores.size() > 0) {
					int i;
					java.util.Iterator highScoreIt = highScores.iterator();
					for (i = 1; i <= maxNumScores; i++) {
						if (highScoreIt.hasNext()) {
							ScoreData score = (ScoreData) highScoreIt.next();
							bw.write(score.getName());
							bw.newLine();
							bw.write(score.getScore());
						}
						else {
							break;
						}
					}
				}
				
				fw.close();
				bw.close();
			}
			catch (IOException e) {e.getStackTrace().toString();}
			finally {
				try {
					 if (fw != null) fw.close();
					 if (bw != null) bw.close();
				 }
				 catch (IOException e) {System.out.println("Error!");}
			}
		}
	}
	
	public static TreeSet getPastHighScores() {
		if (highScores == null) {
			initalizeScoreData(); 
		}
		
		return highScores;
	}
	
	private static void initalizeScoreData () {
		 highScores = new TreeSet();
		 
		 File dataFile = new File("highscores.xpr");
		 FileReader fr = null;
		 BufferedReader br = null;
		 
		 try {			 
			 fr = new FileReader(dataFile);
	         br = new BufferedReader(fr);
			 
			 String line = null;  
			 String name = null;
			 while ((line=br.readLine()) != null ) {
				 if (name == null) {
					 name = line;
				 }
				 else {
					 try {
						 int score = Integer.parseInt(line);
						 highScores.add(new ScoreData(name, score));
						 numScores++;
						 name = null;
					 }
					 catch (NumberFormatException e) {}
				 }
			 }
			 fr.close();
			 br.close();
		 }
		 catch (IOException e) {}
		 finally {
			 try {
				 if (fr != null) fr.close();
				 if (br != null) br.close();
			 }
			 catch (IOException e) {}
		 }
	}
	
	public static int getLowestHighScore() {
		ScoreData lowestHighScore = new ScoreData("", 0);
		java.util.Iterator highScoreIt = highScores.iterator();
		for (int i = 1; i <= maxNumScores; i++) {
			if (highScoreIt.hasNext()) {
				lowestHighScore = (ScoreData) highScoreIt.next();
			}
			else {
				lowestHighScore = new ScoreData("", 0);
				break;
			}
		}
		
		return lowestHighScore.getScore();
	}
	
	public static int getNumScores() {
		return numScores;
	}
	
	public static int getMaxNumScores() {
		return maxNumScores;
	}
}

class HighScoreWindow extends XPnDialog {
	private static final long serialVersionUID = 1L;
	private TreeSet highScores = HighScores.getPastHighScores();
		
	public void init() {
		setTitle("High Scores");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		this.add(new JLabel(" "));
		this.add(new XPnCenteredLabel("High Scores"));
		this.add(new JLabel(" "));
		
		if (HighScores.getNumScores() > 0) {
			int i;
			java.util.Iterator highScoreIt = highScores.iterator();
			for (i = 1; i <= HighScores.getMaxNumScores(); i++) {
				if (highScoreIt.hasNext()) {
					ScoreData score = (ScoreData) highScoreIt.next();
					this.add(new XPnCenteredLabel(i + ". " + score.getName() + " : " + score.getScore()));
				}
				else {
					break;
				}
			}
			
			for (; i <=HighScores.getMaxNumScores(); i++) {
				this.add(new XPnCenteredLabel(i + ". "));
			}
			
			this.add(new JLabel(" "));
			addButtons(new String[]{"   Clear Scores   ", "   Close   "});
		}
		else {
			this.add (new XPnCenteredLabel("No High Scores"));
			this.add(new JLabel(" "));
			addButtons(new String[]{"      Close     "});
		}
	}
		
	public void onButtonPressed(String label) {
		if (label.trim().equals("Close")) {
			this.dispose();
		}
		else if (label.trim().equals("Clear Scores")) {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the high scores?",
					"Ben's XP Tetris 2007", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			{
				HighScores.clearAll();
				HighScores.showScores();
				this.dispose();
			}
		}
	}
}

class ScoreData implements Comparable {
	private int score;
	private String name;
	
	public ScoreData(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}
	
	public int compareTo(Object obj) {
		ScoreData other = (ScoreData) obj;
		if (this.equals(other)) {
			return 0;
		}
		else if (other.getScore() < this.getScore() || other.getScore() == this.getScore() ) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	public boolean equals(Object other) {
		return (other == this);
	}
	
	
}