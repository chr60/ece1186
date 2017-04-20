package TrainControllerComps;

import CTC.TrainManager;
import TrackModel.Block;
import TrackModel.TrackModel;
import TrainModel.Train;
import TrainModel.GPS;
import TrainModel.TrainHandler;
import WaysideController.PLC;
import WaysideController.WS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import javax.sound.midi.Track;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is responsible for testing the various components in the Train Controller, mimicking the components on 
 * a train object. 
 * 
 * This class collaborates with the Train Controller and Train class. 
 * 
 * @author Andrew Lendacky
 */
public class TCTestConsole extends javax.swing.JFrame {

    /**
     * Train being controlled by the Train Controller.
     */
    private Train selectedTrain; 
    
    /**
     * Train Controller to relay any changes made to the train to.
     */
    private TrainController trainController; 
        
    /**
     * The number of dispatched trains in the system
     */
    private int dispatchedTrains; 
    
    
    private TrainHandler trainHandler; 
    
    WS ws; 
    TrainManager tm; 
    Block yardBlock; 
    Block endingBlock;   
    TrackModel track; 
    
    Timer systemClock = new Timer(1000, new ActionListener(){
            Random rand = new Random();
            public void actionPerformed(ActionEvent e) {

                for (TrainController tc : trainHandler.openTrainControllers)
                    tc.refreshComponents();
            }
        });
    
    /**
     * Constructor for creating a TCTestConsole object with no Train Controller and no 
     * selected train. 
     */
    public TCTestConsole() {
        initComponents();
        
        this.track = new TrackModel("Testing"); 
        String[] fnames = {"test-classes/redline.csv"}; 
        String[] fNamesLink = {"test-classes/redlineLink.csv"};
        track.readCSV(fnames, fNamesLink);
        this.dispatchedTrains = 0; 
        this.selectedTrain = null; 
        this.trainController = null; 
             
        this.trainHandler = new TrainHandler(this.track); 
        
        this.setupRadioButtons();
        
        this.setupTestScenario();
        
        this.systemClock.start();
    }
    
    /**
     * Sets up a testing scenario to allow a train to move around the track.
     */
    private void setupTestScenario(){
        
        // create dummy data
        ws = new WS("Red", this.track);
        tm = new TrainManager("Red", this.track);
        
        yardBlock = this.track.getBlock("Red", "U", new Integer(77));
        yardBlock.setSuggestedSpeed(35.0);
        
        Double str2 = (this.track.getBlock("Red", "C", new Integer(7)).getLen() / 2); 
        //String str = Double.toString((b - 10));
        
        Double b = this.track.getBlock("Red", "C", new Integer(8)).getLen();     
        String str = Double.toString((b - 10) + str2);
        this.track.getBlock("Red", "C", new Integer(8)).addBeacon(str, 10.0);
        
        this.track.getBlock("Red", "C", new Integer(9)).setSwitchState(0);
        endingBlock = this.track.getBlock("Red", "F", new Integer(16));          
    }
    
    /**
     * Sets the states of the radio buttons to a default setting.
     */
    private void setupRadioButtons(){
        
        this.fixACMurphy.setSelected(true);
        this.fixHeatMurphy.setSelected(true);
        this.fixLeftDoorsMurphy.setSelected(true);
        this.fixRightDoorsMurphy.setSelected(true);
        this.fixLightsMurphy.setSelected(true);
        
        this.fixEngineRadioButton.setSelected(true);
        this.fixSignalRadioButton.setSelected(true);
        this.fixBrakeRadioButton.setSelected(true);
        
        this.acOffRadioButton.setSelected(true);
        this.heatOffRadioButton.setSelected(true);
        this.leftDoorsCloseRadioButton.setSelected(true);
        this.rightDoorsCloseRadioButton.setSelected(true);
        this.lightsOffRadioButton.setSelected(true);
    }
         
    /**
     * Sets the Train Controller that the Test Console should use.
     * 
     * @param trainCont the train controller object.
     */
    private void setTrainController(TrainController trainCont){
    
        this.trainController = trainCont;
    }
    
    /**
     * Sets the train that the Test Console should control.
     * 
     * @param train the train object to have the test console control.
     */
    public void setTrain(Train train){
    
        this.selectedTrain = train; 
    }
    
