package TrainModel;

import java.util.ArrayList;

import TrackModel.*;
import TrainControllerComps.*;

// class designed to handle communcation between train model and other interfaces
public class TrainHandler {

	int trainCount = 0;
	Integer trainIDAssign = 1;
	ArrayList<Train> trains;
	ArrayList<String> trainIDs;
	TrackModel globalTrack;
	Block yardBlockRed, yardBlockGreen;
        
        int clockSpeed; 
        
        public ArrayList<TrainController> openTrainControllers; 

	public TrainHandler(TrackModel gTrack)
	{
		trains = new ArrayList<Train>();
		trainIDs = new ArrayList<String>();
		globalTrack = gTrack;
                
                openTrainControllers = new ArrayList<TrainController>(); 
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
        
        /**
         * Sets the speed at which the system should run.
         * 
         * @param speed the speed in ms. 
         */
        public void setClockSpeed(int speed){
        
            this.clockSpeed = speed; 
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
			trainCount++;
			trainIDAssign++;
			currT.setSpeed(Speed);
			currT.setAuthority(goToBlock);
			currT.setCurrBlock(startBlock);
                        trains.add(currT);
			// open a train controller
			TrainController tc = new TrainController(currT, "Automatic", "Normal"); 
                              
                        if (this.clockSpeed == 100){ tc.playFast(); }
                        else if(this.clockSpeed == 1000){ tc.playNormal();}
                        
                        this.openTrainControllers.add(tc);
                        tc.setTrainHandler(this); 
                        
                        for (TrainController trainCont : this.openTrainControllers){
                            Integer trainNum = trainCont.getTrain().getID();  
                            trainCont.setTrainListComboBox();
                            trainCont.setSelectedItem(trainNum);
                        }
                        
			tc.setVisible(true);
			tc.setDefaultCloseOperation(2);
                        //tc.setSelectedItem(currT.getID());
		}else{
			currT = findTrain(trainID);
                        trains.add(currT);
		}
		
		currT.setSpeed(Speed);
		currT.setAuthority(goToBlock);
                
		return trainID;
	}
        
        
        public ArrayList<TrainController> getOpenTrainControllers(){
            
            return this.openTrainControllers;
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
	
	//method to search and return train object based on train ID
	public Antenna getTrainAntenna(Integer id)
	{
		Train currT = findTrain(id);
		if (currT != null)
		{
			return currT.getAntenna();
		}
		return null;
	}

	//method to pull
	public void pollYard()
	{
		//pull both yards to see if a new train has been initialized.

		//check red line first
		Double suggestedSpeed = yardBlockRed.getSuggestedSpeed();

		yardBlockRed.setSuggestedSpeed(null);
		Block authorityBlock = yardBlockRed.getAuthority();
		yardBlockRed.setAuthority(null);
		if(yardBlockRed.getOccupied() == false){
			if (suggestedSpeed != null){
			//suggested speed is greater than 0
			 if (authorityBlock != null && (authorityBlock.compareTo(yardBlockRed) != 1)){
				 //if authority is not null and authority is not the yard (returning train)
				 //then this means a new train is being initialized.
				 Integer ID = setSpeedAndAuthority(-1,suggestedSpeed,authorityBlock,yardBlockRed);
				 yardBlockRed.setTrainId(ID);

				 yardBlockRed.setOccupied(true);
                                 
			 }
		}
		}
		

		/*

		//repeat for green line
		suggestedSpeed = yardBlockGreen.getSuggestedSpeed();

		yardBlockGreen.setSuggestedSpeed(null);
		authorityBlock = yardBlockGreen.getAuthority();

		yardBlockGreen.setAuthority(null);

		if (suggestedSpeed != null){
			//suggested speed is greater than 0
			 if (authorityBlock != null && (authorityBlock.compareTo(yardBlockGreen) != 1)){
				 //if authority is not null and authority is not the yard (returning train)
				 //then this means a new train is being initialized.
				 Integer ID = setSpeedAndAuthority(-1,suggestedSpeed,authorityBlock,yardBlockGreen);
				 yardBlockGreen.setTrainId(ID);

				 yardBlockGreen.setOccupied(true);
			 }
		}
		*/
	}

	//method to search and return yard block
	public void getYard()
	{
		//gets the exit yard blocks for both red and green line. each will be pulled to determine if there is a train ready to be added to the track.
		yardBlockRed = globalTrack.viewStationMap().get("Red").get("YARD").get(0);
                yardBlockRed = globalTrack.lateralLookup(yardBlockRed);
                assert(yardBlockRed.nextBlockForward() != null);
		//yardBlockGreen = globalTrack.viewStationMap().get("Green").get("YARD").get(1);
	}

	public int getNumTrains(){
		return trains.size();
	}




}
