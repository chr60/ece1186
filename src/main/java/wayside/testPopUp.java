package proj1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings({"unchecked", "rawtypes"})

public class testPopUp {

	private JFrame frmTestConsole;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testPopUp window = new testPopUp();
					window.frmTestConsole.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public testPopUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestConsole = new JFrame();
		frmTestConsole.setTitle("Test Console");
		frmTestConsole.setBounds(100, 100, 451, 391);
		frmTestConsole.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestConsole.getContentPane().setLayout(null);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		textField = new JTextField();
		textField.setText("2");
		textField.setBounds(228, 67, 39, 20);
		frmTestConsole.getContentPane().add(textField);
		textField.setColumns(10);
		btnOk.setBounds(207, 318, 89, 23);
		frmTestConsole.getContentPane().add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(314, 318, 89, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTestConsole.setVisible(false);
			}
		});
		frmTestConsole.getContentPane().add(btnCancel);

		JLabel lblTestConsole = new JLabel("Track Controller: Test Console");
		lblTestConsole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTestConsole.setBounds(105, 11, 241, 14);
		frmTestConsole.getContentPane().add(lblTestConsole);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Line"}));
		comboBox.setBounds(10, 36, 118, 23);
		frmTestConsole.getContentPane().add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Select Segment"}));
		comboBox_1.setToolTipText("LINE");
		comboBox_1.setBounds(128, 36, 118, 23);
		frmTestConsole.getContentPane().add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Select Block"}));
		comboBox_2.setBounds(246, 36, 118, 23);
		frmTestConsole.getContentPane().add(comboBox_2);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Select Train"}));
		comboBox_3.setBounds(10, 168, 118, 23);
		frmTestConsole.getContentPane().add(comboBox_3);

		JLabel lblBlockOccupancy = new JLabel("Block Occupancy");
		lblBlockOccupancy.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBlockOccupancy.setBounds(10, 60, 89, 33);
		frmTestConsole.getContentPane().add(lblBlockOccupancy);

		JRadioButton rdbtnNone = new JRadioButton("None");
		rdbtnNone.setBounds(105, 65, 57, 23);
		frmTestConsole.getContentPane().add(rdbtnNone);

		JRadioButton rdbtnTrain = new JRadioButton(" Train #");
		rdbtnTrain.setSelected(true);
		rdbtnTrain.setBounds(164, 66, 70, 23);
		frmTestConsole.getContentPane().add(rdbtnTrain);

		JLabel lblSwitchPosition = new JLabel("Switch Position");
		lblSwitchPosition.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSwitchPosition.setBounds(10, 83, 89, 33);
		frmTestConsole.getContentPane().add(lblSwitchPosition);

		JRadioButton rdbtnOpen = new JRadioButton("OPEN");
		rdbtnOpen.setBounds(105, 88, 60, 23);
		frmTestConsole.getContentPane().add(rdbtnOpen);

		JRadioButton rdbtnClosed = new JRadioButton("CLOSED");
		rdbtnClosed.setSelected(true);
		rdbtnClosed.setBounds(164, 89, 70, 23);
		frmTestConsole.getContentPane().add(rdbtnClosed);

		JLabel lblRailroadCrossings = new JLabel("Railroad Crossings");
		lblRailroadCrossings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRailroadCrossings.setBounds(10, 105, 89, 33);
		frmTestConsole.getContentPane().add(lblRailroadCrossings);

		JRadioButton rdbtnActive = new JRadioButton("Active");
		rdbtnActive.setBounds(105, 110, 60, 23);
		frmTestConsole.getContentPane().add(rdbtnActive);

		JRadioButton rdbtnInactive = new JRadioButton("Inactive");
		rdbtnInactive.setSelected(true);
		rdbtnInactive.setBounds(164, 111, 70, 23);
		frmTestConsole.getContentPane().add(rdbtnInactive);

		JLabel lblColorOfLights = new JLabel("Color of Lights");
		lblColorOfLights.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblColorOfLights.setBounds(10, 126, 89, 33);
		frmTestConsole.getContentPane().add(lblColorOfLights);

		JRadioButton rdbtnRed = new JRadioButton("Red");
		rdbtnRed.setBounds(105, 131, 60, 23);
		frmTestConsole.getContentPane().add(rdbtnRed);

		JRadioButton rdbtnGreen = new JRadioButton("Green");
		rdbtnGreen.setSelected(true);
		rdbtnGreen.setBounds(164, 132, 70, 23);
		frmTestConsole.getContentPane().add(rdbtnGreen);

		JLabel lblSetSuggestedSpeed = new JLabel("Set Suggested Speed");
		lblSetSuggestedSpeed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetSuggestedSpeed.setBounds(10, 191, 103, 33);
		frmTestConsole.getContentPane().add(lblSetSuggestedSpeed);

		textField_1 = new JTextField();
		textField_1.setText("55");
		textField_1.setColumns(10);
		textField_1.setBounds(137, 197, 39, 20);
		frmTestConsole.getContentPane().add(textField_1);

		JLabel lblKph = new JLabel("MPH");
		lblKph.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblKph.setBounds(178, 191, 30, 33);
		frmTestConsole.getContentPane().add(lblKph);

		JLabel lblSetSuggestedAuthority = new JLabel("Set Suggested Authority");
		lblSetSuggestedAuthority.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSetSuggestedAuthority.setBounds(10, 214, 118, 33);
		frmTestConsole.getContentPane().add(lblSetSuggestedAuthority);

		textField_2 = new JTextField();
		textField_2.setText("100");
		textField_2.setColumns(10);
		textField_2.setBounds(137, 220, 39, 20);
		frmTestConsole.getContentPane().add(textField_2);

		JLabel lblM = new JLabel("mi");
		lblM.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblM.setBounds(178, 214, 30, 33);
		frmTestConsole.getContentPane().add(lblM);

		JLabel lblCurrentSpeed = new JLabel("Current Speed");
		lblCurrentSpeed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCurrentSpeed.setBounds(10, 235, 118, 33);
		frmTestConsole.getContentPane().add(lblCurrentSpeed);

		textField_3 = new JTextField();
		textField_3.setText("55");
		textField_3.setColumns(10);
		textField_3.setBounds(137, 241, 39, 20);
		frmTestConsole.getContentPane().add(textField_3);

		JLabel lblMph = new JLabel("MPH");
		lblMph.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMph.setBounds(178, 235, 30, 33);
		frmTestConsole.getContentPane().add(lblMph);

		JLabel lblCurrentAuthority = new JLabel("Current Authority");
		lblCurrentAuthority.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCurrentAuthority.setBounds(10, 258, 118, 33);
		frmTestConsole.getContentPane().add(lblCurrentAuthority);

		textField_4 = new JTextField();
		textField_4.setText("150");
		textField_4.setColumns(10);
		textField_4.setBounds(137, 264, 39, 20);
		frmTestConsole.getContentPane().add(textField_4);

		JLabel lblM_1 = new JLabel("mi");
		lblM_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblM_1.setBounds(178, 258, 30, 33);
		frmTestConsole.getContentPane().add(lblM_1);

		JLabel lblFailureStatus = new JLabel("Failure Status");
		lblFailureStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFailureStatus.setBounds(10, 279, 118, 33);
		frmTestConsole.getContentPane().add(lblFailureStatus);

		JRadioButton rdbtnNone_1 = new JRadioButton("None");
		rdbtnNone_1.setBounds(135, 283, 60, 23);
		frmTestConsole.getContentPane().add(rdbtnNone_1);

		JRadioButton rdbtnFail = new JRadioButton("Fail");
		rdbtnFail.setSelected(true);
		rdbtnFail.setBounds(197, 283, 49, 23);
		frmTestConsole.getContentPane().add(rdbtnFail);
		frmTestConsole.setVisible(true);
	}
}
