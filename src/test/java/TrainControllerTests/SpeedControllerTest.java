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
  TrackModel track = new TrackModel("Testing");
  String[] fNames = {"src/test/resources/redline.csv"};
  
  @BeforeEach
  /**
  * Initialization of the Train Controller to be used for testing
  */
  void init(){  
    track.readCSV(fNames);
  }
    
    
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the right train is being passed to the Train Controller")
  void testTrain(){
    
    Train train; 
      
    for (int i = 0; i < 10; i++){
      train = new Train(i + 1, track); 
      tc = new TrainController(train, true); 
      assertTrue(tc.getTrain() != null); // there was a train passed in
      assertTrue(train.getID() == tc.getTrain().getID()); // the ids match  
    }
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the set speed is not greater than the block speed")
  void speedNGThanBlockSpeed(){
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

       
//      Train train = new Train(0, this.track);
//      
//      tc = new TrainController(train, "Manual", "Normal"); 
//      train.setKpAndKi(400000.0, 20000.0);
//      tc.getSpeedController().setManualMode(true);
//      
//      Block yardBlock = this.track.viewStationMap().get("Red").get("YARD").get(0);
//      Double blkSpeed = yardBlock.getSpeedLimit();   
//      tc.getBlockInfoPane().setBlockSpeed(blkSpeed);
//      tc.getSpeedController().getSpeedSlider().setMaximum(blkSpeed.intValue());
//      
//      assertTrue(tc.getSpeedController().getSpeedSlider().getMaximum() == (int) tc.getBlockInfoPane().getBlockSpeed() ); 
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the slider's max value equals block speed")
  void sliderMaxValueNGSuggSpeed(){

      
  }   
}
