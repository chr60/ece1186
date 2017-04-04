/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerTests;

import TrainControllerComps.TrainController;
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
    
  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    
    tc = new TrainController();   
  }
  
  
  @Test
  /**
   * Test to make sure heat is off when ac is on.
   */
  @DisplayName("Check to make sure heat is off, when ac is on") 
  void acOnHeatOff_Set(){
  
      // turn on ac
      
  }
  
  @Test
  /**
   * Test to make sure heat is on and ac is off.
   */
  @DisplayName("Check to make sure ac is off, when heat is on") 
  void heatOnAcOff_Set(){
  
      // turn on heat
      
  }

  @Test
  /**
   * Test to make sure heat is off when ac is on.
   */
  @DisplayName("Check to make sure ac is off, when heat is on") 
  void acOnHeatOff_RadioButton(){
  
      // turn on heat
      
  }
  
   @Test
  /**
   * Test to make sure heat is on and ac is off.
   */
  @DisplayName("Check to make sure ac is off, when heat is on") 
  void heatOnAcOff_RadioButton(){
  
      // turn on heat  
  } 
  
    
}
