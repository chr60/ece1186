//Module Imports
import CTC.CTCgui;
import CTC.DummyTrain;
import CTC.TrainManager;
import CommonUIElements.ClockAndLauncher.Launcher;
import TrackModel.Block;
import TrackModel.Station;
import TrackModel.TrackModel;
import TrainModel.GPS;
import TrainModel.Train;
import TrainModel.TrainHandler;
import MBO.Schedule;

//Test Imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Utils
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.script.ScriptException;


public class MBOTest{

  private TrackModel track;
  private Schedule schedule;
  private TrainHandler handler;
  private TrainManager manager;

  @BeforeEach
  /**
  * Initialization of the trackmodel to be used for testing
  */
  void init(){
    String[] fNames = {"src/test/resources/redline.csv"};
    this.track = new TrackModel("MBOTest");
    String override = "test-clases/redlinelink.csv";
    String[] overrideNames = {override};
    this.track.readCSV(fNames, overrideNames);
  }

  
  //@Test
  /**
  * Generate Train Schedule
  */
  @DisplayName("Generate Train Schedule")
  void testGenerateTrainSchedule() throws IOException, ScriptException{
    
    schedule = getRedSchedule();
    schedule.createSchedule(1, 0, 1);
  }

  private Block[] hardCodeStops(String line) {
    ArrayList<Block> blocks = new ArrayList<Block>();

    if ("Red".equals(line)) {
      blocks.add(track.getBlock(line, "C", new Integer(7)));
      blocks.add(track.getBlock(line, "F", new Integer(16)));
      blocks.add(track.getBlock(line, "G", new Integer(21)));
      blocks.add(track.getBlock(line, "H", new Integer(25)));
      blocks.add(track.getBlock(line, "H", new Integer(35)));
      blocks.add(track.getBlock(line, "H", new Integer(45)));
      blocks.add(track.getBlock(line, "I", new Integer(48)));
      blocks.add(track.getBlock(line, "L", new Integer(60)));
      blocks.add(track.getBlock(line, "I", new Integer(48)));
      blocks.add(track.getBlock(line, "H", new Integer(45)));
      blocks.add(track.getBlock(line, "H", new Integer(35)));
      blocks.add(track.getBlock(line, "H", new Integer(25)));
      blocks.add(track.getBlock(line, "G", new Integer(21)));
      blocks.add(track.getBlock(line, "F", new Integer(16)));
    } else if ("Green".equals(line)) {
      
    }

    return blocks.toArray(new Block [0]);
  }

  public Schedule getRedSchedule() {
    manager = new TrainManager("Red", track);
    handler = new TrainHandler(track);

    String [] redStationNames = {"YARD", "SHADYSIDE", "HERRON AVE", "SWISSVILLE", 
      "PENN STATION", "STEEL PLAZA", "FIRST AVE", "STATION SQUARE", "SOUTH HILLS JUNCTION"};
    String [] redStationOrder = {"SHADYSIDE", "HERRON AVE", "SWISSVILLE", "PENN STATION", 
      "STEEL PLAZA", "FIRST AVE", "STATION SQUARE", "SOUTH HILLS JUNCTION", "STATION SQUARE", 
      "FIRST AVE", "STEEL PLAZA", "PENN STATION", "SWISSVILLE", "HERRON AVE", "SHADYSIDE"};
    int [] redStationTimes = {162, 78, 30, 48, 66, 66, 42, 78, 78, 42, 66, 66, 48, 30, 78};
    int redLineLoopTime = 2040; //in seconds, includies dwell


    return (new Schedule(track, manager, handler, hardCodeStops("Red"), "Red",
           redStationNames, redStationOrder, redStationTimes, redLineLoopTime, null));
  }
}
