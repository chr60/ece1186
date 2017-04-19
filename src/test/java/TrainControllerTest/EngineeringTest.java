/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerTests;

import TrackModel.Block;
import TrackModel.TrackModel;
import TrainControllerComps.TCEngineerPanel;
import TrainControllerComps.TrainController;
import TrainModel.Train;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Andrew
 */
public class EngineeringTest {
    
        // Track Info 
    static TrackModel track = new TrackModel("Testing");
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    // Train Info
    static Train testTrain = new Train(0, new TrackModel("Test")); 
    
    // Yard Block 
    static Block yardBlock; 
    
    // Train Controller
    static TrainController tc = new TrainController(testTrain);
    
    static TCEngineerPanel engPanel; 
    
    private static String[] fOverrideNames = {"test-classes/redlinelink.csv"};
    
  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    
    track.readCSV(fNames, fOverrideNames);

  }


  // @Test
  /**
  * Test to make sure that the Train Controller is getting the right train.
  */
  @DisplayName("Validate that the system is controlling the correct train")
  static void switchToCorrectTrain(){

//      engPanel = new TCEngineerPanel(tc.getTrain());
//
//      assertTrue(engPanel.getTrain() == tc.getTrain());
  }


  // @Test
  // /**
  // * Test to make sure that the Train Controller is getting the right train.
  // */
  // @DisplayName("Validate that the system is controlling the correct train")
  // static void setPowerConstants(){
  //
  //
  // }

}
