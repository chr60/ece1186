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
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("Test");
    this.track.readCSV(fNames);
  }

  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate some reading of a trackModel from a csv")
  void testReading(){
    assertEquals(testNamesSet, this.track.viewStationMap().keySet());
  }

  @Test
  /**
  * Test proper block assignment for switch roots.
  */
  @DisplayName("Validate proper switch roots of a track")
  void testSwitchRoot(){
    TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
    for(String switchBlock : this.track.viewRootMap().keySet()){
      treeSetTest.add(this.track.viewRootMap().get(switchBlock).blockNum());
    }
    assertEquals(treeSetTest,testBlocksRoot);
  }

  @Test
  /**
  * Test proper leaf assignments for block on the red line.
  */
  @DisplayName("Validate correct switch leaves")
  void testSwitchleaf(){
    TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
    for(String blk : this.track.viewRootMap().keySet()){
      assertTrue(this.track.viewLeafMap().get(blk).get(0).blockNum().compareTo(track.viewLeafMap().get(blk).get(1).blockNum())<0);
    }
  }

  @Test
  /**
  * Check proper funcitonality of nextBlockForward by switch state on the red line.
  */
  @DisplayName("Validate the nextBlockForward through a switch")
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
  @DisplayName("Validate switching and nextBlockBackward default switch state")
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
  @DisplayName("Validate Switching and nextBlockBackward functionality")
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
  *  train controller.
  */
  @DisplayName("Test proper block station map of a track")
  void testBlockStationMap(){
    TreeSet<Integer> blockNums = new TreeSet<Integer>();
    for (Block b : track.viewBlockStationMap().keySet()){
      blockNums.add(b.blockNum());
    }
    assertEquals(testExpectedStationBlockNums, blockNums);
  }

  @Test
  /**
  * Test the pathing functionality in a simple case expecting forward.
  */
  @DisplayName("Simple pathing test 1")
  void testBlockToBlockSimpleForward() {
    Block startBlock = track.getBlock("Red", "H", new Integer(24));
    Block endBlock = track.getBlock("Red", "H", new Integer(25));
    ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
    ArrayList<Block> testArr = new ArrayList<Block>();

    testArr.add(startBlock);
    testArr.add(endBlock);
    assertEquals(testArr,paths.get(0));
  }

  @Test
  /**
  * Test the pathing functionality in a simple case expecting backward.
  */
  @DisplayName("Simple pathing test 2")
  void testBlockToBlockSimpleBackward() {
    Block startBlock = track.getBlock("Red", "H", new Integer(25));
    Block endBlock = track.getBlock("Red", "H", new Integer(24));
    ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
    ArrayList<Block> testArr = new ArrayList<Block>();

    testArr.add(startBlock);
    testArr.add(endBlock);
    assertEquals(testArr,paths.get(0));
  }

  @Test
  /**
  * Test the ability of the path planning to deal with switching conditions.
  */
  @DisplayName("Switching pathing test 1")
  void testPathingSwitching() {
    Block startBlock = track.getBlock("Red","U",new Integer(77));
    Block endBlock  = track.getBlock("Red","C",new Integer(7));
    ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
    ArrayList<Block> testArr = new ArrayList<Block>();
  }
  @Test
  /**
  * MBO pathing test 1
  */
  @DisplayName("MBO Pathing Test 1")
  void testMBOOne() {
    Block startBlock = track.getBlock("Red", "C", new Integer(7));
    Block endBlock = track.getBlock("Red", "F", new Integer(16));
    ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
    ArrayList<Block> testArr = new ArrayList<Block>();
    testArr.add(track.getBlock("Red", "C", new Integer(7)));
    testArr.add(track.getBlock("Red", "B", new Integer(6)));
    testArr.add(track.getBlock("Red", "B", new Integer(5)));
    testArr.add(track.getBlock("Red", "B", new Integer(4)));
    testArr.add(track.getBlock("Red", "A", new Integer(3)));
    testArr.add(track.getBlock("Red", "A", new Integer(2)));
    testArr.add(track.getBlock("Red", "A", new Integer(1)));
    testArr.add(track.getBlock("Red", "F", new Integer(16)));
    assertEquals(testArr, paths.get(0));
}

  //@Test
  /**
  * MBO pathing test 2
  */
  @DisplayName("MBO Pathing Test 2")
  void testMBOTwo() {
    Block startBlock = track.getBlock("Red", "H", new Integer(25));
    Block endBlock = track.getBlock("Red", "H", new Integer(35));
    ArrayList<ArrayList<Block>> paths = track.blockToBlock(startBlock, endBlock);
    ArrayList<Block> testArr = new ArrayList<Block>();
    System.out.println("MBO 2");


    // First possible path
    testArr.add(track.getBlock("Red", "H", new Integer(25)));
    testArr.add(track.getBlock("Red", "H", new Integer(26)));
    testArr.add(track.getBlock("Red", "H", new Integer(27)));
    testArr.add(track.getBlock("Red", "H", new Integer(28)));
    testArr.add(track.getBlock("Red", "H", new Integer(29)));
    testArr.add(track.getBlock("Red", "H", new Integer(30)));
    testArr.add(track.getBlock("Red", "H", new Integer(31)));
    testArr.add(track.getBlock("Red", "H", new Integer(32)));
    testArr.add(track.getBlock("Red", "H", new Integer(33)));
    testArr.add(track.getBlock("Red", "H", new Integer(34)));
    testArr.add(track.getBlock("Red", "H", new Integer(35)));
    /*
    // Second possible path
    testArr.add(track.getBlock("Red", "H", new Integer(25)));
    testArr.add(track.getBlock("Red", "H", new Integer(26)));
    testArr.add(track.getBlock("Red", "H", new Integer(27)));
    testArr.add(track.getBlock("Red", "T", new Integer(76)));
    testArr.add(track.getBlock("Red", "S", new Integer(75)));
    testArr.add(track.getBlock("Red", "S", new Integer(74)));
    testArr.add(track.getBlock("Red", "S", new Integer(73)));
    testArr.add(track.getBlock("Red", "R", new Integer(72)));
    testArr.add(track.getBlock("Red", "H", new Integer(33)));
    testArr.add(track.getBlock("Red", "H", new Integer(34)));
    testArr.add(track.getBlock("Red", "H", new Integer(35)));
    System.out.println("paths:");
    System.out.print(paths.get(0));
    */
    assertEquals(testArr, paths.get(0));
  }

  @Test
  /**
  * Validate no nulls after linking blocks.
  */
  @DisplayName("Test the presence of a nullptr in the track due to incorrect linking")
  void testNullPtrExceptionDefault() {
    for(String l : this.track.trackList.keySet()) {
      for(String s : this.track.trackList.get(l).keySet()) {
        for(Integer b : this.track.trackList.get(l).get(s).keySet()) {
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockForward().blockNum,null);
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockBackward().blockNum,null);
        }
      }
    }
  }

  @Test
  /**
  * Validate no nulls after linking blocks and switching all switches.
  */
  @DisplayName("Test the presence of a nullptr in the track due to incorrect linking under switched condition")
  void testNullPtrExceptionSwitched() {
    for(String s : this.track.viewRootMap().keySet()) {
      Integer falseInt = new Integer(0);
      this.track.viewRootMap().get(s).setSwitchState(falseInt);
    }

    for (String l : this.track.trackList.keySet()) {
      for (String s : this.track.trackList.get(l).keySet()) {
        for(Integer b : this.track.trackList.get(l).get(s).keySet()) {
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockForward().blockNum,null);
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockBackward().blockNum,null);
        }
      }
    }
  }

  @Test
  /**
  * Test the functionality of the brokenList
  */
  @DisplayName("Broken List Test--expecting false")
  void testBrokenListFalse() {
    ArrayList<Block> brokenListTest = new ArrayList<Block>();
    Block first = this.track.getBlock("Red","A",new Integer(1));
    Block second = this.track.getBlock("Red","U",new Integer(77));
    brokenListTest.add(first);
    brokenListTest.add(second);
    assertFalse(brokenListTest.equals(this.track.getBrokenBlocks("Red")));

    this.track.getBlock("Red","A",new Integer(1)).setBroken(true);
    this.track.getBlock("Red","U",new Integer(77)).setBroken(true);

  }

  @Test
  /**
  * Test the functionality of the brokenList
  */
  @DisplayName("Broken List Test--expecting true")
  void testBrokenListTrue() {
    ArrayList<Block> brokenListTest = new ArrayList<Block>();
    Block first = this.track.getBlock("Red","A",new Integer(1));
    Block second = this.track.getBlock("Red","U",new Integer(77));
    brokenListTest.add(first);
    brokenListTest.add(second);


    this.track.getBlock("Red","A",new Integer(1)).setBroken(true);
    this.track.getBlock("Red","U",new Integer(77)).setBroken(true);
    assertTrue(brokenListTest.equals(this.track.getBrokenBlocks("Red")));


  }
 }
