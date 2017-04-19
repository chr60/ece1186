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

import CommonUIElements.ClockAndLauncher.Launcher;
import MBO.*;
import TrackModel.*;
import WaysideController.*;

public class CTCgui {

  private JFrame mainGUI;

  public JFrame getFrame(){
    return this.mainGUI;
  }

  Launcher launcher;
  MovingBlockOverlay MBO;
	ArrayList<WS> waysides;
	TrackModel dummyTrack;
	ArrayList<TrainManager> managerList;
  TrainManager tmanager;
  TrainManager tmanager2;
	private Border grayline;
  int lastClickedButton;
  TrainPanel trainPanel;
  TrainManagerPanel tmPanel;
  TrainManagerPanel tmPanel2;
  TrackPanel blockPanel;
  TrackModel realTrack;
  ArrayList<Block> brokenList;
  ArrayList<String> setDropdownFailure;
  JPanel failurePanel;
  JButton btnNoFailure;
  int blockNum;
  boolean failureDetected;

	/**
	 * Create the application.
	 */
	public CTCgui(Launcher launcher, ArrayList<TrainManager> tm, TrackModel dt, ArrayList<WS> ws, TrackModel globalTrack) {
    this.launcher = launcher;
    this.dummyTrack = dt;
		this.waysides = ws;
		this.managerList = tm;
    this.tmanager = managerList.get(0);
  //  this.tmanager2 = managerList.get(1);
    this.realTrack = globalTrack;
    lastClickedButton = 2;
    this.brokenList = new ArrayList<Block>();
    this.setDropdownFailure = new ArrayList<String>();
    failureDetected = false;

		grayline = BorderFactory.createLineBorder(Color.gray);

// overall frame of gui
		JFrame frame = new JFrame();
		this.mainGUI = frame;
		frame.setBounds(100, 100, 790, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

// TRACK SECTION OF GUI
		blockPanel = new TrackPanel(dummyTrack, waysides, brokenList);
		blockPanel.setBounds(402, 0, 367, 229);
		blockPanel.setBorder(grayline);
		frame.getContentPane().add(blockPanel);
		blockPanel.setLayout(null);


// DISPATCH TRAIN PANEL
		trainPanel = new TrainPanel(managerList, dummyTrack, waysides, realTrack);
		trainPanel.setBounds(402, 229, 367, 98);
		trainPanel.setBorder(grayline);
		frame.getContentPane().add(trainPanel);
		trainPanel.setLayout(null);

// image wont work in JAR currently
    // IMAGE
		//ImageIcon image = new ImageIcon(getClass().getResource("trackPicture.jpg"));
		JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
		panel.setBounds(0, 0, 390, 255);

		//panel.add(new JLabel(image), BorderLayout.CENTER);
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.setBounds(0, 256, 390, 255);

// Train manager panel
    tmPanel = new TrainManagerPanel(tmanager, dummyTrack, managerList);
    tmPanel.setBounds(0, 0, 390, 255);
    frame.getContentPane().add(tmPanel);
/*  WAIT FOR GREEN LINE TO WORK
    tmPanel2 = new TrainManagerPanel(tmanager2, dummyTrack, managerList);
    tmPanel2.setBounds(0, 256, 390, 255);
    frame.getContentPane().add(tmPanel2);
*/
// EVERYTHING ELSE PANEL
		JPanel miscPanel = new JPanel();
		miscPanel.setLayout(null);
		miscPanel.setBorder(grayline);
		miscPanel.setBounds(402, 326, 367, 85);
		frame.getContentPane().add(miscPanel);
/*
    JButton buttonShowPicture = new JButton("Show Track Pic");
    buttonShowPicture.setBounds(20, 11, 118, 23);
    miscPanel.add(buttonShowPicture);
*/
    JButton buttonShowSchedule = new JButton("Show Schedule");
    buttonShowSchedule.setBounds(20, 36, 118, 23);
    miscPanel.add(buttonShowSchedule);

    JButton buttonAllTrains = new JButton("Show All Trains");
    buttonAllTrains.setBounds(20, 58, 118, 23);
    miscPanel.add(buttonAllTrains);

    buttonShowSchedule.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(lastClickedButton == 2){ //train
          frame.getContentPane().remove(tmPanel);
          frame.getContentPane().remove(tmPanel2);
          //frame.getContentPane().add(schedulePanel);
          //frame.getContentPane().add();
          frame.validate();
          frame.repaint();
        }
/*        else if(lastClickedButton == 0){  //pic
          frame.getContentPane().remove(panel);
          //frame.getContentPane().add(schedulePanel);
          frame.validate();
          frame.repaint();
        }
*/
        else{
          // do nothing - schedule already set
        }
        lastClickedButton = 1;
      }
    });
    buttonAllTrains.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
