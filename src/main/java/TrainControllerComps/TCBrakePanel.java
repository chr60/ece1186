/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerComps;

import TrackModel.Beacon;
import TrackModel.Block;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import TrainModel.*;
import java.util.HashMap;
import java.util.Set;

/**
 * This class is responsible for applying and managing the use of a train's braking 
 * system. In manual mode, this class must present a window to confirm the use of
 * the emergency brake to the user. 
 * 
 * This class collaborates with the Train, TCSpeedController, and the TCEmergencyFrame class. 
 * 
 * @author Andrew Lendacky
 */
public class TCBrakePanel extends javax.swing.JPanel {

    /**
     * The train whose brakes that are being controlled.
     * This variable is passed in from the Train Controller class. 
     * 
     */
    private Train selectedTrain;
    
    /**
     * Text area to print notifications to. 
     */
    private JTextArea operatingLogs; 
    
    /**
     * A list used to store up messages to print to the operating log. 
     */
    private LinkedList<String> logBook; 
    
    /**
     * Value used to determine if the system in Manual or Automatic mode. 
     * This field gets set by the Train Controller class. 
     */
    private boolean inManualMode; 
    
    
    /**
     * The speed controller object used to communicate between.
     */
    private TCSpeedController speedController; 
    
    /**
     * Used to choose between ignoring the suggested speed on the track or following it.
     */
    public boolean ignoreSpeed; 
    
    /**
     * Used to flag if there is an emergency, and we should use the emergency brake. 
     */
    public boolean isEmergency; 
    
    /**
     * Constructor for creating a TCBrakePanel object without a selected train. 
     * The selected train property must be passed in from the Train Controller class before being used. 
     */
    public TCBrakePanel() {
        initComponents();
        
        this.logBook = new LinkedList<String>(); // init logbook 
    }
    
    /**
     * Sets the selected train that this class is to control.
     * 
     * @param selectedTrain the train being controlled.
     */
    public void setSelectedTrain(Train selectedTrain){
    
        this.selectedTrain = selectedTrain; 
    }
    
    public Train getSelectedTrain(){
    
        return this.selectedTrain; 
    }
        
    /**
     * Sets the text field to be used for the operating logs. 
     * 
     * @param opLogs the area to display messages in. 
     */
    public void setOperatingLogs(JTextArea opLogs){
    
        this.operatingLogs = opLogs; 
    }
    
    /**
     * Prints the messages stored in the logbook to the operating log, then clears the logbook. 
     * 
     */
    private void printLogs(){
    
        // check if empty
        if (this.logBook.isEmpty() == false){
            // print logs
            for(String log : this.logBook){
                this.operatingLogs.setText(this.operatingLogs.getText() + log + "\n");
            }
        }
        
        this.logBook.clear();
    }
    
    /**
     * Set if the system should be in Manual or Automatic mode.
     * This method is called from the Train Controller class. 
     * 
     * @param b true if in Manual mode, false if in Automatic mode
     */
    public void inManualMode(boolean b){
    
        this.inManualMode = b; 
    }
    
    /**
     * Returns the service brake button. 
     * 
     * @return the service brake 
     */
    public JButton getServiceBrake(){
    
        return this.serviceBrake;
    }
    
    /**
     * Returns the emergency brake button. 
     * 
     * @return the emergency brake 
     */
    public JButton getEmgBrake(){
        
        return this.emergencyBrake;
    }
    
    /**
     * Checks if the train should come to a stop based on different criteria. 
     * 
     * @return returns true if the train needs to come to a complete stop, false if it doesn't.
     */
    private void shouldStopTrainChecks(){
            
        // stop if  
        //if (this.failureDetected()){ this.bringTrainToHalt(true); } //failure detected
             
        if (this.approachingStation()){ this.bringTrainToHalt(false); } // approaching a station
             
        //if (this.trainAhead()){ this.bringTrainToHalt(true); } // there's a train ahead

        this.willExceedAuthority();
        
    }
    
    /**
     * Determines if a there is any failure (Power, Signal, Engine) detected on the train. 
     * 
     * @return returns true if there is a failure, false otherwise.
     */
    private boolean failureDetected(){
    
        if (this.selectedTrain.isBrakeFailure() ||
                this.selectedTrain.isEngineFailure() || this.selectedTrain.isSignalFailure()){
       
            return true; 
        }
        return false; 
    }
    
    /**
     * Checks if the train is going to exceed authority, and stops the train if it will.
     * 
     */
    private void willExceedAuthority(){
                
        // were on our authority...
        System.out.println(this.selectedTrain.getAuthority().blockNum()); 
        
        if ( this.selectedTrain.getGPS().getCurrBlock().compareTo(this.selectedTrain.getAuthority()) == 0){
            
            double footprintSB = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistSB();
            double footprintEB = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistEB();
            // check if about to leave the block
            if (footprintSB == this.selectedTrain.getGPS().getCurrBlock().getLen()){ this.bringTrainToHalt(false); }
            else if (footprintEB > this.selectedTrain.getGPS().getCurrBlock().getLen()){ this.bringTrainToHalt(true); }
        }
    }
    
    
    /**
     * Brings a train to a stop using either the emergency brakes or the service brakes. 
     * Sending true as the parameter, will use the emergency brakes to bring the train to a halt. 
     * 
     * @param isEmergency says if to stop the train using the emergency brakes or the service brakes.
     */
    private void bringTrainToHalt(boolean isEmergency){
    
        this.ignoreSpeed = true; 
        this.isEmergency = isEmergency; 
    }
     
