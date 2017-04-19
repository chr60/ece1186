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

public class TrainManagerPanel extends JPanel {

      private JTable managerTable = new JTable();
      private DefaultTableModel manager = new DefaultTableModel();
      TrainManager trainManager;
      TrackModel dummyTrack;
      String [] headers = {"Line", "Train ID", "Position", "Speed", "Authority"};
      String lineAssign = "";
      ArrayList<TrainManager> managerList;


			public TrainManagerPanel(TrainManager tm, TrackModel dt, ArrayList<TrainManager> ml) {
          this.dummyTrack = dt;
          this.trainManager = tm;
          this.managerList = ml;

          setLineAssign(tm.getLine());

					setLayout(new BorderLayout());

					JPanel options = new JPanel(new GridBagLayout());

					manager = new DefaultTableModel();
					managerTable = new JTable();
					add(new JScrollPane(managerTable));
					add(options, BorderLayout.NORTH);

					managerTable.setAutoCreateColumnsFromModel(true);
					managerTable.setAutoResizeMode(0);
					for (int i = 0; i < (managerTable.getColumnCount()); i++){
						managerTable.getColumn(i).setPreferredWidth(250);
				}

					manager.setColumnIdentifiers(headers);
					manager.setColumnCount(headers.length);

      		manager.setRowCount(50);
      		managerTable.setModel(manager);

          updateTable(managerList);

			}

      public void setLineAssign(String line){
        this.lineAssign = line;
      }

      public String getTrainManagerLine(){
        return lineAssign;
      }

      public void updateTable(ArrayList<TrainManager> managers){
        String lineForTM = getTrainManagerLine();
        TrainManager trainManager = managers.get(0);

        for(int i=0; i<managers.size(); i++){
          if(managers.get(i).getLine().equals(lineForTM)){
            trainManager = managers.get(i);
          }else{
              System.out.println("No Line Assignment for TrainManager");
              trainManager = managers.get(0);
          }
        }

        Double currSpeed = 0.0;
        for(int j = 0; j < trainManager.getTrainList().size(); j++){
          managerTable.setValueAt(trainManager.getLine(),j, 0);
          managerTable.setValueAt(trainManager.getTrainList().get(j).getID(), j, 1);
          managerTable.setValueAt(trainManager.getTrainList().get(j).getPosition().blockNum(), j, 2);
          Integer currPos = trainManager.getTrainList().get(j).getPosition().blockNum();
          ArrayList<Block> blockNum = trainManager.getTrainList().get(j).getPath();
          Integer num = 0;
          int saveX = 0;
          for(int x = 0; x<blockNum.size(); x++){
            num = blockNum.get(x).blockNum();
            if(currPos.equals(num)){
              currSpeed = blockNum.get(x).getSuggestedSpeed();
              currSpeed = currSpeed*(0.621371);
            }
          }
          managerTable.setValueAt(currSpeed, j, 3);
          managerTable.setValueAt(trainManager.getTrainList().get(j).getPath().get(saveX).getAuthority().blockNum(), j, 4);
        }
      }

	}
