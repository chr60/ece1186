package CTC;

import TrackModel.*;
import java.util.*;

public class TrainManager {

	private String line;
  private TrackModel dummyTrack;
  private Block yardBlock;
	private ArrayList<DummyTrain> trainList;
	private ArrayList<Block> occupancyList;

	public TrainManager(String lineName, TrackModel dummyTrack){
		line = lineName;
		trainList = new ArrayList<DummyTrain>();
		occupancyList = new ArrayList<Block>();
    this.dummyTrack = dummyTrack;
    setYardBlock();
	}

  /**
   * Adds a train
   * @param train Train
   */
  public void addTrain(DummyTrain train){
    trainList.add(train);
  }

  private void setYardBlock(){

    ArrayList<Block> yards = dummyTrack.viewStationMap().get(line).get("YARD");
    int max = 0;

    for(int i = 1; i < yards.size(); i++){
      if(yards.get(i).compareTo(yards.get(max)) > 0) max = i;
    }

    yardBlock = yards.get(max);
  }

  public Block getYardBlock(){
    return yardBlock;
  }

	public String getLine(){
		return line;
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
