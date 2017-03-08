package TrackModelTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.ArrayList;
import java.util.Arrays;
import TrackModel.TrackModel;
import java.util.TreeSet;

/**
 * Tests the printing of hello world
 */
public class TrackModelTest 
	extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public TrackModelTest( String testName )
	{
		super( testName );
	}

	/** Test Suite
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( TrackModelTest.class );
	}

	/**
	 * Basic functionality test of reading from the CSV
	 */
	public void testApp()
	{
		String[] fNames={"src/test/resources/redline.csv"};
		String[] stationNames={"YARD","SHADYSIDE","HERRON AVE","SWISSVILLE","PENN STATION",
								"STEEL PLAZA","FIRST AVE","STATION SQUARE","SOUTH HILLS JUNCTION"};

		TreeSet<String> testSet = new TreeSet<>(Arrays.asList(stationNames));
		TrackModel testModel = new TrackModel();
		testModel.readCSV(fNames);

		assertTrue( testModel.stationList.equals(testSet) );
	}
}