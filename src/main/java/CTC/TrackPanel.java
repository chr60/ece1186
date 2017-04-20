package CTC;

import TrackModel.*;
import WaysideController.*;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.*;
import java.awt.*;

public class TrackPanel extends JPanel{
	ArrayList<WS> waysides;
	WS currWorkingWS;
	TrackModel dummyTrack;
	Block savedBlock = null;
	Block wsBlock;
	String lineChoice = "";
	String sectionChoice = "";
	Integer blockChoice = null;
	String switchSegment1 = "";
	String switchSegment2 = "";

	//GUI elements
	JToggleButton station_no;
	JToggleButton station_yes;
	JLabel return_stn_name;
	JLabel switch_number_label;
	JLabel return_speed_limit;
	JToggleButton occupancy_yes;
	JToggleButton occupancy_no;
	JToggleButton crossing_yes;
	JToggleButton crossing_no;
	JToggleButton activated_yes;
	JToggleButton activated_no;
	JToggleButton switch_yes;
	JToggleButton switch_no;
	JToggleButton switch_label_connect1;
	JToggleButton switch_label_connect2;
	JComboBox<String> dropdown_line;
	JComboBox<String> dropdown_segment;
	JComboBox<String> dropdown_block;
	boolean failure;
	boolean trackClosed;




