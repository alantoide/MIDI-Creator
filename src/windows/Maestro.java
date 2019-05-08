package windows;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class Maestro {
	
	String key;
	String scale;
	String styleDrums;
	String soloFilePatch;
	String chordsFilePatch;
	String drumsFilePatch;
	int bars;
	int timeSignature;
	int bpm;
	int instrumentChords;
	int instrumentMelody;
	int instrumentRhythm;
	ArrayList<int[]> chords;
	ArrayList<int[]> melody;
	ArrayList<int[]> rhythm;

	Note note;
	Chords harmonicField;
	Rhythm drums; 
	
	MidiPlayer soloPlayer;
	MidiPlayer chordsPlayer;
	MidiPlayer drumsPlayer;
	
	static boolean isNewDrums = false;
	static boolean isNewChords = false;
	static boolean isNewMelody = false;
	
	//constructor
	public Maestro(String key, String scale, int bars, String tempo, int bpm, String instMelody,String instChords, String styleDrums) throws MidiUnavailableException {
		
		this.soloFilePatch = "Melody_Track.mid";
		this.chordsFilePatch = "Chords_Track.mid";
		this.drumsFilePatch = "Drums_Track.mid";
		this.styleDrums = styleDrums;
		this.key = key + "3";  //3 for third octave
		this.scale = scale;
		this.bars = bars;
		this.bpm = bpm;
		this.timeSignature = tempoToTimeSignature(tempo); // time signature (4 = 4/4, 3 = 3/4)
		this.instrumentChords = intrumentStringtoInt(instChords);
		this.instrumentMelody = intrumentStringtoInt(instMelody);
		this.instrumentRhythm = 1;  //standard drums in channel 10
		this.chords = new ArrayList<int[]>();
		this.melody = new ArrayList<int[]>();
		this.rhythm = new ArrayList<int[]>();
		
		note = new Note();
		harmonicField = new Chords();
		drums = new Rhythm();
		
		soloPlayer = new MidiPlayer();
		chordsPlayer = new MidiPlayer();
		drumsPlayer = new MidiPlayer();
	}
	
	
	//convert tempo String to int: 4/4 -> 4 and 3/4 -> 3
	public int tempoToTimeSignature(String s) {
		char[] c = s.toCharArray();
		int toReturn = Integer.parseInt(String.valueOf(c[0]));		
		return toReturn; 
	}
	
	//convert instrument name to number (0 to 127)
	public int intrumentStringtoInt(String s) {
		int toReturn = 0;
		switch (s) {
			case "Piano": toReturn=1; break;
			case "Guitar": toReturn=25; break;
			case "Violin": toReturn=41; break;
			case "Strings": toReturn=49; break;
			case "Trumpet": toReturn=57; break;
			case "Saxophone": toReturn=65; break;
			case "Xylophone": toReturn=14; break;			
		}
		return toReturn;
	}

	public void newChordTrack() throws InvalidMidiDataException{
		harmonicField.generateMainScale(key, scale);
		chords.clear();
		chords = harmonicField.generateRandomChords(bars,timeSignature);
		MidiFile file = new MidiFile("anythingButDrumsFlag",chordsFilePatch, bpm,instrumentChords,chords);
		chordsPlayer.loadMidiFile(chordsFilePatch);
	}
	
	public void newSoloTrack() throws InvalidMidiDataException{		
		melody.clear();
		System.out.println("New solo muss noch implementiert werden!");
	}
	
	public void newDrumsTrack() throws InvalidMidiDataException{
		rhythm.clear();
		rhythm = drums.generateRhythm(styleDrums, timeSignature, bars);
		MidiFile file = new MidiFile("drumsFlag",drumsFilePatch, bpm, 1, rhythm);
		drumsPlayer.loadMidiFile(drumsFilePatch);
	}
	
}
