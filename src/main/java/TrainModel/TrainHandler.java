package TrainModel;

import java.util.ArrayList;

import TrackModel.*;

// class designed to handle communcation between train model and other interfaces
public class TrainHandler {

	int trainCount = 0;
	Integer trainIDAssign = 1;
	ArrayList<Train> trains;
	ArrayList<String> trainIDs;
	TrackModel globalTrack;
	Block yardBlockRed, yardBlockGreen;

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
	//this method will generate a new train by passing a trainID of -1
	public int setSpeedAndAuthority(Integer trainID, Double Speed, Block goToBlock, Block startBlock)
	{
		Train currT;
		if(trainID == -1)
		{
			//train is a new train
			currT = new Train(trainIDAssign, globalTrack);
			trainID = trainIDAssign;
			trainCount ++;
			trainIDAssign++;
			currT.setSpeed(Speed);
			currT.setAuthority(goToBlock);
			currT.setCurrBlock(startBlock);
		}else{
			currT = findTrain(trainID);
		}
		trains.add(currT);
		currT.setSpeed(Speed);
		currT.setAuthority(goToBlock);
		return trainID;
	}

	//method to return list of all train IDS
	//return as strings (ArrayList)


	//method to search and return train object based on train ID
	public Train findTrain(Integer id)
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
		//pull both yards to see if a new train has been initialized.

		//check red line first
		Double suggestedSpeed = yardBlockRed.getSuggestedSpeed();


		yardBlockRed.setSuggestedSpeed(null);
		Block authorityBlock = yardBlockRed.getAuthority();
		yardBlockRed.setAuthority(null);

		if (suggestedSpeed > 0.0){
			//suggested speed is greater than 0
			 if (authorityBlock != null && (authorityBlock.compareTo(yardBlockRed) != 1)){
				 //if authority is not null and authority is not the yard (returning train)
				 //then this means a new train is being initialized.
				 Integer ID = setSpeedAndAuthority(-1,suggestedSpeed,authorityBlock,yardBlockRed);
				 yardBlockRed.setTrainId(ID);
				 yardBlockRed.setOccupied(true);
			 }
		}


		//repeat for green line
		suggestedSpeed = yardBlockGreen.getSuggestedSpeed();

		yardBlockGreen.setSuggestedSpeed(null);
		authorityBlock = yardBlockGreen.getAuthority();

		yardBlockGreen.setAuthority(null);

		if (suggestedSpeed > 0.0){
			//suggested speed is greater than 0
			 if (authorityBlock != null && (authorityBlock.compareTo(yardBlockGreen) != 1)){
				 //if authority is not null and authority is not the yard (returning train)
				 //then this means a new train is being initialized.
				 Integer ID = setSpeedAndAuthority(-1,suggestedSpeed,authorityBlock,yardBlockGreen);
				 yardBlockGreen.setTrainId(ID);
				 yardBlockGreen.setOccupied(true);
			 }
		}
	}

	//method to search and return yard block
	public void getYard()
	{
		//gets the exit yard blocks for both red and green line. each will be pulled to determine if there is a train ready to be added to the track.
		yardBlockRed = globalTrack.viewStationMap().get("Red").get("YARD").get(0);
		//yardBlockGreen = globalTrack.viewStationMap().get("Green").get("YARD").get(1);
	}




}