	public TrackPanel(TrackModel dummyTrack, ArrayList<WS> ws, ArrayList<Block> brokenList){
		this.waysides = ws;
		this.currWorkingWS = waysides.get(0);
		this.dummyTrack = dummyTrack;
		this.trackClosed = false;

		JLabel trackWindowLabel = new JLabel("TRACK");
		trackWindowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trackWindowLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		trackWindowLabel.setBounds(10, 6, 78, 23);
		add(trackWindowLabel);

		// dropdowns to select block to view
		Set<Integer> blockInts = dummyTrack.viewTrackList().get("Red").get("A").keySet();
		Integer[] intArr = blockInts.toArray(new Integer[blockInts.size()]);
		Set<String> lineSet = dummyTrack.viewTrackList().keySet();

		String[] lineName = lineSet.toArray(new String[lineSet.size()]);
		dropdown_line = new JComboBox<>();
		dropdown_line.setModel(new DefaultComboBoxModel<String>(lineName));
		dropdown_line.setBounds(121, 6, 72, 20);
		add(dropdown_line);
		dropdown_line.setToolTipText("LINE");

		String[] segName = {};
		dropdown_segment = new JComboBox<String>();
		dropdown_segment.setModel(new DefaultComboBoxModel<String>(segName));
		dropdown_segment.setBounds(203, 6, 72, 20);
		add(dropdown_segment);
		dropdown_segment.setToolTipText("SEGMENT");

		String[] blockName = {};
		dropdown_block = new JComboBox<>();
		dropdown_block.setModel(new DefaultComboBoxModel<String>(blockName));
		dropdown_block.setBounds(285, 6, 72, 20);
		add(dropdown_block);
		dropdown_block.setToolTipText("BLOCK");


		JLabel station_label = new JLabel("Station?");
		station_label.setHorizontalAlignment(SwingConstants.RIGHT);
		station_label.setBounds(26, 37, 46, 14);
		add(station_label);

		station_no = new JToggleButton("N");
		station_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		station_no.setBounds(121, 35, 45, 16);
		add(station_no);

		station_yes = new JToggleButton("Y");
		station_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		station_yes.setBounds(78, 35, 45, 16);
		add(station_yes);

		JLabel name_station_label = new JLabel("Name");
		name_station_label.setHorizontalAlignment(SwingConstants.RIGHT);
		name_station_label.setBounds(170, 37, 46, 14);
		add(name_station_label);

		return_stn_name = new JLabel("N/A");
		return_stn_name.setHorizontalAlignment(SwingConstants.LEFT);
		return_stn_name.setBounds(226, 37, 134, 14);
		add(return_stn_name);

		JLabel speed_limit_label = new JLabel("Speed Limit");
		speed_limit_label.setHorizontalAlignment(SwingConstants.RIGHT);
		speed_limit_label.setBounds(0, 62, 72, 14);
		add(speed_limit_label);

		return_speed_limit = new JLabel("#");
		return_speed_limit.setHorizontalAlignment(SwingConstants.CENTER);
		return_speed_limit.setBounds(78, 62, 45, 14);
		add(return_speed_limit);

		JLabel speed_label = new JLabel("mi/hr");
		speed_label.setHorizontalAlignment(SwingConstants.LEFT);
		speed_label.setBounds(133, 62, 45, 14);
		add(speed_label);

		JLabel crossing_label = new JLabel("Crossing?");
		crossing_label.setHorizontalAlignment(SwingConstants.RIGHT);
		crossing_label.setBounds(24, 112, 46, 14);
		add(crossing_label);

		crossing_yes = new JToggleButton("Y");
		crossing_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		crossing_yes.setBounds(76, 110, 45, 16);
		add(crossing_yes);

		crossing_no = new JToggleButton("N");
		crossing_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		crossing_no.setBounds(119, 110, 45, 16);
		add(crossing_no);

		JLabel crossing_activated_label = new JLabel("Activated?");
		crossing_activated_label.setHorizontalAlignment(SwingConstants.RIGHT);
		crossing_activated_label.setBounds(189, 114, 72, 14);
		add(crossing_activated_label);

		activated_yes = new JToggleButton("Y");
		activated_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		activated_yes.setBounds(267, 112, 45, 16);
		add(activated_yes);

		activated_no = new JToggleButton("N");
		activated_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		activated_no.setBounds(310, 112, 45, 16);
		add(activated_no);

		JLabel occupancy_label = new JLabel("Occupied?");
		occupancy_label.setHorizontalAlignment(SwingConstants.RIGHT);
		occupancy_label.setBounds(-2, 85, 72, 14);
		add(occupancy_label);

		occupancy_yes = new JToggleButton("Y");
		occupancy_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		occupancy_yes.setBounds(76, 83, 45, 16);
		add(occupancy_yes);

		occupancy_no = new JToggleButton("N");
		occupancy_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		occupancy_no.setBounds(119, 83, 45, 16);
		add(occupancy_no);

		JLabel switch_label = new JLabel("Switch?");
		switch_label.setHorizontalAlignment(SwingConstants.RIGHT);
		switch_label.setBounds(24, 139, 46, 14);
		add(switch_label);

		switch_yes = new JToggleButton("Y");
		switch_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_yes.setBounds(76, 139, 45, 16);
		add(switch_yes);

		switch_no = new JToggleButton("N");
		switch_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_no.setBounds(119, 139, 45, 16);
		add(switch_no);


/*
		JLabel switch_actual_number = new JLabel("#");
		switch_actual_number.setHorizontalAlignment(SwingConstants.RIGHT);
		switch_actual_number.setBounds(267, 139, 45, 16);
		add(switch_actual_number);


		JToggleButton flipped_yes = new JToggleButton("Y");
		flipped_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flipped_yes.setBounds(267, 135, 45, 16);
		add(flipped_yes);

		JToggleButton flipped_no = new JToggleButton("N");
		flipped_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flipped_no.setBounds(310, 135, 45, 16);
		add(flipped_no);
*/
		switch_number_label = new JLabel("Switch #");
		switch_number_label.setHorizontalAlignment(SwingConstants.RIGHT);
		switch_number_label.setBounds(205, 139, 75, 14);
		add(switch_number_label);

		JLabel switch_pos_label = new JLabel("Connect to?");
		switch_pos_label.setHorizontalAlignment(SwingConstants.RIGHT);
		switch_pos_label.setBounds(189, 164, 70, 14);
		add(switch_pos_label);

		switch_label_connect1 = new JToggleButton(switchSegment1);
		switch_label_connect1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_label_connect1.setBounds(267, 162, 45, 16);
		add(switch_label_connect1);

		switch_label_connect2 = new JToggleButton(switchSegment2);
		switch_label_connect2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_label_connect2.setBounds(310, 162, 45, 16);
		add(switch_label_connect2);

// initialize dropdowns to Red/A/1 at start up to avoid null pointer exception
		dropdown_line.setSelectedItem(lineName[0]);
		String l = (String) dropdown_line.getSelectedItem();
		Set<String> segSet = dummyTrack.viewTrackList().get(l).keySet();
		segName = segSet.toArray(new String[segSet.size()]);
		dropdown_block.removeAllItems();
		for (String item : segSet){
			dropdown_segment.addItem(item);
		}
		dropdown_segment.setSelectedItem(segName[0]);
		l = (String) dropdown_line.getSelectedItem();
		String s = (String) dropdown_segment.getSelectedItem();
		Set<Integer> blockSet = dummyTrack.viewTrackList().get(l).get(s).keySet();
		Integer[] blockInt = blockSet.toArray(new Integer[blockSet.size()]);
		dropdown_block.removeAllItems();
		for (Integer item : blockSet){
			dropdown_block.addItem(Integer.toString(item));
		}
		dropdown_block.setSelectedItem(blockInt[0]);
		String b = (String)dropdown_block.getSelectedItem();
		int blockNum;
		try{
			blockNum = Integer.parseInt(b);
		}catch(NumberFormatException num){
			blockNum = dummyTrack.getSection(l, s).keySet().toArray(new Integer [0])[0];
		}


		//wsBlock = dummyTrack.getBlock(l, s, blockNum);
		setBlockWS(dummyTrack, l, s, blockNum);
		updateTrackInfo(getBlockWS());


// all action listeners
		dropdown_line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String l = (String) dropdown_line.getSelectedItem();
				Set<String> segName = dummyTrack.viewTrackList().get(l).keySet();
				dropdown_block.removeAllItems();
				for (String item : segName){
					dropdown_segment.addItem(item);
				}

					// assign wayside via line
					for(int k=0; k<waysides.size(); k++){
						if(waysides.get(k).equals(l)){
							currWorkingWS = waysides.get(k);
						}
					}

				}

		});

		dropdown_segment.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String l = (String) dropdown_line.getSelectedItem();
				String s = (String) dropdown_segment.getSelectedItem();
				Set<Integer> blockSet = dummyTrack.viewTrackList().get(l).get(s).keySet();
				dropdown_block.removeAllItems();
				for (Integer item : blockSet)
					dropdown_block.addItem(Integer.toString(item));
				}

		});

		dropdown_block.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					String block = (String) dropdown_block.getSelectedItem();
					String section = (String) dropdown_segment.getSelectedItem();
					String line = (String) dropdown_line.getSelectedItem();
					//convert block from string to Integer
					int blockNum;
					try{
						blockNum = Integer.parseInt(block);
					}catch(NumberFormatException num){
						blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
					}

					//wsBlock = dummyTrack.getBlock(line, section, blockNum);
					setBlockWS(dummyTrack, line, section, blockNum);
					updateTrackInfo(getBlockWS());
				}

		});

		JButton changeSwitch = new JButton("Change Switch");
		changeSwitch.setBounds(50, 164, 120, 23);
		add(changeSwitch);
		changeSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String block = (String) dropdown_block.getSelectedItem();
				String section = (String) dropdown_segment.getSelectedItem();
				String line = (String) dropdown_line.getSelectedItem();
				//convert block from string to Integer
				int blockNum;
				try{
					blockNum = Integer.parseInt(block);
				}catch(NumberFormatException num){
					blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
				}

				Block switchBlockChange = dummyTrack.getBlock(line, section, blockNum);
				if(switchBlockChange.hasSwitch() == true){
					currWorkingWS.manualSwitchCTC(switchBlockChange);
				}else{
					// do nothing
				}

			}
		});

		JButton btnCloseTrack = new JButton("Close Track");
		btnCloseTrack.setBounds(77, 195, 87, 23);
		add(btnCloseTrack);
		btnCloseTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trackClosed = true;
