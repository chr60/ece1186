import Loggy.Loggy;
import TrackModel.TrackModel;
import WaysideController.*;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.*;

import java.util.ArrayList;

public class LogLauncher{
	public static final String[] fNames = {"resources/redline.csv"};

	public static void main(String[] args) throws Exception{

		//We can move these into functions for cleanliness.
		TrackModel globalTrack = new TrackModel("global");
		globalTrack.readCSV(fNames);

		TrackModel secondTrack = new TrackModel("second");
		secondTrack.readCSV(fNames);

		ArrayList<TrackModel> trackModels = new ArrayList<TrackModel>();
		trackModels.add(globalTrack);
		trackModels.add(secondTrack);

		Loggy logger = new Loggy(trackModels);

		System.out.println(logger.varScopes);
		logger.save("log.txt");
		logger.loadModel("log.txt");

	}
}