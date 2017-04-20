package WaysideController;

import CTC.CTCgui;
import TrackModel.Block;
import TrackModel.TrackModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

//Imports
//Train Packages
public class WS {


	//WS ELEMENTS
	public String line;
  public String number;
  public String name;
	private PLC plc;
	private TrackModel track;
	private WaysideGUI waysideGui;
	private CTCgui ctc;
  private ArrayList<Block> blockJurisdiction;


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
   * Closes a block on the track for Maintenance (FOR CTC)
   * @param Block b - Block to be closed
   */
  public void closeBlock(Block b){
    this.track.lateralLookup(b).setClosed(true);
  }

  /**
  * Returns value of blockJurisdiction
  * @return
  */
  public ArrayList<Block> getBlockJurisdiction() {
    return blockJurisdiction;
  }

  /**
  * Sets new value of blockJurisdiction
  * @param blockJurisdiction
  */
  public void setBlockJurisdiction(ArrayList<Block> blockJurisdiction) {
    this.blockJurisdiction = blockJurisdiction;
  }

	/**
	 * Called every clock tick from launcher
	 * @throws ScriptException B/C of PLC code call
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
				blockToAdd.setSuggestedSpeed(trackBlock.getSuggestedSpeed());
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
		if(b.hasSwitch() && !b.getOccupied()){
			if(b.viewSwitchState()==true)
				b.getAssociatedSwitch().setSwitchState(false);
			else if(b.viewSwitchState()==false)
				b.getAssociatedSwitch().setSwitchState(true);
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
			boolean result = b.viewSwitchState();
			if(result == true)
				return 1;	//default position, root connected to lower block num
			else if(result == false)
				return 0;	//switched position, root connected to higher block num
		}
		return -1;
	}

  /**
   * Queries track and checks for broken blocks
   * @return List of broken blocks (if any)
   */
	public ArrayList<Block> checkForBroken(){
		ArrayList<Block> brokenBlocks = track.getBrokenBlocks(this.line);
		if(brokenBlocks.size()>0){
			for(Block b: brokenBlocks){
        b.setOccupied(true);  //Set occupied so it is 'unavailable'
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
		// Block toReturn = new Block(null, liveBlock.getOccupied(), null, null, null, null, liveBlock.getSpeedLimit(), liveBlock.getStationName(), null, liveBlock.getBlockLine(), liveBlock.getBlockSection(), liveBlock.blockNum(), liveBlock.hasSwitch(), liveBlock.getSwitchBlock());
		return liveBlock;
	}

  /**
   * Manipulates track when CTC sends Maintenance.
   * @param Block b - block to be fixed.
   */
  public void transferMaintenance(Block b){
    b.setBroken(false);
    b.setOccupied(false);
    b.setClosed(false);
  }

  /**
  * Runs code of PLC file that is loaded
  * @throws ScriptException
  */
  public void runPLC() throws ScriptException{
    this.plc.runSwitchPLC();
    this.plc.runCrossingPLC();
    this.plc.updateOccupancy();
  }

  /**
   * Sets PLC file of the WS unit.
   * @param  PLC PLC file to be set to WS
   */
	public void setPlc(PLC plc) {
		this.plc = plc;
	}
  /**
   * Sets ID number of WS
   * @param String num - ID number of WS
   */
  public void setNum(String num){
    this.number = num;
    this.name = new String(this.line+this.number);
  }
}
