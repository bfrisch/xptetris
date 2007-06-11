package xpn;

import java.awt.Toolkit;
import javax.sound.midi.*;
import java.io.*;

/**
 * @author Benjamin Frisch
 * @author David Kosibie
 * @version 0.9 Alpha 7
 */


public class XPnSound {
	public static void beep() {
		Toolkit.getDefaultToolkit().beep(); 
	}
	
	public static void playMidi(String resLoc) {
		playMidi(new File(resLoc),false); 
	}
	 
	public static void playMidi(String resLoc, boolean loop) {
		playMidi(new File(resLoc), loop); 
	}
	
	public static void playMidi(String resLoc, Class resLoader) {
		playMidi(resLoader.getResourceAsStream(resLoc),false); 
	}
	 
	public static void playMidi(String resLoc, Class resLoader, boolean loop) {
		playMidi(resLoader.getResourceAsStream(resLoc), loop); 
	}	
	
	public static void playMidi(File fileLoc) {
		playMidi(fileLoc,false); 
	}
	
	public static void playMidi(File fileLoc, boolean loop) {
		playMidi(fileLoc,false); 
	}
	
	public static void playMidi(InputStream midiStream) {
		playMidi(midiStream,false); 
	}
	
	public static void playMidi(InputStream midiStream, boolean loop) {
		try {
			stopMidi();
			sequencer = MidiSystem.getSequencer();
			sequencer.setSequence(MidiSystem.getSequence(midiStream));
			if (loop)
				sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.open();
	        sequencer.start();
	     } catch (Exception e) {
	     	System.err.println("MidiPlayer: " + e);
	     	sequencer = null;
	     }
	}
	
	public static Sequencer getSequencer() { return sequencer; }
	
	public static void stopMidi() {
		try {
			if ((sequencer == null) || (!sequencer.isRunning())) return;
			sequencer.stop();
			sequencer.close();
		}
		catch (Exception e) {
	     	System.err.println("MidiPlayer: " + e);
		}
		sequencer = null;
	}

	private static Sequencer sequencer = null;	
}