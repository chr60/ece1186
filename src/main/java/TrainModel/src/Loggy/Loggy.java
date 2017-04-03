package Loggy;

import TrackModel.*;
import TrainControllerComps.*;
import TrainModel.*;
import TrackModel.*;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Loggy implements java.io.Serializable{
	
	public ArrayList<String> varScopes = new ArrayList<String>();
	public ArrayList<TrackModel> trackModels = new ArrayList<TrackModel>();
	public ArrayList<ArrayList<TrackModel>> timeSeriesTrackModels = new ArrayList<ArrayList<TrackModel>>();
	
	public Loggy(ArrayList<TrackModel> trackModels){

		for(TrackModel t : trackModels){
			varScopes.add(t.trackScope);
		}
		this.trackModels = trackModels;
		System.out.println(varScopes.size());
	}

	public Loggy() throws Exception{
	}

	public void indexTimeStep(){
		this.timeSeriesTrackModels.add(trackModels);
	}

	public void save(String fName) throws Exception{
		try{
			FileOutputStream fOut = new FileOutputStream(fName);
			ObjectOutputStream outStream = new ObjectOutputStream(fOut);

			System.out.println(this.trackModels);
			outStream.writeObject(this);

			outStream.flush();
			outStream.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void loadModel(String fName) throws Exception{
		Loggy read = new Loggy();
		try{
			FileInputStream fIn = new FileInputStream(fName);
			ObjectInputStream oIs = new ObjectInputStream(fIn);
			
			read = (Loggy) oIs.readObject();
			this.varScopes = read.varScopes;
			this.trackModels = read.trackModels;
			this.timeSeriesTrackModels = read.timeSeriesTrackModels;
			
			oIs.close();
			fIn.close();
		}catch(IOException e){
			e.printStackTrace();
			return;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return;
		}
	}

	public TrackModel lookupTrack(String varScope){
		for (TrackModel t : this.trackModels){
			if (t.trackScope.equals(varScope)){
				return t;
			}
		}
		return trackModels.get(0);
	}
}