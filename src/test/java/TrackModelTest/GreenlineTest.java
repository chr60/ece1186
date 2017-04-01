package TrackModel;

import TrackModel.TrackModel;
import TrackModel.Block;
import java.util.TreeSet;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
 public class GreenlineTest{
	private static String[] stationNames={"OVERBROOK", "UNIVERSITY OF PITTSBURGH", "CENTRAL", "POPLAR", "CASTLE SHANNON", 
										"DORMONT", "SOUTH BANK", "WHITED", "MT LEBANON", "INGLEWOOD", "PIONEER", "EDGEBROOK", 
										"GLENBURY","YARD"};
	private static Integer[] rootBlocksNum={62,13,28,57,77,85};
	private static Integer[] expectedStationBlockNums={2,9,16,22,31,39,48,57,65,73,77,88,96,105,114,123,132,141,151,152};
	//Move to a Set based interface for easy comparables
	private static TreeSet<Integer> testBlocksRoot = new TreeSet<>(Arrays.asList(rootBlocksNum));
	private static TreeSet<String> testNamesSet = new TreeSet<>(Arrays.asList(stationNames));
	private static TreeSet<Integer> testExpectedStationBlockNums = new TreeSet<>(Arrays.asList(expectedStationBlockNums));
	private static TrackModel track;
	private static String[] fNames = {"src/test/resources/greenline.csv"};

 	@BeforeEach
 	void init(){
 		String[] fNames = {"src/test/resources/greenline.csv"};
 		track = new TrackModel();
 		track.readCSV(fNames);
 	}

 	//@Test
 	/**
 	* Test for proper station names reading
 	*/
 	void testReading(){
 		assertEquals(track.viewStationMap().keySet(), testNamesSet);
 	}

 	//@Test
 	/**
 	* Test proper block assignment for switch roots
 	*/
 	void testSwitchRoot(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String blk : track.viewRootMap().keySet()){
 			treeSetTest.add(track.viewRootMap().get(blk).blockNum);
 		}
 		assertEquals(treeSetTest,testBlocksRoot);
 	}

 	//@Test
 	/**
 	* Test proper leaf assignments for block on the red line
 	*/
 	void testSwitchleaf(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String blk : track.viewRootMap().keySet()){
 			assertTrue(track.viewLeafMap().get(blk).get(0).blockNum.compareTo(track.viewLeafMap().get(blk).get(1).blockNum)<0);
 		}
 	}

 	//@Test
 	/**
 	* Check proper funcitonality of nextBlockForward by switch state on the green line
 	*/
 	void testSwitchNextBlockForward(){
 		for (String blk : track.viewRootMap().keySet()){
 			Block switchTrue = track.viewRootMap().get(blk).nextBlockForward();
 			//Since it's true by default, we simply set to false
 			Integer falseInteger = new Integer(0);
 			track.viewRootMap().get(blk).setSwitchState(falseInteger);
 			Block switchFalse = track.viewRootMap().get(blk).nextBlockForward();
 			assertFalse(switchTrue.equals(switchFalse));
 		}
 	}

 	//@Test
 	/**
 	* Check testing of next block backward on the redline. For a "true" (default switch state),
 	* the nexBlockBackward should return the rootBlock for the lower indexed block. 
 	*/
 	void testNextBlockBackwardDefaultSwitch(){
 		//For the case where we don't toggle switches
 		for (String s : track.viewLeafMap().keySet()){
 			assertTrue(track.viewLeafMap().get(s).get(0).nextBlockBackward().equals(track.viewRootMap().get(s)));
 			assertTrue(track.viewLeafMap().get(s).get(1).nextBlockBackward().equals(track.viewLeafMap().get(s).get(1)));
 		}
 	}
 	
 	//@Test
 	/**
 	* Check testing of next block backwards on the redline. For a "false" (non-default switch state),
 	* the nextBlockBackward should return the rootBlock for the higher indexed block.
 	*/
 	void testNextBlockBackwardFalseSwitch(){
 		//Toggle all the switches
 		for (String s : track.viewRootMap().keySet()){
 			Integer falseInteger = new Integer(0);
 			track.viewRootMap().get(s).setSwitchState(falseInteger);
 		}

 		for (String s : track.viewLeafMap().keySet()){
 			assertTrue(track.viewLeafMap().get(s).get(0).nextBlockBackward().equals(track.viewLeafMap().get(s).get(0)));
 			assertTrue(track.viewLeafMap().get(s).get(1).nextBlockBackward().equals(track.viewRootMap().get(s))); 			
 		}
 	}	

  	//@Test
 	/**
 	*  Check the functionality of the external blockStation map for use in the train model and 
 	*  train controller
 	*/
 	void testBlockStationMap(){
 		TreeSet<Integer> blockNums = new TreeSet<Integer>();
 		for (Block b : track.viewBlockStationMap().keySet()){
 			blockNums.add(b.blockNum);
 		}
 		assertEquals(testExpectedStationBlockNums, blockNums);
 	}

 }