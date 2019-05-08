package windows;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotesGrid extends JPanel{

	int bars;
	ArrayList<String> notesArr;
	
	// constructor
	public NotesGrid() {
		super();
		bars = 16;
		notesArr = new ArrayList();
		notesArr.add("(");
		notesArr.add("N");
		notesArr.add("o");
		notesArr.add("t");
		notesArr.add("e");
		notesArr.add("s");
		notesArr.add(" ");
		notesArr.add("D");
		notesArr.add("i");
		notesArr.add("s");
		notesArr.add("p");
		notesArr.add("l");
		notesArr.add("a");
		notesArr.add("y");
		notesArr.add(")");
		notesArr.add("");

		for (int i=0; i<bars; i++) {
			JPanel panel = new JPanel();
			//panel.setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel note = new JLabel(notesArr.get(i));
			panel.add(note);
			add(panel);
		}
		
	}
}
