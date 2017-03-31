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
import java.util.Set;
import TrackModel.*;

public class WS {


	//WS ELEMENTS
	//Train
	private String name;
	private PLC plc;


	public WS(String name, PLC plcinit, TrackModel track) throws IOException {

	}
	public boolean manualSwitch(Block block){
		return true;
	}
	public void updateInputs(TrackModel updatedTrack){

	}
	public boolean[] runPLC(){

		return null;
	}
	public PLC getPlc() {
		return plc;
	}
	public boolean setPlc(PLC plc) {
		this.plc = plc;
		return true;
	}

}
