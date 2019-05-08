package windows;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import javax.sound.midi.*; // package for all midi classes

public class MidiFile {

	int ticks = 480; // a 1/4-note (beat) can be divide in n(Standard 480) ticks
	String trackFlag;
	
	//constructor
	public MidiFile(String trackFlag, String filePatch, int bpm, int instrument, ArrayList<int[]> notes) throws InvalidMidiDataException{
		
		this.trackFlag = trackFlag;
		try{	
			//create a new midi sequence
			Sequence sequence = new Sequence(javax.sound.midi.Sequence.PPQ, ticks);
			
			Track t = sequence.createTrack();
			t = buildTrack(t, instrument, notes, bpm, 1);

			//write midi sequence to a midi file
			File f = new File(filePatch);
			MidiSystem.write(sequence,1,f);
			}
		catch(Exception e){
			System.out.println("Exception caught " + e.toString());
		}
		
	}

	//configure a track and write the notes in it
	private Track buildTrack(Track track, int instrument, ArrayList<int[]> notes, int bpm, int channel) throws InvalidMidiDataException {

		//general MIDI sysex. Turn on general midi sound set
		byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
		SysexMessage sm = new SysexMessage();
		sm.setMessage(b, 6);
		MidiEvent me = new MidiEvent(sm, (long)0);
		track.add(me);
					
		//set tempo (meta event)
		MetaMessage mt = new MetaMessage();
		int tempoDec = 60000000/bpm;  			// a minute has 60.000.000 microseconds
		byte[] bt = ByteBuffer.allocate(4).putInt(tempoDec).array();			//convert int in byte array
		mt.setMessage(0x51 ,bt, 4); 				//0x51 code to set tempo, tempo in byte[], how many bytes
		me = new MidiEvent(mt,(long)0);
		track.add(me);
		
		//set omni on in ch1
		ShortMessage mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7D,0x00);
		me = new MidiEvent(mm,(long)0);
		track.add(me);
		
		//set poly on in ch 1
		mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7F,0x00);
		me = new MidiEvent(mm,(long)0);
		track.add(me);
				
		//set instrument
		if (trackFlag.equals("drumsFlag")){ 
			mm = new ShortMessage();
			mm.setMessage(0xC9, (byte)instrument, 0x00);  //program change code for ch10 (drums channel), instrument code (1 for standard drums)
			me = new MidiEvent(mm,(long)0);
			track.add(me);
		}
		else{
			mm = new ShortMessage();
			mm.setMessage(0xC0, (byte)instrument, 0x00);  //program change code for ch1 (instruments), instrument code
			me = new MidiEvent(mm,(long)0);
			track.add(me);
		}
		
		
		int endOfLastNote = 1;		
		//add notes to drums track (ch10)
		if (trackFlag.equals("drumsFlag")){
			for (int i=0; i<notes.size();i++){
				int[] note = notes.get(i); 
				
				int startPosition = (note[1]*ticks*4) + (note[2]*ticks/4) + 1;
				mm = new ShortMessage();
				mm.setMessage(0x99,(byte)note[0],0x60); //status (note on ch10), note, velocity 
				me = new MidiEvent(mm,(long)startPosition); //message above, note start position (ticks)
				track.add(me);
			
				int stopPosition = startPosition + ticks/4*note[3];
				mm = new ShortMessage();
				mm.setMessage(0x89,(byte)note[0],0x40);	//status(note off ch10), note
				me = new MidiEvent(mm,(long)stopPosition); //message above, note stop position (ticks)
				track.add(me);
				
				if(stopPosition>endOfLastNote){endOfLastNote = stopPosition;} //catch tick position from end of last note
			}
		}
		//add notes to chords or solo track (ch1)
		else{
			for (int i=0; i<notes.size();i++){
				int[] note = notes.get(i); 
				
				int startPosition = (note[1]*ticks*4) + (note[2]*ticks/4) + 1;
				mm = new ShortMessage();
				mm.setMessage(0x90,(byte)note[0],0x60); //status (note on ch1), note, velocity 
				me = new MidiEvent(mm,(long)startPosition); //message above, note start position (ticks)
				track.add(me);
			
				int stopPosition = startPosition + ticks/4*note[3];
				mm = new ShortMessage();
				mm.setMessage(0x80,(byte)note[0],0x40);	//status(note off ch1), note
				me = new MidiEvent(mm,(long)stopPosition); //message above, note stop position (ticks)
				track.add(me);
				
				if(stopPosition>endOfLastNote){endOfLastNote = stopPosition;} //catch tick position from end of last note
			}
		}
					
											
		//set end of track (meta event) 19 ticks later
		mt = new MetaMessage();
        byte[] bet = {}; // empty array
		mt.setMessage(0x2F,bet,0);
		me = new MidiEvent(mt, (long)endOfLastNote+ticks*4);
		track.add(me);		
		
		return track;
	}
	
	
}
