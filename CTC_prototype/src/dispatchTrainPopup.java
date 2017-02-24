import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.*;

public class dispatchTrainPopup {

	private JFrame frame;
	private JTextField speed_txt;
	private JTextField authority_txt;
	private train newTrain;
	private static ArrayList<train> trainList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dispatchTrainPopup window = new dispatchTrainPopup(trainList);
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
	public dispatchTrainPopup(ArrayList<train> sendTrains) {
		initialize(sendTrains);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArrayList<train> sendTrains) {
		
		newTrain = new train();
		frame = new JFrame();
		frame.setBounds(100, 100, 323, 269);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dispatch Train");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(86, 11, 149, 32);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Line");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(76, 57, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		String[] lineColors = new String[] {"Red", "Green"};
		JComboBox<String> lineDropdown = new JComboBox<>(lineColors);
		lineDropdown.setBounds(130, 54, 70, 20);
		frame.getContentPane().add(lineDropdown);
		
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
		
		JButton btnDispatch = new JButton("Dispatch Train");
		btnDispatch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newTrain.setSpeed(Double.parseDouble(speed_txt.getText()));
				newTrain.setAuthority(Double.parseDouble(authority_txt.getText()));
				int x = sendTrains.size() + 1;
				newTrain.setTrainID(x);
				newTrain.setLine((String)lineDropdown.getSelectedItem());
				sendTrains.add(newTrain);
				//System.out.println(sendTrains.get(0).getTrainID());
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
