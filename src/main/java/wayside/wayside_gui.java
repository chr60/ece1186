package proj1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings({"unchecked", "rawtypes", "serial"})

public class wayside_gui {

	private JFrame frmTrackController;
	private JTextField textField;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wayside_gui window = new wayside_gui();
					window.frmTrackController.setTitle("Wayside Controller");
					window.frmTrackController.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public wayside_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrackController = new JFrame();
		frmTrackController.setTitle("Track Controller");
		frmTrackController.setBounds(100, 100, 785, 464);
		frmTrackController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrackController.getContentPane().setLayout(null);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setAction(action);
		btnBrowse.setBounds(329, 317, 89, 23);
		frmTrackController.getContentPane().add(btnBrowse);

		JButton btnLoad = new JButton("Load");
		btnLoad.setAction(action_1);
		btnLoad.setBounds(230, 317, 89, 23);
		frmTrackController.getContentPane().add(btnLoad);

		textField = new JTextField("Choose PLC file path here...");
		textField.setBounds(10, 318, 210, 20);
		frmTrackController.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblMurphy = new JLabel("Murphy Controls");
		lblMurphy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMurphy.setBounds(10, 362, 115, 23);
		frmTrackController.getContentPane().add(lblMurphy);

		JButton btnNewButton = new JButton("FAIL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(174, 388, 55, 23);
		frmTrackController.getContentPane().add(btnNewButton);

		JComboBox MurphySelect = new JComboBox();
		MurphySelect.setModel(new DefaultComboBoxModel(new String[] {"Select Failure Type"}));
		MurphySelect.setBounds(10, 389, 136, 20);
		frmTrackController.getContentPane().add(MurphySelect);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(10, 362, 883, 23);
		frmTrackController.getContentPane().add(separator);

		JTextPane txtpnMessageBoardsuggested = new JTextPane();
		txtpnMessageBoardsuggested.setText("\tMessage Board\r\n-suggested speeds\r\n-failure reports\r\n-signal/ light status changes\r\n-PLC syntax error report\r\n");
		txtpnMessageBoardsuggested.setBounds(424, 36, 319, 270);
		frmTrackController.getContentPane().add(txtpnMessageBoardsuggested);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Select Line"}));
		comboBox.setBounds(10, 36, 118, 23);
		frmTrackController.getContentPane().add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Select Segment"}));
		comboBox_1.setToolTipText("LINE");
		comboBox_1.setBounds(128, 36, 118, 23);
		frmTrackController.getContentPane().add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Select Block"}));
		comboBox_2.setBounds(246, 36, 118, 23);
		frmTrackController.getContentPane().add(comboBox_2);

		JTextPane txtpnReportingFromTrack = new JTextPane();
		txtpnReportingFromTrack.setText("Reporting from Track Model...\r\n- block occupancy\r\n- color of lights\r\n- switch positions\r\n- activity of railroad crossings\r\n- any failures");
		txtpnReportingFromTrack.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtpnReportingFromTrack.setBounds(10, 58, 354, 101);
		frmTrackController.getContentPane().add(txtpnReportingFromTrack);

		JLabel lblTrackController = new JLabel("Track Controller");
		lblTrackController.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTrackController.setBounds(284, 11, 205, 14);
		frmTrackController.getContentPane().add(lblTrackController);

		JTextPane txtpnReportingFromCtc = new JTextPane();
		txtpnReportingFromCtc.setText("Reporting from CTC/ Track Model\r\n-Suggested Speed\r\n-Suggested Authority\r\n-Failure Status");
		txtpnReportingFromCtc.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtpnReportingFromCtc.setBounds(10, 191, 354, 115);
		frmTrackController.getContentPane().add(txtpnReportingFromCtc);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"Select Train"}));
		comboBox_3.setBounds(10, 167, 118, 23);
		frmTrackController.getContentPane().add(comboBox_3);

		JButton btnOpenTestConsole = new JButton("Open Test Console");
		btnOpenTestConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOpenTestConsole.setBounds(613, 388, 130, 23);
		frmTrackController.getContentPane().add(btnOpenTestConsole);
	}


	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Opens file browser to search for PLC file");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Load");
			putValue(SHORT_DESCRIPTION, "Loads selected file");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}

