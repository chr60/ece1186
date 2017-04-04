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
		trainH.setSpeedAndAuthority(-1,50.0,yardBlockRed.nextBlockForward(),yardBlockRed);
		trainH.setSpeedAndAuthority(-1,50.0,yardBlockRed.nextBlockForward(),yardBlockRed);
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
      TrackModel globalTrack = new TrackModel();
  		globalTrack.readCSV(fNames);
      return globalTrack;
    }
}