/*
        if(lastClickedButton == 0){ //pic
          frame.getContentPane().remove(panel);
          frame.getContentPane().add(tmPanel);
          frame.validate();
          frame.repaint();
        }else
*/
        if(lastClickedButton == 1){ //sched
          //frame.getContentPane().remove(schedulePanel);
          //frame.getContentPane().remove();
          frame.getContentPane().add(tmPanel);
          frame.getContentPane().add(tmPanel2);
          frame.validate();
          frame.repaint();
        }else{
          // do nothing - train panel already set
        }
        lastClickedButton = 2;
      }
    });
/*
    buttonShowPicture.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(lastClickedButton == 2){  //train
          frame.getContentPane().remove(tmPanel);
          frame.getContentPane().add(panel);
          frame.validate();
          frame.repaint();
        }else if(lastClickedButton == 1){ //sched
          frame.getContentPane().remove(panel);
          //frame.getContentPane().add(schedulePanel);
          frame.validate();
          frame.repaint();
        }else{
          // do nothing - picture already set
        }
        lastClickedButton = 0;
      }
    });
*/

// setting modes for CTC/MBO
    JLabel lblModes = new JLabel("MODES");
    lblModes.setBounds(176, 11, 46, 14);
    miscPanel.add(lblModes);
    lblModes.setHorizontalAlignment(SwingConstants.LEFT);

    JRadioButton radioAuto = new JRadioButton("Automatic");
    radioAuto.setSelected(true);
    radioAuto.setBounds(176, 29, 78, 23);
    miscPanel.add(radioAuto);

    JRadioButton radioManual = new JRadioButton("Manual");
    radioManual.setBounds(255, 29, 73, 23);
    miscPanel.add(radioManual);


    JRadioButton radioFixed = new JRadioButton("Fixed Block");
    radioFixed.setSelected(true);
    radioFixed.setBounds(255, 61, 84, 23);
    miscPanel.add(radioFixed);

    JRadioButton radioMBO = new JRadioButton("MBO");
    radioMBO.setBounds(176, 61, 65, 23);
    miscPanel.add(radioMBO);

    ButtonGroup scheduleGroup = new ButtonGroup();
    scheduleGroup.add(radioFixed);
    scheduleGroup.add(radioMBO);

    ButtonGroup modeGroup = new ButtonGroup();
    modeGroup.add(radioAuto);
    modeGroup.add(radioManual);

    radioAuto.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String mainMode = "FB";
        radioFixed.setEnabled(true);
        radioMBO.setEnabled(true);
        radioFixed.setSelected(true);
        launcher.setMode(mainMode);
        tmPanel.setModeForPanel(mainMode);
        //tmPanel2.setModeForPanel(mainMode);
      }
    });

    radioManual.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String mainMode = "MAN";
        radioFixed.setEnabled(false);
        radioMBO.setEnabled(false);
        launcher.setMode(mainMode);
        tmPanel.setModeForPanel(mainMode);
        //tmPanel2.setModeForPanel(mainMode);
      }
    });

    radioMBO.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String mainMode = "MBO";
        launcher.setMode(mainMode);
        tmPanel.setModeForPanel(mainMode);
        //tmPanel2.setModeForPanel(mainMode);
      }
    });

    radioFixed.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String mainMode = "FB";
        launcher.setMode(mainMode);
        tmPanel.setModeForPanel(mainMode);
        //tmPanel2.setModeForPanel(mainMode);
      }
    });


