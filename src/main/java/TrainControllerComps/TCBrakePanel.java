/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainControllerComps;

import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import TrainModel.*;

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
    
    
    private TCSpeedController speedController; 
    
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
    private boolean shouldStopTrainChecks(){
            
        // stop if  
        if (this.failureDetected()){ return true; } //failure detected
        
        if (this.approachingStation()){ return true; } // approaching a station
             
        if (this.trainAhead()){ return true; } // there's a train ahead
 
        
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
       
            return true; 
        }
        return false; 
    }
    
    private void willExceedAuthority(){
        
        //this.logBook.add("Calling Will Exceed Authority"); 
        System.out.println(this.selectedTrain.getGPS().getCurrBlock().nextBlockForward().blockNum());
        System.out.println(this.selectedTrain.getGPS().getCurrBlock().nextBlockBackward().blockNum());
        
        //System.out.println(this.selectedTrain.getGPS().getCurrBlock().compareTo(this.selectedTrain.getAuthority())); 
        
        if ( (this.selectedTrain.getGPS().getCurrBlock().nextBlockForward().compareTo(this.selectedTrain.getAuthority()) == 0) || 
                (this.selectedTrain.getGPS().getCurrBlock().nextBlockBackward().compareTo(this.selectedTrain.getAuthority()) == 0)){
        
            this.logBook.add("Next Block is Authority!"); 
            
            double footprintSB = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistSB();
            double footprintEB = this.selectedTrain.getGPS().getDistIntoBlock() + this.selectedTrain.getSafeBrakingDistEB();
            
            if (footprintSB == this.selectedTrain.getGPS().getCurrBlock().getLen()){
                this.logBook.add("Authority 1");
                this.speedController.setSetSpeed(0);
            }else if (footprintEB > this.selectedTrain.getGPS().getCurrBlock().getLen()){
                this.logBook.add("Authority 2");
                this.speedController.setSetSpeed(0);
            }
        }
        
        this.printLogs();
    }
    
    /**
     * Determines if the train is approaching a station. 
     * 
     * @return returns true if the train is approaching a station, false otherwise. 
     */
    private boolean approachingStation(){
    
        System.out.println("Approaching Station is not currently implemented yet."); 
        return false; 
    }
    
    private boolean trainAhead(){
        
        System.out.println("Train Ahead is not currently implemented yet."); 
        return false;    
    }
    
    public void setSpeedController(TCSpeedController speedController){
    
        this.speedController = speedController;
    }
    
    public void refreshUI(){
        
        // we need to stop the train
        /**
         * @bug The train needs to use the emergency brakes if there's an emergency and needs to slow down.
         * Currently, this uses the service brakes.
         * 
         * FIX ME: Have it use the emergency brake to stop in a hurry.
         */
        //if (this.shouldStopTrainChecks()){ this.speedController.setSetSpeed(0); }
        
        this.willExceedAuthority();
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
        
                TCEmergencyFrame window = new TCEmergencyFrame(this.selectedTrain); 
           

                window.setOperatingLog(this.operatingLogs);
                window.setVisible(true);
                window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
