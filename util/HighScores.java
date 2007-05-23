package util;

import gui.CenteredLabel;
import gui.IntroCsApplication;

import java.util.*;
import javax.swing.*;

/**
 * @author Benjamin Frisch
 * @version 0.1 Alpha 2
 */
public class HighScores {	
	private static TreeSet highScores = null;
	private static int numScores = 0;
	
	public static void add(String name, int score) {
		getPastHighScores().add(new ScoreData(name, score));
		numScores++;
		writeScores();
	}
	public static void clearAll() {
		
		highScores.removeAll(highScores);		
	}

	public static void showScores() {
		new HighScoreWindow();
	}
	
	private static void writeScores() {
		
	}
	
	public static TreeSet getPastHighScores() {
		if (highScores == null) {
			initalizeScoreData(); 
		}
		
		return highScores;
	}
	
	private static void initalizeScoreData () {
		 highScores = new TreeSet();
	}
	
	public static int getLowestHighScore() {
		ScoreData lowestHighScore = new ScoreData("", 0);
		java.util.Iterator highScoreIt = highScores.iterator();
		for (int i = 1; i <= 10; i++) {
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
}
class HighScoreWindow extends IntroCsApplication {
	private static final long serialVersionUID = 1L;
	private TreeSet highScores = HighScores.getPastHighScores();
		
	public void init() {
		setTitle("High Scores");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		add(new JLabel(" "));
		add(new CenteredLabel("High Scores"));
		add(new JLabel(" "));
		
		if (highScores.size() > 0) {
			int i;
			java.util.Iterator highScoreIt = highScores.iterator();
			for (i = 1; i <= 10; i++) {
				if (highScoreIt.hasNext()) {
					ScoreData score = (ScoreData) highScoreIt.next();
					add(new CenteredLabel(i + ". " + score.getName() + " : " + score.getScore()));
				}
				else {
					break;
				}
			}
			
			for (; i <=10; i++) {
				add(new CenteredLabel(i + ". "));
			}
			
			add(new JLabel(" "));
			addButtons(new String[]{"   Clear Scores   ", "   Close   "});
		}
		else {
			add (new CenteredLabel("No High Scores"));
			add(new JLabel(" "));
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