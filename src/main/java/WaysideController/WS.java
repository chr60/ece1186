package WaysideController;

//Imports
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.ArrayList;
import java.util.Set;

//Train Packages
import TrackModel.Block;
import TrackModel.TrackModel;
public class WS {


	//WS ELEMENTS
	public String line;
	private PLC plc;
	private TrackModel Track;


	public WS(String line, TrackModel track) {
		this.line = line;
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
	public Integer switchStatus(Block b){
		if(b.hasSwitch()){
			boolean result = b.setSwitchState(-1);
			if(result == true)
				return 1;	//default position, root connected to lower block num
			else if(result == false)
				return 0;	//switched position, root connected to higher block num
		}
		return -1;
	}
	public ArrayList<Block> checkForBroken(){
		ArrayList<Block> brokenBlocks = Track.getBrokenBlocks(this.line);
		if(brokenBlocks.size()>0){
			//alert CTC
		}
		return brokenBlocks;
	}
	public Block getBlock(Block b) {
		Block liveBlock = Track.lateralLookup(b);
		Block toReturn = new Block(null, liveBlock.getOccupied(), null, null, null, null, liveBlock.getSpeedLimit(), liveBlock.getStationName(), null, liveBlock.getBlockLine(), liveBlock.getBlockSection(), liveBlock.blockNum(), liveBlock.hasSwitch(), liveBlock.getSwitchBlock());
		return toReturn;
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
