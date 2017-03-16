package TrackModel;

/**
* Crossing class. Is a station that holds the blocks that host (have platforms to) the station, a name
* a temperature, heaters, and passengers
*/
import java.util.ArrayList;
import java.util.Random;

public class Crossing{
	public Block hostBlock;
	public TrackModel superTrackModel;

	public Crossing(TrackModel superTrackModel, Block hostBlock){
		this.superTrackModel = superTrackModel;
		this.hostBlock = hostBlock;
	}
}