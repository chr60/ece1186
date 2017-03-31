package TrackModel;

/**
* Crossing class. Is a station that holds the blocks that host (have platforms to) the station, a name
* a temperature, heaters, and passengers
*/
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Crossing implements java.io.Serializable{
	public Block hostBlock;
	public TrackModel superTrackModel;

	public Crossing(TrackModel superTrackModel, Block hostBlock){
		this.superTrackModel = superTrackModel;
		this.hostBlock = hostBlock;
	}
}