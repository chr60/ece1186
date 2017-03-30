package TrainControllerComps;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TrainModel.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is responsible for allowing the user to set the speed of the selected train if in Manual mode, 
 * or controls the speed automatically if in Automatic mode. 
 * 
 * This class collaborates with the Train and Train Controller class.
 *  
 * @author Andrew Lendacky
 */

public class TCSpeedController extends javax.swing.JPanel {
    
    // MARK: - Variables/Properties
    
    /**
     * The train that is being controlled by the Train Controller. 
     */
    private Train selectedTrain; 
    
    /**
     * Area the logs are printed to.
     */
    private JTextArea operatingLogs; 
    
    /**
     * This is the power command sent from the Train Controller to the train.
     * Based on this value, the train either speeds up, brakes, or does nothing.
     */
    private double powerCommandOut;
    
    /**
     * Max speed the train is allowed to go. The source of this value is determined based on 
     * what mode the system is in. This value is used to set the slider's 
     * max value so that the train cannot go faster than allowed. 
     * 
     */
    private double maxSpeed;
        
    /**
     * A list that is used to hold logs to print to the Operating Logs of the Train Controller. 
     */
    private LinkedList<String> logBook; // log book used to save logs and then print them to the notification windows
    
    /**
     * The speed that the train is desired to go. This is set by the user or
     * automatically depending on what mode the system is in. 
     */
    private int setSpeed;   
    
    /**
     * Used to keep track of how long it takes the train to reach the set speed.
     */
    private int timeElapsed;
    
    /**
     * A boolean value indicating if the Speed Controller is operating in Manual or Automatic mode. 
     * This value is set from the Train Controller class. 
     */
    private boolean inManualMode; 
    
    private boolean speedSet; 
    
    /**
     * Brake panel used to control the brakes on the train if needed to slow down. 
     */
    private TCBrakePanel brakePanel; 
    
    /**
     * Timer used to perform the power law calculations with the train 1 second. 
     */
//    private Timer beginPowerControl = new Timer(1000, new ActionListener(){
//        Random rand = new Random(); 
//        public void actionPerformed(ActionEvent e) {
//             
//            // if there is a selected train and that train's speed doesnt match the set speed
//            if (selectedTrain != null && speedsEqual() == false){
//                timeElapsed++;
//                String log;
//                // get the error 
//                double error = setSpeed - selectedTrain.speed; 
//            
//                // FIX ME: Kp and Ki must be some value, and not null
//                // If user opens the window, and wants to enter 0, 0. if they are null they are set to 
//                // 0,0.
//                powerCommand_Out = selectedTrain.kp * error + selectedTrain.ki;
//                System.out.println(powerCommand_Out);   
//                if (powerCommand_Out < 0){
//                    
//                    brakePanel.getServiceBrake().doClick();
//                }else if (powerCommand_Out > 0){
//                    logBook.add("Full steam ahead!");
//                    // speed up train by 1 MPH 
//                    // FOR TESTING PURPOSES, THIS IS 1 MPH
//                    selectedTrain.speed++; 
//                }else if (powerCommand_Out == 0){ logBook.add("No more power."); }
//
//                logBook.add(Integer.toString(timeElapsed));
//                printLogs();
//             } 
//        }
//    });
    
    // MARK: - Constructors
    
