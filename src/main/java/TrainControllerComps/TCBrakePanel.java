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
import java.util.Stack;

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
     */
    private Train selectedTrain;

    /**
     * Text area to print notifications to.
     */
    private JTextArea operatingLogs;

    /**
     * Text area to print announcements to.
     */
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
     * Indicates if a train is approaching a station.
     */
    private boolean approachingStation;

    /**
     * The message contained in a beacon indicating how far the station is
     * from the beacon.
     */
    private String beaconDistanceMessage;
    
    /**
     * The name of the station that the train is approaching. 
     */
    public String approachingStationName; 
    
    /**
     * Counter to indicate how long the train was at the station for. 
     */
    private int waitingAtStationCounter;

    /**
     * Indicates if a train is currently at a station.
     */
    private boolean atStation;
    
    /**
     * Flag used to indicate when a train will start to exceed the authority.
     */
    private boolean willExceedAuthority; 
    
    /**
     * The distance from the from the train when the flag is first set, to the authority block.
     */
    private Double distanceToAuthority; 
        
    /**
     * Indicated what side of the track the station is on. 
     */
    private String stationSide; 
    
    /**
     * Distance traveled when the train reads a beacon and should start preparing 
     * to stop at a station. 
     */
    public Double distanceTraveledToStation = 0.0;
    
    /**
     * Distance that the train has traveled since detecting the authority is approaching. 
     */
    public Double distanceTraveledToAuthority = 0.0; 
    
    /**
     * Stack used to hold the last visited station. 
     */
    private Stack<String> visitedStationStack;
    
    /**
     * Constructor for creating a TCBrakePanel object without a selected train.
     * The selected train property must be passed in from the Train Controller class before being used.
     */
    public TCBrakePanel() {
        initComponents();

        this.operatingLogbook = new LinkedList<String>(); // init logbook
        this.announcementLogbook = new LinkedList<String>(); // init logbook

        this.distanceTraveledToStation = 0.0;
        this.distanceTraveledToAuthority = 0.0; 
        this.waitingAtStationCounter = 0;
        this.atStation = false;
        this.visitedStationStack = new Stack<String>(); 
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
     * Returns the next 'x' blocks from the train's current block.
     * 
     * @param numBlocksAhead the number of blocks you want to look ahead
     * @return a list containing the next 'x' blocks
     */
    private LinkedList<Block> blocksAhead(int numBlocksAhead){
    
        Block currBlock = this.selectedTrain.getGPS().getCurrBlock(); 
        Block nextBlock = currBlock; 
        LinkedList<Block> nextBlocks = new LinkedList<Block>(); 
        
        for (int i = 0; i < numBlocksAhead; i++){
        
            nextBlock = nextBlock.nextBlockForward(); 
            nextBlocks.add(nextBlock);
        }
        
        return nextBlocks; 
    }
    
    /**
     * Returns the last 'x' blocks from the train's current block.
     * 
     * @param numBlocksBehind the number of blocks to look behind
     * @return a list containing the last 'x' blocks.
     */
    private LinkedList<Block> blocksBehind(int numBlocksBehind){
    
        Block currBlock = this.selectedTrain.getGPS().getCurrBlock(); 
        Block nextBlock = currBlock;     
        LinkedList<Block> nextBlocks = new LinkedList<Block>(); 
        
        for (int i = 0; i < numBlocksBehind; i++){
       
            nextBlock = nextBlock.nextBlockBackward(); 
            nextBlocks.add(nextBlock);
        }
        
        return nextBlocks; 
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

            this.atStationProtocol();
            return true;
        }
        
        // stop train is -1 is seen on the track
        if (this.selectedTrain.getSuggestedSpeed().equals(new Double(-1.0))){ this.bringTrainToHalt(false); return true; }

        if (this.willExceedAuthority()){ return true; }

        return false;
    }
    
    /**
     * Tells the train what to do when arriving at a station.
     * 
     * Currently, the train waits 60 s at the station before leaving. 
     */
    private void atStationProtocol(){
              
        if (this.selectedTrain.getVelocity() == 0.0) {

            if (this.waitingAtStationCounter == 0){ // make announcement
            
                this.approachingStation =false; 
                this.atStation = true; 
                this.announcementLogbook.add("Arriving at " + this.selectedTrain.getGPS().getCurrBlock().getStationName());
                
                // pop old station and add new one
                if (this.visitedStationStack.isEmpty() == false) {this.visitedStationStack.pop(); }
                this.visitedStationStack.add(this.selectedTrain.getGPS().getCurrBlock().getStationName());

                this.waitingAtStationCounter++;
            }else if (this.waitingAtStationCounter == 1){ // open doors
            
                if (this.stationSide.equals("L")){ this.selectedTrain.setLeftDoor(1); }
                else if (this.stationSide.equals("R")){ this.selectedTrain.setRightDoor(1); }
                this.selectedTrain.updatePassengerCount();
		this.waitingAtStationCounter++; 

            }else if (this.waitingAtStationCounter == 11){ // close the doors
                
                if (this.stationSide.equals("L")){ this.selectedTrain.setLeftDoor(0); }
                else if (this.stationSide.equals("R")){ this.selectedTrain.setRightDoor(0); }
                this.waitingAtStationCounter++; 
            }else if (this.waitingAtStationCounter == 13){ 
                
                this.announcementLogbook.add("All Aboard! *Choo Choo*");
                this.waitingAtStationCounter++; 
            }else if (this.waitingAtStationCounter == 30){
                
                this.announcementLogbook.add("We will be taking off shortly.");
                this.waitingAtStationCounter++; 
            }else if (this.waitingAtStationCounter == 60){
                
                this.resetBrakingConditions(); // continue as normal
                this.announcementLogbook.add("Departing " + this.selectedTrain.getGPS().getCurrBlock().getStationName());
                this.printLogs();
                this.distanceTraveledToStation = 0.0;
                this.waitingAtStationCounter = 0;
                this.approachingStationName = null; 
                this.atStation = false;
            }else{
                
                this.waitingAtStationCounter++; 
            }  
            this.printLogs();
        }
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
     */
    private void requestFix(){

        this.selectedTrain.requestFix(true);
    }

    /**
     * Uses the authority of the train to determine if in Fixed Block Mode or 
     * Moving Block Overlay. 
     * 
     * @return returns true if in FBM, false if in MBO
     */
    private boolean inFBM(){
    
        if (this.selectedTrain.getAuthority().getDistIntoBlock() == null){ return true; }
        else{ return false; }
    }
    
    /**
     * Checks if the train is going to exceed its authority 
     * when in MBO mode, and stops if it will using the service brake. 
     * 
     * @return returns true if the train will exceed authority. 
     */
    private boolean willExceedAuthorityMBO(){
    
        if (this.willExceedAuthority == false){
        
            if (this.blocksAhead(3).contains(this.selectedTrain.getAuthority().getCurrBlock())){
                
                this.willExceedAuthority = true; 
                this.distanceToAuthority = this.selectedTrain.getGPS().getCurrBlock().getLen(); 
                
                for (Block b : this.blocksAhead(3)){
                
                    if (b.compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){
                        this.distanceToAuthority = this.distanceToAuthority + this.selectedTrain.getAuthority().getDistIntoBlock(); 
                        break; 
                    }else{
                        this.distanceToAuthority = this.distanceToAuthority + b.getLen();
                    }
                }
            
            }  
        }
        
        
        if (this.willExceedAuthority == false){
        
            if (this.blocksBehind(3).contains(this.selectedTrain.getAuthority().getCurrBlock())){

                this.willExceedAuthority = true; 
                this.distanceToAuthority = this.selectedTrain.getGPS().getCurrBlock().getLen();

                for (Block b : this.blocksBehind(3)){
                
                    if (b.compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){
                        this.distanceToAuthority = this.distanceToAuthority + this.selectedTrain.getAuthority().getDistIntoBlock(); 
                        break; 
                    }else{
                        this.distanceToAuthority = this.distanceToAuthority + b.getLen();
                    }
                }
            }
        }
        
        if (this.willExceedAuthority == true){ // start calculating distance until we hit authority
        
            Double dist = this.distanceTraveled(1.0); // dist in 1 second
            this.distanceTraveledToAuthority = this.distanceTraveledToAuthority + dist; // total distanced traveled
            
            // stop train if about to exceed. 
            if (this.distanceTraveledToAuthority + this.selectedTrain.getSafeBrakingDistSB() >= this.distanceToAuthority){
            
                this.bringTrainToHalt(false);
                return true; 
            }        
        }
        
        return false; 
    }
    
    /**
     * Checks if the train is going to exceed authority when in FBM,
     * and stops the train if it will using the service brake.
     * 
     * @return returns true if the train will exceed authority. 
     */
    private boolean willExceedAuthorityFBM(){

        // check block ahead
        if (this.willExceedAuthority == false){
        
            if (this.blocksAhead(3).contains(this.selectedTrain.getAuthority().getCurrBlock())){
                
                this.willExceedAuthority = true; 
                this.distanceToAuthority = this.selectedTrain.getGPS().getCurrBlock().getLen(); 
                
                for (Block b : this.blocksAhead(3)){
                    this.distanceToAuthority = this.distanceToAuthority + b.getLen();
                    
                    // only count distance from train to authority
                    if (b.compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){
                    
                        break;
                    }
                }    
            }   
        }
        // check blocks behind
        if (this.willExceedAuthority == false){
        
            if (this.blocksBehind(3).contains(this.selectedTrain.getAuthority().getCurrBlock())){

                this.willExceedAuthority = true; 
                this.distanceToAuthority = this.selectedTrain.getGPS().getCurrBlock().getLen();

                for (Block b : this.blocksBehind(3)){
                    this.distanceToAuthority = this.distanceToAuthority + b.getLen();
                    
                    // only count distance from train to authority
                    if (b.compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){
                    
                        break;
                    }
                }
            }
        }
        
        if (this.willExceedAuthority == true){
        
            Double dist = this.distanceTraveled(1.0);
            this.distanceTraveledToAuthority = this.distanceTraveledToAuthority + dist; 
            
            if (this.distanceTraveledToAuthority + this.selectedTrain.getSafeBrakingDistSB() >= this.distanceToAuthority){
            
                this.bringTrainToHalt(false);
                return true; 
            }
            
        }
        
        return false;
    }

    /**
     * Checks if the train will exceed authority depending on the mode of the system. 
     * 
     * @return returns true if the train is about to exceed authority.
     */
    private boolean willExceedAuthority(){
    
        if(this.inFBM()){ return this.willExceedAuthorityFBM(); } 
        else{ return this.willExceedAuthorityMBO(); }
    }
    
    /**
     * Checks to see if a train is at it's given authority.
     *
     * @return returns true if the train is at the given authority, false otherwise.
     */
    private boolean isAtAuthority(){
        
        if (this.selectedTrain.getGPS().getCurrBlock().compareTo(this.selectedTrain.getAuthority().getCurrBlock()) == 0){

            return true;
        }

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
     * Reads the beaconDistanceMessage from the beacon from the current block the train is on,
     * and returns the distance to the next station. Sets a flag when this condition is met to signal a
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

                    String[] split = beacon.getBeaconMessage().split(","); 
                    String stationName = split[1].replace("_", " ");
                    this.approachingStationName = stationName; 
                    String stationPeek = ""; 
                    if (this.visitedStationStack.isEmpty() == false) { stationPeek = this.visitedStationStack.peek(); }
                    
                    if (stationPeek.equals(stationName) == false || this.visitedStationStack.isEmpty()){ // we werent just at this station
                        this.beaconDistanceMessage = split[0]; // get distance to station
                        this.stationSide = split[2]; // get the station side
                        this.approachingStation = true;                
                    }
                }
            }
        }
    }

    /**
     *
     * Determines the distance traveled during a given amount of seconds;
     *
     * @param stopTime the time to determine the distance covered in.
     * @return the distanced traveled. 
     */
    private Double distanceTraveled(Double stopTime){

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

        /**
         * @bug Sometimes the train overshoots the station by around 1.7 m. 
         * This might have to do with where the beacons are placed. 
         */
        
        
        /**
         * @bug The train doesn't stop at Station Square on the way back around the track.
         * This is most likely due to the distance from the beacon to the station not being fully accurate.
         */
        
        this.approachingStation();

        if (this.approachingStation == true){ // start to calculate distance

            Double x = Double.parseDouble(this.beaconDistanceMessage); // parse beaconDistanceMessage
            System.out.println("Beacon Message: " + x); 
            Double distElapsed = this.distanceTraveled(1.0); // distanced traveled in 1 second
            Double distTrainToStation = x - this.distanceTraveledToStation;

            this.distanceTraveledToStation = this.distanceTraveledToStation + distElapsed; // total distance traveled
            this.operatingLogbook.add("Distance to station: " + String.format("%.2f", distTrainToStation) + " m.");

            if(this.inManualMode == false){
                if (distTrainToStation <= this.selectedTrain.getSafeBrakingDistSB()){ return true; }
            }
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

        }else if(this.atStation){
            // hold
            System.out.println("At station: " + this.atStation); 
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
            this.selectedTrain.setEmergencyBrake(1);
            this.selectedTrain.setEmergencyBrake(0);
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
