package TrackModel;

/**
* Station class. Is a station that holds the blocks that host (have platforms to) the station, a name
* a temperature, heaters, and passengers
*/
import java.util.ArrayList;
import java.util.Random;

public class Station implements Comparable<Station>{
	public String stationName;
	public Integer stationTemp;
	public Boolean trackHeatersOn;
	public Integer passengersWaiting;
	public ArrayList<Block> hostBlocks; 
	public TrackModel superTrackModel;
	private Random rand;
	private Integer numLoaded;

	public Station(String stationName, ArrayList<Block> hostBlocks){
		this.hostBlocks = hostBlocks;
		this.stationName = stationName;
		this.stationTemp = new Integer(20);
		this.trackHeatersOn = false;
		this.passengersWaiting = new Integer(0);
	}

	/**
	* Loads passengers from the waiting area to a train
	* @param maxPassengers maximum passengers to be added to a train
	* @return return value for a number of passengers
	*/
	public Integer loadPassengers(Integer maxPassengers){
		if (maxPassengers > this.passengersWaiting){
			numLoaded = this.rand.nextInt(this.passengersWaiting);
		}
		else{
			numLoaded = this.rand.nextInt(maxPassengers);
		}

		this.passengersWaiting = this.passengersWaiting - numLoaded;
		return numLoaded;
	}

	/**
	* Adds departing passengers to the waiting area
	* @param numPassengers number of passengers to add to the waiting area
	*/
	public void addDepartingPassengers(Integer numPassengers){
		this.passengersWaiting = this.passengersWaiting + numPassengers;
	}

	/**
	* Implements comparable interface for a station based upon the name of a station
	* @param thatStation station to be compared to
	*/
	@Override
	public int compareTo(Station thatStation){
		if (thatStation.stationName.equals(stationName)){
			return 1;
		}
		else{
			return 0;
		}
	}
}