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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * Various test for the Train Controller to perform.
 *
 * @author Andrew Lendacky
 */
public class TrainControllerTest {
    
    // Track Info 
    TrackModel track;
    String[] fNames = {"src/test/resources/redline.csv"};
    Train testTrain;

    // Train Controller
    TrainController tc;
    private String[] fOverrideNames = {"test-classes/redlinelink.csv"};

  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    this.testTrain = new Train(0, new TrackModel("Test")); 
    
    this.track = new TrackModel("Testing");
    this.track.readCSV(fNames, fOverrideNames);

    this.tc = new TrainController(this.testTrain, "Automatic", "Normal");
  }


  // @Test
  /**
  * Test to make sure that the Train Controller is getting the right train.
  */
  @DisplayName("Validate that the system is controlling the correct train")
  void switchToCorrectTrain(){
      Train newTrain = new Train(10202, track); 
      
      tc.setSelectedTrain(newTrain);
      
      assertTrue(tc.getTrain() == newTrain); 
  }

  // @Test
  /**
  * Test to make sure that the system in in automatic mode.
  */
  @DisplayName("Validate that the system is put into Automatic mode")
  void inAutomaticMode(){

      tc.setMode("Automatic", "Normal");

      assertTrue(tc.getPlayMode().equals("Automatic"));
  }

  // @Test
  /**
  * Test to make sure that the system in in manual mode.
  */
  @DisplayName("Validate that the system is put into manual mode")
  void inManualMode(){

      tc.setMode("Manual", "Normal");

      assertTrue(tc.getPlayMode().equals("Manual"));
  }

  // @Test
  /**
  * Test that the system in in normal mode.
  */
  @DisplayName("Validate that the system is in normal mode")
  void inNormalMode(){

      tc.setMode("Automatic", "Normal");

      assertTrue(tc.getTestMode().equals("Normal"));
  }

  // @Test
  /**
  * Test that the system is in testing mode.
  */
  @DisplayName("Validate that the system is in testing mode")
  void inTestingMode(){

      tc.setMode("Manual", "Testing");

      assertTrue(tc.getTestMode().equals("Testing"));
  }

  // @Test
  /**
  * Test that the system is put in normal speed.
  */
  @DisplayName("Validate that the system plays at normal speed")
  void inNormalSpeed(){

      tc.playNormal();

      assertTrue(tc.clock.getDelay() == 1000);
  }

  // @Test
  /**
  * Test that the system is put in fast speed.
  */
  @DisplayName("Validate that the system plays at fast speed")
  void inFastSpeed(){
      tc.playFast();

      assertTrue(tc.clock.getDelay() == 100);
  }
}
