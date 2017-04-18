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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TrainInfoTest {
    
    static TrackModel track = new TrackModel("Testing"); 
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    static Train testTrain = new Train(1, track); 
    static TrainController tc = new TrainController(testTrain, "Automatic", "Normal");
    static Block yardBlock = track.getBlock("Red", "U", new Integer(77));
    static Block endBlock = track.getBlock("Red", "C", new Integer(6)); 
    
    static TrainHandler trainhandler;
    
  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    this.track.readCSV(fNames);   
    this.testTrain  = new Train(1, this.track);   
    tc.getBlockInfoPane().setBlockSpeed(this.yardBlock.getSpeedLimit()); 
    trainhandler = new TrainHandler(this.track); 
  }
  
  @Test
  /**
   * Test to make sure the info panel is displaying the correct information.
   */
  @DisplayName("Check to make sure the info panel is displaying the correct information") 
  static void hasCorrectInfo(){
  
      testTrain.powerCommand(100.0);
      testTrain.setAuthority(endBlock);
      yardBlock.setSuggestedSpeed(50.0);
      
      tc.getTrainInfoPane().setSelectedTrain(testTrain);
   
      tc.getTrainInfoPane().refreshUI();
      
      assertTrue(tc.getTrainInfoPane().getPowerLabel().equals(Double.toString(tc.getTrainInfoPane().getSelectedTrain().getPower()))); 
      assertTrue(tc.getTrainInfoPane().getSpeedLabel().equals(Double.toString(tc.getTrainInfoPane().getSelectedTrain().getVelocity()))); 
      assertTrue(tc.getTrainInfoPane().getSuggestedSpeedLabel().equals(Double.toString(tc.getTrainInfoPane().getSelectedTrain().getSuggestedSpeed()))); 
      assertTrue(tc.getTrainInfoPane().getAuthorityLabel().equals(Double.toString(tc.getTrainInfoPane().getSelectedTrain().getAuthority().getCurrBlock().blockNum()))); 
  }
  
  
  @Test
  /**
   * Test to make the utility panel is receiving the correct train.
   */
  @DisplayName("Check to make the utility panel is receiving the correct train") 
  void passedCorrectTrain(){
  
      Train tcTrain = tc.getTrain(); 
      tc.getTrainInfoPane().setSelectedTrain(tcTrain);
      
      assertTrue(tcTrain == tc.getTrainInfoPane().getSelectedTrain());   
  }
  
  
  @Test
  /**
   * Test to make the speed picked up from the train is converted correctly.
   */
  @DisplayName("Check to make the speed picked up from the train is converted correctly") 
  void convertSpeedCorrectly(){
  
    testTrain.powerCommand(100.0);
    testTrain.setAuthority(endBlock);
    yardBlock.setSuggestedSpeed(50.0);
      
    tc.getTrainInfoPane().setSelectedTrain(testTrain);
   
    tc.getTrainInfoPane().refreshUI();
    
    Double speed = testTrain.getSuggestedSpeed()*0.621371;
    
    assertTrue(speed == Double.parseDouble( tc.getTrainInfoPane().getSpeedLabel()) );     
  }   
}