    /**
     * Constructor for creating a new TCSpeedController object with no selected train.
     * The selected train must be set by the Train Controller before being used. 
     * 
     */
    public TCSpeedController() {
                       
        initComponents();
        this.powerCommandOut = 0.0; 
        this.maxSpeed = 0.0;         
        this.logBook = new LinkedList<String>(); 
        
        this.speedSet = false; 
        
        // add action listensers
        this.speedSlider.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
             
        String sliderValue = Integer.toString(speedSlider.getValue());
        //System.out.println("Speed: " + sliderValue);
         
        currentSliderSpeedLabel.setText(sliderValue);
        
        }
       
    });
    }
    
    /**
     * Sets which Operating Logs the Speed Controller should print to. 
     * The operating log should be set from the Train Controller. 
     * 
     * @param opLogs the text field that the Speed Controller will print to. 
     */
    public void setOperatingLogs(JTextArea opLogs){
    
        this.operatingLogs = opLogs; 
    }
    
    /**
     * Sets the brake panel. 
     * This method is called from the Train Controller class. 
     * 
     * @param brakePanel the brake panel from the Train Controller.
     */
    public void setBrakePanel(TCBrakePanel brakePanel){
    
        this.brakePanel = brakePanel; 
    }
    
    /**
     * Gives the Speed Controller the train whose speed to it has to change. 
     * This value is given from the Train Controller class.
     * 
     * @param train the train that is being controlled in the Train Controller class.
     */
    public void setTrain(Train train){

        this.selectedTrain = train;  
        this.setSpeed = selectedTrain.getVelocity().intValue();
    }
    
    /**
     * Sets the max speed the train is allowed to go, and then updates the UI 
     * so that the slider's max value is that of the maxSpeed and the correct speed 
     * is on the label.
     * 
     * @param maxSpeed the max speed the train is allowed to go. 
     */
    public void setMaxSpeed(double maxSpeed){
    
        this.maxSpeed = maxSpeed;         
       
        // update ui
        this.maxSpeedSlider.setText(Double.toString(this.maxSpeed));
        this.speedSlider.setMaximum((int) this.maxSpeed);   
    }
    
    /**
     * Refreshes all the UI components in the SpeedController. 
     * 
     * FIX ME: This should be called from the TrainController every 'x' seconds to update 
     * the components with up-to-date information. 
     */
    public void refreshUI(){
       
        this.maxSpeedSlider.setText(Double.toString(this.maxSpeed));
        this.speedSlider.setMaximum((int) this.maxSpeed);
        
        if (this.inManualMode){ // manual mode
            this.setSpeedButton.setEnabled(true);
            this.speedSlider.setEnabled(true);
            
        }else if (this.inManualMode == false){ // automatic mode
            this.setSpeedButton.setEnabled(false);
            this.speedSlider.setEnabled(false);
            
            
            // get suggested speed
            
            // set slider value to suggested speed
            
            // set speed
        }     
    }
    
    /**
     * Retrieves the set speed the user wants the train to go, which is determined by the speed slider.  
     * 
     * @return returns the value of the speed slider. 
     */
    public int getSetSpeed(){
        
        return this.speedSlider.getValue();
    }
    
    /**
     * Updates the speed label with that of the slider value. This allows the user to 
     * see what value the slider is on. Note,
     */
    public void setSpeedLabel(){
    
        this.currentSliderSpeedLabel.setText(Integer.toString(this.speedSlider.getValue()));
    }
    
    /**
     * Sets if the Speed Controller should run in Manual or Automatic mode. 
     * This value is set from the Train Controller class depending on the states 
     * of the radio buttons, "Automatic" and "Manual".
     * 
     * @param b true if in manual mode, false if in automatic mode. 
     */
    public void setManualMode(Boolean b){
        
        this.inManualMode = b; 
    }
    
    /**
     * Sets the max value of the speed controller slider. Note, this strictly only 
     * changes the slider based on the given parameter, and has nothing to do with the selected train.
     * 
     * @param max the value to change the speed controller's slider to.
     */
    public void setSliderMax(int max){
    
        this.speedSlider.setMaximum(max);
        this.maxSpeedSlider.setText(Integer.toString(this.speedSlider.getMaximum()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        speedSlider = new javax.swing.JSlider();
        setSpeedLabel = new javax.swing.JLabel();
        currentSliderSpeedLabel = new javax.swing.JLabel();
        minSpeedSlider = new javax.swing.JLabel();
        maxSpeedSlider = new javax.swing.JLabel();
        mphLabel = new javax.swing.JLabel();
        maxMPHLabel = new javax.swing.JLabel();
        minMPHLabel = new javax.swing.JLabel();
        setSpeedButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        speedSlider.setPaintTicks(true);

        setSpeedLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        setSpeedLabel.setText("Set Speed:");

        currentSliderSpeedLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        currentSliderSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentSliderSpeedLabel.setText("0");

        minSpeedSlider.setText("0");

        maxSpeedSlider.setText("100");

        mphLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        mphLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mphLabel.setText("MPH");

        maxMPHLabel.setText("MPH");

        minMPHLabel.setText("MPH");

        setSpeedButton.setText("Set Speed");
        setSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSpeed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setSpeedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(speedSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(maxSpeedSlider)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maxMPHLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(currentSliderSpeedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mphLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setSpeedLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minMPHLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(setSpeedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentSliderSpeedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mphLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(maxSpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(maxMPHLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(minSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(minMPHLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setSpeedButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Begins the process of changing the selected train's speed by using Power Control Law. 
     * 
     * FIX ME: This isn't complete yet! 
     * 
     * @param evt the sender of the action, i.e., the "Set Speed" button.
     */
    
    /**
     * @Bug If the train is accelerating, and the selected train is switched, the previous train will 
     * stop changing speeds. 
     * 
     * This should be looked into, and perhaps making switching trains unavailable while the current one
     * is changing speeds. 
     */
    private void setSpeed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSpeed
           
        String log;
        this.setSpeed = speedSlider.getValue();
        //this.beginPowerControl.start();
        
        log = "Telling train to set speed to " + setSpeed;
        logBook.add(log);
        this.printLogs();
    }//GEN-LAST:event_setSpeed

    /**
     * Determines if the the train's speed is equal to the speed the user set. 
     * The system will continue to run the Power Control until this function returns true. 
     * 
     * @return returns true if the train's speed is equal to the set speed, false otherwise. 
     */
//    private boolean speedsEqual(){
//    
//        // if train's speed is equal to the set speed, we can stop power control 
//        if (this.selectedTrain.speed == this.setSpeed ){ 
//            this.beginPowerControl.stop();
//            System.out.println("The train took " + this.timeElapsed + " s to get to " + this.setSpeed);
//            this.timeElapsed = 0; 
//            return true;
//        }
//        else {return false; }
//    }
    
    /**
     * Prints the stored logs to the operating log, then clears the logbook. 
     * The JTextPane must be set from the TrainController class before being used. 
     */
    public void printLogs(){
         
        if (this.operatingLogs != null && this.logBook.isEmpty() == false){
            for (String log : this.logBook){
    
                this.operatingLogs.setText(this.operatingLogs.getText() + log + "\n");
            }
        }
      
        this.logBook.clear();
    }

    /**
     * Regulates the train's speed using Power Law.
     */
    public void powerControl(){
            
        //String log;
         
        this.logBook.add("Set Speed: " + this.setSpeed);
        // calculate the error 
        double error = this.setSpeed - this.selectedTrain.getVelocity(); 
            
        this.powerCommandOut = this.selectedTrain.getKp() * error + this.selectedTrain.getKi();
        
        // send powerCommandOut to the train, which then changes its speed
        this.selectedTrain.powerCommand(this.powerCommandOut); 
          
        // if (this.powerCommand < 0){
            // this.selectedTrain.setServiceBrake(); 
        //}
        
        // train should maintain speed when powerCommandOut stays the same
         
        System.out.println(this.powerCommandOut);  
        
     
//        if (this.powerCommandOut < 0){ this.brakePanel.getServiceBrake().doClick(); } // train should slow down
//        else if (this.powerCommandOut > 0){
//            this.logBook.add("Full steam ahead!");
//            // speed up train by 1 MPH 
//            // FOR TESTING PURPOSES, THIS IS 1 MPH
//            this.selectedTrain.setSpeed(this.selectedTrain.getVelocity() + 1);
//            System.out.println("Velocity: " + this.selectedTrain.getVelocity()); 
//            
//        } // train should speed up
//        else if (this.powerCommandOut == 0){ this.logBook.add("Steady Power!!."); } // train should continue at same speed

        this.logBook.add(Integer.toString(timeElapsed));
        printLogs();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentSliderSpeedLabel;
    private javax.swing.JLabel maxMPHLabel;
    private javax.swing.JLabel maxSpeedSlider;
    private javax.swing.JLabel minMPHLabel;
    private javax.swing.JLabel minSpeedSlider;
    private javax.swing.JLabel mphLabel;
    private javax.swing.JButton setSpeedButton;
    private javax.swing.JLabel setSpeedLabel;
    private javax.swing.JSlider speedSlider;
    // End of variables declaration//GEN-END:variables
}
