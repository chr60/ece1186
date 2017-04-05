package MBO;

import java.util.*;
import CommonUIElements.*;
import TrackModel.*;
import TrainModel.*;
import CTC.*;

public class MovingBlockOverlay{

	public String mode;
	private TrackModel dummyTrack;
	private ArrayList<TrainManager> managers;
	private TrainHandler handler;
	private List<Train> trainList;
	private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	MBO_gui gui;

	private String [] redStationNames = {"YARD", "SHADYSIDE", "HERRON AVE", "SWISSVILLE", "PENN STATION", 
									"STEEL PLAZA", "FIRST AVE", "STATION SQUARE", "SOUTH HILLS JUNCTION"};
	private int [] redStationTimes = {162, 78, 30, 48, 66, 66, 42, 78};
	private int redLineLoopTime = 2040; //in seconds, includies dwell
	private CTCgui ctc;

	public MovingBlockOverlay(TrackModel dummyTrack, ArrayList<TrainManager> managers, TrainHandler handler, CTCgui ctc){
		this.dummyTrack = dummyTrack;
		this.managers = managers;
		this.handler = handler;
		this.ctc = ctc;
		//initGUI();
		//this.redStationNames = dummyTrack.viewStationMap().keySet().toArray(new String[0]);
		createSchedules();
	}

	/*public static void main(String[] args){
		try {
			MBO_gui gui = new MBO_gui();
			//gui.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public void initGUI(){
		try {
			gui = new MBO_gui(this);
			//gui = new MBO_gui();
			gui.frame.setVisible(true);
        	gui.frame.setDefaultCloseOperation(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSchedules(){
		/*
		String [] stationNames;
		int [] stationTimes;

		for(String linekey: dummyTrack.trackList.keySet()){
			System.out.println(linekey);
			//schedules.add(new Schedule(linekey, stationNames, stationTimes, lineLoopTime));
			System.out.println(dummyTrack.viewStationMap().keySet());
		}
		*/

		schedules.add(new Schedule(dummyTrack, managers.get(0), hardCodeStops(), "Red",
					 redStationNames, redStationTimes, redLineLoopTime, this.ctc));
		//schedules.add(new Schedule(linekey, stationNames, stationTimes, lineLoopTime));
	}

	public ArrayList<Schedule> getSched(){
		return schedules;
	}

	/**
	 * Given an ID, returns the corresponding train
	 * @param  id ID of a train
	 * @return    [description]
	 */
	private Train getTrain(int id){
		return null;
	}

	private Block[] hardCodeStops(){
		ArrayList<Block> blocks = new ArrayList<Block>();
		String line = "Red";

		blocks.add(dummyTrack.getBlock(line, "C", new Integer(7)));
		blocks.add(dummyTrack.getBlock(line, "F", new Integer(16)));
		blocks.add(dummyTrack.getBlock(line, "G", new Integer(21)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(25)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(35)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(45)));
		blocks.add(dummyTrack.getBlock(line, "I", new Integer(48)));
		blocks.add(dummyTrack.getBlock(line, "L", new Integer(60)));
		blocks.add(dummyTrack.getBlock(line, "I", new Integer(48)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(45)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(35)));
		blocks.add(dummyTrack.getBlock(line, "H", new Integer(25)));
		blocks.add(dummyTrack.getBlock(line, "G", new Integer(21)));
		blocks.add(dummyTrack.getBlock(line, "F", new Integer(16)));

		return blocks.toArray(new Block [0]);
	}

	/**
	 * Gets the actual speed of a train
	 * @param  train Train object
	 * @return double actSpeed
	 */
	private Double getActSpeed(Train train){
		return train.getVelocity();
	}

	/**
	 * Gets the actual authority of a train
	 * @param  train Train object
	 * @return Block actAuth
	 */
	private Block getActAuth(Train train){
		return null;
	}

	/**
	 * Calculates the variance in speed between suggested and actual
	 * @param  id ID of a train
	 * @param  train Train object
	 * @return double diffSpeed
	 */
	private double calcVarSpeed(int id, Train train){
		return 0;
	}

	/**
	 * Displays the variance between suggested and actual authority
	 * @param  train Train object
	 * @return String diffAuthority
	 */
	private String calcVarAuth(Train train){
		return null;
	}

	/**
	 * Updates the variance for all trains for display
	 */
	private void updateVariance(){

	}

	/**
	 * Transmit safe Moving Block speed and authority to a train and CTC
	 * @param id ID of a train
	 */
	private void sendSpeedAuth(Train train){

	}

	/**
	 * Finds the safe stopping distance for a train
	 * @param  train Train object
	 * @return double safeStop
	 */
	private double findStopDist(Train train){
		return 0;
	}

	/**
	 * Finds the time it takes for a train to brake in seconds
	 * @param  train Train object
	 * @return int   time
	 */
	private int timeToBrake(Train train){
		return 0;
	}

	/**
	 * Finds the safe emergency braking distance for a train
	 * @param  train Train object
	 * @return double safeStop
	 */
	private double findEmgStopDist(Train train){
		return 0;
	}

	/**
	 * Finds the time it takes for a train to emergency brake in seconds
	 * @param  train Train object
	 * @return int   time
	 */
	private int timeToEmgBrake(Train train){
		return 0;
	}

	/**
	 * Gets a GPS object for the train's current position
	 * @param  train Train object
	 * @return GPS trainGPS
	 */
	//private GPS getGPS(Train train){
	//	return null;
	//}

	/**
	 * Generates the appropriate schedule for the mode
	 * @param mode Specifies MBO or FB mode
	 */
	private void genSchedule(String mode){

	}

}
