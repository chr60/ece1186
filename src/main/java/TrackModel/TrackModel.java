import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;

/* Class model and some associated utility functions for storing the TrackModel
*/	
public class TrackModel {
	private static TrackModel trackModel = new TrackModel();
	public TrackModel(){}

	public static HashMap<Integer, Block> trackList = 
		new HashMap<Integer, Block>();
	
	/*
	* Adds a selected block to the TrackModel. Expects a valid Block object.
	*
	* Parameters
	*
	* @param blockLine String. Name of the line being added
	* @param blockSection String. Name of the section the block belongs to
	* @param blockNum Int. Number of the block within a line 
	* @param Block. The block to be added.
	*/
	public static void addBlock(String blockLine,String blockSection, Integer blockNum, Block block){
		trackList.put(blockNum, block);
	}

	/*
	*Helper function for reading the information from the excel-dumped CSV
	*/
	public static void readCSV(){
		String  fName = "track.csv";
		String line = "";
		String delimiter = ",";
		Boolean initLine = true;
		Boolean defaultOccupied = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(fName))) {
			while ((line = reader.readLine()) != null){
				String[] str = line.split(delimiter);

				//For safety when parsing headers
				if (initLine.equals(false)){

					String blockLine = str[0];
					String blockSection = str[1];
					Integer blockNum = Integer.valueOf(str[2]);
					Double blockLen = Double.valueOf(str[3]);
					Double blockGrade = Double.valueOf(str[4]);
					Double elevation = Double.valueOf(str[7]);
					Double speedLimit = Double.valueOf(str[5]);

					Block myblock = new Block(defaultOccupied, blockLen, blockGrade, elevation, speedLimit, str[6]);
					trackModel.addBlock(blockLine, blockSection, blockNum, myblock );
					
					/*
					System.out.println(myblock.getOccupied());
					System.out.println(blockNum);
					*/
				}
				initLine = false;
			} 
		}catch(IOException|ArrayIndexOutOfBoundsException e){
				System.out.println("Finished Reading");
			}
	}

}