// GETTING BLOCK CURR SELECTED
				String block = (String) dropdown_block.getSelectedItem();
				String section = (String) dropdown_segment.getSelectedItem();
				String line = (String) dropdown_line.getSelectedItem();
				//convert block from string to Integer
				int blockNum;
				try{
					blockNum = Integer.parseInt(block);
				}catch(NumberFormatException num){
					blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
				}

				Block closeThisBlock = dummyTrack.getBlock(line, section, blockNum);

				currWorkingWS.closeBlock(closeThisBlock);
			}
		});


		JButton btnPerformMaintenance = new JButton("Maintenance");
		btnPerformMaintenance.setBounds(194, 195, 93, 23);
		add(btnPerformMaintenance);
		btnPerformMaintenance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// GETTING BLOCK CURR SELECTED
								String block = (String) dropdown_block.getSelectedItem();
								String section = (String) dropdown_segment.getSelectedItem();
								String line = (String) dropdown_line.getSelectedItem();
								//convert block from string to Integer
								int blockNum;
								try{
									blockNum = Integer.parseInt(block);
								}catch(NumberFormatException num){
									blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
								}

								Block sendMaintToBlock = dummyTrack.getBlock(line, section, blockNum);

				if(trackClosed == true){
					currWorkingWS.transferMaintenance(sendMaintToBlock);
					if(!(brokenList.get(0) == null)){
						brokenList.remove(0);
					}
					trackClosed = false;
				}
			}
		});

	}

	public void setBlockWS(TrackModel dummyTrack, String line, String section, Integer bl){
		Block block = dummyTrack.getBlock(line, section, bl);
		this.savedBlock = block;
	}

	public Block getBlockWS(){
		return savedBlock;
	}

	public void updateTrackInfo(Block wsBlock){
		Block updatedBlock = currWorkingWS.getBlock(wsBlock);
		if(updatedBlock.getOccupied() == true){
			//System.out.println("occupied");
		}else{
			//System.out.println("NOT occupied");
		}
		// update station info
		if(updatedBlock.getStationName().equals("")){
			station_no.setSelected(true);
			station_yes.setSelected(false);
			return_stn_name.setText("N/A");
		}else if(!updatedBlock.getStationName().equals("")){
			station_no.setSelected(false);
			station_yes.setSelected(true);
			return_stn_name.setText(updatedBlock.getStationName());
		}
		//update speed limit
		String speedLimitStr = updatedBlock.getSpeedLimit().toString();
		Double speedLimitDouble = Double.parseDouble(speedLimitStr);
		speedLimitDouble = speedLimitDouble*(0.621371);
		return_speed_limit.setText(speedLimitDouble.toString());
		//update occupied
		if(updatedBlock.getOccupied() == true){
			occupancy_yes.setSelected(true);
			occupancy_no.setSelected(false);
		}else{
			occupancy_yes.setSelected(false);
			occupancy_no.setSelected(true);
		}
		//update crossings

		//update Switches
		if(updatedBlock.hasSwitch() == true){
			switch_yes.setSelected(true);
			switch_no.setSelected(false);
			String switchNumber = updatedBlock.getSwitchBlock();
			switch_number_label.setText(switchNumber);
			ArrayList<Block> switchBlockOptions = dummyTrack.viewLeafMap().get(switchNumber);
			Block switchOption1 = switchBlockOptions.get(0);
			Block switchOption2 = switchBlockOptions.get(1);
			switch_label_connect1.setText(switchOption1.getBlockSection());
			switch_label_connect2.setText(switchOption2.getBlockSection());
			Integer switchLeaf1 = switchOption1.blockNum();
			Integer switchLeaf2 = switchOption2.blockNum();
			//get switch status if switch exists on root block
			//System.out.println(updatedBlock.setSwitchState(-1).equals(null));
			Boolean switchStatusWS = updatedBlock.viewSwitchState();
			//report switch swtichStatus
			if(switchStatusWS == true){
				if(switchLeaf1<switchLeaf2){
					switch_label_connect1.setSelected(true);
					switch_label_connect2.setSelected(false);
				}else{
					switch_label_connect1.setSelected(false);
					switch_label_connect2.setSelected(true);
				}
			}else if(switchStatusWS == false){
				if(switchLeaf1<switchLeaf2){
					switch_label_connect1.setSelected(false);
					switch_label_connect2.setSelected(true);
				}else{
					switch_label_connect1.setSelected(true);
					switch_label_connect2.setSelected(false);
				}
			}

		}else{
			switch_yes.setSelected(false);
			switch_no.setSelected(true);
			switch_number_label.setText("N/A");
			switch_label_connect1.setText("N/A");
			switch_label_connect2.setText("N/A");
			switch_label_connect1.setSelected(false);
			switch_label_connect2.setSelected(false);
		}

	}


}
