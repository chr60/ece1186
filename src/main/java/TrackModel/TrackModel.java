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

public class TrackModel {

	public  HashMap<String,HashMap<String, HashMap<Integer, Block>>> trackList =
		new HashMap<String,HashMap<String, HashMap<Integer, Block>>>();

	HashMap<String, Block> rootMap = new HashMap<String, Block>();
	HashMap<String, ArrayList<Block>> leafMap = new HashMap<String, ArrayList<Block>>();
	HashMap<String, ArrayList<Block>> stationList = new HashMap<String, ArrayList<Block>>();
	HashMap<String, Station> stationHostList = new HashMap<String, Station>();
	HashMap<Block, Station> blockStationMap = new HashMap<Block, Station>();

	/**
	* Simplicity wrapper to return a block on the track given the parameters
	* @param line Line of the block to be looked up
	* @param section Section of the block to be looked up
	* @param blockNum Number of the block to be looked up
	*/
	public Block getBlock(String line, String section, Integer blockNum){
		return trackList.get(line).get(section).get(blockNum);
	}

	/**
	* Allows viewing of the station map to other modules
	* @return HashMap<String, ArrayList<Block>>
	*/
	public HashMap<String, ArrayList<Block>> viewStationMap(){
		return this.stationList;
	}

	/**
	* Allows viewing of leaf nodes of the switches to other modules
	* @return HashMap<String, ArrayList<Block>> of the leafmap
	*/
	public HashMap<String, ArrayList<Block>> viewLeafMap(){
		return this.leafMap;
	}

	/**
	* Allows viewing of the blockStationMap by other modules
	* @return HashMap<Block, Station>
	*/
	public HashMap<Block, Station> viewBlockStationMap(){
		return this.blockStationMap;
	}

	public HashMap<String, Block> viewRootMap(){
		return this.rootMap;
	}
	/**
	* Adds a selected block to the TrackModel. Expects a valid Block object.
	*
	* @param blockLine Name of the line being added
	* @param blockSection Name of the section the block belongs to
	* @param blockNum Number of the block within a line
	* @param Block The block to be added.
	*/
	private void addBlock(String blockLine,String blockSection, Integer blockNum, Block block){
		if (!this.trackList.containsKey(blockLine)){
			this.trackList.put(blockLine, new HashMap<String,HashMap<Integer,Block>>());
		}
		if (!this.trackList.get(blockLine).containsKey(blockSection)){
			this.trackList.get(blockLine).put(blockSection, new HashMap<Integer, Block>());
		}
		this.trackList.get(blockLine).get(blockSection).put(blockNum, block);
	}
	
	/**
	* Adds a station-block pair to the stationList. 
	* @param stationName station to be added to the stationList
	* @param stationLocation the block location of an point where the station interacts with the block
	*/
	private void addStation(String stationName, Block stationLocation){
		if (!this.stationList.containsKey(stationName)){
			this.stationList.put(stationName, new ArrayList<Block>());
		}
		this.stationList.get(stationName).add(stationLocation);
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
	*	Links blocks across block and sections
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

					if (curBlock.equals(maxBlock)){
						this.trackList.get(lineKey).get(sectionKey).get(blk).setNextBlockForward();
						prevBlock.setNextBlockForward(minBlock);
						prevBlock = maxBlock;
					}
					else if (!minBlock.equals(maxBlock)){
						curBlock.setNextBlockForward(this.trackList.get(lineKey).get(sectionKey).get(blk+1));
					}

				}
			}
		}
	}

	/**
	* Build a map for storing the blocks and station for use by the train controller and
	* train model
	*/
	private void buildBlockStationMap(){
		for(String s : stationList.keySet()){
			for(Block b : stationList.get(s))
				if(!this.blockStationMap.containsKey(s)){
					this.blockStationMap.put(b, this.stationHostList.get(s));
				}
		}
	}

	/** 
	*Build the listing of the host station list for external consumption
	*/
	private void buildStationHostList(){
		for (String stationName : this.stationList.keySet()){
			Station myStation = new Station(stationName, this.stationList.get(stationName));
			this.stationHostList.put(stationName, myStation);
		}
	}

	/** Helper function to link nextBlock for switches
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
	* @param String[] fNames: filenames of the csv's of to read in
	* @param TrackModel track: track to have the given fNames to be added to
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

						this.addBlock(blockLine, blockSection, blockNum, myBlock );

						if (!stationName.equals("")){
							this.addStation(stationName, myBlock);
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
			this.buildStationHostList();
			this.buildBlockStationMap();
		}
}
