package WaysideController;

//Module Imports
import TrackModel.TrackModel;
import TrackModel.Block;
import WaysideController.WS;
import WaysideController.PLC;

//Test Imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Utils
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.script.ScriptException;


public class PLCRedTest{

  private static TrackModel track;
  //private static WS redWS;
  private static PLC plc;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("WaysideRedTest");
    this.track.readCSV(fNames);
    //this.redWS = new WS("Red", this.track);
    this.plc = new PLC(this.track, new File("src/test/resources/redline.plc"), new String("Red"));
  }

  @Test
  /**
  * Test PLC returning valid fields for occupancy - Yard
  */
  @DisplayName("Validate Yard Block Occupied, setSwitchState to 0")
  void testRedYardSwitch() throws IOException, ScriptException{
    assertTrue(this.track.getBlock("Red", "C", 9).setSwitchState(-1));
    this.track.getBlock("Red", "U", 77).setOccupied(true);
    this.plc.parse();
    this.plc.runSwitchPLC();
    assertFalse(this.track.getBlock("Red", "C", 9).setSwitchState(-1));
  }

  @Test
  /**
  * Test PLC returning valid fields for occupancy - Bottom loop
  */
  @DisplayName("Validate N Section Occupied, setSwitchState to 0")
  void testRedBottomLoopSwitch() throws IOException, ScriptException{
    assertTrue(this.track.getBlock("Red", "J", 52).setSwitchState(-1));
    this.track.getBlock("Red", "N", 64).setOccupied(true);
    this.plc.parse();
    this.plc.runSwitchPLC();
    assertFalse(this.track.getBlock("Red", "J", 52).setSwitchState(-1));
  }

  @Test
  /**
  * Test PLC returning valid fields for occupancy for crossing
  */
  @DisplayName("Validate I Section Occupied, setSwitchState to 0")
  void testRedCross() throws IOException, ScriptException{
    assertTrue(this.track.getBlock("Red", "I", 47).getAssociatedCrossing().viewCrossingState());
    this.track.getBlock("Red", "I", 47).setOccupied(true);
    this.track.getBlock("Red", "I", 48).setOccupied(true);
    this.plc.parse();
    this.plc.runCrossingPLC();
    assertFalse(this.track.getBlock("Red", "I", 47).getAssociatedCrossing().viewCrossingState());
  }

  @Test
  /**
  * Test PLC for not changing switch while block is occupied
  */
  @DisplayName("C9 occupied, attempt to change switch block")
  void testOccSwitch() throws IOException, ScriptException{
    Block switchBlock = this.track.getBlock("Red", "C", 9);
    Block yardBlock = this.track.getBlock("Red", "U", 77);
    switchBlock.setOccupied(true);
    this.plc.runSwitchPLC();
    assertTrue(switchBlock.setSwitchState(-1));
  }

}
