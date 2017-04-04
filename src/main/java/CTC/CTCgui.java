package CTC;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.*;
import java.awt.*;

import MBO.*;
import TrackModel.*;
import WaysideController.*;

public class CTCgui {

  private JFrame mainGUI;

  public JFrame getFrame(){
    return this.mainGUI;
  }

	ArrayList<WS> waysides;
	TrackModel dummyTrack;
	ArrayList<TrainManager> tmanagers;
	private Border grayline;


	/**
	 * Create the application.
	 */
	public CTCgui(ArrayList<TrainManager> tmanagers, TrackModel dummyTrack, ArrayList<WS> waysides) {
		this.dummyTrack = dummyTrack;
		this.waysides = waysides;
		this.tmanagers = tmanagers;

		grayline = BorderFactory.createLineBorder(Color.gray);

		JFrame frame = new JFrame();
		this.mainGUI = frame;
		frame.setBounds(100, 100, 790, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

// TRACK SECTION OF GUI
		TrackPanel blockPanel = new TrackPanel(dummyTrack, waysides);
		blockPanel.setBounds(402, 0, 367, 229);
		blockPanel.setBorder(grayline);
		frame.getContentPane().add(blockPanel);
		blockPanel.setLayout(null);


// DISPATCH TRAIN PANEL
		TrainPanel trainPanel = new TrainPanel();
		trainPanel.setBounds(402, 229, 367, 98);
		trainPanel.setBorder(grayline);
		frame.getContentPane().add(trainPanel);
		trainPanel.setLayout(null);


// EVERYTHING ELSE PANEL
		MiscPanel miscPanel = new MiscPanel();
		miscPanel.setLayout(null);
		miscPanel.setBorder(grayline);
		miscPanel.setBounds(402, 326, 367, 85);
		frame.getContentPane().add(miscPanel);


// TESTING PANEL
		TestPanel testPanel = new TestPanel();
		testPanel.setBounds(402, 413, 123, 98);
		testPanel.setBorder(grayline);
		frame.getContentPane().add(testPanel);
		testPanel.setLayout(null);


//FAILURE PANEL
		FailurePanel failurePanel = new FailurePanel();
		failurePanel.setBackground(new Color(34, 139, 34));
		failurePanel.setBounds(525, 413, 245, 98);
		frame.getContentPane().add(failurePanel);
		failurePanel.setLayout(null);



// IMAGE
		ImageIcon image = new ImageIcon(getClass().getResource("trackPicture.jpg"));
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 390, 511);
		frame.getContentPane().add(panel);
		panel.add(new JLabel(image));

// connections between panels
		miscPanel.setTrainPanel(trainPanel);


	}

// have mode live here to update in MBO
	public void setMode(){

	}

	public String getMode(){
		return null;
	}

//

/*
	public void checkMode(String mode, TrainPanel panel){
		// deactivates entire train panel if automatic mode is selected
		System.out.println(mode);
		if(mode==null) {
			mode = "auto";
		}
		if(mode.equals("auto")){
			//panel.disableTrainPanel(false);
		}else if(mode.equals("manual")){
			//panel.disableTrainPanel(true);
		}
	}


//updating trainPositions from WS to trainManager
		public void updateTrainPosition(TrainManager tm){
      ArrayList<DummyTrain> trainList = tm.getTrainList();
      ArrayList<Block> occList = tm.getOccupancyList();
	    for(int i=0; i<trainList.size(); i++){
	      Block posTrainI = trainList.get(i).getPosition();
	      for(int k=0; k<occList.size(); k++){
	        Block newOccBlockID = occList.get(k);
	        if(posTrainI newOccBlockID){
	          trainList.get(i).setPostion(newOccBlockID);
	        }
	      }
	    }
		}
*/


}
