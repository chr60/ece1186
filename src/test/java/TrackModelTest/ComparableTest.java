package TrackModel;

import TrackModel.TrackModel;
import TrackModel.Block;
import java.util.TreeSet;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
 public class ComparableTest{
	private static String[] fNames = {"src/test/resources/redline.csv", "src/test/resources/greenline.csv"};
	private static TrackModel track;
	private boolean verbose = false;
	
 	@BeforeEach
 	void init(){
 		String[] fNames = {"src/test/resources/redline.csv","src/test/resources/greenline.csv"};
 		this.track = new TrackModel("Test",this.verbose);
 		this.track.readCSV(fNames);
 	}

 	@Test
 	/**
 	* Test the comparable interface for a true result
 	*/
 	void testComparableTrue(){
 		assertTrue(this.track.viewTrackList().get("Red").get("A").get(1).equals(this.track.getBlock("Red","A",1)));
 	}

 	@Test
 	/**
 	* Test comparable false on the same line
 	*/
 	void testComparableFalseSameTrack(){
 		assertFalse(this.track.viewTrackList().get("Red").get("A").get(1).equals(this.track.getBlock("Red","A",2)));
 	}

 	@Test
 	/**
 	* Test comparable different tracks, same block+section
 	*/
 	void testComparableDifferentTrack(){
 		assertFalse(this.track.viewTrackList().get("Green").get("A").get(1).equals(this.track.getBlock("Red","A",1)));
 	}
 }