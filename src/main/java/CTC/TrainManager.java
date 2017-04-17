package CTC;

import TrackModel.*;
import java.util.*;

public class TrainManager {

	private String line;
  private TrackModel dummyTrack;
  private Block yardBlock;
	private ArrayList<DummyTrain> trainList;
	private ArrayList<Block> occupancyList;
	private ArrayList<Block> previousOccList;

	public TrainManager(String lineName, TrackModel dummyTrack){
		line = lineName;
		trainList = new ArrayList<DummyTrain>();
		occupancyList = new ArrayList<Block>();
		previousOccList = new ArrayList<Block>();
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

  public int size(){
    return trainList.size();
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

	public ArrayList<Block> getPreviousOccList(){
		return previousOccList;
	}

	public void setPreviousOccList(ArrayList<Block> prevList){
		this.previousOccList = prevList;
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


	public void updateTrainPosition(ArrayList<Block> occList){
    for(int i=0; i<trainList.size(); i++){
      Block posTrainI = trainList.get(i).getPosition();
      for(int k=0; k<occList.size(); k++){
        Block newOccBlockID = occList.get(i);
        if(posTrainI.blockNum() != newOccBlockID.blockNum()){
          trainList.get(i).setPosition(newOccBlockID);
        }
      }
    }
	}

	public void updateTrainId(Integer updatedID){
		for(int i=0; i<trainList.size(); i++){
			DummyTrain lookAtTrain = trainList.get(i);
			if(lookAtTrain.getID() == -1){
				lookAtTrain.setID(updatedID);
			}
		}
	}


}
