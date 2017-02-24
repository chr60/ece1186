

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import java.awt.Color;
import java.util.Set;
import java.util.Arrays;

public class TrackGUI {

	private JFrame frame;
	private JTextField lengthField;
	private JTextField gradeField;
	private JTextField elevationField;
	private JTextField speedField;
	private JLabel lblBlockLength;
	private JLabel lblBlockGrade;
	private JLabel lblElevation;
	private JLabel lblSpeedLimit;
	private JLabel lblLengthUnit;
	private JLabel lblGradePercent;
	private JLabel lblElevationUnit;
	private JLabel lblSpeedUnit;
	private JLabel lblOccupied;
	private JLabel lblCrossingsActive;
	private JToggleButton toggleSignals;
	private JLabel lblStation;
	private JTextField txtname;
	private JLabel lblNewLabel;
	private JLabel lblBrokenRail;
	private JToggleButton toggleBroken;
	private JToggleButton toggleCircuitFailure;
	private JLabel lblCircuitFailure;
	private JLabel lblPowerFailure;
	private JToggleButton togglePowerFailure;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackGUI window = new TrackGUI();
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
	public TrackGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		//Load up the track model
		TrackModel track = new TrackModel();
		track.readCSV();

		Set<Integer> blockInts = track.trackList.keySet();
		String[] blockStrings = new String[blockInts.size()];
		
		Integer[] intArr = blockInts.toArray(new Integer[blockInts.size()]);

