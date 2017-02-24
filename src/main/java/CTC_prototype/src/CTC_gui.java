import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.*;
import java.awt.*;

public class CTC_gui {

	JFrame frame;
	private Border grayline;
	private JToggleButton flipped_yes;
	static ArrayList<train> dispatchTrainList = new ArrayList<train>();
	private int sizeArr = 0;
	static Integer dispatchCounter = 0;
	private trackDetails block1 = new trackDetails("Red", "A", 1, 24.8548, "", "6");
	private trackDetails block2 = new trackDetails("Red", "A", 2, 24.8548, "", "");
	static trackDetails block3 = new trackDetails("Red", "A", 3, 24.8548, "", "");
	static trackDetails block4 = new trackDetails("Red", "B", 4, 24.8548, "", "");
	static trackDetails block5 = new trackDetails("Red", "B", 5, 24.8548, "", "");
	static trackDetails block6 = new trackDetails("Red", "B", 6, 24.8548, "", "");
	static trackDetails block7 = new trackDetails("Red", "C", 7, 24.8548, "Shadyside", "");
	static trackDetails block8 = new trackDetails("Red", "C", 8, 24.8548, "", "");
	static trackDetails block9 = new trackDetails("Red", "C", 9, 24.8548, "Yard", "12");
	static ArrayList<trackDetails> blockList = new ArrayList<trackDetails>();
	

	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CTC_gui window = new CTC_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CTC_gui() {
	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) { 
            if ("Windows".equals(info.getName()))
                {try{javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;} 
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           break;}
		}
		
		//adding blocks to arraylist
		blockList.add(block1);
		blockList.add(block2);
		blockList.add(block3);
		blockList.add(block4);
		blockList.add(block5);
		blockList.add(block6);
		blockList.add(block7);
		blockList.add(block8);
		blockList.add(block9);
		
		
		grayline = BorderFactory.createLineBorder(Color.gray);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 785, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel blockPanel = new JPanel();
		blockPanel.setBounds(402, 0, 367, 229);
		blockPanel.setBorder(grayline);
		frame.getContentPane().add(blockPanel);
		blockPanel.setLayout(null);
		
		JButton btnCloseTrack = new JButton("Close Track");
		btnCloseTrack.setBounds(77, 195, 87, 23);
		blockPanel.add(btnCloseTrack);
		btnCloseTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JComboBox<String> dropdown_line = new JComboBox<>();
		dropdown_line.setModel(new DefaultComboBoxModel(new String[] {"Line"}));
		dropdown_line.setBounds(121, 6, 72, 20);
		blockPanel.add(dropdown_line);
		dropdown_line.addItem("Red");
		dropdown_line.addItem("Green");

		
		JComboBox<String> dropdown_segment = new JComboBox<String>();
		dropdown_segment.setModel(new DefaultComboBoxModel(new String[] {"Segment"}));
		dropdown_segment.setBounds(203, 6, 72, 20);
		blockPanel.add(dropdown_segment);
		dropdown_segment.setToolTipText("LINE");
		dropdown_segment.addItem("A");
		dropdown_segment.addItem("B");
		dropdown_segment.addItem("C");

		
		JButton btnPerformMaintenance = new JButton("Maintenance");
		btnPerformMaintenance.setBounds(194, 195, 93, 23);
		blockPanel.add(btnPerformMaintenance);
		
		JLabel station_label = new JLabel("Station?");
		station_label.setHorizontalAlignment(SwingConstants.RIGHT);
		station_label.setBounds(26, 37, 46, 14);
		blockPanel.add(station_label);
		
		JToggleButton station_no = new JToggleButton("N");
		station_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		station_no.setBounds(121, 35, 45, 16);
		blockPanel.add(station_no);
		
		JToggleButton station_yes = new JToggleButton("Y");
		station_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		station_yes.setBounds(78, 35, 45, 16);
		blockPanel.add(station_yes);
		
		JLabel name_station_label = new JLabel("Name");
		name_station_label.setHorizontalAlignment(SwingConstants.RIGHT);
		name_station_label.setBounds(170, 37, 46, 14);
		blockPanel.add(name_station_label);
		
		JLabel return_stn_name = new JLabel("N/A");
		return_stn_name.setHorizontalAlignment(SwingConstants.LEFT);
		return_stn_name.setBounds(226, 37, 134, 14);
		blockPanel.add(return_stn_name);
		
