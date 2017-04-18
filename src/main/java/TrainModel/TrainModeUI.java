package TrainModel;

import java.awt.EventQueue;
import TrackModel.*;

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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TrainModeUI {

	public JFrame frmTrainModel;
	private JTextField txtHeight;
	private JTextField txtLength;
	private JTextField txtWidth;
	private JTextField txtMass;
	private JTextField txtPass;
	private JTextField txtCrew;
	private JTextField txtCar;
	private JTextField txtRightDoor;
	private JTextField txtLeftDoor;
	private JTextField txtLights;
	private JTextField txtTemperature;
	private JTextField txtThermostat;
	private JTextField txtServiceBrake;
	private JTextField txtEmergencyBrake;
	private JTextField txtPower;
	private JTextField txtTestThermostat;
	private JTextField txtTestPower;
	private JTextField txtTestAddPassengers;
	private JTextField txtTestAuthority;
	private JTextPane txtCurrBlock;
	private JTextPane txtDistIntoBlock;
	private final ButtonGroup engineFailureBG = new ButtonGroup();
	private final ButtonGroup signalFailureBG = new ButtonGroup();
	private final ButtonGroup brakeFailureBG = new ButtonGroup();
	private final ButtonGroup rightDoorBG = new ButtonGroup();
	private final ButtonGroup lightsBG = new ButtonGroup();
	private final ButtonGroup serviceBrakeBG = new ButtonGroup();
	private final ButtonGroup emergencyBrakeBG = new ButtonGroup();
	private final ButtonGroup leftDoorBG = new ButtonGroup();
	private	JRadioButton rdbtnEngineOn;
	private JRadioButton rdbtnSignalOn;
	private JRadioButton rdbtnBrakeOn;
	private	JRadioButton rdbtnEngineOff;
	private	JRadioButton rdbtnSignalOff;
	private	JRadioButton rdbtnBrakeOff;
	private	JRadioButton rdbtnRightDoorOpen;
	private	JRadioButton rdbtnLeftDoorOpen;
	private	JRadioButton rdbtnLeftDoorClosed;
	private	JRadioButton rdbtnLightsOn;
	private	JRadioButton rdbtnLightsOff;
	private	JRadioButton rdbtnServiceBrakeOn;
	private	JRadioButton rdbtnServiceBrakeOff;
	private	JRadioButton rdbtnEmergencyBrakeOn;
	private	JRadioButton rdbtnEmergencyBrakeOff;
	private	JRadioButton rdbtnRightDoorClosed;
	
        JComboBox<String> comboBox = new JComboBox<String>();
	JTextPane txtSpeed;
	JTextPane txtAuthority;
	ArrayList<Train> trainArray;
	Train currTrain;
	int stop;
	private JTextField txtTestGrade;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	public void setTrainArray(ArrayList<Train> trains){
		trainArray = trains;
		this.comboBox.addItem("Select a Train:");
        if (this.comboBox.getItemCount() != 0){
            this.comboBox.removeAllItems();
        }
        this.comboBox.addItem("Select a Train:");
        for (int i = 0; i < trains.size(); i++){
            this.comboBox.addItem(Integer.toString( trains.get(i).getID()) );
        }
                
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModeUI window = new TrainModeUI();
					window.frmTrainModel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Train getCurrT ()
	{
		return currTrain;
	}

	/**
	 * Create the application.
	 */
	public TrainModeUI() {
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
		lblLeftDoor_1.setBounds(488, 320, 71, 33);
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
		
		txtHeight = new JTextField();
		txtHeight.setBackground(Color.WHITE);
		txtHeight.setEditable(false);
		txtHeight.setForeground(Color.BLACK);
		txtHeight.setText("11.22 ft");
		txtHeight.setBounds(116, 71, 86, 20);
		frmTrainModel.getContentPane().add(txtHeight);
		txtHeight.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(20, 77, 46, 14);
		frmTrainModel.getContentPane().add(lblHeight);
		
		JLabel lblLength = new JLabel("Length");
		lblLength.setBounds(20, 108, 46, 14);
		frmTrainModel.getContentPane().add(lblLength);
		
		txtLength = new JTextField();
		txtLength.setBackground(Color.WHITE);
		txtLength.setEditable(false);
		txtLength.setForeground(Color.BLACK);
		txtLength.setText("105.64 ft");
		txtLength.setColumns(10);
		txtLength.setBounds(116, 102, 86, 20);
		frmTrainModel.getContentPane().add(txtLength);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(20, 139, 46, 14);
		frmTrainModel.getContentPane().add(lblWidth);
		
		txtWidth = new JTextField();
		txtWidth.setBackground(Color.WHITE);
		txtWidth.setEditable(false);
		txtWidth.setForeground(Color.BLACK);
		txtWidth.setText("8.69 ft");
		txtWidth.setColumns(10);
		txtWidth.setBounds(116, 133, 86, 20);
		frmTrainModel.getContentPane().add(txtWidth);
		
		JLabel lblMass = new JLabel("Mass");
		lblMass.setBounds(20, 167, 46, 14);
		frmTrainModel.getContentPane().add(lblMass);
		
		txtMass = new JTextField();
		txtMass.setEditable(false);
		txtMass.setBackground(Color.WHITE);
		txtMass.setForeground(Color.BLACK);
		txtMass.setText("100000 ");
		txtMass.setColumns(10);
		txtMass.setBounds(116, 161, 86, 20);
		frmTrainModel.getContentPane().add(txtMass);
		
		JLabel lblNumberOfCrew = new JLabel("Crew Count");
		lblNumberOfCrew.setBounds(20, 195, 70, 14);
		frmTrainModel.getContentPane().add(lblNumberOfCrew);
		
		txtPass = new JTextField();
		txtPass.setBackground(Color.WHITE);
		txtPass.setEditable(false);
		txtPass.setForeground(Color.BLACK);
		txtPass.setText("145");
		txtPass.setColumns(120);
		txtPass.setBounds(116, 217, 86, 20);
		frmTrainModel.getContentPane().add(txtPass);
		
		JLabel lblPassengerCount = new JLabel("Passenger Count");
		lblPassengerCount.setBounds(20, 220, 86, 14);
		frmTrainModel.getContentPane().add(lblPassengerCount);
		
		txtCrew = new JTextField();
		txtCrew.setEditable(false);
		txtCrew.setBackground(Color.WHITE);
		txtCrew.setForeground(Color.BLACK);
		txtCrew.setText("2");
		txtCrew.setColumns(10);
		txtCrew.setBounds(116, 192, 86, 20);
		frmTrainModel.getContentPane().add(txtCrew);
		
		JLabel lblCarCount = new JLabel("Number of Cars");
		lblCarCount.setBounds(20, 251, 86, 14);
		frmTrainModel.getContentPane().add(lblCarCount);
		
		txtCar = new JTextField();
		txtCar.setBackground(Color.WHITE);
		txtCar.setEditable(false);
		txtCar.setForeground(Color.BLACK);
		txtCar.setText("1");
		txtCar.setColumns(120);
		txtCar.setBounds(116, 248, 86, 20);
		frmTrainModel.getContentPane().add(txtCar);
		
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
		
		txtRightDoor = new JTextField();
		txtRightDoor.setBackground(Color.WHITE);
		txtRightDoor.setEditable(false);
		txtRightDoor.setText("CLOSED");
		txtRightDoor.setColumns(10);
		txtRightDoor.setBounds(584, 71, 86, 20);
		frmTrainModel.getContentPane().add(txtRightDoor);
		
		txtLeftDoor = new JTextField();
		txtLeftDoor.setBackground(Color.WHITE);
		txtLeftDoor.setEditable(false);
		txtLeftDoor.setText("CLOSED");
		txtLeftDoor.setColumns(10);
		txtLeftDoor.setBounds(584, 102, 86, 20);
		frmTrainModel.getContentPane().add(txtLeftDoor);
		
		JLabel lblLeftDoor = new JLabel("Left Door");
		lblLeftDoor.setBounds(488, 108, 46, 14);
		frmTrainModel.getContentPane().add(lblLeftDoor);
		
		JLabel lblLights = new JLabel("Lights");
		lblLights.setBounds(488, 139, 46, 14);
		frmTrainModel.getContentPane().add(lblLights);
		
		txtLights = new JTextField();
		txtLights.setBackground(Color.WHITE);
		txtLights.setEditable(false);
		txtLights.setText("ON");
		txtLights.setColumns(10);
		txtLights.setBounds(584, 133, 86, 20);
		frmTrainModel.getContentPane().add(txtLights);
		
		txtTemperature = new JTextField();
		txtTemperature.setBackground(Color.WHITE);
		txtTemperature.setEditable(false);
		txtTemperature.setText("55 F");
		txtTemperature.setColumns(10);
		txtTemperature.setBounds(584, 161, 86, 20);
		frmTrainModel.getContentPane().add(txtTemperature);
		
		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setBounds(488, 167, 70, 14);
		frmTrainModel.getContentPane().add(lblTemperature);
		
		JLabel lblThermostat = new JLabel("Thermostat");
		lblThermostat.setBounds(488, 195, 70, 14);
		frmTrainModel.getContentPane().add(lblThermostat);
		
		txtThermostat = new JTextField();
		txtThermostat.setBackground(Color.WHITE);
		txtThermostat.setEditable(false);
		txtThermostat.setText("60 F");
		txtThermostat.setColumns(10);
		txtThermostat.setBounds(584, 192, 86, 20);
		frmTrainModel.getContentPane().add(txtThermostat);
		
		txtServiceBrake = new JTextField();
		txtServiceBrake.setEditable(false);
		txtServiceBrake.setBackground(Color.WHITE);
		txtServiceBrake.setForeground(new Color(0, 0, 0));
		txtServiceBrake.setText("OFF");
		txtServiceBrake.setColumns(120);
		txtServiceBrake.setBounds(584, 217, 86, 20);
		frmTrainModel.getContentPane().add(txtServiceBrake);
		
		JLabel lblServiceBrake = new JLabel("Service Brake");
		lblServiceBrake.setBounds(488, 220, 86, 14);
		frmTrainModel.getContentPane().add(lblServiceBrake);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake");
		lblEmergencyBrake.setBounds(488, 251, 86, 14);
		frmTrainModel.getContentPane().add(lblEmergencyBrake);
		
		txtEmergencyBrake = new JTextField();
		txtEmergencyBrake.setBackground(Color.WHITE);
		txtEmergencyBrake.setEditable(false);
		txtEmergencyBrake.setText("OFF");
		txtEmergencyBrake.setColumns(120);
		txtEmergencyBrake.setBounds(584, 248, 86, 20);
		frmTrainModel.getContentPane().add(txtEmergencyBrake);
		
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
		
		rdbtnEngineOn = new JRadioButton("ON");
		engineFailureBG.add(rdbtnEngineOn);
		rdbtnEngineOn.setBounds(112, 369, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnEngineOn);
		
		rdbtnSignalOn	= new JRadioButton("ON");
		signalFailureBG.add(rdbtnSignalOn);
		rdbtnSignalOn.setBounds(112, 397, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnSignalOn);
		
		rdbtnBrakeOn = new JRadioButton("ON");
		brakeFailureBG.add(rdbtnBrakeOn);
		rdbtnBrakeOn.setSelected(false);
		rdbtnBrakeOn.setBounds(112, 429, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnBrakeOn);
		
		rdbtnEngineOff = new JRadioButton("OFF");
		engineFailureBG.add(rdbtnEngineOff);
		rdbtnEngineOff.setSelected(true);
		rdbtnEngineOff.setBounds(156, 369, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnEngineOff);
		
		rdbtnSignalOff = new JRadioButton("OFF");
		signalFailureBG.add(rdbtnSignalOff);
		rdbtnSignalOff.setSelected(true);
		rdbtnSignalOff.setBounds(156, 397, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnSignalOff);
		
		
		rdbtnBrakeOff = new JRadioButton("OFF");
		brakeFailureBG.add(rdbtnBrakeOff);
		rdbtnBrakeOff.setSelected(true);
		rdbtnBrakeOff.setBounds(156, 429, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnBrakeOff);
		
		JLabel lblGpsLocation = new JLabel("GPS Location");
		lblGpsLocation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGpsLocation.setBounds(285, 139, 99, 20);
		frmTrainModel.getContentPane().add(lblGpsLocation);
		
		txtCurrBlock = new JTextPane();
		txtCurrBlock.setText("\r\nBlock 77\r\n");
		txtCurrBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCurrBlock.setBounds(229, 188, 77, 67);
		frmTrainModel.getContentPane().add(txtCurrBlock);
		
		JLabel lblCurrentBlock = new JLabel("Current Block");
		lblCurrentBlock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCurrentBlock.setBounds(212, 162, 99, 20);
		frmTrainModel.getContentPane().add(lblCurrentBlock);
		
		JLabel lblDistanceIntoBlock = new JLabel("Distance Into Block");
		lblDistanceIntoBlock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDistanceIntoBlock.setBounds(328, 162, 150, 20);
		frmTrainModel.getContentPane().add(lblDistanceIntoBlock);
		
		txtDistIntoBlock = new JTextPane();
		txtDistIntoBlock.setText("\r\n   32 m");
		txtDistIntoBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDistIntoBlock.setBounds(351, 188, 77, 67);
		frmTrainModel.getContentPane().add(txtDistIntoBlock);
		
		txtSpeed = new JTextPane();
		txtSpeed.setEditable(false);
		txtSpeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSpeed.setText("                                                                                                  65 MPH");
		txtSpeed.setBounds(229, 71, 77, 67);
		frmTrainModel.getContentPane().add(txtSpeed);

		txtAuthority = new JTextPane();
		txtAuthority.setEditable(false);
		txtAuthority.setText("Block 77");
		txtAuthority.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtAuthority.setBounds(351, 71, 77, 67);
		frmTrainModel.getContentPane().add(txtAuthority);
		
		JLabel lblPowerCommand = new JLabel("Power Command");
		lblPowerCommand.setBounds(237, 271, 100, 14);
		frmTrainModel.getContentPane().add(lblPowerCommand);
		
		txtPower = new JTextField();
		txtPower.setBackground(Color.WHITE);
		txtPower.setEditable(false);
		txtPower.setText("5,000 W");
		txtPower.setForeground(Color.BLACK);
		txtPower.setColumns(10);
		txtPower.setBounds(351, 264, 86, 20);
		frmTrainModel.getContentPane().add(txtPower);
		
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
		
		rdbtnRightDoorOpen = new JRadioButton("OPEN");
		rightDoorBG.add(rdbtnRightDoorOpen);
		rdbtnRightDoorOpen.setSelected(false);
		rdbtnRightDoorOpen.setBounds(328, 360, 71, 23);
		frmTrainModel.getContentPane().add(rdbtnRightDoorOpen);
		
		rdbtnLeftDoorOpen = new JRadioButton("OPEN");
		leftDoorBG.add(rdbtnLeftDoorOpen);
		rdbtnLeftDoorOpen.setBounds(559, 325, 64, 23);
		frmTrainModel.getContentPane().add(rdbtnLeftDoorOpen);

		rdbtnLeftDoorClosed = new JRadioButton("CLOSED");
		leftDoorBG.add(rdbtnLeftDoorClosed);
		rdbtnLeftDoorClosed.setSelected(true);
		rdbtnLeftDoorClosed.setBounds(625, 325, 73, 23);
		frmTrainModel.getContentPane().add(rdbtnLeftDoorClosed);
		
		JLabel lblLights_1 = new JLabel("Lights");
		lblLights_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLights_1.setBounds(245, 383, 61, 33);
		frmTrainModel.getContentPane().add(lblLights_1);

		rdbtnLightsOn = new JRadioButton("ON");
		lightsBG.add(rdbtnLightsOn);
		rdbtnLightsOn.setBounds(328, 388, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnLightsOn);


		rdbtnLightsOff = new JRadioButton("OFF");
		lightsBG.add(rdbtnLightsOff);
		rdbtnLightsOff.setSelected(true);
		rdbtnLightsOff.setBounds(377, 386, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnLightsOff);
		
		JLabel lblSetThermostat = new JLabel("Set Thermostat");
		lblSetThermostat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetThermostat.setBounds(490, 373, 86, 14);
		frmTrainModel.getContentPane().add(lblSetThermostat);
		
		txtTestThermostat = new JTextField();
		txtTestThermostat.setText("60 F");
		txtTestThermostat.setColumns(10);
		txtTestThermostat.setBounds(615, 368, 70, 20);
		frmTrainModel.getContentPane().add(txtTestThermostat);
		
		JLabel lblServiceBrake_1 = new JLabel("Service Brake");
		lblServiceBrake_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblServiceBrake_1.setBounds(245, 405, 70, 33);
		frmTrainModel.getContentPane().add(lblServiceBrake_1);
		
		
		rdbtnServiceBrakeOn = new JRadioButton("ON");
		serviceBrakeBG.add(rdbtnServiceBrakeOn);
		rdbtnServiceBrakeOn.setBounds(328, 410, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnServiceBrakeOn);
		
		rdbtnServiceBrakeOff = new JRadioButton("OFF");
		serviceBrakeBG.add(rdbtnServiceBrakeOff);
		rdbtnServiceBrakeOff.setSelected(true);
		rdbtnServiceBrakeOff.setBounds(377, 410, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnServiceBrakeOff);
		
		JLabel lblEmergencyBrake_1 = new JLabel("Emergency Brake");
		lblEmergencyBrake_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmergencyBrake_1.setBounds(245, 430, 86, 33);
		frmTrainModel.getContentPane().add(lblEmergencyBrake_1);
		

		rdbtnEmergencyBrakeOn = new JRadioButton("ON");
		emergencyBrakeBG.add(rdbtnEmergencyBrakeOn);
		rdbtnEmergencyBrakeOn.setSelected(false);
		rdbtnEmergencyBrakeOn.setBounds(328, 436, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnEmergencyBrakeOn);
		
		rdbtnEmergencyBrakeOff = new JRadioButton("OFF");
		emergencyBrakeBG.add(rdbtnEmergencyBrakeOff);
		rdbtnEmergencyBrakeOff.setSelected(true);
		rdbtnEmergencyBrakeOff.setBounds(377, 436, 46, 23);
		frmTrainModel.getContentPane().add(rdbtnEmergencyBrakeOff);
		
		JLabel lblSetPowerCommand = new JLabel("Set Power Command");
		lblSetPowerCommand.setBounds(245, 342, 107, 14);
		frmTrainModel.getContentPane().add(lblSetPowerCommand);
		
		txtTestPower = new JTextField();
		txtTestPower.setText("5000 ");
		txtTestPower.setColumns(10);
		txtTestPower.setBounds(350, 336, 71, 20);
		frmTrainModel.getContentPane().add(txtTestPower);
		
		JLabel lblAddPassengers = new JLabel("Add/Remove Passengers");
		lblAddPassengers.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAddPassengers.setBounds(490, 391, 127, 14);
		frmTrainModel.getContentPane().add(lblAddPassengers);
		
		txtTestAddPassengers = new JTextField();
		txtTestAddPassengers.setText("120");
		txtTestAddPassengers.setColumns(10);
		txtTestAddPassengers.setBounds(615, 386, 70, 20);
		frmTrainModel.getContentPane().add(txtTestAddPassengers);
		
		JLabel lblSetAuthority = new JLabel("Set Authority");
		lblSetAuthority.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetAuthority.setBounds(488, 410, 86, 14);
		frmTrainModel.getContentPane().add(lblSetAuthority);
		
		txtTestAuthority = new JTextField();
		txtTestAuthority.setText("3 mi");
		txtTestAuthority.setColumns(10);
		txtTestAuthority.setBounds(615, 405, 70, 20);
		frmTrainModel.getContentPane().add(txtTestAuthority);
		
		
		
		
		//JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Train", "123"}));
		comboBox.setBounds(10, 9, 118, 23);
		frmTrainModel.getContentPane().add(comboBox);
		
		rdbtnRightDoorClosed = new JRadioButton("CLOSED");
		rightDoorBG.add(rdbtnRightDoorClosed);
		rdbtnRightDoorClosed.setSelected(true);
		rdbtnRightDoorClosed.setBounds(401, 359, 86, 23);
		frmTrainModel.getContentPane().add(rdbtnRightDoorClosed);
		
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
		
		JLabel lblGrade = new JLabel("Grade\r\n");
		lblGrade.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGrade.setBounds(490, 350, 86, 14);
		frmTrainModel.getContentPane().add(lblGrade);
		
		txtTestGrade = new JTextField();
		txtTestGrade.setText("0");
		txtTestGrade.setColumns(10);
		txtTestGrade.setBounds(615, 350, 70, 20);
		frmTrainModel.getContentPane().add(txtTestGrade);
		
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
		 
		        if (comboBox.getItemCount() == 0) {
		           //no trains exist.
		        																								//CREATE CLEAR CLASS LATER
		        } else if (selectedTrain == "Select a Train:"){
		        	//no train selected CLEAR ALL VALUES 
		        }else{
		            //if a train is selected update info for this train
		        	
		        	updateGUI(findTrain(Integer.parseInt(selectedTrain)));
					currTrain = findTrain(Integer.parseInt(selectedTrain));
		        }
			}

		});
		//action listener for test console
		btnApplyChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//action button to apply changes made to test console once pressed all pertinent values will be updated based on the component values
				//for prototype first thing to update is velocity based on power command
					String pwrCmdStr = txtTestPower.getText();
					Double pwrCmd = Double.parseDouble(pwrCmdStr);
					String newPassStr = txtTestAddPassengers.getText();
					int newPass = Integer.parseInt(newPassStr);
					currTrain.changePassengers(newPass);
					currTrain.setGrade(Double.parseDouble(txtTestGrade.getText()));
					new Launch().powerCommandToTrain(pwrCmd,currTrain);	
					
					//check radio buttons
					//left door
					if (rdbtnLeftDoorOpen.isSelected()) {
						currTrain.setLeftDoor(1);
					}else if(rdbtnLeftDoorClosed.isSelected()) {
						currTrain.setLeftDoor(0);
					}
					
					//right door
					if (rdbtnRightDoorOpen.isSelected()) {
						currTrain.setRightDoor(1);
					}else if(rdbtnRightDoorClosed.isSelected()) {
						currTrain.setRightDoor(0);
					}
					
					//Lights
					if (rdbtnLightsOn.isSelected()) {
						currTrain.setLights(1);
					}else if(rdbtnLightsOff.isSelected()) {
						currTrain.setLights(0);
					}
					
					//Service Brake
					if (rdbtnServiceBrakeOn.isSelected()) {
						currTrain.setServiceBrake(1);
					}else if(rdbtnServiceBrakeOff.isSelected()) {
						currTrain.setServiceBrake(0);
					}
					
					//Emergency Brake
					if (rdbtnEmergencyBrakeOn.isSelected()) {
						currTrain.setEmergencyBrake(1);
					}else if(rdbtnEmergencyBrakeOff.isSelected()) {
						currTrain.setEmergencyBrake(0);
					}
	
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
		if (currTrain != null){
				txtSpeed.setText("\n   "+ String.format("%.2f", currTrain.getVelocity())+" MPH");
				txtAuthority.setText("Block "+currTrain.getAuthority().getCurrBlock().blockNum().toString());
				txtTemperature.setText(currTrain.getTemp().toString()+" F");
				txtThermostat.setText(currTrain.getThermostat().toString()+" F");
				txtPass.setText(String.valueOf(currTrain.getNumPassengers()));
				txtCar.setText(String.valueOf(currTrain.getNumCars()));
				txtPower.setText(currTrain.getPower().intValue()+" W");
				txtMass.setText(currTrain.getMass().intValue()+" lbs");
				txtLength.setText(currTrain.getLength().toString());
				txtCurrBlock.setText("\r\nBlock "+currTrain.getGPS().getCurrBlock().blockNum().toString()+"\r\n");
				txtDistIntoBlock.setText("\r\n   "+currTrain.getGPS().getDistIntoBlock().toString()+" m\r\n");
			
                
                // set labels of status
                this.txtLeftDoor.setText(this.getStatusOfTrainDoors(this.currTrain.getLeftDoor()));
                this.txtRightDoor.setText(this.getStatusOfTrainDoors(this.currTrain.getRightDoor()));
                
                this.txtServiceBrake.setText(this.getStatusOfTrainLightsAndBrakes(this.currTrain.getServiceBrake()));
                this.txtEmergencyBrake.setText(this.getStatusOfTrainLightsAndBrakes(this.currTrain.getEmergencyBrake()));
                
                this.txtLights.setText(this.getStatusOfTrainLightsAndBrakes(this.currTrain.getLights()));   
				
				//check murphy console for failures
				//check engine failure
				if (rdbtnEngineOn.isSelected()) {
					//there is a failure in the engine
					currTrain.setEngineFailure(true);
				}else if (rdbtnEngineOff.isSelected()){
					//no failure in engines
					currTrain.setEngineFailure(false);
				}
				
				//check signal failure
				if (rdbtnSignalOn.isSelected()) {
					//there is a failure in the Signal
					currTrain.setSignalFailure(true);
				}else if (rdbtnSignalOff.isSelected()){
					//no failure in Signal
					currTrain.setSignalFailure(false);
				}
				
				//check brake Failure
				if (rdbtnBrakeOn.isSelected()) {
					//there is a failure in the Brakes
					currTrain.setBrakeFailure(true);
				}else if (rdbtnBrakeOff.isSelected()){
					//no failure in Brakes
					currTrain.setBrakeFailure(false);
				}
            }   	     
	}
        
        /**
         * 
         * 
         * 
         * @param numStatus
         * @return 
         */
        private String getStatusOfTrainDoors(int numStatus){
                    
            if (numStatus == 1){
                // open 
                return "OPEN";
            }else if (numStatus == 0){
                // close
                return "CLOSED";
            }else if (numStatus == -1){
                // failure
               return "FAILURE";
            }                
            return null; 
        }
        
        private String getStatusOfTrainLightsAndBrakes(int numStatus){
                    
            if (numStatus == 1){
                // open 
                return "ON";
            }else if (numStatus == 0){
                // close
                return "OFF";
            }else if (numStatus == -1){
                // failure
               return "FAILURE";
            }                
            return null; 
        }
        
      //method to search and return train object based on train ID
    	public Train findTrain(Integer id)
    	{
    		for(int i = 0; i < trainArray.size(); i++)
    		{
    			if (trainArray.get(i).getID() == id)
    			{
    				return trainArray.get(i);
    			}
    		}
    		return null;
    	}
        
        
        
}
