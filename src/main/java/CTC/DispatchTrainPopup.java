package CTC;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.*;
import TrackModel.*;
import WaysideController.*;

public class DispatchTrainPopup {

	private JFrame frame;
	private JTextField speed_txt;
	private JTextField authority_txt;
	DummyTrain newTrain;
	Double speed;
	Integer auth;
	ArrayList<Block> path;
	static TrainManager trainManager;
	static WS wayside;
	static TrackModel dummyTrack;
	static TrackPanel trackPanel;
	ArrayList<ArrayList<Block>> pathOptions;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DispatchTrainPopup window = new DispatchTrainPopup(trainManager, wayside, dummyTrack, trackPanel);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DispatchTrainPopup(TrainManager trainManager, WS wayside, TrackModel dummyTrack, TrackPanel trackPanel) {
		initialize(trainManager, wayside, dummyTrack, trackPanel);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(TrainManager trainManager, WS wayside, TrackModel dummyTrack, TrackPanel trackPanel) {

		JComboBox dropdown_line;
		JComboBox dropdown_segment;
		JComboBox dropdown_block;

		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 323, 269);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Dispatch Train");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(86, 11, 149, 32);
		frame.getContentPane().add(lblNewLabel);
/*
		JLabel lblNewLabel_1 = new JLabel("Segment");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(76, 57, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		String[] segment = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"};
		JComboBox<String> lineDropdown = new JComboBox<>(segment);
		lineDropdown.setBounds(130, 54, 70, 20);
		frame.getContentPane().add(lineDropdown);
*/
		JLabel lblTimeToLeave = new JLabel("Speed");
		lblTimeToLeave.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTimeToLeave.setBounds(76, 101, 46, 14);
		frame.getContentPane().add(lblTimeToLeave);

		speed_txt = new JTextField();
		speed_txt.setBounds(130, 95, 70, 20);
		frame.getContentPane().add(speed_txt);
		speed_txt.setColumns(10);

		JLabel lblMs = new JLabel("mi/hr");
		lblMs.setBounds(220, 101, 46, 14);
		frame.getContentPane().add(lblMs);

/*
		JLabel lblAuthority = new JLabel("Authority");
		lblAuthority.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAuthority.setBounds(52, 134, 70, 14);
		frame.getContentPane().add(lblAuthority);

		authority_txt = new JTextField();
		authority_txt.setColumns(10);
		authority_txt.setBounds(130, 128, 70, 20);
		frame.getContentPane().add(authority_txt);
		JLabel lblM = new JLabel("mi");
		lblM.setBounds(220, 134, 46, 14);
		frame.getContentPane().add(lblM);

		*/

		// dropdowns to select block to view
		Set<Integer> blockInts = dummyTrack.viewTrackList().get("Red").get("A").keySet();
		Integer[] intArr = blockInts.toArray(new Integer[blockInts.size()]);
		Set<String> lineSet = dummyTrack.viewTrackList().keySet();

		String[] lineName = lineSet.toArray(new String[lineSet.size()]);
		dropdown_line = new JComboBox<>();
		dropdown_line.setModel(new DefaultComboBoxModel<String>(lineName));
		dropdown_line.setBounds(20, 57, 72, 20);
		frame.getContentPane().add(dropdown_line);
		dropdown_line.setToolTipText("LINE");

		String[] segName = {};
		dropdown_segment = new JComboBox<String>();
		dropdown_segment.setModel(new DefaultComboBoxModel<String>(segName));
		dropdown_segment.setBounds(102, 57, 72, 20);
		frame.getContentPane().add(dropdown_segment);
		dropdown_segment.setToolTipText("SEGMENT");

		String[] blockName = {};
		dropdown_block = new JComboBox<>();
		dropdown_block.setModel(new DefaultComboBoxModel<String>(blockName));
		dropdown_block.setBounds(184, 57, 72, 20);
		frame.getContentPane().add(dropdown_block);
		dropdown_block.setToolTipText("BLOCK");

		// initialize dropdowns to Red/A/1 at start up to avoid null pointer exception
				dropdown_line.setSelectedItem(lineName[0]);
				String l = (String) dropdown_line.getSelectedItem();
				Set<String> segSet = dummyTrack.viewTrackList().get(l).keySet();
				segName = segSet.toArray(new String[segSet.size()]);
				dropdown_block.removeAllItems();
				for (String item : segSet){
					dropdown_segment.addItem(item);
				}
				dropdown_segment.setSelectedItem(segName[0]);
				l = (String) dropdown_line.getSelectedItem();
				String s = (String) dropdown_segment.getSelectedItem();
				Set<Integer> blockSet = dummyTrack.viewTrackList().get(l).get(s).keySet();
				Integer[] blockInt = blockSet.toArray(new Integer[blockSet.size()]);
				dropdown_block.removeAllItems();
				for (Integer item : blockSet){
					dropdown_block.addItem(Integer.toString(item));
				}
				dropdown_block.setSelectedItem(blockInt[0]);
				String b = (String)dropdown_block.getSelectedItem();
				int blockNum;
				try{
					blockNum = Integer.parseInt(b);
				}catch(NumberFormatException num){
					blockNum = dummyTrack.getSection(l, s).keySet().toArray(new Integer [0])[0];
				}


		// all action listeners
				dropdown_line.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String l = (String) dropdown_line.getSelectedItem();
						Set<String> segName = dummyTrack.viewTrackList().get(l).keySet();
						dropdown_block.removeAllItems();
						for (String item : segName){
							dropdown_segment.addItem(item);
						}


						}

				});

				dropdown_segment.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String l = (String) dropdown_line.getSelectedItem();
						String s = (String) dropdown_segment.getSelectedItem();
						Set<Integer> blockSet = dummyTrack.viewTrackList().get(l).get(s).keySet();
						dropdown_block.removeAllItems();
						for (Integer item : blockSet)
							dropdown_block.addItem(Integer.toString(item));
						}

				});

				dropdown_block.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							String block = (String) dropdown_block.getSelectedItem();
							String section = (String) dropdown_segment.getSelectedItem();
							String line = (String) dropdown_line.getSelectedItem();
							//convert block from string to Integer
							int blockNum;
							try{
								blockNum = Integer.parseInt(block);
							}catch(NumberFormatException num){
								blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
							}
						}
				});


		JButton btnDispatch = new JButton("Dispatch Train");
		btnDispatch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String block = (String) dropdown_block.getSelectedItem();
				String section = (String) dropdown_segment.getSelectedItem();
				String line = (String) dropdown_line.getSelectedItem();
				//convert block from string to Integer
				int blockNum;
				try{
					blockNum = Integer.parseInt(block);
				}catch(NumberFormatException num){
					blockNum = dummyTrack.getSection(line, section).keySet().toArray(new Integer [0])[0];
				}
				speed = (Double.parseDouble(speed_txt.getText()));
				Block endBlock = dummyTrack.getBlock(line, section, blockNum);
				Block startBlock = dummyTrack.getBlock("Red", "C", 7);
				pathOptions = dummyTrack.blockToBlock(startBlock, endBlock);

				ArrayList<Block> blocks = new ArrayList<Block>();

					blocks.add(dummyTrack.getBlock("Red", "U", new Integer(77)));
					blocks.add(dummyTrack.getBlock("Red", "C", new Integer(9)));
					blocks.add(dummyTrack.getBlock("Red", "C", new Integer(8)));
					blocks.add(dummyTrack.getBlock("Red", "C", new Integer(7)));

				path = pathOptions.get(0);
				if(path.size() != 0){
					for(int i=0; i<path.size(); i++){
						blocks.add(path.get(i));
					}
					for(int k=0; k<blocks.size(); k++){

							blocks.get(k).setSuggestedSpeed(speed);

					}


					newTrain = new DummyTrain(startBlock, path);
					trainManager.addTrain(newTrain);
				}else{
					System.out.println("Adding Train: path returned null");
				}

				frame.setVisible(false);

			}
		});
		btnDispatch.setBounds(48, 195, 104, 23);
		frame.getContentPane().add(btnDispatch);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
			}
		});
		btnCancel.setBounds(162, 195, 104, 23);
		frame.getContentPane().add(btnCancel);
		frame.setVisible(true);
	}



}
