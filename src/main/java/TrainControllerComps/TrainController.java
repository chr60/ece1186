package TrainControllerComps;

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
    private HashMap<String, TestTrain> trainList = new HashMap<String, TestTrain>(); 
        
    // Train Stuff: 
    private TestTrain selectedTrain; // the train that the Train Controller is controlling. 
  
    // Modes: 
    private boolean manualMode; // used to tell if the Train Controller is in Manual mode
    private boolean automaticMode; // used to tell if the Train Controller is in Automatic mode
    
    private boolean normalMode; // used to tell if the Train Controller is in Manual mode
    private boolean testingMode; // used to tell if the Train Controller is in Automatic mode
     
    // FOR TESTING!
    
    LinkedList<TestTrain> trains = TestTrain.generateRandomTestTrain(10);
    double blockSpeed = 80.0; 
    private TCTestConsole testConsole = null; 
    
    
    // used to update GUI every millisecond (1 s)
    // FIX ME: This time should be set by the CTC can be 
    // wall speed or 10x faster ()
    private Timer systemRunSpeed = new Timer(1000, new ActionListener(){
        Random rand = new Random(); 
        public void actionPerformed(ActionEvent e) {
            //blockSpeed = (int)(rand.nextDouble() * 100.0) % 100.0;
            refreshComponents();    
            
            if (testingMode == true){
            
                System.out.println("In Testing Mode"); 
                
                if (testConsole != null){
                
                    // update test console 
                    testConsole.setTrain(selectedTrain);
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
        
        this.initHashMaps();
        this.setTrainList_ComboBox();
        this.setMode("Manual", "Normal");
        this.speedController.setOperatingLogs(this.operatingLogs);
        this.selectedTrain = null;  
                        
        systemRunSpeed.start();
    }
    
        /**
     * Constructor that creates a Train Controller for a give train in Manual and Normal mode. 
     * 
     * @param mode the mode the Train Controller will start in. Mode should be either 'Manual' or 'Automatic'
     */
    public TrainController(TestTrain train){
        
        initComponents();
       
        this.initHashMaps();
        this.setTrainList_ComboBox();
        this.setMode("Manual", "Normal");
         
        this.selectedTrain = train;   
        this.dispatchedTrains.setSelectedItem(this.selectedTrain.id);
           
        // check if kp/ki is set
        
        if (this.selectedTrain.kp == null & this.selectedTrain.ki == null){
        
            TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain);
            engPanel.setVisible(true);
            engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        
        systemRunSpeed.start();
    }
    
    /**
     * Constructor that creates a Train Controller in a given mode. 
     * 
     * @param mode the mode the Train Controller will start in. Mode should be either 'Manual' or 'Automatic'
     */
    public TrainController(String playMode, String testMode){
        
        initComponents();
       
        this.initHashMaps();
        this.setTrainList_ComboBox();
        this.setMode(playMode, testMode);
         
        this.selectedTrain = null; 
          
        systemRunSpeed.start();
    }
    
    public TCTrainInfoPane getTrainInfoPane(){
    
        return this.trainInfoPanel;
    }
    
    public TCSpeedController getSpeedController(){
        
        return this.speedController;
    }
            
    // MARK: - Mode Setting and Getting
    
    private void setMode(String playMode, String testMode){
        
        if (playMode.equals("Automatic")){
        
            this.automaticMode = true; 
            this.manualMode = false; 
            this.automaticMode_RB.setSelected(true);
            
            
        }else if (playMode.equals("Manual")){
            
            this.manualMode = true; 
            this.automaticMode = false; 
            this.manualMode_RB.setSelected(true);
            
        }else{
            System.out.println("No Real Mode Picked. Must be 'Automatic' or 'Manual' ");
        }
        
        
        if (testMode.equals("Normal")){
        
            this.normalMode = true; 
            this.testingMode = false; 
            this.normalMode_RB.setSelected(true);
            
        }else if (testMode.equals("Testing")){
            
            this.testingMode = true; 
            this.normalMode = false; 
            this.testingMode_RB.setSelected(true);
            
        }else{
            System.out.println("No Real Mode Picked. Must be 'Testing' or 'Normal' ");
        } 
    }
    
    public String getPlayMode(){
        
        if (this.manualMode == true){ return "Manual"; }
        else if(this.automaticMode == true){ return "Automatic"; }
        else{ return null; } // no mode was set
    }
    
    public String getTestMode(){
        
        if (this.testingMode == true){ return "Testing"; }
        else if(this.normalMode == true){ return "Normal"; }
        else{ return null; } // no mode was set
    }
    
    // MARK: - Configure Data Structures
    private void initHashMaps(){
    
        // get the list of dispatched trains
        
        // add them to the hashmaps
        
        for (TestTrain train : this.trains){
        
            this.trainList.put(train.id, train );
        }
     
    }
        
    // MARK: - Train Stuff
    
    private void setTrain(TestTrain train){
    
        this.selectedTrain = train; 
    }
    
    public TestTrain getTrain(){
        
        if (this.selectedTrain == null){
            System.out.println("No train is selected");
            return null; 
        }else{
            return this.selectedTrain; 
        }
    }
    
    // MARK: - Configure UI
    
    private void setTrainList_ComboBox(){
    
        for (TestTrain train : this.trains){
        
            this.dispatchedTrains.addItem(train.id);
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
        jLabel1 = new javax.swing.JLabel();
        sBrake = new javax.swing.JButton();
        eBrake = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dispatchedTrains = new javax.swing.JComboBox<>();
        switchTrains = new javax.swing.JButton();
        setKpAndKi = new javax.swing.JButton();
        vitals = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        automaticMode_RB = new javax.swing.JRadioButton();
        manualMode_RB = new javax.swing.JRadioButton();
        normalMode_RB = new javax.swing.JRadioButton();
        testingMode_RB = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        annoucementLogs = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        operatingLogs = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        annoucementDropDown = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        date = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        speedController = new TrainControllerComps.TCSpeedController();
        tCUtilityPanel1 = new TrainControllerComps.TCUtilityPanel();
        blockInfoPane = new TrainControllerComps.TCBlockInfoPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        errorLogs = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        trainInfoPanel = new TrainControllerComps.TCTrainInfoPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Train Info");

        sBrake.setText("Service Brake");

        eBrake.setText("Emergency Brake");
        eBrake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                engageEmgBrake(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel2.setText("Train Selection");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel3.setText("Brakes");

        jLabel4.setText("Operation:");

        jLabel5.setText("Operation:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Functioning");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Functioning");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setText("Time:");

        jLabel9.setText("Date:");

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

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel10.setText("Mode Selection");

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        auto_manGroup.add(automaticMode_RB);
        automaticMode_RB.setText("Automatic");

        auto_manGroup.add(manualMode_RB);
        manualMode_RB.setText("Manual");

        normal_testGroup.add(normalMode_RB);
        normalMode_RB.setText("Normal");

        normal_testGroup.add(testingMode_RB);
        testingMode_RB.setText("Testing");
        testingMode_RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testModeSelected(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel11.setText("Block Info");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Notifications");

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel13.setText("Utilities");

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Speed Controller");

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        annoucementLogs.setColumns(20);
        annoucementLogs.setRows(5);
        jScrollPane2.setViewportView(annoucementLogs);

        operatingLogs.setColumns(20);
        operatingLogs.setRows(5);
        jScrollPane3.setViewportView(operatingLogs);

        jLabel15.setText("Error Logs:");

        jLabel16.setText("Operating Logs:");

        jLabel17.setText("Announcements:");

        annoucementDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Announcement", "Announce Station", "Announce Next Stop", "Annouce Weather", "Annouce Time" }));
        annoucementDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annoucementDropDownActionPerformed(evt);
            }
        });

        jLabel18.setText("Choose Announcement");

        jButton6.setText("Make Announcement");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeAnnouncement(evt);
            }
        });

        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        errorLogs.setEditable(false);
        jScrollPane4.setViewportView(errorLogs);

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOperatingLogs(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearErrorLogs(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAnnouncements(evt);
            }
        });

        jMenu1.setText("View");

        jMenuItem5.setText("Dispatched Trains");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDispatchedTrains(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Failures");
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem1.setText("Edit Kp/Ki");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Open Train Controller");
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem3.setText("Open User Manual");
        jMenu3.add(jMenuItem3);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Tooltips");
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuItem4.setText("About");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tCUtilityPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(annoucementDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                    .addComponent(speedController, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(blockInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel11))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(normalMode_RB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(automaticMode_RB))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(manualMode_RB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(testingMode_RB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(vitals, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(setKpAndKi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(switchTrains, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                                            .addComponent(dispatchedTrains, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(74, 74, 74)
                                                .addComponent(jLabel10))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addComponent(jLabel2)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(92, 92, 92)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(trainInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(182, 182, 182)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(eBrake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(9, 9, 9))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(sBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(dispatchedTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vitals, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(setKpAndKi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(switchTrains, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(automaticMode_RB)
                            .addComponent(manualMode_RB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(normalMode_RB)
                            .addComponent(testingMode_RB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8)
                                            .addComponent(date)
                                            .addComponent(time))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sBrake, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(trainInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tCUtilityPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(annoucementDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4)))
                    .addComponent(jSeparator6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(blockInfoPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
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
        
            if (this.selectedTrain.powerConstantsSet() == false){
                System.out.println("Opening Engineering Panel");
                // open up the engineering panel
                TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain); 
                engPanel.setVisible(true); 
                engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
            
            this.refreshComponents(); // populate the other components with train info
        }else{ System.out.println((String) this.dispatchedTrains.getSelectedItem()); }
   
    }//GEN-LAST:event_switchTrains

    /**
     * Opens up the Engineering Panel so the engineer can change the Kp and Ki
     * manually. 
     * 
     * @param evt the send of the event, i.e., the "Set Kp/Ki" Button. 
     */
    private void setKpAndKi(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setKpAndKi
        
        // open up the engineering panel
        TCEngineerPanel engPanel = new TCEngineerPanel(this.selectedTrain); 
        engPanel.setVisible(true); 
        engPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_setKpAndKi

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

    private void engageEmgBrake(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_engageEmgBrake
                
        // if there is a selected train..
        if (this.NoTrainSelected() == false){
            System.out.println("Open up 'Confirm Emergency Brake Window.' ");
            // open up the window to confirm using the e-brake
            TCEmergencyFrame eBrakeWindow = new TCEmergencyFrame(this.selectedTrain); 
        
            eBrakeWindow.setVisible(true);
            eBrakeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_engageEmgBrake

    private void clearOperatingLogs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOperatingLogs
         
        this.operatingLogs.setText("");
    }//GEN-LAST:event_clearOperatingLogs

    private void clearErrorLogs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearErrorLogs
        this.errorLogs.setText("");
    }//GEN-LAST:event_clearErrorLogs

    private void clearAnnouncements(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAnnouncements
        
        this.annoucementLogs.setText("");   
    }//GEN-LAST:event_clearAnnouncements

    private void openDispatchedTrains(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDispatchedTrains
        // TODO add your handling code here:    
        TCDispatchedTrainFrame dispatched = new TCDispatchedTrainFrame(this.trainList); 
        dispatched.setVisible(true);
        dispatched.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  
    }//GEN-LAST:event_openDispatchedTrains

    private void makeAnnouncement(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeAnnouncement
        // TODO add your handling code here:
        
        String time = this.getTime(); 
        String dropDownText = (String) this.annoucementDropDown.getSelectedItem();
        this.annoucementLogs.setEditable(true);
        this.annoucementLogs.setText(this.annoucementLogs.getText() + dropDownText + " - " + time + "\n");
        this.annoucementLogs.setEditable(false);
    }//GEN-LAST:event_makeAnnouncement

    private void testModeSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testModeSelected
        // TODO add your handling code here:
        
        TCTestConsole testConsole = new TCTestConsole(this.selectedTrain, this);
        
        testConsole.setVisible(true);
        testConsole.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_testModeSelected
   
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
    private void refreshComponents(){
            
        this.updateTime();
        
        if (this.NoTrainSelected() == false){
        
            // assign other componets the selected train
            
            this.speedController.setTrain(this.selectedTrain);
            
            // set the train info panels speed.. 
            this.trainInfoPanel.setSpeedLabel(this.selectedTrain.speed); 
                
            // set the trains info panels power.. 
            this.trainInfoPanel.setPowerLabel(this.selectedTrain.power);  
            
            this.trainInfoPanel.setSuggestSpeedLabel(this.selectedTrain.currentSuggestedSpeed);
            
            // get the block speed from the train
            // FIX ME: Right now, it's set at 80.0 for the purpose 
            // of getting the block speed to update
            this.speedController.setMaxSpeed(this.selectedTrain.currentBlockSpeed);
            this.blockInfoPane.setBlockSpeed(this.selectedTrain.currentBlockSpeed);
            
        }
    }
    
    /**
     * Updates the time and date label of the Train Controller. This method is called 
     * every second via the Timer object, t. 
     * 
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
    private javax.swing.ButtonGroup auto_manGroup;
    private javax.swing.JRadioButton automaticMode_RB;
    private TrainControllerComps.TCBlockInfoPanel blockInfoPane;
    private javax.swing.JLabel date;
    private javax.swing.JComboBox<String> dispatchedTrains;
    private javax.swing.JButton eBrake;
    private javax.swing.JTextPane errorLogs;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JRadioButton manualMode_RB;
    private javax.swing.JRadioButton normalMode_RB;
    private javax.swing.ButtonGroup normal_testGroup;
    private javax.swing.JTextArea operatingLogs;
    private javax.swing.JButton sBrake;
    private javax.swing.JButton setKpAndKi;
    private TrainControllerComps.TCSpeedController speedController;
    private javax.swing.JButton switchTrains;
    private TrainControllerComps.TCUtilityPanel tCUtilityPanel1;
    private javax.swing.JRadioButton testingMode_RB;
    private javax.swing.JLabel time;
    private TrainControllerComps.TCTrainInfoPane trainInfoPanel;
    private javax.swing.JButton vitals;
    // End of variables declaration//GEN-END:variables

}
