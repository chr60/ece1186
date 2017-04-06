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
import CTC.CTCgui;

public class WS {


	//WS ELEMENTS
	public String line;
	private PLC plc;
	private TrackModel track;
	private WaysideGUI waysideGui;
	private CTCgui ctc;


	public WS(String line, TrackModel trackIn) {
		this.line = line;
		this.track = trackIn;
	}
	public void setGUI(WaysideGUI gui){
		this.waysideGui = gui;
	}
	public void setCTC(CTCgui ctcIn){
		this.ctc = ctcIn;
	}

	/**
	 * Called from launcher to update by clock tick
	 */
	public void update() throws ScriptException{
		checkForBroken();
		if(this.plc!=null){
			runPLC();
		}
	}

	/**
	 * Sets speed and authority (from CTC) to matched blocks on track
	 * @param ArrayList<Block> Blocks - Blocks w/ speed & auth to be set on track
	 */
	public void setSpeedAuth(ArrayList<Block> Blocks){
		for(Block b : Blocks){
			Block trackBlock = track.lateralLookup(b);
			trackBlock.setSuggestedSpeed(b.getSuggestedSpeed());
			trackBlock.setAuthority(b.getAuthority());
			//this.waysideGui.printNotification("Auth: " + b.getAuthority().blockNum() + "\n Set to Block: " + b.blockNum());
		}
	}

	/**
	 *
	 * @param  Block b - Block with associated crossing
	 * @return State of crossing associated with Block b, null if no crossing exists
	 */
	public Boolean getCrossing(Block b){
		for(Block trackBlock: track.viewCrossingMap().keySet()){
			if(b.equals(trackBlock))
				return trackBlock.getAssociatedCrossing().viewCrossingState();
		}
		return null;
	}

	/**
	 * Polls track for occupancy of blocks, and creates copies of Blocks only only occupancy attributes.
	 * @return List of copied blocks with their occupancy statuses same as track
	 */
	public ArrayList<Block> getOccupancy(){
		ArrayList<Block> occupancyList = new ArrayList<Block>();
		for(String section : track.viewTrackList().get(line).keySet()){
			for(Integer blk : track.viewTrackList().get(line).get(section).keySet()){
				Block trackBlock = track.getBlock(line, section, blk);
				Block blockToAdd = new Block(null, null, null, null, null, null, null, null, null, null, null, trackBlock.blockNum(), null, null);
				if(trackBlock.getOccupied()){
					blockToAdd.setOccupied(true);
					occupancyList.add(blockToAdd);
				}
			}
		}
		return occupancyList;
	}

	/**
	 * Manually manipulate a switch to opposite position
	 * @param  Block b - Root block of switch.
	 * @return true if switch successfully switched, false if !b.hasSwitch()
	 */
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

	/**
	 * Returns status of a switch.
	 * @param  Block b - Root block of switch.
	 * @return 1 if default, 0 if !default, -1 if block is not a root block (see below).
	 */
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
		ArrayList<Block> brokenBlocks = track.getBrokenBlocks(this.line);
		if(brokenBlocks.size()>0){
			//alert CTC
			for(Block b: brokenBlocks){
				if(this.waysideGui!=null)
					this.waysideGui.printNotification("Broken Block detected - Block: " + b.blockNum());
			}
		}
		return brokenBlocks;
	}

	/**
	 * Creates copy of block from track with only desired characteristics defined for CTC.
	 * @param  Block b - desired block to be looked up.
	 * @return  - Copy of block with select info defined.
	 */
	public Block getBlock(Block b) {
		Block liveBlock = track.lateralLookup(b);
		Block toReturn = new Block(null, liveBlock.getOccupied(), null, null, null, null, liveBlock.getSpeedLimit(), liveBlock.getStationName(), null, liveBlock.getBlockLine(), liveBlock.getBlockSection(), liveBlock.blockNum(), liveBlock.hasSwitch(), liveBlock.getSwitchBlock());
		return toReturn;
	}

	public void updateInputs(TrackModel updatedTrack){

	}

	public void runPLC() throws ScriptException{
		this.plc.runSwitchPLC();
		this.plc.runCrossingPLC();
	}

	public PLC getPlc() {
		return plc;
	}

	public boolean setPlc(PLC plc) {
		this.plc = plc;
		return true;
	}
}
