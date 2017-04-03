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

public class MiscPanel extends JPanel{

	private String mainMode;
	private String autoMode;
	private TrainPanel tPanel;
	private CTCgui mainPanel;

	public MiscPanel(){

		this.setMainMode(mainMode);
		this.setAutoMode(autoMode);

		JLabel lblthroughput = new JLabel("trains/hr");
		lblthroughput.setBounds(65, 11, 84, 14);
		add(lblthroughput);

		JLabel return_throughput = new JLabel("#");
		return_throughput.setHorizontalAlignment(SwingConstants.CENTER);
		return_throughput.setBounds(10, 11, 52, 14);
		add(return_throughput);

		JButton buttonShowSchedule = new JButton("Show Schedule");
		buttonShowSchedule.setBounds(20, 36, 118, 23);
		add(buttonShowSchedule);
		buttonShowSchedule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//new SchedulePopup();
			}
		});

		JButton buttonAllTrains = new JButton("Show All Trains");
		buttonAllTrains.setBounds(20, 58, 118, 23);
		add(buttonAllTrains);
		buttonAllTrains.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//new TrainListPopup();
			}
		});

		JLabel lblModes = new JLabel("MODES");
		lblModes.setBounds(176, 11, 46, 14);
		add(lblModes);
		lblModes.setHorizontalAlignment(SwingConstants.LEFT);

		JRadioButton radioAuto = new JRadioButton("Automatic");
		radioAuto.setSelected(true);
		radioAuto.setBounds(176, 29, 78, 23);
		add(radioAuto);

		JRadioButton radioManual = new JRadioButton("Manual");
		radioManual.setBounds(255, 29, 73, 23);
		add(radioManual);


		JRadioButton radioFixed = new JRadioButton("Fixed Block");
		radioFixed.setSelected(true);
		radioFixed.setBounds(255, 61, 84, 23);
		add(radioFixed);

		JRadioButton radioMBO = new JRadioButton("MBO");
		radioMBO.setBounds(176, 61, 65, 23);
		add(radioMBO);

		ButtonGroup scheduleGroup = new ButtonGroup();
		scheduleGroup.add(radioFixed);
		scheduleGroup.add(radioMBO);

		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(radioAuto);
		modeGroup.add(radioManual);

		radioAuto.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String mainMode = "auto";
				setMainMode(mainMode);
				radioFixed.setEnabled(true);
				radioMBO.setEnabled(true);

			}
		});

		radioManual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String mainMode = "manual";
				setMainMode(mainMode);
				radioFixed.setEnabled(false);
				radioMBO.setEnabled(false);

			}
		});



	}

	public void setMainMode(String mainMode){
		this.mainMode = mainMode;
	}
	public void setAutoMode(String autoMode){
		this.autoMode = autoMode;
	}

	public String getAutoMode(){
		return autoMode;
	}
	public String getMainMode(){
		return mainMode;
	}

	public void setTrainPanel(TrainPanel tp){
		this.tPanel = tp;
	}


	/*
	public void update(SchedulePopup sPopup, TrainListPopup tPopup){
		//tPopup.update();
		//sPopup.update();
	}
	*/

}
