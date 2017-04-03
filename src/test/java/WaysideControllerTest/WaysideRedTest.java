package WaysideController;

//Module Imports
import TrackModel.TrackModel;
import TrackModel.Block;
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
    assertTrue(yardSwitchBlock.setSwitchState(-1));
    this.redWS.manualSwitch(yardSwitchBlock);
    assertFalse(yardSwitchBlock.setSwitchState(-1));
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
}
