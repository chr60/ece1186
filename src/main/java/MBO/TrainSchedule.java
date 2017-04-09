package MBO;

import CTC.DummyTrain;

import java.util.ArrayList;

public class TrainSchedule{

  /**
   * Stores the times for each station
   * Inner ArrayList has time for each station in station list
   * Outer ArrayList has number of loops around track
   *  Even numbers are arrivals, odds are departures
   */
  private ArrayList<ArrayList<Integer>> stationTimes;
  private DummyTrain train;
  private int numLoops;
  private Driver currDriver;
  private String lineName;

  public TrainSchedule() {
    stationTimes = new ArrayList<ArrayList<Integer>>();
  }

  public TrainSchedule(DummyTrain train) {
    stationTimes = new ArrayList<ArrayList<Integer>>();
    this.train = train;
  }

  public TrainSchedule(ArrayList<ArrayList<Integer>> stationTimes, DummyTrain train) {
    this.stationTimes = stationTimes;
    this.train = train;
  }

  public int getLoops() {
    return numLoops;
  }

  public void setLoops(int numLoops) {
    this.numLoops = numLoops;
  }


  public int size() {
    return stationTimes.size();
  }

  public int getNumStations() {
    return stationTimes.get(0).size();
  }

  public void removeTime(int out, int in) {
    if (stationTimes.size() < out || stationTimes.get(out).size() < in) {
      return;
    }
    stationTimes.get(out).set(in, null);
    stationTimes.get(out + 1).set(in, null);
  }

  public Integer getTime(int out, int in) {
    return stationTimes.get(out).get(in);
  }

  public void setTime(Integer time, int out, int in) {
    stationTimes.get(out).set(in, time);
  }

  public ArrayList<ArrayList<Integer>> getList() {
    return stationTimes;
  }

  public ArrayList<Integer> get(int out) {
    return stationTimes.get(out);
  }

  public void add(int num, ArrayList<Integer> list) {
    //stationTimes.get(num).add(list);
  }

  public DummyTrain getTrain() {
    return train;
  }

  public int getTrainID() {
    return train.getID();
  }

  public void changeDriver(Driver currDriver) {
    this.currDriver = currDriver;
  }

  public String toString() {
    String str = "Station times: ";
    for (int i = 0; i < stationTimes.get(0).size(); i++) {
      str += "\t" + Schedule.convertTime(stationTimes.get(0).get(i));
    }
    return str;
  }

}