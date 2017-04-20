package WaysideController;

import TrackModel.Block;
import TrackModel.Lights;
import TrackModel.TrackModel;
import TrackModel.Switch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PLC {

  private TrackModel track;
  private File plcFile;
  private String line;
  private HashMap<String, String> switchMap = new HashMap<String, String>();	//Key is Switch label (Switch #), return val is expression
  private HashMap<Block, String> crossingMap = new HashMap<Block, String>();	//Block # isblock, return val is expression
  private HashMap<Integer, String> stopMap = new HashMap<Integer, String>();  //Key is Block #, return val is safe stop expression
  private HashMap<Block, String> lightMap = new HashMap<Block, String>();  //Key is Block, return val is lights expression
  private ArrayList<Block> previousOccupancy = new ArrayList<Block>();
  /**
   * Constructor for PLC
   * @param  TrackModel track - global track
   * @param  File       file - PLC file containing boolean expressions
   * @param  String     line - Line PLC is responsible for
   * @return            new PLC
   */
  public PLC(TrackModel track, File file, String line){
			this.track = track;
			this.plcFile = file;
			this.line = line;
		}

  /**
   * Called at init of PLC, parses and sets maps for Boolean expressions
   * @throws IOException [description]
   */
  public void parse() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(plcFile));
    String line;
		while((line = reader.readLine()) != null){
      String[] expression = line.split("-");
      String comp = expression[0];
      if(comp.equals("Switch")){
        this.switchMap.put(expression[0] + " " + expression[1] , expression[2]);
      }else if(comp.equals("Crossing")){
        this.crossingMap.put(track.getBlock(this.line, expression[1], Integer.parseInt(expression[2])), expression[3]);
      }else if(comp.equals("Stop")){
        this.stopMap.put(track.getBlock(this.line, Integer.parseInt(expression[1])).blockNum(), expression[2]);
      }else if(comp.equals("Light")){
        this.lightMap.put(track.getBlock(this.line, Integer.parseInt(expression[1])), expression[2]);
      }
    }
    reader.close();
  }

  /**
   * Updates previous occupancy list of track
   */
  public void updateOccupancy(){
    ArrayList<Block> previousOccupancy = this.track.getOccupiedBlocks(this.line);
  }

  /**
   * Decides whether or not to Stop a train.
   * @throws ScriptException [description]
   * @bug Need to move all replacements of "block", "section", "prev" to a single method for clarity for all runPLC functions
   */
  public void runStopPLC() throws ScriptException{
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine logicengine1 = manager.getEngineByName("js");
    ScriptEngine logicengine2 = manager.getEngineByName("js");
    ScriptEngine logicengine3 = manager.getEngineByName("js");

    StringBuilder sb;
    int loc, loc2;
    for(String sec : this.track.viewTrackList().get(line).keySet()){
      for(Integer b: this.track.viewTrackList().get(line).get(sec).keySet()){
        if(this.stopMap.get(b)!= null){
          sb = new StringBuilder(stopMap.get(b));

          //replaces ALL "block_letter_number" with block occupancy
          while((loc = sb.indexOf("block")) != -1){
            loc2 = loc;
            while(sb.charAt(loc2) != ')')
            ++loc2;
            String [] toReplace = sb.substring(loc, loc2).split("_");
            String section = toReplace[1];
            Integer blockNum = Integer.parseInt(toReplace[2]);
            sb.replace(loc, loc2, this.track.getBlock(this.line, section, blockNum).getOccupied().toString());
          }

          //replaces ALL "section_Letter" with section occupancy
          while((loc = sb.indexOf("section")) != -1){
            loc2 = loc;
            while(sb.charAt(loc2) != ')')
            ++loc2;
            String [] toReplace = sb.substring(loc, loc2).split("_");
            String section = toReplace[1];
            sb.replace(loc, loc2, this.track.sectionOccupancy(line, section).toString());
          }


          //evaluate TMR logic
          Object result1 = logicengine1.eval(sb.toString());
          Object result2 = logicengine2.eval(sb.toString());
          Object result3 = logicengine3.eval(sb.toString());
          System.out.println(Boolean.TRUE.equals(result1));
          System.out.println(Boolean.TRUE.equals(result2));
          System.out.println(Boolean.TRUE.equals(result3));
          //'VOTING' - if any blocks in safety 'buffer' are occupied, set speed to -1 to stop train.
          if ((Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result2)) ||
          (Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result3)) ||
          (Boolean.TRUE.equals(result2) && Boolean.TRUE.equals(result3)) )
          this.track.getBlock(line,sec,b).setSuggestedSpeed(new Double(-1));
        }
      }
    }
  }


  /**
   * Runs scripted PLC code for setting switch state
   * @throws ScriptException [description]
   */
  public void runSwitchPLC() throws ScriptException{
    //FOR VITAL TMR CALCULATION
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine logicengine1 = manager.getEngineByName("js");
    ScriptEngine logicengine2 = manager.getEngineByName("js");
    ScriptEngine logicengine3 = manager.getEngineByName("js");

    StringBuilder sb;
    int loc, loc2;

    for(String s: this.track.viewRootMap().keySet()){
      if(switchMap.keySet().contains(s)){
        String evalString = new String(this.switchMap.get(s));
        sb = new StringBuilder(evalString);

        while((loc = sb.indexOf("prev")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          Integer blockNum = Integer.parseInt(toReplace[2]);
          Block toCheck = this.track.getBlock(line, section, blockNum);
          if(previousOccupancy.contains(toCheck))
            sb.replace(loc, loc2, "true");
          else
            sb.replace(loc, loc2, "false");
        }

        //replaces ALL "block_letter_number" with block occupancy
        while((loc = sb.indexOf("block")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          Integer blockNum = Integer.parseInt(toReplace[2]);
          sb.replace(loc, loc2, this.track.getBlock(this.line, section, blockNum).getOccupied().toString());
        }

        //replaces ALL "section_Letter" with section occupancy
        while((loc = sb.indexOf("section")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          sb.replace(loc, loc2, this.track.sectionOccupancy(line, section).toString());
        }

        //evaluate TMR logic and change switch accordingly
        Object result1 = logicengine1.eval(sb.toString());
        Object result2 = logicengine2.eval(sb.toString());
        Object result3 = logicengine3.eval(sb.toString());

        if(this.track.viewRootMap().get(s).getOccupied()==false){	//cannot changea switch if switch block is occupied
          //'VOTING'
          if ((Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result2)) ||
              (Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result3)) ||
              (Boolean.TRUE.equals(result2) && Boolean.TRUE.equals(result3)) )
                this.track.viewRootMap().get(s).getAssociatedSwitch().setSwitchState(true);
          else if((Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result2)) ||
                  (Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result3)) ||
                  (Boolean.FALSE.equals(result2) && Boolean.FALSE.equals(result3)) )
                this.track.viewRootMap().get(s).getAssociatedSwitch().setSwitchState(false);
        }
      }
    }
  }//runSwitchPLC

  /**
   * Runs scripted PLC code for setting crossing state
   * @throws ScriptException [description]
   */
  public void runCrossingPLC() throws ScriptException{
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine logicengine1 = manager.getEngineByName("js");
    ScriptEngine logicengine2 = manager.getEngineByName("js");
    ScriptEngine logicengine3 = manager.getEngineByName("js");
    StringBuilder sb;
    int loc, loc2;

    for(Block b: this.track.viewCrossingMap().keySet()){
      if(!crossingMap.keySet().contains(b)) continue;
      String evalString = new String(this.crossingMap.get(b));
      sb = new StringBuilder(evalString);

      //replaces ALL "block_letter_number" with block occupancy
      while((loc = sb.indexOf("block")) != -1){
        loc2 = loc;
        while(sb.charAt(loc2) != ')')
        ++loc2;
        String [] toReplace = sb.substring(loc, loc2).split("_");
        String section = toReplace[1];
        Integer blockNum = Integer.parseInt(toReplace[2]);
        sb.replace(loc, loc2, this.track.getBlock(this.line, section, blockNum).getOccupied().toString());
      }

      //replaces ALL "section_Letter" with section occupancy
      while((loc = sb.indexOf("section")) != -1){
        loc2 = loc;
        while(sb.charAt(loc2) != ')')
        ++loc2;
        String [] toReplace = sb.substring(loc, loc2).split("_");
        String section = toReplace[1];
        sb.replace(loc, loc2, this.track.sectionOccupancy(line, section).toString());
      }

      Object result1 = logicengine1.eval(sb.toString());
      Object result2 = logicengine2.eval(sb.toString());
      Object result3 = logicengine3.eval(sb.toString());

        //'VOTING'
        if ((Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result2)) ||
            (Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result3)) ||
            (Boolean.TRUE.equals(result2) && Boolean.TRUE.equals(result3)) )
            this.track.viewCrossingMap().get(b).setCrossingState(true);
        else if((Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result2)) ||
                (Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result3)) ||
                (Boolean.FALSE.equals(result2) && Boolean.FALSE.equals(result3)) )
            this.track.viewCrossingMap().get(b).setCrossingState(false);
      }
  }//runCrossingPLC

  public void runLightsPLC()  throws ScriptException{
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine logicengine1 = manager.getEngineByName("js");
    ScriptEngine logicengine2 = manager.getEngineByName("js");
    ScriptEngine logicengine3 = manager.getEngineByName("js");

    // HashMap<String, ArrayList<Block>> leafMap = this.track.viewLeafMap();
    StringBuilder sb;
    int loc, loc2;

    for(Block b : this.track.viewLightsMap().keySet()){
      if(lightMap.keySet().contains(b)){
        sb = new StringBuilder(lightMap.get(b));

        while((loc = sb.indexOf("prev")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          Integer blockNum = Integer.parseInt(toReplace[2]);
          Block toCheck = this.track.getBlock(line, section, blockNum);
          if(previousOccupancy.contains(toCheck))
            sb.replace(loc, loc2, "true");
          else
            sb.replace(loc, loc2, "false");
        }

        while((loc = sb.indexOf("block")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          Integer blockNum = Integer.parseInt(toReplace[2]);
          sb.replace(loc, loc2, this.track.getBlock(this.line, section, blockNum).getOccupied().toString());
        }

        //replaces ALL "section_Letter" with section occupancy
        while((loc = sb.indexOf("section")) != -1){
          loc2 = loc;
          while(sb.charAt(loc2) != ')')
          ++loc2;
          String [] toReplace = sb.substring(loc, loc2).split("_");
          String section = toReplace[1];
          sb.replace(loc, loc2, this.track.sectionOccupancy(line, section).toString());
        }

        Object result1 = logicengine1.eval(sb.toString());
        Object result2 = logicengine2.eval(sb.toString());
        Object result3 = logicengine3.eval(sb.toString());
        //'VOTING'
        if ((Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result2)) ||
        (Boolean.TRUE.equals(result1) && Boolean.TRUE.equals(result3)) ||
        (Boolean.TRUE.equals(result2) && Boolean.TRUE.equals(result3)) )
          this.track.viewLightsMap().get(b).setLightsState(true);
        else if((Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result2)) ||
        (Boolean.FALSE.equals(result1) && Boolean.FALSE.equals(result3)) ||
        (Boolean.FALSE.equals(result2) && Boolean.FALSE.equals(result3)) )
          this.track.viewLightsMap().get(b).setLightsState(false);

      }
    }
  }
}
