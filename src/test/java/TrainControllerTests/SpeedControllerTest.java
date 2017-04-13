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
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.*; 


/**
 * Various tests for making sure the Speed Controller is working as intended. 
 * 
 * @author Andrew Lendacky
 */
public class SpeedControllerTest {
    
    static TrackModel track = new TrackModel("Testing"); 
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    static Train testTrain = new Train(1, track); 
    static TrainController tc = new TrainController(testTrain, "Automatic", "Normal");
    static Block yardBlock; 
    
    static TrainHandler trainhandler;
    
    static Double suggSpeed = 80.0;
    
   @BeforeAll
  /**
  * Initialization of the Train Controller to be used for testing
  */
    static void init(){ 
                      
    track.readCSV(fNames);
    
    trainhandler = new TrainHandler(track); 
    // spawn a train at the yard
    yardBlock = track.viewStationMap().get("Red").get("YARD").get(0);
    Block endingBlock = yardBlock.nextBlockForward();
    
    trainhandler.setSpeedAndAuthority(-1, suggSpeed, endingBlock , yardBlock);
    Train train = trainhandler.findTrain(1);
    train.setKpAndKi(40000.0, 20000.0);
    tc.setTrains(train);
  }
         
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the set speed is not greater than the block speed")
  static void speedNGThanBlockSpeed(){
      
      
  }
  
  @Test
  /**
  * Test for proper station names reading.
  */
  @DisplayName("Validate that the set speed is not greater than the suggested speed")
  static void speedNGThanSuggSpeed_AutomaticMode(){
    
    tc.getSpeedController().setTrain(tc.getTrain()); // set the train
    tc.getSpeedController().setSetSpeed(100); // set the set of the train to some value over the sugg speed
     
    assertTrue(tc.getSpeedController().getSetSpeed() == suggSpeed); // the set speed should cap out at the sugg speed    
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

//  @Test
//  /**
//  * Test for proper station names reading.
//  */
//  @DisplayName("Validate that Speed Controller gets passed in the same train as the Train Controller")
//  void passedCorrectTrain(){
//
//      Train tcTrain = this.tc.getTrain(); 
//    
//      this.tc.getSpeedController().setTrain(tcTrain);
//      
//      assertTrue(tcTrain == this.tc.getSpeedController().getTrain()); 
//  }   
  
}
