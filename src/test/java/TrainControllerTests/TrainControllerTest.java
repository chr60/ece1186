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
import org.junit.jupiter.api.BeforeAll;
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
    static TrackModel track = new TrackModel("Testing");
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    // Train Info
    static Train testTrain = new Train(0, new TrackModel("Test")); 
    
    // Yard Block 
    static Block yardBlock; 
    
    // Train Controller
    static TrainController tc = new TrainController(testTrain);
    
  @BeforeAll
  /**
  * Initialization of the TrainController to be used for testing
  */
  static void init(){
    
    track.readCSV(fNames); 
  }
  
  
  @Test
  /**
  * Test to make sure that the Train Controller is getting the right train.
  */
  @DisplayName("Validate that the system is controlling the correct train")
  static void switchToCorrectTrain(){

      Train newTrain = new Train(10202, track); 
      
      tc.setTrains(newTrain);
      
      assertTrue(tc.getTrain() == newTrain); 
  }
  
  @Test
  /**
  * Test to make sure that the system in in automatic mode.
  */
  @DisplayName("Validate that the system is put into Automatic mode")
  static void inAutomaticMode(){

      tc.setMode("Automatic", "Normal");
      
      assertTrue(tc.getPlayMode().equals("Automatic")); 
  }
  
  @Test
  /**
  * Test to make sure that the system in in manual mode.
  */
  @DisplayName("Validate that the system is put into manual mode")
  static void inManualMode(){

      tc.setMode("Manual", "Normal");
      
      assertTrue(tc.getPlayMode().equals("Manual"));
  }
  
  @Test
  /**
  * Test that the system in in normal mode.
  */
  @DisplayName("Validate that the system is in normal mode")
  static void inNormalMode(){

      tc.setMode("Automatic", "Normal");
      
      assertTrue(tc.getTestMode().equals("Normal"));  
  }
  
  @Test
  /**
  * Test that the system is in testing mode.
  */
  @DisplayName("Validate that the system is in testing mode")
  static void inTestingMode(){

      tc.setMode("Manual", "Testing");
       
      assertTrue(tc.getTestMode().equals("Testing"));
  }    
}
