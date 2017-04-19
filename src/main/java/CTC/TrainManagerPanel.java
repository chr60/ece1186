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
      String [] headers = {"Line", "Train ID", "Position", "Ft into Block", "Sugg Speed", "Actual Speed", "Authority"};
      String lineAssign = "";
      ArrayList<TrainManager> managerList;
      Integer lastClockTickAuthority;
      private String modeRunning;

			public TrainManagerPanel(TrainManager tm, TrackModel dt, ArrayList<TrainManager> ml) {
          this.dummyTrack = dt;
          this.trainManager = tm;
          this.managerList = ml;
          this.lastClockTickAuthority = 0;
          //this.modeRunning = getModeForPanel();

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

      		manager.setRowCount(200);
      		managerTable.setModel(manager);

          updateTable(managerList);

			}

      public void setModeForPanel(String mode){
        this.modeRunning = mode;
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

        for(int j = 0; j < trainManager.getTrainList().size(); j++){
          assert( j != 0);
          managerTable.setValueAt(trainManager.getLine(),j, 0);
          managerTable.setValueAt(trainManager.getTrainList().get(j).getID(), j, 1);
          managerTable.setValueAt(trainManager.getTrainList().get(j).getPosition().blockNum(), j, 2);
// mode check - do differently if in MBO mode
          if(this.modeRunning.equals("MBO")){
            if(trainManager.getTrainList().get(j).getDistance() == null){
              managerTable.setValueAt("0.0", j, 3);
            }else{
              managerTable.setValueAt(trainManager.getTrainList().get(j).getDistance()*(3.28084), j, 3);
            }
            managerTable.setValueAt(trainManager.getTrainList().get(j).getSuggSpeed()*(0.621371), j, 4);
            managerTable.setValueAt(trainManager.getTrainList().get(j).getActSpeed(), j, 5);
            managerTable.setValueAt(trainManager.getTrainList().get(j).getAuthority(), j, 6);
          }else{
            managerTable.setValueAt("N/A", j, 3);
            managerTable.setValueAt("N/A", j, 5);
            Integer currPos = trainManager.getTrainList().get(j).getPosition().blockNum();
            ArrayList<Block> blockNum = trainManager.getTrainList().get(j).getPath();
            Integer num = 0;
            int saveX = 0;
            Double currSpeed = 0.0;
            for(int x = 0; x<blockNum.size(); x++){
              num = blockNum.get(x).blockNum();
              if(currPos.equals(num)){
                currSpeed = blockNum.get(x).getSuggestedSpeed();
                currSpeed = currSpeed*(0.621371);
              }
            }
            managerTable.setValueAt(currSpeed, j, 4);
        // removes null/out of bounds exceptions
            if(trainManager.getTrainList().get(j).getPath().size() == 0 || (trainManager.getTrainList().get(j).getPath().get(saveX).getAuthority())==null){
              managerTable.setValueAt(lastClockTickAuthority, j, 6);
            }else{
              managerTable.setValueAt(trainManager.getTrainList().get(j).getPath().get(saveX).getAuthority().blockNum(), j, 6);
              lastClockTickAuthority = trainManager.getTrainList().get(j).getPath().get(saveX).getAuthority().blockNum();
            }
          }

        }
      }

	}
