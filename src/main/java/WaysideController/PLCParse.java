package WaysideController;

import TrackModel.*;
import java.io.*;
import java.util.*;


public class PLCParse {

	/**
	 * Starts new PLC generation
	 * @param  File file          [description]
	 * @return      PLC
	 */
	public static PLC newPLC(File file) throws IOException{
		return parseLine(file);
	}

	/**

	 * Parses line of PLC code in String Expressions for various parameters.
	 * @throws IOException
	 */
	public static PLC parseLine(File PLCFile) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(PLCFile));
		String line, proceedExpression = null, switchExpression = null, maintenanceExpression = null, crossingExpression = null, crossingStateExpression = null, monitorExpression = null;
		 while((line = reader.readLine()) != null){
             String[] expression = line.split("-");
             String comp =expression[0].toLowerCase();
                 if(comp.equals("proceed"))
                     proceedExpression = expression[1];
                 else if(comp.equals("switch"))
                     switchExpression = expression[1];
                 else if(comp.equals("maintenance"))
                     maintenanceExpression = expression[1];
                 else if(comp.equals("crossing"))
                     crossingExpression = expression[1];
                 else if(comp.equals("crossingactivate"))
                     crossingStateExpression = expression[1];
                 else if(comp.equals("monitor"))
                     monitorExpression = expression[1];
             }
		 reader.close();
		 return new PLC(proceedExpression, switchExpression, maintenanceExpression, crossingExpression, crossingStateExpression, monitorExpression);
     }

}
