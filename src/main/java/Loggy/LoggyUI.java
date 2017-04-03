import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class LoggyUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoggyUI window = new LoggyUI();
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
	public LoggyUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 575, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 559, 361);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JComboBox moduleComboBox = new JComboBox();
		moduleComboBox.setBounds(68, 63, 115, 20);
		panel.add(moduleComboBox);
		
		JLabel mainTitle = new JLabel("LOGGY");
		mainTitle.setBounds(90, 11, 77, 27);
		mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
		panel.add(mainTitle);
		
		JLabel moduleLabel = new JLabel("MODULE");
		moduleLabel.setBounds(10, 68, 46, 14);
		panel.add(moduleLabel);
		
		JSlider timeSlider = new JSlider();
		timeSlider.setBounds(235, 11, 298, 26);
		panel.add(timeSlider);
		
		textField = new JTextField();
		textField.setBounds(235, 45, 298, 306);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblMode = new JLabel("MODE");
		lblMode.setBounds(10, 119, 46, 14);
		panel.add(lblMode);
		
		JRadioButton trackRadioButton = new JRadioButton("TRACK");
		trackRadioButton.setBounds(68, 119, 109, 23);
		panel.add(trackRadioButton);
		
		JRadioButton trainRadioButton = new JRadioButton("TRAIN");
		trainRadioButton.setBounds(68, 145, 109, 23);
		panel.add(trainRadioButton);
	}
}
