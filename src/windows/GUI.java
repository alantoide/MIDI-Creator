package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI implements ActionListener, ChangeListener {

	Maestro maestro; //controller to build, change and play midi files
	
	JFrame mainW;
	
	JMenuBar menuBar;
	JMenu file;
	JMenu info;
	JMenu exportMidi;
	JMenuItem exportSolo;
	JMenuItem exportChords;
	JMenuItem exportDrums;
	JMenuItem exportAll;
	JMenuItem exit;
	JMenuItem about;
	
	JPanel content;
	JPanel panelUp;
	JPanel panelDown;
	JPanel paneSolo;
	JPanel paneChords;
	JPanel paneDrums;
	JPanel paneSettings;
	JPanel paneLogo;
	JPanel paneNotes;
	JPanel paneControl;
	JPanel paneNotesSolo;
	JPanel paneNotesChords;
	JPanel paneNotesDrums;
	JPanel paneNotesSoloLeft;
	JPanel paneNotesChordsLeft;
	JPanel paneNotesDrumsLeft;
	
	NotesGrid gridSolo;
	NotesGrid gridChords;
	NotesGrid gridDrums;
	
	JButton newSolo;
	JButton newChords;
	JButton newDrums;
	JButton newAll;
	JButton play;
	JButton pause;
	JButton stop;
	
	JLabel picSolo1;
	JLabel picChords1;
	JLabel picDrums1;
	JLabel picSolo2;
	JLabel picChords2;
	JLabel picDrums2;
	JLabel keyInfo;
	JLabel bpm;
	JLabel descVolume;
	JLabel descInstSolo;
	JLabel descInstChords;
	JLabel descStyleDrums;
	JLabel descTempo;
	JLabel descKey;
	JLabel descScale;
	JLabel descTranspose;
	
	JComboBox instSolo;
	JComboBox instChords;
	JComboBox styleDrums;
	JComboBox tempo;
	JComboBox key;
	JComboBox scale;
	JComboBox transpose;
	
	JCheckBox soloMute;
	JCheckBox chordsMute;
	JCheckBox drumsMute;
	
	JSlider mainVolume;
	
	String[] transpValues = {"-7","-6","-5","-4","-3","-2","-1","0","+1","+2","+3","+4","+5","+6","+7"};
	String[] keyValues = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	String[] scaleValues = {"Major", "Minor"};
	String[] tempoValues = {"4/4", "3/4"};
	String[] instSoloValues = {"Piano", "Guitar", "Violin", "Strings", "Trumpet", "Saxophone", "Xylophone"};
	String[] instChordsValues = {"Piano", "Guitar", "Strings"};
	String[] styleDrumsValues = {"PopRock1", "Metronome"};
	
	
	//constructor
	public GUI () throws MidiUnavailableException {
		
		//create main window
		mainW = new JFrame("MIDI Creator");
		mainW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainW.setPreferredSize(new Dimension(800,600));
		mainW.setLocation(800, 300);
		
		//setting a new "root" content pane
		content = new JPanel(new BorderLayout());
		mainW.setContentPane(content);
		
		//menu bar
		menuBar = new JMenuBar();
		
		//menu bar -> file
		file = new JMenu("File");
			exportMidi = new JMenu("Export MIDI");
				exportSolo = new JMenuItem("Export melody track");
					exportMidi.add(exportSolo);
				exportChords = new JMenuItem("Export chords track");
					exportMidi.add(exportChords);	
				exportDrums = new JMenuItem("Export drums track");
					exportMidi.add(exportDrums);
				exportAll = new JMenuItem("Export all tracks");
					exportMidi.add(exportAll);
			file.add(exportMidi);
			exit = new JMenuItem("Exit");
				//exit.setMnemonic(KeyEvent.VK_D);
			file.add(exit);
		menuBar.add(file);
		
		//menu bar -> info
		info = new JMenu("?");
			about = new JMenuItem("About");
			info.add(about);
		menuBar.add(info);
		
		mainW.setJMenuBar(menuBar);	
		
		//create panel up
		panelUp = new JPanel(new FlowLayout());
		panelUp.setBorder(BorderFactory.createLineBorder(Color.black));
		mainW.getContentPane().add(panelUp, BorderLayout.NORTH);
		
		//create panel down
		panelDown = new JPanel (new BorderLayout());
		panelDown.setBorder(BorderFactory.createLineBorder(Color.black));
		mainW.getContentPane().add(panelDown, BorderLayout.CENTER);
		
		//panel up -> solo panel
		paneSolo = new JPanel ();
			paneSolo.setLayout(new BoxLayout(paneSolo, BoxLayout.Y_AXIS));
			paneSolo.setBorder(BorderFactory.createTitledBorder("Melody"));
			paneSolo.add(Box.createRigidArea(new Dimension(0,10)));
			descInstSolo = new JLabel("Instrument:");
				paneSolo.add(descInstSolo);
			instSolo = new JComboBox(instSoloValues);
				instSolo.addActionListener(this);
				paneSolo.add(instSolo);
			picSolo1 = new JLabel("A PICTURE");
				paneSolo.add(picSolo1);
			paneSolo.add(Box.createRigidArea(new Dimension(0,10)));
			newSolo = new JButton("NEW TRACK");
				newSolo.setPreferredSize(new Dimension(120,20));
				newSolo.addActionListener(this);
				paneSolo.add(newSolo);
		panelUp.add(paneSolo);
		
		//panel up -> chords panel
		paneChords = new JPanel ();
			paneChords.setLayout(new BoxLayout(paneChords, BoxLayout.Y_AXIS));
			paneChords.setBorder(BorderFactory.createTitledBorder("Chords"));
			paneChords.add(Box.createRigidArea(new Dimension(0,10)));
			descInstChords = new JLabel("Instrument:");
				paneChords.add(descInstChords);
			instChords = new JComboBox(instChordsValues);
				instChords.addActionListener(this);
				paneChords.add(instChords);
			picChords1 = new JLabel("A PICTURE");
				paneChords.add(picChords1);
			paneChords.add(Box.createRigidArea(new Dimension(0,10)));
			newChords = new JButton("NEW TRACK");
				newChords.setPreferredSize(new Dimension(120,20));
				newChords.addActionListener(this);
				paneChords.add(newChords);
		panelUp.add(paneChords);

		//panel up -> drums panel
		paneDrums = new JPanel ();
			paneDrums.setLayout(new BoxLayout(paneDrums, BoxLayout.Y_AXIS));
			paneDrums.setBorder(BorderFactory.createTitledBorder("Drums"));
			paneDrums.add(Box.createRigidArea(new Dimension(0,10)));
			descStyleDrums = new JLabel("Drums Style:");
				paneDrums.add(descStyleDrums);
			styleDrums = new JComboBox(styleDrumsValues);
				styleDrums.addActionListener(this);
				paneDrums.add(styleDrums);
			picDrums1 = new JLabel("A PICTURE");
				paneDrums.add(picDrums1);
			paneDrums.add(Box.createRigidArea(new Dimension(0,10)));
			newDrums = new JButton("NEW TRACK");
				newDrums.setPreferredSize(new Dimension(120,20));
				newDrums.addActionListener(this);
				paneDrums.add(newDrums);
		panelUp.add(paneDrums);
		
		//panel up -> settings panel
		paneSettings = new JPanel();
			paneSettings.setLayout(new BoxLayout(paneSettings, BoxLayout.Y_AXIS));
			paneSettings.setBorder(BorderFactory.createTitledBorder("Main Settings"));
			paneSettings.add(Box.createRigidArea(new Dimension(0,10)));	
			descTempo = new JLabel("Tempo:");
				paneSettings.add(descTempo);
			tempo = new JComboBox(tempoValues);
				tempo.setSelectedIndex(0);
				tempo.addActionListener(this);
				paneSettings.add(tempo);
			descKey = new JLabel("Key:");
				paneSettings.add(descKey);
			key = new JComboBox(keyValues);
				key.setSelectedIndex(0);
				key.addActionListener(this);
				paneSettings.add(key);
			descScale = new JLabel("Scale:");
				paneSettings.add(descScale);
			scale = new JComboBox(scaleValues);
				scale.setSelectedIndex(0);
				scale.addActionListener(this);
				paneSettings.add(scale);	
			paneSettings.add(Box.createRigidArea(new Dimension(0,10)));	
			newAll = new JButton("ALL NEW");
				newAll.setPreferredSize(new Dimension(120,20));
				newAll.addActionListener(this);
				paneSettings.add(newAll);
			paneSettings.add(Box.createRigidArea(new Dimension(0,10)));	
			descTranspose = new JLabel("Transpose actual:");
				paneSettings.add(descTranspose);
			transpose = new JComboBox(transpValues);
				transpose.setSelectedItem(transpValues[7]);
				transpose.addActionListener(this);
				paneSettings.add(transpose);
		panelUp.add(paneSettings);
		
		paneLogo = new JPanel();
			paneLogo.setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel logoImage = new JLabel("Logo Image");
			paneLogo.add(logoImage);
		panelUp.add(paneLogo);

		//panel down -> control panel
		paneControl = new JPanel();
			paneControl.setLayout(new FlowLayout());
			paneControl.setBorder(BorderFactory.createBevelBorder(0));
			keyInfo = new JLabel("Actual Key: C  Notes: C D E F G A B ");
				paneControl.add(keyInfo);
			paneControl.add(Box.createHorizontalStrut(10));
			descVolume = new JLabel("Volume:");
				paneControl.add(descVolume);
			mainVolume = new JSlider(JSlider.HORIZONTAL, 0,100,80);
				mainVolume.addChangeListener(this);
				mainVolume.setMajorTickSpacing(50);
				mainVolume.setMinorTickSpacing(10);
				mainVolume.setPaintTicks(true);
				mainVolume.setPaintLabels(false);
				paneControl.add(mainVolume);
			paneControl.add(Box.createHorizontalStrut(10));	
			bpm = new JLabel("Bpm: 120");
				paneControl.add(bpm);
			paneControl.add(Box.createHorizontalStrut(10));
			play = new JButton("PLAY");
				play.addActionListener(this);
				paneControl.add(play);
			pause = new JButton("PAUSE");
				pause.addActionListener(this);
				paneControl.add(pause);
			stop = new JButton("STOP");
				stop.addActionListener(this);
				paneControl.add(stop);
		panelDown.add(paneControl, BorderLayout.NORTH);
		
		
		//panel down -> notes panel
		paneNotes = new JPanel();
			paneNotes.setLayout(new GridLayout(0,1));
		panelDown.add(paneNotes, BorderLayout.CENTER);
		
		//panel down -> notes panel -> solo notes panel
		paneNotesSolo = new JPanel();
			paneNotesSolo.setLayout(new BorderLayout());
			paneNotesSolo.setBorder(BorderFactory.createTitledBorder("Melody Notes"));
		paneNotes.add(paneNotesSolo);
		
		//panel down -> notes panel -> solo notes panel -> solo notes left panel
		paneNotesSoloLeft = new JPanel();
			picSolo2 = new JLabel("Bild");
				paneNotesSoloLeft.add(picSolo2);
			soloMute = new JCheckBox("M");
				paneNotesSoloLeft.add(soloMute);
		paneNotesSolo.add(paneNotesSoloLeft, BorderLayout.WEST);
		
		//panel down ->notes panel -> solo notes panel -> solo notes grid panel
		gridSolo = new NotesGrid();
			paneNotesSolo.add(gridSolo, BorderLayout.CENTER);
					
		//panel down -> notes panel -> chords notes panel
		paneNotesChords = new JPanel();
			paneNotesChords.setLayout(new BorderLayout());
			paneNotesChords.setBorder(BorderFactory.createTitledBorder("Chords Notes"));
		paneNotes.add(paneNotesChords);
		
		//panel down -> notes panel -> chords notes panel -> chords notes left panel
		paneNotesChordsLeft = new JPanel();
			picChords2 = new JLabel("Bild");
				paneNotesChordsLeft.add(picChords2);
			chordsMute = new JCheckBox("M");
				paneNotesChordsLeft.add(chordsMute);
		paneNotesChords.add(paneNotesChordsLeft, BorderLayout.WEST);
				
		//panel down ->notes panel -> solo notes panel -> solo notes grid panel
			gridChords = new NotesGrid();
		paneNotesChords.add(gridChords, BorderLayout.CENTER);
				
		//panel down -> notes panel -> drums notes panel
		paneNotesDrums = new JPanel();
			paneNotesDrums.setLayout(new BorderLayout());
			paneNotesDrums.setBorder(BorderFactory.createTitledBorder("Drums Notes"));
		paneNotes.add(paneNotesDrums);		
		
		//panel down -> notes panel -> drums notes panel -> drums notes left panel
		paneNotesDrumsLeft = new JPanel();
			picDrums2 = new JLabel("Bild");
				paneNotesDrumsLeft.add(picDrums2);
			drumsMute = new JCheckBox("M");
				paneNotesDrumsLeft.add(drumsMute);
		paneNotesDrums.add(paneNotesDrumsLeft, BorderLayout.WEST);
				
		//panel down ->notes panel -> solo notes panel -> solo notes grid panel
		gridDrums = new NotesGrid();
			paneNotesDrums.add(gridDrums, BorderLayout.CENTER);
				
		mainW.pack();
		mainW.setVisible(true);
		
		//initialize maestro controller 
		maestro = new Maestro(key.getSelectedItem().toString(),scale.getSelectedItem().toString(),16,tempo.getSelectedItem().toString(),120, instSolo.getSelectedItem().toString(), instChords.getSelectedItem().toString(), styleDrums.getSelectedItem().toString());
		
	}


	//action listener
	public void actionPerformed(ActionEvent e) {
		
		//to get actual values from combo boxes:
		//String SsoloInst = instSolo.getSelectedItem().toString();
		
		if (e.getSource() == instSolo) {			
			//change picture and instrument in midi
        }
		
		if (e.getSource() == instChords) {			
			//change picture and instrument in midi
		}
        
		if (e.getSource() == styleDrums) {			
			//change drum track style
		}

		if (e.getSource() == tempo) {			
			//change timeSignature and generate all new? (fenster mit hinweis)
		}		

		if (e.getSource() == key) {			
			//change key and generate all new? (fenster mit hinweis oder erst nach all new, evtl transpose)
		}		

		if (e.getSource() == scale) {			
			//change scale and generate all new?
		}		
		
		if (e.getSource() == transpose) {			
			//new function to transpose notes
		}		
		
		if (e.getSource() == newSolo) {
        	
        }
        
        if (e.getSource() == newChords) {
        		try {maestro.newChordTrack();} catch (InvalidMidiDataException e1) {e1.printStackTrace();}
        }
        
        if (e.getSource() == newDrums) {
        		try {maestro.newDrumsTrack();} catch (InvalidMidiDataException e1) {e1.printStackTrace();}
        }
        
        if (e.getSource() == newAll) {
        	
        }
        
        if (e.getSource() == play) {
        		if(!(maestro.chords.isEmpty())){
        			maestro.chordsPlayer.sequencer.start();
        		}
        		if(!(maestro.rhythm.isEmpty())){
        			maestro.drumsPlayer.sequencer.start();
        		}
        		if(!(maestro.melody.isEmpty())){
        			maestro.soloPlayer.sequencer.start();
        		}
        }        		
        
        if (e.getSource() == pause) {
	
        }
        if (e.getSource() == stop) {
	
        }
	}

	// trigger for volume slider
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int volValue = (int)source.getValue();
	        System.out.println(volValue);
	    }
	}
	
}
