package trainModel;

public class Launch {
	static trainModeUI gui = new trainModeUI();
	
	public static void main(String [] args){
		Train [] trains = {new Train(123), null, null};
		gui.frmTrainModel.setVisible(true);
		gui.setTrainArray(trains);
	}
	
	public void powerLoop(Double pow, Train currT)
	{
		for(int i =0; i< 100;i++ )
		{
			currT.powerCommand(pow);
			gui.updateGUI(currT);
		}
	}
}
