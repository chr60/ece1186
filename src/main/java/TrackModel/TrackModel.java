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

	public HashMap<String, Block> switchMap = new HashMap<String, Block>();
	public HashMap<String, Block> rootMap = new HashMap<String, Block>();
	public HashMap<String, ArrayList<Block>> leafMap = new HashMap<String, ArrayList<Block>>();
	public TreeSet<String> stationList = new TreeSet<String>();

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
			for (String sectionKey : this.trackList.get(lineKey).keySet()){
				System.out.print("On section: ");
				System.out.println(sectionKey);

				Set<Integer> blockSet = this.trackList.get(lineKey).get(sectionKey).keySet();
				
				for (Integer blockNum : blockSet){
					System.out.println(this.trackList.get(lineKey).get(sectionKey).get(blockNum).nextBlockForward().blockNum);
				}
			}
		}
	}

	/**
	* Infers the format of the current TrackModel and associated arrow directions to add
	* the nextBlock states to blocks where it may be inferred
	* \todo Refactor to improve efficiency with peakable iterators. 
	*/
	private void inferNextBlock(){
		Block store = null;
		for (String lineKey : this.trackList.keySet()){
			store = null;
			System.out.print("Inferring Line: ");
			System.out.println(lineKey);

			for (String sectionKey : this.trackList.get(lineKey).keySet()){	
				//Reference max and min
				Set<Integer> set = this.trackList.get(lineKey).get(sectionKey).keySet();
				Integer max = Collections.max(set);
				Integer min = Collections.min(set);

				Block minBlock = this.trackList.get(lineKey).get(sectionKey).get(min);
				Block maxBlock = this.trackList.get(lineKey).get(sectionKey).get(max);

				if(store != null){
					store.setNextBlockForward(minBlock);
					minBlock.setNextBlockBackward(store);
				}
				store = maxBlock;

				if (maxBlock.arrowDirection.equals("Head") && minBlock.arrowDirection.equals("Head")){
					Set<Integer> mySet = new TreeSet<Integer>(this.trackList.get(lineKey).get(sectionKey).keySet());
					for(Integer myBlock : mySet){
						Block curBlock = this.trackList.get(lineKey).get(sectionKey).get(myBlock);
						if (minBlock.equals(curBlock)){
							curBlock.setNextBlockForward(this.trackList.get(lineKey).get(sectionKey).get(myBlock+1));
							curBlock.setNextBlockBackward(curBlock);
						}

						else if(maxBlock.equals(curBlock)){
							curBlock.setNextBlockForward(curBlock);
							curBlock.setNextBlockBackward(this.trackList.get(lineKey).get(sectionKey).get(myBlock-1));							
						}

						else{
							curBlock.setNextBlockForward(this.trackList.get(lineKey).get(sectionKey).get(myBlock+1));
							curBlock.setNextBlockBackward(this.trackList.get(lineKey).get(sectionKey).get(myBlock-1));
						}
					}
				}

				else if (maxBlock.arrowDirection.equals("Head") && minBlock.arrowDirection.equals("Tail")){
					Set<Integer> mySet = new TreeSet<Integer>(this.trackList.get(lineKey).get(sectionKey).keySet());

					for (Integer myBlock : mySet){
						Block curBlock = this.trackList.get(lineKey).get(sectionKey).get(myBlock);
						if (minBlock.equals(curBlock)){
							curBlock.setNextBlockForward();
						}
					}
				}
				else if (maxBlock.arrowDirection.equals("Head/Head")){
					maxBlock.setNextBlockForward(maxBlock);
				}
			}
		}
		this.handleSwitches();
	}

	/** Helper function to link nextBlock for switches
	*/
	private void handleSwitches(){
		for (String s : this.rootMap.keySet()){
			//forward case
			this.rootMap.get(s).setNextBlockForward(this.leafMap.get(s).get(0), this.leafMap.get(s).get(1));
			//System.out.println(s);
			//System.out.println(this.rootMap.get(s).nextBlockForward().blockNum);
		}

		for (String s : this.leafMap.keySet()){
			this.leafMap.get(s).get(0).setRootBlock(this.rootMap.get(s));
			this.leafMap.get(s).get(1).setRootBlock(this.rootMap.get(s));
			/*
			System.out.println(s);
			System.out.println("Root " + this.rootMap.get(s).blockNum);
			System.out.println("Leaf 1 " + this.leafMap.get(s).get(0).blockNum);
			System.out.println("Leaf 2 " + this.leafMap.get(s).get(1).blockNum);
			*/
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

					//For safety when parsing headers
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

						//Parse infrastructure string to underground + switch
						Boolean isUnderground = infrastructure.contains("UNDERGROUND");
						Boolean hasSwitch = infrastructure.contains("SWITCH");
						
						
						//Initialize and add block
						Block myBlock = new Block(defaultOccupied, isUnderground, blockLen, blockGrade, 
										elevation, speedLimit, stationName, arrowDirection, blockLine, 
										blockSection, blockNum, hasSwitch, switchBlock);

						this.addBlock(blockLine, blockSection, blockNum, myBlock );

						if (!stationName.equals("")){
							this.stationList.add(stationName);
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
			this.inferNextBlock();
			//this.examineNext();
		}
}
