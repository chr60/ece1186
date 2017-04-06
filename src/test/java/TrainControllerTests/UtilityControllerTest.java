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
 * Various tests for making sure the UtilityPanel is working as intended. 
 * 
 * @author Andrew Lendacky
 */
public class UtilityControllerTest {
    
    private TrainController tc;
    TrackModel track = new TrackModel("Testing"); 
    String[] fNames = {"src/test/resources/redline.csv"};
    Train testTrain = new Train(0, new TrackModel("Test")); 
    Block yardBlock; 
    
  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    
//    track.readCSV(fNames);
//    tc = new TrainController(testTrain, "Manual", "Normal");
//    yardBlock = this.track.viewStationMap().get("Red").get("YARD").get(0);
//    tc.getBlockInfoPane().setBlockSpeed(yardBlock.getSpeedLimit());
  }
  
  
  @Test
  /**
   * Test to make sure heat is off when ac is on.
   */
  @DisplayName("Check to make sure heat is off, when ac is on") 
  void acOnHeatOff_Set(){
  
//      // turn on ac
//      tc.getTrain().setAC(1);
//      
//      assertTrue(tc.getTrain().getAC() == 1);
//      assertTrue(tc.getTrain().getHeat() == 0);
  }
  
  @Test
  /**
   * Test to make sure heat is on and ac is off.
   */
  @DisplayName("Check to make sure ac is off, when heat is on") 
  void heatOnAcOff_Set(){
  
//      // turn on heat
//      tc.getTrain().setHeat(1);
//      
//      assertTrue(tc.getTrain().getAC() == 0);
//      assertTrue(tc.getTrain().getHeat() == 1);   
  }
//
//  @Test
//  /**
//   * Test to make sure heat is off when ac is on.
//   */
//  @DisplayName("Check to make sure ac is off, when heat is on") 
//  void acOnHeatOff_RadioButton(){
//  
//      // turn on heat
//      
//  }
//  
//   @Test
//  /**
//   * Test to make sure heat is on and ac is off.
//   */
//  @DisplayName("Check to make sure ac is off, when heat is on") 
//  void heatOnAcOff_RadioButton(){
//  
//      // turn on heat  
//  } 
//  
//    
}
