package TrainControllerComps;

import TrackModel.Block;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TrainModel.*;
import javax.swing.JSlider;

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
     * A list that is used to hold logs to print to the Operating Logs of the Train Controller.
     */
    private LinkedList<String> logBook; // log book used to save logs and then print them to the notification windows

    /**
     * The speed that the train is desired to go. This is set by the user or
     * automatically depending on what mode the system is in.
     */
    private Double setSpeed;

    /**
     * A boolean value indicating if the Speed Controller is operating in Manual or Automatic mode.
     * This value is set from the Train Controller class.
     */
    private boolean inManualMode;

    /**
     * Brake panel used to control the brakes on the train if needed to slow down.
     */
    private TCBrakePanel brakePanel;


    /**
     * Used to calculate the power command.
     */
    private double error;

    private double vitalPwrCmdOne;

    private double vitalPwrCmdTwo;

    private double vitalPwrCmdThree;


    // MARK: - Constructors

    /**
     * Constructor for creating a new TCSpeedController object with no selected train.
     * The selected train must be set by the Train Controller before being used.
     *
     */
    public TCSpeedController() {

        initComponents();
        this.powerCommandOut = 0.0;
        this.error = 0.0; 
        this.logBook = new LinkedList<String>();

        // add action listensers
        this.speedSlider.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {

        String sliderValue = Double.toString(((double) speedSlider.getValue()/100.0));

        currentSliderSpeedLabel.setText(sliderValue);
        }

    });
    }

    // MARK: - Setters and Getters

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
    }

    /**
     * Returns the selected train.
     *
     * @return the selected train object.
     */
    public Train getTrain(){

        return this.selectedTrain;
    }

    public JSlider getSpeedSlider(){

        return this.speedSlider;
    }

    /**
     * Sets the max speed the train is allowed to go, and then updates the UI
     * so that the slider's max value is that of the maxSpeed and the correct speed
     * is on the label.
     *
     * @param maxSpeed the max speed the train is allowed to go.
     */
    public void setMaxSpeed(double maxSpeed){

        // update ui
        this.maxSpeedSlider.setText(String.format("%.2f", maxSpeed));
        
        String formattedString = String.format("%.2f", maxSpeed); 
        double d = Double.parseDouble(formattedString);
        
        this.speedSlider.setMaximum((int)(d*100));
    }

    /**
     * Sets the set speed that the train should go. 
     * 
     * In manual mode, if the set speed if greater than the 
     * suggested speed, the speed of the train is set to the block speed. 
     * 
     * In Automatic mode, if the set speed is greater than the suggested speed, 
     * the speed of the train is set to the suggested speed. 
     *
     * @param setSpeed the speed the train should go.
     */
    public void setSetSpeed(Double setSpeed){
   
        if (this.inManualMode){ // in manual mode
            if (setSpeed > this.selectedTrain.getSuggestedSpeed()){
                
                this.setSpeed = Double.parseDouble(String.format("%.2f", this.selectedTrain.getSuggestedSpeed()));; 
            }else{ this.setSpeed = setSpeed; }
        }else{ // in automatic mode
            if (setSpeed > this.selectedTrain.getSuggestedSpeed()){
                this.setSpeed = Double.parseDouble(String.format("%.2f", this.selectedTrain.getSuggestedSpeed())); 
            }else{ this.setSpeed = setSpeed; }
        }
    }

    /**
     * Refreshes all the UI components in the SpeedController, and regulates the
     * speed of the train if in Automatic mode.
     *
     */
    public void refreshUI(){

        Block currBlock = this.selectedTrain.getCurrBlock(); // get current block
        
        if (currBlock != null){
            
            Double speedLimit = .621371*this.selectedTrain.getGPS().getCurrBlock().getSpeedLimit();             
            this.setMaxSpeed(speedLimit);
            
            if (this.inManualMode){ this.manualModeChecks(); } // manual mode
            else if (this.inManualMode == false){ this.automaticModeChecks(); } // automatic mode
        }
    }
    
    public void automaticModeChecks(){
        
        Block currBlock = this.selectedTrain.getGPS().getCurrBlock();
        Double blockSpeedLimit = (.621371*currBlock.getSpeedLimit()); // get speed limit on block
        Double speedLimit = .621371*this.selectedTrain.getGPS().getCurrBlock().getSpeedLimit(); 
        
        // disable UI elements
        this.setSpeedButton.setEnabled(false);
        this.speedSlider.setEnabled(false);

        // get the block the train is on, and the set suggested speed

        Double blockSuggestedSpeed = .621371*this.selectedTrain.getSuggestedSpeed();

        if (blockSuggestedSpeed != null){

            // FOR TESTING
            if (blockSuggestedSpeed == 0.0){

                this.speedSlider.setValue(speedLimit.intValue());

                // ignore the speed 
                this.ignoreSpeedChecks();
                
            }else{ // !!= 0.0 THIS MAY BE A PROBLEM LATER

                this.speedSlider.setValue(speedLimit.intValue());

                // ignore the speed 
                this.ignoreSpeedChecks();
            }
            this.powerControl();
        }else if (blockSuggestedSpeed == null){

            this.speedSlider.setValue(speedLimit.intValue());
            this.powerControl();
        }      
    }
    
    public void manualModeChecks(){
        
        // enable ui elements
        this.setSpeedButton.setEnabled(true);
        this.speedSlider.setEnabled(true);

        Block currBlock = this.selectedTrain.getGPS().getCurrBlock();
        Double blockSpeedLimit = (.621371*currBlock.getSpeedLimit()); // get speed limit on block
                
        if (blockSpeedLimit != null){

            // if we changed to manual mode from automatic mode, we need to adjust to meet block limit
            if (this.setSpeed > blockSpeedLimit){ this.setSpeed = blockSpeedLimit; }

            // ignore the speed 
            this.ignoreSpeedChecks();
                 
        }else if (blockSpeedLimit == null){ this.ignoreSpeedChecks(); }
                                 
        this.powerControl();
    }
    
    /**
     * Checks to see if the train has to ignore the suggested speed placed on the track. 
     * This is mainly done when needing to come to stop the train to a halt using the brakes. 
     */
    private void ignoreSpeedChecks(){
           
        Double speedLimit = .621371*this.selectedTrain.getGPS().getCurrBlock().getSpeedLimit(); 
        if (speedLimit != null){
            if (this.brakePanel.ignoreSpeed == true && this.brakePanel.isEmergency == true){ this.setSpeed = 0.0; }
            else if (this.brakePanel.ignoreSpeed == true && this.brakePanel.isEmergency == false){ this.setSpeed = 0.0; }
            else{ this.setSpeed = (.621371*this.selectedTrain.getGPS().getCurrBlock().getSpeedLimit()); }   
        }
    }

    /**
     * Retrieves the set speed the user wants the train to go, which is determined by the speed slider.
     *
     * @return returns the value of the speed slider.
     */
    public Double getSetSpeed(){

        return this.setSpeed;
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
//    public void setSliderMax(int max){
//
//        this.speedSlider.setMaximum(max);
//        this.maxSpeedSlider.setText(Integer.toString(this.speedSlider.getMaximum()));
//    }

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
        this.setSpeed = (double)this.speedSlider.getValue()/100.0;
        
        log = "Telling train to set speed to " + this.setSpeed;
        this.brakePanel.resetBrakingConditions();
        this.logBook.add(log);
        this.printLogs();
    }//GEN-LAST:event_setSpeed

    /**
     * Prints the stored logs to the operating log, then clears the logbook.
     * The JTextArea must be set from the TrainController class before being used.
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
     * If the train's speed is greater than the set speed,
     * the train's service brake is pressed to slow the train down.
     *
     * This is called every second.
     *
     */
    public void powerControl(){

        // train is going too fast
        if (this.selectedTrain.getVelocity() >= this.setSpeed){
            
            if (this.brakePanel.isEmergency == true){ this.brakePanel.getEmgBrake().doClick(); } // apply ebrakes
            else{ this.brakePanel.getServiceBrake().doClick(); }
        }else{
            this.logBook.add("Set Speed: " + this.setSpeed);

            if (this.setSpeed != 0.0){ this.error = this.error + (this.setSpeed - this.selectedTrain.getVelocity()); } // calculate the error
            else{ this.error = (this.setSpeed - this.selectedTrain.getVelocity()); }
            
            //this.vitalPwrCmdOne = (this.setSpeed - this.selectedTrain.getVelocity());
            //this.vitalPwrCmdTwo = (this.setSpeed - this.selectedTrain.getVelocity());
            //this.vitalPwrCmdThree = (this.setSpeed - this.selectedTrain.getVelocity());

            this.powerCommandOut = this.selectedTrain.getKp() * error + this.selectedTrain.getKi()*this.selectedTrain.getVelocity();

            this.vitalPwrCmdOne = this.selectedTrain.getKp() * error + this.selectedTrain.getKi()*this.selectedTrain.getVelocity();
            this.vitalPwrCmdTwo = this.selectedTrain.getKp() * error + this.selectedTrain.getKi()*this.selectedTrain.getVelocity();
            this.vitalPwrCmdThree = this.selectedTrain.getKp() * error + this.selectedTrain.getKi()*this.selectedTrain.getVelocity();

            if (this.vitalPwrCmdOne == this.powerCommandOut){
                if (this.vitalPwrCmdTwo == this.powerCommandOut){
                    if (this.vitalPwrCmdThree == this.powerCommandOut){
                        //this.logBook.add("VITAL SYSTEM CHECK PASS!");
                        this.selectedTrain.powerCommand(this.powerCommandOut);
                    }
                }
            }
            printLogs();
        }
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
