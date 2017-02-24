package proj1;

import java.util.*;
import javax.script.*;

public class PLC {
		
		private String proceedExpression;
		private String switchExpression;
		private String maintenanceExpression; 
		private String crossingExpression;
		private String crossingStateExpression;
		private String monitorExpression;
		
		public PLC(String pE, String sE, String maE, String cE, String csE, String moE){
			this.proceedExpression = pE;
			this.switchExpression = sE;
			this.maintenanceExpression = maE;
			this.crossingExpression = cE;
			this.crossingStateExpression = csE;
			this.monitorExpression = moE;
		}	


		public boolean proceedEval(Block b) throws ScriptException{
			StringBuilder SB = new StringBuilder(proceedExpression);
			int loc = SB.indexOf("nextBlockOccupied");
			if(loc > 0)
				SB.replace(loc, loc+"nextBlockOccupied".length(), new Boolean(b.getNextBlock().isOccupied()).toString());
			
			loc = SB.indexOf("nextBlockBroken");
			if(loc > 0)
				SB.replace(loc, loc+"nextBlockBroken".length(), new Boolean(b.getNextBlock().isBroken()).toString());
			
			loc = SB.indexOf("nextBlockClosed");
			if(loc > 0)
				SB.replace(loc, loc+"nextBlockClosed".length(), new Boolean(b.getNextBlock().isClosed()).toString());
			
			loc = SB.indexOf("upcomingBlockOccupied");
			if(loc > 0)
				SB.replace(loc, loc+"upcomingBlockOccupied".length(), new Boolean(b.getUpcomingBlock().isOccupied()).toString());
			
			loc = SB.indexOf("upcomingBlockClosed");
			if(loc > 0)
				SB.replace(loc, loc+"upcomingBlockClosed".length(), new Boolean(b.getUpcomingBlock().isBroken()).toString());
			
			loc = SB.indexOf("upcomingBlockBroken");
			if(loc > 0)
				SB.replace(loc, loc+"upcomingBlockBroken".length(), new Boolean(b.getUpcomingBlock().isClosed()).toString());
			
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine logicengine = manager.getEngineByName("js");
			Object result = logicengine.eval(SB.toString());
			return Boolean.TRUE.equals(result);
		}
}
