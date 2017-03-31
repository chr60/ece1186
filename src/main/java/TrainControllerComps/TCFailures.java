package TrainControllerComps;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import TrainModel.*; 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is responsible for display the states of the 3 failures that can occur on the train, as well as 
 * sending a signal to the CTC to repair the broken systems. 
 * The three failures are: antenna, power, and brake.  
 * 
 * This class collaborates with the Train Controller and Train class.
 * 
 * @author Andrew Lendacky
 */
public class TCFailures extends javax.swing.JFrame {

    /**
     * The train in which to display the failures from. 
     */
    private Train selectedTrain;
    
    /**
     * A list to store up messages to print to the error log. 
     */
    private LinkedList<String> logbook; 
    
    /**
     * Area to print the error logs to. 
     * This comes from the errorLogs from the Train Controller class. 
     */
    private JTextPane errorLogs; 
    
    /**
     * Constructor for creating a TCFailure object with no selected train, logbook, or error log. 
     * These must be passed in from the Train Controller before being used. 
     * 
     */
    public TCFailures() {
        initComponents();
        // set up logbook
        this.logbook = new LinkedList<String>(); 
    }
    
    /**
     * Constructor for creating a TCFailures object with a given train. 
     * 
     * @param train the selected train from the Train Controller 
     */
    public TCFailures(Train train) {
        
        initComponents();
        this.logbook = new LinkedList(); 
        this.selectedTrain = train;
        this.refreshUI();  
    }
    
    /**
     * @Bug This method is not called from anywhere else, so the window does not 
     * refresh itself when a train has been fixed. Therefore, it will continue to 
     * show the old states of the failures until the user closes the window and 
     * reopens it. 
     * 
     * 
     * 
     */
    
    /**
     * Refreshes the UI to update the radio buttons based on the status of the train.
     * 
     */
    private void refreshUI(){
    
        this.trainId.setText(Integer.toString( this.selectedTrain.getID()) );
        
        // update the radio buttons based on the train    
        if (this.isPowerFailure()){ this.powerFailureRadioButton.setSelected(true); }
        else{ this.powerWorkingRadioButton.setSelected(true); }

              
        //FIX ME: For, testing, we will assume they begin working.
        this.antennaWorkingRadioButton.setSelected(true);
        this.brakesWorkingRadioButton.setSelected(true);     
    }
        
    /**
     * Checks if at least one of the utilities on the train is in a failure state, i.e., a power failure. 
     * 
     * @return returns true if there is at least one utility broken, false if all the utilities are working.
     */
    private boolean isPowerFailure(){
    
        // checks for a power failure
        if (this.selectedTrain.getAC() == -1 || this.selectedTrain.getHeat() == -1 
                || this.selectedTrain.getLights() == -1 || this.selectedTrain.getLeftDoor() == -1 
                || this.selectedTrain.getRightDoor() == -1){ return true; }
        else{ return false; }
    }
    
    /**
     * Sets the error log that is to be printed to
     * 
     * @param errLogs a textpane representing the error log
     */
    public void setErrorLogs(JTextPane errLog){
    
        this.errorLogs = errLog;
    }
    
    /**
     * Prints the stored up logs from the logbook to the error log, and then clears the logbook. 
     * 
     */
    private void printLogBook(){
    
        this.errorLogs.setEditable(true);
        
        if (this.logbook.isEmpty() == false){
            for (String log : this.logbook){
        
                // print the log to the error logs
                appendToPane(this.errorLogs, log + "\n", Color.RED); 
            }
        }
        
        this.logbook.clear();
        this.errorLogs.setEditable(false);
    }
    
