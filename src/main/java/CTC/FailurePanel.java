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

public class FailurePanel extends JPanel{

	public FailurePanel(){

		JButton btnNoFailure = new JButton("No Failure");
		btnNoFailure.setBounds(77, 38, 89, 23);
		add(btnNoFailure);
	}
}
