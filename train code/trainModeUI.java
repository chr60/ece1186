package trainModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.SystemColor;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class trainModeUI {

	JFrame frmTrainModel;
	private JTextField txtFdsf;
	private JTextField txtM;
	private JTextField txtM_1;
	private JTextField txtTons;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtOpen;
	private JTextField txtClosed;
	private JTextField txtOn;
	private JTextField txtF;
	private JTextField txtF_1;
	private JTextField txtFailure;
	private JTextField txtOn_1;
	private JTextField txtN;
	private JTextField textField_3;
	private JTextField txtN_1;
	private JTextField textField_4;
	private JTextField txtMi;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private final ButtonGroup buttonGroup_4 = new ButtonGroup();
	private final ButtonGroup buttonGroup_5 = new ButtonGroup();
	private final ButtonGroup buttonGroup_6 = new ButtonGroup();
	private final ButtonGroup buttonGroup_7 = new ButtonGroup();
	JTextPane txtpnMph;
	JTextPane txtpnMi;
	static Train [] trainArray;
	static Train currTrain;
	int stop;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	public void setTrainArray(Train [] trains){
		trainArray = trains;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					trainModeUI window = new trainModeUI();
					window.frmTrainModel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public trainModeUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) { 
            if ("Windows".equals(info.getName()))
                {try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;} 
            }
		
		
		frmTrainModel = new JFrame();
		frmTrainModel.setTitle("Train Model");
		frmTrainModel.setBounds(100, 100, 736, 528);
		frmTrainModel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrainModel.getContentPane().setLayout(null);
		
		JLabel lblLeftDoor_1 = new JLabel("Left Door");
		lblLeftDoor_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLeftDoor_1.setBounds(488, 342, 71, 33);
		frmTrainModel.getContentPane().add(lblLeftDoor_1);
		
		JLabel lblTrainModel = new JLabel("Train Model");
		lblTrainModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainModel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTrainModel.setBackground(new Color(240, 240, 240));
		lblTrainModel.setBounds(275, 0, 162, 33);
		frmTrainModel.getContentPane().add(lblTrainModel);
		
		JLabel lblTrainData = new JLabel("Train Data");
		lblTrainData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTrainData.setBounds(52, 31, 99, 33);
		frmTrainModel.getContentPane().add(lblTrainData);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(10, 58, 162, 2);
		frmTrainModel.getContentPane().add(separator);
		
		txtFdsf = new JTextField();
		txtFdsf.setBackground(Color.WHITE);
		txtFdsf.setEditable(false);
		txtFdsf.setForeground(Color.BLACK);
		txtFdsf.setText("11.22 ft");
		txtFdsf.setBounds(116, 71, 86, 20);
		frmTrainModel.getContentPane().add(txtFdsf);
		txtFdsf.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(20, 77, 46, 14);
		frmTrainModel.getContentPane().add(lblHeight);
		
		JLabel lblLength = new JLabel("Length");
		lblLength.setBounds(20, 108, 46, 14);
		frmTrainModel.getContentPane().add(lblLength);
		
		txtM = new JTextField();
		txtM.setBackground(Color.WHITE);
		txtM.setEditable(false);
		txtM.setForeground(Color.BLACK);
		txtM.setText("105.64 ft");
		txtM.setColumns(10);
		txtM.setBounds(116, 102, 86, 20);
		frmTrainModel.getContentPane().add(txtM);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(20, 139, 46, 14);
		frmTrainModel.getContentPane().add(lblWidth);
		
		txtM_1 = new JTextField();
		txtM_1.setBackground(Color.WHITE);
		txtM_1.setEditable(false);
		txtM_1.setForeground(Color.BLACK);
		txtM_1.setText("8.69 ft");
		txtM_1.setColumns(10);
		txtM_1.setBounds(116, 133, 86, 20);
		frmTrainModel.getContentPane().add(txtM_1);
		
		JLabel lblMass = new JLabel("Mass");
		lblMass.setBounds(20, 167, 46, 14);
		frmTrainModel.getContentPane().add(lblMass);
		
		txtTons = new JTextField();
		txtTons.setBackground(Color.WHITE);
		txtTons.setEditable(false);
		txtTons.setForeground(Color.BLACK);
		txtTons.setText("100,000 lbs");
		txtTons.setColumns(10);
		txtTons.setBounds(116, 161, 86, 20);
		frmTrainModel.getContentPane().add(txtTons);
		
		JLabel lblNumberOfCrew = new JLabel("Crew Count");
		lblNumberOfCrew.setBounds(20, 195, 70, 14);
		frmTrainModel.getContentPane().add(lblNumberOfCrew);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setForeground(Color.BLACK);
		textField.setText("145");
		textField.setColumns(120);
		textField.setBounds(116, 217, 86, 20);
		frmTrainModel.getContentPane().add(textField);
		
		JLabel lblPassengerCount = new JLabel("Passenger Count");
		lblPassengerCount.setBounds(20, 220, 86, 14);
		frmTrainModel.getContentPane().add(lblPassengerCount);
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setForeground(Color.BLACK);
		textField_1.setText("2");
		textField_1.setColumns(10);
		textField_1.setBounds(116, 192, 86, 20);
		frmTrainModel.getContentPane().add(textField_1);
		
		JLabel lblCarCount = new JLabel("Number of Cars");
		lblCarCount.setBounds(20, 251, 86, 14);
		frmTrainModel.getContentPane().add(lblCarCount);
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setEditable(false);
		textField_2.setForeground(Color.BLACK);
		textField_2.setText("1");
		textField_2.setColumns(120);
		textField_2.setBounds(116, 248, 86, 20);
		frmTrainModel.getContentPane().add(textField_2);
		
		JLabel lblTrainStatuses = new JLabel("Train Statuses");
		lblTrainStatuses.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTrainStatuses.setBounds(550, 31, 99, 33);
		frmTrainModel.getContentPane().add(lblTrainStatuses);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(508, 58, 162, 2);
		frmTrainModel.getContentPane().add(separator_1);
		
		JLabel lblRightDoor = new JLabel("Right Door");
		lblRightDoor.setBounds(488, 77, 70, 14);
		frmTrainModel.getContentPane().add(lblRightDoor);
		
		txtOpen = new JTextField();
		txtOpen.setBackground(Color.WHITE);
		txtOpen.setEditable(false);
		txtOpen.setText("OPEN");
		txtOpen.setColumns(10);
		txtOpen.setBounds(584, 71, 86, 20);
		frmTrainModel.getContentPane().add(txtOpen);
		
		txtClosed = new JTextField();
		txtClosed.setBackground(Color.WHITE);
		txtClosed.setEditable(false);
		txtClosed.setText("CLOSED");
		txtClosed.setColumns(10);
		txtClosed.setBounds(584, 102, 86, 20);
		frmTrainModel.getContentPane().add(txtClosed);
		
		JLabel lblLeftDoor = new JLabel("Left Door");
		lblLeftDoor.setBounds(488, 108, 46, 14);
		frmTrainModel.getContentPane().add(lblLeftDoor);
		
		JLabel lblLights = new JLabel("Lights");
		lblLights.setBounds(488, 139, 46, 14);
		frmTrainModel.getContentPane().add(lblLights);
		
		txtOn = new JTextField();
		txtOn.setBackground(Color.WHITE);
		txtOn.setEditable(false);
		txtOn.setText("ON");
		txtOn.setColumns(10);
		txtOn.setBounds(584, 133, 86, 20);
		frmTrainModel.getContentPane().add(txtOn);
		
		txtF = new JTextField();
		txtF.setBackground(Color.WHITE);
		txtF.setEditable(false);
		txtF.setText("55 F");
		txtF.setColumns(10);
		txtF.setBounds(584, 161, 86, 20);
		frmTrainModel.getContentPane().add(txtF);
		
		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setBounds(488, 167, 70, 14);
		frmTrainModel.getContentPane().add(lblTemperature);
		
		JLabel lblThermostat = new JLabel("Thermostat");
		lblThermostat.setBounds(488, 195, 70, 14);
		frmTrainModel.getContentPane().add(lblThermostat);
		
		txtF_1 = new JTextField();
		txtF_1.setBackground(Color.WHITE);
		txtF_1.setEditable(false);
		txtF_1.setText("60 F");
		txtF_1.setColumns(10);
		txtF_1.setBounds(584, 192, 86, 20);
		frmTrainModel.getContentPane().add(txtF_1);
		
		txtFailure = new JTextField();
		txtFailure.setEditable(false);
		txtFailure.setBackground(Color.WHITE);
		txtFailure.setForeground(new Color(0, 0, 0));
		txtFailure.setText("OFF");
		txtFailure.setColumns(120);
		txtFailure.setBounds(584, 217, 86, 20);
		frmTrainModel.getContentPane().add(txtFailure);
		
		JLabel lblServiceBrake = new JLabel("Service Brake");
		lblServiceBrake.setBounds(488, 220, 86, 14);
		frmTrainModel.getContentPane().add(lblServiceBrake);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake");
		lblEmergencyBrake.setBounds(488, 251, 86, 14);
		frmTrainModel.getContentPane().add(lblEmergencyBrake);
		
		txtOn_1 = new JTextField();
		txtOn_1.setBackground(Color.WHITE);
		txtOn_1.setEditable(false);
		txtOn_1.setText("OFF");
		txtOn_1.setColumns(120);
		txtOn_1.setBounds(584, 248, 86, 20);
		frmTrainModel.getContentPane().add(txtOn_1);
		
		JLabel lblCurrentSpeed = new JLabel("Current Speed");
		lblCurrentSpeed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCurrentSpeed.setBounds(229, 44, 99, 20);
		frmTrainModel.getContentPane().add(lblCurrentSpeed);
		
		JLabel lblAuthority = new JLabel("Authority");
		lblAuthority.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthority.setBounds(360, 44, 99, 20);
		frmTrainModel.getContentPane().add(lblAuthority);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(10, 307, 687, 2);
		frmTrainModel.getContentPane().add(separator_2);
		
		JLabel lblMurphy = new JLabel("Murphy");
		lblMurphy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMurphy.setBackground(SystemColor.menu);
		lblMurphy.setBounds(11, 319, 162, 33);
		frmTrainModel.getContentPane().add(lblMurphy);
		
		JLabel lblEngineFailure = new JLabel("Engine Failure");
		lblEngineFailure.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEngineFailure.setBounds(16, 359, 99, 33);
		frmTrainModel.getContentPane().add(lblEngineFailure);
		
		JLabel lblSignalFailure = new JLabel("Signal Failure");
		lblSignalFailure.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSignalFailure.setBounds(16, 387, 99, 33);
		frmTrainModel.getContentPane().add(lblSignalFailure);
		
		JLabel lblBrakeFailure = new JLabel("Brake Failure");
		lblBrakeFailure.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBrakeFailure.setBounds(16, 419, 99, 33);
		frmTrainModel.getContentPane().add(lblBrakeFailure);
		
		JRadioButton rdbtnOn = new JRadioButton("ON");
		buttonGroup.add(rdbtnOn);
		rdbtnOn.setBounds(112, 369, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnOn);
		
		JRadioButton radioButton = new JRadioButton("ON");
		buttonGroup_1.add(radioButton);
		radioButton.setBounds(112, 397, 46, 23);
		frmTrainModel.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("ON");
		buttonGroup_2.add(radioButton_1);
		radioButton_1.setSelected(true);
		radioButton_1.setBounds(112, 429, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_1);
		
		JRadioButton rdbtnOff = new JRadioButton("OFF");
		buttonGroup.add(rdbtnOff);
		rdbtnOff.setSelected(true);
		rdbtnOff.setBounds(156, 369, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnOff);
		
		JRadioButton rdbtnOff_1 = new JRadioButton("OFF");
		buttonGroup_1.add(rdbtnOff_1);
		rdbtnOff_1.setSelected(true);
		rdbtnOff_1.setBounds(156, 397, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnOff_1);
		
		JRadioButton rdbtnOff_2 = new JRadioButton("OFF");
		buttonGroup_2.add(rdbtnOff_2);
		rdbtnOff_2.setBounds(156, 429, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnOff_2);
		
		JTextPane txtpnMessageBoard = new JTextPane();
		txtpnMessageBoard.setEditable(false);
		txtpnMessageBoard.setText("\tMessage Board\r\nPower Command of 5,000 Recieved.\r\nCurrent Speed 65 MPH\r\nSERVICE BRAKE FAILURE!\r\nCurrent Speed Dropping.");
		txtpnMessageBoard.setBounds(229, 186, 232, 85);
		frmTrainModel.getContentPane().add(txtpnMessageBoard);
		
		txtpnMph = new JTextPane();
		txtpnMph.setEditable(false);
		txtpnMph.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnMph.setText("                                                                                                  65 MPH");
		txtpnMph.setBounds(229, 71, 77, 67);
		frmTrainModel.getContentPane().add(txtpnMph);

		txtpnMi = new JTextPane();
		txtpnMi.setEditable(false);
		txtpnMi.setText("             3 mi");
		txtpnMi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtpnMi.setBounds(351, 71, 77, 67);
		frmTrainModel.getContentPane().add(txtpnMi);
		
		JLabel lblPowerCommand = new JLabel("Power Command");
		lblPowerCommand.setBounds(237, 161, 100, 14);
		frmTrainModel.getContentPane().add(lblPowerCommand);
		
		txtN = new JTextField();
		txtN.setBackground(Color.WHITE);
		txtN.setEditable(false);
		txtN.setText("5,000 W");
		txtN.setForeground(Color.BLACK);
		txtN.setColumns(10);
		txtN.setBounds(351, 154, 86, 20);
		frmTrainModel.getContentPane().add(txtN);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBackground(Color.BLACK);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(229, 307, 2, 156);
		frmTrainModel.getContentPane().add(separator_3);
		
		JLabel lblTestConsole = new JLabel("Test Console");
		lblTestConsole.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTestConsole.setBackground(SystemColor.menu);
		lblTestConsole.setBounds(237, 307, 162, 33);
		frmTrainModel.getContentPane().add(lblTestConsole);
		
		JLabel lblRightDoor_1 = new JLabel("Right Door");
		lblRightDoor_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRightDoor_1.setBounds(245, 361, 60, 33);
		frmTrainModel.getContentPane().add(lblRightDoor_1);
		
		JRadioButton rdbtnOpen = new JRadioButton("OPEN");
		buttonGroup_3.add(rdbtnOpen);
		rdbtnOpen.setSelected(true);
		rdbtnOpen.setBounds(328, 360, 71, 23);
		frmTrainModel.getContentPane().add(rdbtnOpen);
		
		JRadioButton radioButton_2 = new JRadioButton("OPEN");
		buttonGroup_7.add(radioButton_2);
		radioButton_2.setBounds(559, 347, 64, 23);
		frmTrainModel.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("CLOSED");
		buttonGroup_7.add(radioButton_3);
		radioButton_3.setSelected(true);
		radioButton_3.setBounds(625, 347, 73, 23);
		frmTrainModel.getContentPane().add(radioButton_3);
		
		JLabel lblLights_1 = new JLabel("Lights");
		lblLights_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLights_1.setBounds(245, 383, 61, 33);
		frmTrainModel.getContentPane().add(lblLights_1);
		
		JRadioButton radioButton_4 = new JRadioButton("ON");
		buttonGroup_4.add(radioButton_4);
		radioButton_4.setBounds(328, 388, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_4);
		
		JRadioButton radioButton_5 = new JRadioButton("OFF");
		buttonGroup_4.add(radioButton_5);
		radioButton_5.setSelected(true);
		radioButton_5.setBounds(377, 386, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_5);
		
		JLabel lblSetThermostat = new JLabel("Set Thermostat");
		lblSetThermostat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetThermostat.setBounds(490, 373, 86, 14);
		frmTrainModel.getContentPane().add(lblSetThermostat);
		
		textField_3 = new JTextField();
		textField_3.setText("60 F");
		textField_3.setColumns(10);
		textField_3.setBounds(615, 368, 70, 20);
		frmTrainModel.getContentPane().add(textField_3);
		
		JLabel lblServiceBrake_1 = new JLabel("Service Brake");
		lblServiceBrake_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblServiceBrake_1.setBounds(245, 405, 70, 33);
		frmTrainModel.getContentPane().add(lblServiceBrake_1);
		
		JRadioButton radioButton_6 = new JRadioButton("ON");
		buttonGroup_5.add(radioButton_6);
		radioButton_6.setBounds(328, 410, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_6);
		
		JRadioButton radioButton_7 = new JRadioButton("OFF");
		buttonGroup_5.add(radioButton_7);
		radioButton_7.setSelected(true);
		radioButton_7.setBounds(377, 410, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_7);
		
		JLabel lblEmergencyBrake_1 = new JLabel("Emergency Brake");
		lblEmergencyBrake_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmergencyBrake_1.setBounds(245, 430, 86, 33);
		frmTrainModel.getContentPane().add(lblEmergencyBrake_1);
		
		JRadioButton radioButton_8 = new JRadioButton("ON");
		buttonGroup_6.add(radioButton_8);
		radioButton_8.setSelected(true);
		radioButton_8.setBounds(328, 436, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_8);
		
		JRadioButton radioButton_9 = new JRadioButton("OFF");
		buttonGroup_6.add(radioButton_9);
		radioButton_9.setBounds(377, 436, 46, 23);
		frmTrainModel.getContentPane().add(radioButton_9);
		
		JLabel lblSetPowerCommand = new JLabel("Set Power Command");
		lblSetPowerCommand.setBounds(245, 342, 107, 14);
		frmTrainModel.getContentPane().add(lblSetPowerCommand);
		
		txtN_1 = new JTextField();
		txtN_1.setText("5000 ");
		txtN_1.setColumns(10);
		txtN_1.setBounds(350, 336, 71, 20);
		frmTrainModel.getContentPane().add(txtN_1);
		
		JLabel lblAddPassengers = new JLabel("Add/Remove Passengers");
		lblAddPassengers.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddPassengers.setBounds(490, 391, 127, 14);
		frmTrainModel.getContentPane().add(lblAddPassengers);
		
		textField_4 = new JTextField();
		textField_4.setText("120");
		textField_4.setColumns(10);
		textField_4.setBounds(615, 386, 70, 20);
		frmTrainModel.getContentPane().add(textField_4);
		
		JLabel lblSetAuthority = new JLabel("Set Authority");
		lblSetAuthority.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetAuthority.setBounds(488, 410, 86, 14);
		frmTrainModel.getContentPane().add(lblSetAuthority);
		
		txtMi = new JTextField();
		txtMi.setText("3 mi");
		txtMi.setColumns(10);
		txtMi.setBounds(615, 405, 70, 20);
		frmTrainModel.getContentPane().add(txtMi);
		
		
		
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Train", "123"}));
		comboBox.setBounds(10, 9, 118, 23);
		frmTrainModel.getContentPane().add(comboBox);
		
		JRadioButton rdbtnClosed = new JRadioButton("CLOSED");
		buttonGroup_3.add(rdbtnClosed);
		rdbtnClosed.setBounds(401, 359, 86, 23);
		frmTrainModel.getContentPane().add(rdbtnClosed);
		
		JButton btnApplyChanges = new JButton("Start Test\r\n");
		btnApplyChanges.setBounds(488, 435, 107, 23);
		frmTrainModel.getContentPane().add(btnApplyChanges);
		
		JLabel lblW = new JLabel("W");
		lblW.setForeground(new Color(0, 0, 0));
		lblW.setBounds(425, 338, 46, 14);
		frmTrainModel.getContentPane().add(lblW);
		
		JButton btnStopTest = new JButton("Stop Test\r\n");
		
		btnStopTest.setBounds(603, 435, 107, 23);
		frmTrainModel.getContentPane().add(btnStopTest);
		
		JMenuBar menuBar = new JMenuBar();
		frmTrainModel.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewTab = new JMenuItem("New Tab");
		mntmNewTab.setHorizontalAlignment(SwingConstants.TRAILING);
		mnFile.add(mntmNewTab);
		
		
		//action listeners for desired inputs
		//action listener for select train dropdown
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<String> combo = (JComboBox<String>) arg0.getSource();
		        String selectedTrain = (String) combo.getSelectedItem();
		 
		        if (selectedTrain.equals("Select Train")) {
		           //no train selected clear all values
		        																								//CREATE CLEAR CLASS LATER
		        } else if (selectedTrain.equals("123")) {
		            //if train 123 is selected update info for this train
		        	updateGUI(trainArray[0]);
		        }
			}

		});
		//action listener for test console
		btnApplyChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//action button to apply changes made to test console once pressed all pertinent values will be updated based on the component values
				//for prototype first thing to update is velocity based on power command
					String pwrCmdStr = txtN_1.getText();
					Double pwrCmd = Double.parseDouble(pwrCmdStr);
					new Launch().powerLoop(pwrCmd,currTrain);					
				
			}
		});
		
		btnStopTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop =1;
			}
		});
		

	}
	
	public void updateGUI(Train currT) {
		//method to update GUI based on selected train info
		currTrain = currT;
		txtpnMph.setText("\n   "+currTrain.getVelocity().intValue()+" MPH");
		txtN.setText(currTrain.getPower().intValue()+" W");
		txtTons.setText(currTrain.getMass().intValue()+" lbs");
	}
	
	
}
