package TrainModel;

public class Launch {
	static TrainModeUI gui = new TrainModeUI();
	
	public static void main(String [] args){
		Train [] trains = {new Train(123), null, null};
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
		TrainHandler trainH = new TrainHandler();
	}
}
