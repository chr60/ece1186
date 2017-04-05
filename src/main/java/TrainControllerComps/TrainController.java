package TrainControllerComps;

import TrackModel.TrackModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

import TrainModel.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is a GUI that is used to control a selected train. The selected train
 * is a Train object that is selected by using the train drop down menu.
 *
 * This class communicates with other components such as:
 *
 * TCTrainInfoPanel - displays basic train info such as speed, power, authority, and suggested speed.
 * TCSpeedController - allows the user to control the train's speed.
 *
 * @author Andrew Lendacky
 */
public class TrainController extends javax.swing.JFrame {

    // Data Structures:
    // used to get a dispatched train by its ID.
    private HashMap<String, Train> trainList = new HashMap<String, Train>();

    // Train Stuff:
    private Train selectedTrain; // the train that the Train Controller is controlling.

    // Modes:
    private boolean manualMode; // used to tell if the Train Controller is in Manual mode
    private boolean automaticMode; // used to tell if the Train Controller is in Automatic mode

    private boolean normalMode; // used to tell if the Train Controller is in Manual mode
    private boolean testingMode; // used to tell if the Train Controller is in Automatic mode

    // FOR TESTING!
    //ArrayList<Train> trains = new ArrayList<Train>();

    double blockSpeed = 80.0;
    private TCTestConsole testConsole = null;


    public boolean detailedTrainWindowOpen;

    TrainModeUI trainUI;

    // FOR TESTING:
    TrackModel track;
    TrainHandler redLineHandler;

    // used to update GUI every millisecond (1 s)
    // FIX ME: This time should be set by the CTC can be
    // wall speed or 10x faster ()
    private Timer systemRunSpeed = new Timer(1000, new ActionListener(){
        Random rand = new Random();
        public void actionPerformed(ActionEvent e) {

            if (detailedTrainWindowOpen == true){ trainUI.updateGUI(selectedTrain); }

            if (selectedTrain != null && selectedTrain.powerConstantsSet() ){ refreshComponents(); }

            // Do specific things if in testing mode...
            if (testingMode == true){

                if (testConsole != null){
                    // update test console
                    testConsole.setTrain(selectedTrain);
                }
            }else if (normalMode == true){
                // do things in normal mode
                if (manualMode == true){
                    // do manual mode thing
                }else if (automaticMode == true){
                    // automate things based on train stuff
                }
            }

            }
        });

    // MARK: - Constructors

    /**
     * Constructor that creates a Train Controller.
     * By default the starting mode is 'Manual' mode.
     *
     */
    public TrainController() {

        initComponents();

        // FIX ME: This is for testing purposes, and should be removed.
        //String[] fNames = {"src/test/resources/redline.csv"};
        //this.track = new TrackModel("Test");
        //this.track.readCSV(fNames);

        //this.redLineHandler = new TrainHandler(this.track);

        //this.trains.add(train);

        //this.initHashMaps();
        //this.setTrainListComboBox();
        this.setMode("Manual", "Normal");


        this.speedController.setOperatingLogs(this.operatingLogs);
        this.selectedTrain = null;

        this.utilityPanel.setVitalsButton(this.vitals);
        this.detailedTrainWindowOpen = false;
        systemRunSpeed.start();

    }

    /**
     * Constructor that creates a Train Controller for a give train in Manual and Normal mode.
     *
     * @param train the train the controller will launch with.
     */
    public TrainController(Train train){

        initComponents();
        this.setMode("Manual", "Normal");

        this.selectedTrain = train;

        this.speedController.setOperatingLogs(this.operatingLogs);
        this.utilityPanel.setVitalsButton(this.vitals);
        this.detailedTrainWindowOpen = false;

        // check if kp/ki is set
        if (this.selectedTrain.powerConstantsSet() == false){

            TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain);
            engPanel.setVisible(true);
            engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        
        systemRunSpeed.start();
    }

