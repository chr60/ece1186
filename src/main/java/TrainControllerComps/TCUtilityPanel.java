package TrainControllerComps;

import CommonUIElements.ConfirmationWindow;
import java.awt.Color;
import java.awt.event.ItemEvent;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A component of the Train Controller responsible for displaying, and transmitting 
 * information and changes to the train corresponding to the Air Conditioning unit (AC), 
 * Heating unit, Left/Right Doors, and Lights. 
 * 
 * This class collaborates with a Train Model, and the TrainController class, which 
 * it is a sub-component of.
 * 
 * @author Andrew Lendacky
 */
public class TCUtilityPanel extends javax.swing.JPanel {

    /**
     * The train in which the UI should update from based on its values. 
     * This object is passed from the parent Train Controller.
     */
    private TestTrain selectedTrain; 
        
    /**
     * A boolean used to determine if the component should operate in manual or automatic mode.
     * This value gets set from the TrainController class. 
     */
    private boolean inManualMode; 
    
    /**
     * Button used to display when a failure has occurred. 
     * This button comes from the Train Controller. 
     */
    private JButton vitalsButton; 
    
    /**
     * Constructor for creating a new TCUtiltiyPanel object where the initial positions of the radio button
     * are in the 'OFF' position, and there is no selected train. 
     * 
     * By default, this constructor sets the 'inManualMode' to false.
     * Selected train must be set by the Train Controller before being used. 
     */
    public TCUtilityPanel() {
             
        initComponents();
              
        // FIX ME: this should be put in refreashUI() when the train model is complete
        this.acOffRadioButton.setSelected(true);
        this.heatOffRadioButton.setSelected(true);
        this.lightsOffRadioButton.setSelected(true);
        this.leftDoorsCloseRadioButton.setSelected(true);
        this.rightDoorsCloseRadioButton.setSelected(true);
        
        this.inManualMode = false; 
        this.selectedTrain = null; 
        
        this.acFailureRadioButton.setEnabled(false);
        this.heatFailureRadioButton.setEnabled(false);
        this.leftDoorsFailureRadioButton.setEnabled(false);
        this.rightDoorsFailureRadioButton.setEnabled(false);
        this.lightsFailureRadioButton.setEnabled(false);
    }
    
    /**
     * Sets the train that this UtilityPanel should control. 
     * This value should be set from the Train Controller class when a new train is selected. 
     * 
     * @param train the train controlled by the Train Controller. 
     */
    public void setSelectedTrain(TestTrain train){
 
        this.selectedTrain = train; 
    }
    
    /**
     * Retrieves the train that the Utility Panel is controlling. 
     * 
     * @return returns the selected train. 
     */
    public TestTrain getSelectedTrain(){
        
        return this.selectedTrain; 
    }
    
    /**
     * Sets the vital button. 
     * This method should be called from the Train Controller. 
     * 
     * @param vitalsButton vital button from the Train Controller
     */
    public void setVitalsButton(JButton vitalsButton){
    
        this.vitalsButton = vitalsButton; 
    }
    
    /**
     * Sets if the Utility Panel should run in Manual or Automatic mode. 
     * This value is set from the Train Controller class depending on the states 
     * of the radio buttons, "Automatic" and "Manual".
     * 
     * @param b true if in manual mode, false if in automatic mode. 
     */
    public void setManualMode(boolean b){
        
        this.inManualMode = b; 
    }
    
    /**
     * Updates the UI of the radio buttons (On, Off, Fail, etc..) of the 
     * doors, lights, air conditioning, and heating unit depending on the values 
     * from the selected train. 
     *
     */
    public void refreshUI(){
                
        // disable/endable certain UI elements depending on mode   
        this.setModeUI();
      
        // set utility stuff based on train information
        this.refreshAC();
        this.refreshHeat();
        this.refreshLights();
        this.refreshLeftDoors();
        this.refreshRightDoors(); 
        
        // change the vital button to red if there is a power failure.         
        if (this.isPowerFailure()){ this.vitalsButton.setForeground(Color.red); }
        
        /**
         * @Bug When selected a train from a TCDispatchedTrainsFrame, a NullPointerException is thrown. 
         * 
         */
        else if (this.isPowerFailure() == false){this.vitalsButton.setForeground(new Color(0,0,0));}
    }
    
