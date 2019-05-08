package windows;

import java.util.ArrayList;
import java.util.Random;

public class Chords {
	
	public ArrayList<Integer> mainScale;
	public ArrayList<int[]> noteList; 
	public int[][] triads;
	
	
	//constructor
	public Chords() {
		mainScale = new ArrayList<Integer>();
		noteList = new ArrayList<int[]>(); 
		triads = new int[7][3];
	}
	
	//generate the main scale
	public void generateMainScale(String key, String scale){
		mainScale.clear();
		int tone = 0;

		// search the index from the first note (middle notes (3)) 
		for (int i=48; i<=59; i++){
			if (Note.name.get(i).equals(key)){
				tone = i;
			}
		}
		
		if (scale.equals("Major")){
			mainScale.add(tone); 
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+1;			// S
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);		
		}
		
		if (scale.equals("Minor")){
			mainScale.add(tone); 
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+1;			// S
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);
			tone = tone+1;			// S
			mainScale.add(tone);
			tone = tone+2;			// T
			mainScale.add(tone);		
		}
		generateTriads();
	}
	
	//generate all common triads for the key [chord number][ground,third,fifth] = tone number
	public void generateTriads(){
		triads[0][0] = mainScale.get(0);
		triads[0][1] = mainScale.get(2);
		triads[0][2] = mainScale.get(4);
		triads[1][0] = mainScale.get(1);
		triads[1][1] = mainScale.get(3);
		triads[1][2] = mainScale.get(5);
		triads[2][0] = mainScale.get(2);
		triads[2][1] = mainScale.get(4);
		triads[2][2] = mainScale.get(6);
		triads[3][0] = mainScale.get(3);
		triads[3][1] = mainScale.get(5);
		triads[3][2] = mainScale.get(0);
		triads[4][0] = mainScale.get(4);
		triads[4][1] = mainScale.get(6);
		triads[4][2] = mainScale.get(1);
		triads[5][0] = mainScale.get(5);
		triads[5][1] = mainScale.get(0);
		triads[5][2] = mainScale.get(2);
		triads[6][0] = mainScale.get(6);
		triads[6][1] = mainScale.get(1);
		triads[6][2] = mainScale.get(3);
	}
	
	//generate simply random chords sequence from key
	public ArrayList<int[]> generateRandomChords(int barsAmount, int timeSignature){	
		ArrayList<int[]> chordsList = new ArrayList<int[]>(); 		
		for (int i=0; i<barsAmount; i++){
			int[] chordInfo = new int[4];
			Random rn = new Random();
			chordInfo[0] = rn.nextInt(6);		//chord number
			chordInfo[1] = i;					//start position (in bars) from chord (in this case only one chord per bar)(starts from 0)
			chordInfo[2] = 0;					//start position in the bar (in 1/16 step) (0,1,2,3,4,5...,15)
			chordInfo[3] = timeSignature*4;		//how long it will be played (in 1/16 step) (in this case 1 bar for every chord)
			chordsList.add(chordInfo);
		}
		ArrayList<int[]> toReturn = chordToNote(chordsList, timeSignature);
		return toReturn;
	}
	
	//convert chord list in note list for the midifile creator
	public ArrayList<int[]> chordToNote(ArrayList<int[]> chordsList, int timeSignature){
		ArrayList<int[]> toReturn = new ArrayList<int[]>();
		for (int i=0; i<chordsList.size(); i++){
			int[] chordInfo = chordsList.get(i);			//get the chord
			
			int[] noteOneInfo = new int[4];							
			noteOneInfo[0] = triads[chordInfo[0]][0];	//get the number from chord's ground note
			noteOneInfo[1] = chordInfo[1];				//get the bar from start position (bar step)
			noteOneInfo[2] = chordInfo[2];				//get the position in the bar (1/16 step)
			noteOneInfo[3] = chordInfo[3];				//get the length from note (1/16 step)
			toReturn.add(noteOneInfo);
			
			int[] noteTwoInfo = new int[4];							
			noteTwoInfo[0] = triads[chordInfo[0]][1];	//get the number from chord's third
			noteTwoInfo[1] = chordInfo[1];				//get the bar from start position (bar step)
			noteTwoInfo[2] = chordInfo[2];				//get the position in the bar (1/16 step)
			noteTwoInfo[3] = chordInfo[3];				//get the length from note (1/16 step)
			toReturn.add(noteTwoInfo);
			
			int[] noteThreeInfo = new int[4];							
			noteThreeInfo[0] = triads[chordInfo[0]][2];	//get the number from chord's fifth
			noteThreeInfo[1] = chordInfo[1];				//get the bar from start position (bar step)
			noteThreeInfo[2] = chordInfo[2];				//get the position in the bar (1/16 step)
			noteThreeInfo[3] = chordInfo[3];				//get the length from note (1/16 step)
			toReturn.add(noteThreeInfo);
			
			int[] noteFourInfo = new int[4];							
			noteFourInfo[0] = triads[chordInfo[0]][0] -12;	//get the number from chord's ground note in an octave below
			noteFourInfo[1] = chordInfo[1];					//get the bar from start position (bar step)
			noteFourInfo[2] = chordInfo[2];					//get the position in the bar (1/16 step)
			noteFourInfo[3] = chordInfo[3];					//get the length from note (1/16 step)
			toReturn.add(noteFourInfo);			
		}		
		return toReturn;
	}
	
}
