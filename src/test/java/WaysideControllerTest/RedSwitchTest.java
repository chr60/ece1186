package WaysideController;

import TrackModel.TrackModel;
import TrackModel.Block;
import WaysideController.WS;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RedSwitchTest{
  private static TrackModel track;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("WSTest");
    this.track.readCSV(fNames);
  }

  @Test
  /**
  * Test for properly switching redline switch to yard
  */
  @DisplayName("Validate redline switching to yard")
  void testRedYardSwitch(){
    WS redWS = new WS("Red", this.track);
    Block yardSwitchBlock = this.track.getBlock("Red", "C", 9);
    assertTrue(yardSwitchBlock.setSwitchState(-1));
    redWS.manualSwitch(yardSwitchBlock);
    assertFalse(yardSwitchBlock.setSwitchState(-1));
  }
}