		JLabel speed_limit_label = new JLabel("Speed Limit");
		speed_limit_label.setHorizontalAlignment(SwingConstants.RIGHT);
		speed_limit_label.setBounds(0, 62, 72, 14);
		blockPanel.add(speed_limit_label);
		
		JLabel return_speed_limit = new JLabel("#");
		return_speed_limit.setHorizontalAlignment(SwingConstants.CENTER);
		return_speed_limit.setBounds(78, 62, 45, 14);
		blockPanel.add(return_speed_limit);
		
		JLabel speed_label = new JLabel("mi/hr");
		speed_label.setHorizontalAlignment(SwingConstants.LEFT);
		speed_label.setBounds(133, 62, 45, 14);
		blockPanel.add(speed_label);
		
		JLabel crossing_label = new JLabel("Crossing?");
		crossing_label.setHorizontalAlignment(SwingConstants.RIGHT);
		crossing_label.setBounds(24, 112, 46, 14);
		blockPanel.add(crossing_label);
		
		JToggleButton crossing_yes = new JToggleButton("Y");
		crossing_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		crossing_yes.setBounds(76, 110, 45, 16);
		blockPanel.add(crossing_yes);
		
		JToggleButton crossing_no = new JToggleButton("N");
		crossing_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		crossing_no.setBounds(119, 110, 45, 16);
		blockPanel.add(crossing_no);
		
		JLabel crossing_activated_label = new JLabel("Activated?");
		crossing_activated_label.setHorizontalAlignment(SwingConstants.RIGHT);
		crossing_activated_label.setBounds(189, 114, 72, 14);
		blockPanel.add(crossing_activated_label);
		
		JToggleButton activated_yes = new JToggleButton("Y");
		activated_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		activated_yes.setBounds(267, 112, 45, 16);
		blockPanel.add(activated_yes);
		
		JToggleButton activated_no = new JToggleButton("N");
		activated_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		activated_no.setBounds(310, 112, 45, 16);
		blockPanel.add(activated_no);
		
		JLabel occupancy_label = new JLabel("Occupied?");
		occupancy_label.setHorizontalAlignment(SwingConstants.RIGHT);
		occupancy_label.setBounds(-2, 85, 72, 14);
		blockPanel.add(occupancy_label);
		
		JToggleButton occupancy_yes = new JToggleButton("Y");
		occupancy_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		occupancy_yes.setBounds(76, 83, 45, 16);
		blockPanel.add(occupancy_yes);
		
		JToggleButton occupancy_no = new JToggleButton("N");
		occupancy_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		occupancy_no.setBounds(119, 83, 45, 16);
		blockPanel.add(occupancy_no);
		
		JLabel switch_label = new JLabel("Switch?");
		switch_label.setHorizontalAlignment(SwingConstants.RIGHT);
		switch_label.setBounds(24, 137, 46, 14);
		blockPanel.add(switch_label);
		
		JToggleButton switch_yes = new JToggleButton("Y");
		switch_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_yes.setBounds(76, 135, 45, 16);
		blockPanel.add(switch_yes);
		
		JToggleButton switch_no = new JToggleButton("N");
		switch_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		switch_no.setBounds(119, 135, 45, 16);
		blockPanel.add(switch_no);
		
		JLabel flipped_label = new JLabel("Flipped?");
		flipped_label.setHorizontalAlignment(SwingConstants.RIGHT);
		flipped_label.setBounds(209, 137, 52, 14);
		blockPanel.add(flipped_label);
		
		flipped_yes = new JToggleButton("Y");
		flipped_yes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flipped_yes.setBounds(267, 135, 45, 16);
		blockPanel.add(flipped_yes);
		
		JToggleButton flipped_no = new JToggleButton("N");
		flipped_no.setFont(new Font("Tahoma", Font.PLAIN, 11));
		flipped_no.setBounds(310, 135, 45, 16);
		blockPanel.add(flipped_no);
		
		JLabel switch_number_label = new JLabel("#");
		switch_number_label.setHorizontalAlignment(SwingConstants.CENTER);
		switch_number_label.setBounds(170, 137, 45, 14);
		blockPanel.add(switch_number_label);
		
