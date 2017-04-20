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

			public SchedulePanel(String [] stationNames, ArrayList<TrainSchedule> lotsOfTrains) {
          this.headers = stationNames;
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

					scheduleModel.setColumnIdentifiers(this.headers);
					scheduleModel.setColumnCount(this.headers.length);

      		scheduleModel.setRowCount(250);
      		schedTable.setModel(scheduleModel);

          //updateTable(lotsOfTrains, this.headers);


			}
/*
      public void updateTable(ArrayList<TrainSchedule> lineSchedule, String [] headers){
        if((lineSchedule.get(0) == null)){

        }else{

          for (int i = 0; i < lineSchedule.size(); i++) {
            schedTable.setValueAt(lineSchedule.get(i).getTrainID(), i, 0);
            for (int j = 1; j < headers.length; j++) {
              schedTable.setValueAt(convertTime(lineSchedule.get(i).getTime(0,j - 1)), i, j);
            }
          }
        }
      }
*/
      public static String convertTime(long secs) {

        int secondsDisplay = (int) secs % 60;
        long minutes = secs / 60;
        int minutesDisplay = (int) minutes % 60;
        long hours = minutes / 60;
        int hoursDisplay = (int) hours % 24;
        int ampm = hoursDisplay / 12;
        String meridian;
        String time = "";

        if (ampm == 0) {
          meridian = " AM";
        } else if (ampm > 0) {
          meridian = " PM";
          hoursDisplay %= 12;
        } else {
          meridian = " ERROR";
        }

        if (0 == hoursDisplay) {
          hoursDisplay = 12;
          time += hoursDisplay + ":";
        } else if (hoursDisplay < 10) {
          time += "0" + hoursDisplay + ":";
        } else {
          time += hoursDisplay + ":";
        }

        if (minutesDisplay < 10) {
          time += "0" + minutesDisplay + ":";
        } else {
          time += minutesDisplay + ":";
        }

        if (secondsDisplay < 10) {
          time += "0" + secondsDisplay;
        } else {
          time += secondsDisplay;
        }

        time += meridian;

        return time;

      }


	}