		for(int i=0;i<blockInts.size();i++){
			blockStrings[i] = Integer.toString(intArr[i]);
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 604, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		String[] lineStrings = { "Red Line"};
		JComboBox dropdownLine = new JComboBox(lineStrings);
		dropdownLine.setBounds(337, 22, 86, 23);
		frame.getContentPane().add(dropdownLine);

		
		String[] segmentStrings = {"Dummy", "Values"};
		JComboBox dropdownSegment = new JComboBox(segmentStrings);
		dropdownSegment.setBounds(420, 22, 86, 23);
		frame.getContentPane().add(dropdownSegment);
		
		JComboBox dropdownBlock = new JComboBox(blockStrings);
		dropdownBlock.setBounds(504, 22, 86, 23);
		frame.getContentPane().add(dropdownBlock);
		
		//Length
		lengthField = new JTextField();
		lengthField.setHorizontalAlignment(SwingConstants.CENTER);
		lengthField.setText("#");
		lengthField.setBounds(450, 58, 86, 23);
		frame.getContentPane().add(lengthField);
		lengthField.setColumns(10);
		
		//Grade
		gradeField = new JTextField();
		gradeField.setHorizontalAlignment(SwingConstants.CENTER);
		gradeField.setText("FIELD 1");
		gradeField.setColumns(10);
		gradeField.setBounds(450, 81, 86, 23);
		frame.getContentPane().add(gradeField);
		
		//Elevation
		elevationField = new JTextField();
		elevationField.setHorizontalAlignment(SwingConstants.CENTER);
		elevationField.setText("#");
		elevationField.setColumns(10);
		elevationField.setBounds(450, 104, 86, 23);
		frame.getContentPane().add(elevationField);
		
		//Speed Limit
		speedField = new JTextField();
		speedField.setText("#");
		speedField.setHorizontalAlignment(SwingConstants.CENTER);
		speedField.setColumns(10);
		speedField.setBounds(450, 127, 86, 23);
		frame.getContentPane().add(speedField);
		
		lblBlockLength = new JLabel("Length");
		lblBlockLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBlockLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBlockLength.setBounds(367, 58, 79, 23);
		frame.getContentPane().add(lblBlockLength);
		
		lblBlockGrade = new JLabel("Grade");
		lblBlockGrade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBlockGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBlockGrade.setBounds(378, 81, 68, 23);
		frame.getContentPane().add(lblBlockGrade);
		
		lblElevation = new JLabel("Elevation");
		lblElevation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblElevation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElevation.setBounds(367, 102, 79, 23);
		frame.getContentPane().add(lblElevation);
		
		lblSpeedLimit = new JLabel("Speed Limit");
		lblSpeedLimit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSpeedLimit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSpeedLimit.setBounds(367, 125, 79, 23);
		frame.getContentPane().add(lblSpeedLimit);
		
		lblLengthUnit = new JLabel("f");
		lblLengthUnit.setHorizontalAlignment(SwingConstants.LEFT);
		lblLengthUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLengthUnit.setBounds(538, 56, 38, 23);
		frame.getContentPane().add(lblLengthUnit);
		
		lblGradePercent = new JLabel("%");
		lblGradePercent.setHorizontalAlignment(SwingConstants.LEFT);
		lblGradePercent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGradePercent.setBounds(538, 81, 38, 23);
		frame.getContentPane().add(lblGradePercent);
		
		lblElevationUnit = new JLabel("f");
		lblElevationUnit.setHorizontalAlignment(SwingConstants.LEFT);
		lblElevationUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElevationUnit.setBounds(538, 102, 38, 23);
		frame.getContentPane().add(lblElevationUnit);
		
		lblSpeedUnit = new JLabel("feet/s");
		lblSpeedUnit.setHorizontalAlignment(SwingConstants.LEFT);
		lblSpeedUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSpeedUnit.setBounds(538, 125, 38, 23);
		frame.getContentPane().add(lblSpeedUnit);
		
		lblOccupied = new JLabel("Occupied");
		lblOccupied.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOccupied.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOccupied.setBounds(367, 183, 79, 23);
		frame.getContentPane().add(lblOccupied);
		
		JToggleButton toggleOccupied = new JToggleButton("Y");
		toggleOccupied.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleOccupied.setSelected(true);
		toggleOccupied.setBounds(450, 185, 45, 23);
		frame.getContentPane().add(toggleOccupied);
		toggleOccupied.setSelected(true);

		JLabel lblHeaters = new JLabel("Heaters ON?");
		lblHeaters.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeaters.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHeaters.setBounds(367, 211, 79, 23);
		frame.getContentPane().add(lblHeaters);
		
		JToggleButton toggleHeatersOn = new JToggleButton("N/A");
		toggleHeatersOn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleHeatersOn.setBounds(450, 211, 45, 23);
		frame.getContentPane().add(toggleHeatersOn);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(337, 305, 253, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblCrossing = new JLabel("Crossings?");
		lblCrossing.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCrossing.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCrossing.setBounds(367, 235, 79, 23);
		frame.getContentPane().add(lblCrossing);
		
		JToggleButton toggleCrossings = new JToggleButton("N/A");
		toggleCrossings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleCrossings.setBounds(450, 235, 45, 23);
		frame.getContentPane().add(toggleCrossings);
		
		lblCrossingsActive = new JLabel("Signals ON?");
		lblCrossingsActive.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCrossingsActive.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCrossingsActive.setBounds(347, 259, 99, 23);
		frame.getContentPane().add(lblCrossingsActive);
		
		toggleSignals = new JToggleButton("N/A");
		toggleSignals.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleSignals.setBounds(450, 259, 45, 23);
		frame.getContentPane().add(toggleSignals);
		
		lblStation = new JLabel("Station");
		lblStation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStation.setBounds(367, 149, 79, 23);
		frame.getContentPane().add(lblStation);
		
		txtname = new JTextField();
		txtname.setText("(name)");
		txtname.setHorizontalAlignment(SwingConstants.CENTER);
		txtname.setColumns(10);
		txtname.setBounds(450, 151, 86, 23);
		frame.getContentPane().add(txtname);
		
		lblNewLabel = new JLabel("MURPHY");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(430, 305, 105, 38);
		frame.getContentPane().add(lblNewLabel);
		
		lblBrokenRail = new JLabel("Broken Rail");
		lblBrokenRail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBrokenRail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrokenRail.setBounds(367, 336, 79, 23);
		frame.getContentPane().add(lblBrokenRail);
		
		toggleBroken = new JToggleButton("N");
		toggleBroken.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleBroken.setBounds(450, 338, 45, 23);
		frame.getContentPane().add(toggleBroken);
		
		toggleCircuitFailure = new JToggleButton("N");
		toggleCircuitFailure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		toggleCircuitFailure.setBounds(450, 364, 45, 23);
		frame.getContentPane().add(toggleCircuitFailure);
		
		lblCircuitFailure = new JLabel("Circuit Failure");
		lblCircuitFailure.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCircuitFailure.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCircuitFailure.setBounds(347, 364, 99, 23);
		frame.getContentPane().add(lblCircuitFailure);
		
		lblPowerFailure = new JLabel("Power Failure");
		lblPowerFailure.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPowerFailure.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPowerFailure.setBounds(347, 388, 99, 23);
		frame.getContentPane().add(lblPowerFailure);
		
		togglePowerFailure = new JToggleButton("N");
		togglePowerFailure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		togglePowerFailure.setBounds(450, 388, 45, 23);
		frame.getContentPane().add(togglePowerFailure);
		
		toggleOccupied.setSelected(false);
		toggleOccupied.setText("N");		
		
		//Button handling
		dropdownLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				String s = (String) dropdownLine.getSelectedItem();
				toggleOccupied.setSelected(false);
				toggleOccupied.setText("N");
			}
		});
		
		/*
		dropdownSegment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				String s = (String) dropdownSegment.getSelectedItem();
				System.out.println(s);
			}
		});
		*/
		dropdownBlock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				String s = (String) dropdownBlock.getSelectedItem();
				
				lengthField.setText(String.valueOf(track.trackList.get(Integer.valueOf(s)).getLen()));
				elevationField.setText(String.valueOf(track.trackList.get(Integer.valueOf(s)).getElevation()));
				speedField.setText(String.valueOf(track.trackList.get(Integer.valueOf(s)).getSpeedLimit()));
				gradeField.setText(String.valueOf(track.trackList.get(Integer.valueOf(s)).getGrade()));
				txtname.setText(track.trackList.get(Integer.valueOf(s)).getInfrastructure());

				Boolean isOccupied = track.trackList.get(Integer.valueOf(s)).getOccupied();
				Boolean isBroken = track.trackList.get(Integer.valueOf(s)).getBroken();
				Boolean isCircuitFailure = track.trackList.get(Integer.valueOf(s)).getCircuitFailure();
				Boolean isPowerFailure = track.trackList.get(Integer.valueOf(s)).getPowerFailure();

				toggleOccupied.setSelected(isOccupied);
				toggleBroken.setSelected(isBroken);
				toggleCircuitFailure.setSelected(isCircuitFailure);
				togglePowerFailure.setSelected(isPowerFailure);

				if (isOccupied){
					toggleOccupied.setText("Y");
					toggleOccupied.setSelected(true);
				}
				else{
					toggleOccupied.setText("N");
					toggleOccupied.setSelected(false);
				}
				
				if (isBroken){
					toggleBroken.setText("Y");
					toggleBroken.setSelected(true);
				}
				else{
					toggleBroken.setText("N");
					toggleBroken.setSelected(false);
				}
				if (isCircuitFailure){
					toggleCircuitFailure.setText("Y");
					toggleCircuitFailure.setSelected(true);
				}
				else{
					toggleCircuitFailure.setText("N");
					toggleCircuitFailure.setSelected(false);
				}

				if (isPowerFailure){
					togglePowerFailure.setText("Y");
					togglePowerFailure.setSelected(true);
				}
				else{
					togglePowerFailure.setText("N");
					togglePowerFailure.setSelected(false);
				}
			}
		});

	}
	/** Listens to the combo box. */
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		String name = (String)cb.getSelectedItem();
	}

}
