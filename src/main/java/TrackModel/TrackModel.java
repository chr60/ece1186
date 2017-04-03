package TrackModel;

/** Class model and some associated utility functions for storing the TrackModel
* @author Michael
*/
import java.lang.NumberFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

public class TrackModel implements Serializable{

    public String trackScope;

    public TrackModel(String scope){
        this.trackScope = scope;
    }

    @Deprecated public TrackModel(){
        System.out.println("WARNING! This initializaaiton of a track model will be deprecated in the future.");
    }


    public HashMap<String,HashMap<String, HashMap<Integer, Block>>> trackList =
        new HashMap<String,HashMap<String, HashMap<Integer, Block>>>();

    HashMap<String, Block> rootMap = new HashMap<String, Block>();
    HashMap<String, ArrayList<Block>> leafMap = new HashMap<String, ArrayList<Block>>();
    HashMap<String,HashMap<String, ArrayList<Block>>> stationList = new HashMap<String,HashMap<String, ArrayList<Block>>>();
    HashMap<String, Station> stationHostMap = new HashMap<String, Station>();
    HashMap<Block, Station> blockStationMap = new HashMap<Block, Station>();
    HashMap<Block, Crossing> crossingMap = new HashMap<Block, Crossing>();

    /**
    * Simplicity wrapper to return a non-aliased block on the track given the parameters
    * @param line Line of the block to be looked up
    * @param section Section of the block to be looked up
    * @param blockNum Number of the block to be looked up
    */
    public Block getBlock(String line, String section, Integer blockNum){
        return this.trackList.get(line).get(section).get(blockNum);
    }

