package TrackModel;

/**
* Crossing class. Is a station that holds the blocks that host (have platforms to) the station, a name
* a temperature, heaters, and passengers
* By default, true implies that a crossing is up, and is initialized to be up
*/
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Crossing implements java.io.Serializable {
  Block hostBlock;
  TrackModel superTrackModel;
  Boolean crossingState;

  public Crossing(TrackModel superTrackModel, Block hostBlock) {
    this.superTrackModel = superTrackModel;
    this.hostBlock = hostBlock;
    this.crossingState = true;
  }

  /**
  * Sets the crossing state of a crossing.
  * @param the new crossing state
  */
  public void setCrossingState(Boolean newCrossingState) {
    this.crossingState = newCrossingState;
  }
    
  /**
  * View the state of a crossing
  */
  public Boolean viewCrossingState() {
    return this.crossingState;
  }
}