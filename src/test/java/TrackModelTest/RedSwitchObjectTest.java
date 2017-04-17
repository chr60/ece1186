package TrackModelTest;

import TrackModel.TrackModel;
import TrackModel.Block;
import TrackModel.Switch;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RedSwitchObjectTest {

  private static TrackModel track;
  private static String[] fNames = {"src/test/resources/redline.csv"};
  private static String[] fOverrideNames = {"test-classes/redlinelink.csv"};
    
  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing.
  */
  void init(){
    this.track = new TrackModel("Test");
    this.track.readCSV(this.fNames, this.fOverrideNames);
  }


  @Test
  /**
  * Test switch 7 object use.
  */
  void testSwitch7Object() {
    Block defaultLeafBlock = this.track.getBlock("Red", "J", 53);
    Block altLeafBlock = this.track.getBlock("Red", "N", 66);
    Block rootBlock = this.track.getBlock("Red", "J", 52);
    Block rootBlockBackward = this.track.getBlock("Red", "J", 51);
    Switch trackSwitch = this.track.viewSwitchMap().get(rootBlock.getSwitchBlock());

    assertTrue(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(defaultLeafBlock));
    assertTrue(rootBlock.nextBlockBackward().equals(rootBlockBackward));
    assertTrue(rootBlock.nextBlockForward().equals(defaultLeafBlock));
    assertTrue(trackSwitch.nextBlock(defaultLeafBlock).equals(rootBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(altLeafBlock));


    trackSwitch.setSwitchState(false);
    assertFalse(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(altLeafBlock));
    assertTrue(rootBlock.nextBlockBackward().equals(rootBlockBackward));
    assertTrue(rootBlock.nextBlockForward().equals(altLeafBlock));
    assertTrue(trackSwitch.nextBlock(defaultLeafBlock).equals(defaultLeafBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(rootBlock));
  }

  @Test
  /**
  * Test switch 8 object use.
  */
  void testSwitch8Object() {
    Block defaultLeafBlock = this.track.getBlock("Red", "H", 32);
    Block altLeafBlock = this.track.getBlock("Red", "R", 72);
    Block rootBlock = this.track.getBlock("Red", "H", 33);
    Block rootBlockForward = this.track.getBlock("Red", "H", 34);
    Switch trackSwitch = this.track.viewSwitchMap().get(rootBlock.getSwitchBlock());
    
    assertTrue(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(defaultLeafBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(altLeafBlock));
    assertTrue(rootBlock.nextBlockForward().equals(rootBlockForward));
    assertTrue(rootBlock.nextBlockBackward().equals(defaultLeafBlock));

    trackSwitch.setSwitchState(false);
    assertFalse(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(altLeafBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(rootBlock));
    assertTrue(trackSwitch.nextBlock(defaultLeafBlock).equals(defaultLeafBlock));
    assertTrue(rootBlock.nextBlockForward().equals(rootBlockForward));
    assertTrue(rootBlock.nextBlockBackward().equals(altLeafBlock));
  }

  @Test
  /**
  * Test switch 6 object use.
  */
  void testSwitch6Object() {
    Block defaultLeafBlock = this.track.getBlock("Red", "A", 1);
    Block altLeafBlock = this.track.getBlock("Red", "E", 15);
    Block rootBlock = this.track.getBlock("Red", "F", 16);
    Block rootBlockForward = this.track.getBlock("Red", "F", 17);
    Switch trackSwitch = this.track.viewSwitchMap().get(rootBlock.getSwitchBlock());

    assertTrue(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(defaultLeafBlock).equals(rootBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(altLeafBlock));
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(defaultLeafBlock));
    assertTrue(rootBlock.nextBlockForward().equals(rootBlockForward));
    assertTrue(rootBlock.nextBlockBackward().equals(defaultLeafBlock));

    trackSwitch.setSwitchState(false);
    assertFalse(trackSwitch.switchState());
    assertTrue(trackSwitch.nextBlock(defaultLeafBlock).equals(defaultLeafBlock));
    assertTrue(trackSwitch.nextBlock(altLeafBlock).equals(rootBlock));
    assertTrue(trackSwitch.nextBlock(rootBlock).equals(altLeafBlock));
    assertTrue(rootBlock.nextBlockForward().equals(rootBlockForward));
    assertTrue(rootBlock.nextBlockBackward().equals(altLeafBlock));
  }

}

