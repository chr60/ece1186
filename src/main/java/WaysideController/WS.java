package WaysideController;


import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptException;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.io.*;
import java.awt.Color;
import TrackModel.*;

public class WS extends JPanel {
	//GUI ELEMENTS
	private JTextArea blockConsole;
	private JTextArea trainConsole;
	private final JTextArea notifConsole = new JTextArea();
	TextAreaOutputStream notifStream;
	TextAreaOutputStream blockStream;
	private String name;
	private PLC plc;

	//WS ELEMENTS
	//Train
	//Block

	/**
	 * Create the panel.
	 * @throws IOException
	 */


	public WS(String name, PLC plcinit, Block [] blocks) throws IOException {
		setLayout(null);
		this.name = name;
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Line", "Green", "Red"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(10, 11, 137, 20);
		add(comboBox);

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Segment", "A", "B", "C"}));
		comboBox_1.setBounds(145, 11, 137, 20);
		add(comboBox_1);

		JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Block", "1", "2", "3", "4", "5", "6", "7", "8"}));
		comboBox_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<blocks.length; i++){

					if(comboBox.getSelectedItem().equals(blocks[i].getBlockLine()) && comboBox_1.getSelectedItem().equals(blocks[i].getBlockSection()) && comboBox_2.getSelectedItem().equals(new Integer(blocks[i].getBlockNum()).toString())){
						try {
							blockConsole.setText(null);
							blockStream.write(new String("\n" + blocks[i].getBlockLine()+ " - " + blocks[i].getBlockSection() + " - " + new Integer(blocks[i].getBlockNum()).toString()).getBytes());
							blockStream.write(new String("\nOccupied: " + new Boolean(blocks[i].getOccupied()).toString()).getBytes());
							blockStream.write(new String("\nNext Block Occupied: " + new Boolean(blocks[i].nextBlockForward().getOccupied()).toString()).getBytes());
							blockStream.write(new String("\nUpcoming Block Occupied: " + new Boolean(blocks[i].nextBlockForward().nextBlockForward().getOccupied()).toString()).getBytes());
							boolean status = plc.proceedEval(blocks[i]);
							if(status == true)
								blockStream.write(new String("\nTrain on block " + (i+1) + " is safe to proceed to block " + (i+2)).getBytes());
							else
								blockStream.write(new String("\nTrain on block " + (i+1) + " is NOT safe to proceed to block " + (i+2)).getBytes());
						} catch (IOException | ScriptException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				try {
					blockStream.write("\n\n".getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		comboBox_2.setBounds(281, 11, 130, 20);
		add(comboBox_2);

		//BLOCK CONSOLE
		blockConsole = new JTextArea();
		blockConsole.setBackground(Color.LIGHT_GRAY);
		blockConsole.setBounds(10, 30, 428, 189);
		add(blockConsole);
		blockConsole.setColumns(10);
		this.blockStream = new TextAreaOutputStream(blockConsole);

		JComboBox<String> comboBox_3 = new JComboBox<String>();
		comboBox_3.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Train"}));
		comboBox_3.setBounds(10, 237, 165, 20);
		add(comboBox_3);

		trainConsole = new JTextArea();
		trainConsole.setColumns(10);
		trainConsole.setBounds(10, 257, 428, 180);
		add(trainConsole);

		JLabel lblNotifications = new JLabel("Notifications");
		lblNotifications.setBounds(448, 14, 92, 14);
		add(lblNotifications);
		notifConsole.setBounds(448, 30, 658, 405);
		add(notifConsole);
		this.notifStream = new TextAreaOutputStream(notifConsole);
		this.notifStream.write(new String("THIS IS A TEST - Wayside Controller " + this.name + " created.").getBytes());

		JButton btnOpenTestConsole = new JButton("Open Test Console");
		btnOpenTestConsole.setBounds(261, 230, 150, 23);
		btnOpenTestConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				testPopUp console = new testPopUp();
			}
		});
		add(btnOpenTestConsole);
	}
	public TextAreaOutputStream getNotifStream(){
		return notifStream;
	}
	public PLC getPlc() {
		return plc;
	}
	public void setPlc(PLC plc) {
		this.plc = plc;
	}
}
