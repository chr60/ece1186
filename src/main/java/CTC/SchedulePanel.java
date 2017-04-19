package CTC;

import TrackModel.*;
import MBO.*;
import WaysideController.*;
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
import java.lang.Math;

public class SchedulePanel extends JPanel {

      private JTable schedTable = new JTable();
      private DefaultTableModel scheduleModel = new DefaultTableModel();
      String [] headers;

			public SchedulePanel() {

					setLayout(new BorderLayout());

					JPanel options = new JPanel(new GridBagLayout());

					scheduleModel = new DefaultTableModel();
					schedTable = new JTable();
					add(new JScrollPane(schedTable));
					add(options, BorderLayout.NORTH);

					schedTable.setAutoCreateColumnsFromModel(true);
					schedTable.setAutoResizeMode(0);
					for (int i = 0; i < (schedTable.getColumnCount()); i++){
						schedTable.getColumn(i).setPreferredWidth(250);
				}

					scheduleModel.setColumnIdentifiers(headers);
					scheduleModel.setColumnCount(headers.length);

      		scheduleModel.setRowCount(50);
      		schedTable.setModel(scheduleModel);

          updateTable();


			}

      public void updateTable(){

      }

	}
