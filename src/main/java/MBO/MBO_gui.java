package MBO;
import java.util.*;
import CommonUIElements.*;
import TrackModel.*;
import TrainModel.*;
import CTC.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;

public class MBO_gui {

	JFrame frame;
	private JPanel trainSchedPanel;
	private JTextField startTextField;
	private JTextField numTrainTextField;
	private JTextField numLoopsTextField;
	private Border grayline;
	private JTextField numThruputTextfield;
	String [] redLineNames = {"Shadyside", "Herron Ave", "Swissvale", "Penn Station",
		"Steel Plaza", "First Ave", "Station Square", "South Hills Junction"};
	private JTable trainTable = new JTable();
	private DefaultTableModel trainModel = new DefaultTableModel();
	private MovingBlockOverlay mbo;
	private CTCgui ctc;


	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MBO_gui window = new MBO_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public MBO_gui(MovingBlockOverlay mbo) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
	//public MBO_gui() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		this.mbo = mbo;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException{

		grayline = BorderFactory.createLineBorder(Color.gray);

		frame = new JFrame();
		frame.setBounds(100, 100, 715, 417);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel testPanel = new JPanel();
		testPanel.setBounds(586, 0, 113, 378);
		testPanel.setBorder(grayline);
		frame.getContentPane().add(testPanel);
		testPanel.setLayout(null);

		JLabel murphyLabel = new JLabel("MURPHY");
		murphyLabel.setBounds(0, 11, 113, 16);
		murphyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		murphyLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		testPanel.add(murphyLabel);

		JLabel murphylabel1 = new JLabel("Break");
		murphylabel1.setBounds(0, 27, 113, 14);
		murphylabel1.setHorizontalAlignment(SwingConstants.CENTER);
		testPanel.add(murphylabel1);

		JLabel murphyLabel2 = new JLabel("Communications");
		murphyLabel2.setBounds(0, 39, 113, 14);
		murphyLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		testPanel.add(murphyLabel2);

		JLabel murphyLabel3 = new JLabel("with CTC");
		murphyLabel3.setBounds(0, 52, 113, 14);
		murphyLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		murphyLabel3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(murphyLabel3);

		JToggleButton failCTCyes = new JToggleButton("Y");
		failCTCyes.setBounds(25, 63, 39, 23);
		failCTCyes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(failCTCyes);

		JToggleButton failCTCno = new JToggleButton("N");
		failCTCno.setBounds(62, 63, 39, 23);
		failCTCno.setSelected(true);
		failCTCno.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(failCTCno);

		JLabel lblTrainAntennaFails = new JLabel("Train Antenna Fails");
		lblTrainAntennaFails.setBounds(0, 97, 113, 14);
		lblTrainAntennaFails.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainAntennaFails.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(lblTrainAntennaFails);

		JToggleButton antennaFailNo = new JToggleButton("N");
		antennaFailNo.setBounds(62, 112, 39, 23);
		antennaFailNo.setSelected(true);
		antennaFailNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(antennaFailNo);

		JToggleButton antennaFailYes = new JToggleButton("Y");
		antennaFailYes.setBounds(25, 112, 39, 23);
		antennaFailYes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testPanel.add(antennaFailYes);

		JLabel testLabel = new JLabel("TESTING");
		testLabel.setHorizontalAlignment(SwingConstants.CENTER);
		testLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		testLabel.setBounds(0, 142, 113, 16);
		testPanel.add(testLabel);

		JLabel startLabel = new JLabel("start time");
		startLabel.setHorizontalAlignment(SwingConstants.LEFT);
		startLabel.setBounds(65, 172, 48, 14);
		testPanel.add(startLabel);

		startTextField = new JTextField();
		startTextField.setBounds(10, 169, 48, 20);
		testPanel.add(startTextField);
		startTextField.setColumns(10);

		numTrainTextField = new JTextField();
		numTrainTextField.setColumns(10);
		numTrainTextField.setBounds(10, 197, 48, 20);
		testPanel.add(numTrainTextField);

		JLabel trainsLabel = new JLabel("trains");
		trainsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		trainsLabel.setBounds(65, 200, 48, 14);
		testPanel.add(trainsLabel);

		JLabel loopsLabel = new JLabel("loops");
		loopsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		loopsLabel.setBounds(65, 228, 48, 14);
		testPanel.add(loopsLabel);

		numLoopsTextField = new JTextField();
		numLoopsTextField.setBounds(10, 225, 48, 20);
		testPanel.add(numLoopsTextField);
		numLoopsTextField.setColumns(10);

		JButton testButton = new JButton("TEST");
		testButton.setBounds(12, 250, 89, 23);
		testPanel.add(testButton);

		testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Schedule test = mbo.getSched().get(0);

                int loops = Integer.parseInt(numLoopsTextField.getText());
	    		int start = Integer.parseInt(startTextField.getText());
	    		int trains = Integer.parseInt(numTrainTextField.getText());

