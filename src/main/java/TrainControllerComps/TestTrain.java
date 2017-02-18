/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerComps;

import java.awt.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Andrew
 */
public class TestTrain {
   
    String id; 
    double speed, power;     
    Double kp, ki; 
    double currentBlockSpeed; 
    double currentSuggestedSpeed; 

    public TestTrain (String id){
    
        this.id = id; 
        this.kp = null; 
        this.ki = null;
        this.speed = 0.0; 
        this.power = 0.0; 
        this.currentBlockSpeed = 0.0; 
        this.currentSuggestedSpeed = 0.0; 
    }
    
    public boolean powerConstantsSet(){
       
        if (this.kp == null && this.ki == null){
            return false;
        }else{
            return true;
        }     
    }
    
    /**
     * Generates 'howMany' number of TestTrains, and assigns a random Speed and Power. 
     * 
     * @param howMany the number of trains you want to generate
     * @return A list containing 'howMany' TestTrain objects. 
     */
    public static LinkedList<TestTrain> generateRandomTestTrain(int howMany){
    
        LinkedList<TestTrain> testTrains = new LinkedList(); 
        
        for (int i = 0; i < howMany; i++ ){
            
            TestTrain train = new TestTrain("Train ID:" + i); 
            
            Random rand = new Random(); 
            double randDouble = (int )(rand.nextDouble() * 100.0) % 100.0;
            train.speed = randDouble;
            
            randDouble = (int) (rand.nextDouble() * 100.0) % 100.0;
            train.power = randDouble; 
            
            train.currentBlockSpeed = train.speed + 20.0; 
            train.currentSuggestedSpeed = randDouble = (int) (rand.nextDouble() * 100.0) % train.currentBlockSpeed;
            
            testTrains.add(train);
        }
        return testTrains; 
    }
    
    
    public static void main(String[] args){
        
        
        LinkedList<TestTrain> trains = TestTrain.generateRandomTestTrain(5); 
        
        for (TestTrain train : trains){
        
            System.out.println("Train Craeted. id: " + train.id + " Power: " + train.power + " Speed: " + train.speed );

        }
    }
    
}