		JLabel light_label = new JLabel("Lights?");
		light_label.setHorizontalAlignment(SwingConstants.RIGHT);
		light_label.setBounds(24, 164, 46, 14);
		blockPanel.add(light_label);
		
		JToggleButton lights_red = new JToggleButton("Red");
		lights_red.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lights_red.setBounds(76, 162, 67, 16);
		blockPanel.add(lights_red);
		
		JToggleButton lights_green = new JToggleButton("Green");
		lights_green.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lights_green.setBounds(149, 162, 67, 16);
		blockPanel.add(lights_green);
		
		JLabel invalidWarning = new JLabel("");
		invalidWarning.setHorizontalAlignment(SwingConstants.CENTER);
		invalidWarning.setFont(new Font("Tahoma", Font.BOLD, 16));
		invalidWarning.setBounds(194, 60, 143, 35);
		blockPanel.add(invalidWarning);
		
		JComboBox<Integer> dropdown_block = new JComboBox<>();
		dropdown_block.setModel(new DefaultComboBoxModel(new String[] {"Block"}));
		dropdown_block.setBounds(285, 6, 72, 20);
		blockPanel.add(dropdown_block);
		dropdown_block.addItem(1);
		dropdown_block.addItem(2);
		dropdown_block.addItem(3);
		dropdown_block.addItem(4);
		dropdown_block.addItem(5);
		dropdown_block.addItem(6);
		dropdown_block.addItem(7);
		dropdown_block.addItem(8);
		dropdown_block.addItem(9);
		dropdown_block.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String line = dropdown_line.getSelectedItem().toString();
				String segment = dropdown_segment.getSelectedItem().toString();
				String block = dropdown_block.getSelectedItem().toString();
				int b = Integer.parseInt(block);
				
