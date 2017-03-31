package WaysideController;

import TrackModel.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WaysideGUI {

  ArrayList<WS> Waysides;
  private JFrame MainFrame;

  public JFrame getFrame(){
    return this.MainFrame;
  }

  public WaysideGUI(TrackModel track, ArrayList<WS> Waysides) {
    /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName()))
				{javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;}
			}*/

      this.Waysides=Waysides;

      JFrame frame = new JFrame();
      this.MainFrame = frame;
      frame.setBounds(100, 100, 1050, 587);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(null);

      Set<Integer> blockInts = track.viewTrackList().get("Red").get("A").keySet();

  		Integer[] intArr = blockInts.toArray(new Integer[blockInts.size()]);
  		Set<String> lineSet = track.viewTrackList().keySet();
  		String[] lineStrings = lineSet.toArray(new String[lineSet.size()]);

      JComboBox<String> lineDropdown = new JComboBox<String>();
  		lineDropdown.setModel(new DefaultComboBoxModel<String>(lineStrings));
  		lineDropdown.setToolTipText("");
  		lineDropdown.setBounds(10, 11, 137, 20);
  		frame.add(lineDropdown);

  		String[] segmentStrings = {};
  		JComboBox<String> segmentDropdown = new JComboBox<String>();
  		segmentDropdown.setModel(new DefaultComboBoxModel<String>(segmentStrings));
  		segmentDropdown.setBounds(145, 11, 137, 20);
  		frame.add(segmentDropdown);

  		String[] blockStrings = {};
  		JComboBox<String> blockDropdown = new JComboBox<String>();
  		blockDropdown.setModel(new DefaultComboBoxModel<String>(blockStrings));
      blockDropdown.setBounds(281, 11, 130, 20);
  		frame.add(blockDropdown);

  		lineDropdown.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e){
  				String l = (String) lineDropdown.getSelectedItem();
  				Set<String> segmentStrings = track.viewTrackList().get(l).keySet();
  				blockDropdown.removeAllItems();
  				for (String item : segmentStrings){
  					segmentDropdown.addItem(item);
  				}
  			}
  		});


  		segmentDropdown.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e){
  				String l = (String) lineDropdown.getSelectedItem();
  				String s = (String) segmentDropdown.getSelectedItem();
  				Set<Integer> blockSet = track.viewTrackList().get(l).get(s).keySet();
  				System.out.println(blockSet);
  				blockDropdown.removeAllItems();
  				for (Integer item : blockSet)
  					blockDropdown.addItem(Integer.toString(item));

  				System.out.println(blockDropdown.getItemCount());
  			}
  		});

  		blockDropdown.addActionListener(new ActionListener() {
  			@Override
  			public void actionPerformed(ActionEvent e){
  				System.out.println(blockDropdown.getItemCount());
  				String block = (String) blockDropdown.getSelectedItem();
  				String section = (String) segmentDropdown.getSelectedItem();
  				String line = (String) lineDropdown.getSelectedItem();
  			}
  		});

  		//BLOCK CONSOLE
  		JTextArea blockConsole = new JTextArea();
  		blockConsole.setBackground(Color.LIGHT_GRAY);
  		blockConsole.setBounds(10, 30, 428, 189);
  		frame.add(blockConsole);
  		blockConsole.setColumns(10);

  		JLabel notificationsLabel = new JLabel("Notifications");
  		notificationsLabel.setBounds(448, 14, 92, 14);
  		frame.add(notificationsLabel);

      JTextArea notifConsole = new JTextArea();
  		notifConsole.setBounds(448, 30, 658, 405);
  		frame.add(notifConsole);

  		JLabel lblMurphy = new JLabel("Murphy");
  		lblMurphy.setFont(new Font("Tahoma", Font.PLAIN, 14));
  		lblMurphy.setBounds(10, 455, 69, 18);
  		frame.getContentPane().add(lblMurphy);

  		JComboBox<String> murphyDropdown = new JComboBox<String>();
  		murphyDropdown.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Comm Failure with CTC", "Comm Failure with Track", "Wayside Failure"}));
  		murphyDropdown.setBounds(10, 484, 143, 20);
  		frame.getContentPane().add(murphyDropdown);

      /*
        @bug Failure button doesn't print to Notifications console
       */
  		JButton failBtn = new JButton("Fail");
  		failBtn.setBounds(163, 483, 60, 23);
  		failBtn.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				failBtn.setActionCommand(murphyDropdown.getSelectedItem().toString());
  				printToAllWayside(failBtn.getActionCommand() + "!!!");
  			}
  		});
  		frame.getContentPane().add(failBtn);

  		JTextField txtEnterPlcFile = new JTextField();
  		txtEnterPlcFile.setText("Enter PLC file path here...");
  		txtEnterPlcFile.setBounds(264, 484, 220, 20);
  		frame.getContentPane().add(txtEnterPlcFile);
  		txtEnterPlcFile.setColumns(10);


  		JButton loadPLCBtn = new JButton("Load");
  		loadPLCBtn.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				loadPLCBtn.setActionCommand(txtEnterPlcFile.getText());
  				try {
  					tryPLCFile(loadPLCBtn.getActionCommand());	//see if selected is a valid file
  				} catch (IOException | ScriptException e1) {
  					e1.printStackTrace();
  				}
  			}
  		});
  		loadPLCBtn.setBounds(494, 483, 69, 23);
  		frame.getContentPane().add(loadPLCBtn);

  		JLabel PLCLabel = new JLabel("Load PLC File");
  		PLCLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
  		PLCLabel.setBounds(264, 455, 115, 18);
  		frame.getContentPane().add(PLCLabel);

  		JButton button = new JButton("Browse");
  		button.setBounds(563, 483, 79, 23);
  		frame.getContentPane().add(button);
  		button.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e){
  				//open file browser
  				JFileChooser PLCChoose = new JFileChooser();
  				PLCChoose.setFileFilter(new FileNameExtensionFilter("PLC Files (.plc)", "plc"));
  				int response = PLCChoose.showOpenDialog(null);
  				if (response == JFileChooser.APPROVE_OPTION){
  					txtEnterPlcFile.setText(PLCChoose.getSelectedFile().getAbsolutePath());
  				} else {
  				}
  			}
  		});
  		frame.setVisible(true);
  	    }

  	public void setSpeedAuth(Block block){

   	}

  	/**
  	 * Tries to open give PLC file and checks for existence
  	 * @param filename - user entered filename/path of PLC Code File
  	 * @throws IOException
  	 * @throws ScriptException
  	 * @bug PLC file not being set to wayside(s) or print success to console, or clear loading bar.
  	 */
  	public boolean tryPLCFile(String filename) throws IOException, ScriptException{
  		File PLCFile= new File(filename);
  		if(PLCFile.exists()){
  			printToAllWayside("PLC Data loaded");
  			PLC plc = PLCParse.parseLine(PLCFile);
  			return true;
  		}
  		else{
  			JOptionPane.showMessageDialog(new Frame(),"Specified file was not found", "Invalid Filename", JOptionPane.WARNING_MESSAGE);
  			return false;
  		}
  	}

  	public void getStates(){

  	}

  	public void printToAllWayside(String toPrint){
  		/*for(WS ws : Waysides)
  			try {
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}*/
  	}

}