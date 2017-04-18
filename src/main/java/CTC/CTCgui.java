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

  private MovingBlockOverlay MBO;
	ArrayList<WS> waysides;
	TrackModel dummyTrack;
	ArrayList<TrainManager> managerList;
  TrainManager tmanager;
	private Border grayline;
  int lastClickedButton;
  TrainPanel trainPanel;
  TrainManagerPanel tmPanel;
  TrackPanel blockPanel;
  TrackModel realTrack;

	/**
	 * Create the application.
	 */
	public CTCgui(ArrayList<TrainManager> tm, TrackModel dt, ArrayList<WS> ws, TrackModel globalTrack) {
		this.dummyTrack = dt;
		this.waysides = ws;
		this.managerList = tm;
    this.tmanager = managerList.get(0);
    this.realTrack = globalTrack;
    lastClickedButton = 0;

		grayline = BorderFactory.createLineBorder(Color.gray);

		JFrame frame = new JFrame();
		this.mainGUI = frame;
		frame.setBounds(100, 100, 790, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

// TRACK SECTION OF GUI
		blockPanel = new TrackPanel(dummyTrack, waysides);
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


    // IMAGE
		//ImageIcon image = new ImageIcon(getClass().getResource("trackPicture.jpg"));
		JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
		panel.setBounds(0, 0, 390, 511);
		frame.getContentPane().add(panel);
		//panel.add(new JLabel(image), BorderLayout.CENTER);

// Train manager panel
    tmPanel = new TrainManagerPanel(tmanager, dummyTrack);
    tmPanel.setBounds(0, 0, 390, 511);
/*
// MBO SCHEDULE PANEL - appears in CTC
    JPanel schedulePanel = MBOgui.getTrainSchedulePanel();
    schedulePanel.setBounds(0, 0, 390, 511);
*/
// EVERYTHING ELSE PANEL
		JPanel miscPanel = new JPanel();
		miscPanel.setLayout(null);
		miscPanel.setBorder(grayline);
		miscPanel.setBounds(402, 326, 367, 85);
		frame.getContentPane().add(miscPanel);

    JButton buttonShowPicture = new JButton("Show Track Pic");
    buttonShowPicture.setBounds(20, 11, 118, 23);
    miscPanel.add(buttonShowPicture);

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
          //frame.getContentPane().add(schedulePanel);
          frame.validate();
          frame.repaint();
        }else if(lastClickedButton == 0){  //pic
          frame.getContentPane().remove(panel);
          //frame.getContentPane().add(schedulePanel);
          frame.validate();
          frame.repaint();
        }else{
          // do nothing - schedule already set
        }
        lastClickedButton = 1;
      }
    });
    buttonAllTrains.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(lastClickedButton == 0){ //pic
          frame.getContentPane().remove(panel);
          frame.getContentPane().add(tmPanel);
          frame.validate();
          frame.repaint();
        }else if(lastClickedButton == 1){ //sched
          //frame.getContentPane().remove(schedulePanel);
          frame.getContentPane().add(tmPanel);
          frame.validate();
          frame.repaint();
        }else{
          // do nothing - picture already set
        }
        lastClickedButton = 2;
      }
    });
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
        String mainMode = "auto";
        radioFixed.setEnabled(true);
        radioMBO.setEnabled(true);

      }
    });

    radioManual.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        String mainMode = "manual";
        radioFixed.setEnabled(false);
        radioMBO.setEnabled(false);

      }
    });


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


// connections between panels
		//miscPanel.setTrainPanel(trainPanel);


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

*/


}
