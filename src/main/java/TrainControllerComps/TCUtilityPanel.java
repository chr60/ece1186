package TrainControllerComps;

import CommonUIElements.ConfirmationWindow;
import java.awt.Color;
import java.awt.event.ItemEvent;
import javax.swing.JButton;

import TrainModel.*;
import java.util.LinkedList;
import javax.swing.JTextArea;

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
    private Train selectedTrain;

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
     * Text area to print train announcements to.
     */
    private JTextArea announcementLogs;

    /**
     * Text area to print operating messages to.
     */
    private JTextArea operatingLogs;

    /**
     * List to store announcementLogs.
     */
    private LinkedList<String> announcementLogbook;

    /**
     * List to store messages to print to the operatingLogbook
     */
    private LinkedList<String> operatingLogbook;

    /**
     * The desired temperature for the train to be at.
     */
    private Double setTemp;

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

        this.announcementLogbook = new LinkedList<String>();
        this.operatingLogbook = new LinkedList<String>();
    }

    /**
     * Sets the train that this UtilityPanel should control.
     * This value should be set from the Train Controller class when a new train is selected.
     *
     * @param train the train controlled by the Train Controller.
     */
    public void setSelectedTrain(Train train){

        this.selectedTrain = train;
    }

    /**
     * Retrieves the train that the Utility Panel is controlling.
     *
     * @return returns the selected train.
     */
    public Train getSelectedTrain(){

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
     * Sets the announcements log to print to.
     *
     * @param annoucneLog the text area object.
     */
    public void setAnnouncementLog(JTextArea annoucneLog){

        this.announcementLogs = annoucneLog;
    }

    /**
     * Sets the operating log to print to.
     *
     * @param annoucneLog the text area object.
     */
    public void setOperatingLog(JTextArea operatingLogs){

        this.operatingLogs = operatingLogs;
    }

    /**
     * Prints the stored up logs to the announcement panel, and then clears
     * the logs.
     */
    public void printAnnouncements(){

        if (this.announcementLogbook.isEmpty() == false){

            for (String log : this.announcementLogbook){
                this.announcementLogs.setText(this.announcementLogs.getText() + log + "\n");
            }
        }

        this.announcementLogbook.clear();
    }

    public void printOperatingLogs(){

        if (this.operatingLogbook.isEmpty() == false){

            for (String log : this.operatingLogbook){
                this.operatingLogs.setText(this.operatingLogs.getText() + log + "\n");
            }
        }

        this.operatingLogbook.clear();
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

        if (this.inManualMode == false){ this.automaticModeChecks(); }

        /**
         * @bug When selected a train from a TCDispatchedTrainsFrame, a NullPointerException is thrown.
         *
         */
        if (this.isUtilityFailure()){ this.vitalsButton.setForeground(Color.red); } // change to red
        else if (this.isUtilityFailure() == false){this.vitalsButton.setForeground(new Color(0,0,0));}

        // checks so that the doors can't open doors if moving..
        if (this.canOpenDoors()){ this.enableOpeningDoors(); }

        else{ // shut the doors if moving
            this.disableOpeningDoors();
            if (this.selectedTrain.getLeftDoor() != -1){ this.selectedTrain.setLeftDoor(0); }
            if (this.selectedTrain.getRightDoor() != -1){ this.selectedTrain.setRightDoor(0); }
        }

        // set utility stuff based on train information
        this.refreshAC();
        this.refreshHeat();
        this.refreshLights();
        this.refreshLeftDoors();
        this.refreshRightDoors();
    }

    /**
     * Performs the checks to see if certain utilities need to be turned on/off, open/closed
     * when in Automatic mode based on train status.
     *
     */
    private void automaticModeChecks(){
        // turn on lights if underground
        if (this.isUnderground()){ this.selectedTrain.setLights(1);}
        // set heat
        if (this.selectedTrain.getTemp() <= 40.0){this.selectedTrain.setThermostat(60.0);}
        else if (this.selectedTrain.getTemp() >= 80){ this.selectedTrain.setThermostat(50.0);}
    }

    /**
     * Disable the Radio buttons for opening the doors of the train.
     * This is probably done because the train is in motion, and opening the doors
     * while in motion is not safe.
     *
     */
    private void disableOpeningDoors(){

        this.leftDoorsOpenRadioButton.setEnabled(false);
        this.rightDoorsOpenRadioButton.setEnabled(false);
    }
     /**
     * Enabled the Radio buttons for opening the doors of the train.
     * This is probably done because the train is not in motion, and can potentially open
     * the doors.
     *
     */
    private void enableOpeningDoors(){

        this.leftDoorsOpenRadioButton.setEnabled(true);
        this.rightDoorsOpenRadioButton.setEnabled(true);
    }

    /**
     * Determines if there is a failure in one of utilities.
     *
     * @return returns true if at least one of the utilities are in a failure state, false otherwise.
     */
    private boolean isUtilityFailure(){

        if (this.selectedTrain.getAC() == -1||
                this.selectedTrain.getHeat() == -1 || this.selectedTrain.getLights() == -1
                || this.selectedTrain.getLeftDoor() == -1 || this.selectedTrain.getRightDoor() == -1){
            return true; // there is a utility failure..
        }

        return false; // there isn't a utility failure
    }

    /**
     * Sets the UI elements that the user can interact with depending on the mode the system is in.
     *
     */
    private void setModeUI(){

        if (this.inManualMode == true){ this.enableRadioButtons(); } // manual mode
        else if (this.inManualMode == false){ this.disableRadioButtons(); } // automatic mode
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

        this.acOffRadioButton.setEnabled(true);

        this.heatOffRadioButton.setEnabled(true);

        this.lightsOnRadioButton.setEnabled(true);
        this.lightsOffRadioButton.setEnabled(true);

        this.leftDoorsOpenRadioButton.setEnabled(true);
        this.leftDoorsCloseRadioButton.setEnabled(true);

        this.rightDoorsOpenRadioButton.setEnabled(true);
        this.rightDoorsCloseRadioButton.setEnabled(true);
    }

    /**
     * Checks to see if the train is underground based on its current block.
     *
     * @return returns true if the train is underground, false otherwise
     */
    private boolean isUnderground(){

        if (this.selectedTrain.getGPS().getCurrBlock().isUnderground() == true){return true; }
        else{ return false; }
    }

    /**
     * Checks to see if it's safe to open the doors on the train.
     * This can occur when the train is not moving.
     *
     * @return returns true if it's safe to open the doors of the train, false if its unsafe.
     */
    private boolean canOpenDoors(){

        // if the train is stoped, we can open doors
        if (this.selectedTrain.getVelocity() == 0.0){ return true; }
        else { return false; }
    }

    /**
     * Checks if the block the train is on has a station.
     *
     * @return returns true if there is a station, false otherwise
     */
    private boolean isAtStation(){

        if (this.selectedTrain.getGPS().getCurrBlock().getStationName() != null){return true; }
        else { return false; }
    }
    /**
     * Refreshes the Air Conditioning Radio Buttons based on the status of the train.
     */
    private void refreshAC(){
        if (this.selectedTrain.getAC() == 1){

            this.acOnRadioButton.setSelected(true);

            if (this.selectedTrain.getTemp() >= this.setTemp){ this.selectedTrain.updateTemp();}
        }
        else if (this.selectedTrain.getAC() == 0){ this.acOffRadioButton.setSelected(true); }
        else if (this.selectedTrain.getAC() == -1){ this.acFailureRadioButton.setSelected(true); }
    }

    /**
     * Refreshes the Heating Radio Buttons based on the status of the train.
     */
    private void refreshHeat(){

        if (this.selectedTrain.getHeat() == 1){

            this.heatOnRadioButton.setSelected(true);
            // update heat until set heat
            if (this.selectedTrain.getTemp() <= this.setTemp){ this.selectedTrain.updateTemp();}
        }
        else if (this.selectedTrain.getHeat() == 0){ this.heatOffRadioButton.setSelected(true);}
        else if (this.selectedTrain.getHeat() == -1){ this.heatFailureRadioButton.setSelected(true); }
    }

    /**
     * Refreshes the Lights Radio Buttons based on the status of the train.
     */
    private void refreshLights(){

        if (this.selectedTrain.getLights() == 1){ this.lightsOnRadioButton.setSelected(true); }
        else if (this.selectedTrain.getLights() == 0){ this.lightsOffRadioButton.setSelected(true); }
        else if (this.selectedTrain.getLights() == -1){ this.lightsFailureRadioButton.setSelected(true); }
    }

    /**
     * Refreshes the Left Doors Radio Buttons based on the status of the train.
     */
    private void refreshLeftDoors(){


        if (this.selectedTrain.getLeftDoor() == 1){ this.leftDoorsOpenRadioButton.setSelected(true); }
        else if (this.selectedTrain.getLeftDoor() == 0){ this.leftDoorsCloseRadioButton.setSelected(true); }
        else if (this.selectedTrain.getLeftDoor() == -1){ this.leftDoorsFailureRadioButton.setSelected(true); }
    }

    /**
     * Refreshes the Right Doors Radio Buttons based on the status of the train.
     */
    private void refreshRightDoors(){

        if (this.selectedTrain.getRightDoor() == 1){ this.rightDoorsOpenRadioButton.setSelected(true); }
        else if (this.selectedTrain.getRightDoor() == 0){ this.rightDoorsCloseRadioButton.setSelected(true); }
        else if (this.selectedTrain.getRightDoor() == -1) {this.rightDoorsFailureRadioButton.setSelected(true); }
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

        heatButtonGroup.add(heatOnRadioButton);
        heatOnRadioButton.setText("ON");

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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(acOnRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(acOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(acFailureRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lightsLabel)
                                    .addComponent(leftDoorsLabel)
                                    .addComponent(rightDoorsLabel)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rightDoorsOpenRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(rightDoorsCloseRadioButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(leftDoorsOpenRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(leftDoorsCloseRadioButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lightsOnRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(lightsOffRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lightsFailureRadioButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(leftDoorsFailureRadioButton)
                                        .addComponent(rightDoorsFailureRadioButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(heatOnRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(heatOffRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(heatFailureRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(heatLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(heatTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(heatTempDegrees, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(setHeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(acLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(acTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(acTempDegrees, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(setAirCondBotton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acLabel)
                    .addComponent(acTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acTempDegrees)
                    .addComponent(setAirCondBotton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acOnRadioButton)
                    .addComponent(acOffRadioButton)
                    .addComponent(acFailureRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(uiSeparatorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heatLabel)
                    .addComponent(heatTempDegrees)
                    .addComponent(heatTempTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setHeatButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heatOnRadioButton)
                    .addComponent(heatFailureRadioButton)
                    .addComponent(heatOffRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uiSeparatorTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(leftDoorsOpenRadioButton)
                            .addComponent(leftDoorsCloseRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(uiSeparatorFour, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rightDoorsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rightDoorsCloseRadioButton)
                            .addComponent(rightDoorsOpenRadioButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(leftDoorsFailureRadioButton)
                        .addGap(70, 70, 70)
                        .addComponent(rightDoorsFailureRadioButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Tells the selected train to turn off its Lights.
     * This occurs when the "OFF" radio button is clicked.
     *
     * @param evt the sender of the action, i.e., the "OFF" radio button.
     */
    private void turnOffLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffLights

        this.selectedTrain.setLights(0); // turn off lights
        this.operatingLogbook.add("Lights off.");
        this.printOperatingLogs();
    }//GEN-LAST:event_turnOffLights

    /**
     * Tells the selected train to turn on its Lights.
     *
     * @param evt the sender of the action, i.e., the "ON" radio button.
     */
    private void turnOnLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnLights

        this.selectedTrain.setLights(1); // turn on lights
        this.operatingLogbook.add("Lights on.");
        this.printOperatingLogs();
    }//GEN-LAST:event_turnOnLights

    /**
     * Tells the selected train to close its Left Doors.
     *
     * @param evt the sender of the action, i.e., the "CLOSE" radio button.
     */
    private void closeLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeLeftDoors

        this.selectedTrain.setLeftDoor(0);
        this.operatingLogbook.add("Closed left doors.");
        this.printOperatingLogs();
    }//GEN-LAST:event_closeLeftDoors

    /**
     * Tells the selected train to open its Left Doors.
     *
     * @param evt the sender of the action, i.e., the "OPEN" radio button.
     */
    private void openLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLeftDoors

        this.selectedTrain.setLeftDoor(1);
        this.operatingLogbook.add("Opened left doors.");
        this.printOperatingLogs();
    }//GEN-LAST:event_openLeftDoors


    /**
     * Tells the selected train to close its Right Doors.
     *
     * @param evt the sender of the action, i.e., the "CLOSE" radio button.
     */
    private void closeRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeRightDoors

        this.selectedTrain.setRightDoor(0);
        this.operatingLogbook.add("Closed right doors.");
        this.printOperatingLogs();
    }//GEN-LAST:event_closeRightDoors


    /**
     * Tells the selected train to open its Right Doors.
     *
     * @param evt the sender of the action, i.e., the "OPEN" radio button.
     */
    private void openRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRightDoors

        this.selectedTrain.setRightDoor(1);
        this.operatingLogbook.add("Opened right doors.");
        this.printOperatingLogs();
    }//GEN-LAST:event_openRightDoors

    /**
     * Tells the selected train to turn off the Heating unit.
     *
     * @param evt the sender of the action, i.e., the "OFF" radio button.
     */
    private void turnOffHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffHeat

        this.selectedTrain.setHeat(0); // turn off heat
        this.operatingLogbook.add("Turned off heat");
        this.printOperatingLogs();
    }//GEN-LAST:event_turnOffHeat

    /**
     * Tells the train to set its heating unit to the value set in the text box.
     * If the set temperature of over the threshold, an error will pop up stating that the value is too high.
     *
     * @param evt the sender of the action, i.e., "Set" button.
     */
    private void setHeatTemp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setHeatTemp

        String tempStr = this.heatTempTextField.getText();
        if (Double.parseDouble(tempStr) < 60.0 || Double.parseDouble(tempStr) > 80.0 ){

            System.out.println("Error. Please set a temperature between 60.0 - 80.0");
        }else{
            // turn on heat and transmit the temp
            this.setTemp = Double.parseDouble(tempStr);
            this.turnOnHeat(Double.parseDouble(tempStr));
        }
    }//GEN-LAST:event_setHeatTemp

    /**
     * Tells the train to set its air conditioning unit to the value set in the text box.
     * If the set temperature of over the threshold, an error will pop up stating that the value is too high.
     *
     * @param evt the sender of the action, i.e., "Set" button.
     */
    private void setAirCondTemp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setAirCondTemp

        String tempStr = this.acTempTextField.getText();
        System.out.println(tempStr);
        // make sure the user doesnt set an unreasonable temp
        if (Double.parseDouble(tempStr) < 40.0 || Double.parseDouble(tempStr) > 60.0 ){
            System.out.println("Error. Please set a temperature between 40.0 - 60.0");

        }else{
            this.setTemp = Double.parseDouble(tempStr);
            this.turnOnAC(Double.parseDouble(tempStr));
        }
    }//GEN-LAST:event_setAirCondTemp

    /**
     * Turns on the AC and turns off the heat, and sets the trains thermostat
     * to the given temp.
     *
     * @param temp the temperature in degrees f to set the train's thermometer to.
     */
    public void turnOnAC(Double temp){

        this.selectedTrain.setAC(1); // turn on heat
        this.selectedTrain.setHeat(0); // turn off heat

        if (temp == null){ this.selectedTrain.setThermostat(40.0); } // set to default heat
        else{ this.selectedTrain.setThermostat(temp); }

        this.operatingLogbook.add("AC Set: " + temp.toString());
        this.printOperatingLogs();
    }

    /**
     * Turns on the heat, and turns the ac off, and sets the train's thermostat
     * to the given temperature.
     *
     * @param temp the temperature in degrees f to set the train's thermometer to.
     */
    public void turnOnHeat(Double temp){

        this.selectedTrain.setHeat(1); // turn on heat
        this.selectedTrain.setAC(0); // turn off heat

        if (temp == null){ this.selectedTrain.setThermostat(60.0); } // set to default heat
        else{ this.selectedTrain.setThermostat(temp); }

        this.operatingLogbook.add("Heat Set: " + temp.toString());
        this.printOperatingLogs();
    }

    /**
     * Tells the train to turn off the Air Conditioning unit.
     *
     * @param evt the sender of the event, i.e., the 'OFF' radio button.
     */
    private void turnOffAirCond(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffAirCond

        this.selectedTrain.setAC(0);
        this.operatingLogbook.add("Turned off AC.");
        this.printOperatingLogs();
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
