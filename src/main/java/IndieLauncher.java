import TrackModel.TrackModel;
import WaysideController.*;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.*;


public class IndieLauncher{
	public static final String[] fNames = {"resources/redline.csv"};

	public static void main(String[] args){

		//We can move these into functions for cleanliness.
		TrackModel globalTrack = new TrackModel();
		globalTrack.readCSV(fNames);

    try{
      WaysideGUI wayside = new WaysideGUI(globalTrack, null);
    } catch (ClassNotFoundException ex){

    } catch (InstantiationException ex){

    } catch (IllegalAccessException ex){

    } catch (UnsupportedLookAndFeelException ex){

    } catch (IOException ex){

    }

	}
}
