package TrainControllerComps;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class TCSpeedController extends javax.swing.JPanel {
    
    // MARK: - Variables/Properties
    
    /**
     * The train that is being controlled. This is sent to the Speed Controller 
     * by the Train Controller. 
     */
    private TestTrain selectedTrain; 
    
    /**
     * The area where the logs are printed to.
     */
    private JTextArea operatingLogs; 
    
    /**
     * This is the power command sent from the Train Controller to the train.
     * Based on this value, the train either speeds up, brakes, or does nothing.
     */
    private double powerCommand_Out;
    
    /**
     * Max speed the train is allowed to go. The source of this value is determined based on 
     * what mode the system is in. This value is used to set the slider's 
     * max value so that the train cannot go faster than allowed. 
     * 
     */
    private double maxSpeed;
        
    /**
     * A list that is used to hold logs to print to the Operating Logs of the Train Controller. 
     */
    private LinkedList<String> logBook; // log book used to save logs and then print them to the notification windows
    
    /**
     * The speed that the train is desired to go. This is set by the user or
     * automatically depending on what mode the system is in. 
     */
    private int setSpeed;  
    
    /**
     * Used to keep track of how long it takes the train to reach the set speed.
     */
    private int timeElapsed;
    
    /**
     * Timer used to perform the power law calculations with the train every second. 
     */
    private Timer beginPowerControl = new Timer(1000, new ActionListener(){
        Random rand = new Random(); 
        public void actionPerformed(ActionEvent e) {
             
            // if there is a selected train and that train's speed doesnt match the set speed
            if (selectedTrain != null && systemStable() == false){
                timeElapsed++;
                String log;
                // get the error 
                double error = setSpeed - selectedTrain.speed; 
            
                // FIX ME: Kp and Ki must be some value, and not null
                // If user opens the window, and wants to enter 0, 0. if they are null they are set to 
                // 0,0.
                powerCommand_Out = selectedTrain.kp * error + selectedTrain.ki;
                System.out.println(powerCommand_Out);   
                if (powerCommand_Out < 0){
                    
                    logBook.add("Engage the service brake");             
                    // slow the train down by 1 MPH
                    // FOR TESTING PURPOSES, THIS IS 1 MPH
                    selectedTrain.speed--; 
                    
                }else if (powerCommand_Out > 0){
                    logBook.add("Full steam ahead!");
                    // speed up train by 1 MPH 
                    // FOR TESTING PURPOSES, THIS IS 1 MPH
                    selectedTrain.speed++; 
                }else if (powerCommand_Out == 0){ logBook.add("No more power."); }

                logBook.add(Integer.toString(timeElapsed));
                printLogs();
             } 
        }
    });
    
    // MARK: - Constructors
    
    /**
     * Creates new form TCSpeedController
     */
    public TCSpeedController() {
                       
        initComponents();
        
        this.powerCommand_Out = 0.0; 
        this.maxSpeed = 0.0;         
        this.logBook = new LinkedList<String>(); 
         
        // add action listensers
        this.speedSlider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
             
        String sliderValue = Integer.toString(speedSlider.getValue());
        //System.out.println("Speed: " + sliderValue);
         
        setSpeed_Label.setText(sliderValue);
        
        }
       
    });
    }
    
    public void setOperatingLogs(JTextArea opLogs){
    
        this.operatingLogs = opLogs; 
    }
    
    /**
     * Gives the Speed Controller the train whose speed to it has to change. 
     * This value is given from the Train Controller class.
     * 
     * @param train the train that is being controlled in the Train Controller class.
     */
    public void setTrain(TestTrain train){

        this.selectedTrain = train;         
    }
    
    /**
     * Sets the max speed the train is allowed to go, and then updates the UI 
     * so that the slider's max value is that of the maxSpeed and the correct speed 
     * is on the label.
     * 
     * @param maxSpeed the max speed the train is allowed to go. 
     */
    public void setMaxSpeed(double maxSpeed){
    
        this.maxSpeed = maxSpeed;         
       
        // update ui
        this.maxSpeed_Slider.setText(Double.toString(this.maxSpeed));
        this.speedSlider.setMaximum((int) this.maxSpeed);   
    }
    
    /**
     * Refreshes all the UI components in the SpeedController. 
     * 
     * FIX ME: This should be called from the TrainController every 'x' seconds to update 
     * the components with up-to-date information. 
     */
    public void refreshUI(){
       
        this.maxSpeed_Slider.setText(Double.toString(this.maxSpeed));
        this.speedSlider.setMaximum((int) this.maxSpeed);
    }
    
    public int getSetSpeed(){
        
        return this.speedSlider.getValue();
    }
    
    /**
     * Updates the speed label with that of the slider value. This allows the user to 
     * see what value the slider is on. Note,
     */
    public void setSpeedLabel(){
    
        this.setSpeed_Label.setText(Integer.toString(this.speedSlider.getValue()));
    }
    
    /**
     * Sets the max value of the speed controller slider. Note, this strictly only 
     * changes the slider based on the given parameter, and has nothing to do with the selected train.
     * 
     * @param max the value to change the speed controller's slider to.
     */
    public void setSliderMax(int max){
    
        this.speedSlider.setMaximum(max);
        this.maxSpeed_Slider.setText(Integer.toString(this.speedSlider.getMaximum()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        speedSlider = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        setSpeed_Label = new javax.swing.JLabel();
        minSpeed_Slider = new javax.swing.JLabel();
        maxSpeed_Slider = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        setSpeedButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        speedSlider.setPaintTicks(true);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Set Speed:");

        setSpeed_Label.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        setSpeed_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setSpeed_Label.setText("0");

        minSpeed_Slider.setText("0");

        maxSpeed_Slider.setText("100");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MPH");

        jLabel7.setText("MPH");

        jLabel2.setText("MPH");

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
                                .addComponent(maxSpeed_Slider)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(setSpeed_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minSpeed_Slider, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setSpeed_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(maxSpeed_Slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(minSpeed_Slider, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
     * @param evt the "Set Speed" button that triggers this event. 
     */
    private void setSpeed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSpeed
           
        String log;
        this.setSpeed = speedSlider.getValue();
        this.beginPowerControl.start();
        
        log = "Telling train to set speed to " + setSpeed;
        logBook.add(log);
        this.printLogs();
    }//GEN-LAST:event_setSpeed

    /**
     * Determines if the the train's speed is equal to the speed the user set. 
     * 
     * @return returns true if the train's speed is equal to the set speed, false otherwise. 
     */
    private boolean systemStable(){
    
        // if train's speed is equal to the set speed, we can stop power control 
        if (this.selectedTrain.speed == this.setSpeed ){ 
            this.beginPowerControl.stop();
            System.out.println("The train took " + this.timeElapsed + " s to get to " + this.setSpeed);
            this.timeElapsed = 0; 
            return true;
        }
        else {return false; }
    }
    
    /**
     * Prints the stored logs to the Operating Log text pane, then clears the logbook. 
     * The JTextPane must be set from the TrainController class.
     */
    public void printLogs(){
    
        
        if (this.operatingLogs != null){
            for (String log : this.logBook){
        
                this.operatingLogs.setText(this.operatingLogs.getText() + log + "\n");
            }
        }else{
            
        }
        
        this.logBook.clear();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel maxSpeed_Slider;
    private javax.swing.JLabel minSpeed_Slider;
    private javax.swing.JButton setSpeedButton;
    private javax.swing.JLabel setSpeed_Label;
    private javax.swing.JSlider speedSlider;
    // End of variables declaration//GEN-END:variables
}
