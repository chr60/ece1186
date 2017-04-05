/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerTests;

import TrackModel.TrackModel;
import TrainControllerComps.TrainController;
import TrainModel.Train;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Various tests for making sure the Speed Controller is working as intended. 
 * 
 * @author Andrew Lendacky
 */
public class SpeedControllerTest {
    

  private TrainController tc;
    
    
  @BeforeEach
  /**
  * Initialization of the Train Controller to be used for testing
  */
  void init(){
    
    tc = new TrainController();   
  }
    
    
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the right train is being passed to the Speed Controller")
  void testTrain(){
    
//      Train testTrain = new Train(0, new TrackModel());
//      tc.setTrains(testTrain);
//      Train trainFromSpeedController = tc.getSpeedController().getTrain();
//      
//      assertTrue(testTrain == trainFromSpeedController); 
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the set speed is not greater than the block speed")
  void speedNGThanBlockSpeed(){
    
//      double blockSpeed = 80.0; // set the block speed limit
//      
//      tc.getSpeedController().setSetSpeed(100); // set the set of the train to some value over the block speed
//      
//      assertTrue(tc.getSpeedController().getSetSpeed() == 80); // the set speed should cap out at the block speed
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the set speed is not greater than the suggested speed")
  void speedNGThanSuggSpeed(){
    
//    double suggSpeed = 80.0; // set the block speed limit
//      
//    tc.getSpeedController().setSetSpeed(100); // set the set of the train to some value over the sugg speed
//     
//    assertTrue(tc.getSpeedController().getSetSpeed() == 80); // the set speed should cap out at the sugg speed
      
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the slider's max value equals block speed")
  void sliderMaxValueNGBlockSpeed(){
//  
//      double blockSpeed = 80.0; 
//           
//      tc.getBlockInfoPane().setBlockSpeed(blockSpeed);
//      
//      assertTrue(tc.getSpeedController().getSpeedSlider().getMaximum() == (int) tc.getBlockInfoPane().getBlockSpeed() ); 
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the slider's max value equals block speed")
  void sliderMaxValueNGSuggSpeed(){
//  
//    double suggSpeed = 80.0; 
//           
//    tc.getBlockInfoPane().setBlockSpeed(suggSpeed);
//      
//    assertTrue(tc.getSpeedController().getSpeedSlider().getMaximum() == (int) tc.getBlockInfoPane().getBlockSpeed() ); 
  }   
}