    /**
     * Refreshes the UI elements of the Test Console with the train information.
     */
    public void refreshUI(){
    
       if (this.selectedTrain != null){
            // update labels
            this.trainSpeed.setText(Double.toString(this.selectedTrain.getVelocity()));
            this.trainPower.setText(Double.toString(this.selectedTrain.getPower()/1000));
            this.suggestedSpeed.setText(Double.toString(this.selectedTrain.getSuggestedSpeed()));          
            this.authority.setText(Double.toString(this.selectedTrain.getAuthority().getCurrBlock().blockNum()));
       } 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acButtonGroupMurphy = new javax.swing.ButtonGroup();
        heatButtonGroupMurphy = new javax.swing.ButtonGroup();
        lightsButtonGroupMurphy = new javax.swing.ButtonGroup();
        leftDoorsButtonGroupMurphy = new javax.swing.ButtonGroup();
        rightDoorsButtonGroupMurphy = new javax.swing.ButtonGroup();
        acButtonGroup = new javax.swing.ButtonGroup();
        heatButtonGroup = new javax.swing.ButtonGroup();
        lightsButtonGroup = new javax.swing.ButtonGroup();
        leftDoorsButtonGroup = new javax.swing.ButtonGroup();
        rightDoorsButtonGroups = new javax.swing.ButtonGroup();
        engineButtonGroup = new javax.swing.ButtonGroup();
        signalButtonGroup = new javax.swing.ButtonGroup();
        brakeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        trainSpeedLabel = new javax.swing.JLabel();
        trainPowerLabel = new javax.swing.JLabel();
        suggestedSpeedLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        breakACMurphy = new javax.swing.JRadioButton();
        fixACMurphy = new javax.swing.JRadioButton();
        breakHeatMurphy = new javax.swing.JRadioButton();
        fixHeatMurphy = new javax.swing.JRadioButton();
        breakLightsMurphy = new javax.swing.JRadioButton();
        fixLightsMurphy = new javax.swing.JRadioButton();
        breakLeftDoorsMurphy = new javax.swing.JRadioButton();
        fixLeftDoorsMurphy = new javax.swing.JRadioButton();
        breakRightDoorsMurphy = new javax.swing.JRadioButton();
        fixRightDoorsMurphy = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        acLabel = new javax.swing.JLabel();
        heatLabel = new javax.swing.JLabel();
        lightsLabel = new javax.swing.JLabel();
        rightDoorsLabel = new javax.swing.JLabel();
        leftDoorsLabel = new javax.swing.JLabel();
        acOnRadioButton = new javax.swing.JRadioButton();
        acOffRadioButton = new javax.swing.JRadioButton();
        heatOffRadioButton = new javax.swing.JRadioButton();
        heatOnRadioButton = new javax.swing.JRadioButton();
        lightsOnRadioButton = new javax.swing.JRadioButton();
        lightsOffRadioButton = new javax.swing.JRadioButton();
        leftDoorsOpenRadioButton = new javax.swing.JRadioButton();
        leftDoorsCloseRadioButton = new javax.swing.JRadioButton();
        rightDoorsOpenRadioButton = new javax.swing.JRadioButton();
        rightDoorsCloseRadioButton = new javax.swing.JRadioButton();
        trainSpeed = new javax.swing.JLabel();
        trainPower = new javax.swing.JLabel();
        suggestedSpeed = new javax.swing.JLabel();
        authority = new javax.swing.JLabel();
        dispatchTrainsButton = new javax.swing.JButton();
        engineFailureLabel = new javax.swing.JLabel();
        brakeFailureLabel = new javax.swing.JLabel();
        signalFailureLabel = new javax.swing.JLabel();
        breakEnginerMurphy = new javax.swing.JRadioButton();
        fixEngineRadioButton = new javax.swing.JRadioButton();
        breakSignalMurphy = new javax.swing.JRadioButton();
        fixSignalRadioButton = new javax.swing.JRadioButton();
        breakBrakeMurphy = new javax.swing.JRadioButton();
        fixBrakeRadioButton = new javax.swing.JRadioButton();
        fixFailureButton = new javax.swing.JButton();
        requestFixButton = new javax.swing.JButton();
        playNormalSpeedButton = new javax.swing.JButton();
        playFastSpeedButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        trainSpeedLabel.setText("Speed:");

        trainPowerLabel.setText("Power:");

        suggestedSpeedLabel.setText("Suggested Speed:");

        jLabel6.setText("Authority (Block Id):");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText(" MPH");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("kW");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("MPH");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel11.setText("Murphy");

        jLabel12.setText("AC:");

        jLabel13.setText("Heat:");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("Lights:");

        jLabel15.setText("Left Door:");

        jLabel16.setText("Right Door:");

        acButtonGroupMurphy.add(breakACMurphy);
        breakACMurphy.setText("Break");
        breakACMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakAC(evt);
            }
        });

        acButtonGroupMurphy.add(fixACMurphy);
        fixACMurphy.setText("Unbreak");
        fixACMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixAC(evt);
            }
        });

        heatButtonGroupMurphy.add(breakHeatMurphy);
        breakHeatMurphy.setText("Break");
        breakHeatMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakHeat(evt);
            }
        });

        heatButtonGroupMurphy.add(fixHeatMurphy);
        fixHeatMurphy.setText("Unbreak");
        fixHeatMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixHeat(evt);
            }
        });

        lightsButtonGroupMurphy.add(breakLightsMurphy);
        breakLightsMurphy.setText("Break");
        breakLightsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakLightsMurphy(evt);
            }
        });

        lightsButtonGroupMurphy.add(fixLightsMurphy);
        fixLightsMurphy.setText("Unbreak");
        fixLightsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixLights(evt);
            }
        });

        leftDoorsButtonGroupMurphy.add(breakLeftDoorsMurphy);
        breakLeftDoorsMurphy.setText("Break");
        breakLeftDoorsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakLeftDoors(evt);
            }
        });

        leftDoorsButtonGroupMurphy.add(fixLeftDoorsMurphy);
        fixLeftDoorsMurphy.setText("Unbreak");
        fixLeftDoorsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixLeftDoors(evt);
            }
        });

        rightDoorsButtonGroupMurphy.add(breakRightDoorsMurphy);
        breakRightDoorsMurphy.setText("Break");
        breakRightDoorsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakRightDoors(evt);
            }
        });

        rightDoorsButtonGroupMurphy.add(fixRightDoorsMurphy);
        fixRightDoorsMurphy.setText("Unbreak");
        fixRightDoorsMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixRightDoors(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        acLabel.setText("AC:");

        heatLabel.setText("Heat:");

        lightsLabel.setText("Lights:");

        rightDoorsLabel.setText("Right Door:");

        leftDoorsLabel.setText("Left Door:");

        acButtonGroup.add(acOnRadioButton);
        acOnRadioButton.setText("ON");
        acOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnAC(evt);
            }
        });

        acButtonGroup.add(acOffRadioButton);
        acOffRadioButton.setText("OFF");
        acOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffAC(evt);
            }
        });

        heatButtonGroup.add(heatOffRadioButton);
        heatOffRadioButton.setText("OFF");
        heatOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffHeat(evt);
            }
        });

        heatButtonGroup.add(heatOnRadioButton);
        heatOnRadioButton.setText("ON");
        heatOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnHeat(evt);
            }
        });

        lightsButtonGroup.add(lightsOnRadioButton);
        lightsOnRadioButton.setText("ON");
        lightsOnRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOnLights(evt);
            }
        });

        lightsButtonGroup.add(lightsOffRadioButton);
        lightsOffRadioButton.setText("OFF");
        lightsOffRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnOffLights(evt);
            }
        });

        leftDoorsButtonGroup.add(leftDoorsOpenRadioButton);
        leftDoorsOpenRadioButton.setText("OPEN");
        leftDoorsOpenRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openLeftDoors(evt);
            }
        });

        leftDoorsButtonGroup.add(leftDoorsCloseRadioButton);
        leftDoorsCloseRadioButton.setText("CLOSE");
        leftDoorsCloseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeLeftDoors(evt);
            }
        });

        rightDoorsButtonGroups.add(rightDoorsOpenRadioButton);
        rightDoorsOpenRadioButton.setText("OPEN");
        rightDoorsOpenRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRightDoors(evt);
            }
        });

        rightDoorsButtonGroups.add(rightDoorsCloseRadioButton);
        rightDoorsCloseRadioButton.setText("CLOSE");
        rightDoorsCloseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeRightDoors(evt);
            }
        });

        trainSpeed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        trainSpeed.setText("0");

        trainPower.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        trainPower.setText("0");

        suggestedSpeed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        suggestedSpeed.setText("0");

        authority.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        authority.setText("0");

        dispatchTrainsButton.setText("Dispatch Train");
        dispatchTrainsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispatchTrains(evt);
            }
        });

        engineFailureLabel.setText("Engine:");

        brakeFailureLabel.setText("Brake:");

        signalFailureLabel.setText("Signal:");

        engineButtonGroup.add(breakEnginerMurphy);
        breakEnginerMurphy.setText("Break");
        breakEnginerMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakEnginerMurphybreakRightDoors(evt);
            }
        });

        engineButtonGroup.add(fixEngineRadioButton);
        fixEngineRadioButton.setText("Unbreak");
        fixEngineRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixEngineRadioButtonfixRightDoors(evt);
            }
        });

        signalButtonGroup.add(breakSignalMurphy);
        breakSignalMurphy.setText("Break");
        breakSignalMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakSignalMurphybreakRightDoors(evt);
            }
        });

        signalButtonGroup.add(fixSignalRadioButton);
        fixSignalRadioButton.setText("Unbreak");
        fixSignalRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixSignalRadioButtonfixRightDoors(evt);
            }
        });

        brakeButtonGroup.add(breakBrakeMurphy);
        breakBrakeMurphy.setText("Break");
        breakBrakeMurphy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                failBrakes(evt);
            }
        });

        brakeButtonGroup.add(fixBrakeRadioButton);
        fixBrakeRadioButton.setText("Unbreak");
        fixBrakeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixBrakes(evt);
            }
        });

        fixFailureButton.setText("Fix");
        fixFailureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixFailures(evt);
            }
        });

        requestFixButton.setText("Request Fix");
        requestFixButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestFix(evt);
            }
        });

        playNormalSpeedButton.setText("Normal Speed");
        playNormalSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playNormalSpeed(evt);
            }
        });

        playFastSpeedButton.setText("Fast Speed");
        playFastSpeedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playFastSpeed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setText("Utilities");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(trainSpeedLabel)
                            .addComponent(trainPowerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(trainPower, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(trainSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(suggestedSpeedLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(authority, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(suggestedSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addGap(15, 15, 15)
                                            .addComponent(breakLeftDoorsMurphy))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(breakRightDoorsMurphy))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel12)
                                                .addComponent(jLabel14)
                                                .addComponent(jLabel13))
                                            .addGap(35, 35, 35)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(breakHeatMurphy)
                                                .addComponent(breakACMurphy)
                                                .addComponent(breakLightsMurphy))))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(fixLeftDoorsMurphy)
                                        .addComponent(fixRightDoorsMurphy)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fixHeatMurphy)
                                            .addComponent(fixACMurphy)
                                            .addComponent(fixLightsMurphy))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(89, 89, 89)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(engineFailureLabel)
                                    .addComponent(signalFailureLabel)
                                    .addComponent(brakeFailureLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(breakSignalMurphy)
                                        .addGap(18, 18, 18)
                                        .addComponent(fixSignalRadioButton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(breakEnginerMurphy)
                                        .addGap(18, 18, 18)
                                        .addComponent(fixEngineRadioButton))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(breakBrakeMurphy)
                                        .addGap(18, 18, 18)
                                        .addComponent(fixBrakeRadioButton)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(acLabel)
                                .addComponent(heatLabel)
                                .addComponent(lightsLabel)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(fixFailureButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(playNormalSpeedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(leftDoorsLabel)
                                        .addComponent(rightDoorsLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(rightDoorsOpenRadioButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rightDoorsCloseRadioButton))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(leftDoorsOpenRadioButton)
                                                .addComponent(jLabel1)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(heatOnRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(acOnRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lightsOnRadioButton)))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lightsOffRadioButton)
                                                .addComponent(leftDoorsCloseRadioButton)
                                                .addComponent(heatOffRadioButton)
                                                .addComponent(acOffRadioButton)))))
                                .addComponent(dispatchTrainsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(requestFixButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(playFastSpeedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(trainSpeedLabel)
                        .addGap(18, 18, 18)
                        .addComponent(trainPowerLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(trainSpeed))
                                    .addGap(34, 34, 34))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(trainPower))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(suggestedSpeedLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel6))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(suggestedSpeed))
                                    .addGap(18, 18, 18)
                                    .addComponent(authority))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel12)
                                            .addComponent(breakACMurphy))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13)
                                            .addComponent(breakHeatMurphy))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(breakLightsMurphy))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel15)
                                            .addComponent(breakLeftDoorsMurphy)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(fixACMurphy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fixHeatMurphy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fixLightsMurphy)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fixLeftDoorsMurphy)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(breakRightDoorsMurphy))
                                    .addComponent(fixRightDoorsMurphy)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(acLabel)
                                            .addComponent(acOffRadioButton))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(heatOffRadioButton)
                                            .addComponent(heatLabel)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(acOnRadioButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(heatOnRadioButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lightsOnRadioButton)
                                    .addComponent(lightsLabel)
                                    .addComponent(lightsOffRadioButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(leftDoorsOpenRadioButton)
                                    .addComponent(leftDoorsCloseRadioButton)
                                    .addComponent(leftDoorsLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rightDoorsOpenRadioButton)
                                        .addComponent(rightDoorsCloseRadioButton))
                                    .addComponent(rightDoorsLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(breakEnginerMurphy)
                                        .addComponent(engineFailureLabel))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fixEngineRadioButton)
                                        .addComponent(dispatchTrainsButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(breakSignalMurphy)
                                        .addComponent(signalFailureLabel))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fixSignalRadioButton)
                                        .addComponent(fixFailureButton)
                                        .addComponent(playNormalSpeedButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(breakBrakeMurphy)
                                        .addComponent(brakeFailureLabel))
                                    .addComponent(fixBrakeRadioButton)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(requestFixButton)
                                .addComponent(playFastSpeedButton)))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jSeparator3)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Breaks the ac on the selected train.
     * 
     * @param evt 
     */
    private void breakAC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakAC
       
        this.selectedTrain.setAC( -1 );    
    }//GEN-LAST:event_breakAC

    /**
     * Fixes the ac on the selected train.
     * 
     * @param evt 
     */
    private void fixAC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixAC
        
        this.selectedTrain.setAC( 0 ); 
    }//GEN-LAST:event_fixAC

    /**
     * Breaks the heat on the selected train.
     * 
     * @param evt 
     */
    private void breakHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakHeat
        
        this.selectedTrain.setHeat( -1 );
    }//GEN-LAST:event_breakHeat

    /**
     * Fixes the heat on the selected train.
     * 
     * @param evt 
     */
    private void fixHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixHeat
        
        this.selectedTrain.setHeat( 0 ); 
    }//GEN-LAST:event_fixHeat

    /**
     * Breaks the lights on the selected train.
     * 
     * @param evt 
     */
    private void breakLightsMurphy(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakLightsMurphy
        this.selectedTrain.setLights( -1 );
    }//GEN-LAST:event_breakLightsMurphy

    /**
     * Fixes the lights on the selected train. 
     * 
     * @param evt 
     */
    private void fixLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixLights
        this.selectedTrain.setLights( 0 ); 
    }//GEN-LAST:event_fixLights

    /**
     * Breaks the left doors on the selected train.
     * 
     * @param evt 
     */
    private void breakLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakLeftDoors
        
        this.selectedTrain.setLeftDoor( -1 );
    }//GEN-LAST:event_breakLeftDoors

    /**
     * Fixes the left doors on the selected train.
     * 
     * @param evt 
     */
    private void fixLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixLeftDoors
        
        this.selectedTrain.setLeftDoor( 0 );
    }//GEN-LAST:event_fixLeftDoors

    /**
     * Breaks the right doors on the selected train.
     * 
     * @param evt 
     */
    private void breakRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakRightDoors
        this.selectedTrain.setRightDoor( -1 );
    }//GEN-LAST:event_breakRightDoors

    /**
     * Fixes the right doors on the selected train.
     * 
     * @param evt 
     */
    private void fixRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixRightDoors
        this.selectedTrain.setRightDoor( 0 ); 
    }//GEN-LAST:event_fixRightDoors

    /**
     * Turns on the ac on the selected train.
     * 
     * @param evt 
     */
    private void turnOnAC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnAC
        
        this.selectedTrain.setAC( 1 );
    }//GEN-LAST:event_turnOnAC

    /**
     * Turns off the ac on the selected train.
     * 
     * @param evt 
     */
    private void turnOffAC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffAC
        
        this.selectedTrain.setAC( 0 );
    }//GEN-LAST:event_turnOffAC

    /**
     * Turns on the heat on the selected train.
     * 
     * @param evt 
     */
    private void turnOnHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnHeat
        
        this.selectedTrain.setHeat( 1 );
    }//GEN-LAST:event_turnOnHeat

    /**
     * Turns off the heat on the selected train.
     * 
     * @param evt 
     */
    private void turnOffHeat(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffHeat
       
        this.selectedTrain.setHeat( 0 );
    }//GEN-LAST:event_turnOffHeat

    /**
     * Turns on the lights on the selected train.
     * 
     * @param evt 
     */
    private void turnOnLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOnLights
        
        this.selectedTrain.setLights( 1 );
    }//GEN-LAST:event_turnOnLights

    /**
     * Turns off the lights on the selected train.
     * 
     * @param evt 
     */
    private void turnOffLights(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnOffLights
        
        this.selectedTrain.setLights( 0 );
    }//GEN-LAST:event_turnOffLights

    /**
     * Opens the left doors on the selected train.
     * 
     * @param evt 
     */
    private void openLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLeftDoors
        
        this.selectedTrain.setLeftDoor( 1 );
    }//GEN-LAST:event_openLeftDoors

    /**
     * Closes the left doors on the selected train.
     * 
     * @param evt 
     */
    private void closeLeftDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeLeftDoors
        
        this.selectedTrain.setLeftDoor( 0 );
    }//GEN-LAST:event_closeLeftDoors

    /**
     * Opens the right doors on the selected train.
     * 
     * @param evt 
     */
    private void openRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRightDoors
        
        this.selectedTrain.setRightDoor(1);
    }//GEN-LAST:event_openRightDoors

    /**
     * Closes the right doors on the selected train.
     * 
     * @param evt 
     */
    private void closeRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeRightDoors
        
        this.selectedTrain.setRightDoor( 0 );
    }//GEN-LAST:event_closeRightDoors

    /**
     * Dispatches a train, opening a new TrainController. 
     * 
     * @param evt the button
     */
    private void dispatchTrains(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dispatchTrains
       
        this.dispatchedTrains++;         
		GPS newA = new GPS();
		newA.setCurrBlock(this.endingBlock);
		newA.setDistIntoBlock(null);
        this.trainHandler.setSpeedAndAuthority(-1, 35.0, newA, this.yardBlock);

        this.selectedTrain = this.trainHandler.findTrain(this.dispatchedTrains);
        
        TrainController tc = this.trainHandler.getOpenTrainControllers().get(this.dispatchedTrains-1);
        tc.setTestConsole(this);
        tc.testWindowOpen = true; 
        
        if (this.trainController != null){ this.trainController.setTrainListComboBox(); }
    }//GEN-LAST:event_dispatchTrains

    private void breakEnginerMurphybreakRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakEnginerMurphybreakRightDoors
        // TODO add your handling code here:
    }//GEN-LAST:event_breakEnginerMurphybreakRightDoors

    private void fixEngineRadioButtonfixRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixEngineRadioButtonfixRightDoors
        // TODO add your handling code here:
    }//GEN-LAST:event_fixEngineRadioButtonfixRightDoors

    private void breakSignalMurphybreakRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakSignalMurphybreakRightDoors
        // TODO add your handling code here:
    }//GEN-LAST:event_breakSignalMurphybreakRightDoors

    private void fixSignalRadioButtonfixRightDoors(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixSignalRadioButtonfixRightDoors
        // TODO add your handling code here:
    }//GEN-LAST:event_fixSignalRadioButtonfixRightDoors


    /**
     * Fixes the failures on the selected train instantly.
     * 
     * @param evt 
     */
    private void fixFailures(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixFailures
        
        // reset failures
        this.selectedTrain.setBrakeFailure(false);
        this.selectedTrain.setSignalFailure(false);
        this.selectedTrain.setEngineFailure(false);
    }//GEN-LAST:event_fixFailures

    /**
     * Breaks the brakes on the selected train.
     * 
     * @param evt 
     */

    private void failBrakes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_failBrakes
      
        this.selectedTrain.setBrakeFailure(true);
    }//GEN-LAST:event_failBrakes

    /**
     * Fixes the brakes on the selected train.
     * 
     * @param evt 
     */
    private void fixBrakes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixBrakes
       
        this.selectedTrain.setBrakeFailure(false);
    }//GEN-LAST:event_fixBrakes

    /**
     * Tells the train to put a request down on the track in order to be fixed. 
     *
     * @param evt 
     */
    private void requestFix(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestFix
       
        this.selectedTrain.requestFix(true);
    }//GEN-LAST:event_requestFix

    /**
     * Plays the open train controllers in normal speed. 
     * 
     * @param evt 
     */
    private void playNormalSpeed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playNormalSpeed
        
        for (TrainController tc : this.trainHandler.openTrainControllers){
        
            //tc.playNormal();
        }  
    }//GEN-LAST:event_playNormalSpeed

    /**
     * Plays the open train controllers in fast speed. 
     * 
     * @param evt 
     */
    private void playFastSpeed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playFastSpeed
        
        for (TrainController tc : this.trainHandler.openTrainControllers){
        
            //tc.playFast();
        }        
    }//GEN-LAST:event_playFastSpeed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TCTestConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TCTestConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TCTestConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TCTestConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new TCTestConsole().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup acButtonGroup;
    private javax.swing.ButtonGroup acButtonGroupMurphy;
    private javax.swing.JLabel acLabel;
    private javax.swing.JRadioButton acOffRadioButton;
    private javax.swing.JRadioButton acOnRadioButton;
    private javax.swing.JLabel authority;
    private javax.swing.ButtonGroup brakeButtonGroup;
    private javax.swing.JLabel brakeFailureLabel;
    private javax.swing.JRadioButton breakACMurphy;
    private javax.swing.JRadioButton breakBrakeMurphy;
    private javax.swing.JRadioButton breakEnginerMurphy;
    private javax.swing.JRadioButton breakHeatMurphy;
    private javax.swing.JRadioButton breakLeftDoorsMurphy;
    private javax.swing.JRadioButton breakLightsMurphy;
    private javax.swing.JRadioButton breakRightDoorsMurphy;
    private javax.swing.JRadioButton breakSignalMurphy;
    private javax.swing.JButton dispatchTrainsButton;
    private javax.swing.ButtonGroup engineButtonGroup;
    private javax.swing.JLabel engineFailureLabel;
    private javax.swing.JRadioButton fixACMurphy;
    private javax.swing.JRadioButton fixBrakeRadioButton;
    private javax.swing.JRadioButton fixEngineRadioButton;
    private javax.swing.JButton fixFailureButton;
    private javax.swing.JRadioButton fixHeatMurphy;
    private javax.swing.JRadioButton fixLeftDoorsMurphy;
    private javax.swing.JRadioButton fixLightsMurphy;
    private javax.swing.JRadioButton fixRightDoorsMurphy;
    private javax.swing.JRadioButton fixSignalRadioButton;
    private javax.swing.ButtonGroup heatButtonGroup;
    private javax.swing.ButtonGroup heatButtonGroupMurphy;
    private javax.swing.JLabel heatLabel;
    private javax.swing.JRadioButton heatOffRadioButton;
    private javax.swing.JRadioButton heatOnRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.ButtonGroup leftDoorsButtonGroup;
    private javax.swing.ButtonGroup leftDoorsButtonGroupMurphy;
    private javax.swing.JRadioButton leftDoorsCloseRadioButton;
    private javax.swing.JLabel leftDoorsLabel;
    private javax.swing.JRadioButton leftDoorsOpenRadioButton;
    private javax.swing.ButtonGroup lightsButtonGroup;
    private javax.swing.ButtonGroup lightsButtonGroupMurphy;
    private javax.swing.JLabel lightsLabel;
    private javax.swing.JRadioButton lightsOffRadioButton;
    private javax.swing.JRadioButton lightsOnRadioButton;
    private javax.swing.JButton playFastSpeedButton;
    private javax.swing.JButton playNormalSpeedButton;
    private javax.swing.JButton requestFixButton;
    private javax.swing.ButtonGroup rightDoorsButtonGroupMurphy;
    private javax.swing.ButtonGroup rightDoorsButtonGroups;
    private javax.swing.JRadioButton rightDoorsCloseRadioButton;
    private javax.swing.JLabel rightDoorsLabel;
    private javax.swing.JRadioButton rightDoorsOpenRadioButton;
    private javax.swing.ButtonGroup signalButtonGroup;
    private javax.swing.JLabel signalFailureLabel;
    private javax.swing.JLabel suggestedSpeed;
    private javax.swing.JLabel suggestedSpeedLabel;
    private javax.swing.JLabel trainPower;
    private javax.swing.JLabel trainPowerLabel;
    private javax.swing.JLabel trainSpeed;
    private javax.swing.JLabel trainSpeedLabel;
    // End of variables declaration//GEN-END:variables
}
