package WaysideController;


import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class wayside_gui_main {

	private JFrame frame;
	private JTextField txtEnterPlcFile;
	private String PLCFilePath;
	private JButton loadBtn;
	private File PLCFile;
	private Wayside_PLC WaysidePLC;
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
	public wayside_gui_main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		initialize();
	}
	public void show() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
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
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
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


		samples = makeSampleBlocks();

		WS Red1 = new WS("Red1", null, samples);
		WS Red2 = new WS("Red2", null, samples);
		WS Green1 = new WS("Green1", null, samples);
		WS Green2 = new WS("Green2",null, samples);
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

		JComboBox<String> murphyBox = new JComboBox<String>();
		murphyBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Comm Failure with CTC", "Comm Failure with Track"}));
		murphyBox.setBounds(10, 484, 143, 20);
		frame.getContentPane().add(murphyBox);

		JButton btnFail = new JButton("Fail");
		btnFail.setBounds(163, 483, 60, 23);
		btnFail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				btnFail.setActionCommand(murphyBox.getSelectedItem().toString());
				printToAllWayside(btnFail.getActionCommand() + "!!!");
			}
		});
		frame.getContentPane().add(btnFail);

		txtEnterPlcFile = new JTextField();
		txtEnterPlcFile.setText("Enter PLC file path here...");
		txtEnterPlcFile.setBounds(264, 484, 220, 20);
		frame.getContentPane().add(txtEnterPlcFile);
		txtEnterPlcFile.setColumns(10);

		JButton btnLoadBtn = new JButton("Load");
		btnLoadBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				btnLoadBtn.setActionCommand(txtEnterPlcFile.getText());
				try {
					tryPLCFile(btnLoadBtn.getActionCommand());	//see if selected is a valid file
				} catch (IOException | ScriptException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.loadBtn = btnLoadBtn;
		btnLoadBtn.setBounds(494, 483, 69, 23);
		frame.getContentPane().add(btnLoadBtn);

		JLabel label = new JLabel("Load PLC File");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(264, 455, 115, 18);
		frame.getContentPane().add(label);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBounds(233, 455, 12, 73);
		frame.getContentPane().add(verticalStrut);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setBounds(642, 455, 12, 73);
		frame.getContentPane().add(verticalStrut_1);

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
			WaysidePLC = new Wayside_PLC(PLCFile);
			ws.setPlc(WaysidePLC.getPLC());
		}
		else
			JOptionPane.showMessageDialog(new Frame(),"Specified file was not found", "Invalid Filename", JOptionPane.WARNING_MESSAGE);
	}
  
	public void printToAllWayside(String toPrint){
		for(WS ws : Waysides)
			try {
				ws.getNotifStream().write(new String("\n" + toPrint).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public Block[] makeSampleBlocks(){

		Block Block1 = new Block("Red", "A", 1, true);
		Block Block2 = new Block("Red", "A", 2, false);
		Block Block3 = new Block("Red", "A", 3, false);
		Block Block4 = new Block("Red", "B", 4, false);
		Block1.setNextBlock(Block2);
		Block1.setUpcomingBlock(Block3);
		Block2.setNextBlock(Block3);
		Block2.setUpcomingBlock(Block4);
		Block3.setNextBlock(Block4);
		Block [] samples = {Block1, Block2, Block3, Block4};
		return samples;
	}
}
