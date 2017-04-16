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
  private Boolean verbose = true;
  @BeforeEach
  void init(){
    String[] fNames = {"src/test/resources/greenline.csv"};
    this.track = new TrackModel("Test", this.verbose);
    this.track.readCSV(fNames);
  }

  @Test
  /**
  * Test for proper station names reading
  */
  @DisplayName("Test the reading of the green line")
  void testReading(){
    assertEquals(this.track.viewStationMap().get("Green").keySet(), testNamesSet);
  }

  @Test
  /**
  * Test proper block assignment for switch roots
  */
  @DisplayName("Test the switch roots of the green line")
  void testSwitchRoot(){
    TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
    for(String blk : this.track.viewRootMap().keySet()){
      treeSetTest.add(track.viewRootMap().get(blk).blockNum());
    }
    assertEquals(treeSetTest,testBlocksRoot);
  }

  @Test
  /**
  * Test proper leaf assignments for block on the red line
  */
  @DisplayName("Test the switch leafs on the green line")
  void testSwitchleaf(){
    TreeSet<Integer> treeSetTest = new TreeSet<Integer>();
    for(String blk : track.viewRootMap().keySet()){
      assertTrue(this.track.viewLeafMap().get(blk).get(0).blockNum().compareTo(this.track.viewLeafMap().get(blk).get(1).blockNum())<0);
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
      blockNums.add(b.blockNum());
    }
    assertEquals(testExpectedStationBlockNums, blockNums);
  }

  //@Test
  /**
  * Validate no nulls after linking blocks.
  */
  @DisplayName("Test the presence of a nullptr in the track due to incorrect linking")
  void testNullPtrExceptionDefault(){
    for(String l : this.track.trackList.keySet()) {
      for(String s : this.track.trackList.get(l).keySet()) {
        for(Integer b : this.track.trackList.get(l).get(s).keySet()) {
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockForward().blockNum(),null);
          assertNotEquals(this.track.trackList.get(l).get(s).get(b).nextBlockBackward().blockNum(),null);
        }
      }
    }
  }

    //  @Test
     /**
     * Validate track linkage with switch FROM yard
     */
     @DisplayName("Test the track linkage of blocks from yard")
     void testFromYard(){
       Block leafBlock = this.track.getBlock("Green", "J", 61);
       Block yardBlock = this.track.getBlock("Green", "ZZ", 152);
       Block rootBlock = this.track.getBlock("Green", "J", 62);


       //Test J61 forward and backwards
       assertTrue(rootBlock.setSwitchState(-1));
       assertTrue(rootBlock.nextBlockBackward().equals(leafBlock));
       assertTrue(leafBlock.nextBlockBackward().equals(this.track.getBlock("Green", "J", 60)));

       rootBlock.setSwitchState(0);//change switch to yard

       //Test ZZ152 Forward and backwards
       assertFalse(rootBlock.setSwitchState(-1));
       assertTrue(yardBlock.nextBlockForward().equals(rootBlock));
       assertTrue(yardBlock.nextBlockBackward().equals(yardBlock));

       //Test root block when switch is false
       assertTrue(rootBlock.nextBlockBackward().equals(yardBlock));
       assertTrue(rootBlock.nextBlockForward().equals(this.track.getBlock("Green", "K", 63)));

       rootBlock.setSwitchState(0);//change switch to !yard
       assertTrue(rootBlock.nextBlockBackward().equals(leafBlock));
       assertTrue(rootBlock.nextBlockForward().equals(this.track.getBlock("Green", "K", 63)));
      }
}