// TESTING PANEL
		TestPanel testPanel = new TestPanel();
		testPanel.setBounds(402, 413, 123, 98);
		testPanel.setBorder(grayline);
		frame.getContentPane().add(testPanel);
		testPanel.setLayout(null);


//FAILURE PANEL
		failurePanel = new JPanel();
		failurePanel.setBackground(new Color(34, 139, 34));
		failurePanel.setBounds(525, 413, 245, 98);
		frame.getContentPane().add(failurePanel);
		failurePanel.setLayout(null);

    btnNoFailure = new JButton("No Failure");
    btnNoFailure.setBounds(77, 38, 89, 23);
    failurePanel.add(btnNoFailure);


    btnNoFailure.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(btnNoFailure.getText().equals("No Failure")){
          // do nothing
        }else{
          // case for when a failure occurs

          blockPanel.dropdown_line.setSelectedItem(setDropdownFailure.get(0));
          blockPanel.dropdown_segment.setSelectedItem(setDropdownFailure.get(1));
          blockPanel.dropdown_block.setSelectedItem(setDropdownFailure.get(2));
          try{
            blockNum = Integer.parseInt(setDropdownFailure.get(2));
          }catch(NumberFormatException num){
            blockNum = dummyTrack.getSection(setDropdownFailure.get(0), setDropdownFailure.get(1)).keySet().toArray(new Integer [0])[0];
          }

          blockPanel.setBlockWS(dummyTrack, setDropdownFailure.get(0), setDropdownFailure.get(1), blockNum);
        }
      }
    });

	}

// have mode live here to update in MBO
	public void setMode(){

	}

	public String getMode(){
		return null;
	}

  public void setMBO(MovingBlockOverlay mbo){
    this.MBO = mbo;
  }

  public TrainPanel getTrainPanel(){
    return trainPanel;
  }

  public TrackPanel getTrackPanel(){
    return blockPanel;
  }

  public TrainManagerPanel getTrainManagerPanel(){
    return tmPanel;
  }

  public JPanel getFailurePanel(){
    return failurePanel;
  }

  public ArrayList<Block> getBrokenList(){
    return brokenList;
  }

  public ArrayList<String> getDropdownFailList(){
    return setDropdownFailure;
  }

  public JButton getFailureButton(){
    return btnNoFailure;
  }

  public ArrayList<WS> getWSList(){
    return waysides;
  }

  public void getTrackFailuresWS(){
    int numWaysides = waysides.size();
    ArrayList<Block> listFromWS = new ArrayList<Block>();
    for(int x=0; x<numWaysides; x++){
      listFromWS = waysides.get(x).checkForBroken();
      if(listFromWS.size()>0){
        for(int y=0; y<listFromWS.size(); y++){
          if(!brokenList.contains(listFromWS.get(y))){
            brokenList.add(listFromWS.get(y));
          }
        }

      }
    }
      setFailureBackground();
      setDropdownFailure = setFailureButton();
  }

  private void setFailureBackground(){
    if(brokenList.size() > 0){
      failurePanel.setBackground(new Color(255, 0, 0));
    }else{
      failurePanel.setBackground(new Color(34, 139, 34));
    }
  }

  private ArrayList<String> setFailureButton(){
    ArrayList<String> brokenBlockText = new ArrayList<String>();
    if(brokenList.size() > 0){
      String blockLine = brokenList.get(0).getBlockLine();
      brokenBlockText.add(blockLine);
      String blockSection = brokenList.get(0).getBlockSection();
      brokenBlockText.add(blockSection);
      Integer blockNumber = brokenList.get(0).blockNum();
      brokenBlockText.add(blockNumber.toString());
      btnNoFailure.setText(blockLine + ", " + blockSection + ", " + blockNumber.toString());
    }else{
      btnNoFailure.setText("No Failure");
    }

    return brokenBlockText;
  }


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

*/



}
