package TrackModel;

/**
 * Class for the block object. Contains attributes of a block associated with a train track block.
 * Fundamental building block of the track module.
 * @author Michael
 */
import java.util.ArrayList;

public class Block implements Comparable<Block>{

	public Double blockLen;
	public Boolean occupied;
	public Double speedLimit;
	public Double blockGrade;
	public Double blockElevation;
	public Boolean signals;
	public Boolean brokenRail;
	public Boolean circuitFailure;
	public Boolean powerFailure;
	public Boolean trackHeaters;
	public String stationName;
	public Boolean isUnderground;
	public String arrowDirection;
	public Integer blockNum;
	public Boolean switchState;
	public String switchBlock;
	public Boolean hasSwitch;
	public String blockLine;
	public String blockSection;
	public Block nextBlockForward;
	public Block switchNextBlockForward;
	public Block nextBlockBackward;
	public Block rootBlock;
	public TrackModel superTrackModel;
	public Boolean maintenence;

	public Block(TrackModel track, Boolean occupied, Boolean isUnderground, Double blockLen, Double blockGrade, Double elevation, Double speedLimit,
				String stationName, String arrowDirection, String blockLine, String blockSection, Integer blockNum, Boolean hasSwitch, String switchBlock){

		this.superTrackModel = track;
		this.blockLen = blockLen;
		this.blockGrade = blockGrade;
		this.blockElevation = elevation;
		this.speedLimit = speedLimit;
		this.stationName = stationName;
		this.isUnderground = isUnderground;
		this.arrowDirection = arrowDirection;
		this.blockNum = blockNum;
		this.switchBlock = switchBlock;
		this.hasSwitch = hasSwitch;
		this.blockLine = blockLine;
		this.blockSection = blockSection;

		this.occupied = false;
		this.trackHeaters = false;
		this.signals = false;
		this.brokenRail = false;
		this.circuitFailure = false;
		this.powerFailure = false;
		this.maintenence=false;
	}

	/**
	* Returns the length of a block object
	*/
	public Double getLen(){
		return this.blockLen;
	}

	public Boolean getBroken(){
		return this.brokenRail;
	}

	/**
	*	Returns if a track block is open or not based upon the state of the breakages of a block
	*/
	public Boolean getOpen(){
		if (this.brokenRail || this.powerFailure || this.occupied || this.maintenence){
			return false;
		}
		else{
			return true;
		}
	}

	/**
	*	Returns the occupied state of a given block object
	*/
	public Boolean getOccupied(){
		return this.occupied;
	}

	public void setOccupied(){
		this.occupied= (!this.occupied);
	}

	/**
	* Returns the speed limit of a given block
	*/
	public Double getSpeedLimit(){
		return this.speedLimit;
	}

	/**
	* Returns the cumulative elevation of a given blcok
	*/
	public Double getElevation(){
		return this.blockElevation;
	}

	/**
	* Returns the grade of a given block
	*/
	public Double getGrade(){
		return this.blockGrade;
	}

	public Boolean getCircuitFailure(){
		return this.circuitFailure;
	}

	public Boolean getPowerFailure(){
		return this.powerFailure;
	}

	public Boolean getUnderground(){
		return this.isUnderground;
	}

	public String getStationName(){
		return this.stationName;
	}

	public Boolean getTrackHeaters(){
		return this.trackHeaters;
	}

	public void setNextBlockForward(){
		this.nextBlockForward = this;
	}

	public void setNextBlockForward(Block setBlock){
		this.nextBlockForward = setBlock;
	}

	public Integer getBlockNum(){
		return this.blockNum;
	}
	
	/**
	* Returns the section a block is on
	*/
	public String getBlockSection(){
		return this.blockSection;
	}

	/**
	* Returns the line a block is on
	*/
	public String getBlockLine(){
		return this.blockLine;
	}

	/** Setter for the next block forward in the condition for a switch is present.
	*	By default, initializes the switch to the lower block (as determined by blockNum)
	* 	to be destination when the this.switchState = true
	*/
	public void setNextBlockForward(Block lowBlock, Block highBlock){
		this.nextBlockForward = lowBlock;
		this.switchNextBlockForward = highBlock;
		this.switchState = true;
	}

	/** 
	* Sets the next block in the "reverse" direction. Does not handle switch conditions
	* @param setBlock the block to set the default backwards block to
	*/
	public void setNextBlockBackward(Block setBlock){
		this.nextBlockBackward = setBlock;
	}

	/** 
	* Sets the root block in the "reverse" direction to deal with switch conditions
	* @param rootBlock the root block backwards of a switch block
	*/
	public void setRootBlock(Block rootBlock){
		this.rootBlock = rootBlock;
	}

	/** Gets the next block forward along based upon the current switch state. If there is no
	* switch, it returns the next block
	*/
	public Block nextBlockForward(){
		if(this.switchState != null){
			if (this.switchState.equals(true)){
				return this.nextBlockForward;
			}
			else{
				return this.switchNextBlockForward;
			}
		}
		else{
			return this.nextBlockForward;
		}
	}

	public Block nextBlockBackward(){
		if(this.rootBlock != null){
			if (this.rootBlock.nextBlockForward().equals(this)){
				return this.rootBlock;
			}
			else{				
				return this;
			}
		}
		else{
			return this.nextBlockBackward;
		}
	}

	/**
	* Setter for the Wayside Controller module to set a switch state based upon an Integer
	* Maps 1 to true, 0 to false based upon the track internal convention.
	* @param setInt integer for the switch state to be set to
	* @todo Add strict value assertions that setInt is either 0 or 1 
	*/
	public Boolean setSwitchState(Integer setInt){
		//condition check for having a switch
		assert(this.switchState != null);

		if(setInt.equals(1)){
			this.switchState = true;
		}
		else if(setInt.equals(0)){
			this.switchState = false;
		}
		return this.switchState;
	}
	
	/**
	* Returns the associated station of the block
	* @param track the track the block is on
	* @return the Station object of the associated block
	*/
	public Station getAssociatedStation(){
		return this.superTrackModel.blockStationMap.get(this);
	}

	/** Implements the comparable interface for blocks via the associalted blockNum of a given block.
	* At this time, this implementation does not verify that blocks are the same across lines.
	*/
	@Override
	public int compareTo(Block thatBlock){
		return Integer.compare(this.blockNum, thatBlock.blockNum)+ this.blockLine.compareTo(thatBlock.blockLine);
	}
}
