import TrackModel.TrackModel;
import WaysideController.*;

public class Launcher{
	public static final String[] fNames = {"resources/redline.csv"};

	public static void main(String[] args){

		//We can move these into functions for cleanliness.
		TrackModel globalTrack = new TrackModel();
		globalTrack.readCSV(fNames);
		
	}
}