package TrainModel;
import TrackModel.*;

public class Antenna {
	
	GPS trainLoc;
	Double currVelocity;
	Double suggSpeed;
	GPS trainAuthority;
	Double SBD;
	Double EBD;
	/**
     * Constructor to create a new Antenna object for current train.
     */
	public Antenna() {
		suggSpeed = null;
		trainAuthority = null;
	}

	/**
     * Mutator to set current GPS location
     * @param a GPS argument is passed to set the new  location of the train
     */
	public void setGPS(GPS newLoc) {
		trainLoc = newLoc;
	}
	
	/**
     * Accessor to get current GPS of train
     * @return a GPS argument is returned to set the new block location of the train
     */
	public GPS getGPS() {
		return trainLoc;
	}
	
	/**
     * Mutator to set current velocity of train
     * @param a Double argument is passed to set the velocity
     */
	public void setCurrVelocity(Double newV) {
		currVelocity = newV;
	}
	
	/**
     * Accessor to get current velocity of train
     * @return a Double argument is passed to get the velocity
     */
	public Double getCurrVelocity() {
		return currVelocity;
	}
	
	/**
     * Mutator to set suggested speed of train
     * @param a Double argument is passed to set suggested speed of train
     */
	public void setSuggestedSpeed(Double newSS) {
		suggSpeed = newSS;
	}
	
	/**
     * Accessor to get suggested speed of train
     * @return a Double argument is passed to get the suggested speed of train
     */
	public Double getSuggestedSpeed() {
		return suggSpeed;
	}
	
	/**
     * Mutator to set Authority
     * @param a Double argument is passed to set the Authority
     */
	public void setCurrAuthority(GPS newA) {
		trainAuthority = newA;
	}
	
	/**
     * Accessor to get current Authority of train
     * @return a Double argument is passed to get the Authority
     */
	public GPS getCurrAuthority() {
		return trainAuthority;
	}
	
	/**
     * Mutator to set safe braking distance of train based on SB
     * @param a Double argument is passed to set safe braking distance using SB
     */
	public void setSafeBrakingDistSB(Double newSBD) {
		SBD = newSBD;
	}
	
	/**
     * Accessor to get safe braking distance of train based on SB
     * @return a Double argument is passed to set safe braking distance using SB
     */
	public Double getSafeBrakingDistSB() {
		return SBD;
	}
	
	/**
     * Mutator to set get safe braking distance of train based on EB
     * @param a Double argument is passed to get safe braking distance of train based on EB
     */
	public void setSafeBrakingDistEB(Double newEBD) {
		EBD = newEBD;
	}
	
	/**
     * Accessor to get safe braking distance of train based on EB
     * @return a Double argument is passed to get safe braking distance of train based on EB
     */
	public Double getSafeBrakingDistEB() {
		return EBD;
	}
	
	
	
}