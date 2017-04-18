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

public class UtilityControllerTest {
    
    static TrackModel track = new TrackModel("Testing"); 
    static String[] fNames = {"src/test/resources/redline.csv"};
    
    static Train testTrain = new Train(1, track); 
    static TrainController tc = new TrainController(testTrain, "Automatic", "Normal");
    static Block yardBlock = track.getBlock("Red", "U", new Integer(77));
    
    static TrainHandler trainhandler;
    
  @BeforeEach
  /**
  * Initialization of the TrainController to be used for testing
  */
  void init(){
    
    this.track.readCSV(fNames);
    this.testTrain  = new Train(1, this.track); 
    tc.getBlockInfoPane().setBlockSpeed(this.yardBlock.getSpeedLimit());
    trainhandler = new TrainHandler(track); 
  }
  
  @Test
  /**
   * Test to make sure heat is off when ac is on.
   */
  @DisplayName("Check to make sure heat is off, when ac is on") 
  void acOnHeatOff(){
  
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
  void heatOnAcOff(){
  
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
  void passedCorrectTrain(){
  
      Train tcTrain = tc.getTrain(); 
      tc.getUtilityPane().setSelectedTrain(tcTrain);
      
      assertTrue(tcTrain == tc.getBrakePanel().getSelectedTrain());   
  }
}
