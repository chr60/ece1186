package CTC;

import TrackModel.*;
import java.util.*;

public class TrainManager {

	private ArrayList<DummyTrain> trainList;
	private ArrayList<Block> occupancyList;

	public TrainManager(){
		trainList = new ArrayList<DummyTrain>();
		occupancyList = new ArrayList<Block>();
	}

	public ArrayList<DummyTrain> getTrainList(){
		return trainList;
	}

	public ArrayList<Block> getOccupancyList(){
		return occupancyList;
	}

	public void setOccupancyListInt(ArrayList<Block> occupancyList){
		this.occupancyList = occupancyList;
	}

  public DummyTrain getTrain(Integer id){
    for(int i=0; i<trainList.size(); i++){
      Integer foundID = trainList.get(i).getID();
      if(foundID == id){
        return trainList.get(i);
      }
    }
      return null;

  }

/*
	public ArrayList<DummyTrain> updateTrainPosition(){
    for(int i=0; i<trainList.size(); i++){
      Integer posTrainI = trainList.get(i).getPositionInt();
      for(int k=0; k<occupancyList.size(); k++){
        Integer newOccBlockID = occupancyList.get(i);
        if(posTrainI != newOccBlockID){
          trainList.get(i).setPostion(newOccBlockID);
        }
      }
    }
	}
  */

}
