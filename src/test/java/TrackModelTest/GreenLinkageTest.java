package TrackModelTest;

import TrackModel.Block;
import TrackModel.Switch;
import TrackModel.TrackModel;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GreenLinkageTest {
  
  private static String line = "Green";
  private static Integer[] rootBlocksNum={16,27,33,38,44,52,9};
  private static Integer[] expectedStationBlockNums={7,16,21,25,35,45,48,60,77};
  private static TrackModel track;
  private static String[] fNames = {"src/test/resources/greenline.csv"};
  private static String[] fOverrideNames = {"src/test/resources/greenlinelink.csv"};
  private Boolean verbose = false;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing.
  */
  void init(){
    this.track = new TrackModel("Test", verbose);
    this.track.readCSV(this.fNames, this.fOverrideNames);
  }

  @Test
  /**
  * Test the successful override state on the default read-in on the redline.
  */
  @DisplayName("Validates the override of 2")
  void overrideTest() {
    Block sourceBlock = this.track.getBlock("Green", "A", 2);
    Block nextBlockForward = this.track.getBlock("Green", "A", 3);
    Block nextBlockBackward = this.track.getBlock("Green", "A", 2);

   
    assertEquals(sourceBlock.nextBlockForward().blockNum(), nextBlockForward.blockNum());
    assertEquals(sourceBlock.nextBlockBackward().blockNum(), nextBlockBackward.blockNum());
  }
}