    /**
     * Constructor that creates a Train Controller in a given mode.
     *
     * @param playMode the mode (Manual or Automatic) that the Train Controller will launch in.
     * @param testMode the mode (Normal or Testing) that the Train Controller will launch in.
     */
    public TrainController(String playMode, String testMode){

        initComponents();

        this.initHashMaps();
        this.setTrainListComboBox();
        this.setMode(playMode, testMode);

        this.selectedTrain = null;

        systemRunSpeed.start();
    }

    /**
     * Constructor that creates a Train Controller in a given Test and Play mode with a given train.
     *
     * @param train the train the Train Controller with control.
     * @param playMode the mode (Manual or Automatic) that the Train Controller will launch in.
     * @param testMode the mode (Normal or Testing) that the Train Controller will launch in.
     */
    public TrainController(Train train, String playMode, String testMode){

        initComponents();
        this.initHashMaps();
        this.setTrainListComboBox();
        this.setMode(playMode, testMode);

        this.selectedTrain = train;

        if (this.selectedTrain.powerConstantsSet() == false){

            TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain);
            engPanel.setVisible(true);
            engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        systemRunSpeed.start();
    }

    /**
     * Retrieves the TrainInfoPanel of the Train Controller.
     *
     * @return returns the Train Info panel.
     */
    public TCTrainInfoPane getTrainInfoPane(){

        return this.trainInfoPanel;
    }

    /**
     * Retrieves the SpeedController component of the Train Controller.
     *
     *
     * @return returns the Speed Controller panel.
     */
    public TCSpeedController getSpeedController(){

        return this.speedController;
    }

    public TCBlockInfoPanel getBlockInfoPane(){

        return this.blockInfoPane;
    }

    // MARK: - Mode Setting and Getting

    /**
     * Sets the modes of the Train Controller.
     *
     * @param playMode the mode (Manual or Automatic) that the Train Controller will launch in.
     * @param testMode the mode (Normal or Testing) that the Train Controller will launch in.
     */
    private void setMode(String playMode, String testMode){

        if (playMode.equals("Automatic")){

            this.automaticMode = true;
            this.manualMode = false;
            this.automaticModeRadioButton.setSelected(true);


        }else if (playMode.equals("Manual")){

            this.manualMode = true;
            this.automaticMode = false;
            this.manualModeRadioButton.setSelected(true);

        }else{
            System.out.println("No Real Mode Picked. Must be 'Automatic' or 'Manual' ");
        }


        if (testMode.equals("Normal")){

            this.normalMode = true;
            this.testingMode = false;
            this.normalModeRadioButton.setSelected(true);

        }else if (testMode.equals("Testing")){

            this.testingMode = true;
            this.normalMode = false;
            this.testingModeRadioButton.setSelected(true);

        }else{
            System.out.println("No Real Mode Picked. Must be 'Testing' or 'Normal' ");
        }
    }

    /**
     * Retrieves which Play mode the Train Controller is in.
     *
     * @return returns either Manual if the Train Controller is in manual mode, and "Automatic" if in automatic mode.
     */
    public String getPlayMode(){

        if (this.manualMode == true){ return "Manual"; }
        else if(this.automaticMode == true){ return "Automatic"; }
        else{ return null; } // no mode was set
    }

    /**
     * Retrieves which Test mode the Train Controller is in.
     *
     * @return returns either Testing if the Train Controller is in testing mode, and "Normal" if in normal mode.
     */
    public String getTestMode(){

        if (this.testingMode == true){ return "Testing"; }
        else if(this.normalMode == true){ return "Normal"; }
        else{ return null; } // no mode was set
    }

    // MARK: - Configure Data Structures

    /**
     * Takes the list of dispatched trains, and stores them in a HashMap with the key-value pair as
     * the train's id and the train object.
     */
    private void initHashMaps(){

        // get the list of dispatched trains
        for (Train train : this.redLineHandler.getTrains()){
            // add them to the hashmaps
            this.trainList.put(Integer.toString(train.getID()), train );
        }
    }

