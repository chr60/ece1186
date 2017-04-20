package CTC;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.*;

import MBO.*;
import TrackModel.*;
import WaysideController.*;

public class TrainPanel extends JPanel{
	ArrayList<WS> waysides;
	ArrayList<TrainManager> trainManagers;
	TrainManager currWorkingManager;
	WS currWorkingWS;
	TrackModel DummyTrack;
	TrackModel realTrack;
	TrackPanel trackPanel;
	DispatchTrainPopup popup;
	private JLabel set_speed_label;
	private JLabel authority_label;
	private JLabel speedNUM;
	private JLabel mileshourLabel;
	private JLabel authorityNUM;
	private JLabel authMilesLabel;
	private JLabel lineLabel;
	private JLabel lineReturnLabel;
	private JComboBox<String> dropdownTrains;
	private JButton updateBtn;
	private JButton buttonDispatchTrain;
	private JLabel trainWindowLabel;
	private JButton buttonEditTrain;

	public TrainPanel(ArrayList<TrainManager> tm, TrackModel dt, ArrayList<WS> ws, TrackModel globalTrack, TrackPanel trackPanel){
		this.waysides = ws;
		this.currWorkingWS = waysides.get(0);
		this.DummyTrack = dt;
		this.trainManagers = tm;
		this.currWorkingManager = trainManagers.get(0);
		this.realTrack = globalTrack;
		this.trackPanel = trackPanel;

		set_speed_label = new JLabel("Speed");
		set_speed_label.setHorizontalAlignment(SwingConstants.RIGHT);
		set_speed_label.setBounds(160, 24, 72, 14);
		add(set_speed_label);

		authority_label = new JLabel("Authority");
		authority_label.setHorizontalAlignment(SwingConstants.RIGHT);
		authority_label.setBounds(160, 41, 72, 14);
		add(authority_label);

		speedNUM = new JLabel("#");
		speedNUM.setHorizontalAlignment(SwingConstants.CENTER);
		speedNUM.setBounds(238, 24, 45, 14);
		add(speedNUM);

		mileshourLabel = new JLabel("mi/hr");
		mileshourLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mileshourLabel.setBounds(293, 24, 45, 14);
		add(mileshourLabel);

		authorityNUM = new JLabel("#");
		authorityNUM.setHorizontalAlignment(SwingConstants.CENTER);
		authorityNUM.setBounds(238, 41, 45, 14);
		add(authorityNUM);

		authMilesLabel = new JLabel("mi");
		authMilesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		authMilesLabel.setBounds(293, 41, 45, 14);
		add(authMilesLabel);

		lineLabel = new JLabel("Line");
		lineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lineLabel.setBounds(160, 6, 72, 14);
		add(lineLabel);

		lineReturnLabel = new JLabel("-----");
		lineReturnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lineReturnLabel.setBounds(238, 6, 45, 14);
		add(lineReturnLabel);

		dropdownTrains = new JComboBox<String>();
		dropdownTrains.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String s = dropdownTrains.getSelectedItem().toString();
					int selection = Integer.parseInt(s);
					//lineReturnLabel.setText(dispatchTrainList.get(selection-1).getLine());
					//authorityNUM.setText(dispatchTrainList.get(selection-1).getAuthorityS());
					//speedNUM.setText(dispatchTrainList.get(selection-1).getSpeedS());
					//System.out.println(selection);
				}catch(NullPointerException r){
				}
			}
		});
		dropdownTrains.setBounds(25, 40, 87, 23);
		add(dropdownTrains);

		JLabel lblthroughput = new JLabel("trains/hr");
    lblthroughput.setBounds(80, 67, 119, 23);
    add(lblthroughput);

    JLabel return_throughput = new JLabel("#");
    return_throughput.setHorizontalAlignment(SwingConstants.CENTER);
    return_throughput.setBounds(25, 67, 87, 23);
    add(return_throughput);

		/*
		updateBtn = new JButton("Update List");
		updateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dropdownTrains.removeAllItems();
				//int sizeArr = sizeArrList(dispatchTrainList);
				//for(int i=0; i<sizeArr; i++){
					//dropdownTrains.addItem(dispatchTrainList.get(i).getTrainID());
				//}

			}
		});
		updateBtn.setBounds(25, 67, 87, 23);
		add(updateBtn);
		*/

		buttonDispatchTrain = new JButton("Dispatch Train");
		buttonDispatchTrain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				popup = new DispatchTrainPopup(currWorkingManager, currWorkingWS, DummyTrack, trackPanel);
			}
		});
		buttonDispatchTrain.setBounds(143, 67, 101, 23);
		add(buttonDispatchTrain);

		trainWindowLabel = new JLabel("TRAINS");
		trainWindowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trainWindowLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		trainWindowLabel.setBounds(10, 6, 78, 23);
		add(trainWindowLabel);

		buttonEditTrain = new JButton("Edit Train");
		buttonEditTrain.setBounds(254, 67, 101, 23);
		add(buttonEditTrain);

	}

	public void disableTrainPanel(boolean enabled) {
		set_speed_label.setEnabled(enabled);
		authority_label.setEnabled(enabled);
		speedNUM.setEnabled(enabled);
		mileshourLabel.setEnabled(enabled);
		authorityNUM.setEnabled(enabled);
		authMilesLabel.setEnabled(enabled);
		lineLabel.setEnabled(enabled);
		lineReturnLabel.setEnabled(enabled);
		dropdownTrains.setEnabled(enabled);
		updateBtn.setEnabled(enabled);
		buttonDispatchTrain.setEnabled(enabled);
		trainWindowLabel.setEnabled(enabled);
		buttonEditTrain.setEnabled(enabled);
	}


	// MBO calls this method to give speed and authority for that clock tick for the entire trackList
	public void updateSpeedAuthToWS(ArrayList<Block> path){
		currWorkingWS.setSpeedAuth(path);
	}

	// CTC calls this method to update occupancy from WS
	public void updateTrainPositionsToManager(ArrayList<TrainManager> trainManagerList){
		ArrayList<Block> trainPositions = currWorkingWS.getOccupancy();
		for (TrainManager tm : trainManagerList) {
			tm.updateTrainPosition(trainPositions);
		}

	}

	// CTC calls this method to update train id
	public void updateTrainIDinList(TrainManager tm, TrackModel realTrack){
		Integer updatedID = realTrack.getBlock("Red", "U", 77).getTrainId();
		ArrayList<DummyTrain> trainList = tm.getTrainList();
		tm.updateTrainId(updatedID);
	}


}
