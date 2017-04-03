package TrackModel;

/**
* Lights class. It holds the lights object that is used on the track
* By default, true implies that a crossing is up, and is initialized to be true (green).
*/
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Lights implements java.io.Serializable{
    Block hostBlock;
    TrackModel superTrackModel;
    Boolean lightsState;

    public Lights(TrackModel superTrackModel, Block hostBlock){
        this.superTrackModel = superTrackModel;
        this.hostBlock = hostBlock;
        this.lightsState = true;
    }

    /**
    * Sets the lights state of a crossing. true -> green, false->red.
    * @param the new lights state
    */
    public void setLightsState(Boolean newLightsState) {
    	this.lightsState = newLightsState;
    }
    
    /**
    * View the state of a lights.
    * @return the current state of the lights
    */
    public Boolean viewCrossingState(){
    	return this.lightsState;
    }
}