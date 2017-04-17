package WaysideController;

//Module Imports
import TrackModel.TrackModel;
import TrackModel.Block;
import TrackModel.Switch;
import WaysideController.WS;

//Test Imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Utils
import java.util.ArrayList;

public class WaysideRedTest{
  private static TrackModel track;
  private static WS redWS;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("WaysideRedTest");
    this.track.readCSV(fNames);
    this.redWS = new WS("Red", this.track);

  }

  @Test
  /**
  * Test for properly switching redline switches
  */
  @DisplayName("Validate redline switching to yard")
  void testRedYardSwitch(){
    Block yardSwitchBlock = this.track.getBlock("Red", "C", 9);
    assertTrue(yardSwitchBlock.viewSwitchState());
    this.redWS.manualSwitch(yardSwitchBlock);
    assertFalse(yardSwitchBlock.viewSwitchState());
  }

  @Test
  /**
  * Test for checking list of brokenBlocks from track
  */
  @DisplayName("U77 Broken, C9 Broken, size of broken list > 0")
  void testRedBrokenList(){
    Block yardBlock = this.track.getBlock("Red", "U", 77);
    Block aBlock = this.track.getBlock("Red", "C", 9);
    yardBlock.setBroken(true);
    aBlock.setBroken(true);
    ArrayList<Block> brokenBlocks = redWS.checkForBroken();
    assertTrue(brokenBlocks.size()>0); //size == 2
  }

  @Test
  /**
  * Test for checking list of brokenBlocks from track
  */
  @DisplayName("Nothing Broken, size of broken list == 0")
  void testRedNotBrokenList(){
    ArrayList<Block> brokenBlocks = redWS.checkForBroken();
    assertTrue(brokenBlocks.size()==0);
  }

  @Test
  /**
  * Test for checking switch status
  */
  @DisplayName("Checking switch status of C9, default pos")
  void testSwitchStatusDefault(){
    Block aBlock = this.track.getBlock("Red", "C", 9);
    assertTrue(redWS.switchStatus(aBlock) == 1);
  }

  @Test
  /**
  * Test for checking switch status
  */
  @DisplayName("Checking switch status of C9, switched pos")
  void testSwithStatusSwitched(){
    Block aBlock = this.track.getBlock("Red", "C", 9);
    assertTrue(redWS.switchStatus(aBlock) == 1);
    redWS.manualSwitch(aBlock);
    assertTrue(redWS.switchStatus(aBlock) == 0);
  }

  @Test
  /**
  * Test for making occupancy list for CTC
  */
  @DisplayName("Set Blocks A1 & U77 to Occupied, test in list")
  void testOccList(){
    this.track.getBlock("Red", "A", 1).setOccupied(true);
    this.track.getBlock("Red", "U", 77).setOccupied(true);
    ArrayList<Block> occupancyList = redWS.getOccupancy();
    assertTrue(occupancyList.get(0).getOccupied()==true);
    assertTrue(occupancyList.get(occupancyList.size()-1).getOccupied()==true);
  }

  @Test
  /**
  * Test for setting speed & authority of a block
  */
  @DisplayName("Set Speed and Auth of U77 to: 30 and to Block C9")
  void testSetSpeedAuth(){
    this.track.getBlock("Red", "U", 77).setSuggestedSpeed(new Double(30));
    this.track.getBlock("Red", "U", 77).setAuthority(this.track.getBlock("Red", "C", 9));
    assertTrue(this.track.getBlock("Red", "U", 77).getSuggestedSpeed() == 30);
    assertTrue(this.track.getBlock("Red", "U", 77).getAuthority().equals(this.track.getBlock("Red", "C", 9)));
  }

  @Test
  /**
  * Test for setting speed & authority of a block list
  */
  @DisplayName("Set Speed and Auth List from dummy Blocks")
  void testSetSpeedAuthList(){
    //"Dummy" Creation
    String[] fNames = {"src/test/resources/redline.csv"};
    TrackModel dummyTrack= new TrackModel("dummy");
    dummyTrack.readCSV(fNames);
    dummyTrack.getBlock("Red", "U", 77).setSuggestedSpeed(new Double(30));
    dummyTrack.getBlock("Red", "U", 77).setAuthority(this.track.getBlock("Red", "C", 9));
    ArrayList<Block> listToSet = new ArrayList<Block>();
    listToSet.add(dummyTrack.getBlock("Red", "U", 77));

    //Send to WS
    redWS.setSpeedAuth(listToSet);
    assertTrue(this.track.getBlock("Red", "U", 77).getSuggestedSpeed() == 30);
    assertTrue(this.track.getBlock("Red", "U", 77).getAuthority().equals(this.track.getBlock("Red", "C", 9)));
  }

  @Test
  /**
  * Test for getting Crossing status
  */
  @DisplayName("Set I47 to Active (false), check status with WS before and after")
  void testCrossingStatus(){
    Block crossingBlock = this.track.getBlock("Red","I",47);
    assertTrue(this.track.viewCrossingMap().get(crossingBlock).viewCrossingState());
    assertTrue(redWS.getCrossing(crossingBlock));
    crossingBlock.getAssociatedCrossing().setCrossingState(false);
    assertFalse(this.track.viewCrossingMap().get(crossingBlock).viewCrossingState());
    assertFalse(redWS.getCrossing(crossingBlock));
  }

  @Test
  /**
   * Test for making sure a switch is not occupied before manually switching
   */
  @DisplayName("Make sure switch is not occupied before switching it")
  void occupiedSwitchTest(){
    Block switchBlock = this.track.getBlock("Red", "F", 16);
    assertTrue(switchBlock.viewSwitchState());
    switchBlock.setOccupied(true);
    this.redWS.manualSwitch(switchBlock);
    assertTrue(switchBlock.viewSwitchState());
  }
}
