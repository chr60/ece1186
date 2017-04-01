package TrackModel;

/**
 * Class for the block object. Contains attributes of a block associated with a train track block.
 * Fundamental building block of the track module.
 * @author Michael
 */
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Comparable<Block>, java.io.Serializable{
  Double blockLen;
  Boolean occupied;
  Double speedLimit;
  Double blockGrade;
  Double blockElevation;
  Boolean signals;
  Boolean brokenRail;
  Boolean circuitFailure;
  Boolean powerFailure;
  Boolean trackHeaters;
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
  Block switchNextBlockForward;
  Block nextBlockBackward;
  Block rootBlock;
  Boolean lightState;
  TrackModel superTrackModel;
  Boolean maintenence;
  Boolean hasLight;
  Double suggestedSpeed;
  Block authority;
  Integer trainId;

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
    this.hasLight = hasSwitch;
    this.blockLine = blockLine;
    this.blockSection = blockSection;

    this.suggestedSpeed = 0.0;
    this.lightState=true;
    this.occupied = false;
    this.trackHeaters = false;
    this.signals = false;
    this.brokenRail = false;
    this.circuitFailure = false;
    this.powerFailure = false;
    this.maintenence = false;
    this.authority = null;
    this.trainId = new Integer(-1);

    }

  /**
  * Returns the broken status of a given block.
  */
  public Boolean getBroken(){
    return this.brokenRail;
  }

  /**
  * Sets the suggested speed to a new speed.
  * @param new speed to set
  */
  public void setSuggestedSpeed(double newSpeed){
    this.suggestedSpeed = suggestedSpeed;
  }

  /**
  * Get the current suggested speed at this block.
  */
  public double getSuggestedSpeed(){
    return this.suggestedSpeed;
  }

  /**
  * Gets the authority of this block.
  */
  public Block getAuthority(){
    return this.authority;
  }

  /**
  * Sets the new authority of a block.
  * @param the new authority at this block
  */
  public void setAuthority(Block newAuthority){
    this.authority = newAuthority;
  }

  /**
  * Gets the train ID that occupies the block.
  * @param the new trainId
  */
  public Integer getTrainId(){
    return this.trainId;
  }

  /**
  * Sets the occupied train id
  * @param thew new id to be assigned to the block
  */
  public void setTrainId(Integer newId){
    this.trainId = newId;
  }
  /**
  * Setter for the Wayside Controller module to set a light state based upon an Integer
  * Maps 1 to true, 0 to false based upon the track internal convention.
  * @param setInt integer for the switch state to be set to
  * @todo Add strict value assertions that setInt is either 0 or 1 
  */
  public Boolean setLightState(Integer setInt){
    //condition check for having a switch
    assert(this.lightState != null);

    if(setInt.equals(1)){
      this.lightState = true;
    }
    else if(setInt.equals(0)){
      this.lightState = false;
    }
    return this.lightState;
  }

  /**
  * Returns the length of a block object
  */
  public Double getLen(){
    return this.blockLen;
  }

  /**
  *   Returns if a track block is open or not based upon the state of the breakages of a block
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
  *   Returns the occupied state of a given block object
  */
  public Boolean getOccupied(){
    return this.occupied;
  }

  /**
  * Sets the occupied state of the given block
  */
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
  *   By default, initializes the switch to the lower block (as determined by blockNum)
  *   to be destination when the this.switchState = true
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
    * @return the Station object of the associated block
    */
    public Station getAssociatedStation(){
        return this.superTrackModel.blockStationMap.get(this);
    }

    public Crossing getAssociatedCrossing(){
        return this.superTrackModel.crossingMap.get(this);
    }
    /** Implements the comparable interface for blocks via the associalted blockNum of a given block.
    * At this time, this implementation does not verify that blocks are the same across lines.
    */
    @Override
    public int compareTo(Block thatBlock){
        return Integer.compare(this.blockNum, thatBlock.blockNum)+ this.blockLine.compareTo(thatBlock.blockLine);
    }
}
