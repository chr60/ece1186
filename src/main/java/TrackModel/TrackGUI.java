/** GUI associated with the track module.
*  \author Michael
*/
package TrackModel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import java.awt.Color;
import java.util.Set;
import java.util.Arrays;
import java.util.Properties;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.io.InputStreamReader;
import java.io.File;

public class TrackGUI {

  private JFrame frame;
  private JTextField lengthField;
  private JTextField gradeField;
  private JTextField elevationField;
  private JTextField speedField;
  private JLabel lblBlockLength;
  private JLabel lblBlockGrade;
  private JLabel lblElevation;
  private JLabel lblSpeedLimit;
  private JLabel lblLengthUnit;
  private JLabel lblGradePercent;
  private JLabel lblElevationUnit;
  private JLabel lblSpeedUnit;
  private JLabel lblOccupied;
  private JLabel lblCrossingsActive;
  private JLabel lblIsUnderground;
  private JLabel lblNextBlock;
  private JToggleButton toggleSignals;
  private JLabel lblStation;
  private JTextField stationName;
  private JTextField stationArrivalTime;
  private JLabel lblNewLabel;
  private JLabel lblBrokenRail;
  private JToggleButton toggleBroken;
  private JToggleButton toggleCircuitFailure;
  private JLabel lblCircuitFailure;
  private JLabel lblPowerFailure;
  private JLabel lblToggleUpdate;
  private JToggleButton togglePowerFailure;
  private JToggleButton toggleIsUnderground;
  private JToggleButton toggleNextBlockBackward;
  private JToggleButton toggleNextBlockForward;
  private JToggleButton toggleUpdate;
  private JLabel imageLabel;
  private final Double METERSMULT = 3.28084;
  private JLabel lblBeaconMessage;
  private JTextField textFieldBeaconMessage;
  
  /**
   * Create the application.
   */
  public TrackGUI(TrackModel track) {
    initialize(track);
  }