    /**
     * Determines if there is a failure in one of utilities. 
     * 
     * @return returns true if at least one of the utilities are in a failure state, false otherwise. 
     */
    private boolean isPowerFailure(){
    
        if (this.acFailureRadioButton.isSelected() || 
                this.heatFailureRadioButton.isSelected() || this.lightsFailureRadioButton.isSelected()
                || this.leftDoorsFailureRadioButton.isSelected() || this.rightDoorsFailureRadioButton.isSelected()){
            return true; // there is a power failure..
        }
        
        return false; // there isn't a power failure
    }
    
    /**
     * Sets the UI elements that the user can interact with depending on the mode the Utility Panel is in. 
     */
    private void setModeUI(){
        
        // manual mode
        if (this.inManualMode == true){
            
            // enable setting temp
            this.setAirCondBotton.setEnabled(true);
            this.setHeatButton.setEnabled(true);

            this.acTempTextField.setEditable(true);
            this.heatTempTextField.setEditable(true);
            
            this.enableRadioButtons();
            
        }
        // automatic mode stuff
        else if (this.inManualMode == false){
        
            // disable setting temp
            this.setAirCondBotton.setEnabled(false);
            this.setHeatButton.setEnabled(false);
            
            this.acTempTextField.setEditable(false);
            this.heatTempTextField.setEditable(false);
            
            this.disableRadioButtons();            
        }  
    }
       
    /**
     * Disables the AC, Heat, Lights, Left/Right Doors radio buttons. 
     * 
     */
    private void disableRadioButtons(){
    
        this.acOnRadioButton.setEnabled(false);
        this.acOffRadioButton.setEnabled(false);
        
        this.heatOffRadioButton.setEnabled(false);
        this.heatOnRadioButton.setEnabled(false);
        
        this.lightsOnRadioButton.setEnabled(false);
        this.lightsOffRadioButton.setEnabled(false);
        
        this.leftDoorsOpenRadioButton.setEnabled(false);
        this.leftDoorsCloseRadioButton.setEnabled(false);
        
        this.rightDoorsOpenRadioButton.setEnabled(false);
        this.rightDoorsCloseRadioButton.setEnabled(false);
    }
    
    /**
     * Enables the AC, Heat, Lights, Left/Right Doors radio buttons.
     * 
     */
    private void enableRadioButtons(){
        
        this.acOnRadioButton.setEnabled(true);
        this.acOffRadioButton.setEnabled(true);
        
        this.heatOffRadioButton.setEnabled(true);
        this.heatOnRadioButton.setEnabled(true);
        
        this.lightsOnRadioButton.setEnabled(true);
        this.lightsOffRadioButton.setEnabled(true);
        
        this.leftDoorsOpenRadioButton.setEnabled(true);
        this.leftDoorsCloseRadioButton.setEnabled(true);
        
        this.rightDoorsOpenRadioButton.setEnabled(true);
        this.rightDoorsCloseRadioButton.setEnabled(true);
    }
    
    /**
     * Refreshes the Air Conditioning Radio Buttons based on the status of the train. 
     * 
     * This is called by the system timer every 'x' seconds. function is called a lot in 
     * automatic to refresh the states of the train and have it reflected in the UI. 
     * 
     */
    private void refreshAC(){

        if (this.selectedTrain.ac == 1){ this.acOnRadioButton.setSelected(true); }
        else if (this.selectedTrain.ac == 0){ this.acOffRadioButton.setSelected(true); }
        else if (this.selectedTrain.ac == -1){ this.acFailureRadioButton.setSelected(true); }
    }
    
