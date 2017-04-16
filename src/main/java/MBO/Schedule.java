package MBO;

import CTC.CTCgui;
import CTC.DummyTrain;
import CTC.TrainManager;
import CommonUIElements.ClockAndLauncher.Launcher;
import TrackModel.Block;
import TrackModel.Station;
import TrackModel.TrackModel;
import TrainModel.GPS;
import TrainModel.Train;
import TrainModel.TrainHandler;

import java.util.ArrayList;
import java.util.List;

public class Schedule{
  /*
  Global data for the train
    Station names
    Times between stations

  Hardcoded in for now
  */
  private CTCgui ctc;
  private String mode;
  private TrackModel dummyTrack;
  private TrainManager manager;
  private TrainHandler handler;
  private ArrayList<TrainSchedule> schedules = new ArrayList<TrainSchedule>();
  private Block yardBlock;
  private int numTrains = 0;
  private Block [] lineStops;
  private String lineName;
  private String [] stationNames;
  private String [] stationOrder;
  // time in seconds to travel between stations
  private int [] stationTimes;
  private int dwellTime = 60; // default time to dwell at a station is 60 seconds
  private int lineLoopTime; //in seconds, includies dwell

  /**
   * Constructor for the schedule
   * @param  lineName     Name of the line the schedule is for
   * @param  stationNames Array of station names
   * @param  stationTimes Array of times to travel between stations
   * @param  lineLoopTime Time it takes a train to complete a loop
   */
  public Schedule(TrackModel dummyTrack, TrainManager manager, Block [] lineStops, 
                String lineName, String [] stationNames, String [] stationOrder, 
                int [] stationTimes, int lineLoopTime, CTCgui ctc) {
    this.dummyTrack = dummyTrack;
    this.manager = manager;
    this.lineStops = lineStops;
    this.lineName = lineName;
    this.stationNames = stationNames;
    this.stationOrder = stationOrder;
    this.stationTimes = stationTimes;
    this.lineLoopTime = lineLoopTime;
    this.ctc = ctc;
    //this.lineLoopTime = calcLoopTime(stationTimes);
    yardBlock = manager.getYardBlock();
  }

  public static void main(String[] args) {

  }

  private int calcLoopTime(int [] stationTimes) {

    return 0;
  }

  private int getStationIndex(String str) {
    for (int i = 0; i < stationNames.length; i++) {
      if (stationNames[i].equalsIgnoreCase(str)) {
        return i;
      }
    }

    return -1;
  }