  /**
  * Returns the main frame for external dispaly.
  */
  public JFrame getFrame(){
    return this.frame;
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize(TrackModel track) {

    Set<Integer> blockInts = track.trackList.get("Red").get("A").keySet();
    Integer[] intArr = blockInts.toArray(new Integer[blockInts.size()]);
    Set<String> lineSet = track.trackList.keySet();
    System.out.println(lineSet);
    String[] lineStrings = lineSet.toArray(new String[lineSet.size()]);

    frame = new JFrame();
    frame.setBounds(100, 100, 863, 665);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    String fName = ("classes/images/trackPicture.jpg");
    ImageIcon image = new ImageIcon(fName);

    imageLabel = new JLabel("Track", image, JLabel.CENTER);
    imageLabel.setVerticalTextPosition(JLabel.CENTER);
    imageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
    imageLabel.setBounds(10, 11, 400, 600);
    frame.getContentPane().add(imageLabel);
    

    JComboBox dropdownLine = new JComboBox(lineStrings);
    dropdownLine.setBounds(570, 22, 86, 23);
    frame.getContentPane().add(dropdownLine);

    String[] segmentStrings = {};
    JComboBox dropdownSegment = new JComboBox(segmentStrings);
    dropdownSegment.setBounds(653, 22, 86, 23);
    frame.getContentPane().add(dropdownSegment);

    String[] blockStrings = {};
    JComboBox dropdownBlock = new JComboBox(blockStrings);
    dropdownBlock.setBounds(737, 22, 86, 23);
    frame.getContentPane().add(dropdownBlock);

    //Length
    lengthField = new JTextField();
    lengthField.setHorizontalAlignment(SwingConstants.CENTER);
    lengthField.setText("#");
    lengthField.setBounds(634, 56, 86, 23);
    frame.getContentPane().add(lengthField);
    lengthField.setColumns(10);

    //Grade
    gradeField = new JTextField();
    gradeField.setHorizontalAlignment(SwingConstants.CENTER);
    gradeField.setText("#");
    gradeField.setColumns(10);
    gradeField.setBounds(634, 79, 86, 23);
    frame.getContentPane().add(gradeField);

    //Elevation
    elevationField = new JTextField();
    elevationField.setHorizontalAlignment(SwingConstants.CENTER);
    elevationField.setText("#");
    elevationField.setColumns(10);
    elevationField.setBounds(634, 102, 86, 23);
    frame.getContentPane().add(elevationField);

    //Speed Limit
    speedField = new JTextField();
    speedField.setText("#");
    speedField.setHorizontalAlignment(SwingConstants.CENTER);
    speedField.setColumns(10);
    speedField.setBounds(634, 125, 86, 23);
    frame.getContentPane().add(speedField);

    lblBlockLength = new JLabel("Length");
    lblBlockLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblBlockLength.setHorizontalAlignment(SwingConstants.RIGHT);
    lblBlockLength.setBounds(529, 56, 79, 23);
    frame.getContentPane().add(lblBlockLength);

    lblBlockGrade = new JLabel("Grade");
    lblBlockGrade.setHorizontalAlignment(SwingConstants.RIGHT);
    lblBlockGrade.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblBlockGrade.setBounds(529, 79, 79, 23);
    frame.getContentPane().add(lblBlockGrade);

    lblElevation = new JLabel("Elevation");
    lblElevation.setHorizontalAlignment(SwingConstants.RIGHT);
    lblElevation.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblElevation.setBounds(529, 100, 79, 23);
    frame.getContentPane().add(lblElevation);

    lblSpeedLimit = new JLabel("Speed Limit");
    lblSpeedLimit.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSpeedLimit.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblSpeedLimit.setBounds(529, 123, 79, 23);
    frame.getContentPane().add(lblSpeedLimit);

    lblLengthUnit = new JLabel("f");
    lblLengthUnit.setHorizontalAlignment(SwingConstants.LEFT);
    lblLengthUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblLengthUnit.setBounds(733, 56, 38, 23);
    frame.getContentPane().add(lblLengthUnit);

    lblGradePercent = new JLabel("%");
    lblGradePercent.setHorizontalAlignment(SwingConstants.LEFT);
    lblGradePercent.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblGradePercent.setBounds(733, 81, 38, 23);
    frame.getContentPane().add(lblGradePercent);

    lblElevationUnit = new JLabel("f");
    lblElevationUnit.setHorizontalAlignment(SwingConstants.LEFT);
    lblElevationUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblElevationUnit.setBounds(733, 102, 38, 23);
    frame.getContentPane().add(lblElevationUnit);

    lblSpeedUnit = new JLabel("feet/s");
    lblSpeedUnit.setHorizontalAlignment(SwingConstants.LEFT);
    lblSpeedUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblSpeedUnit.setBounds(733, 125, 38, 23);
    frame.getContentPane().add(lblSpeedUnit);

    lblOccupied = new JLabel("Occupied");
    lblOccupied.setHorizontalAlignment(SwingConstants.LEFT);
    lblOccupied.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblOccupied.setBounds(550, 192, 79, 23);
    frame.getContentPane().add(lblOccupied);

    JToggleButton toggleOccupied = new JToggleButton("Y");
    toggleOccupied.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleOccupied.setSelected(true);
    toggleOccupied.setBounds(632, 192, 45, 23);
    frame.getContentPane().add(toggleOccupied);
    toggleOccupied.setSelected(true);

    JLabel lblHeaters = new JLabel("Heaters ON?");
    lblHeaters.setHorizontalAlignment(SwingConstants.RIGHT);
    lblHeaters.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblHeaters.setBounds(527, 211, 79, 23);
    frame.getContentPane().add(lblHeaters);

    JToggleButton toggleHeatersOn = new JToggleButton("N");
    toggleHeatersOn.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleHeatersOn.setBounds(632, 213, 45, 23);
    frame.getContentPane().add(toggleHeatersOn);

    JSeparator separator = new JSeparator();
    separator.setForeground(Color.BLACK);
    separator.setBackground(Color.BLACK);
    separator.setBounds(507, 405, 316, 2);
    frame.getContentPane().add(separator);

    JLabel lblCrossing = new JLabel("Crossings?");
    lblCrossing.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCrossing.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblCrossing.setBounds(527, 235, 79, 23);
    frame.getContentPane().add(lblCrossing);

    JToggleButton toggleCrossings = new JToggleButton("N/A");
    toggleCrossings.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleCrossings.setBounds(632, 237, 90, 23);
    frame.getContentPane().add(toggleCrossings);

    JToggleButton toggleUpdate = new JToggleButton("Update");
    toggleUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleUpdate.setBounds(483, 22, 80, 23);
    frame.getContentPane().add(toggleUpdate);

    lblCrossingsActive = new JLabel("Lights");
    lblCrossingsActive.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCrossingsActive.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblCrossingsActive.setBounds(550, 259, 56, 23);
    frame.getContentPane().add(lblCrossingsActive);

    lblNextBlock = new JLabel("Next Block: ");
    lblNextBlock.setHorizontalAlignment(SwingConstants.RIGHT);
    lblNextBlock.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblNextBlock.setBounds(507, 332, 99, 23);
    frame.getContentPane().add(lblNextBlock);

    lblIsUnderground = new JLabel("Underground");
    lblIsUnderground.setHorizontalAlignment(SwingConstants.RIGHT);
    lblIsUnderground.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblIsUnderground.setBounds(507, 352, 99, 23);
    frame.getContentPane().add(lblIsUnderground);

    toggleSignals = new JToggleButton("N/A");
    toggleSignals.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleSignals.setBounds(632, 261, 90, 23);
    frame.getContentPane().add(toggleSignals);

    toggleNextBlockBackward = new JToggleButton("NBB");
    toggleNextBlockBackward.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleNextBlockBackward.setBounds(731, 330, 65, 23);
    frame.getContentPane().add(toggleNextBlockBackward);
    
    toggleNextBlockForward = new JToggleButton("NBF");
    toggleNextBlockForward.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleNextBlockForward.setBounds(632, 330, 65, 23);
    frame.getContentPane().add(toggleNextBlockForward);

    toggleIsUnderground = new JToggleButton("N");
    toggleIsUnderground.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleIsUnderground.setBounds(632,351,45,23);
    frame.getContentPane().add(toggleIsUnderground);

    lblStation = new JLabel("Station");
    lblStation.setHorizontalAlignment(SwingConstants.RIGHT);
    lblStation.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStation.setBounds(529, 147, 79, 23);
    frame.getContentPane().add(lblStation);

    stationName = new JTextField();
    stationName.setText("(name)");
    stationName.setHorizontalAlignment(SwingConstants.CENTER);
    stationName.setColumns(10);
    stationName.setBounds(634, 149, 186, 23);
    frame.getContentPane().add(stationName);

    stationArrivalTime = new JTextField();
    stationArrivalTime.setText("(time)");
    stationArrivalTime.setHorizontalAlignment(SwingConstants.CENTER);
    stationArrivalTime.setColumns(10);
    stationArrivalTime.setBounds(634, 169, 186, 23);
    frame.getContentPane().add(stationArrivalTime);

    lblNewLabel = new JLabel("MURPHY");
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblNewLabel.setBounds(529, 406, 105, 38);
    frame.getContentPane().add(lblNewLabel);

    lblBrokenRail = new JLabel("Broken Rail");
    lblBrokenRail.setHorizontalAlignment(SwingConstants.RIGHT);
    lblBrokenRail.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblBrokenRail.setBounds(537, 442, 79, 23);
    frame.getContentPane().add(lblBrokenRail);

    toggleBroken = new JToggleButton("N");
    toggleBroken.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleBroken.setBounds(634, 444, 45, 23);
    frame.getContentPane().add(toggleBroken);

    toggleCircuitFailure = new JToggleButton("N");
    toggleCircuitFailure.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleCircuitFailure.setBounds(634, 470, 45, 23);
    frame.getContentPane().add(toggleCircuitFailure);

    lblCircuitFailure = new JLabel("Circuit Failure");
    lblCircuitFailure.setHorizontalAlignment(SwingConstants.RIGHT);
    lblCircuitFailure.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblCircuitFailure.setBounds(529, 470, 87, 23);
    frame.getContentPane().add(lblCircuitFailure);

    lblPowerFailure = new JLabel("Power Failure");
    lblPowerFailure.setHorizontalAlignment(SwingConstants.RIGHT);
    lblPowerFailure.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblPowerFailure.setBounds(529, 494, 87, 23);
    frame.getContentPane().add(lblPowerFailure);

    togglePowerFailure = new JToggleButton("N");
    togglePowerFailure.setFont(new Font("Tahoma", Font.PLAIN, 11));
    togglePowerFailure.setBounds(634, 494, 45, 23);
    frame.getContentPane().add(togglePowerFailure);

    toggleOccupied.setSelected(false);
    toggleOccupied.setText("N");

    JLabel lblStationLights = new JLabel("Station Lights");
    lblStationLights.setHorizontalAlignment(SwingConstants.RIGHT);
    lblStationLights.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStationLights.setBounds(520, 307, 86, 23);
    frame.getContentPane().add(lblStationLights);

    JToggleButton toggleStationLightsForward = new JToggleButton("N/A");
    toggleStationLightsForward.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleStationLightsForward.setBounds(632, 309, 90, 23);
    frame.getContentPane().add(toggleStationLightsForward);

    JToggleButton toggleStationLightsBack = new JToggleButton("N/A");
    toggleStationLightsBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
    toggleStationLightsBack.setBounds(731, 309, 90, 23);
    frame.getContentPane().add(toggleStationLightsBack);

    JLabel backlabel = new JLabel("Backward");
    backlabel.setHorizontalAlignment(SwingConstants.CENTER);
    backlabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
    backlabel.setBounds(731, 285, 90, 23);
    frame.getContentPane().add(backlabel);

    JLabel lblForward = new JLabel("Forward");
    lblForward.setHorizontalAlignment(SwingConstants.CENTER);
    lblForward.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblForward.setBounds(632, 285, 90, 23);
    frame.getContentPane().add(lblForward);

    lblBeaconMessage = new JLabel("Beacon Message");
    lblBeaconMessage.setHorizontalAlignment(SwingConstants.RIGHT);
    lblBeaconMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblBeaconMessage.setBounds(494, 371, 112, 23);
    frame.getContentPane().add(lblBeaconMessage);

    textFieldBeaconMessage = new JTextField();
    textFieldBeaconMessage.setText("N/A");
    textFieldBeaconMessage.setHorizontalAlignment(SwingConstants.CENTER);
    textFieldBeaconMessage.setColumns(10);
    textFieldBeaconMessage.setBounds(632, 375, 189, 23);
    frame.getContentPane().add(textFieldBeaconMessage);

    //Button handling
    dropdownLine.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e){
        String l = (String) dropdownLine.getSelectedItem();
        
        Set<String> segmentStrings = track.trackList.get(l).keySet();
        
        dropdownBlock.removeAllItems();

        for (String item : segmentStrings){
          dropdownSegment.addItem(item);
        }
      }
    });


    dropdownSegment.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e){
        String l = (String) dropdownLine.getSelectedItem();
        String s = (String) dropdownSegment.getSelectedItem();

        Set<Integer> blockSet = track.trackList.get(l).get(s).keySet();
        System.out.println(blockSet);

        dropdownBlock.removeAllItems();

        for (Integer item : blockSet){
          dropdownBlock.addItem(Integer.toString(item));
        }

        //System.out.println(dropdownBlock.getItemCount());
      }
    });

    dropdownBlock.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e){
        //System.out.println(dropdownBlock.getItemCount());
        String block = (String) dropdownBlock.getSelectedItem();
        String section = (String) dropdownSegment.getSelectedItem();
        String line = (String) dropdownLine.getSelectedItem();
      }
    });

    /** Update button action listeners. Updates the display fields based upon the dropdown
    * buttons selected.
    */
    toggleUpdate.addActionListener(new ActionListener() {

    	@Override
      public void actionPerformed(ActionEvent e){

        String block = (String) dropdownBlock.getSelectedItem();
        String section = (String) dropdownSegment.getSelectedItem();
        String line = (String) dropdownLine.getSelectedItem();

        Block thisBlock = track.getBlock(line,section,Integer.valueOf(block));

        lengthField.setText(String.valueOf(METERSMULT*track.trackList.get(line).get(section).get(Integer.valueOf(block)).getLen()));
        elevationField.setText(String.valueOf(METERSMULT*track.trackList.get(line).get(section).get(Integer.valueOf(block)).getElevation()));
        speedField.setText(String.valueOf(METERSMULT*track.trackList.get(line).get(section).get(Integer.valueOf(block)).getSpeedLimit()));
        gradeField.setText(String.valueOf(track.trackList.get(line).get(section).get(Integer.valueOf(block)).getGrade()));
        stationName.setText(track.trackList.get(line).get(section).get(Integer.valueOf(block)).getStationName());

        //Probably a better way to template this.
        Boolean isOccupied = thisBlock.getOccupied();
        Boolean isBroken = thisBlock.getBroken();
        Boolean isCircuitFailure = thisBlock.getCircuitFailure();
        Boolean isPowerFailure = thisBlock.getPowerFailure();
        Boolean isUnderground = thisBlock.getUnderground();
        //Boolean trackHeaters = track.trackList.get(line).get(section).get(Integer.valueOf(block)).getTrackHeaters();

        Boolean trackHeaters = false;
        Boolean hasCrossing = track.crossingMap.keySet().contains(thisBlock);
        Boolean hasStation = track.blockStationMap.keySet().contains(thisBlock);
        Boolean hasLights = track.lightsMap.keySet().contains(thisBlock);
        Boolean hasBeacon = track.blockBeaconMap.keySet().contains(thisBlock);
        String strNextBlockForward = String.valueOf(thisBlock.nextBlockForward().blockNum());
        String strNextBlockBackward = String.valueOf(thisBlock.nextBlockBackward().blockNum());

        toggleOccupied.setSelected(isOccupied);
        toggleBroken.setSelected(isBroken);
        toggleCircuitFailure.setSelected(isCircuitFailure);
        togglePowerFailure.setSelected(isPowerFailure);

        toggleNextBlockBackward.setText(strNextBlockBackward);
        toggleNextBlockForward.setText(strNextBlockForward);

        if (hasBeacon) {
            textFieldBeaconMessage.setText(track.blockBeaconMap.get(thisBlock).getBeaconMessage());
        } else {
            textFieldBeaconMessage.setText("");
        }

        if (isOccupied) {
          toggleOccupied.setText("Y");
          toggleOccupied.setSelected(true);
        } else {
          toggleOccupied.setText("N");
          toggleOccupied.setSelected(false);
        }

        if (isBroken) {
          toggleBroken.setText("Y");
          toggleBroken.setSelected(true);
        } else{
          toggleBroken.setText("N");
          toggleBroken.setSelected(false);
        }

        if (isCircuitFailure) {
          toggleCircuitFailure.setText("Y");
          toggleCircuitFailure.setSelected(true);
        } else {
          toggleCircuitFailure.setText("N");
          toggleCircuitFailure.setSelected(false);
        }

        if (isPowerFailure) {
          togglePowerFailure.setText("Y");
          togglePowerFailure.setSelected(true);
        } else {
          togglePowerFailure.setText("N");
          togglePowerFailure.setSelected(false);
        }

        if (isUnderground) {
          toggleIsUnderground.setText("Y");
          toggleIsUnderground.setSelected(true);
        } else {
          toggleIsUnderground.setText("N");
          toggleIsUnderground.setSelected(false);
        }

        if (trackHeaters) {
          toggleHeatersOn.setText("Y");
          toggleHeatersOn.setSelected(true);
        } else {
          toggleHeatersOn.setText("N");
          toggleHeatersOn.setSelected(false);
        }

        if (hasCrossing) {
          if (track.crossingMap.get(thisBlock).crossingState) {
            toggleCrossings.setText("Down");
            toggleCrossings.setSelected(true);
          }
          else {
            toggleCrossings.setText("Up");
            toggleCrossings.setSelected(false);
          }
        } else {
            toggleCrossings.setText("N/A");
            toggleCrossings.setSelected(false);
        }

        if (hasStation) {
          if (track.blockStationMap.get(thisBlock).trackHeatersOn) {
            toggleHeatersOn.setText("On");
            toggleHeatersOn.setSelected(true);
            } else {
            toggleHeatersOn.setText("Off");
            toggleHeatersOn.setSelected(false);
          }

        if(track.blockStationMap.get(thisBlock).getForwardLights().viewLightsState()) {
            toggleStationLightsForward.setText("Green");
        } else {
            toggleStationLightsForward.setText("Red");
        }
        if (track.blockStationMap.get(thisBlock).getBackwardLights().viewLightsState()) {
            toggleStationLightsBack.setText("Green");
        } else{
            toggleStationLightsBack.setText("Red");
        }
        
        String nextArrivalTime = track.blockStationMap.get(thisBlock).nextArrivalTime();
        stationArrivalTime.setText(nextArrivalTime);
        
        } else{
            toggleHeatersOn.setText("N/A");
            toggleHeatersOn.setSelected(false);
        }

        if (hasLights) {
          if (track.lightsMap.get(thisBlock).viewLightsState()) {
            toggleSignals.setText("Green");
            toggleSignals.setSelected(false);
          } else {
            toggleSignals.setText("Red");
            toggleSignals.setSelected(false);
          }
        }
      }
    });

  }

  /** Listens to the combo box and updates the GUI based upon the inputs.
  */
  public void actionPerformed(ActionEvent e) {
    /** @bug The segment and block descriptors need to be cleared when selecting a dropdown from line.
    * In its current configuration, it will not reset to empty fields, allowing the user to select
    *   blocks that do not exist. This condition applies to selecting a new line while holding segment
    *   and block constant.
    *
    * @bug It is possible for a user to select default vaules in the dropdown menu. When selecting the default
    *   values, it results in an unhandled exception, rendering the program unusable.
    */
    JComboBox cb = (JComboBox) e.getSource();
    String name = (String)cb.getSelectedItem();
  }
}
