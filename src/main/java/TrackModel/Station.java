package TrackModel;

/**
* Station class. Is a station.
*/
import java.util.ArrayList;
import java.util.Random;

public class Station implements Comparable<Station>{
	public String stationName;
	public Integer stationTemp;
	public Boolean trackHeatersOn;
	public Integer passengersWaiting;
	public ArrayList<Block> hostBlocks; 
	private Random rand;

	public Station(String stationName, ArrayList<Block> hostBlocks){
		this.hostBlocks = hostBlocks;
		this.stationName = stationName;
		this.stationTemp = new Integer(20);
		this.trackHeatersOn = false;
		this.passengersWaiting = new Integer(0);
	}

	public Integer loadPassengers(Integer maxPassengers){
		Integer random = this.rand.nextInt(this.passengersWaiting);
		return random;
	}

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