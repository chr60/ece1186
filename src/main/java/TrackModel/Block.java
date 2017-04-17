package TrackModel;

/**
 * Class for the block object. Contains attributes of a block associated with a train track block.
 * Fundamental building block of the track module.
 * @author Michael
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Comparable<Block>, java.io.Serializable {
  Double blockLen;
  Boolean occupied;
  Double speedLimit;
  Double blockGrade;
  Double blockElevation;
  Boolean signals;
  Boolean brokenRail;
  Boolean circuitFailure;
  Boolean powerFailure;
  String stationName;
  Boolean isUnderground;
  String arrowDirection;
  Integer blockNum;
  Boolean switchState;
  String switchBlock;
  Boolean hasSwitch;
  String blockLine;
  String blockSection;
  Block nextBlockForward;
  Block nextBlockBackward;
  Boolean switchNextBlockForward;
  Boolean switchNextBlockBackward;
  Block rootBlock;
  Boolean lightState;
  TrackModel superTrackModel;
  Boolean maintenence;
  Boolean hasLight;
  Double suggestedSpeed;
  Block authority;
  Integer trainId;
  Boolean blockClosed;

  /**
  * Constructor for the block class.
  */
  public Block(TrackModel track, Boolean occupied, Boolean isUnderground, 
               Double blockLen, Double blockGrade, Double elevation, 
               Double speedLimit, String stationName, String arrowDirection, String blockLine, 
               String blockSection, Integer blockNum, Boolean hasSwitch, String switchBlock) {

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
    this.hasLight = hasSwitch;
    this.blockLine = blockLine;
    this.blockSection = blockSection;

    this.suggestedSpeed = 0.0;
    this.lightState = true;
    this.occupied = false;
    this.signals = false;
    this.brokenRail = false;
    this.circuitFailure = false;
    this.powerFailure = false;
    this.maintenence = false;
    this.authority = null;
    this.trainId = new Integer(-1);
    this.blockClosed = false;
  }

  /**
   * Returns the switch name (if it exists) on a block.
   * @return the name of the switch on a block
   */
  public String getSwitchBlock() {
    return this.switchBlock;
  }

  /**
  * Returns the broken status of a given block.
  * @return the boolean state of the broken rail
  */
  public Boolean getBroken() {
    return this.brokenRail;
  }

  /**
  * Sets a rail to the broken state.
  * @param the new state of broken of the rail
  */
  public void setBroken(Boolean brokenState) {
    this.brokenRail = brokenState;
  }

  /**
  * Allows for setting a block to be closed
  * @param the new state of the blocks closed status
  */
  public void setClosed(Boolean newState) {
    this.blockClosed = newState;
  }

  /**
  * Sets the suggested speed to a new speed.
  * @param new speed to set
  */
  public void setSuggestedSpeed(Double newSpeed) {
    this.suggestedSpeed = newSpeed;
  }

  /**
  * Get the current suggested speed at this block.
  * @return the current suggested speed of the block
  */
  public Double getSuggestedSpeed() {
    return this.suggestedSpeed;
  }

  /**
  * Gets the authority of this block.
  * @return the block authority of the current block
  */
  public Block getAuthority() {
    return this.authority;
  }

  /**
  * Sets the new authority of a block.
  * @param the new authority at this block
  */
  public void setAuthority(Block newAuthority) {
    this.authority = newAuthority;
  }

  /**
  * Gets the train ID that occupies the block.
  * @param the new trainId
  */
  public Integer getTrainId() {
    return this.trainId;
  }

  /**
  * Sets the occupied train id.
  * @param thew new id to be assigned to the block
  */
  public void setTrainId(Integer newId) {
    this.trainId = newId;
  }
  /**
  * Setter for the Wayside Controller module to set a light state based upon an Integer
  * Maps 1 to true, 0 to false based upon the track internal convention.
  * @param setInt integer for the switch state to be set to
  * @todo Add strict value assertions that setInt is either 0 or 1
  */
  public Boolean setLightState(Integer setInt) {
    //condition check for having a switch
    assert (this.lightState != null);

    if (setInt.equals(1)) {
      this.lightState = true;
    } else if (setInt.equals(0)) {
      this.lightState = false;
    }
    return this.lightState;
  }

  /**
  * Returns the length of a block object.
  * @return the length of a block
  */
  public Double getLen() {
    return this.blockLen;
  }

  /**
  * Returns if a track block is open or not based upon the state of the breakages of a block.
  * @return the boolean state of the openness of the block
  */
  public Boolean getOpen() {
    if (this.brokenRail || this.powerFailure || this.occupied || this.maintenence) {      
      return false;
    } else {
      return true;
    }
  }

  /**
  * Returns the underground status of a block.
  * @return the boolean state of the underground state of the block
  */
  public Boolean isUnderground() {
    return this.isUnderground;
  }

  /**
  * Returns the occupied state of a given block object.
  * @return the occupied state of a block
  */
  public Boolean getOccupied() {
    return this.occupied;
  }

  /**
  * Sets the occupied state of the given block.
  * @param the new occupied state of the block
  */
  public void setOccupied(Boolean occupiedState) {
    this.occupied = occupiedState;
  }

  /**
  * Returns the speed limit of a given block.
  * @return the speed limit of a block
  */
  public Double getSpeedLimit() {
    return this.speedLimit;
  }

  /**
  * Returns the cumulative elevation of a given blcok.
  * @return the elevation of the block
  */
  public Double getElevation() {
    return this.blockElevation;
  }

  /**
  * Returns the grade of a given block.
  * @return the grade of a block.
  */
  public Double getGrade() {
    return this.blockGrade;
  }

  /**
  * Returns the status of a circuit failure at a given block.
  * @return the state of a circuit failure on that block of the track
  */
  public Boolean getCircuitFailure() {
    return this.circuitFailure;
  }

  /**
  * Returns the state of a track power failures.
  * @return the failure state of the power on the track
  */
  public Boolean getPowerFailure() {
    return this.powerFailure;
  }

  /**
  * Returns the underground state of the block.
  * @return boolean representing the underground state of the block.
  */
  public Boolean getUnderground() {
    return this.isUnderground;
  }

  /**
  * Returns the name of the associated state of the block.
  * @return the associtaed stateion name with this block.
  */
  public String getStationName() {
    return this.stationName;
  }

  /**
  * Sets the nextBlockForward to this block in the case travel in that direction is not valid.
  */
  public void setNextBlockForward() {
    this.nextBlockForward = this;
  }

  /**
  * Sets the nextBlockBackward to this block in the case travel in that direction is not valid.
  */
  public void setNextBlockBackward() {
    this.nextBlockBackward = this;
  }

  /**
  * Sets the next block forward given a block. 
  */
  public void setNextBlockForward(Block setBlock) {
    this.nextBlockForward = setBlock;
  }

  /**
  * Sets the next block in the "reverse" direction. Does not handle switch conditions.
  * @param setBlock the block to set the default backwards block to
  */
  public void setNextBlockBackward(Block setBlock) {
    this.nextBlockBackward = setBlock;
  }

  /** 
  * Setter for the next block forward in the condition for a switch is present.
  * By default, initializes the switch to the lower block (as determined by blockNum).
  * to be destination when the this.switchState = true.
  * @param the low block for forward
  * @param the high block for forward  
  */
  public void setNextBlockForward(Block lowBlock, Block highBlock) {
    this.nextBlockForward = lowBlock;
    this.switchNextBlockForward = true;
    this.switchNextBlockBackward = false;
    this.switchState = true;
  }

  /**
  * Handles the switch conditions for the condition where the blocknums ofleaf0<\leaf1<\root
  * @param the lower leaf block (leaf0)
  * @param the higher leaf block (leaf1)
  */
  public void setNextBlockBackward(Block lowBlock, Block highBlock) {
    this.nextBlockBackward = lowBlock;
    this.switchNextBlockForward = false;
    this.switchNextBlockBackward = true;
    this.switchState = true;
  }

  /**
  * Returns the number of a given block.
  */
  public Integer blockNum() {
    return this.blockNum;
  }

  /**
  * Returns the section a block is on.
  */
  public String getBlockSection() {
    return this.blockSection;
  }

  /**
  * Returns the line a block is on.
  */
  public String getBlockLine() {
    return this.blockLine;
  }

  /**
  * Sets the root block in the "reverse" direction to deal with switch conditions.
  * @param rootBlock the root block backwards of a switch block
  */
  public void setRootBlock(Block rootBlock) {
    this.rootBlock = rootBlock;
    this.hasSwitch = true;
    if (this.rootBlock.blockNum() > this.blockNum()) {
      this.switchNextBlockForward = true;
      this.switchNextBlockBackward = false;
    } else {
      this.switchNextBlockBackward = true;
      this.switchNextBlockForward = false;
    }
  }

  public void setRootBlock(Block rootBlock, Boolean switchNextBlockForwardOverride, Boolean switchNextBlockBackwardOverride) {
    this.rootBlock = rootBlock;
    this.hasSwitch = true;
    this.switchNextBlockForward = switchNextBlockForwardOverride;
    this.switchNextBlockBackward = switchNextBlockBackwardOverride;
  }

  /** 
  * Gets the next block forward along based upon the current switch state. If there is no
  * switch, it returns the next block
  */
  public Block nextBlockForward() {
    if (this.hasSwitch == true && this.switchNextBlockForward == true) {
      return this.superTrackModel.switchMap.get(this.switchBlock).nextBlock(this);
    } 
    else {
      return this.nextBlockForward;
    }
  }

  /**
  * Returns the next block backward given a current switch state.
  * @return the nextBlock in the backward direction given a blocks switch state.
  */
  public Block nextBlockBackward() {
    if (this.hasSwitch == true && this.switchNextBlockBackward == true) {
        return this.superTrackModel.switchMap.get(this.switchBlock).nextBlock(this);
    }
     else {
      return this.nextBlockBackward;
    }
  }

  /**
  * Setter for the Wayside Controller module to set a switch state based upon an Integer
  * Maps 1 to true, 0 to false based upon the track internal convention.
  * @param setInt integer for the switch state to be set to
  * @todo Add strict value assertions that setInt is either 0 or 1
  */
  @Deprecated public Boolean setSwitchState(Integer setInt) {
    //System.out.println("Currently using setSwitchState on a block object. Please use it on the associated TrackModel switch block instead.");
    //condition check for having a switch
    assert (this.switchState != null);

    if (setInt.equals(1)) {
      this.superTrackModel.switchMap.get(this.switchBlock).setSwitchState(true);
      this.switchState = this.superTrackModel.switchMap.get(this.switchBlock).switchState();
    } else if (setInt.equals(0)) {
      this.superTrackModel.switchMap.get(this.switchBlock).setSwitchState(false);
      this.switchState = this.superTrackModel.switchMap.get(this.switchBlock).switchState();
    }

    return this.superTrackModel.switchMap.get(this.switchBlock).switchState();
  }

  /**
  * Return the status of the associated switch, if one exists.
  */
  public Boolean viewSwitchState() {
    assert(this.superTrackModel.switchMap.containsKey(this.switchBlock));
    return this.superTrackModel.switchMap.get(this.switchBlock).switchState();
  }

  /**
  * Returns the switch state of a given block.
  * @param the switch state
  */
  public Boolean hasSwitch() {
    return this.hasSwitch;
  }

  /**
  * Returns the associated station of the block.
  * @return the Station object of the associated block
  */
  public Station getAssociatedStation() {
    return this.superTrackModel.blockStationMap.get(this);
  }

  /**
  * Method for adding a beacon to a given track.
  */
  public void addBeacon(String message, Double distance) {
    Beacon b = new Beacon(this.superTrackModel, this, message, distance);
    this.superTrackModel.blockBeaconMap.put(this,b);
  }

  /**
  * Returns the associated Switch object of the block.
  */
  public Switch getAssociatedSwitch() {
    return this.superTrackModel.switchMap.get(this);
  }

  /**
  * Returns the crossing associated with this block.
  * @return the crossing associated with the block
  */
  public Crossing getAssociatedCrossing() {
    return this.superTrackModel.crossingMap.get(this);
  }

  /** 
  * Implements the comparable interface for blocks via the associalted blockNum of a given block.
  * At this time, this implementation does not verify that blocks are the same across lines.
  * @return 1 if equivalent, 0 if false
  */
  @Override
  public int compareTo(Block thatBlock) {
    return Integer.compare(this.blockNum, thatBlock.blockNum) 
                          + this.blockLine.compareTo(thatBlock.blockLine);
  }
}
