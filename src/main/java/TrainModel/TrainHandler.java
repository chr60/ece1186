package TrainModel;

import java.util.ArrayList;
import java.util.HashMap;

import TrackModel.*;

// class designed to handle communcation between train model and other interfaces
public class TrainHandler {

	int trainCount = 0;
	Integer trainIDAssign = 1;
	ArrayList<Train> trains;
	ArrayList<String> trainIDs;
	TrackModel globalTrack;
	Block yardBlock;
	
	public TrainHandler(TrackModel gTrack)
	{
		trains = new ArrayList<Train>();
		trainIDs = new ArrayList<String>();
		globalTrack = gTrack;
		getYard();
	}
	
	/**
     * Accessor to return all trains in the system
     * @return an ArrayList of trains to the caller.
     */
	public ArrayList<Train> getTrains()
	{
		return trains;
	}
	
	//Communcation to and from CTC 
	public int setSpeedAndAuthority(Integer trainID, Double Speed, Block goToBlock)
	{
		Train currT;
		if(trainID == -1)
		{
			//train is a new train
			currT = new Train(trainIDAssign, globalTrack);
			trainID = trainIDAssign;
			trainCount ++;
			trainIDAssign++;
		}else{
			currT = findTrain(trainID);
		}
		
		currT.setSpeed(Speed);
		currT.setAuthority(goToBlock);
		return trainID;
	}
	
	//method to return list of all train IDS
	//return as strings (ArrayList) 
	

	//method to search and return train object based on train ID
	public Train findTrain(int id)
	{
		for(int i = 0; i < trains.size(); i++)
		{
			if (trains.get(i).getID() == id)
			{
				return trains.get(i);
			}
		}
		return null;
	}
	
	//method to pull 
	public void pullYard()
	{
		//yardBlock.
	}
	
	//method to search and return yard block
	public void getYard()
	{
		yardBlock = globalTrack.viewStationMap().get("YARD").get(0);
	}
}
