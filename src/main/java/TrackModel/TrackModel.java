package TrackModel;

/** Class model and some associated utility functions for storing the TrackModel.
* @author Michael
*/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class TrackModel implements Serializable {

  public String trackScope;
  private Boolean verbose;

  public TrackModel(String scope) {
    this.trackScope = scope;
    this.verbose = false;
  }

  public TrackModel(String scope, Boolean verbose) {
    this.trackScope = scope;
    this.verbose = verbose;
  }

  public HashMap<String,HashMap<String, HashMap<Integer, Block>>> trackList =
      new HashMap<String,HashMap<String, HashMap<Integer, Block>>>();

  HashMap<String, Block> rootMap = new HashMap<String, Block>();
  HashMap<String, ArrayList<Block>> leafMap = new HashMap<String, ArrayList<Block>>();
  HashMap<String,HashMap<String, ArrayList<Block>>> stationList
      = new HashMap<String,HashMap<String, ArrayList<Block>>>();
  HashMap<String,HashMap<String, Station>> stationHostMap
      = new HashMap<String,HashMap<String, Station>>();
  HashMap<Block, Station> blockStationMap = new HashMap<Block, Station>();
  HashMap<Block, Crossing> crossingMap = new HashMap<Block, Crossing>();
  HashMap<Block, Lights> lightsMap = new HashMap<Block,Lights>();
  HashMap<Station, ArrayList<Lights>> stationLightsMap = new HashMap<Station, ArrayList<Lights>>();
  HashMap<Block, Beacon> blockBeaconMap = new HashMap<Block, Beacon>();
  HashMap<String, ArrayList<Block>> flatList = new HashMap<String, ArrayList<Block>>();
  HashMap<String, Switch> switchMap = new HashMap<String, Switch>();

  /**
   * Returns flatList
   * @return flatList
   */
  public HashMap<String, ArrayList<Block>> getFlatList() {
    return this.flatList;
  }

  /**
  * Simplicity wrapper to return a non-aliased block on the track given the parameters.
  * @param line Line of the block to be looked up
  * @param section Section of the block to be looked up
  * @param blockNum Number of the block to be looked up
  */
  public Block getBlock(String line, String section, Integer blockNum) {
    return this.trackList.get(line).get(section).get(blockNum);
  }

  /**
  * Allows for lookup of a block via only its blocknum.
  * @param the blockNum of the block
  * @return the Block object associated with that blocknum
  */
  private Block getBlock(String line, Integer blockNum) throws NoSuchElementException{
    for (String section : this.trackList.get(line).keySet()) {
      for (Integer currBlock : this.trackList.get(line).get(section).keySet()) {
        if (currBlock.equals(blockNum)) {
          return this.trackList.get(line).get(section).get(currBlock);
        }
      }
    }
    NoSuchElementException e = new NoSuchElementException();
    e.printStackTrace();
    throw e;
  }

  /**
  * Returns the occupancy of a section on a given line.
  */
  public Boolean sectionOccupancy(String line, String section) {
    for (Integer b : this.trackList.get(line).get(section).keySet()) {
      if(this.trackList.get(line).get(section).get(b).getOccupied()) {
        return true;
      }
    }
    return false;
  }

  /**
  * Returns a block given a line and number.
  * @param the line
  * @param the block number
  */
  public ArrayList<Block> getBrokenBlocks(String line) {
    ArrayList<Block> brokenList = new ArrayList<Block>();

    for (String section : this.trackList.get(line).keySet()) {
      for(Integer blk : this.trackList.get(line).get(section).keySet()) {
        if (this.trackList.get(line).get(section).get(blk).getBroken()) {
          brokenList.add(this.trackList.get(line).get(section).get(blk));
        }
      }
    }
    return brokenList;
  }

  /**
  * Returns the occupied blocks of the track for a given line
  * @return an arraylist of the occupied blocks.
  */
  public ArrayList<Block> getOccupiedBlocks(String line) {
    ArrayList<Block> occupiedList = new ArrayList<Block>();
    for(String section : this.trackList.get(line).keySet()) {
      for(Integer blk : this.trackList.get(line).get(section).keySet()) {
        if(this.trackList.get(line).get(section).get(blk).getOccupied()) {
          occupiedList.add(this.trackList.get(line).get(section).get(blk));
        }
      }
    }
    return occupiedList;
  }

  /**
  * Returns the occupied blocks of the track
  * @return an arraylist of the occupied blocks.
  */
  public ArrayList<Block> getOccupiedBlocks() {
    ArrayList<Block> occupiedList = new ArrayList<Block>();
    for(String line : this.trackList.keySet()) {
      for(String section : this.trackList.get(line).keySet()) {
        for(Integer blk : this.trackList.get(line).get(section).keySet()) {
          if(this.trackList.get(line).get(section).get(blk).getOccupied()) {
            occupiedList.add(this.trackList.get(line).get(section).get(blk));
          }
        }
      }
    }
    return occupiedList;
  }

  /**
  * Lateral lookup for tracks. Intended for use with dummyTrack->globalTrack and
  * vica-versa. Should be called on the object whose information is desired using
  * the source block.
  * @param block to be laterally looked up.
  */
  public Block lateralLookup(Block block) {
    return this.trackList.get(block.blockLine).get(block.blockSection).get(block.blockNum);
  }

  /**
  * A method for viewing the trackmodels object. A god method, not intended for use
  * by other modules. For logging purposes; other modules should use the accessors.
  * @return this track model.
  */
  public TrackModel view() {
    return this;
  }

  /**
  * Returns a non-aliased section of blocks.
  * @param line line the section is on
  * @param section section being requested
  */
  public HashMap<Integer, Block> getSection(String line, String section) {
    return this.trackList.get(line).get(section);
  }

  /**
  * Allows viewing of the trackList to other modules, implemented as a copy method.
  * @return HashMap<String, HashMap<String,HashMap<Integer,Block>>>
  */
  public HashMap<String,HashMap<String,HashMap<Integer,Block>>> viewTrackList() {
    return new HashMap<String,HashMap<String,HashMap<Integer,Block>>>(this.trackList);
  }

  /**
  * Allows viewing of the beacon map by other modules. Implemented as a copy method.
  * @return HashMap<Block, Beacon>
  */
  public HashMap<Block, Beacon> viewBeaconMap() {
    return new HashMap<Block, Beacon>(this.blockBeaconMap);
  }
  /**
  * Returns a block-to-block list of lists that details the possible paths from a block to block. Does not permit
  * revisiting a block from within this function
  * @param block that you start at
  * @param blcok that you end at
  */
  public ArrayList<ArrayList<Block>> blockToBlock(Block startBlock, Block endBlock) {
    ArrayList<ArrayList<Block>> possiblePaths = new ArrayList<ArrayList<Block>>();
    ArrayList<Block> visited = new ArrayList<Block>();
    possiblePaths = blockPath(possiblePaths, visited, startBlock, endBlock);

    return possiblePaths;
  }

  /**
  * Build a specific path
  * @param the list of visited blocks
  * @param the current block
  * @param the desired ending block
  */
  private ArrayList<ArrayList<Block>> blockPath(ArrayList<ArrayList<Block>> possiblePaths,
    ArrayList<Block> visited, Block currBlock, Block endBlock) {

    if(visited.contains(currBlock)) {
      return possiblePaths;
    }
    visited.add(currBlock);

    if(currBlock.equals(endBlock)) {
      possiblePaths.add(visited);
      return possiblePaths;
    }

    possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockBackward(), endBlock);
    possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockForward(), endBlock);
    if(!currBlock.switchBlock.equals("") && this.leafMap.get(currBlock.switchBlock).contains(currBlock)) {
      Block switchBlock = this.rootMap.get(currBlock.switchBlock);
      switchBlock.setSwitchState(1);
      possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockBackward(), endBlock);
      //System.out.println(possiblePaths.size());
      possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockForward(), endBlock);
    }
    return possiblePaths;
  }

    /**
    * Allows viewing of the station map to other modules, implemented as a copy method
    * @return HashMap<String, ArrayList<Block>>
    */
    public HashMap<String,HashMap<String, ArrayList<Block>>> viewStationMap() {
      return new HashMap<String,HashMap<String, ArrayList<Block>>>(this.stationList);
    }

    /**
    * Allows viewing of the crossingMap from other modules, implemented as a copy method.
    * @return HashMap<Block, Crossing>
    */
    public HashMap<Block, Crossing> viewCrossingMap() {
      return new HashMap<Block, Crossing>(this.crossingMap);
    }

    /**
    * Allows viewing of leaf nodes of the switches to other modules.
    * @return HashMap<String, ArrayList<Block>> of the leafmap
    */
    public HashMap<String, ArrayList<Block>> viewLeafMap() {
      return new HashMap<String, ArrayList<Block>>(this.leafMap);
    }

    /**
    * Allows viewing of the lights map to other modules, implemented as a copy method
    * @return HashMap<Block, Lights>
    */
    public HashMap<Block, Lights> viewLightsMap() {
      return new HashMap<Block, Lights>(this.lightsMap);
    }

    /**
    * Allows viewing of the blockStationMap by other modules.
    *
    * @return HashMap<Block, Station>
    */
    public HashMap<Block, Station> viewBlockStationMap() {
      return new HashMap<Block, Station>(this.blockStationMap);
    }

    /**
    * Allows viewing of the rootMap by other modules.
    *
    * @return Hash<String, Block>
    */
    public HashMap<String, Block> viewRootMap() {
      return new HashMap<String, Block>(this.rootMap);
    }

    /**
    * Allows viewing of the switchMap by other modules.
    */
    public HashMap<String, Switch> viewSwitchMap() {
      return new HashMap<String, Switch>(this.switchMap);
    }

    /**
    * Adds a selected block to the TrackModel. Expects a valid Block object.
    * @param block The block to be added.
    */
    private void addBlock(Block block) {
      if (!this.trackList.containsKey(block.blockLine)) {
        this.trackList.put(block.blockLine, new HashMap<String,HashMap<Integer,Block>>());
      }
      if (!this.trackList.get(block.blockLine).containsKey(block.blockSection)) {
        this.trackList.get(block.blockLine).put(block.blockSection, new HashMap<Integer, Block>());
      }

      if (!this.flatList.containsKey(block.blockLine)) {
        this.flatList.put(block.blockLine, new ArrayList<Block>());
      }

      this.flatList.get(block.blockLine).add(block);
      this.trackList.get(block.blockLine).get(block.blockSection).put(block.blockNum, block);
    }

    /**
    * Adds a station-block pair to the stationList.
    * @param stationName station to be added to the stationList
    * @param stationLocation the block location of an point where the station interacts with the block
    */
    private void addStation(String line, String stationName, Block stationLocation) {
      if(!this.stationList.containsKey(line)) {
        this.stationList.put(line, new HashMap<String, ArrayList<Block>>());
      }
      if (!this.stationList.get(line).containsKey(stationName)) {
        this.stationList.get(line).put(stationName, new ArrayList<Block>());
      }
      this.stationList.get(line).get(stationName).add(stationLocation);
    }

    /**
    * Adds a switch to the root list of the switches present on a given line.
    *
    * @param rootBlockString  String of the added switch to be added to as the "root" switch
    * @param rootBlock The block where that will point between two blocks depending on the current state
    * of the switch
    */
    private void addSwitchRoot(String rootBlockString, Block rootBlock) {
      if (!this.rootMap.containsKey(rootBlockString)) {
        this.rootMap.put(rootBlockString, rootBlock);
      }
    }

    /**
    * Add the leaf of a switch to the list.
    * @param the string linking the block
    * @param the block to be referenced
    */
    private void addSwitchLeaf(String leafBlockString, Block leafBlock) {
      if (!this.leafMap.containsKey(leafBlockString)) {
        this.leafMap.put(leafBlockString, new ArrayList<Block>());
      }
      this.leafMap.get(leafBlockString).add(leafBlock);
    }

    /**
    * A small function to view the next and previous blocks of a track model.
    */
    void examineNext() {
      for (String lineKey : this.trackList.keySet()) {
        System.out.print("Examining Line: ");
        System.out.println(lineKey);
        Set<String> sectionKeySet = this.trackList.get(lineKey).keySet();
        ArrayList<String> sectionKeyList = new ArrayList(sectionKeySet);
        Collections.sort(sectionKeyList);

        for (String sectionKey : sectionKeyList) {
          System.out.print("On section: ");
          System.out.println(sectionKey);

          Set<Integer> blockSet = this.trackList.get(lineKey).get(sectionKey).keySet();

          for (Integer blockNum : blockSet) {
            System.out.print("On block: ");
            System.out.println(this.trackList.get(lineKey).get(sectionKey).get(blockNum).blockNum);
            System.out.print("Next block forward: ");
            System.out.println(this.trackList.get(lineKey).get(sectionKey).get(blockNum).nextBlockForward().blockNum);
            System.out.print("Next block backward: ");
            System.out.println(this.trackList.get(lineKey).get(sectionKey).get(blockNum).nextBlockBackward().blockNum);
          }
        }
      }
    }

    /**
    *   Links blocks across block and sections.
    *   @todo refactor.
    */
    private void linkBlocks() {
      for (String lineKey : this.trackList.keySet()) {
        Block prevBlock = null;
        Set<String> sectionKeySet = this.trackList.get(lineKey).keySet();
        ArrayList<String> sortedSectionKeyList = new ArrayList(sectionKeySet);
        Collections.sort(sortedSectionKeyList);
        ArrayList<Block> storeList = new ArrayList<Block>();

        for (String sectionKey : sortedSectionKeyList) {
          for (Integer blkNum : this.trackList.get(lineKey).get(sectionKey).keySet()) {
            storeList.add(this.trackList.get(lineKey).get(sectionKey).get(blkNum));
          }
        }

        Collections.sort(storeList);
        Block storeBlock = null;

        if(lineKey.equals("Red")) {
          for (int i = 0; i < storeList.size(); i ++) {
            storeList.get(i).setNextBlockForward();


            if(i != storeList.size()-1) {
              storeList.get(i).setNextBlockForward(storeList.get(i+1));
            }
            storeList.get(i).setNextBlockBackward();
            //System.out.println("On block: " + (i+1)  + " -> " + storeList.get(i).nextBlockForward().blockNum());
            if(i != 0) {
              storeList.get(i).setNextBlockBackward(storeList.get(i-1));
            } else {
              storeList.get(i).setNextBlockBackward();
            }
          }
        }
      }
    }

    /**
    * Build a map for storing the blocks and station for use by the train controller and
    * train model.
    */
    private void buildBlockStationMap() {
      for(String l : stationList.keySet()) {
        for(String s : stationList.get(l).keySet()) {
          for(Block b : stationList.get(l).get(s))
            if(!this.blockStationMap.containsKey(s)) {
              this.blockStationMap.put(b, this.stationHostMap.get(l).get(s));
            }
          }
      }
    }

    /**
    * Builds the light map for usage by the wayside controller to modify the state of the lights.
    */
    private void buildLightsMap() {
      for(String s : this.leafMap.keySet()) {
        for(int i=0; i<this.leafMap.get(s).size(); i++) {
          Lights light = new Lights(this, this.leafMap.get(s).get(i));
          this.lightsMap.put(this.leafMap.get(s).get(i), light);
        }
      }
    }

    /**
    *Build the listing of the host station list for external consumption.
    */
    private void buildStationHostMap() {
      for(String l : this.stationList.keySet()) {
        if(!this.stationHostMap.containsKey(l)) {
          this.stationHostMap.put(l, new HashMap<String, Station>());
        }

        for (String stationName : this.stationList.get(l).keySet()) {
          Station myStation = new Station(this, stationName, this.stationList.get(l).get(stationName));
          this.stationHostMap.get(l).put(stationName, myStation);
        }
      }
    }

    /**
    * Helper function to link nextBlock for switches.
    */
    private void handleSwitches() {
      for (String s : this.rootMap.keySet()) {

        //case 1: root.blockNum < leaf0.blockNum < leaf1.blockNum: uses switchNextBlockForward
        //case 2: leaf0.blockNum < root.blockNum < leaf1.blockNum: uses switchNextBlockBackward
        //case 3: leaf0.blockNum < leaf1.blockNum < root.blockNum: uses switchNextBlockBackward
        if (this.rootMap.get(s).blockNum() < this.leafMap.get(s).get(0).blockNum()) {

          Switch trackSwitch = new Switch(this, s, this.rootMap.get(s), this.leafMap.get(s));
          this.switchMap.put(s, trackSwitch);
          this.rootMap.get(s).setNextBlockForward(this.leafMap.get(s).get(0), this.leafMap.get(s).get(1));

          this.leafMap.get(s).get(0).setRootBlock(this.rootMap.get(s));
          this.leafMap.get(s).get(1).setRootBlock(this.rootMap.get(s));
        } else if (this.rootMap.get(s).blockNum() < this.leafMap.get(s).get(1).blockNum()) {
          Switch trackSwitch = new Switch(this, s, this.rootMap.get(s), this.leafMap.get(s));
          this.switchMap.put(s, trackSwitch);
          this.rootMap.get(s).setNextBlockBackward(this.leafMap.get(s).get(0), this.leafMap.get(s).get(1));
          this.leafMap.get(s).get(0).setRootBlock(this.rootMap.get(s));
          this.leafMap.get(s).get(1).setRootBlock(this.rootMap.get(s));
        } else if (this.rootMap.get(s).blockNum() > this.leafMap.get(s).get(1).blockNum()) {
          Switch trackSwitch = new Switch(this, s, this.rootMap.get(s), this.leafMap.get(s));
          this.switchMap.put(s, trackSwitch);
          this.rootMap.get(s).setNextBlockBackward(this.leafMap.get(s).get(0), this.leafMap.get(s).get(1));
          this.leafMap.get(s).get(0).setRootBlock(this.rootMap.get(s), false, true);
          this.leafMap.get(s).get(1).setRootBlock(this.rootMap.get(s), true, false);
        }
      }
    }

    public void linkCSVOverride(String[] fNames) {
      String line = "";
      String delimiter = ",";
      for (String s : fNames){
        System.out.println("Reading "+s);
        Boolean initLine = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
          while ((line = reader.readLine()) != null) {
            if (initLine.equals(false)) {            
              String[] str = line.split(delimiter, -1);
              String sourceLine = str[0];
              String targetLine = str[0];
              String sourceSection = str[1];
              int sourceBlockNum = Integer.parseInt(str[2]);
              String forwardTargetSection = str[3];
              String backwardTargetSection = str[5];

              if (forwardTargetSection != "" && str[4] != "") {
                int forwardTargetBlockNum = Integer.parseInt(str[4]);
                Block sourceBlock = this.getBlock(sourceLine, sourceSection, sourceBlockNum);
                Block nextBlockForwardOverride = this.trackList.get(targetLine).get(forwardTargetSection).get(forwardTargetBlockNum);
                sourceBlock.setNextBlockForward(nextBlockForwardOverride);
              }
              System.out.println(str[6]);
              System.out.println("ASDF");
              
              if (!backwardTargetSection.equals("") && !str[6].equals("")) {

                int backwardTargetBlockNum = Integer.parseInt(str[6]);
                System.out.println("DSAF");
                System.out.println(backwardTargetBlockNum);
                Block sourceBlock = this.getBlock(sourceLine, sourceSection, sourceBlockNum);
                System.out.println(sourceBlock);
                Block nextBlockBackwardOverride = this.trackList.get(targetLine).get(backwardTargetSection).get(backwardTargetBlockNum);
                System.out.print("SB num: ");
                System.out.println(sourceBlock.blockNum());
                System.out.print("NBB num: ");
                System.out.println(nextBlockBackwardOverride.blockNum());
                sourceBlock.setNextBlockBackward(nextBlockBackwardOverride);
                System.out.print("NBB source: ");
                System.out.println(sourceBlock.nextBlockBackward().blockNum());
              }
              
            }
            initLine = false;
          }
        } catch(IOException|ArrayIndexOutOfBoundsException|NumberFormatException e) {
          System.out.println("Finished reading override!");
        }
      }
    }

    /**
    * Helper function for reading the information from the excel-dumped CSV
    * @param fNames: filenames of the csv's of to read in
    */
    @Deprecated public void readCSV(String[] fNames) {
      System.out.println("WARNING. You are using an unsafe function to read the track model. Use the updated constructor.");
      String line = "";
      String delimiter = ",";
      Boolean defaultOccupied = false;

      for (String s : fNames) {
        Boolean initLine = true; //Allows for graceful failure of reading multiple csv's without exiting
        try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
          while ((line = reader.readLine()) != null) {
            String[] str = line.split(delimiter,-1);

            //For safety when parsing headers; first line will result in incorrect info
            if (initLine.equals(false)){

              String blockLine = str[0];
              String blockSection = str[1];
              Integer blockNum = Integer.valueOf(str[2]);
              Double blockLen = Double.valueOf(str[3]);
              Double blockGrade = Double.valueOf(str[4]);
              Double speedLimit = Double.valueOf(str[5]);
              String infrastructure = str[6];
              Double elevation = Double.valueOf(str[7]);
              String crossing = str[8];
              String switchBlock = str[9];
              String arrowDirection = str[10];
              String stationName = str[11];

              Boolean isUnderground = infrastructure.contains("UNDERGROUND");
              Boolean hasSwitch = infrastructure.contains("SWITCH");
              Boolean hasCrossing = infrastructure.contains("CROSSING");

              //Initialize and add block
              Block myBlock = new Block(this, defaultOccupied, isUnderground, blockLen, blockGrade,
                    elevation, speedLimit, stationName, arrowDirection, blockLine,
                    blockSection, blockNum, hasSwitch, switchBlock);

              this.addBlock(myBlock);

              if (!stationName.equals("")) {
                this.addStation(blockLine, stationName, myBlock);
              }

              if(hasSwitch) {
                this.addSwitchRoot(switchBlock, myBlock);
              }

              if(!hasSwitch && !switchBlock.equals("")) {
                this.addSwitchLeaf(switchBlock, myBlock);
              }

              if(hasCrossing) {
                this.crossingMap.put(myBlock,new Crossing(this, myBlock));
              }

            }
            initLine = false;
          }
        }catch(IOException|ArrayIndexOutOfBoundsException|NumberFormatException e) {
          if (this.verbose.equals(true)) {
            System.out.println("Finished Reading");
          }
        }
    }

    this.linkBlocks();
    this.handleSwitches();
    this.buildStationHostMap();
    this.buildBlockStationMap();
    this.buildLightsMap();
  }

  /**
  * Helper function for reading the information from the excel-dumped CSV
  * @param fNames: filenames of the csv's of to read in
  * @param fOverrideNames: filenames of the corresponding linkage overrides csvs
  */
  public void readCSV(String[] fNames, String[] fOverrideNames) {
    System.out.println(fOverrideNames);
    String line = "";
    String delimiter = ",";
    Boolean defaultOccupied = false;
    for (String s : fNames) {
      Boolean initLine = true; //Allows for graceful failure of reading multiple csv's without exiting
      try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
        while ((line = reader.readLine()) != null) {
          String[] str = line.split(delimiter,-1);

          //For safety when parsing headers; first line will result in incorrect info
          if (initLine.equals(false)){

            String blockLine = str[0];
            String blockSection = str[1];
            Integer blockNum = Integer.valueOf(str[2]);
            Double blockLen = Double.valueOf(str[3]);
            Double blockGrade = Double.valueOf(str[4]);
            Double speedLimit = Double.valueOf(str[5]);
            String infrastructure = str[6];
            Double elevation = Double.valueOf(str[7]);
            String crossing = str[8];
            String switchBlock = str[9];
            String arrowDirection = str[10];
            String stationName = str[11];

            Boolean isUnderground = infrastructure.contains("UNDERGROUND");
            Boolean hasSwitch = infrastructure.contains("SWITCH");
            Boolean hasCrossing = infrastructure.contains("CROSSING");

            //Initialize and add block
            Block myBlock = new Block(this, defaultOccupied, isUnderground, blockLen, blockGrade,
                elevation, speedLimit, stationName, arrowDirection, blockLine,
                blockSection, blockNum, hasSwitch, switchBlock);
            this.addBlock(myBlock);

            if (!stationName.equals("")) {
              this.addStation(blockLine, stationName, myBlock);
            }

            if(hasSwitch) {
              this.addSwitchRoot(switchBlock, myBlock);
            }

            if(!hasSwitch && !switchBlock.equals("")) {
              this.addSwitchLeaf(switchBlock, myBlock);
            }
            if(hasCrossing) {
              this.crossingMap.put(myBlock,new Crossing(this, myBlock));
            }
          }
          initLine = false;
        }
      }catch(IOException|ArrayIndexOutOfBoundsException|NumberFormatException e) {
        if (this.verbose.equals(true)) {
          System.out.println("Finished Reading");
        }
      }
    }

    this.linkBlocks();
    this.handleSwitches();
    this.linkCSVOverride(fOverrideNames);
    this.buildStationHostMap();
    this.buildBlockStationMap();
    this.buildLightsMap();
  }

}