	    		test.simpleSchedule(loops, start, trains);
	    		scheduleToGUI(loops * trains, test.getSched());
            }
        });

		JPanel choicePanel = new JPanel();
		choicePanel.setLayout(null);
		choicePanel.setBounds(473, 0, 113, 378);
		choicePanel.setBorder(grayline);
		frame.getContentPane().add(choicePanel);

		JLabel lineLabel = new JLabel("Choose a Line:");
		lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lineLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lineLabel.setBounds(0, 11, 113, 14);
		choicePanel.add(lineLabel);

		JSpinner lineSpinner = new JSpinner();
		lineSpinner.setModel(new SpinnerListModel(new String[] {"Red", "Green"}));
		lineSpinner.setBounds(10, 29, 93, 20);
		choicePanel.add(lineSpinner);

		JLabel chooseTrainLabel = new JLabel("Choose a Train:");
		chooseTrainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chooseTrainLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chooseTrainLabel.setBounds(0, 60, 113, 14);
		choicePanel.add(chooseTrainLabel);

		JSpinner chooseTrainSpinner = new JSpinner();
		chooseTrainSpinner.setBounds(10, 85, 93, 20);
		choicePanel.add(chooseTrainSpinner);

		JButton trainInfoBtn = new JButton("View Train");
		trainInfoBtn.setBounds(10, 108, 93, 23);
		choicePanel.add(trainInfoBtn);

		JLabel chooseThroughputLabel = new JLabel("Choose Throughput:");
		chooseThroughputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chooseThroughputLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chooseThroughputLabel.setBounds(0, 146, 113, 14);
		choicePanel.add(chooseThroughputLabel);

		numThruputTextfield = new JTextField();
		numThruputTextfield.setColumns(10);
		numThruputTextfield.setBounds(10, 164, 48, 20);
		choicePanel.add(numThruputTextfield);

		JLabel throughputLabel = new JLabel("people");
		throughputLabel.setHorizontalAlignment(SwingConstants.LEFT);
		throughputLabel.setBounds(65, 167, 48, 14);
		choicePanel.add(throughputLabel);

		JLabel chooseModeLabel = new JLabel("Choose a Mode:");
		chooseModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chooseModeLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chooseModeLabel.setBounds(0, 195, 113, 14);
		choicePanel.add(chooseModeLabel);

		JSpinner modeSpinner = new JSpinner();
		modeSpinner.setModel(new SpinnerListModel(new String[] {"MBO", "Fixed Block", "Manual"}));
		modeSpinner.setBounds(10, 210, 93, 20);
		choicePanel.add(modeSpinner);

		JButton setModeTypeBtn = new JButton("Set Mode");
		setModeTypeBtn.setBounds(10, 233, 93, 23);
		choicePanel.add(setModeTypeBtn);

		JLabel editDriverLabel = new JLabel("Update Driver List:");
		editDriverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editDriverLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		editDriverLabel.setBounds(0, 267, 113, 14);
		choicePanel.add(editDriverLabel);

		trainSchedPanel = new TrainSchedPanel();
		trainSchedPanel.setBounds(0, 0, 473, 194);
		trainSchedPanel.setBorder(grayline);
		frame.getContentPane().add(trainSchedPanel);

		JPanel driverSchedPanel = new DriverSchedPanel();
		driverSchedPanel.setBounds(0, 193, 473, 185);
		driverSchedPanel.setBorder(grayline);
		frame.getContentPane().add(driverSchedPanel);

		frame.setVisible(true);
	}

	/**
	 * Displays the schedule in the train
	 * @param rows     Number of rows to make the table
	 * @param arrivals List of times broken up by stations
	 *
	 * @bug Only works for one train now
	 */
	private void scheduleToGUI(int rows, ArrayList<ArrayList<Integer>> arrivals){
		trainModel.setRowCount(rows);
		trainTable.setModel(trainModel);

		for(int i = 0; i < arrivals.size()-1; i++){
			for(int j = 0; j < arrivals.get(0).size(); j++){
				trainTable.setValueAt(Schedule.convertTime(arrivals.get(i).get(j)), j, i);
			}
		}
	}

	public class TrainSchedPanel extends JPanel {

        public TrainSchedPanel() {

            setLayout(new BorderLayout());

            JPanel options = new JPanel(new GridBagLayout());

            trainModel = new DefaultTableModel();
            trainTable = new JTable();
            add(new JScrollPane(trainTable));
            add(options, BorderLayout.NORTH);

            trainTable.setAutoCreateColumnsFromModel(true);
            trainTable.setAutoResizeMode(0);
            for (int i = 0; i < (trainTable.getColumnCount()); i++){
	            trainTable.getColumn(i).setPreferredWidth(250);
	        }

            trainModel.setColumnIdentifiers(redLineNames);
            trainModel.setColumnCount(redLineNames.length);
            trainModel.setRowCount(50);

            // Replace model
            trainTable.setModel(trainModel);

        }

    }

    public class DriverSchedPanel extends JPanel {

        private JTable table = new JTable();
        private DefaultTableModel model = new DefaultTableModel();

        public DriverSchedPanel() {

            setLayout(new BorderLayout());

            JPanel options = new JPanel(new GridBagLayout());

            model = new DefaultTableModel();
            table = new JTable();
            add(new JScrollPane(table));
            add(options, BorderLayout.NORTH);

            table.setAutoCreateColumnsFromModel(true);

            model.setColumnCount(10);
            model.setRowCount(50);

            // Replace model
            table.setModel(model);

        }

    }

		public void setCTC(CTCgui ctc){
			this.ctc = ctc;
		}

		public JPanel getTrainSchedulePanel(){
			return trainSchedPanel;
		}
}
