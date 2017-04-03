package CTC;

import TrackModel.*;
import java.util.*;

public class DummyTrain {

	private Integer id;
	private Block position;
	// path is a list of blocks that have the speed and authority that we want to set on train
	private ArrayList<Block> path;

	public DummyTrain(){
		this.setID(id);
		this.setPosition(position);
		this.setPath(path);
	}

	public DummyTrain(Block pos, ArrayList<Block> p){
		id = -1;
		position = pos;
		path = p;
	}

	public Integer getID(){
		return id;
	}

	public void setID(Integer id){
		this.id = id;
	}

	public Block getPosition(){
    return position;
	}


	public void setPosition(Block position){
		this.position = position;
	}

	public ArrayList<Block> getPath(){
		return path;
	}

	public void setPath(ArrayList<Block> path){
		this.path = path;
		// sendToWS(path);
	}
}
