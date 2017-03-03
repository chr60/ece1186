package TrackModel;

/**
 * Class for the block object. Contains attributes of a block associated with a train track block
 */

public class Block{
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

	public Block(Boolean occupied, Boolean isUnderground, Double blockLen, Double blockGrade, Double elevation, Double speedLimit,
				String stationName){
		this.blockLen = blockLen;
		this.blockGrade = blockGrade;
		this.blockElevation = elevation;
		this.speedLimit = speedLimit;
		this.stationName = stationName;
		this.isUnderground = isUnderground;

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
}