    /**
     * Add to a given log a given message in a given color.  
     * 
     * @param tp the text pane to display the message in.
     * @param msg the message to display.
     * @param c the color to display the message in. 
     */
    private void appendToPane(JTextPane tp, String msg, Color c){
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        antennaGroup = new javax.swing.ButtonGroup();
        powerGroup = new javax.swing.ButtonGroup();
        brakeGroup = new javax.swing.ButtonGroup();
        antennaLabel = new javax.swing.JLabel();
        powerLabel = new javax.swing.JLabel();
        brakesLabel = new javax.swing.JLabel();
        antennaWorkingRadioButton = new javax.swing.JRadioButton();
        antennaFailureRadioButton = new javax.swing.JRadioButton();
        powerFailureRadioButton = new javax.swing.JRadioButton();
        powerWorkingRadioButton = new javax.swing.JRadioButton();
        brakesFailureRadioButton = new javax.swing.JRadioButton();
        brakesWorkingRadioButton = new javax.swing.JRadioButton();
        titleLabel = new javax.swing.JLabel();
        uiSeparatorOne = new javax.swing.JSeparator();
        requestFixButton = new javax.swing.JButton();
        trainIdLabel = new javax.swing.JLabel();
        trainId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        antennaLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        antennaLabel.setText("Antenna:");

        powerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        powerLabel.setText("Power:");

        brakesLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        brakesLabel.setText("Brakes:");

        antennaGroup.add(antennaWorkingRadioButton);
        antennaWorkingRadioButton.setText("Working");

        antennaGroup.add(antennaFailureRadioButton);
        antennaFailureRadioButton.setText("Failure");

        powerGroup.add(powerFailureRadioButton);
        powerFailureRadioButton.setText("Failure");

        powerGroup.add(powerWorkingRadioButton);
        powerWorkingRadioButton.setText("Working");

        brakeGroup.add(brakesFailureRadioButton);
        brakesFailureRadioButton.setText("Failure");

        brakeGroup.add(brakesWorkingRadioButton);
        brakesWorkingRadioButton.setText("Working");

        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Communications and Vitals");

        requestFixButton.setText("Request Fix");
        requestFixButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestFixButton(evt);
            }
        });

        trainIdLabel.setText("Train ID:");

        trainId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        trainId.setText("(train ID)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(antennaLabel)
                    .addComponent(powerLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(brakesLabel)))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(powerWorkingRadioButton)
                    .addComponent(brakesWorkingRadioButton)
                    .addComponent(antennaWorkingRadioButton))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(antennaFailureRadioButton)
                    .addComponent(powerFailureRadioButton)
                    .addComponent(brakesFailureRadioButton))
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(requestFixButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(uiSeparatorOne, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(trainIdLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trainId, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainIdLabel)
                    .addComponent(trainId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(uiSeparatorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antennaLabel)
                    .addComponent(antennaWorkingRadioButton)
                    .addComponent(antennaFailureRadioButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(powerLabel)
                    .addComponent(powerWorkingRadioButton)
                    .addComponent(powerFailureRadioButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(brakesLabel)
                    .addComponent(brakesWorkingRadioButton)
                    .addComponent(brakesFailureRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requestFixButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Requests a fix to the CTC if there is an Antenna, Power, or Brake Failure, and 
     * records it to the error log. 
     * 
     * @param evt the sender of the event, i.e., the 'Request Fix' button.
     */
    private void requestFixButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestFixButton
        
        // reset all the problem back to normal 
        String log; 
        String time = this.getTime();
        
        if (this.antennaFailureRadioButton.isSelected()){
        
            log = "**Request to fix Antenna system - " + time;
            this.logbook.add(log);
           
            this.antennaWorkingRadioButton.setSelected(true);
        }
        
        if (this.powerFailureRadioButton.isSelected()){
            log = "**Request to fix Power system - " + time;
            this.logbook.add(log);
            
            // send request to ctc to request fix
            
            // For testing, it will be automatically fixed
            if (this.selectedTrain.getAC() == -1){ this.selectedTrain.setAC( 0 ); }
            if (this.selectedTrain.getHeat() == -1){ this.selectedTrain.setHeat( 0 ); }
            if (this.selectedTrain.getLights() == -1){ this.selectedTrain.setLights( 0 ); }
            if (this.selectedTrain.getLeftDoor() == -1){ this.selectedTrain.setLeftDoor( 0 ); }
            if (this.selectedTrain.getRightDoor() == -1){ this.selectedTrain.setRightDoor( 0 ); }

            this.powerWorkingRadioButton.setSelected(true);
        }
        
        if(this.brakesFailureRadioButton.isSelected()){
            log = "**Request to fix Brake system - " + time;
            this.logbook.add(log);
            this.brakesWorkingRadioButton.setSelected(true);
        }
          
        this.printLogBook();
        this.logbook.clear();
    }//GEN-LAST:event_requestFixButton

    /**
     * Gets the time from the Operating System as a String.
     * 
     * @return returns the time of the system. 
     */
    private String getTime(){
    
        DateFormat dateandtime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");        
        LocalDate localDate = LocalDate.now();
        Calendar cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss a");
              
        // get time
        return sdf.format(cal.getTime());
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TCFailures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TCFailures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TCFailures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TCFailures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TCFailures().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton antennaFailureRadioButton;
    private javax.swing.ButtonGroup antennaGroup;
    private javax.swing.JLabel antennaLabel;
    private javax.swing.JRadioButton antennaWorkingRadioButton;
    private javax.swing.ButtonGroup brakeGroup;
    private javax.swing.JRadioButton brakesFailureRadioButton;
    private javax.swing.JLabel brakesLabel;
    private javax.swing.JRadioButton brakesWorkingRadioButton;
    private javax.swing.JRadioButton powerFailureRadioButton;
    private javax.swing.ButtonGroup powerGroup;
    private javax.swing.JLabel powerLabel;
    private javax.swing.JRadioButton powerWorkingRadioButton;
    public javax.swing.JButton requestFixButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel trainId;
    private javax.swing.JLabel trainIdLabel;
    private javax.swing.JSeparator uiSeparatorOne;
    // End of variables declaration//GEN-END:variables
}