    /**
    * Returns the occupancy of a section on a given line.
    * @param the line to be searched
    * @param the section to be searched
    */
    public Boolean sectionOccupancy(String line, String section){
        for (Integer b : this.trackList.get(line).get(section).keySet()) {
            if(this.trackList.get(line).get(section).get(b).getOccupied()){
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
    public ArrayList<Block> getBrokenBlocks(String line){
        ArrayList<Block> brokenList = new ArrayList<Block>();
        for (String section : this.trackList.get(line).keySet()) {
            for(Integer blk : this.trackList.get(line).get(section).keySet() ) {
                if (this.trackList.get(line).get(section).get(blk).getBroken()){
                    brokenList.add(this.trackList.get(line).get(section).get(blk));
                }
            }
        }
        return brokenList;
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
    public TrackModel view(){
        return this;
    }

    /**
    * Returns a non-aliased section of blocks.
    * @param line line the section is on
    * @param section section being requested
    */
    public HashMap<Integer, Block> getSection(String line, String section){
        return this.trackList.get(line).get(section);
    }

    /**
    * Allows viewing of the trackList to other modules, implemented as a copy method
    * @return HashMap<String, HashMap<String,HashMap<Integer,Block>>>
    */
    public HashMap<String,HashMap<String,HashMap<Integer,Block>>> viewTrackList(){
        return new HashMap<String,HashMap<String,HashMap<Integer,Block>>>(trackList);
    }

    /**
    * Returns a block-to-block list of lists that details the possible paths from a block to block. Does not permit
    * revisiting a block from within this function
    * @param block that you start at
    * @param blcok that you end at
    */
    public ArrayList<ArrayList<Block>> blockToBlock(Block startBlock, Block endBlock){
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

        for(Block b : visited) {
            //System.out.println(b.blockNum());
        }
        if(currBlock.equals(endBlock)) {
            //System.out.println("DONE");
            possiblePaths.add(visited);
            return possiblePaths;
        }

        possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockBackward(), endBlock);
        //System.out.println(possiblePaths.size());
        possiblePaths = blockPath(possiblePaths, new ArrayList<Block>(visited), currBlock.nextBlockForward(), endBlock);
        //System.out.println(possiblePaths.size());
        //path logic
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
    public HashMap<String,HashMap<String, ArrayList<Block>>> viewStationMap(){
        return new HashMap<String,HashMap<String, ArrayList<Block>>>(this.stationList);
    }

    /*
    * Allows viewing of the crossingMap from other modules, implemented as a copy method
    * @return HashMap<Block, Crossing>
    */
    public HashMap<Block, Crossing> viewCrossingMap(){
        return new HashMap<Block, Crossing>(this.crossingMap);
    }

    /**
    * Allows viewing of leaf nodes of the switches to other modules
    * @return HashMap<String, ArrayList<Block>> of the leafmap
    */
    public HashMap<String, ArrayList<Block>> viewLeafMap(){
        return new HashMap<String, ArrayList<Block>>(this.leafMap);
    }

    /**
    * Allows viewing of the blockStationMap by other modules
    *
    * @return HashMap<Block, Station>
    */
    public HashMap<Block, Station> viewBlockStationMap(){
        return new HashMap<Block, Station>(this.blockStationMap);
    }

    /**
    * Allows viewing of the rootMap by other modules
    *
    * @return Hash<String, Block>
    */
    public HashMap<String, Block> viewRootMap(){
        return new HashMap<String, Block>(this.rootMap);
    }

    /**
    * Adds a selected block to the TrackModel. Expects a valid Block object.
    *
    * @param block The block to be added.
    */
    private void addBlock(Block block){

        if (!this.trackList.containsKey(block.blockLine)){
            this.trackList.put(block.blockLine, new HashMap<String,HashMap<Integer,Block>>());
        }
        if (!this.trackList.get(block.blockLine).containsKey(block.blockSection)){
            this.trackList.get(block.blockLine).put(block.blockSection, new HashMap<Integer, Block>());
        }
        this.trackList.get(block.blockLine).get(block.blockSection).put(block.blockNum, block);
    }

    /**
    * Adds a station-block pair to the stationList.
    * @param stationName station to be added to the stationList
    * @param stationLocation the block location of an point where the station interacts with the block
    */
    private void addStation(String line, String stationName, Block stationLocation){
        if(!this.stationList.containsKey(line)){
            this.stationList.put(line, new HashMap<String, ArrayList<Block>>());
        }
        if (!this.stationList.get(line).containsKey(stationName)){
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
    private void addSwitchRoot(String rootBlockString, Block rootBlock){
        if (!this.rootMap.containsKey(rootBlockString)){
            this.rootMap.put(rootBlockString, rootBlock);
        }
    }

    /**
    * Add the leaf of a switch to the list.
    * @param the string linking the block
    * @param the block to be referenced
    */
    private void addSwitchLeaf(String leafBlockString, Block leafBlock){
        if (!this.leafMap.containsKey(leafBlockString)){
            this.leafMap.put(leafBlockString, new ArrayList<Block>());
        }
        this.leafMap.get(leafBlockString).add(leafBlock);
    }

    /**
    * A small function to view the next and previous blocks of a track model.
    */
    private void examineNext(){
        for (String lineKey : this.trackList.keySet()){
            System.out.print("Examining Line: ");
            System.out.println(lineKey);
            //lineKey = "Red";
            Set<String> sectionKeySet = this.trackList.get(lineKey).keySet();
            ArrayList<String> sectionKeyList = new ArrayList(sectionKeySet);
            Collections.sort(sectionKeyList);

            for (String sectionKey : sectionKeyList){
                System.out.print("On section: ");
                System.out.println(sectionKey);

                Set<Integer> blockSet = this.trackList.get(lineKey).get(sectionKey).keySet();

                for (Integer blockNum : blockSet){
                    //System.out.println(this.trackList.get(lineKey).get(sectionKey).get(blockNum).nextBlockForward().blockNum);
                }

            }
        }
    }

    /**
    *   Links blocks across block and sections.
    */
    private void linkBlocks(){
        for (String lineKey : this.trackList.keySet()){
            Block prevBlock = null;
            Set<String> sectionKeySet = this.trackList.get(lineKey).keySet();
            ArrayList<String> sortedSectionKeyList = new ArrayList(sectionKeySet);
            Collections.sort(sortedSectionKeyList);

            for (String sectionKey : sortedSectionKeyList){
                for(Integer blk : this.trackList.get(lineKey).get(sectionKey).keySet()){

                    Integer max = Collections.max(this.trackList.get(lineKey).get(sectionKey).keySet());
                    Integer min = Collections.min(this.trackList.get(lineKey).get(sectionKey).keySet());

                    Block minBlock = this.trackList.get(lineKey).get(sectionKey).get(min);
                    Block maxBlock = this.trackList.get(lineKey).get(sectionKey).get(max);
                    Block curBlock = this.trackList.get(lineKey).get(sectionKey).get(blk);
                    if (prevBlock == null){
                        prevBlock = maxBlock;
                    }
                    else {
                        minBlock.setNextBlockBackward(prevBlock);
                    }

                    if (curBlock.equals(maxBlock)){
                        this.trackList.get(lineKey).get(sectionKey).get(blk).setNextBlockForward();
                        this.trackList.get(lineKey).get(sectionKey).get(blk)
                            .setNextBlockBackward(this.trackList.get(lineKey).get(sectionKey).get(blk-1));
                        prevBlock.setNextBlockForward(minBlock);
                        prevBlock = maxBlock;
                    }
                    else if (!minBlock.equals(maxBlock)){
                        curBlock.setNextBlockForward(this.trackList.get(lineKey).get(sectionKey).get(blk+1));
                        curBlock.setNextBlockBackward(this.trackList.get(lineKey).get(sectionKey).get(blk-1));
                    }

                }
            }
        }
    }

    /**
    * Build a map for storing the blocks and station for use by the train controller and.
    * train model
    */
    private void buildBlockStationMap(){
        for(String l : stationList.keySet()){
            for(String s : stationList.get(l).keySet()){
                for(Block b : stationList.get(l).get(s))
                    if(!this.blockStationMap.containsKey(s)){
                        this.blockStationMap.put(b, this.stationHostMap.get(s));
                    }
            }
        }
    }

    /**
    *Build the listing of the host station list for external consumption.
    */
    private void buildStationHostMap(){
        for(String l : this.stationList.keySet()){
            for (String stationName : this.stationList.get(l).keySet()){
                Station myStation = new Station(stationName, this.stationList.get(l).get(stationName));
                this.stationHostMap.put(stationName, myStation);
            }
        }
    }

    /** Helper function to link nextBlock for switches.
    */
    private void handleSwitches(){

        for (String s : this.rootMap.keySet()){

            if(this.rootMap.get(s).blockLine.equals("Red")){
                this.rootMap.get(s).setNextBlockForward(this.leafMap.get(s).get(0), this.leafMap.get(s).get(1));
            }

        }

        for (String s : this.leafMap.keySet()){

            //It is safe to override red-line switches, so we do that first
            if(this.leafMap.get(s).get(0).blockLine.equals("Red")){
                this.leafMap.get(s).get(0).setRootBlock(this.rootMap.get(s));
            }
            if (this.leafMap.get(s).get(0).blockLine.equals("Red")){
                this.leafMap.get(s).get(1).setRootBlock(this.rootMap.get(s));
            }
        }
    }

    /**
    * Helper function for reading the information from the excel-dumped CSV
    *
    * @param fNames: filenames of the csv's of to read in
    */
    public void readCSV(String[] fNames){

        String line = "";
        String delimiter = ",";
        Boolean defaultOccupied = false;

        for (String s : fNames){
            Boolean initLine = true; //Allows for graceful failure of reading multiple csv's without exiting
            try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
                while ((line = reader.readLine()) != null){
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
                        String switchBlock = str[9];
                        String arrowDirection = str[10];
                        String stationName = str[11];

                        Boolean isUnderground = infrastructure.contains("UNDERGROUND");
                        Boolean hasSwitch = infrastructure.contains("SWITCH");


                        //Initialize and add block
                        Block myBlock = new Block(this, defaultOccupied, isUnderground, blockLen, blockGrade,
                                        elevation, speedLimit, stationName, arrowDirection, blockLine,
                                        blockSection, blockNum, hasSwitch, switchBlock);

                        this.addBlock(myBlock);

                        if (!stationName.equals("")){
                            this.addStation(blockLine, stationName, myBlock);
                        }

                        if(hasSwitch){
                            this.addSwitchRoot(switchBlock, myBlock);
                        }

                        if(!hasSwitch && !switchBlock.equals("")){
                            this.addSwitchLeaf(switchBlock, myBlock);
                        }

                    }
                    initLine = false;
                }
            }catch(IOException|ArrayIndexOutOfBoundsException|NumberFormatException e){
                    System.out.println("Finished Reading");
                }
            }
            this.linkBlocks();
            this.handleSwitches();
            this.buildStationHostMap();
            this.buildBlockStationMap();
        }
}
