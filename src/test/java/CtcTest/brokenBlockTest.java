
package CTC;

import TrackModel.*;
import WaysideController.*;

//Test Imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Utils
import java.util.ArrayList;


public class brokenBlockTest{
  private static TrackModel track;
  private static CTCgui ctc;
  private static TrackModel dummyTrack;
  private static ArrayList<TrainManager> trainManagers;
  private static ArrayList<WS> waysideList;
  private static WS ws;
/*
  @BeforeEach
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("BrokenBlockTest");
    this.dummyTrack = new TrackModel("BrokenBlockTest");
    this.track.readCSV(fNames);
    this.dummyTrack.readCSV(fNames);

      //System.out.println(s);
      this.ws = new WS("Red", this.track);
      this.waysideList = new ArrayList<WS>();
      this.waysideList.add(ws);
      this.trainManagers = new ArrayList<TrainManager>();
      trainManagers.add(new TrainManager("Red", this.dummyTrack));


  this.ctc = new CTCgui(this.trainManagers, this.dummyTrack, this.waysideList, this.track);

  }


@Test

@DisplayName("Size of broken list, zero")
void testBrokenListZero(){
  ArrayList<Block> totalBroken = ctc.getBrokenList();
  assertTrue(totalBroken.size() == 0);

  assertTrue(ctc.btnNoFailure.getText().equals("No Failure"));
}

@Test

@DisplayName("Size of broken list, not zero")
void testBrokenListNonZero(){
  Block brokenBlock1 = this.track.getBlock("Red", "H", 25);
  Block brokenBlock2 = this.track.getBlock("Red", "C", 8);
  brokenBlock1.setBroken(true);
  brokenBlock2.setBroken(true);

  System.out.println(ctc.listFromWS.size());
  assertTrue(ctc.listFromWS.size() > 0);
  assertTrue(!ctc.btnNoFailure.getText().equals("No Failure"));
  ctc.btnNoFailure.doClick();

  assertTrue(ctc.setDropdownFailure.get(2).equals(ctc.blockPanel.dropdown_block.getSelectedItem()));

}

*/
}
