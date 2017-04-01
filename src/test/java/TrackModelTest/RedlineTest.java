package TrackModel;

import TrackModel.TrackModel;
import TrackModel.Block;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
 public class RedlineTest{
	private static String[] stationNames={"YARD","SHADYSIDE","HERRON AVE","SWISSVILLE","PENN STATION",
								"STEEL PLAZA","FIRST AVE","STATION SQUARE","SOUTH HILLS JUNCTION","YARD"};
	private static Integer[] rootBlocksNum={16,27,33,38,44,52,9};
	private static Integer[] expectedStationBlockNums={7,9,16,21,25,35,45,48,60};
	//Move to a Set based interface for easy comparables
	private static TreeSet<Integer> testBlocksRoot = new TreeSet<>(Arrays.asList(rootBlocksNum));
	private static TreeSet<String> testNamesSet = new TreeSet<>(Arrays.asList(stationNames));
	private static TreeSet<Integer> testExpectedStationBlockNums = new TreeSet<>(Arrays.asList(expectedStationBlockNums));
	private static TrackModel track;
	private static String[] fNames = {"src/test/resources/redline.csv"};

 	@BeforeEach
 	void init(){
 		String[] fNames = {"src/test/resources/redline.csv"};
 		this.track = new TrackModel();
 		this.track.readCSV(fNames);
 	}

 	@Test
 	/**
 	* Test for proper station names reading
 	*/
 	void testReading(){
 		assertEquals(testNamesSet, this.track.viewStationMap().keySet());
 	}

 	@Test
 	/**
 	* Test proper block assignment for switch roots
 	*/
 	void testSwitchRoot(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String switchBlock : this.track.viewRootMap().keySet()){
 			treeSetTest.add(this.track.viewRootMap().get(switchBlock).getBlockNum());
 		}
 		assertEquals(treeSetTest,testBlocksRoot);
 	}

 	@Test
 	/**
 	* Test proper leaf assignments for block on the red line
 	*/
 	void testSwitchleaf(){
 		TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
 		for(String blk : this.track.viewRootMap().keySet()){
 			assertTrue(this.track.viewLeafMap().get(blk).get(0).getBlockNum().compareTo(track.viewLeafMap().get(blk).get(1).getBlockNum())<0);
 		}
 	}

 	@Test
 	/**
 	* Check proper funcitonality of nextBlockForward by switch state on the red line
 	*/
 	void testSwitchNextBlockForward(){
 		for (String blk : this.track.viewRootMap().keySet()){
 			Block switchTrue = this.track.viewRootMap().get(blk).nextBlockForward();
 			//Since it's true by default, we simply set to false
 			Integer falseInteger = new Integer(0);
 			this.track.viewRootMap().get(blk).setSwitchState(falseInteger);
 			Block switchFalse = this.track.viewRootMap().get(blk).nextBlockForward();
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
 		for (String s : this.track.viewLeafMap().keySet()){
 			assertTrue(this.track.viewLeafMap().get(s).get(0).nextBlockBackward().equals(track.viewRootMap().get(s)));
 			assertTrue(this.track.viewLeafMap().get(s).get(1).nextBlockBackward().equals(track.viewLeafMap().get(s).get(1)));
 		}
 	}

 	@Test
 	/**
 	* Check testing of next block backwards on the redline. For a "false" (non-default switch state),
 	* the nextBlockBackward should return the rootBlock for the higher indexed block.
 	*/
 	void testNextBlockBackwardFalseSwitch(){
 		//Toggle all the switches
 		for (String s : this.track.viewRootMap().keySet()){
 			Integer falseInteger = new Integer(0);
 			this.track.viewRootMap().get(s).setSwitchState(falseInteger);
 		}

 		for (String s : this.track.viewLeafMap().keySet()){
 			assertTrue(this.track.viewLeafMap().get(s).get(0).nextBlockBackward().equals(track.viewLeafMap().get(s).get(0)));
 			assertTrue(this.track.viewLeafMap().get(s).get(1).nextBlockBackward().equals(track.viewRootMap().get(s))); 			
 		}
 	}	

 	@Test
 	/**
 	*  Check the functionality of the external blockStation map for use in the train model and 
 	*  train controller
 	*/
 	void testBlockStationMap(){
 		TreeSet<Integer> blockNums = new TreeSet<Integer>();
 		for (Block b : track.viewBlockStationMap().keySet()){
 			blockNums.add(b.getBlockNum());
 		}
 		assertEquals(testExpectedStationBlockNums, blockNums);
 	}

 	@Test
 	/**
 	*	Test the nextBlock functionality in a simple case.
 	*/
 	void testBlockToBlockSimple() {
 		Block startBlock = track.getBlock("Red", "U", new Integer(77));
 		Block endBlock = track.getBlock("Red", "C", new Integer(7));
 		ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
 	}
 }