				for(int i=0; i<9; i++){
					if(line.equals(blockList.get(i).getLine()) && segment.equals(blockList.get(i).getSection()) && b==blockList.get(i).getBlock()){
						//speed limit
						return_speed_limit.setText(blockList.get(i).getSpeedLimitS(blockList.get(i).getSpeedLimit()));
						//station buttons and text
						if(blockList.get(i).getStation().equals("")){
							station_no.setSelected(true);
							station_yes.setSelected(false);
							return_stn_name.setText("N/A");
						}else{
							station_no.setSelected(false);
							station_yes.setSelected(true);
							return_stn_name.setText(blockList.get(i).getStation());
						}
						//switch buttons and text
						if(blockList.get(i).getSwitchNum().equals("")){
							switch_no.setSelected(true);
							switch_yes.setSelected(false);
							switch_number_label.setText("N/A");
						}else{
							switch_no.setSelected(false);
							switch_yes.setSelected(true);
							switch_number_label.setText(blockList.get(i).getSwitchNum());
						}
						

					}
					/*
					else{
						invalidWarning.setEnabled(true);
						invalidWarning.setText("Invalid Selection");
					}
					*/
				}
				
			}
		});
		
		JLabel trackWindowLabel = new JLabel("TRACK");
		trackWindowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trackWindowLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		trackWindowLabel.setBounds(10, 6, 78, 23);
		blockPanel.add(trackWindowLabel);
		
		
		JPanel trainPanel = new JPanel();
		trainPanel.setBounds(402, 229, 367, 98);
		trainPanel.setBorder(grayline);
		frame.getContentPane().add(trainPanel);
		trainPanel.setLayout(null);
		
		JLabel set_speed_label = new JLabel("Speed");
		set_speed_label.setHorizontalAlignment(SwingConstants.RIGHT);
		set_speed_label.setBounds(121, 29, 72, 14);
		trainPanel.add(set_speed_label);
		
		JLabel authority_label = new JLabel("Authority");
		authority_label.setHorizontalAlignment(SwingConstants.RIGHT);
		authority_label.setBounds(121, 46, 72, 14);
		trainPanel.add(authority_label);
		
		JLabel speedNUM = new JLabel("#");
		speedNUM.setHorizontalAlignment(SwingConstants.CENTER);
		speedNUM.setBounds(199, 29, 45, 14);
		trainPanel.add(speedNUM);
		
		JLabel mileshourLabel = new JLabel("mi/hr");
		mileshourLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mileshourLabel.setBounds(254, 29, 45, 14);
		trainPanel.add(mileshourLabel);
		
		JLabel authorityNUM = new JLabel("#");
		authorityNUM.setHorizontalAlignment(SwingConstants.CENTER);
		authorityNUM.setBounds(199, 46, 45, 14);
		trainPanel.add(authorityNUM);
		
		JLabel authMilesLabel = new JLabel("mi");
		authMilesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		authMilesLabel.setBounds(254, 46, 45, 14);
		trainPanel.add(authMilesLabel);
		
		JLabel lineLabel = new JLabel("Line");
		lineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lineLabel.setBounds(121, 11, 72, 14);
		trainPanel.add(lineLabel);
		
		JLabel lineReturnLabel = new JLabel("-----");
		lineReturnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lineReturnLabel.setBounds(199, 11, 45, 14);
		trainPanel.add(lineReturnLabel);
		
		JComboBox<String> dropdownTrains = new JComboBox<String>();
		dropdownTrains.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String s = dropdownTrains.getSelectedItem().toString();
					int selection = Integer.parseInt(s);
					lineReturnLabel.setText(dispatchTrainList.get(selection-1).getLine());
					authorityNUM.setText(dispatchTrainList.get(selection-1).getAuthorityS());
					speedNUM.setText(dispatchTrainList.get(selection-1).getSpeedS());
					//System.out.println(selection);
				}catch(NullPointerException r){
				}
			}
		});
		dropdownTrains.setBounds(25, 40, 87, 23);
		trainPanel.add(dropdownTrains);

		JButton updateBtn = new JButton("Update List");
		updateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dropdownTrains.removeAllItems();
				sizeArr = sizeArrList(dispatchTrainList);
				for(int i=0; i<sizeArr; i++){
					dropdownTrains.addItem(dispatchTrainList.get(i).getTrainID());
				}
				
			}
		});
		updateBtn.setBounds(25, 67, 87, 23);
		trainPanel.add(updateBtn);
		
		JButton buttonDispatchTrain = new JButton("Dispatch Train");
		buttonDispatchTrain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new dispatchTrainPopup(dispatchTrainList);
			}
		});
		buttonDispatchTrain.setBounds(169, 67, 101, 23);
		trainPanel.add(buttonDispatchTrain);
		
		JLabel trainWindowLabel = new JLabel("TRAINS");
		trainWindowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trainWindowLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		trainWindowLabel.setBounds(10, 6, 78, 23);
		trainPanel.add(trainWindowLabel);
				
		JPanel miscPanel = new JPanel();
		miscPanel.setLayout(null);
		miscPanel.setBorder(grayline);
		miscPanel.setBounds(402, 326, 367, 85);
		frame.getContentPane().add(miscPanel);
		
		JLabel lblPassengersmin = new JLabel("passengers/hr");
		lblPassengersmin.setBounds(65, 11, 84, 14);
		miscPanel.add(lblPassengersmin);
		
		JLabel return_throughput = new JLabel("#");
		return_throughput.setHorizontalAlignment(SwingConstants.CENTER);
		return_throughput.setBounds(10, 11, 52, 14);
		miscPanel.add(return_throughput);
		
		JButton buttonShowSchedule = new JButton("Show Schedule");
		buttonShowSchedule.setBounds(20, 36, 118, 23);
		miscPanel.add(buttonShowSchedule);
		
		JButton buttonAllTrains = new JButton("Show All Trains");
		buttonAllTrains.setBounds(20, 58, 118, 23);
		miscPanel.add(buttonAllTrains);
		
		JLabel lblModes = new JLabel("MODES");
		lblModes.setBounds(176, 11, 46, 14);
		miscPanel.add(lblModes);
		lblModes.setHorizontalAlignment(SwingConstants.LEFT);
		
		JRadioButton radioAuto = new JRadioButton("Automatic");
		radioAuto.setBounds(176, 29, 78, 23);
		miscPanel.add(radioAuto);
		
		JRadioButton radioManual = new JRadioButton("Manual");
		radioManual.setSelected(true);
		radioManual.setBounds(255, 29, 73, 23);
		miscPanel.add(radioManual);
		
		JRadioButton radioFixed = new JRadioButton("Fixed Block");
		radioFixed.setBounds(255, 61, 84, 23);
		miscPanel.add(radioFixed);
		
		JRadioButton radioMBO = new JRadioButton("MBO");
		radioMBO.setBounds(176, 61, 65, 23);
		miscPanel.add(radioMBO);
		
		JPanel testPanel = new JPanel();
		testPanel.setBounds(287, 0, 115, 229);
		testPanel.setBorder(grayline);
		frame.getContentPane().add(testPanel);
		testPanel.setLayout(null);
		
		JLabel murphyLabel = new JLabel("MURPHY");
		murphyLabel.setBounds(21, 0, 78, 23);
		testPanel.add(murphyLabel);
		murphyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		murphyLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblBreak = new JLabel("Break");
		lblBreak.setBounds(38, 20, 46, 14);
		testPanel.add(lblBreak);
		lblBreak.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMbo = new JLabel("with MBO");
		lblMbo.setBounds(27, 42, 65, 23);
		testPanel.add(lblMbo);
		lblMbo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMbo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblBreakCommunications = new JLabel("Communications");
		lblBreakCommunications.setBounds(0, 31, 118, 14);
		testPanel.add(lblBreakCommunications);
		lblBreakCommunications.setHorizontalAlignment(SwingConstants.CENTER);
		
		JToggleButton toggleButton_1 = new JToggleButton("N");
		toggleButton_1.setBounds(59, 62, 45, 16);
		testPanel.add(toggleButton_1);
		toggleButton_1.setSelected(true);
		toggleButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JToggleButton toggleButton = new JToggleButton("Y");
		toggleButton.setBounds(16, 62, 45, 16);
		testPanel.add(toggleButton);
		toggleButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblWayside = new JLabel("with Wayside");
		lblWayside.setBounds(27, 77, 69, 23);
		testPanel.add(lblWayside);
		lblWayside.setHorizontalAlignment(SwingConstants.CENTER);
		lblWayside.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JToggleButton toggleButton_3 = new JToggleButton("Y");
		toggleButton_3.setBounds(16, 92, 45, 15);
		testPanel.add(toggleButton_3);
		toggleButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JToggleButton toggleButton_2 = new JToggleButton("N");
		toggleButton_2.setBounds(59, 92, 45, 15);
		testPanel.add(toggleButton_2);
		toggleButton_2.setSelected(true);
		toggleButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel testLabel = new JLabel("TESTING");
		testLabel.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		testLabel.setBounds(21, 118, 78, 23);
		testPanel.add(testLabel);
		
		JLabel testLabel2 = new JLabel("Turning on this");
		testLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel2.setBounds(0, 137, 118, 14);
		testPanel.add(testLabel2);
		
		JLabel testLabel3 = new JLabel("mode will cut off ");
		testLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel3.setBounds(0, 152, 118, 14);
		testPanel.add(testLabel3);
		
		JLabel testLabel4 = new JLabel("the CTC from all other");
		testLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel4.setBounds(0, 166, 118, 14);
		testPanel.add(testLabel4);
		
		JLabel testLabel5 = new JLabel("modules");
		testLabel5.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel5.setBounds(0, 180, 118, 14);
		testPanel.add(testLabel5);
		
		JRadioButton testBtnOn = new JRadioButton("ON");
		testBtnOn.setSelected(true);
		testBtnOn.setBounds(59, 197, 46, 23);
		testPanel.add(testBtnOn);
		
		JRadioButton testBtnOff = new JRadioButton("OFF");
		testBtnOff.setBounds(15, 197, 46, 23);
		testPanel.add(testBtnOff);
		
		JPanel failurePanel = new JPanel();
		failurePanel.setBackground(new Color(34, 139, 34));
		failurePanel.setBounds(287, 229, 115, 182);
		frame.getContentPane().add(failurePanel);
		failurePanel.setLayout(null);
		
		JButton btnNoFailure = new JButton("No Failure");
		btnNoFailure.setBounds(10, 80, 89, 23);
		failurePanel.add(btnNoFailure);
		
		ImageIcon image = new ImageIcon(getClass().getResource("trackLayout2.jpg"));
		JPanel panel = new JPanel();
		panel.setBounds(0, 21, 286, 368);
		frame.getContentPane().add(panel);
		panel.add(new JLabel(image));
	}
	
	public int sizeArrList(ArrayList<train> trainList){
		
		return trainList.size();
	}
	
}
