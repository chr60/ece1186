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
    
    private JTextArea announcementLogs;
    
    /**
     * A list used to store up messages to print to the operating log. 
     */
    private LinkedList<String> operatingLogbook; 
    
    /**
     * A list used to store up announcements to make to the announcement panel. 
     */
    private LinkedList<String> announcementLogbook;
    
    /**
     * Value used to determine if the system in Manual or Automatic mode. 
     * This field gets set by the Train Controller class. 
     */
    private boolean inManualMode; 
     
    public Double distanceTraveled = 0.0; 
    
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
    
    
    public boolean approachingStation; 
    
    public String message; 
    
    public int tickNum;
    
    public boolean atStation; 
    /**
     * Constructor for creating a TCBrakePanel object without a selected train. 
     * The selected train property must be passed in from the Train Controller class before being used. 
     */
    public TCBrakePanel() {
        initComponents();
        
        this.operatingLogbook = new LinkedList<String>(); // init logbook 
        this.announcementLogbook = new LinkedList<String>(); // init logbook 
        
        this.distanceTraveled = 0.0; 
        this.tickNum = 0; 
        this.atStation = false; 
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
     * Sets the text area to be used for the announcement logs.
     * 
     * @param annoucnementLogs the area to display announcements in.
     */
    public void setAnnouncementLogs(JTextArea annoucnementLogs){
    
        this.announcementLogs = annoucnementLogs;
    }
    
    /**
     * Prints the messages stored in the logbook to the operating log, then clears the logbook. 
     * 
     */
    private void printLogs(){
    
        // check if empty
        if (this.operatingLogbook.isEmpty() == false){
            // print logs
            for(String log : this.operatingLogbook){
                this.operatingLogs.setText(this.operatingLogs.getText() + log + "\n");
            }
        }
        
        this.operatingLogbook.clear();
        
        if (this.announcementLogbook.isEmpty() == false){
        
            for (String announcement : this.announcementLogbook){
                this.announcementLogs.setText(this.announcementLogs.getText() + announcement + "\n");
            }
        }
        
        this.announcementLogbook.clear();
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
    private boolean shouldStopTrainChecks(){
                    
        // stop if  
        if (this.failureDetected()){ 
            
            this.bringTrainToHalt(true);
            
            // if train is stopped..
            if (this.selectedTrain.getVelocity() == 0.0){
                
                this.requestFix(); // request fix
                this.operatingLogbook.add("Request to Fix Train Made!");
                this.printLogs();
            }
            
            return true;
        } //failure detected
             
        if (this.startApproachingStation() || this.atStation == true){ // approaching a station
            
            this.bringTrainToHalt(false); 
            
            if (this.selectedTrain.getVelocity() == 0.0){
            
                // wait for 6 clock ticks
                if (this.tickNum == 0){
                    this.approachingStation = false; 
                    this.atStation = true; 
                    this.announcementLogbook.add("Arriving at " + this.selectedTrain.getGPS().getCurrBlock().getStationName());
                    this.printLogs();
                    this.tickNum++; 
                }else if (this.tickNum == 1){ // open left doors
                    this.selectedTrain.setLeftDoor(1);
                    this.tickNum++; 
                }else if (this.tickNum == 2){
                    this.tickNum++; 
                }else if (this.tickNum == 3){
                    this.selectedTrain.setLeftDoor(0);
                    this.selectedTrain.setRightDoor(1);
                    this.tickNum++; 
                }else if (this.tickNum == 4){
                    this.tickNum++; 
                }else if (tickNum == 5){
                    this.selectedTrain.setRightDoor(0);
                    this.tickNum++; 
                }else if (this.tickNum == 6){
                    this.resetBrakingConditions(); // continue as normal
                    this.announcementLogbook.add("Departing " + this.selectedTrain.getGPS().getCurrBlock().getStationName());
                    this.printLogs();
                    this.distanceTraveled = 0.0; 
                    this.tickNum = 0; 
                    this.atStation = false; 
                }      
            } 
            return true;
        }
        
        
        if (this.selectedTrain.getSuggestedSpeed() == -1.0){ // pick up emergency signal
            this.bringTrainToHalt(true); // stop immediately w/ e-brake
            return true;
        } 
             
        //if (this.trainAhead()){ this.bringTrainToHalt(true); } // there's a train ahead

        /**
         * @bug The train will stop at the authority, but then reset and begin
         * back on its way. Look into the flags being reset. 
         * 
         */
        if (this.willExceedAuthority()){ return true; }
        
        return false;
    }
    
    /**
     * Determines if a there is any failure (Power, Signal, Engine) detected on the train. 
     * 
     * @return returns true if there is a failure, false otherwise.
     */
    private boolean failureDetected(){
    
        if (this.selectedTrain.isBrakeFailure() ||
                this.selectedTrain.isEngineFailure() || this.selectedTrain.isSignalFailure()){
            this.operatingLogbook.add("Failure detected!"); 
            this.printLogs();
            return true; 
        }
        return false; 
    }
    
    /**
     * Sends a request to the train to tell the wayside controller that the train needs to be fixed. 
     * 
     */
    private void requestFix(){
        
        //this.selectedTrain.requestFix(true); 
    }

    /**
     * Checks if the train is going to exceed authority, and stops the train if it will using the service brake.
     */
    private boolean willExceedAuthority(){
                
        // were on our authority...        
        if ( this.selectedTrain.getGPS().getCurrBlock().compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){
            
            double footprintSB = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistSB();
            
            // check if about to leave the block
            if (footprintSB >= this.selectedTrain.getGPS().getCurrBlock().getLen()){ this.bringTrainToHalt(false); return true; }
        }
        return false;
    }
    
    /**
     * Checks to see if a train is at it's given authority. 
     * 
     * @return returns true if the train is at the given authority, false otherwise. 
     */
    private boolean isAtAuthority(){
      
        if (this.selectedTrain.getGPS().getCurrBlock().compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){ return true; }
       
        return false;
    }
    
    
    /**
     * Brings a train to a stop using either the emergency brakes or the service brakes. 
     * Sending true as the parameter, will use the emergency brakes to bring the train to a halt. 
     * 
     * @param isEmergency says if to stop the train using the emergency brakes or the service brakes.
     */
    public void bringTrainToHalt(boolean isEmergency){
    
        this.ignoreSpeed = true; 
        this.isEmergency = isEmergency; 
    }
    
    /**
     * Resets the flags used to tell the train to ignore the suggested speed and to use the emergency brake.
     */
    public void resetBrakingConditions(){
    
        this.ignoreSpeed = false; 
        this.isEmergency = false; 
    }
    
    /**
     * Reads the message from the beacon from the current block the train is on,
     * and returns the distance to the next station. Sets a flag when this condition is met to siganl, a 
     * train is approaching the station.
     * 
     */
    private void approachingStation(){
        // get the distance from the beacon
        HashMap<Block, Beacon> beacons = this.selectedTrain.getBeacons(); 
        
        for (Block b : beacons.keySet()){
            
            Block currBlock = this.selectedTrain.getGPS().getCurrBlock(); 
            if (currBlock.compareTo(b) == 0){
                Beacon beacon = beacons.get(b); 
                
                if (this.selectedTrain.getGPS().getDistIntoBlock() >= beacon.getDistance()){ // at a beacon
                    this.message = beacon.getBeaconMessage();
                    this.approachingStation = true; 
                }
            }
        }
    }
    
    
    /**
     * 
     * Determines the distance traveled during a given amount of seconds;
     * 
     * @param Drate
     * @param stopTime
     * @return 
     */
    private Double distanceToStop(Double stopTime){
	
        //using S = Vi(t) + (1/2)(a)(t^2)  to compute distance    
	Double stopDist = (this.selectedTrain.getVelocity()*0.44704)*(stopTime) + (1/2)*(this.selectedTrain.deccelRate(-1.2))*(Math.pow(stopTime, 2));
	return stopDist; 				
    }
       
    /**
     * Starts calculating distance from the beacon to the station once a beacon is detected. 
     * 
     * @return returns true if the distance from the train to the station is less than the safe braking distance.
     */
    private boolean startApproachingStation(){
    
        this.approachingStation(); 
        
        if (this.approachingStation == true){ // start to calculate distance
                    
            Double x = Double.parseDouble(this.message); // parse message
            
            Double distElapsed = this.distanceToStop(1.0); // distanced traveled in 1 second
            Double distTrainToStation = x - this.distanceTraveled; 

            this.distanceTraveled = this.distanceTraveled + distElapsed; // total distance traveled
                    
            this.operatingLogbook.add("Distance to station: " + distTrainToStation + " m."); 
                    
            if (distTrainToStation <= this.selectedTrain.getSafeBrakingDistSB()){ return true; }
        }
               
        return false; 
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
        
        if (this.shouldStopTrainChecks()){ // checks to see if the train has to stop
            // we will stop the train..
        }else if(this.isAtAuthority()){
            // dont move if at authority       
        }else if(this.atStation){
            // hold
        }else{ this.resetBrakingConditions(); }
            this.printLogs();
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
                engageEmergencyBrake(evt);
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
    private void engageEmergencyBrake(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_engageEmergencyBrake

        if (this.selectedTrain != null){
        
            if (this.inManualMode == true){ 
                
                this.selectedTrain.setEmergencyBrake(1);
                this.selectedTrain.setEmergencyBrake(0);
            }     
        }
    }//GEN-LAST:event_engageEmergencyBrake
  
    /**
     * Initiates the service brake on the selected train. If the service brakes are 
     * broken, the service brake won't trigger.
     * 
     * @param evt the send of the event, i.e., the 'Service Brake' button
     */
    private void engageServiceBrake(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_engageServiceBrake
  
        if (this.selectedTrain != null){
        
            this.operatingLogbook.add("Engage the service brakes!"); 
            
            if (this.selectedTrain.getServiceBrake() != -1){ // make sure the train brakes are not broken
                this.selectedTrain.setServiceBrake(1); // turn on brakes            
                this.selectedTrain.setServiceBrake(0); // turn off brakes
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
