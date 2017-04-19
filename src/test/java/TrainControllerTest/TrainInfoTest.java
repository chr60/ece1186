///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package TrainControllerTests;
//
//import TrackModel.Block;
//import TrackModel.TrackModel;
//import TrainControllerComps.TrainController;
//import TrainModel.Train;
//import TrainModel.GPS;
//import TrainModel.TrainHandler;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//public class TrainInfoTest {
//    
//    TrackModel track;
//    String[] fNames = {"src/test/resources/redline.csv"};
//    
//    Train testTrain = new Train(1, track); 
//    TrainController tc;
//    Block yardBlock;
//    Block endBlock;
//    
//    private String[] fOverrideNames = {"test-classes/redlinelink.csv"};
//
//    TrainHandler trainhandler;
//    
//  @BeforeEach
//  /**
//  * Initialization of the TrainController to be used for testing
//  */
//  void init(){
//    this.track = new TrackModel("Testing");
//    this.track.readCSV(this.fNames, this.fOverrideNames);
//    
//    this.yardBlock = this.track.getBlock("Red", "U", new Integer(77));
//    this.endBlock = this.track.getBlock("Red", "C", new Integer(6)); 
//    
//    this.testTrain  = new Train(1, this.track);   
//    this.tc = new TrainController(this.testTrain, "Automatic", "Normal");
//    this.tc.getBlockInfoPane().setBlockSpeed(this.yardBlock.getSpeedLimit()); 
//    this.trainhandler = new TrainHandler(this.track); 
//
//  }
//
//  // @Test
//  /**
//   * Test to make sure the info panel is displaying the correct information.
//   */
//  @DisplayName("Check to make sure the info panel is displaying the correct information") 
//  void hasCorrectInfo(){
//      
//      this.testTrain  = new Train(1, this.track); 
//      System.out.println(this.testTrain); 
//      Double power = new Double(100.0); 
//      //this.testTrain.powerCommand(new Double(100.0));
//      //this.testTrain.setAuthority(endBlock);
//      this.yardBlock.setSuggestedSpeed(50.0);
//      
//      this.tc.getTrainInfoPane().setSelectedTrain(testTrain);
//      
//      //assertTrue(this.tc.getTrainInfoPane().getPowerLabel().equals(Double.toString(this.tc.getTrainInfoPane().getSelectedTrain().getPower()))); 
//      System.out.println("Speed: " + this.tc.getTrainInfoPane().getSpeedLabel()); 
//      System.out.println("Speed: " + String.format("%.2f", this.testTrain.getVelocity()));
//      
//      assertTrue(this.tc.getTrainInfoPane().getSpeedLabel().equals(String.format("%.2f", this.testTrain.getVelocity()))); 
//      assertTrue(this.tc.getTrainInfoPane().getSuggestedSpeedLabel().equals(String.format("%.2f", this.testTrain.getSuggestedSpeed()))); 
//      assertTrue(this.tc.getTrainInfoPane().getAuthorityLabel().equals(Double.toString(this.tc.getTrainInfoPane().getSelectedTrain().getAuthority().getCurrBlock().blockNum()))); 
//  }
// 
//  // @Test
//  /**
//   * Test to make the utility panel is receiving the correct train.
//   */
//  @DisplayName("Check to make the utility panel is receiving the correct train")
//  void passedCorrectTrain(){
//
//      Train tcTrain = tc.getTrain();
//      tc.getTrainInfoPane().setSelectedTrain(tcTrain);
//
//      assertTrue(tcTrain == tc.getTrainInfoPane().getSelectedTrain());
//  }
//
//  // @Test
//  /**
//   * Test to make the speed picked up from the train is converted correctly.
//   */
//  @DisplayName("Check to make the speed picked up from the train is converted correctly")
//  void convertSpeedCorrectly(){
//
//    testTrain.powerCommand(100.0);
//	GPS auth = new GPS(endBlock,null);
//    testTrain.setAuthority(auth);
//    testTrain.setAuthority(auth);
//    yardBlock.setSuggestedSpeed(50.0);
//
//    tc.getTrainInfoPane().setSelectedTrain(testTrain);
//
//    tc.getTrainInfoPane().refreshUI();
//
//    Double speed = testTrain.getSuggestedSpeed()*0.621371;
//
//    assertTrue(speed == Double.parseDouble( tc.getTrainInfoPane().getSpeedLabel()) );
//  }
//}
////