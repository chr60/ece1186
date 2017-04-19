package TrainModel;

//Module Imports
import TrainModel.*;
import TrackModel.*;

//Test Imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Utils
import java.util.ArrayList;

public class PowerCommandTest{
  private static Train myTrain;
  private static TrackModel myTrack;
  private static Double baseTestA;
  private static Double serviceSpeed;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
	String[] fNames = {"src/test/resources/redline.csv"};
  String[] fNamesOverride = {"resources/redlineLink.csv"};
	myTrack= new TrackModel("GlobalTrack");
	myTrack.readCSV(fNames, fNamesOverride);
	Block yardBlockRed = myTrack.getBlock("Red", "U", 77);
    yardBlockRed = myTrack.lateralLookup(yardBlockRed);
    myTrain = new Train(1, myTrack);
	myTrain.setCurrBlock(yardBlockRed);
	
	//run baseTestA at start of each test. since unsure of order of tests and BaseTestA needs to be intialized
	Train trainA = new Train(2, myTrack);
	trainA.setCurrBlock(yardBlockRed);
	trainA.powerCommand(100000.0);
	
	baseTestA = trainA.getVelocity();
	//velocity should be greater than 0
	assertTrue(baseTestA > 0.0);

  }


  
  @Test
  /**
  * Compute velocity of Train at rest with power Command greater than 100 kW
  */
  @DisplayName("Test applying Power Command greater than 100000 Train starting from stationary")
  void testLessPositivePowerCommand(){
    myTrain.powerCommand(119000.0);
	//velocity should be slightly greater than base case velocity
	assertTrue(myTrain.getVelocity() >= baseTestA);
  }
  
  @Test
  /**
  *Compute velocity of Train at rest with power Command less than 100 kW
  */
  @DisplayName("Test applying Power Command less than 100000 Train starting from stationary")
  void testMorePositivePowerCommand(){
    myTrain.powerCommand(50000.0);
	//velocity should be less than base case velocity
	assertTrue(myTrain.getVelocity() <= baseTestA);
  }
  
  @Test
  /**
  * repeat Base test A with positive grade
  */
  @DisplayName("Increase grade and test again")
  void testPositivePowerCommandHighGrade(){
	  myTrain.setGrade(3.0);
    myTrain.powerCommand(100000.0);
	//velocity should be slightly less than base case velocity
	assertTrue(myTrain.getVelocity() <= baseTestA);
  }
  
   @Test
  /**
  * repeat Base test A with negative grade
  */
  @DisplayName("decrease grade and test again")
  void testPositivePowerCommandLowGrade(){
	 myTrain.setGrade(-3.0);
    myTrain.powerCommand(100000.0);
	//velocity should be greater than base case velocity
	assertTrue(myTrain.getVelocity() >= baseTestA);
  }
  
  @Test
  /**
  * repeat Base test A with 200 passengers added
  */
  @DisplayName("Add 15 passengers and test again")
  void testPositivePowerCommandMorePass(){
	 myTrain.changePassengers(200);
    myTrain.powerCommand(100000.0);
	//velocity should be less than base case velocity
	assertTrue(myTrain.getVelocity() <= baseTestA);
  }
  
   @Test
  /**
  * Base Test B: Compute velocity of moving Train power Command at 100 kW
  */
  @DisplayName("Test applying postive Power Command to Train starting from 25 MPH")
  void testPositivePowerCommandMoving(){
	  myTrain.setVelocity(25.0);
    myTrain.powerCommand(100000.0);
	//velocity should be greater than 25
	assertTrue(myTrain.getVelocity() > 25.0);
  }
 
  
   @Test
  /**
  * repeat test B with new conditions Compute velocity of moving Train power Command at 0 kW
  */
  @DisplayName("Test applying zero Power Command to Train starting from 25 MPH")
  void testZeroPowerCommandMoving(){
	myTrain.setVelocity(25.0);
    myTrain.powerCommand(0.0);
	//velocity should not change or slightly slow down
	assertTrue(myTrain.getVelocity() <= 25.0);
  }
  
    @Test
  /**
  * repeat test B with new conditions Compute velocity of moving Train power Command at  less than 0 kW
  */
  @DisplayName("Test applying negative Power Command to Train starting from 25 MPH")
  void testNegativePowerCommandMoving(){
	myTrain.setVelocity(25.0);
    myTrain.powerCommand(-10000.0);
	//velocity should not change
	assertTrue(myTrain.getVelocity() <= 25.0);
  }
  
  @Test
  /**
  * repeat test B with new conditions Compute velocity of moving Train power Command at  greater than max
  */
  @DisplayName("Test applying Power Command about max to Train starting from 25 MPH")
  void testMaxPowerCommand(){
	myTrain.setVelocity(25.0);
    myTrain.powerCommand(200000.0);
	//power command set to max and minimum change in velocity
	assertTrue(myTrain.getPower() == 120000.0);
  }
  
   @Test
  /**
  * repeat test B with new conditions with service brakes applied
  */
  @DisplayName("Test applying service brakes to Train starting from 25 MPH")
  void testServiceBrakeMoving(){
	myTrain.setVelocity(25.0);
	myTrain.setServiceBrake(1);
	//velocity should not change 
	assertTrue(myTrain.getVelocity() <  25.0);
  }
  
   @Test
  /**
  * repeat test B with new conditions with emergency brakes applied
  */
  @DisplayName("Test applying emergency brakes to Train starting from 25 MPH")
  void testEmergencyBrakeMoving(){
	myTrain.setVelocity(25.0);
	myTrain.setEmergencyBrake(1);
	//velocity should not change 
	assertTrue(myTrain.getVelocity() <  25.0);;
  }  
}
