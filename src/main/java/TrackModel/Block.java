package TrackModel;

/**
 * Class for the block object. Contains attributes of a block associated with a train track block.
 * Fundamental building block of the track module.
 * \author Michael
 */
import java.util.ArrayList;

public class Block implements Comparable<Block>{

	public Double blockLen;
	public Boolean occupied;
	public Double speedLimit;
	public Double blockGrade;
	public Double blockElevation;
	public Boolean signals;
	public Boolean crossings;
	public Boolean brokenRail;
	public Boolean circuitFailure;
	public Boolean powerFailure;
	public Boolean trackHeaters;
	public String stationName;
	public Boolean isUnderground;
	public Block nextBlock;
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

	public Block(Boolean occupied, Boolean isUnderground, Double blockLen, Double blockGrade, Double elevation, Double speedLimit,
				String stationName, String arrowDirection, String blockLine, String blockSection, Integer blockNum, Boolean hasSwitch, String switchBlock){
		
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
		this.crossings = false;
		this.brokenRail = false;
		this.circuitFailure = false;
		this.powerFailure = false;

	}
	
	public Double getLen(){
		return this.blockLen;
	}

	public Boolean getCrossings(){
		return this.crossings;
	}

	public Boolean getBroken(){
		return this.brokenRail;
	}

	public Boolean getOccupied(){
		return this.occupied;
	}

	public void setOccupied(){
		this.occupied= (!this.occupied);
	}

	public Double getSpeedLimit(){
		return this.speedLimit;
	}

	public Double getElevation(){
		return this.blockElevation;
	}

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

	/** Setter for the next block forward in the condition for a switch is present.
	*	By default, initializes the switch to the lower block (as determined by blockNum)
	* 	to be destination when the this.switchState = true
	*/
	public void setNextBlockForward(Block lowBlock, Block highBlock){
		this.nextBlockForward = lowBlock;
		this.switchNextBlockForward = highBlock;
		this.switchState = true;
	}

	/** Sets the next block in the "reverse" direction. 
	*/	
	public void setNextBlockBackward(Block setBlock){
		this.nextBlockBackward = setBlock;
	}

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
		return this.nextBlockBackward;
	}

	/** Implements the comparable interface for blocks via the associalted blockNum of a given block.
	* At this time, this implementation does not verify that blocks are the same across lines.
	*
	* \todo Implement comparable via line as well as block
	* \warning Do not use for implementing comparable across lines. This may return incorrect values.
	*/
	@Override
	public int compareTo(Block thatBlock){
		return Integer.compare(this.blockNum, thatBlock.blockNum);
	}
}
