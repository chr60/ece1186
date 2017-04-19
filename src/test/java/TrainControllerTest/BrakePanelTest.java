/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerTests;

import TrackModel.Block;
import TrackModel.TrackModel;
import TrainControllerComps.TrainController;
import TrainModel.Train;
import TrainModel.TrainHandler;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Andrew
 */
public class BrakePanelTest {
    

  static TrackModel track = new TrackModel("Testing");
  static String[] fNames = {"src/test/resources/redline.csv"};

  static Train testTrain;

  static TrainController tc;

  static TrainHandler trainhandler;

  static Block yardBlock;
  static Block endingBlock;

  @BeforeAll
  /**
  * Initialization of the TrainController to be used for testing
  */
  static void init(){

    track.readCSV(fNames);

    testTrain = new Train(1, track);
    tc = new TrainController(testTrain, "Automatic", "Normal");

    trainhandler = new TrainHandler(track);
  }

  // // @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that Speed Controller gets passed in the same train as the Train Controller")
  void passedCorrectTrain(){

      Train tcTrain = tc.getTrain();

      tc.getBrakePanel().setSelectedTrain(tcTrain);

      assertTrue(tcTrain == tc.getBrakePanel().getSelectedTrain());
  }

}
