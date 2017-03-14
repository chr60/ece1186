package WaysideController;


import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import TrackModel.*;

public class WaysideGuiMain {

	private JFrame frame;
	private JTextField txtEnterPlcFile;
	private String PLCFilePath;
	private JButton loadBtn;
	private File PLCFile;
	private Block [] samples;
	private WS ws;

	ArrayList<WS> Waysides = new ArrayList<WS>();
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public WaysideGuiMain(TrackModel track) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		initialize(track);
	}
	/*public void show() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName()))
				{javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;}

			}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wayside_gui_main window = new wayside_gui_main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void initialize(TrackModel track) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName()))
				{javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;}
			}


		frame = new JFrame();
		frame.setBounds(100, 100, 1050, 587);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1000, 433);
		frame.getContentPane().add(tabbedPane);



		WS Red1 = new WS("Red1", null, track);
		WS Red2 = new WS("Red2", null, track);
		WS Green1 = new WS("Green1", null, track);
		WS Green2 = new WS("Green2",null, track);

		Waysides.add(Red1);
		Waysides.add(Red2);
		Waysides.add(Green1);
		Waysides.add(Green2);
		this.ws = Red1;


		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Green - WS 1", null, Green1, null);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Green - WS 2", null, Green2, null);

		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Red - WS 1", null, Red1, null);

		JTabbedPane tabbedPane_4 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Red - WS 2", null, Red2, null);

		JLabel lblMurphy = new JLabel("Murphy");
		lblMurphy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMurphy.setBounds(10, 455, 69, 18);
		frame.getContentPane().add(lblMurphy);

		JComboBox<String> murphyDropdown = new JComboBox<String>();
		murphyDropdown.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Comm Failure with CTC", "Comm Failure with Track", "Wayside Failure"}));
		murphyDropdown.setBounds(10, 484, 143, 20);
		frame.getContentPane().add(murphyDropdown);

		JButton failBtn = new JButton("Fail");
		failBtn.setBounds(163, 483, 60, 23);
		failBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				failBtn.setActionCommand(murphyDropdown.getSelectedItem().toString());
				printToAllWayside(failBtn.getActionCommand() + "!!!");
			}
		});
		frame.getContentPane().add(failBtn);

		txtEnterPlcFile = new JTextField();
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
		this.loadBtn = loadPLCBtn;
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
					setPLCFilePath(txtEnterPlcFile.getSelectedText());
				} else {
				}
			}
		});
		frame.setVisible(true);
	    }

	/**
	 * Tries to open give PLC file and checks for existence
	 * @param filename - user entered filename/path of PLC Code File
	 * @throws IOException
	 * @throws ScriptException
	 */

	public void tryPLCFile(String filename) throws IOException, ScriptException{
		File PLCFile= new File(filename);
		if(PLCFile.exists()){
			printToAllWayside("PLC Data loaded");
			this.PLCFile = PLCFile;
			PLC plc = WaysidePLC.parseLine(PLCFile);
			ws.setPlc(plc);
		}
		else
			JOptionPane.showMessageDialog(new Frame(),"Specified file was not found", "Invalid Filename", JOptionPane.WARNING_MESSAGE);
	}

	public void printToAllWayside(String toPrint){
		/*for(WS ws : Waysides)
			try {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}

	public String getPLCFilePath() {
		return PLCFilePath;
	}
	public void setPLCFilePath(String pLCFilePath) {
		PLCFilePath = pLCFilePath;
	}
	public JButton getLoadBtn() {
		return loadBtn;
	}
	public void setLoadBtn(JButton loadBtn) {
		this.loadBtn = loadBtn;
	}

}
