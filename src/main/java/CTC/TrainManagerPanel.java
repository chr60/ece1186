package CTC;
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
import java.util.*;

public class TrainManagerPanel extends JPanel {

      private JTable managerTable = new JTable();
      private DefaultTableModel trainManager = new DefaultTableModel();
      TrainManager tm;
      String [] headers = {"Line", "Train ID", "Current Position", "Speed", "Authority"};

			public TrainManagerPanel() {
          this.tm = tm;

					setLayout(new BorderLayout());

					JPanel options = new JPanel(new GridBagLayout());

					trainManager = new DefaultTableModel();
					managerTable = new JTable();
					add(new JScrollPane(managerTable));
					add(options, BorderLayout.NORTH);

					managerTable.setAutoCreateColumnsFromModel(true);
					managerTable.setAutoResizeMode(0);
					for (int i = 0; i < (managerTable.getColumnCount()); i++){
						managerTable.getColumn(i).setPreferredWidth(250);
				}

					trainManager.setColumnIdentifiers(headers);
					trainManager.setColumnCount(headers.length);
					trainManager.setRowCount(50);

					// Replace model
					managerTable.setModel(trainManager);

			}

	}
