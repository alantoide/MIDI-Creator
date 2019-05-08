package windows;

import java.util.ArrayList;
import java.util.Random;

public class Rhythm {

	int bd = 36;
	int snare = 38;
	int hh = 42;  //closed hh
	
	//constructor
	public Rhythm(){
		
	}
	
	//build a straight pop rock rhythm
	public ArrayList<int[]> generateRhythm(String style, int timeSignature, int barsAmount){
		ArrayList<int[]> toReturn = new ArrayList<int[]>();
		
		if (style.equals("PopRock1")){
			for (int i=0; i<barsAmount; i++){
				int[] bd1 = new int[4];
				bd1[0] = bd;					//note number
				bd1[1] = i;					//start position (in bars)
				bd1[2] = 0;					//start position in the bar (in 1/16 step) (0,1,2,3,4,5...,15)
				bd1[3] = 1;					//how long it will be played (in 1/16 step)
				toReturn.add(bd1);
				int[] bd2 = new int[4];
				bd2[0] = bd;					
				bd2[1] = i;					
				bd2[2] = 8;					
				bd2[3] = 1;					
				toReturn.add(bd2);
				int[] bd3 = new int[4];
				bd3[0] = bd;					
				bd3[1] = i;					
				bd3[2] = 10;					
				bd3[3] = 1;					
				toReturn.add(bd3);
				int[] sn1 = new int[4];
				sn1[0] = snare;					
				sn1[1] = i;					
				sn1[2] = 4;					
				sn1[3] = 1;					
				toReturn.add(sn1);
				int[] sn2 = new int[4];
				sn2[0] = snare;					
				sn2[1] = i;					
				sn2[2] = 12;					
				sn2[3] = 1;					
				toReturn.add(sn2);
			}
		}
		
		return toReturn;
	}
}
