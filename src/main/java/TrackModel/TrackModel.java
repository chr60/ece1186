package TrackModel;


import java.lang.NumberFormatException;
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

	public static HashMap<String,HashMap<String, HashMap<Integer, Block>>> trackList =
		new HashMap<String,HashMap<String, HashMap<Integer, Block>>>();

	/*
	*	Utility for testing the TrackModel from main. not intended for more than dev testing
	*/
	public static void main(String[] args){
		TrackModel track = new TrackModel();
		String[] fNames = {"redline.csv", "greenline.csv"};
		track.readCSV(fNames);

	}

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
		if (!trackList.containsKey(blockLine)){
			trackList.put(blockLine, new HashMap<String,HashMap<Integer,Block>>());
		}
		if (!trackList.get(blockLine).containsKey(blockSection)){
			trackList.get(blockLine).put(blockSection, new HashMap<Integer, Block>());
		}
		trackList.get(blockLine).get(blockSection).put(blockNum, block);
	}

	/*
	*Helper function for reading the information from the excel-dumped CSV
	*/
	public static void readCSV(String[] fNames){

		String line = "";
		String delimiter = ",";
		Boolean defaultOccupied = false;

		for (String s : fNames){

			Boolean initLine = true; //Allows for graceful failure of reading multiple csv's without exiting

			try (BufferedReader reader = new BufferedReader(new FileReader(s))) {
				while ((line = reader.readLine()) != null){
					String[] str = line.split(delimiter,-1);
					//System.out.println(s);

					//For safety when parsing headers
					if (initLine.equals(false)){

						String blockLine = str[0];
						String blockSection = str[1];
						Integer blockNum = Integer.valueOf(str[2]);
						Double blockLen = Double.valueOf(str[3]);
						Double blockGrade = Double.valueOf(str[4]);
						Double speedLimit = Double.valueOf(str[5]);
						String infrastructure = str[6];
						Double elevation = Double.valueOf(str[7]);

						String stationName = str[11];

						//Parse infrastructure string to underground + switch
						Boolean isUnderground = infrastructure.contains("UNDERGROUND");
						infrastructure = infrastructure.replace("UNDERGROUND","");

						//System.out.println(infrastructure);

						//Initialize and add block
						Block myblock = new Block(defaultOccupied, isUnderground, blockLen, blockGrade, elevation, speedLimit, stationName);
						TrackModel.addBlock(blockLine, blockSection, blockNum, myblock );

						/*
						System.out.println(myblock.getOccupied());
						System.out.println(blockNum);
						*/
					}
					initLine = false;
				}
			}catch(IOException|ArrayIndexOutOfBoundsException|NumberFormatException e){
					System.out.println("Finished Reading");
				}
			}
		}

}
