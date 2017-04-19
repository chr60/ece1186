package MBO;

import CTC.CTCgui;
import CTC.TrainManager;
import TrackModel.Block;
import TrackModel.TrackModel;
import TrainModel.Train;
import TrainModel.TrainHandler;

import java.util.ArrayList;
import java.util.List;

public class MovingBlockOverlay{

  public String mode = "FB";
  private TrackModel dummyTrack;
  private ArrayList<TrainManager> managers;
  private TrainHandler handler;
  private List<Train> trainList;
  private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
  MBO_gui gui;

  private String [] redStationNames = {"YARD", "SHADYSIDE", "HERRON AVE", "SWISSVILLE", 
      "PENN STATION", "STEEL PLAZA", "FIRST AVE", "STATION SQUARE", "SOUTH HILLS JUNCTION"};
  private String [] redStationOrder = {"SHADYSIDE", "HERRON AVE", "SWISSVILLE", "PENN STATION", 
      "STEEL PLAZA", "FIRST AVE", "STATION SQUARE", "SOUTH HILLS JUNCTION", "STATION SQUARE", 
      "FIRST AVE", "STEEL PLAZA", "PENN STATION", "SWISSVILLE", "HERRON AVE", "SHADYSIDE"};
  private int [] redStationTimes = {162, 78, 30, 48, 66, 66, 42, 78, 78, 42, 66, 66, 48, 30, 78};
  private int redLineLoopTime = 2040; //in seconds, includies dwell
  private CTCgui ctc;

  public MovingBlockOverlay(TrackModel dummyTrack, ArrayList<TrainManager> managers, 
                            TrainHandler handler, CTCgui ctc) {
    this.dummyTrack = dummyTrack;
    this.managers = managers;
    this.handler = handler; 
    this.ctc = ctc;
    //initGUI();
    //this.redStationNames = dummyTrack.viewStationMap().keySet().toArray(new String[0]);
    createSchedules();
  }

  /*public static void main(String[] args) {
    try {
      MBO_gui gui = new MBO_gui();
      //gui.frame.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }*/

  public void initGUI() {
    try {
      gui = new MBO_gui(this);
      //gui = new MBO_gui();
      gui.frame.setVisible(true);
      gui.frame.setDefaultCloseOperation(2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void createSchedules() {
    /*
    String [] stationNames;
    int [] stationTimes;

    for (String linekey: dummyTrack.trackList.keySet()) {
      System.out.println(linekey);
      //schedules.add(new Schedule(linekey, stationNames, stationTimes, lineLoopTime));
      System.out.println(dummyTrack.viewStationMap().keySet());
    }
    */

    schedules.add(new Schedule(dummyTrack, managers.get(0), handler, hardCodeStops("Red"), "Red",
           redStationNames, redStationOrder, redStationTimes, redLineLoopTime, this.ctc));
    //schedules.add(new Schedule(linekey, stationNames, stationTimes, lineLoopTime));
  }

  public void setMode(String mode) {

    if ("MBO".equals(mode) || "FB".equals(mode) || "MAN".equals(mode)) {
      this.mode = mode;
    } else {
      this.mode = "FB";
    }

    for (int i = 0; i < schedules.size(); i++) {
      schedules.get(i).setMode(mode);
    }
  }

  public void updateTrains() {

    if ("MAN".equals(mode)) {
      return;
    }

    for (int i = 0; i < schedules.size(); i++) {
      schedules.get(i).updateTrains();
    }
  }

  public ArrayList<Schedule> getSched() {
    return schedules;
  }

  private Block[] hardCodeStops(String line) {
    ArrayList<Block> blocks = new ArrayList<Block>();

    if ("Red".equals(line)) {
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
    } else if ("Green".equals(line)) {
      
    }

    return blocks.toArray(new Block [0]);
  }

  /**
   * Gets the actual speed of a train
   * @param  train Train object
   * @return double actSpeed
   */
  private Double getActSpeed(Train train) {
    return train.getVelocity();
  }

  /**
   * Gets the actual authority of a train
   * @param  train Train object
   * @return Block actAuth
   */
  private Block getActAuth(Train train) {
    return null;
  }

  /**
   * Calculates the variance in speed between suggested and actual
   * @param  id ID of a train
   * @param  train Train object
   * @return double diffSpeed
   */
  private double calcVarSpeed(int id, Train train) {
    return 0;
  }

  /**
   * Displays the variance between suggested and actual authority
   * @param  train Train object
   * @return String diffAuthority
   */
  private String calcVarAuth(Train train) {
    return null;
  }

  /**
   * Updates the variance for all trains for display
   */
  private void updateVariance() {

  }
}