    // MARK: - Train Stuff

    /**
     * Sets the selected train that the Train Controller will be controlling.
     *
     * @param train the train object that the Train Controller will control.
     */
    private void setTrain(Train train){

        this.selectedTrain = train;
    }

    /**
     * Retrieves the selected train that the Train Controller is controlling.
     *
     * @return returns the selected train that the Train Controller is controlling, or returns null if no train is selected.
     */
    public Train getTrain(){

        if (this.selectedTrain == null){
            System.out.println("No train is selected");
            return null;
        }else{
            return this.selectedTrain;
        }
    }

    // MARK: - Configure UI

    /**
     * Updates the combo box that contains the dispatched trains.
     */
    public void setTrainListComboBox(){

        System.out.println(this.redLineHandler.getTrains().size());

        this.dispatchedTrains.removeAllItems();
        this.dispatchedTrains.addItem("No Train Selected");

        for (Train train : this.redLineHandler.getTrains()){

            this.dispatchedTrains.addItem(Integer.toString(train.getID()) );
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

        auto_manGroup = new javax.swing.ButtonGroup();
        normal_testGroup = new javax.swing.ButtonGroup();
        trainInfoTitle = new javax.swing.JLabel();
        trainSelectionTitle = new javax.swing.JLabel();
        brakesTitle = new javax.swing.JLabel();
        uiSeparatorThree = new javax.swing.JSeparator();
        uiSeparatorTwo = new javax.swing.JSeparator();
        timeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dispatchedTrains = new javax.swing.JComboBox<>();
        switchTrains = new javax.swing.JButton();
        setKpAndKi = new javax.swing.JButton();
        vitals = new javax.swing.JButton();
        modeSelectionTitle = new javax.swing.JLabel();
        uiSeparatorOne = new javax.swing.JSeparator();
        automaticModeRadioButton = new javax.swing.JRadioButton();
        manualModeRadioButton = new javax.swing.JRadioButton();
        normalModeRadioButton = new javax.swing.JRadioButton();
        testingModeRadioButton = new javax.swing.JRadioButton();
        blockInfoTitle = new javax.swing.JLabel();
        notificationsTitle = new javax.swing.JLabel();
        utilitiesTitle = new javax.swing.JLabel();
        speedControllerTitle = new javax.swing.JLabel();
        uiSeparatorFive = new javax.swing.JSeparator();
        uiSeparatorSix = new javax.swing.JSeparator();
        announcementScrollPane = new javax.swing.JScrollPane();
        annoucementLogs = new javax.swing.JTextArea();
        operatingLogsScrollPane = new javax.swing.JScrollPane();
        operatingLogs = new javax.swing.JTextArea();
        errorLogsLabel = new javax.swing.JLabel();
        operatingLogsLabel = new javax.swing.JLabel();
        announcementsLabel = new javax.swing.JLabel();
        annoucementDropDown = new javax.swing.JComboBox<>();
        chooseAnnouncementLabel = new javax.swing.JLabel();
        makeAnnouncementButton = new javax.swing.JButton();
        date = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        uiSeparatorFour = new javax.swing.JSeparator();
        speedController = new TrainControllerComps.TCSpeedController();
        errorLogScrollPane = new javax.swing.JScrollPane();
        errorLogs = new javax.swing.JTextPane();
        clearOperatingLog = new javax.swing.JButton();
        clearErrorLog = new javax.swing.JButton();
        clearAnnouncements = new javax.swing.JButton();
        trainInfoPanel = new TrainControllerComps.TCTrainInfoPane();
        utilityPanel = new TrainControllerComps.TCUtilityPanel();
        brakePanel = new TrainControllerComps.TCBrakePanel();
        blockInfoPane = new TrainControllerComps.TCBlockInfoPanel();
        menuBar = new javax.swing.JMenuBar();
        viewMenu = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        trainInfoTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        trainInfoTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        trainInfoTitle.setText("Train Info");

        trainSelectionTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        trainSelectionTitle.setText("Train Selection");

        brakesTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        brakesTitle.setText("Brakes");

        uiSeparatorThree.setForeground(new java.awt.Color(0, 0, 0));

        uiSeparatorTwo.setForeground(new java.awt.Color(0, 0, 0));
        uiSeparatorTwo.setOrientation(javax.swing.SwingConstants.VERTICAL);

        timeLabel.setText("Time:");

        dateLabel.setText("Date:");

        dispatchedTrains.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Train Selected" }));

        switchTrains.setText("Switch");
        switchTrains.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchTrains(evt);
            }
        });

        setKpAndKi.setText("Set Kp/Ki");
        setKpAndKi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setKpAndKi(evt);
            }
        });

        vitals.setText("Vitals");
        vitals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewVitals(evt);
            }
        });

        modeSelectionTitle.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        modeSelectionTitle.setText("Mode Selection");

        uiSeparatorOne.setForeground(new java.awt.Color(0, 0, 0));

        auto_manGroup.add(automaticModeRadioButton);
        automaticModeRadioButton.setText("Automatic");
        automaticModeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchToAutoMode(evt);
            }
        });

        auto_manGroup.add(manualModeRadioButton);
        manualModeRadioButton.setText("Manual");
        manualModeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchToManualMode(evt);
            }
        });

        normal_testGroup.add(normalModeRadioButton);
        normalModeRadioButton.setText("Normal");
        normalModeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalModeSelected(evt);
            }
        });

        normal_testGroup.add(testingModeRadioButton);
        testingModeRadioButton.setText("Testing");
        testingModeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testModeSelected(evt);
            }
        });

        blockInfoTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        blockInfoTitle.setText("Block Info");

        notificationsTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        notificationsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notificationsTitle.setText("Notifications");

        utilitiesTitle.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        utilitiesTitle.setText("Utilities");

        speedControllerTitle.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        speedControllerTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        speedControllerTitle.setText("Speed Controller");

        uiSeparatorFive.setForeground(new java.awt.Color(0, 0, 0));
        uiSeparatorFive.setOrientation(javax.swing.SwingConstants.VERTICAL);

        uiSeparatorSix.setForeground(new java.awt.Color(0, 0, 0));
        uiSeparatorSix.setOrientation(javax.swing.SwingConstants.VERTICAL);

        annoucementLogs.setColumns(20);
        annoucementLogs.setRows(5);
        announcementScrollPane.setViewportView(annoucementLogs);

        operatingLogs.setColumns(20);
        operatingLogs.setRows(5);
        operatingLogsScrollPane.setViewportView(operatingLogs);

        errorLogsLabel.setText("Error Logs:");

        operatingLogsLabel.setText("Operating Logs:");

        announcementsLabel.setText("Announcements:");

        annoucementDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Announcement", "Announce Station", "Announce Next Stop", "Annouce Weather", "Annouce Time" }));
        annoucementDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annoucementDropDownActionPerformed(evt);
            }
        });

        chooseAnnouncementLabel.setText("Choose Announcement");

        makeAnnouncementButton.setText("Make Announcement");
        makeAnnouncementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeAnnouncement(evt);
            }
        });

        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        uiSeparatorFour.setForeground(new java.awt.Color(0, 0, 0));
        uiSeparatorFour.setOrientation(javax.swing.SwingConstants.VERTICAL);

        errorLogs.setEditable(false);
        errorLogScrollPane.setViewportView(errorLogs);

        clearOperatingLog.setText("Clear");
        clearOperatingLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOperatingLogs(evt);
            }
        });

        clearErrorLog.setText("Clear");
        clearErrorLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearErrorLogs(evt);
            }
        });

        clearAnnouncements.setText("Clear");
        clearAnnouncements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAnnouncements(evt);
            }
        });

        viewMenu.setText("View");

        jMenuItem5.setText("Dispatched Trains");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDispatchedTrains(evt);
            }
        });
        viewMenu.add(jMenuItem5);

        jMenuItem6.setText("Failures");
        viewMenu.add(jMenuItem6);

        jMenuItem7.setText("Selected Train Detail");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        viewMenu.add(jMenuItem7);

        menuBar.add(viewMenu);

        editMenu.setText("Edit");

        jMenuItem1.setText("Edit Kp/Ki");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem1);

        jMenuItem2.setText("Open Train Controller");
        editMenu.add(jMenuItem2);

        menuBar.add(editMenu);

        helpMenu.setText("Help");

        jMenuItem3.setText("Open User Manual");
        helpMenu.add(jMenuItem3);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Tooltips");
        helpMenu.add(jCheckBoxMenuItem1);

        jMenuItem4.setText("About");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem4);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(utilitiesTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(utilityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uiSeparatorFive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(errorLogsLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(clearErrorLog, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(errorLogScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(operatingLogsLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(clearOperatingLog, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(chooseAnnouncementLabel)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(announcementsLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(clearAnnouncements, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(annoucementDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(announcementScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(operatingLogsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                    .addComponent(makeAnnouncementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(notificationsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(uiSeparatorSix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(blockInfoTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(blockInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(speedControllerTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                        .addComponent(speedController, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(normalModeRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(automaticModeRadioButton))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(manualModeRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(testingModeRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(vitals, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(setKpAndKi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(switchTrains, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                            .addComponent(dispatchedTrains, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(uiSeparatorOne))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(74, 74, 74)
                                                .addComponent(modeSelectionTitle))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addComponent(trainSelectionTitle)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(uiSeparatorTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(92, 92, 92)
                                        .addComponent(trainInfoTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(trainInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(timeLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(182, 182, 182)
                                                .addComponent(dateLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addComponent(uiSeparatorFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addComponent(brakesTitle))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(brakePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(uiSeparatorThree, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trainSelectionTitle)
                        .addGap(18, 18, 18)
                        .addComponent(dispatchedTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vitals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(setKpAndKi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(switchTrains, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uiSeparatorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modeSelectionTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(automaticModeRadioButton)
                            .addComponent(manualModeRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(normalModeRadioButton)
                            .addComponent(testingModeRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(trainInfoTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timeLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(dateLabel)
                                        .addComponent(date)
                                        .addComponent(time)))
                                .addGap(6, 6, 6)
                                .addComponent(trainInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(uiSeparatorTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uiSeparatorFour, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(brakesTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(brakePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(uiSeparatorThree, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(utilitiesTitle)
                        .addGap(18, 18, 18)
                        .addComponent(utilityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(uiSeparatorFive)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(notificationsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(errorLogsLabel)
                            .addComponent(operatingLogsLabel)
                            .addComponent(clearOperatingLog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearErrorLog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(operatingLogsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(announcementsLabel)
                                    .addComponent(clearAnnouncements, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(announcementScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseAnnouncementLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(annoucementDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(makeAnnouncementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(errorLogScrollPane)))
                    .addComponent(uiSeparatorSix)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(blockInfoTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(blockInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(speedControllerTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(speedController, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void annoucementDropDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annoucementDropDownActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_annoucementDropDownActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed


    /**
     * The action that is performed when the "Switch" button is pressed. This method
     * switches the Train Controller's selected train to that of the train that is
     * picked from the dispatched train drop down menu.
     *
     * @param evt the sender of the action, i.e., the "Switch" button.
     */
    private void switchTrains(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchTrains

        // we dont want to do anything if "No Train Selected" is picked...
        System.out.println(this.dispatchedTrains.getSelectedIndex());
        if (this.dispatchedTrains.getSelectedIndex() != 0){

            // get the train id that is selected
            String trainId = (String) this.dispatchedTrains.getSelectedItem();

            // get the train from the hashMap
            this.selectedTrain = this.trainList.get(trainId);

            if (this.selectedTrain.powerConstantsSet() == false && this.NoTrainSelected() == false){
                System.out.println("Opening Engineering Panel");
                // open up the engineering panel
                TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain);
                engPanel.setVisible(true);
                engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }


            this.setTrains(this.selectedTrain);

            //this.refreshComponents(); // populate the other components with train info
        }else{ System.out.println((String) this.dispatchedTrains.getSelectedItem()); }

    }//GEN-LAST:event_switchTrains

    public void setTrains(Train train){

        this.speedController.setTrain(train);
        this.utilityPanel.setSelectedTrain(train);
        this.trainInfoPanel.setSelectedTrain(train);
        this.brakePanel.setSelectedTrain(train);
    }

    /**
     * Opens up the Engineering Panel so the engineer can change the Kp and Ki
     * manually.
     *
     * @param evt the sender of the event, i.e., the "Set Kp/Ki" Button.
     */
    private void setKpAndKi(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setKpAndKi

        if (this.NoTrainSelected() == false){

            // open up the engineering panel
            TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain);
            engPanel.setVisible(true);
            engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_setKpAndKi

    /**
     * Opens up the Failures Panel so that the train's vitals can be viewed.
     * These failures consist of Power, Antenna, and Brake.
     *
     * @param evt the sender of the action, i.e., the "View Vitals" button.
     */
    private void viewVitals(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewVitals

        if (this.NoTrainSelected()){
            System.out.println("No Train Selected");
        }else if (this.NoTrainSelected() == false){

            // open up a vitals window to monitor vitals
            TCFailures vitalPanel = new TCFailures(this.selectedTrain);
            vitalPanel.setVisible(true);
            vitalPanel.setErrorLogs(this.errorLogs);
            vitalPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_viewVitals

    /**
     * Clears the text of the Operating Logs.
     *
     * @param evt the sender of the action, i.e., the "Clear" button.
     */
    private void clearOperatingLogs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOperatingLogs
        this.operatingLogs.setText("");
    }//GEN-LAST:event_clearOperatingLogs

    /**
     * Clears the text of the Error Logs.
     *
     * @param evt the sender of the action, i.e., the "Clear" button.
     */
    private void clearErrorLogs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearErrorLogs
        this.errorLogs.setText("");
    }//GEN-LAST:event_clearErrorLogs

    /**
     * Clears the text of the Announcement Logs.
     *
     * @param evt the sender of the action, i.e., the "Clear" button.
     */
    private void clearAnnouncements(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAnnouncements
        this.annoucementLogs.setText("");
    }//GEN-LAST:event_clearAnnouncements

    /**
     * Opens a window that displays the list of dispatched trains.
     * This allows the user to open multiple Train Controllers for selected trains.
     *
     * @param evt the sender of the action, i.e., the "Dispatched Trains" button from the menu bar.
     */
    private void openDispatchedTrains(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDispatchedTrains
        TCDispatchedTrainFrame dispatched = new TCDispatchedTrainFrame(this.trainList);
        dispatched.setVisible(true);
        dispatched.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_openDispatchedTrains

    /**
     * Prints the announcement that is selected by the Announcement Combo Box to the Announcement Logs.
     *
     * @param evt the sender of the action, i.e., the "Make Announcement" button.
     */
    private void makeAnnouncement(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeAnnouncement

        String time = this.getTime();
        String dropDownText = (String) this.annoucementDropDown.getSelectedItem();
        this.annoucementLogs.setEditable(true);
        this.annoucementLogs.setText(this.annoucementLogs.getText() + dropDownText + " - " + time + "\n");
        this.annoucementLogs.setEditable(false);
    }//GEN-LAST:event_makeAnnouncement

    /**
     * Opens up the Test Console when the Testing mode radio button is clicked.
     *
     * @param evt the sender of the action, i.e., the "Testing" radio button.
     */
    private void testModeSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testModeSelected

        if (this.NoTrainSelected() == false){

            TCTestConsole testConsole = new TCTestConsole(this.selectedTrain, this);
            testConsole.setVisible(true);
            testConsole.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.testingMode = true;
            this.normalMode = false;
            System.out.println("Normal Mode: " + this.normalMode + " Testing Mode: " + this.testingMode);
        }else{

            TCTestConsole testConsole = new TCTestConsole(this);
            testConsole.setVisible(true);
            testConsole.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

    }//GEN-LAST:event_testModeSelected

    private void normalModeSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalModeSelected

        this.normalMode = true;
        this.testingMode = false;

        System.out.println("Normal Mode: " + this.normalMode + " Testing Mode: " + this.testingMode);
    }//GEN-LAST:event_normalModeSelected

    private void switchToAutoMode(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchToAutoMode
        // switch modes
        this.manualMode = false;
        this.automaticMode = true;
    }//GEN-LAST:event_switchToAutoMode

    private void switchToManualMode(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchToManualMode
        // switch mode
        this.manualMode = true;
        this.automaticMode = false;
    }//GEN-LAST:event_switchToManualMode

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:

        // open up the train GUI
        this.trainUI = new TrainModeUI();

        trainUI.updateGUI(this.selectedTrain);

        trainUI.frmTrainModel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        trainUI.frmTrainModel.setVisible(true);

        this.detailedTrainWindowOpen = true;
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    /**
     * Returns the current time of the system in "HH:mm:ss a" format.
     * HH - the hours
     * mm - the minutes
     * ss - the seconds
     * a - AM or PM
     *
     * @return the current system time.
     */
    private String getTime(){

        DateFormat sdf;
        Calendar cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss a");

        // get time
        return sdf.format(cal.getTime());
    }

    /**
     * Returns the current time of the system in "MM/dd/yyyy" format.
     * MM - month
     * dd - day
     * yyyy - year
     *
     * @return the current date of the system.
     */
    private String getDate(){

        LocalDate localDate = LocalDate.now();

        return DateTimeFormatter.ofPattern("MM/dd/yyyy").format(localDate);
    }

    /**
     * Checks if there is a selected train or not.
     *
     * @return returns true if no train is selected, false if a train is selected.
     */
    private boolean NoTrainSelected(){

        if (this.selectedTrain == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Populates the other components of the Train Controller with the information
     * from the selected train. This functions calls the other component's refreshUI()
     * functions.
     *
     * FIXME:
     * For now, it only updates the train's speed and power in the TrainInfoPanel.
     * Flesh this out more!
     */
    public void refreshComponents(){

        this.updateTime();

        //this.initHashMaps();

        if (this.NoTrainSelected() == false){

            // assign other componets the selected train

            this.speedController.setTrain(this.selectedTrain);

            this.trainInfoPanel.setSelectedTrain(this.selectedTrain);
            this.trainInfoPanel.refreshUI();
//            // FIX ME: TrainInfoPanelStuff should be put in the refreshUI method in the
//            // TrainInfoPanelClass
//
            // set the train info panels speed..
            this.trainInfoPanel.refreshUI();

            // get the block speed from the train
            // FIX ME: Right now, it's set at 80.0 for the purpose
            // of getting the block speed to update
            this.speedController.setMaxSpeed(this.blockSpeed);

            this.blockInfoPane.setSelectedTrain(this.selectedTrain);
            this.blockInfoPane.refreshUI();
            //this.blockInfoPane.setBlockSpeed(this.blockSpeed);

            this.utilityPanel.setManualMode(this.manualMode);
            this.utilityPanel.setSelectedTrain(this.selectedTrain);
            this.utilityPanel.refreshUI();

            // disable buttons if in automatic mode..
            if (this.automaticMode == true){

                this.brakePanel.getServiceBrake().setEnabled(false);
                this.makeAnnouncementButton.setEnabled(false);
                this.annoucementDropDown.setEnabled(false);
            }else if (this.automaticMode == false){
                //this.sBrake.setEnabled(true);
                this.brakePanel.getServiceBrake().setEnabled(true);
                this.makeAnnouncementButton.setEnabled(true);
                this.annoucementDropDown.setEnabled(true);
            }

            this.speedController.setManualMode(this.manualMode);
            this.speedController.refreshUI();


            this.brakePanel.setSelectedTrain(this.selectedTrain);
            this.brakePanel.setOperatingLogs(this.operatingLogs);
            this.brakePanel.inManualMode(this.manualMode);


            this.speedController.setBrakePanel(this.brakePanel);
        }
    }

    /**
     * Updates the time and date label of the Train Controller. This method is called
     * every second via the Timer object, t.
     */
    private void updateTime(){

        // update time and date label
        this.time.setText(this.getTime());
        this.date.setText(this.getDate());
    }

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
            java.util.logging.Logger.getLogger(TrainController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrainController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrainController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrainController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrainController().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> annoucementDropDown;
    private javax.swing.JTextArea annoucementLogs;
    private javax.swing.JScrollPane announcementScrollPane;
    private javax.swing.JLabel announcementsLabel;
    private javax.swing.ButtonGroup auto_manGroup;
    private javax.swing.JRadioButton automaticModeRadioButton;
    private TrainControllerComps.TCBlockInfoPanel blockInfoPane;
    private javax.swing.JLabel blockInfoTitle;
    private TrainControllerComps.TCBrakePanel brakePanel;
    private javax.swing.JLabel brakesTitle;
    private javax.swing.JLabel chooseAnnouncementLabel;
    private javax.swing.JButton clearAnnouncements;
    private javax.swing.JButton clearErrorLog;
    private javax.swing.JButton clearOperatingLog;
    private javax.swing.JLabel date;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JComboBox<String> dispatchedTrains;
    private javax.swing.JMenu editMenu;
    private javax.swing.JScrollPane errorLogScrollPane;
    private javax.swing.JTextPane errorLogs;
    private javax.swing.JLabel errorLogsLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JButton makeAnnouncementButton;
    private javax.swing.JRadioButton manualModeRadioButton;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel modeSelectionTitle;
    private javax.swing.JRadioButton normalModeRadioButton;
    private javax.swing.ButtonGroup normal_testGroup;
    private javax.swing.JLabel notificationsTitle;
    private javax.swing.JTextArea operatingLogs;
    private javax.swing.JLabel operatingLogsLabel;
    private javax.swing.JScrollPane operatingLogsScrollPane;
    private javax.swing.JButton setKpAndKi;
    private TrainControllerComps.TCSpeedController speedController;
    private javax.swing.JLabel speedControllerTitle;
    private javax.swing.JButton switchTrains;
    private javax.swing.JRadioButton testingModeRadioButton;
    private javax.swing.JLabel time;
    private javax.swing.JLabel timeLabel;
    private TrainControllerComps.TCTrainInfoPane trainInfoPanel;
    private javax.swing.JLabel trainInfoTitle;
    private javax.swing.JLabel trainSelectionTitle;
    private javax.swing.JSeparator uiSeparatorFive;
    private javax.swing.JSeparator uiSeparatorFour;
    private javax.swing.JSeparator uiSeparatorOne;
    private javax.swing.JSeparator uiSeparatorSix;
    private javax.swing.JSeparator uiSeparatorThree;
    private javax.swing.JSeparator uiSeparatorTwo;
    private javax.swing.JLabel utilitiesTitle;
    private TrainControllerComps.TCUtilityPanel utilityPanel;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JButton vitals;
    // End of variables declaration//GEN-END:variables

}