  private int getStationOrder(String str) {
    for (int i = 0; i < stationOrder.length; i++) {
      if (stationOrder[i].equalsIgnoreCase(str)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Convert time from seconds for display
   * @param  secs Time in seconds from the start of the day
   * @return String time
   */
  public static String convertTime(long secs) {

    int secondsDisplay = (int) secs % 60;
    long minutes = secs / 60;
    int minutesDisplay = (int) minutes % 60;
    long hours = minutes / 60;
    int hoursDisplay = (int) hours % 24;
    int ampm = hoursDisplay / 12;
    String meridian;
    String time = "";

    if (ampm == 0) {
      meridian = " AM";
    } else if (ampm > 0) {
      meridian = " PM";
      hoursDisplay %= 12;
    } else {
      meridian = " ERROR";
    }

    if (0 == hoursDisplay) {
      hoursDisplay = 12;
      time += hoursDisplay + ":";
    } else if (hoursDisplay < 10) {
      time += "0" + hoursDisplay + ":";
    } else {
      time += hoursDisplay + ":";
    }

    if (minutesDisplay < 10) {
      time += "0" + minutesDisplay + ":";
    } else {
      time += minutesDisplay + ":";
    }

    if (secondsDisplay < 10) {
      time += "0" + secondsDisplay;
    } else {
      time += secondsDisplay;
    }

    time += meridian;

    return time;

  }

  /**
   * Creates a simple schedule for the trains
   * @param  numLoops           Number of loops the schedule should be calculated for
   * @param  start              Time the schedule should be started
   * @param  numTrains          Number of trains on the line
   * @return ArrayList<List<Integer>>     stationArrivals
   *
   */
  public void createSchedule(int numLoops, int start, int numTrains) {
    
    this.numTrains = numTrains;

    int trainSpacing = lineLoopTime / numTrains;
    int sum;
    for (int train = 0; train < numTrains; train++) {

      if (manager.size() <= train) {
        manager.addTrain(new DummyTrain(manager.size() + 1));
        DummyTrain temp = manager.getTrainList().get(manager.size() - 1);
        if (null == temp.getLastStation()) {
          temp.setLastStation(yardBlock);
        }
      }
      if (schedules.size() <= train) {
        schedules.add(new TrainSchedule(manager.getTrain(manager.size())));
        schedules.get(train).setLoops(numLoops);
      }

      sum = start + train * trainSpacing;
      for (int loop = 0; loop < numLoops; loop++) {
        if (schedules.get(train).size() <= loop) {
          schedules.get(train).getList().add(new ArrayList<Integer>());
        }
        for (int station = 0; station < stationOrder.length - 1; station++) {
          if (0 == loop && 0 == station) {
            sum -= dwellTime;
          }
          sum += stationTimes[station] + dwellTime;
          schedules.get(train).get(loop).add((sum));
        }
      }
    }
    updateTrains();
  }


  private void driverSchedule(int start, int numLoops) {

    long scheduleLength = lineLoopTime * numLoops;

    for(int sched = 0; sched < schedules.size(); sched++){
      TrainSchedule train = schedules.get(sched);
      ArrayList<ArrayList<Integer>> list = train.getList();
      ArrayList<Integer> arr = list.get(list.size() - 1);
      Integer lastTime = arr.get(arr.size() - 1);
    }
  }

  public void testBlockToBlock() {
    for (int i = 0; i < lineStops.length - 1; i++) {
      Block startBlock = lineStops[i];
      Block stopBlock = lineStops[i + 1];

      ArrayList<Block> path = dummyTrack.blockToBlock(startBlock, stopBlock).get(0);

      System.out.printf("\n\n");
      printPath(path);
      System.out.printf("\n\n");
    }
  }

  /**
   * Handles the switching of modes
   * @param nextMode Next mode of operation
   */
  private void switchModes(String nextMode) {

    if ("MBO".equals(nextMode) || "FB".equals(nextMode)) {
      mode = nextMode;
    }

  }

  /**
   * Updates the list of trains with their new authority and speed
   *
   * @bug Add in update for MBO mode
   */
  public void updateTrains() {
    ArrayList<DummyTrain> trainList = manager.getTrainList();
    ArrayList<Block> newPaths = new ArrayList<Block>();
    ArrayList<Block> tempPath;
    Block lastStation;
    Block nextStop;

    if ("MBO".equals(mode)) {

    } else {
      for (int i = 0; i < trainList.size(); i++) {
        lastStation = trainList.get(i).getLastStation();
        nextStop = lineStops[(getStopNum(lastStation) + 1) % lineStops.length];
        tempPath = createRoute(trainList.get(i), lastStation, nextStop);
        manager.getTrain(trainList.get(i).getID()).setPath(tempPath);
        newPaths.addAll(tempPath);
        
        if (null != trainList.get(i).getPosition()) {
          if (trainList.get(i).getPosition().compareTo(nextStop) == 0) {
            manager.getTrain(trainList.get(i).getID()).setLastStation(nextStop);
          }
        } else {
          DummyTrain newTrain = trainList.get(i);
          newTrain.setPosition(yardBlock);
          tempPath = createRoute(newTrain, yardBlock, lineStops[0]);
          newTrain.setPath(tempPath);
          newPaths.addAll(tempPath);
        }
      }
    }

    if (null != ctc) {
      this.ctc.getTrainPanel().updateSpeedAuthToWS(newPaths);
    }
  }

  private void printPath(ArrayList<Block> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).blockNum() + "\t");
    }
  }

