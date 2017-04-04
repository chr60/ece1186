package CTC;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.*;
import java.awt.*;

public class TestPanel extends JPanel{

	public TestPanel(){

		JLabel lblBreak = new JLabel("Break Comm with MBO");
		lblBreak.setBounds(3, 5, 119, 14);
		add(lblBreak);
		lblBreak.setHorizontalAlignment(SwingConstants.CENTER);

		JToggleButton toggleButton_1 = new JToggleButton("N");
		toggleButton_1.setBounds(13, 21, 45, 16);
		add(toggleButton_1);
		toggleButton_1.setSelected(true);
		toggleButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JToggleButton toggleButton = new JToggleButton("Y");
		toggleButton.setBounds(56, 21, 45, 16);
		add(toggleButton);
		toggleButton.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel lblWayside = new JLabel("with Wayside");
		lblWayside.setBounds(27, 48, 64, 14);
		add(lblWayside);
		lblWayside.setHorizontalAlignment(SwingConstants.CENTER);
		lblWayside.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JToggleButton toggleButton_3 = new JToggleButton("Y");
		toggleButton_3.setBounds(56, 67, 45, 16);
		add(toggleButton_3);
		toggleButton_3.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JToggleButton toggleButton_2 = new JToggleButton("N");
		toggleButton_2.setBounds(13, 67, 45, 16);
		add(toggleButton_2);
		toggleButton_2.setSelected(true);
		toggleButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));


	}
}