    /**
     * Determines if the train is approaching a station. 
     * 
     * @return returns true if the train is approaching a station, false otherwise. 
     */
    private boolean approachingStation(){
        
        Block currBlock = this.selectedTrain.getGPS().getCurrBlock();
     
        String distStr = this.getDistanceFromStation();

        if (distStr != null){ // start calculating safe breaking distance
            //double dist = Double.parseDouble(distStr);
            
            /**
             * FIX ME: This message should be able to be parsed into a number
             */
            this.logBook.add("Beacon Message: " + distStr);
            
            // determine when to begin braking the train
//            double footprint = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistSB(); 
//            
////            if (footprint >= dist){ return true; }
////            else{ return false; } 
        }
        return false; 
    }
    
    /**
     * Reads the message from the beacon from the current block the train is on,
     * and returns the distance to the next station.
     * 
     * @return returns the distance from the beacon to the station, -1 if there is no beacon.
     */
    private String getDistanceFromStation(){
        
        // get the distance from the beacon
        HashMap<Block, Beacon> beacons = this.selectedTrain.getBeacons(); 
        
        for (Block b : beacons.keySet()){
            
            //System.out.println("Block Number: " + b.blockNum()); 
            
            if (this.selectedTrain.getGPS().getCurrBlock().compareTo(b) == 0){
                //System.out.println("We are on a block with a Beacon");
                return beacons.get(b).getBeaconMessage(); // return the message on the beacon
            }
        }
          return null;
    }
        
    private boolean trainAhead(){
        
        //System.out.println("Train Ahead is not currently implemented yet."); 
        return false;    
    }
    
    /**
     * Sets the speed controller object to use.
     * 
     * @param speedController the Speed Controller object 
     */
    public void setSpeedController(TCSpeedController speedController){
    
        this.speedController = speedController;
    }
    
    /**
     * Refreshes the brake panel to determine if the train should stop for any reason. 
     * 
     */
    public void refreshUI(){
        
        this.shouldStopTrainChecks(); // checks to see if the train has to stop
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        emergencyBrake = new javax.swing.JButton();
        serviceBrake = new javax.swing.JButton();
        statusLabelService = new javax.swing.JLabel();
        functionLabelService = new javax.swing.JLabel();
        statusLabelEmg = new javax.swing.JLabel();
        functionLabelEmg = new javax.swing.JLabel();

        emergencyBrake.setText("Emergency Brake");
        emergencyBrake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initateEmergencyBrake(evt);
            }
        });

        serviceBrake.setText("Service Brake");
        serviceBrake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                engageServiceBrake(evt);
            }
        });

        statusLabelService.setText("Status:");

        functionLabelService.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        functionLabelService.setText("Off");

        statusLabelEmg.setText("Status:");

        functionLabelEmg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        functionLabelEmg.setText("Off");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(emergencyBrake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(serviceBrake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusLabelService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(functionLabelService, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(statusLabelEmg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(functionLabelEmg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabelEmg)
                    .addComponent(functionLabelEmg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emergencyBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabelService)
                    .addComponent(functionLabelService))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Initiates the emergency brake on the selected train.
     * If the system is in Manual mode, a TCEmergencyFrame object is created to confirm the use of the emergency 
     * brake. 
     * 
     * If the system is in Automatic mode, the speed of the selected train is decreased by the emergency 
     * brake deceleration constant. 
     * 
     * @param evt the send of the event, i.e., the 'Emergency Brake' button
     */
    private void initateEmergencyBrake(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initateEmergencyBrake

        if (this.selectedTrain != null){
        
            if (this.inManualMode == true){ 
                
                    this.selectedTrain.setEmergencyBrake( 1 );
                    this.functionLabelEmg.setText("On");
                    this.selectedTrain.setEmergencyBrake( 0 );
                    this.functionLabelEmg.setText("Off");
             }else if (this.inManualMode == false){
           
                // if the emergency brake is broke, this can't happen
                if (this.selectedTrain.getEmergencyBrake() != -1){
                    
                    this.selectedTrain.setEmergencyBrake( 1 );
                    this.functionLabelEmg.setText("On");
                    this.selectedTrain.setEmergencyBrake( 0 );
                    this.functionLabelEmg.setText("Off");
                }  
            }
        }

    }//GEN-LAST:event_initateEmergencyBrake
  
    /**
     * Initiates the service brake on the selected train. If the service brakes are 
     * broken, the service brake won't trigger.
     * 
     * @param evt the send of the event, i.e., the 'Service Brake' button
     */
    private void engageServiceBrake(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_engageServiceBrake
  
        if (this.selectedTrain != null){
        
            this.logBook.add("Engage the service brakes!"); 
        
            // make sure the train brakes are not broken
            if (this.selectedTrain.getServiceBrake() != -1){
                this.selectedTrain.setServiceBrake( 1 );
                this.functionLabelService.setText("On");
            
                this.selectedTrain.setServiceBrake( 0 );
                this.functionLabelService.setText("Off");

                this.printLogs();
            }
        }
    }//GEN-LAST:event_engageServiceBrake


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton emergencyBrake;
    private javax.swing.JLabel functionLabelEmg;
    private javax.swing.JLabel functionLabelService;
    private javax.swing.JButton serviceBrake;
    private javax.swing.JLabel statusLabelEmg;
    private javax.swing.JLabel statusLabelService;
    // End of variables declaration//GEN-END:variables
}
