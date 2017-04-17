package TrackModel;

/**
* Beacons class. It holds the beacon object that is used on the track
* By default, beacon messages are initilized to an empty string.
*/
import java.util.Random;
import java.io.Serializable;

public class Beacon implements java.io.Serializable {
  Block hostBlock;
  TrackModel superTrackModel;
  String beaconMessage;
  Double distance;

  public Beacon(TrackModel superTrackModel, Block hostBlock, String message, Double distance) {
    this.superTrackModel = superTrackModel;
    this.hostBlock = hostBlock;
    this.beaconMessage = message;
    this.distance = distance;
  }

  /**
  * Returns the beacon message.
  * @return the message stored in the beacon
  */
  public String getBeaconMessage() {
    return this.beaconMessage;
  }

  public Double getDistance() {
    return this.distance;
  }
}