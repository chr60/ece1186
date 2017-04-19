package TrainModel;
import TrackModel.*;

import java.util.ArrayList;

import TrackModel.TrackModel;

public class Launch {
	static TrainModeUI gui = new TrainModeUI();
	static TrackModel gTrack;
	static TrainHandler trainH;
	public static void main(String [] args){


		gTrack = generateTrack();
		trainH = new TrainHandler(gTrack);
		Block yardBlockRed = gTrack.viewStationMap().get("Red").get("YARD").get(0);
		
		GPS newA = new GPS();
		newA.setCurrBlock(yardBlockRed.nextBlockForward());
		newA.setDistIntoBlock(null);
		trainH.setSpeedAndAuthority(-1,50.0,newA,yardBlockRed);
		ArrayList<Train> trains = trainH.getTrains();
		gui.frmTrainModel.setVisible(true);
		gui.setTrainArray(trains);

	}

	public void powerCommandToTrain(Double pow, Train currT)
	{
		currT.updateTemp();
		currT.powerCommand(pow);
		for(int i =0; i< 100;i++ )
		{
			currT.powerCommand(pow);
			gui.updateGUI(currT);
		}
	}

	  /**
     * Generates a "Dummy Track" and returns it.
     * @return Dummy Track
     * @bug REDLINE ONLY CURRENTLY
     */
    public static TrackModel generateTrack(){
      String[] fNames = {"resources/redline.csv"};
      String[] fNamesOverride = {"resources/redlineLink.csv"};
      TrackModel globalTrack = new TrackModel("GlobalTrack");
  		globalTrack.readCSV(fNames, fNamesOverride);
      return globalTrack;
    }
}
