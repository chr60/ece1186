package proj1;
import java.util.*;
import java.io.*;


public class Wayside_PLC {
	private File PLCFile;
	private PLC plc;
	public Wayside_PLC(File file) throws IOException{
		PLCFile = file;
		parseLine();
	}
	
	/**
	 * Parses line of PLC code in String Expressions for various parameters.
	 * @throws IOException
	 */
	public void parseLine() throws IOException{
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
                     switchExpression = expression[1];
                 else if(comp.equals("crossing"))
                     switchExpression = expression[1];
                 else if(comp.equals("crossingactivate"))
                     switchExpression = expression[1];
                 else if(comp.equals("monitor"))
                     switchExpression = expression[1];  
             }
		 reader.close();
		 setPLC(new PLC(proceedExpression, switchExpression, maintenanceExpression, crossingExpression, crossingStateExpression, monitorExpression));
     }

	public PLC getPLC() {
		return plc;
	}

	public void setPLC(PLC plc) {
		this.plc = plc;
	}	
}
