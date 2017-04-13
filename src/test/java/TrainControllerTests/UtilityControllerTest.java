/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerTests;

import TrackModel.Block;
import TrackModel.TrackModel;
import TrainControllerComps.TrainController;
import static TrainControllerTests.BrakePanelTest.tc;
import TrainModel.Train;
import TrainModel.TrainHandler;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Various tests for making sure the UtilityPanel is working as intended. 
 * 
 * @author Andrew Lendacky
 */
public class UtilityControllerTest {
    
    static TrackModel track = new TrackModel("Testing"); 
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    static Train testTrain = new Train(1, track); 
    static TrainController tc = new TrainController(testTrain, "Automatic", "Normal");
    static Block yardBlock; 
    
    static TrainHandler trainhandler;
    
  @BeforeAll
  /**
  * Initialization of the TrainController to be used for testing
  */
  static void init(){
    
    track.readCSV(fNames);
    
    testTrain  = new Train(1, track); 
    
    tc.getBlockInfoPane().setBlockSpeed(yardBlock.getSpeedLimit());
   
    trainhandler = new TrainHandler(track); 
  }
  
  @Test
  /**
   * Test to make sure heat is off when ac is on.
   */
  @DisplayName("Check to make sure heat is off, when ac is on") 
  static void acOnHeatOff(){
  
      // turn on ac
      tc.getUtilityPane().turnOnAC();
      
      assertTrue(tc.getTrain().getAC() == 1);
      assertTrue(tc.getTrain().getHeat() == 0);
  }
  
  @Test
  /**
   * Test to make sure heat is on and ac is off.
   */
  @DisplayName("Check to make sure ac is off, when heat is on") 
  static void heatOnAcOff(){
  
      // turn on heat
      tc.getUtilityPane().turnOnHeat(); 
      
      assertTrue(tc.getTrain().getAC() == 0);
      assertTrue(tc.getTrain().getHeat() == 1);   
  }
  
  @Test
  /**
   * Test to make the utility panel is receiving the correct train.
   */
  @DisplayName("Check to make the utility panel is receiving the correct train") 
  static void passedCorrectTrain(){
  
      Train tcTrain = tc.getTrain(); 
      tc.getUtilityPane().setSelectedTrain(tcTrain);
      
      assertTrue(tcTrain == tc.getBrakePanel().getSelectedTrain());   
  }
}
