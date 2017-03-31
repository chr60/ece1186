import Loggy.Loggy;
import TrackModel.TrackModel;

public class Viewer{
	public static void main(String[] args) throws Exception{
		Loggy logger = new Loggy();
		logger.loadModel("log.txt");
		System.out.println(logger.varScopes);
		System.out.println(logger.trackModels);
		System.out.println(logger.timeSeriesTrackModels);
	}
}