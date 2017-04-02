package WaysideController;

//Imports
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.script.ScriptException;
import javax.sound.midi.Track;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Train Packages
import TrackModel.Block;
import TrackModel.TrackModel;
public class WS {


	//WS ELEMENTS
	public String name;
	public String line;
	private PLC plc;
	private TrackModel Track;


	public WS(String name, TrackModel track) {
		this.name = name;
		this.Track = track;
	}

	public boolean manualSwitch(Block b){
		if(b.hasSwitch()){
			if(b.setSwitchState(-1)==true)
				b.setSwitchState(0);
			else if(b.setSwitchState(-1)==false)
				b.setSwitchState(1);
			return true;
		}
		return false;
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
