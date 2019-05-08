package windows;

import java.io.File;

import javax.sound.midi.*;

public class MidiPlayer {

	public Sequencer sequencer;
	
	//constructor
	public MidiPlayer () throws MidiUnavailableException{
		 try{
			 sequencer = MidiSystem.getSequencer();
			 sequencer.open();
		 }
		 catch(Exception e){
			 System.out.println("Sequencer error: " + e.toString());
		 }		 
	}
	
	//load a midi file (and play it)
	public void loadMidiFile(String filePatch){
		try{
			File myMidiFile = new File(filePatch);
			Sequence mySeq = MidiSystem.getSequence(myMidiFile);
			sequencer.setSequence(mySeq);			
			//sequencer.start(); // play 
		}
		catch (Exception e){
			System.out.println("MIDI load error: " + e.toString());
		}
		
	}
	
	
}
