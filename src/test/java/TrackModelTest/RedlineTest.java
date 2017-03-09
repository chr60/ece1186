package TrackModelTest;

import TrackModel.TrackModel;
import TrackModel.Block;
import java.util.TreeSet;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
 public class RedlineTest{
	private static String[] stationNames={"YARD","SHADYSIDE","HERRON AVE","SWISSVILLE","PENN STATION",
								"STEEL PLAZA","FIRST AVE","STATION SQUARE","SOUTH HILLS JUNCTION"};
	private static Integer[] rootBlocksNum={16,27,33,38,44,52,9};

	//Move to a Set based interface for easy comparables
	private static TreeSet<Integer> testBlocksRoot = new TreeSet<>(Arrays.asList(rootBlocksNum));
	private static TreeSet<String> testNamesSet = new TreeSet<>(Arrays.asList(stationNames));
	private static TrackModel track;
	private static String[] fNames = {"src/test/resources/redline.csv"};

 	@BeforeEach
 	void init(){
 		String[] fNames = {"src/test/resources/redline.csv"};
 		track = new TrackModel();
 		track.readCSV(fNames);
 	}

 	@Test
 	void testReading(){
 		assertEquals(track.stationList.keySet(), testNamesSet);
 	}

 	@Test
 	/**
 	* Test proper block assignment for switch roots
 	*/
 	void testSwitchRoot(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String blk : track.rootMap.keySet()){
 			treeSetTest.add(track.rootMap.get(blk).blockNum);
 		}
 		assertEquals(treeSetTest,testBlocksRoot);
 	}

 	@Test
 	/**
 	* Test proper leaf assignments for block on the red line
 	*/
 	void testSwitchleaf(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String blk : track.rootMap.keySet()){
 			assertTrue(track.leafMap.get(blk).get(0).blockNum.compareTo(track.leafMap.get(blk).get(1).blockNum)<0);
 		}
 	}

 	@Test
 	/**
 	* Check proper funcitonality of nextBlockForward by switch state on the red line
 	*/
 	void testSwitchNextBlockForward(){
 		for (String blk : track.rootMap.keySet()){
 			Block switchTrue = track.rootMap.get(blk).nextBlockForward();
 			//Since it's true by default, we simply set to false
 			Integer falseInteger = new Integer(0);
 			track.rootMap.get(blk).setSwitchState(falseInteger);
 			Block switchFalse = track.rootMap.get(blk).nextBlockForward();
 			assertFalse(switchTrue.equals(switchFalse));
 		}
 	}

 	@Test
 	/**
 	* Check testing of next block backward on the redline. For a "true" (default switch state),
 	* the nexBlockBackward should return the rootBlock for the lower indexed block. 
 	*/
 	void testNextBlockBackwardDefaultSwitch(){
 		//For the case where we don't toggle switches
 		for (String s : track.leafMap.keySet()){
 			assertTrue(track.leafMap.get(s).get(0).nextBlockBackward().equals(track.rootMap.get(s)));
 			assertTrue(track.leafMap.get(s).get(1).nextBlockBackward().equals(track.leafMap.get(s).get(1)));
 		}
 	}

 	@Test
 	/**
 	* Check testing of next block backwards on the redline. For a "false" (non-default switch state),
 	* the nextBlockBackward should return the rootBlock for the higher indexed block.
 	*/
 	void testNextBlockBackwardFalseSwitch(){
 		//Toggle all the switches
 		for (String s : track.rootMap.keySet()){
 			Integer falseInteger = new Integer(0);
 			track.rootMap.get(s).setSwitchState(falseInteger);
 		}

 		for (String s : track.leafMap.keySet()){
 			assertTrue(track.leafMap.get(s).get(0).nextBlockBackward().equals(track.leafMap.get(s).get(0)));
 			assertTrue(track.leafMap.get(s).get(1).nextBlockBackward().equals(track.rootMap.get(s))); 			
 		}
 	}
 }