package TrackModelTest;

import TrackModel.Block;
import TrackModel.Switch;
import TrackModel.TrackModel;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RedLinkageTest {
  
  private static String line = "Red";
  private static String[] stationNames={"YARD","SHADYSIDE","HERRON AVE","SWISSVILLE","PENN STATION",
                "STEEL PLAZA","FIRST AVE","STATION SQUARE","SOUTH HILLS JUNCTION","YARD"};
  private static Integer[] rootBlocksNum={16,27,33,38,44,52,9};
  private static Integer[] expectedStationBlockNums={7,16,21,25,35,45,48,60,77};
  //Move to a Set based interface for easy comparables
  private static TreeSet<Integer> testBlocksRoot = new TreeSet<Integer>(Arrays.asList(rootBlocksNum));
  private static TreeSet<String> testNamesSet = new TreeSet<String>(Arrays.asList(stationNames));
  private static TreeSet<Integer> testExpectedStationBlockNums = new TreeSet<Integer>(Arrays.asList(expectedStationBlockNums));
  private static TrackModel track;
  private static String[] fNames = {"src/test/resources/redline.csv"};
  private static String[] fOverrideNames = {"src/test/resources/redlinelink.csv"};
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
  @DisplayName("Validates the override of 65 for reverse notation")
  void overrideTest() {
    Block sourceBlock = this.track.getBlock("Red", "N", 66);
    Block nextBlockForward = this.track.getBlock("Red", "N", 65);
    System.out.println(sourceBlock.blockNum());
    assertEquals(sourceBlock.nextBlockForward(), nextBlockForward);
  }

}