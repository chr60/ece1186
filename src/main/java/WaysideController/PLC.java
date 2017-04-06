package WaysideController;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import TrackModel.TrackModel;
import TrackModel.Block;

public class PLC {

		// private String proceedExpression;
		// private String switchExpression;
		// private String maintenanceExpression;
		// private String crossingExpression;
		// private String crossingStateExpression;
		// private String monitorExpression;
		private TrackModel track;
		private File plcFile;
		private String line;
		private HashMap<String, String> switchMap = new HashMap<String, String>();	//Key is Switch label (Switch #), return val is expression
		private HashMap<Block, String> crossingMap = new HashMap<Block, String>();	//Block # isblock, return val is expression


		public PLC(TrackModel track, File file, String line){
			this.track = track;
			this.plcFile = file;
			this.line = line;
		}

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
				}
			}
			reader.close();
		}
		public void runSwitchPLC() throws ScriptException{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine logicengine = manager.getEngineByName("js");
			StringBuilder sb;
			int loc, loc2;

			for(String s: this.track.viewRootMap().keySet()){
				if(switchMap.keySet().contains(s)){
					String evalString = new String(this.switchMap.get(s));
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

					//evaluate logic and change switch accordingly
					Object result = logicengine.eval(sb.toString());
					if(Boolean.TRUE.equals(result))
					this.track.viewRootMap().get(s).setSwitchState(1);
					else
					this.track.viewRootMap().get(s).setSwitchState(0);
				}
			}
		}//runSwitchPLC

		public void runCrossingPLC() throws ScriptException{
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine logicengine = manager.getEngineByName("js");
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
					Object result = logicengine.eval(sb.toString());
					if(Boolean.TRUE.equals(result))
						this.track.viewCrossingMap().get(b).setCrossingState(true);
					else
						this.track.viewCrossingMap().get(b).setCrossingState(false);
			}
		}//runCrossingPLC

}
