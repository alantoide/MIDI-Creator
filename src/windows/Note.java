package windows;

import java.util.ArrayList;

public class Note {

	public static ArrayList<String> name = new ArrayList<String>();

	//constructor
	public Note(){
		for (int i=-1; i<=9 ;i++){
			String noteName = "C" + i;
			name.add(noteName);
			noteName = "C#" + i;
			name.add(noteName);
			noteName = "D" + i;
			name.add(noteName);
			noteName = "D#" + i;
			name.add(noteName);
			noteName = "E" + i;
			name.add(noteName);
			noteName = "F" + i;
			name.add(noteName);
			noteName = "F#" + i;
			name.add(noteName);
			noteName = "G" + i;
			name.add(noteName);
			if (name.size()==128){break;}
			noteName = "G#" + i;
			name.add(noteName);
			noteName = "A" + i;
			name.add(noteName);
			noteName = "A#" + i;
			name.add(noteName);
			noteName = "B" + i;
			name.add(noteName);
		}
		//for (int i=0; i<name.size(); i++){
		//	System.out.println(name.get(i) + " " + i);
		//}
	}
}