    /**
     * Refreshes the Heating Radio Buttons based on the status of the train.
     */
    private void refreshHeat(){
    
        if (this.selectedTrain.heat == 1){ this.heatOnRadioButton.setSelected(true); }
        else if (this.selectedTrain.heat == 0){ this.heatOffRadioButton.setSelected(true);}
        else if (this.selectedTrain.heat == -1){ this.heatFailureRadioButton.setSelected(true); }
    }
    
    /**
     * Refreshes the Lights Radio Buttons based on the status of the train.
     */
    private void refreshLights(){
    
        if (this.selectedTrain.lights == 1){ this.lightsOnRadioButton.setSelected(true); }
        else if (this.selectedTrain.lights == 0){ this.lightsOffRadioButton.setSelected(true); }
        else if (this.selectedTrain.lights == -1){ this.lightsFailureRadioButton.setSelected(true); }
    }
    
    /**
     * Refreshes the Left Doors Radio Buttons based on the status of the train.
     */
    private void refreshLeftDoors(){
    
        if (this.selectedTrain.leftDoors == 1){ this.leftDoorsOpenRadioButton.setSelected(true); }
        else if (this.selectedTrain.leftDoors == 0){ this.leftDoorsCloseRadioButton.setSelected(true); }
        else if (this.selectedTrain.leftDoors == -1){ this.leftDoorsFailureRadioButton.setSelected(true); }
    }
    
    /**
     * Refreshes the Right Doors Radio Buttons based on the status of the train.
     */
    private void refreshRightDoors(){
    
        if (this.selectedTrain.rightDoors == 1){ this.rightDoorsOpenRadioButton.setSelected(true); }
        else if (this.selectedTrain.rightDoors == 0){ this.rightDoorsCloseRadioButton.setSelected(true); }
        else if (this.selectedTrain.rightDoors == -1) {this.rightDoorsFailureRadioButton.setSelected(true); }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acButtonGroup = new javax.swing.ButtonGroup();
        heatButtonGroup = new javax.swing.ButtonGroup();
        lightsButtonGroup = new javax.swing.ButtonGroup();
        leftDoorsButtonGroup = new javax.swing.ButtonGroup();
        rightDoorsButtonGroup = new javax.swing.ButtonGroup();
        acLabel = new javax.swing.JLabel();
        heatLabel = new javax.swing.JLabel();
        lightsLabel = new javax.swing.JLabel();
        leftDoorsLabel = new javax.swing.JLabel();
        rightDoorsLabel = new javax.swing.JLabel();
        acFailureRadioButton = new javax.swing.JRadioButton();
        acOffRadioButton = new javax.swing.JRadioButton();
        lightsFailureRadioButton = new javax.swing.JRadioButton();
        lightsOffRadioButton = new javax.swing.JRadioButton();
        lightsOnRadioButton = new javax.swing.JRadioButton();
        leftDoorsFailureRadioButton = new javax.swing.JRadioButton();
        leftDoorsCloseRadioButton = new javax.swing.JRadioButton();
        leftDoorsOpenRadioButton = new javax.swing.JRadioButton();
        rightDoorsFailureRadioButton = new javax.swing.JRadioButton();
        rightDoorsCloseRadioButton = new javax.swing.JRadioButton();
        rightDoorsOpenRadioButton = new javax.swing.JRadioButton();
        heatOffRadioButton = new javax.swing.JRadioButton();
        heatTempTextField = new javax.swing.JTextField();
        heatTempDegrees = new javax.swing.JLabel();
        acTempDegrees = new javax.swing.JLabel();
        acTempTextField = new javax.swing.JTextField();
        heatFailureRadioButton = new javax.swing.JRadioButton();
        setAirCondBotton = new javax.swing.JButton();
        setHeatButton = new javax.swing.JButton();
        uiSeparatorOne = new javax.swing.JSeparator();
        uiSeparatorTwo = new javax.swing.JSeparator();
        uiSeparatorThree = new javax.swing.JSeparator();
        uiSeparatorFour = new javax.swing.JSeparator();
        acOnRadioButton = new javax.swing.JRadioButton();
        heatOnRadioButton = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(255, 255, 255));

        acLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        acLabel.setText("A/C:");
        acLabel.setToolTipText("Hey");

        heatLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        heatLabel.setText("Heat:");

        lightsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        lightsLabel.setText("Lights:");

        leftDoorsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        leftDoorsLabel.setText("Left Doors:");

        rightDoorsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        rightDoorsLabel.setText("Right Doors:");

        acButtonGroup.add(acFailureRadioButton);
        acFailureRadioButton.setText("FAIL");

        acButtonGroup.add(acOffRadioButton);
        acOffRadioButton.setText("OFF");
        acOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffAirCond(evt);
            }
        });

        lightsButtonGroup.add(lightsFailureRadioButton);
        lightsFailureRadioButton.setText("FAIL");

        lightsButtonGroup.add(lightsOffRadioButton);
        lightsOffRadioButton.setText("OFF");
        lightsOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffLights(evt);
            }
        });

        lightsButtonGroup.add(lightsOnRadioButton);
        lightsOnRadioButton.setText("ON");
        lightsOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnLights(evt);
            }
        });

        leftDoorsButtonGroup.add(leftDoorsFailureRadioButton);
        leftDoorsFailureRadioButton.setText("FAIL");

        leftDoorsButtonGroup.add(leftDoorsCloseRadioButton);
        leftDoorsCloseRadioButton.setText("CLOSE");
        leftDoorsCloseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeLeftDoors(evt);
            }
        });

        leftDoorsButtonGroup.add(leftDoorsOpenRadioButton);
        leftDoorsOpenRadioButton.setText("OPEN");
        leftDoorsOpenRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openLeftDoors(evt);
            }
        });

        rightDoorsButtonGroup.add(rightDoorsFailureRadioButton);
        rightDoorsFailureRadioButton.setText("FAIL");

        rightDoorsButtonGroup.add(rightDoorsCloseRadioButton);
        rightDoorsCloseRadioButton.setText("CLOSE");
        rightDoorsCloseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeRightDoors(evt);
            }
        });

        rightDoorsButtonGroup.add(rightDoorsOpenRadioButton);
        rightDoorsOpenRadioButton.setText("OPEN");
        rightDoorsOpenRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRightDoors(evt);
            }
        });

        heatButtonGroup.add(heatOffRadioButton);
        heatOffRadioButton.setText("OFF");
        heatOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffHeat(evt);
            }
        });

        heatTempTextField.setText("0");

        heatTempDegrees.setText("°F");

        acTempDegrees.setText("°F");

        acTempTextField.setText("0");

        heatButtonGroup.add(heatFailureRadioButton);
        heatFailureRadioButton.setText("FAIL");

        setAirCondBotton.setText("Set");
        setAirCondBotton.setToolTipText("Set the A/C to the specified temperature");
        setAirCondBotton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setAirCondTemp(evt);
            }
        });

        setHeatButton.setText("Set");
        setHeatButton.setToolTipText("Set the heat to the specified temperature");
        setHeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setHeatTemp(evt);
            }
        });

        acButtonGroup.add(acOnRadioButton);
        acOnRadioButton.setText("ON");
        acOnRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                turnOnAirCond(evt);
            }
        });
        acOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnAC(evt);
            }
        });

        heatButtonGroup.add(heatOnRadioButton);
        heatOnRadioButton.setText("ON");
        heatOnRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                turnHeatOn(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uiSeparatorOne)
            .addComponent(uiSeparatorThree, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(uiSeparatorTwo, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uiSeparatorFour)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lightsLabel)
                            .addComponent(leftDoorsLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(acTempDegrees, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(setAirCondBotton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(acOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(acFailureRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(leftDoorsOpenRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(leftDoorsCloseRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leftDoorsFailureRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(heatTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(heatTempDegrees, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(setHeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lightsOnRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lightsOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(heatOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lightsFailureRadioButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(heatFailureRadioButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rightDoorsLabel)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rightDoorsOpenRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(rightDoorsCloseRadioButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rightDoorsFailureRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(acLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(acOnRadioButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(heatLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(heatOnRadioButton)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acLabel)
                    .addComponent(acOnRadioButton))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acTempDegrees)
                    .addComponent(acOffRadioButton)
                    .addComponent(acFailureRadioButton)
                    .addComponent(setAirCondBotton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(uiSeparatorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heatLabel)
                    .addComponent(heatOnRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heatTempDegrees)
                    .addComponent(heatTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heatFailureRadioButton)
                    .addComponent(heatOffRadioButton)
                    .addComponent(setHeatButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiSeparatorTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lightsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lightsOnRadioButton)
                    .addComponent(lightsOffRadioButton)
                    .addComponent(lightsFailureRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(uiSeparatorThree, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(leftDoorsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftDoorsFailureRadioButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(leftDoorsOpenRadioButton)
                        .addComponent(leftDoorsCloseRadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(uiSeparatorFour, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightDoorsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rightDoorsCloseRadioButton)
                    .addComponent(rightDoorsFailureRadioButton)
                    .addComponent(rightDoorsOpenRadioButton))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Tells the selected train to turn off its Lights. 
     * This occurs when the "OFF" radio button is clicked. 
     * 
     * @param evt the sender of the action, i.e., the "OFF" radio button.
     */
    private void turnOffLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffLights
        
        System.out.println("Telling train to turn off lights."); 
        // send signal to train
        this.selectedTrain.lights = 0; 
    }//GEN-LAST:event_turnOffLights

    /**
     * Tells the selected train to turn on its Lights.
     * 
     * @param evt the sender of the action, i.e., the "ON" radio button.
     */
    private void turnOnLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnLights
        
        System.out.println("Telling train to turn on lights."); 
        // send signal to train
        this.selectedTrain.lights = 1; 
    }//GEN-LAST:event_turnOnLights

    /**
     * Tells the selected train to close its Left Doors.
     * 
     * @param evt the sender of the action, i.e., the "CLOSE" radio button.
     */
    private void closeLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeLeftDoors
        
        System.out.println("Telling train to close left doors.");
        // send signal to train
        this.selectedTrain.leftDoors = 0;      
    }//GEN-LAST:event_closeLeftDoors

    /**
     * Tells the selected train to open its Left Doors.
     * 
     * @param evt the sender of the action, i.e., the "OPEN" radio button.
     */
    private void openLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLeftDoors
       
        System.out.println("Telling train to open left doors."); 
        // send signal to train
        this.selectedTrain.leftDoors = 1; 
    }//GEN-LAST:event_openLeftDoors

    
    /**
     * Tells the selected train to close its Right Doors.
     * 
     * @param evt the sender of the action, i.e., the "CLOSE" radio button.
     */
    private void closeRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeRightDoors
        
        System.out.println("Telling train to close right doors."); 
        // send signal to train
        this.selectedTrain.rightDoors = 0; 
    }//GEN-LAST:event_closeRightDoors

    
    /**
     * Tells the selected train to open its Right Doors.
     * 
     * @param evt the sender of the action, i.e., the "OPEN" radio button.
     */
    private void openRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRightDoors
        
        System.out.println("Telling train to open right doors."); 
        // send signal to train
        this.selectedTrain.rightDoors = 1; 
    }//GEN-LAST:event_openRightDoors

    /**
     * Tells the selected train to turn off the Heating unit.
     * 
     * @param evt the sender of the action, i.e., the "OFF" radio button.
     */
    private void turnOffHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffHeat
       
        System.out.println("Telling train to turn off heat.");  
        // send signal to train
        this.selectedTrain.heat = 0; 
    }//GEN-LAST:event_turnOffHeat

    /**
     * Tells the train to set its heating unit to the value set in the text box. 
     * If the set temperature of over the threshold, an error will pop up stating that the value is too high. 
     * 
     * @param evt the sender of the action, i.e., "Set" button.
     */
    private void setHeatTemp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setHeatTemp
       
        String temp = this.heatTempTextField.getText();
        
        if (Double.parseDouble(temp) < 60.0 || Double.parseDouble(temp) > 80.0 ){
               
            System.out.println("Error. Please set a temperature between 60.0 - 80.0"); 
           
        }else{
            // turn the heat on the train, this will cause the GUI to update on the next refreshGUI call.
            // turn on heat and transmit the temp
            if (this.selectedTrain.heat == 0){ this.selectedTrain.heat = 1; }
            
            // transmit the temp
            // FIX ME: replace when the train model is complete to set the temp
            System.out.println("Telling the train to set temperature to " + temp + " for Heating unit");
                         
        }        
    }//GEN-LAST:event_setHeatTemp

    /**
     * Tells the train to set its air conditioning unit to the value set in the text box. 
     * If the set temperature of over the threshold, an error will pop up stating that the value is too high. 
     * 
     * @param evt the sender of the action, i.e., "Set" button.
     */
    private void setAirCondTemp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setAirCondTemp
       
        String temp = this.acTempTextField.getText();
        
        // make sure the user doesnt set an unreasonable temp
        if (Double.parseDouble(temp) < 40.0 || Double.parseDouble(temp) > 60.0 ){
            System.out.println("Error. Please set a temperature between 40.0 - 60.0"); 
            
            // display error:   
            //ConfirmationWindow window = new ConfirmationWindow("Error!", "Please set a temperature between 40.0 - 60.0", 1); 
        }else{
                   
            // turn on the ac, and transmit the temp
            // if it's off turn it on
     
            if (this.selectedTrain.ac == 0){ this.selectedTrain.ac = 1; }
                                 
            System.out.println("Telling the train to set temperature to " + temp + " for Air Conditioning unit"); 
        }  
    }//GEN-LAST:event_setAirCondTemp

    /**
     * @Bug Currently this function is called whenever the item (Radio Button) is selected. 
     * This should be the code that sends the information to the train regarding the states of on the train. 
     * 
     * Clicking the 'Set' button causes the GUI to change to 'On', and 
     * 
     * FIXED!
     * 
     * @param evt 
     */
    
    /**
     * @Bug Clicking the 'On' button will set the temperature to the default temp instead of 
     * resending the temp that was previously in the text field. 
     * 
     * Replicate by 
     * 
     */
    
    /**
     * Tells the train to turn on the Air Conditioning unit, and to turn off the heat if it's on. 
     * If in Manual mode, the temperature sent to the train is the value in the text box. 
     * If in Automatic mode, the temperature sent to the train is the default temperature.  
     * 
     * @param evt the sender of the event, i.e, the 'ON' radio button.
     */
    private void turnOnAirCond(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_turnOnAirCond
       
        if (evt.getStateChange() == ItemEvent.SELECTED){
            
            // manual vs automatic mode
            if (this.inManualMode){
                // turn on ac and transmit temp
                this.selectedTrain.ac = 1; 
                Double temp = Double.parseDouble(this.acTempTextField.getText());
            }else{
            
                // transmit default temp        
                Double temp = 45.0; 
                this.selectedTrain.ac = 1; 
                // update the text on the gui
                this.acTempTextField.setText(Double.toString(temp));       
            }
            
            // turn heat off if its on
            if (this.heatOnRadioButton.isSelected() == true){ 
                this.selectedTrain.heat = 0;  
                //this.heatOffRadioButton.setSelected(true); // set the OFF radio button
            }
        }
    }//GEN-LAST:event_turnOnAirCond

    /**
     * Tells the train to turn on the heat, and turn off the AC if it's on.
     * If in Manual mode, the temperature sent to the train is the value in the text box. 
     * If in Automatic mode, the temperature sent to the train is the default temperature. 
     * 
     * @param evt the sender of the event, i.e., the 'ON' radio button.  
     */
    private void turnHeatOn(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_turnHeatOn

        if (evt.getStateChange() == ItemEvent.SELECTED){            
            // manual vs automatic mode
            if (this.inManualMode){
                // turn on heat and transmit temp             
                this.selectedTrain.heat = 1; 
                Double temp = Double.parseDouble(this.heatTempTextField.getText());
                System.out.println("Telling the train to set temperature to " + temp + " for Heating unit");
            }else{
                // transmit default temp        
                Double temp = 65.0;     
                // update the text on the gui
                this.selectedTrain.heat = 1;
                this.heatTempTextField.setText(Double.toString(temp));
                System.out.println("Telling the train to set temperature to " + temp + " for Heating unit");
            }
            
            // turn off ac if its on
            if (this.acOnRadioButton.isSelected() == true){ 
                this.selectedTrain.ac = 0; 
                //this.acOffRadioButton.setSelected(true); 
            }
        }
    }//GEN-LAST:event_turnHeatOn


    private void turnOnAC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnAC
        
        this.selectedTrain.ac = 1; 
            
        // set train to default default temp
        this.acTempTextField.setText("40.0");
    }//GEN-LAST:event_turnOnAC

    /**
     * Tells the train to turn off the Air Conditioning unit. 
     * 
     * @param evt the sender of the event, i.e., the 'OFF' radio button.
     */
    private void turnOffAirCond(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffAirCond
        
        this.selectedTrain.ac = 0;         
        System.out.println("Telling Air Conditioning unit to turn off."); 
    }//GEN-LAST:event_turnOffAirCond


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup acButtonGroup;
    private javax.swing.JRadioButton acFailureRadioButton;
    private javax.swing.JLabel acLabel;
    private javax.swing.JRadioButton acOffRadioButton;
    private javax.swing.JRadioButton acOnRadioButton;
    private javax.swing.JLabel acTempDegrees;
    private javax.swing.JTextField acTempTextField;
    private javax.swing.ButtonGroup heatButtonGroup;
    private javax.swing.JRadioButton heatFailureRadioButton;
    private javax.swing.JLabel heatLabel;
    private javax.swing.JRadioButton heatOffRadioButton;
    private javax.swing.JRadioButton heatOnRadioButton;
    private javax.swing.JLabel heatTempDegrees;
    private javax.swing.JTextField heatTempTextField;
    private javax.swing.ButtonGroup leftDoorsButtonGroup;
    private javax.swing.JRadioButton leftDoorsCloseRadioButton;
    private javax.swing.JRadioButton leftDoorsFailureRadioButton;
    private javax.swing.JLabel leftDoorsLabel;
    private javax.swing.JRadioButton leftDoorsOpenRadioButton;
    private javax.swing.ButtonGroup lightsButtonGroup;
    private javax.swing.JRadioButton lightsFailureRadioButton;
    private javax.swing.JLabel lightsLabel;
    private javax.swing.JRadioButton lightsOffRadioButton;
    private javax.swing.JRadioButton lightsOnRadioButton;
    private javax.swing.ButtonGroup rightDoorsButtonGroup;
    private javax.swing.JRadioButton rightDoorsCloseRadioButton;
    private javax.swing.JRadioButton rightDoorsFailureRadioButton;
    private javax.swing.JLabel rightDoorsLabel;
    private javax.swing.JRadioButton rightDoorsOpenRadioButton;
    private javax.swing.JButton setAirCondBotton;
    private javax.swing.JButton setHeatButton;
    private javax.swing.JSeparator uiSeparatorFour;
    private javax.swing.JSeparator uiSeparatorOne;
    private javax.swing.JSeparator uiSeparatorThree;
    private javax.swing.JSeparator uiSeparatorTwo;
    // End of variables declaration//GEN-END:variables
}
