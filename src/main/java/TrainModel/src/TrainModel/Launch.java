package TrainModel;

import java.util.ArrayList;

import TrackModel.TrackModel;

public class Launch {
	static TrainModeUI gui = new TrainModeUI();
	static TrackModel gTrack;
	TrainHandler trainH = new TrainHandler(gTrack);
	public static void main(String [] args){

		
		gTrack = generateTrack();
		ArrayList<Train> trains = new ArrayList<Train>();
		trains.add(new Train(123,gTrack));
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