  private void printSandA(ArrayList<Block> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).blockNum() + "\t" + list.get(i).getSuggestedSpeed()
                          + "\t" + list.get(i).getAuthority().blockNum());
    }
  }

  private void printAuth(ArrayList<Block> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).getAuthority().blockNum() + "\t");
    }
  }

  private int getStopNum(Block lastStation) {
    for (int i = 0; i < lineStops.length; i++) {
      if (lastStation.compareTo(lineStops[i]) == 0) {
        return i;
      }
    }
    return -1;
  }

  private int findScheduleIndex(int trainID) {

    for (int i = 0; i < schedules.size(); i++) {
      if (trainID == schedules.get(i).getTrainID()) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Creates a small route for a train from one station to another
   * @param train Train object
   * @param start Block train starts at
   * @param stop  Block train stops at
   *
   * @bug Actually choose a path instead of just the first one
   * @bug Assosciate arrival time with train ID
   */
  private ArrayList<Block> createRoute(DummyTrain train, Block startBlock, Block stopBlock) {

    ArrayList<Block> path = pathHardCoded(startBlock);
    //ArrayList<Block> path = dummyTrack.blockToBlock(startBlock, stopBlock).get(0);

    GPS auth = findAuthority(path, train);
    int stationIndex = getStationIndex(stopBlock.getStationName());
    long schedArrival = schedules.get(findScheduleIndex(train.getID())).getTime(0, stationIndex);
    GPS currPos;
    if ("MBO".equals(mode)) {
      currPos = handler.findTrain(train.getID()).getGPS();
    } else {
      currPos = new GPS(train.getPosition(), null);
    }
    double [] speeds = calcBlockSpeeds(path, currPos, schedArrival);
    int start = findBlockInList(currPos.getCurrBlock(), path);
    if (start < 0) {
      start = 0;
    }
    
    if ("MBO".equals(mode)) {
      for (int i = start; i < path.size(); i++) {
        //handler.findTrain(train.getID()).setAuthority(auth);
        handler.findTrain(train.getID()).setSpeed(speeds[i]);
      }
    } else {
      for (int i = start; i < path.size(); i++) {
        path.get(i).setAuthority(auth.getCurrBlock());
        path.get(i).setSuggestedSpeed(speeds[i]);
      }
    }

    return new ArrayList<Block>(path.subList(start, path.size()));
  }

  private int findBlockInList(Block block, ArrayList<Block> list) {
    if (null == block) {
      return -1;
    }
    int num = block.blockNum();
    for (int i = 0; i < list.size(); i++) {
      if (num == list.get(i).blockNum()) {
        return i;
      }
    }
    return -1;
  }

  private ArrayList<Block> pathHardCoded(Block startBlock) {
    ArrayList<Block> blocks = new ArrayList<Block>();

    if (startBlock.compareTo(yardBlock) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "U", new Integer(77)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(9)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(8)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(7)));
    } else if (startBlock.compareTo(lineStops[0]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(7)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(6)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(5)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(4)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(3)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(2)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(1)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(16)));
    } else if (startBlock.compareTo(lineStops[1]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(16)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(17)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(18)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(19)));
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(20)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(21)));
    } else if (startBlock.compareTo(lineStops[2]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(21)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(22)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(23)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(24)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(25)));
    } else if (startBlock.compareTo(lineStops[3]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(25)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(26)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(27)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(28)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(29)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(30)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(31)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(32)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(33)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(34)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(35)));
    } else if (startBlock.compareTo(lineStops[4]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "H", new Integer(35)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(36)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(37)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(38)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(39)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(40)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(41)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(42)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(43)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(44)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(45)));
    } else if (startBlock.compareTo(lineStops[5]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "H", new Integer(45)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(46)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(47)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(48)));
    } else if (startBlock.compareTo(lineStops[6]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(48)));
      blocks.add(dummyTrack.getBlock(lineName, "J", new Integer(49)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(50)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(51)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(52)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(53)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(54)));
      blocks.add(dummyTrack.getBlock(lineName, "K", new Integer(55)));
      blocks.add(dummyTrack.getBlock(lineName, "K", new Integer(56)));
      blocks.add(dummyTrack.getBlock(lineName, "K", new Integer(57)));
      blocks.add(dummyTrack.getBlock(lineName, "L", new Integer(58)));
      blocks.add(dummyTrack.getBlock(lineName, "L", new Integer(59)));
      blocks.add(dummyTrack.getBlock(lineName, "L", new Integer(60)));
    } else if (startBlock.compareTo(lineStops[7]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "L", new Integer(60)));
      blocks.add(dummyTrack.getBlock("Red", "M", new Integer(61)));
      blocks.add(dummyTrack.getBlock("Red", "M", new Integer(62)));
      blocks.add(dummyTrack.getBlock("Red", "M", new Integer(63)));
      blocks.add(dummyTrack.getBlock("Red", "N", new Integer(64)));
      blocks.add(dummyTrack.getBlock("Red", "N", new Integer(65)));
      blocks.add(dummyTrack.getBlock("Red", "N", new Integer(66)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(52)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(51)));
      blocks.add(dummyTrack.getBlock("Red", "J", new Integer(50)));
      blocks.add(dummyTrack.getBlock(lineName, "J", new Integer(49)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(48)));
    } else if (startBlock.compareTo(lineStops[8]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(48)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(47)));
      blocks.add(dummyTrack.getBlock(lineName, "I", new Integer(46)));
      blocks.add(dummyTrack.getBlock(lineName, "H", new Integer(45)));
    } else if (startBlock.compareTo(lineStops[9]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(45)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(44)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(43)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(42)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(41)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(40)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(39)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(38)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(37)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(36)));
      blocks.add(dummyTrack.getBlock(lineName, "H", new Integer(35)));
    } else if (startBlock.compareTo(lineStops[10]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(35)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(34)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(33)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(32)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(31)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(30)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(29)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(28)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(27)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(26)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(25)));
    } else if (startBlock.compareTo(lineStops[11]) == 0) {
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(25)));
      blocks.add(dummyTrack.getBlock("Red", "H", new Integer(24)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(23)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(22)));
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(21)));
    } else if (startBlock.compareTo(lineStops[12]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "G", new Integer(21)));
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(20)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(19)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(18)));
      blocks.add(dummyTrack.getBlock("Red", "F", new Integer(17)));
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(16)));
    } else if (startBlock.compareTo(lineStops[13]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(16)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(1)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(2)));
      blocks.add(dummyTrack.getBlock("Red", "A", new Integer(3)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(4)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(5)));
      blocks.add(dummyTrack.getBlock("Red", "B", new Integer(6)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(7)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(8)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(9)));
      blocks.add(dummyTrack.getBlock(lineName, "U", new Integer(77)));
    } else {
      blocks = null;
    }

    /*else if (startBlock.compareTo(lineStops[13]) == 0) {
      blocks.add(dummyTrack.getBlock(lineName, "F", new Integer(16)));
      blocks.add(dummyTrack.getBlock("Red", "E", new Integer(15)));
      blocks.add(dummyTrack.getBlock("Red", "E", new Integer(14)));
      blocks.add(dummyTrack.getBlock("Red", "E", new Integer(13)));
      blocks.add(dummyTrack.getBlock("Red", "D", new Integer(12)));
      blocks.add(dummyTrack.getBlock("Red", "D", new Integer(11)));
      blocks.add(dummyTrack.getBlock("Red", "D", new Integer(10)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(9)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(8)));
      blocks.add(dummyTrack.getBlock("Red", "C", new Integer(7)));
    }*/

    return blocks;
  }

  /**
   * Gets the next train in front if there is one
   * @param  blocks    list of blocks
   * @return Train     nextTrain
   *
   * @bug Need to add MBO mode to nextTrain
   */
  private DummyTrain nextTrain(ArrayList<Block> blockList, int start) {

    ArrayList<DummyTrain> trainList = manager.getTrainList();
    blockList = new ArrayList<Block>(blockList.subList(start + 1, blockList.size()));

    for (int i = 0; i <= trainList.size(); i++) {
      if (blockList.contains(trainList.get(i).getPosition())) {
        return trainList.get(i);
      }
    }

    return null;
  }

  private Block nextOccupied(ArrayList<Block> blockList, int start) {

    ArrayList<Block> occupied = manager.getOccupancyList();
    int ind;
    if (null == occupied || start < 0 || start > blockList.size()) {
      return null;
    }
    for (int i = start; i < blockList.size(); i++) {
      if (occupied.contains(blockList.get(i))) {
        return blockList.get(i);
      }
    }


    return null;
  }

  private Block nextStation(ArrayList<Block> blockList, int start) {

    if (start < 0 || start + 1 > blockList.size()) {
      return null;
    }
    for (int i = start + 1; i < blockList.size(); i++) {
      if (blockList.get(i).getAssociatedStation() != null) {
        return blockList.get(i);
      }
    }

    return null;
  }

  /**
   * Sets the authority of a train
   * @param blockList list of blocks
   * @param train     Train object
   *
   * @bug Add in check for switches
   * @bug Get position of trains in fixed block mode
   * @bug Check for null values
   */
  private GPS findAuthority(ArrayList<Block> blockList, DummyTrain train) {

    Block currBlock;
    GPS nextTrain;

    if ("MBO".equals(mode)) {
      Train tr = handler.findTrain(train.getID());
      currBlock = tr.getGPS().getCurrBlock();
      DummyTrain temp = nextTrain(blockList, findBlockInList(currBlock, blockList));
      nextTrain = handler.findTrain(temp.getID()).getGPS();
    } else {
      currBlock = train.getPosition();
      nextTrain = new GPS(nextOccupied(blockList, findBlockInList(currBlock, blockList)), null);
    }

    Block nextStation = nextStation(blockList, findBlockInList(currBlock, blockList));
    GPS auth = null;
    if (null == nextTrain && null == nextStation) {
      if ("MBO" == mode) {

      } else {
        auth = new GPS(blockList.get(blockList.size()), null);
      }

    } else if (null == nextStation) {
      if ("MBO" == mode) {

      } else {
        auth = nextTrain;
      }
    } else if (null == nextTrain || null == nextTrain.getCurrBlock()) {
      if ("MBO" == mode) {

      } else {
        auth = new GPS(nextStation, null);
      }
    } else {
      int comp = nextTrain.getCurrBlock().compareTo(nextStation);
      if (comp <= 0) {
        if ("MBO".equals(mode)) {
          auth = nextTrain;
        } else {
          auth = new GPS(nextTrain.getCurrBlock(), null);
        }
      } else {
        auth = new GPS(nextStation, null);
      }
    }

    return auth;
  }

  /**
   * Checks to see if a collision between two trains will occur
   * @param  back   Train in the back
   * @param  front  Train in the front
   * @return int    collision
   *
   * @bug take into account that the trains will change speeds as they change blocks
   *       change over from getMinSpeedLimit to calcBlockSpeeds to increase throughput
   * @bug implement whole function as authority calculation
   */
  //private int collisionCheck(Train back, Train front) {
    /*
    0: No braking necessary
    -1: Apply service brakes
    -2: Apply emergency brakes
    else: Speed the front train needs to go to avoid collision
     */
  /*  int collision;
    double distBetween = getDistBetween(front, back);
    double safeBrake = MovingBlockOverlay.findStopDist(back);
    int timeToBrake = MovingBlockOverlay.timeToBrake(back);
    double emgBrake = MovingBlockOverlay.findEmgStopDist(back);
    int timeToEmgBrake = MovingBlockOverlay.timeToEmgBrake(back);
    double speedDifference = front.getVelocity() - back.getVelocity();

    if (speedDifference >= 0) {
      collision = 0;
    }
    else if (distBetween + timeToBrake * speedDifference > safeBrake) {
      collision = -1;
    }
    else if (distBetween + timeToEmgBrake * speedDifference > emgBrake) {
      collision = -2;
    }
    else {
    }

    return collision;
  }*/

  /**
   * Gets the distance in meters between two trains
   * @param  front   Train in front
   * @param  back    Train in back
   * @return double  dist
   */
  private double getDistBetween(Train front, Train back) {
    return 0;
  }


  /**
   * Gets the distance in meters between the current position and end of the block list
   * @param  blockList List of blocks
   * @param  currPos   Current position
   * @return double    dist
   */
  private double getDistBetween(ArrayList<Block> blockList, GPS currPos) {

    int index = blockList.indexOf(currPos.getCurrBlock());
    double dist = 0;

    // throw exception
    if (-1 == index) {
      return 0;
    }
    while (index <= blockList.size()) {
      dist += blockList.get(index++).getLen();
    }

    if (null != currPos.getDistIntoBlock()) {
      dist -= currPos.getDistIntoBlock();
    }

    return dist;
  }

  /**
   * Performs check to see if a train will arrive on time
   * @param  blockList List of blocks
   * @param  speeds    List of speeds
   * @param  currPos   Current position
   * @param  schedTime Scheduled
   * @return           [description]
   */
  private boolean arriveOnTime(ArrayList<Block> blockList, double[] speeds, 
                                GPS currPos, long schedTime) {

    int index = blockList.indexOf(currPos.getCurrBlock());
    long estimate = 0;

    // throw exception
    if (-1 == index) {
      return false;
    }
    if (null != currPos.getDistIntoBlock()) {
      estimate -= currPos.getDistIntoBlock() / speeds[index];
    }
    while (index <= blockList.size()) {
      estimate += blockList.get(index).getLen() / speeds[index++];
    }

    return (estimate <= schedTime);
  }

  /**
   * Calculates speeds per block taking into account speed limits, other trains, etc
   * @param  blockList list of blocks
   * @param currPos Current position of train in block list
   * @param schedArrival Time the train is scheduled to arrive
   * @return double[] speeds
   *
   * @bug Increase speed in a block to get there before curr time == schedArrival
   *       Use getMaxSpeedDiffBlock and add to that block
   */
  private double[] calcBlockSpeeds(ArrayList<Block> blockList, GPS currPos, long schedArrival) {

    double dist = getDistBetween(blockList, currPos);
    long eta = schedArrival - CommonUIElements.ClockAndLauncher.Launcher.getCurrTime();
    double avgSpeed = dist / eta;
    Block currBlock = currPos.getCurrBlock();
    double minSpeed = getMinSpeedLimit(blockList, blockList.indexOf(currBlock));
    double[] speeds = new double[blockList.size()];
    int firstSlowBlock = 0;

    for (int i = 0; i < speeds.length; i++) {
      speeds[i] = minSpeed;
    }

    if (avgSpeed <= minSpeed) {
      return speeds;
    }

    while (true) {
      firstSlowBlock = blockBelowSpeedLimit(blockList, speeds, firstSlowBlock);
      if (firstSlowBlock > speeds.length) {
        break;
      }
      for (int i = firstSlowBlock; i < speeds.length; i++) {
        if ((speeds[i] + 1) <= blockList.get(i).getSpeedLimit()) {
          speeds[i]++;
        }
      }
      if (arriveOnTime(blockList, speeds, currPos, eta)) {
        return speeds;
      }
    }

    for (int i = 0; i < speeds.length; i++) {
      speeds[i] = blockList.get(i).getSpeedLimit();
    }

    return speeds;
  }

  /**
   * Gets the minimum speed limit from a list of blocks
   * @param  blockList list of blocks
   * @return double    minSpeed
   *
   * @bug Change so it calculates the min speed from start onwards
   */
  private double getMinSpeedLimit(ArrayList<Block> blockList, int start) {
    double minSpeed = Double.MAX_VALUE;
    double blockLimit;

    if (start < 0) {
      start = 0;
    } else if (start > blockList.size() - 1) {
      start = blockList.size() - 1;
    }

    for (int i = start; i < blockList.size(); i++) {
      blockLimit = blockList.get(i).getSpeedLimit();
      minSpeed = (blockLimit < minSpeed) ? blockLimit : minSpeed;
    }

    return minSpeed;
  }

  /**
   * Gets the index of the block with the maximum difference between
   * set speed and speed limit
   * @param  blockList list of blocks
   * @param  speeds    array of set speeds
   * @return int       index
   */
  private int getMaxSpeedDiffBlock(ArrayList<Block> blockList, double[] speeds) {
    int index = -1;
    double maxDiff = Double.MIN_VALUE;
    double currLimit;

    for (int i = 0; i < blockList.size(); i++) {
      currLimit = blockList.get(i).getSpeedLimit() - speeds[i];
      if (currLimit > maxDiff) {
        maxDiff = currLimit;
        index = i;
      }
    }

    return index;
  }

  /**
   * Returns the position of the block furthest below it's speed limit
   * @param  blockList List of block
   * @param  speeds    List of speeds
   * @param  prevIndex Index to start from
   * @return int       index
   */
  private int blockBelowSpeedLimit(ArrayList<Block> blockList, double[] speeds, int prevIndex) {

    if (prevIndex > speeds.length) {
      return 2 * speeds.length;
    }
    if (prevIndex < 0) {
      prevIndex = 0;
    }
    for (int i = prevIndex; i < speeds.length; i++) {
      if ((speeds[i] + 1) < blockList.get(i).getSpeedLimit()) {
        return i;
      }
    }

    return 2 * speeds.length;
  }

  /**
   * Calculates the station arrival times for a particular station
   * @param  station        Station to get times for
   * @return List<Integer>  times
   */
  private List<Integer> stationArrivalTimes(Station station) {
    return null;
  }

  /**
   * Compares the number of trains on the track with user entered number of trains
   * Adds/Removes trains as necessary
   * @param trainList List of trains currently on the track
   */
  private void compareTrainNums(List<Train> trainList) {

  }

  /**
   * Attempts to space out the trains as evenly as possible
   */
  private void spaceOut() {

  }

  /**
   * Gets the train that is closest to a station
   * @return Train closestTrain
   */
  private Train closestToStation() {
    return null;
  }

  public ArrayList<TrainSchedule> getSched() {
    return schedules;
  }

}
